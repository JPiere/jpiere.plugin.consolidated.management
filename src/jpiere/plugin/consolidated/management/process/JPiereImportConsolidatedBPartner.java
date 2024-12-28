/******************************************************************************
 * Product: JPiere                                                            *
 * Copyright (C) Hideaki Hagiwara (h.hagiwara@oss-erp.co.jp)                  *
 *                                                                            *
 * This program is free software, you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY.                          *
 * See the GNU General Public License for more details.                       *
 *                                                                            *
 * JPiere is maintained by OSS ERP Solutions Co., Ltd.                        *
 * (http://www.oss-erp.co.jp)                                                 *
 *****************************************************************************/
package jpiere.plugin.consolidated.management.process;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.TimeZone;
import java.util.logging.Level;

import org.adempiere.model.ImportValidator;
import org.adempiere.process.ImportProcess;
import org.adempiere.util.IProcessUI;
import org.compiere.model.MBPartner;
import org.compiere.model.MColumn;
import org.compiere.model.MTable;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.PO;
import org.compiere.model.X_I_BPartner;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.compiere.util.Util;

import jpiere.plugin.consolidated.management.model.MCMBPartner;
import jpiere.plugin.consolidated.management.model.MCMBPartnerLocation;
import jpiere.plugin.consolidated.management.model.X_I_BPartnerJP;

import jpiere.plugin.consolidated.management.util.JPiereLocationUtil;

/**
 *	JPIERE-0636 : Import Consolidated BPartners from I_BPartnerJP
 *
 * @author Hideaki Hagiwara
 */
public class JPiereImportConsolidatedBPartner extends SvrProcess implements ImportProcess
{
	/**	Delete old Imported				*/
	private boolean			p_deleteOldImported = false;
	/**	Only validate, don't import		*/
	private boolean			p_IsValidateOnly = false;

	/** Effective						*/
	private Timestamp		m_DateValue = null;

	private int p_I_BPartnerJP_ID = 0;

	private IProcessUI processMonitor = null;

	private String message = null;

	private long startTime = System.currentTimeMillis();

	/**
	 *  Prepare - e.g., get Parameters.
	 */
	protected void prepare()
	{
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (name.equals("DeleteOldImported"))
				p_deleteOldImported = "Y".equals(para[i].getParameter());
			else if (name.equals("IsValidateOnly"))
				p_IsValidateOnly = para[i].getParameterAsBoolean();
			else if (name.equals("I_BPartnerJP_ID"))
				p_I_BPartnerJP_ID = para[i].getParameterAsInt();
			else
				log.log(Level.SEVERE, "Unknown Parameter: " + name);
		}
		if (m_DateValue == null)
			m_DateValue = new Timestamp (System.currentTimeMillis());

	}	//	prepare


	/**
	 *  Perform process.
	 *  @return Message
	 *  @throws Exception
	 */
	protected String doIt() throws java.lang.Exception
	{

		processMonitor = Env.getProcessUI(getCtx());

		StringBuilder sql = null;
		int no = 0;

		/**	Delete Old Imported */
		if (p_deleteOldImported)
		{
			sql = new StringBuilder ("DELETE FROM I_BPartnerJP ")
					.append("WHERE AD_Client_ID=0 AND I_IsImported='Y' AND Processed='Y' ");
			try {
				no = DB.executeUpdateEx(sql.toString(), get_TrxName());
				if (log.isLoggable(Level.FINE)) log.fine("Delete Old Impored =" + no);
			}catch(Exception e) {
				throw new Exception(Msg.getMsg(getCtx(), "Error") + sql );
			}
		}

		/** Reset Message */
		sql = new StringBuilder ("UPDATE I_BPartnerJP ")
				.append("SET I_ErrorMsg='' ")
				.append(" WHERE I_IsImported<>'Y'").append(getWhereClause());
		try {
			no = DB.executeUpdateEx(sql.toString(), get_TrxName());
			if (log.isLoggable(Level.FINE)) log.fine(String.valueOf(no));
		}catch(Exception e) {
			throw new Exception(Msg.getMsg(getCtx(), "Error") + sql );
		}

		ModelValidationEngine.get().fireImportValidate(this, null, null, ImportValidator.TIMING_BEFORE_VALIDATE);

		/** Reverse Lookup Surrogate Key */
		message = Msg.getMsg(getCtx(), "Matching") + " : " + Msg.getElement(getCtx(), "JP_CM_BPartner_ID");
		if(processMonitor != null)	processMonitor.statusUpdate(message);
		if(reverseLookupJP_CM_BPartner_ID())
			commitEx();
		else
			return message;

		message = Msg.getMsg(getCtx(), "Matching") + " : " + Msg.getElement(getCtx(), "JP_Corporation_ID");
		if(processMonitor != null)	processMonitor.statusUpdate(message);
		if(reverseLookupJP_Corporation_ID())
			commitEx();
		else
			return message;

		message = Msg.getMsg(getCtx(), "Matching") + " : " + Msg.getElement(getCtx(), "C_Location_ID");
		if(processMonitor != null)	processMonitor.statusUpdate(message);
		if(reverseLookupC_Location_ID())
			commitEx();
		else
			return message;

		ModelValidationEngine.get().fireImportValidate(this, null, null, ImportValidator.TIMING_AFTER_VALIDATE);

		commitEx();
		if (p_IsValidateOnly)
		{
			return "Validated";
		}


		/** Register & Update Business Partner */
		String msg = Msg.getMsg(getCtx(), "Register") +" & "+ Msg.getMsg(getCtx(), "Update")  + " " + Msg.getElement(getCtx(), "JP_CM_BPartner_ID");
		if (processMonitor != null)	processMonitor.statusUpdate(msg);

		sql = new StringBuilder ("SELECT * FROM I_BPartnerJP WHERE AD_Client_ID=0 AND (I_IsImported='N' OR Processed='N') ");
				if(p_I_BPartnerJP_ID > 0)
					sql.append(" AND I_BPartnerJP_ID=? ");
		sql.append(" ORDER BY Value, ContactName, EMail ");


		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int recordsNum = 0;
		int successNewNum = 0;
		int successUpdateNum = 0;
		int failureNewNum = 0;
		int failureUpdateNum = 0;
		String records = Msg.getMsg(getCtx(), "JP_NumberOfRecords");
		String success = Msg.getMsg(getCtx(), "JP_Success");
		String failure = Msg.getMsg(getCtx(), "JP_Failure");
		String newRecord = Msg.getMsg(getCtx(), "New");
		String updateRecord = Msg.getMsg(getCtx(), "Update");

		try
		{
			pstmt = DB.prepareStatement(sql.toString(), get_TrxName());
			if(p_I_BPartnerJP_ID > 0)
				pstmt.setInt(1, p_I_BPartnerJP_ID);

			rs = pstmt.executeQuery();
			String preValue = "";
			MCMBPartner bpartner = null;

			while (rs.next())
			{
				X_I_BPartnerJP imp = new X_I_BPartnerJP (getCtx (), rs, get_TrxName());

				boolean isNew = true;
				if(imp.getJP_CM_BPartner_ID() != 0)
				{
					isNew =false;
					bpartner = new MCMBPartner(getCtx(), imp.getJP_CM_BPartner_ID(), get_TrxName());

				}else{

					if(preValue.equals(imp.getValue()))
					{
						isNew = false;

					}else {

						preValue = imp.getValue();

					}

				}

				if(isNew)
				{
					bpartner = new MCMBPartner(getCtx(), 0, get_TrxName());
					if(createNewBPartner(imp,bpartner))
						successNewNum++;
					else
						failureNewNum++;

				}else{

					if(updateBPartner(imp, bpartner))
						successUpdateNum++;
					else
						failureUpdateNum++;

				}

				commitEx();

				recordsNum++;
				if (processMonitor != null)
				{
					processMonitor.statusUpdate(
						newRecord + "( "+  success + " : " + successNewNum + "  /  " +  failure + " : " + failureNewNum + " ) + "
						+ updateRecord + " ( "+  success + " : " + successUpdateNum + "  /  " +  failure + " : " + failureUpdateNum+ " ) "
						);
				}

			}//while

		}catch (Exception e) {

			log.log(Level.SEVERE, e.toString(), e);
			throw e;

		}finally{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}

		long endTime = System.currentTimeMillis();
		long time = endTime - startTime;
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss.SSS");
        formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
        String timeFormatted = formatter.format(time);

		return Msg.getMsg(getCtx(), "ProcessOK") + "  "  + timeFormatted + "  "+ records + recordsNum + " = "	+
		newRecord + "( "+  success + " : " + successNewNum + "  /  " +  failure + " : " + failureNewNum + " ) + "
		+ updateRecord + " ( "+  success + " : " + successUpdateNum + "  /  " +  failure + " : " + failureUpdateNum+ " ) ";

	}	//	doIt


	//@Override
	public String getWhereClause()
	{
		StringBuilder msgreturn = new StringBuilder(" AND AD_Client_ID=0");
		return msgreturn.toString();
	}


	//@Override
	public String getImportTableName()
	{
		return X_I_BPartner.Table_Name;
	}


	/**
	 * Reverse look up C_BPartner_ID From Value
	 *
	 * @throws Exception
	 */
	private boolean reverseLookupJP_CM_BPartner_ID() throws Exception
	{
		StringBuilder sql = new StringBuilder ("UPDATE I_BPartnerJP i ")
			.append("SET JP_CM_BPartner_ID=(SELECT JP_CM_BPartner_ID FROM JP_CM_BPartner p")
			.append(" WHERE i.Value=p.Value AND i.AD_Client_ID=p.AD_Client_ID) ")
			.append("WHERE i.C_BPartner_ID IS NULL AND i.Value IS NOT NULL ")
			.append(" AND I_IsImported<>'Y'").append(getWhereClause());
		if(p_I_BPartnerJP_ID > 0)
			sql.append(" AND I_BPartnerJP_ID=? ");

		try {

			if(p_I_BPartnerJP_ID > 0)
			{
				Object[] objs = new Object[]{p_I_BPartnerJP_ID};
				DB.executeUpdateEx(sql.toString(), objs, get_TrxName());
			}else {
				DB.executeUpdateEx(sql.toString(), get_TrxName());
			}

		}catch(Exception e) {
			throw new Exception(Msg.getMsg(getCtx(), "Error") + message +" : " + e.toString() +" : " + sql );
		}

		return true;

	}//reverseLookupJP_CM_BPartner_ID

	
	/**
	 * Reverse Look up JP_Corporation_ID From JP_CorporationValue
	 *
	 *
	 */
	private boolean reverseLookupJP_Corporation_ID() throws Exception
	{
		int no = 0;

		StringBuilder sql = new StringBuilder ("UPDATE I_BPartnerJP i ")
				.append("SET JP_Corporation_ID=(SELECT JP_Corporation_ID FROM JP_Corporation p")
				.append(" WHERE i.JP_CorporationValue=p.Value AND p.AD_Client_ID=i.AD_Client_ID) ")
				.append(" WHERE i.JP_CorporationValue IS NOT NULL")
				.append(" AND i.I_IsImported='N'").append(getWhereClause());
		if(p_I_BPartnerJP_ID > 0)
			sql.append(" AND I_BPartnerJP_ID=? ");

		try
		{
			if(p_I_BPartnerJP_ID > 0)
			{
				Object[] objs = new Object[]{p_I_BPartnerJP_ID};
				DB.executeUpdateEx(sql.toString(), objs, get_TrxName());
			}else {
				DB.executeUpdateEx(sql.toString(), get_TrxName());
			}

		}catch(Exception e) {
			throw new Exception(Msg.getMsg(getCtx(), "Error") + message +" : " + e.toString() +" : " + sql );
		}

		//Invalid JP_CorporationValue
		message = Msg.getMsg(getCtx(), "Error") + Msg.getMsg(getCtx(), "Invalid")+Msg.getElement(getCtx(), "JP_CorporationValue");
		sql = new StringBuilder ("UPDATE I_BPartnerJP ")
			.append("SET I_ErrorMsg='"+ message + "'")
			.append(" WHERE JP_CorporationValue IS NOT NULL AND JP_Corporation_ID IS NULL ")
			.append(" AND I_IsImported<>'Y'").append(getWhereClause());
		if(p_I_BPartnerJP_ID > 0)
			sql.append(" AND I_BPartnerJP_ID=? ");

		try
		{
			if(p_I_BPartnerJP_ID > 0)
			{
				Object[] objs = new Object[]{p_I_BPartnerJP_ID};
				no =DB.executeUpdateEx(sql.toString(), objs, get_TrxName());
			}else {
				no = DB.executeUpdateEx(sql.toString(), get_TrxName());
			}
		}catch(Exception e) {
			throw new Exception( message + " : " + e.toString() + " : " + sql);
		}

		if(no > 0)
		{
			return false;
		}

		return true;
	}

	/**
	 * Reverse Look up C_Location_ID From JP_Location_Label
	 *
	 * @throws Exception
	 */
	private boolean reverseLookupC_Location_ID() throws Exception
	{
		int no = 0;

		StringBuilder sql = new StringBuilder ("UPDATE I_BPartnerJP i ")
				.append("SET C_Location_ID=(SELECT C_Location_ID FROM C_Location p")
				.append(" WHERE i.JP_Location_Label=p.JP_Location_Label AND p.AD_Client_ID=i.AD_Client_ID ) ")
				.append(" WHERE i.JP_Location_Label IS NOT NULL")
				.append(" AND i.I_IsImported='N'").append(getWhereClause());
		if(p_I_BPartnerJP_ID > 0)
			sql.append(" AND I_BPartnerJP_ID=? ");

		try {

			if(p_I_BPartnerJP_ID > 0)
			{
				Object[] objs = new Object[]{p_I_BPartnerJP_ID};
				DB.executeUpdateEx(sql.toString(), objs, get_TrxName());
			}else {
				DB.executeUpdateEx(sql.toString(), get_TrxName());
			}

			if (log.isLoggable(Level.FINE)) log.fine("Reverse Look up C_Location_ID From JP_Location_Label -> #" + no);

		}catch(Exception e) {
			throw new Exception(Msg.getMsg(getCtx(), "Error") + message +" : " + e.toString() +" : " + sql );
		}


		return true;

	}//reverseLookupC_Location_ID



	/**
	 * Create New Business Partner
	 *
	 * @param importBpartner
	 * @throws Exception
	 */
	private boolean createNewBPartner(X_I_BPartnerJP importBPartner, MCMBPartner newBPartner) throws Exception
	{
		newBPartner.setAD_Org_ID(importBPartner.getAD_Org_ID());

		if(Util.isEmpty(importBPartner.getValue()))
		{
			Object[] objs = new Object[]{Msg.getElement(Env.getCtx(), "Value")};
			importBPartner.setI_ErrorMsg(Msg.getMsg(getCtx(), "Error") + Msg.getMsg(Env.getCtx(),"JP_Mandatory",objs));
			importBPartner.setI_IsImported(false);
			importBPartner.setProcessed(false);
			importBPartner.saveEx(get_TrxName());
			return false;
		}

		if(Util.isEmpty(importBPartner.getName()))
		{
			Object[] objs = new Object[]{Msg.getElement(Env.getCtx(), "Name")};
			importBPartner.setI_ErrorMsg(Msg.getMsg(getCtx(), "Error") + Msg.getMsg(Env.getCtx(),"JP_Mandatory",objs));
			importBPartner.setI_IsImported(false);
			importBPartner.setProcessed(false);
			importBPartner.saveEx(get_TrxName());
			return false;
		}

		ModelValidationEngine.get().fireImportValidate(this, importBPartner, newBPartner, ImportValidator.TIMING_BEFORE_IMPORT);

		PO.copyValues(importBPartner, newBPartner);
		newBPartner.setIsActive(importBPartner.isI_IsActiveJP());

		ModelValidationEngine.get().fireImportValidate(this, importBPartner, newBPartner, ImportValidator.TIMING_AFTER_IMPORT);

		try {
			newBPartner.saveEx(get_TrxName());
		}catch (Exception e) {
			importBPartner.setI_ErrorMsg(Msg.getMsg(getCtx(),"SaveIgnored") + Msg.getElement(getCtx(), "C_BPartner_ID") + " -> " + e.toString());
			importBPartner.setI_IsImported(false);
			importBPartner.setProcessed(false);
			importBPartner.saveEx(get_TrxName());
			return false;
		}

		importBPartner.setJP_CM_BPartner_ID(newBPartner.getJP_CM_BPartner_ID());

		//Create C_BPartner_Location
		if(!Util.isEmpty(importBPartner.getJP_BPartner_Location_Name()) || !Util.isEmpty(importBPartner.getJP_Location_Label()) )
		{
			int JP_CM_BPartner_Location_ID = createBPartnerLocation(importBPartner);
			if(JP_CM_BPartner_Location_ID == 0)
			{
				String msg = Msg.getMsg(getCtx(), "JP_CouldNotCreate") + " : " + Msg.getElement(getCtx(), "JP_CM_BPartner_Location_ID");
				if(Util.isEmpty(importBPartner.getI_ErrorMsg()))
				{
					importBPartner.setI_ErrorMsg(msg);
				}else{
					importBPartner.setI_ErrorMsg(importBPartner.getI_ErrorMsg()+ " : "+ msg);
				}

				importBPartner.setI_IsImported(true);
				importBPartner.setProcessed(false);
				importBPartner.saveEx(get_TrxName());
				return false;

			}else {
				importBPartner.setJP_CM_BPartner_Location_ID(JP_CM_BPartner_Location_ID);
			}
		}

		if(!Util.isEmpty(importBPartner.getI_ErrorMsg()))
		{
			importBPartner.setI_IsImported(true);
			importBPartner.setProcessed(false);
			importBPartner.saveEx(get_TrxName());
			return false;
		}


		StringBuilder msg = new StringBuilder(Msg.getMsg(getCtx(), "NewRecord"));
		importBPartner.setI_ErrorMsg(msg.toString());
		importBPartner.setI_IsImported(true);
		importBPartner.setProcessed(true);
		importBPartner.saveEx(get_TrxName());
		return true;
	}


	/**
	 * Update Business Partner
	 *
	 * @param importBPartner
	 * @param updateBPartner
	 * @throws Exception
	 */
	private boolean updateBPartner(X_I_BPartnerJP importBPartner, MCMBPartner updateBPartner) throws Exception
	{

		ModelValidationEngine.get().fireImportValidate(this, importBPartner, updateBPartner, ImportValidator.TIMING_BEFORE_IMPORT);

		//Update Business Partner
		MTable C_BPartner_Table = MTable.get(getCtx(), MBPartner.Table_ID, get_TrxName());
		MColumn[] C_BPartner_Columns = C_BPartner_Table.getColumns(true);

		MTable I_BPartnerJP_Table = MTable.get(getCtx(), X_I_BPartnerJP.Table_ID, get_TrxName());
		MColumn[] I_BPartnerJP_Columns = I_BPartnerJP_Table.getColumns(true);

		MColumn i_Column = null;
		for(int i = 0 ; i < C_BPartner_Columns.length; i++)
		{
			i_Column = C_BPartner_Columns[i];
			if(i_Column.isVirtualColumn() || i_Column.isKey() || i_Column.isUUIDColumn())
				continue;//i

			if(i_Column.getColumnName().equals("IsActive")
				|| i_Column.getColumnName().equals("AD_Client_ID")
				|| i_Column.getColumnName().equals("Value")
				|| i_Column.getColumnName().equals("Processing")
				|| i_Column.getColumnName().equals("Created")
				|| i_Column.getColumnName().equals("CreatedBy")
				|| i_Column.getColumnName().equals("Updated")
				|| i_Column.getColumnName().equals("UpdatedBy") )
				continue;//i

			MColumn j_Column = null;
			Object importValue = null;
			for(int j = 0 ; j < I_BPartnerJP_Columns.length; j++)
			{
				j_Column = I_BPartnerJP_Columns[j];

				if(i_Column.getColumnName().equals(j_Column.getColumnName()))
				{
					importValue = importBPartner.get_Value(j_Column.getColumnName());
					if(importValue == null )
					{
						break;//j

					}else if(importValue instanceof BigDecimal) {

						BigDecimal bigDecimal_Value = (BigDecimal)importValue;
						if(bigDecimal_Value.compareTo(Env.ZERO) == 0)
							break;

					}else if(j_Column.getAD_Reference_ID()==DisplayType.String) {

						String string_Value = (String)importValue;
						if(!Util.isEmpty(string_Value))
						{
							updateBPartner.set_ValueNoCheck(i_Column.getColumnName(), importValue);
						}

						break;

					}else if(j_Column.getColumnName().endsWith("_ID")) {

						Integer p_key = (Integer)importValue;
						if(p_key.intValue() <= 0)
							break;

					}

					if(importValue != null)
					{

						try {
							updateBPartner.set_ValueNoCheck(i_Column.getColumnName(), importValue);
						}catch (Exception e) {

							importBPartner.setI_ErrorMsg(Msg.getMsg(getCtx(), "Error") + " Column = " + i_Column.getColumnName() + " & " + "Value = " +importValue.toString() + " -> " + e.toString());
							importBPartner.setI_IsImported(false);
							importBPartner.setProcessed(false);
							importBPartner.saveEx(get_TrxName());
							return false;
						}

					}

					break;
				}
			}//for j

		}//for i

		updateBPartner.setIsActive(importBPartner.isI_IsActiveJP());
		if(importBPartner.getJP_CM_BPartner_ID() == 0)
			importBPartner.setJP_CM_BPartner_ID(updateBPartner.getJP_CM_BPartner_ID());

		ModelValidationEngine.get().fireImportValidate(this, importBPartner, updateBPartner, ImportValidator.TIMING_AFTER_IMPORT);

		try {
			updateBPartner.saveEx(get_TrxName());
		}catch (Exception e) {
			importBPartner.setI_ErrorMsg(Msg.getMsg(getCtx(),"SaveError") + Msg.getElement(getCtx(), "C_BPartner_ID") + " -> " +e.toString());
			importBPartner.setI_IsImported(false);
			importBPartner.setProcessed(false);
			importBPartner.saveEx(get_TrxName());
			return false;
		}

		//Business Partner Location
		if(!Util.isEmpty(importBPartner.getJP_BPartner_Location_Name()))
		{
			MCMBPartnerLocation bpLocation = getMCMBPartnerLocation(importBPartner.getJP_CM_BPartner_ID(), importBPartner.getJP_BPartner_Location_Name());
			if(bpLocation == null)
			{
				int C_BPartner_Location_ID = createBPartnerLocation(importBPartner);
				if(C_BPartner_Location_ID == 0)
				{
					String msg = Msg.getMsg(getCtx(), "JP_CouldNotCreate") + " : " + Msg.getElement(getCtx(), "C_BPartner_Location_ID");
					if(Util.isEmpty(importBPartner.getI_ErrorMsg()))
					{
						importBPartner.setI_ErrorMsg(msg);
					}else{
						importBPartner.setI_ErrorMsg(importBPartner.getI_ErrorMsg()+ " : "+ msg);
					}

					importBPartner.setI_IsImported(false);
					importBPartner.setProcessed(false);
					importBPartner.saveEx(get_TrxName());
					return false;

				}else {
					importBPartner.setC_BPartner_Location_ID(C_BPartner_Location_ID);
				}



			}else {
				updateBPartnerLocation(importBPartner,bpLocation);
			}
		}

		if(!Util.isEmpty(importBPartner.getI_ErrorMsg()))
		{
			importBPartner.setI_IsImported(false);
			importBPartner.setProcessed(false);
			importBPartner.saveEx(get_TrxName());
			commitEx();
			return false;
		}

		StringBuilder msg = new StringBuilder(Msg.getMsg(getCtx(), "Update"));
		importBPartner.setI_ErrorMsg(msg.toString());
		importBPartner.setI_IsImported(true);
		importBPartner.setProcessed(true);
		importBPartner.saveEx(get_TrxName());
		commitEx();

		return true;
	}


	/**
	 * Create Business Partner Location
	 *
	 * @param importBPartner
	 * @return
	 * @throws Exception
	 */
	private int createBPartnerLocation(X_I_BPartnerJP importBPartner) throws Exception
	{
		MCMBPartnerLocation newBPartnerLocation = new MCMBPartnerLocation(getCtx(), 0, get_TrxName());
		ModelValidationEngine.get().fireImportValidate(this, importBPartner, newBPartnerLocation, ImportValidator.TIMING_BEFORE_IMPORT);

		PO.copyValues(importBPartner, newBPartnerLocation);
		newBPartnerLocation.setAD_Org_ID(importBPartner.getAD_Org_ID());
		newBPartnerLocation.setJP_CM_BPartner_ID(importBPartner.getJP_CM_BPartner_ID());
		if(!Util.isEmpty(importBPartner.getJP_BPartner_Location_Name()))
			newBPartnerLocation.setName(importBPartner.getJP_BPartner_Location_Name());
		newBPartnerLocation.setIsActive(importBPartner.isI_IsActiveJP());

		//Location
		if(importBPartner.getC_Location_ID() > 0)
		{
			newBPartnerLocation.setC_Location_ID(importBPartner.getC_Location_ID());

		}else {

			int C_Location_ID = JPiereLocationUtil.searchLocationByLabel(getCtx(), importBPartner.getJP_Location_Label(), get_TrxName());
			if(C_Location_ID == 0)
			{
				C_Location_ID = JPiereLocationUtil.createLocation(
						getCtx()
						,0
						,importBPartner.getJP_Location_Label()
						,importBPartner.getComments()
						,importBPartner.getCountryCode()
						,importBPartner.getPostal()
						,importBPartner.getPostal_Add()
						,importBPartner.getJP_Region_Name()
						,importBPartner.getRegionName()
						,importBPartner.getJP_City_Name()
						,importBPartner.getCity()
						,importBPartner.getAddress1()
						,importBPartner.getAddress2()
						,importBPartner.getAddress3()
						,importBPartner.getAddress4()
						,importBPartner.getAddress5()
						,get_TrxName() );
			}
			newBPartnerLocation.setC_Location_ID(C_Location_ID);
			importBPartner.setC_Location_ID(C_Location_ID);
		}

		ModelValidationEngine.get().fireImportValidate(this, importBPartner, newBPartnerLocation, ImportValidator.TIMING_AFTER_IMPORT);

		newBPartnerLocation.saveEx(get_TrxName());
		importBPartner.setJP_CM_BPartner_Location_ID(newBPartnerLocation.getJP_CM_BPartner_Location_ID());

		return newBPartnerLocation.getJP_CM_BPartner_Location_ID();
	}

	/**
	 *
	 * Update Business Partner Location
	 *
	 * @param importBPartner
	 * @throws Exception
	 */
	private void updateBPartnerLocation(X_I_BPartnerJP importBPartner,MCMBPartnerLocation updateBPartnerLocation) throws Exception
	{
		ModelValidationEngine.get().fireImportValidate(this, importBPartner, updateBPartnerLocation, ImportValidator.TIMING_BEFORE_IMPORT);

		//Update Business Partner
		MTable C_BPartner_Location_Table = MTable.get(getCtx(), MCMBPartnerLocation.Table_ID, get_TrxName());
		MColumn[] C_BPartner_Location_Columns = C_BPartner_Location_Table.getColumns(true);

		MTable I_BPartnerJP_Table = MTable.get(getCtx(), X_I_BPartnerJP.Table_ID, get_TrxName());
		MColumn[] I_BPartnerJP_Columns = I_BPartnerJP_Table.getColumns(true);

		MColumn i_Column = null;
		for(int i = 0 ; i < C_BPartner_Location_Columns.length; i++)
		{
			i_Column = C_BPartner_Location_Columns[i];
			if(i_Column.isVirtualColumn() || i_Column.isKey() || i_Column.isUUIDColumn())
				continue;//i

			if(i_Column.getColumnName().equals("IsActive")
				|| i_Column.getColumnName().equals("AD_Client_ID")
				|| i_Column.getColumnName().equals("Value")
				|| i_Column.getColumnName().equals("Name")
				|| i_Column.getColumnName().equals("Processing")
				|| i_Column.getColumnName().equals("Created")
				|| i_Column.getColumnName().equals("CreatedBy")
				|| i_Column.getColumnName().equals("Updated")
				|| i_Column.getColumnName().equals("UpdatedBy") )
				continue;//i

			MColumn j_Column = null;
			Object importValue = null;
			for(int j = 0 ; j < I_BPartnerJP_Columns.length; j++)
			{
				j_Column = I_BPartnerJP_Columns[j];

				if(i_Column.getColumnName().equals(j_Column.getColumnName()))
				{
					importValue = importBPartner.get_Value(j_Column.getColumnName());

					if(importValue == null )
					{
						break;//j

					}else if(importValue instanceof BigDecimal) {

						BigDecimal bigDecimal_Value = (BigDecimal)importValue;
						if(bigDecimal_Value.compareTo(Env.ZERO) == 0)
							break;

					}else if(j_Column.getAD_Reference_ID()==DisplayType.String) {

						String string_Value = (String)importValue;
						if(!Util.isEmpty(string_Value))
						{
							updateBPartnerLocation.set_ValueNoCheck(i_Column.getColumnName(), importValue);
						}

						break;

					}else if(j_Column.getColumnName().endsWith("_ID")) {

						Integer p_key = (Integer)importValue;
						if(p_key.intValue() <= 0)
							break;
					}

					updateBPartnerLocation.set_ValueNoCheck(i_Column.getColumnName(), importValue);
					break;
				}
			}//for j

		}//for i

		updateBPartnerLocation.setIsActive(importBPartner.isI_IsActiveJP());
		ModelValidationEngine.get().fireImportValidate(this, importBPartner, updateBPartnerLocation, ImportValidator.TIMING_AFTER_IMPORT);

		updateBPartnerLocation.saveEx(get_TrxName());

	}

	/**
	 * Get MCMBPartnerLocation
	 *
	 * @param C_BPartner_ID
	 * @param JP_BPartner_Location_Name
	 * @return
	 * @throws Exception
	 */
	private MCMBPartnerLocation getMCMBPartnerLocation(int JP_CM_BPartner_ID, String JP_BPartner_Location_Name) throws Exception
	{

		if(JP_CM_BPartner_ID==0 || Util.isEmpty(JP_BPartner_Location_Name))
			return null;

		MCMBPartnerLocation[] bpLocations = MCMBPartnerLocation.getForBPartner(getCtx(), JP_CM_BPartner_ID, get_TrxName());
		for(int i = 0 ; i < bpLocations.length; i++)
		{
			if(bpLocations[i].getName().equals(JP_BPartner_Location_Name))
				return bpLocations[i];
		}

		return null;

	}//getMCMBPartnerLocation

}	//	Import Consolidated Business Partner
