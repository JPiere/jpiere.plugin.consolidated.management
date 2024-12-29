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
import java.text.SimpleDateFormat;
import java.util.TimeZone;
import java.util.logging.Level;

import org.adempiere.model.ImportValidator;
import org.adempiere.process.ImportProcess;
import org.compiere.model.MColumn;
import org.compiere.model.MTable;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.PO;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.compiere.util.Util;

import jpiere.plugin.consolidated.management.model.MCMProduct;
import jpiere.plugin.consolidated.management.model.X_I_ProductJP;

/**
 * JPIERE-0629 Consolidated Management - Import Consolidated Product
 *
 *  @author Hideaki Hagiwara
 *
 */
public class JPiereImportConsolidatedProduct extends SvrProcess  implements ImportProcess
{

	/**	Client to be imported to		*/
	private int				p_AD_Client_ID = 0;
	/**	Delete old Imported				*/
	private boolean			p_deleteOldImported = false;
	
	private String message = null;

	private long startTime = System.currentTimeMillis();
	
	@Override
	protected void prepare() 
	{
		p_AD_Client_ID = getAD_Client_ID();
		
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (name.equals("DeleteOldImported"))
				p_deleteOldImported = "Y".equals(para[i].getParameter());
			else
				log.log(Level.SEVERE, "Unknown Parameter: " + name);
		}
	}

	@Override
	protected String doIt() throws Exception 
	{
		if(p_AD_Client_ID > 0)
			return Msg.getMsg(getCtx(), "Error") + Msg.getMsg(getCtx(), "システムテナントでのみ実行できます");//TODO
		
		StringBuilder sql = null;
		int no = 0;
		String clientCheck = getWhereClause();

		/** Delete Old Imported */
		if (p_deleteOldImported)
		{
			sql = new StringBuilder ("DELETE FROM I_ProductJP ")
				.append("WHERE I_IsImported='Y' AND Processed='Y' ").append(clientCheck);
			try {
				no = DB.executeUpdate(sql.toString(), get_TrxName());
				if (log.isLoggable(Level.INFO)) log.info("Delete Old Imported =" + no);
			}catch (Exception e) {
				throw new Exception(Msg.getMsg(getCtx(), "Error") + sql );
			}
		}

		/** Reset Message */
		sql = new StringBuilder ("UPDATE I_ProductJP ")
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
		message = Msg.getMsg(getCtx(), "Matching") + " : " + Msg.getElement(getCtx(), "JP_CM_Product_ID");
		if(processUI != null)	processUI.statusUpdate(message);
		if(reverseLookupJP_CM_Product_ID())
			commitEx();
		else
			return message;
		
		message = Msg.getMsg(getCtx(), "Matching") + " : " + Msg.getElement(getCtx(), "JP_CM_Product_Category_ID");
		if(processUI != null)	processUI.statusUpdate(message);
		if(reverseLookupJP_CM_ProductCategory_ID())
			commitEx();
		else
			return message;
		
		message = Msg.getMsg(getCtx(), "Matching") + " : " + Msg.getElement(getCtx(), "C_UOM_ID");
		if(processUI != null)	processUI.statusUpdate(message);
		if(reverseLookupC_UOM_ID())
			commitEx();
		else
			return message;
		
		message = Msg.getMsg(getCtx(), "Matching") + " : " + Msg.getElement(getCtx(), "JP_VendorUOM_ID");
		if(processUI != null)	processUI.statusUpdate(message);
		if(reverseLookupJP_VendorUOM_ID())
			commitEx();
		else
			return message;
		
		/** Register & Update Product */
		String msg = Msg.getMsg(getCtx(), "Register") +" & "+ Msg.getMsg(getCtx(), "Update")  + " " + Msg.getElement(getCtx(), "M_Product_ID");
		if (processUI != null)	processUI.statusUpdate(msg);
		
		sql = new StringBuilder ("SELECT * FROM I_ProductJP WHERE (I_IsImported='N' OR Processed='N') ")
				.append(clientCheck).append(" ORDER BY Value ");
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
			rs = pstmt.executeQuery();
			String lastValue = "";
			MCMProduct product = null;
			boolean isNew = true;

			while (rs.next())
			{
				X_I_ProductJP imp = new X_I_ProductJP (getCtx (), rs, get_TrxName());
				if(imp.getJP_CM_Product_ID() != 0)
				{
					product = new MCMProduct(getCtx(), imp.getJP_CM_Product_ID(), get_TrxName());
					if(Util.isEmpty(product.getValue()))
					{
						isNew =true;
						lastValue = imp.getValue();

					}else {

						isNew =false;
						lastValue = product.getValue();
					}

				}else{

					if(lastValue.equals(imp.getValue()))
					{
						isNew = false;

					}else {

						isNew = true;
						lastValue = imp.getValue();

					}
				}
				
				if(isNew)
				{
					product = new MCMProduct(getCtx(), 0, get_TrxName());
					if(createNewProduct(imp,product))
					{
						successNewNum++;
						imp.setI_ErrorMsg(Msg.getMsg(getCtx(), "NewRecord"));
						imp.setI_IsImported(true);
						imp.setProcessed(true);
						imp.saveEx(get_TrxName());

					}else {

						failureNewNum++;
						rollback();
						imp.setI_ErrorMsg(message);
						imp.setI_IsImported(false);
						imp.setProcessed(false);
						imp.saveEx(get_TrxName());
					}
					
				}else {//Update
					
					if(updateProduct(imp,product))
					{
						successUpdateNum++;
						imp.setI_ErrorMsg(Msg.getMsg(getCtx(), "Update"));
						imp.setI_IsImported(true);
						imp.setProcessed(true);
						imp.saveEx(get_TrxName());

					}else {

						failureUpdateNum++;
						rollback();
						imp.setI_ErrorMsg(message);
						imp.setI_IsImported(false);
						imp.setProcessed(false);
						imp.saveEx(get_TrxName());
					}
				}
				
				commitEx();

				recordsNum++;
				if (processUI != null)
				{
					processUI.statusUpdate(
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
	}
	
	@Override
	public String getImportTableName() {
		return "I_ProductJP";
	}


	@Override
	public String getWhereClause() {
		StringBuilder msgreturn = new StringBuilder(" AND AD_Client_ID=").append(p_AD_Client_ID);
		return msgreturn.toString();
	}

	/**
	 * Reverse Look up Consolidated Product From Value and UPC.
	 * @throws Exception
	 *
	 */
	private boolean reverseLookupJP_CM_Product_ID() throws Exception
	{
		int no = 0;

		//Reverse lookup JP_CM_Product_ID From Value
		StringBuilder sql = new StringBuilder ("UPDATE I_ProductJP i ")
				.append("SET JP_CM_Product_ID=(SELECT JP_CM_Product_ID FROM JP_CM_Product p")
				.append(" WHERE i.Value=p.Value AND p.AD_Client_ID=i.AD_Client_ID) ")
				.append(" WHERE i.JP_CM_Product_ID IS NULL AND i.Value IS NOT NULL")
				.append(" AND i.I_IsImported='N'").append(getWhereClause());
		try {
			no = DB.executeUpdateEx(sql.toString(), get_TrxName());
		}catch(Exception e) {
			throw new Exception(Msg.getMsg(getCtx(), "Error") + message +" : " + e.toString() +" : " + sql );
		}

		//Reverse lookup JP_CM_Product_ID From UPC
		sql = new StringBuilder ("UPDATE I_ProductJP i ")
			.append("SET JP_CM_Product_ID=(SELECT JP_CM_Product_ID FROM JP_CM_Product p")
			.append(" WHERE i.UPC=p.UPC AND i.AD_Client_ID=p.AD_Client_ID) ")
			.append(" WHERE i.UPC IS NOT NULL AND i.JP_CM_Product_ID IS NULL")
			.append(" AND i.I_IsImported='N'").append(getWhereClause());
		try {
			no = DB.executeUpdateEx(sql.toString(), get_TrxName());
		}catch(Exception e) {
			throw new Exception(Msg.getMsg(getCtx(), "Error") + message +" : " + e.toString() +" : " + sql );
		}

		//Error : Search Key is null
		message = Msg.getMsg(getCtx(), "Error") + Msg.getMsg(getCtx(), "JP_Null")+Msg.getElement(getCtx(), "Value");
		sql = new StringBuilder ("UPDATE I_ProductJP ")
			.append("SET I_ErrorMsg='"+ message + "'")
			.append(" WHERE Value IS NULL AND JP_CM_Product_ID IS NULL ")
			.append(" AND I_IsImported<>'Y'").append(getWhereClause());
		try {
			no = DB.executeUpdateEx(sql.toString(), get_TrxName());
		}catch(Exception e) {
			throw new Exception(message  +" : " +  e.toString() +" : " + sql );
		}

		if(no > 0)
		{
			return false;
		}

		return true;

	}//reverseLookupJP_CM_Product_ID

	/**
	 * Reverse Look up Consolidated Product Category From JP_CM_ProductCategory_Value
	 *
	 * @throws Exception
	 */
	private boolean reverseLookupJP_CM_ProductCategory_ID() throws Exception
	{
		int no = 0;

		StringBuilder sql = new StringBuilder ("UPDATE I_ProductJP i ")
				.append("SET JP_CM_ProductCategory_ID=(SELECT JP_CM_ProductCategory_ID FROM JP_CM_ProductCategory p")
				.append(" WHERE i.JP_CM_ProductCategory_Value=p.Value AND p.AD_Client_ID=i.AD_Client_ID) ")
				.append(" WHERE i.JP_CM_ProductCategory_Value IS NOT NULL")
				.append(" AND i.I_IsImported='N'").append(getWhereClause());
		try {
			no = DB.executeUpdateEx(sql.toString(), get_TrxName());
		}catch(Exception e) {
			throw new Exception(Msg.getMsg(getCtx(), "Error") + message  +" : " +  e.toString() +" : " + sql );
		}

		//Invalid Consolidated Product Category_Value
		message = Msg.getMsg(getCtx(), "Error") + Msg.getMsg(getCtx(), "Invalid")+Msg.getElement(getCtx(), "JP_CM_ProductCategory_Value");
		sql = new StringBuilder ("UPDATE I_ProductJP ")
			.append("SET I_ErrorMsg='"+ message + "'")
			.append(" WHERE JP_CM_ProductCategory_Value IS NOT NULL AND JP_CM_ProductCategory_ID IS NULL ")
			.append(" AND I_IsImported<>'Y'").append(getWhereClause());
		try {
			no = DB.executeUpdateEx(sql.toString(), get_TrxName());
		}catch(Exception e) {
			throw new Exception(message  +" : " +  e.toString() +" : " + sql);
		}

		if(no > 0)
		{
			return false;
		}

		return true;

	} //reverseLookupJP_CM_ProductCategory_ID
	
	
	/**
	 * Reverse Look up C_UOM_ID From X12DE355
	 *
	 * @throws Exception
	 */
	private boolean reverseLookupC_UOM_ID() throws Exception
	{
		int no = 0;

		StringBuilder sql = new StringBuilder ("UPDATE I_ProductJP i ")
				.append("SET C_UOM_ID=(SELECT C_UOM_ID FROM C_UOM p")
				.append(" WHERE i.X12DE355=p.X12DE355 AND (i.AD_Client_ID=p.AD_Client_ID OR p.AD_Client_ID = 0) ) ")
				.append("WHERE X12DE355 IS NOT NULL")
				.append(" AND I_IsImported='N'").append(getWhereClause());
		try {
			no = DB.executeUpdateEx(sql.toString(), get_TrxName());
		}catch(Exception e) {
			throw new Exception(Msg.getMsg(getCtx(), "Error") + message +" : " +  e.toString() +" : " + sql );
		}

		//Invalid X12DE355
		message = Msg.getMsg(getCtx(), "Invalid")+Msg.getElement(getCtx(), "X12DE355");
		sql = new StringBuilder ("UPDATE I_ProductJP ")
			.append("SET I_ErrorMsg='"+ message + "'")
			.append(" WHERE X12DE355 IS NOT NULL AND C_UOM_ID IS NULL ")
			.append(" AND I_IsImported<>'Y'").append(getWhereClause());
		try {
			no = DB.executeUpdateEx(sql.toString(), get_TrxName());
		}catch(Exception e) {
			throw new Exception(message +" : " +  e.toString() +" : " + sql);
		}

		if(no > 0)
		{
			return false;
		}

		return true;

	}//reverseLookupC_UOM_ID
	
	/**
	 * Reverse Look up JP_VendorUOM_ID From JP_VendorX12DE355
	 *
	 * @throws Exception
	 */
	private boolean reverseLookupJP_VendorUOM_ID() throws Exception
	{
		int no = 0;

		StringBuilder sql = new StringBuilder ("UPDATE I_ProductJP i ")
				.append("SET JP_VendorUOM_ID=(SELECT C_UOM_ID FROM C_UOM p")
				.append(" WHERE i.JP_VendorX12DE355=p.X12DE355 AND (i.AD_Client_ID=p.AD_Client_ID OR p.AD_Client_ID = 0) ) ")
				.append("WHERE JP_VendorX12DE355 IS NOT NULL")
				.append(" AND I_IsImported='N'").append(getWhereClause());
		try {
			no = DB.executeUpdateEx(sql.toString(), get_TrxName());
		}catch(Exception e) {
			throw new Exception(Msg.getMsg(getCtx(), "Error") + message +" : " +  e.toString() +" : " + sql );
		}

		//Invalid X12DE355
		message = Msg.getMsg(getCtx(), "Invalid")+Msg.getElement(getCtx(), "JP_VendorX12DE355");
		sql = new StringBuilder ("UPDATE I_ProductJP ")
			.append("SET I_ErrorMsg='"+ message + "'")
			.append(" WHERE JP_VendorX12DE355 IS NOT NULL AND JP_VendorUOM_ID IS NULL ")
			.append(" AND I_IsImported<>'Y'").append(getWhereClause());
		try {
			no = DB.executeUpdateEx(sql.toString(), get_TrxName());
		}catch(Exception e) {
			throw new Exception(message +" : " +  e.toString() +" : " + sql);
		}

		if(no > 0)
		{
			return false;
		}

		return true;

	}//reverseLookupJP_VendorUOM_ID
	
	/**
	 * Create New Consolidated Product
	 *
	 * @param importProduct
	 * @throws Exception
	 */
	private boolean createNewProduct(X_I_ProductJP importProduct, MCMProduct newProduct) throws Exception
	{
		newProduct.setAD_Org_ID(importProduct.getAD_Org_ID());

		//Mandatory Check!
		if(Util.isEmpty(importProduct.getValue()))
		{
			Object[] objs = new Object[]{Msg.getElement(Env.getCtx(), "Value")};
			message = Msg.getMsg(getCtx(), "Error") + Msg.getMsg(Env.getCtx(),"JP_Mandatory",objs);
			return false;
		}

		if(Util.isEmpty(importProduct.getName()))
		{
			Object[] objs = new Object[]{Msg.getElement(Env.getCtx(), "Name")};
			message = Msg.getMsg(getCtx(), "Error") + Msg.getMsg(Env.getCtx(),"JP_Mandatory",objs);
			return false;
		}

		if(importProduct.getJP_CM_ProductCategory_ID() == 0)
		{
			Object[] objs = new Object[]{Msg.getElement(Env.getCtx(), "JP_CM_ProductCategory_ID")};
			message = Msg.getMsg(getCtx(), "Error") + Msg.getMsg(Env.getCtx(),"JP_Mandatory",objs);
			return false;
		}

		if(!MCMProduct.PRODUCTTYPE_Resource.equals(importProduct.getProductType()))
		{
			if(Util.isEmpty(importProduct.getProductCategory_Value()))
			{
				Object[] objs = new Object[]{Msg.getElement(Env.getCtx(), "ProductCategory_Value")};
				message = Msg.getMsg(getCtx(), "Error") + Msg.getMsg(Env.getCtx(),"JP_Mandatory",objs);
				return false;
			}
	
			if(Util.isEmpty(importProduct.getJP_TaxCategory_Name()))
			{
				Object[] objs = new Object[]{Msg.getElement(Env.getCtx(), "JP_TaxCategory_Name")};
				message = Msg.getMsg(getCtx(), "Error") + Msg.getMsg(Env.getCtx(),"JP_Mandatory",objs);
				return false;
			}
		}
		
		if(importProduct.getC_UOM_ID() == 0)
		{
			Object[] objs = new Object[]{Msg.getElement(Env.getCtx(), "C_UOM_ID")};
			message = Msg.getMsg(getCtx(), "Error") + Msg.getMsg(Env.getCtx(),"JP_Mandatory",objs);
			return false;
		}

		if(Util.isEmpty(importProduct.getProductType()))
		{
			Object[] objs = new Object[]{Msg.getElement(Env.getCtx(), "ProductType")};
			message = Msg.getMsg(getCtx(), "Error") + Msg.getMsg(Env.getCtx(),"JP_Mandatory",objs);
			return false;
		}

		ModelValidationEngine.get().fireImportValidate(this, importProduct, newProduct, ImportValidator.TIMING_BEFORE_IMPORT);

		//Copy
		PO.copyValues(importProduct, newProduct);
		if(!Util.isEmpty(importProduct.getProductCategory_Value()))
			newProduct.setJP_Product_Category_Value(importProduct.getProductCategory_Value());
		if(!Util.isEmpty(importProduct.getBPartner_Value()))
			newProduct.setBPartnerValue(importProduct.getBPartner_Value());
		newProduct.setIsActive(importProduct.isI_IsActiveJP());

		ModelValidationEngine.get().fireImportValidate(this, importProduct, newProduct, ImportValidator.TIMING_AFTER_IMPORT);

		try {
			newProduct.saveEx(get_TrxName());
		}catch (Exception e) {
			message = Msg.getMsg(getCtx(),"SaveIgnored") + Msg.getElement(getCtx(), "JP_CM_Product_ID");
			return false;
		}

		importProduct.setJP_CM_Product_ID(newProduct.getJP_CM_Product_ID());

		return true;
	}
	
	/**
	 *
	 * Update Consolidated Product
	 *
	 * @param importProduct
	 * @throws Exception
	 */
	private boolean updateProduct(X_I_ProductJP importProduct, MCMProduct updateProduct) throws Exception
	{
		ModelValidationEngine.get().fireImportValidate(this, importProduct, updateProduct, ImportValidator.TIMING_BEFORE_IMPORT);

		//Update Product
		MTable JP_CM_Product_Table = MTable.get(getCtx(), MCMProduct.Table_ID, get_TrxName());
		MColumn[] JP_CM_Product_Columns = JP_CM_Product_Table.getColumns(true);

		MTable I_ProductJP_Table = MTable.get(getCtx(), X_I_ProductJP.Table_ID, get_TrxName());
		MColumn[] I_ProductJP_Columns = I_ProductJP_Table.getColumns(true);

		MColumn i_Column = null;
		for(int i = 0 ; i < JP_CM_Product_Columns.length; i++)
		{
			i_Column = JP_CM_Product_Columns[i];
			if(i_Column.isVirtualColumn() || i_Column.isKey() || i_Column.isUUIDColumn())
				continue;//i

			if(i_Column.getColumnName().equals("IsActive")
				|| i_Column.getColumnName().equals("IsStocked")
				|| i_Column.getColumnName().equals("ProductType")
				|| i_Column.getColumnName().equals("C_UOM_ID")
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
			for(int j = 0 ; j < I_ProductJP_Columns.length; j++)
			{
				j_Column = I_ProductJP_Columns[j];

				if(i_Column.getColumnName().equals(j_Column.getColumnName()))
				{
					importValue = importProduct.get_Value(j_Column.getColumnName());
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
							updateProduct.set_ValueNoCheck(i_Column.getColumnName(), importValue);
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
							updateProduct.set_ValueNoCheck(i_Column.getColumnName(), importValue);
						}catch (Exception e) {
							message = Msg.getMsg(getCtx(), "Error") + " Column = " + i_Column.getColumnName() + " & " + "Value = " +importValue.toString() + " -> " + e.toString();
							return false;
						}
					}

					break;
				}
			}//for j

		}//for i

		if(!Util.isEmpty(importProduct.getProductCategory_Value()))
			updateProduct.setJP_Product_Category_Value(importProduct.getProductCategory_Value());
		
		if(!Util.isEmpty(importProduct.getBPartner_Value()))
			updateProduct.setBPartnerValue(importProduct.getBPartner_Value());
		
		if(!importProduct.isI_IsActiveJP())
			updateProduct.setIsActive(importProduct.isI_IsActiveJP());

		if(importProduct.getJP_CM_Product_ID() == 0)
			importProduct.setJP_CM_Product_ID(updateProduct.getJP_CM_Product_ID());

		ModelValidationEngine.get().fireImportValidate(this, importProduct, updateProduct, ImportValidator.TIMING_AFTER_IMPORT);

		try {
			updateProduct.saveEx(get_TrxName());
		}catch (Exception e) {
			message = Msg.getMsg(getCtx(),"SaveError") + Msg.getElement(getCtx(), "M_Product_ID")+" :  " + e.toString();
			return false;
		}

		return true;

	}
}
