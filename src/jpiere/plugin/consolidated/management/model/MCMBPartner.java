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
package jpiere.plugin.consolidated.management.model;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.compiere.model.MBPGroup;
import org.compiere.model.MBPartner;
import org.compiere.model.MClient;
import org.compiere.model.MDiscountSchema;
import org.compiere.model.MDunning;
import org.compiere.model.MInvoiceSchedule;
import org.compiere.model.MPaymentTerm;
import org.compiere.model.MPriceList;
import org.compiere.model.PO;
import org.compiere.model.Query;
import org.compiere.print.MPrintFormat;
import org.compiere.util.CCache;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.compiere.util.Util;

/**
 * JPIERE-0636 Consolidated Management - Consolidated Business Partner Process
 *
 *  @author Hideaki Hagiwara
 *
 */
public class MCMBPartner extends X_JP_CM_BPartner {

	private static final long serialVersionUID = 4078450850155974452L;

	public MCMBPartner(Properties ctx, int JP_CM_BPartner_ID, String trxName) 
	{
		super(ctx, JP_CM_BPartner_ID, trxName);
	}

	public MCMBPartner(Properties ctx, int JP_CM_BPartner_ID, String trxName, String... virtualColumns) 
	{
		super(ctx, JP_CM_BPartner_ID, trxName, virtualColumns);
	}

	public MCMBPartner(Properties ctx, String JP_CM_BPartner_UU, String trxName) 
	{
		super(ctx, JP_CM_BPartner_UU, trxName);
	}

	public MCMBPartner(Properties ctx, String JP_CM_BPartner_UU, String trxName, String... virtualColumns) 
	{
		super(ctx, JP_CM_BPartner_UU, trxName, virtualColumns);
	}

	public MCMBPartner(Properties ctx, ResultSet rs, String trxName) 
	{
		super(ctx, rs, trxName);
	}
	
	@Override
	protected boolean beforeSave(boolean newRecord) 
	{
		return true;
	}
	
	public MCMBPartnerLocation[] getMCMBPartnerLocations()
	{
		StringBuilder whereClauseFinal = new StringBuilder(MCMBPartnerLocation.COLUMNNAME_JP_CM_BPartner_ID + "=? ");
		String orderClause = MCMBPartnerLocation.COLUMNNAME_JP_CM_BPartner_Location_ID;
		List<MCMBPartnerLocation> list = new Query(getCtx(), MCMBPartnerLocation.Table_Name, whereClauseFinal.toString(), get_TrxName())
										.setParameters(getJP_CM_BPartner_ID())
										.setOrderBy(orderClause)
										.list();
		return list.toArray(new MCMBPartnerLocation[list.size()]);		
	}

	static public MBPartner getMBPartner(int AD_Client_ID, String value, String trxName)
	{
		StringBuilder whereClause = new StringBuilder("AD_Client_ID=? AND Value=?");
		MBPartner po = new Query(Env.getCtx(), MBPartner.Table_Name, whereClause.toString(), trxName)
							.setParameters(AD_Client_ID, value)
							.firstOnly();
		return po;
	}
	
	private static CCache<Integer,Map<String,MBPGroup>>c_MBPGroup = new CCache<Integer,Map<String,MBPGroup>>(Table_Name, 60);
	
	static public MBPGroup getMBPGroup(int AD_Client_ID, String value, String trxName)
	{	
		MBPGroup po = null;
		Map<String,MBPGroup> cachedMap = c_MBPGroup.get(AD_Client_ID);
		if (cachedMap == null)
		{
			cachedMap = new HashMap<String,MBPGroup>();
			c_MBPGroup.put(AD_Client_ID, cachedMap);	
		}
		
		po = cachedMap.get(value);
		if(po == null)
		{
			StringBuilder whereClause = new StringBuilder("AD_Client_ID=? AND Value=?");
			po = new Query(Env.getCtx(), MBPGroup.Table_Name, whereClause.toString(), trxName)
								.setParameters(AD_Client_ID, value)
								.firstOnly();
			cachedMap.put(value, po);
		}
		return po;
	}
	
	private static CCache<Integer,Map<String,MInvoiceSchedule>>c_MInvoiceSchedule = new CCache<Integer,Map<String,MInvoiceSchedule>>(Table_Name, 60);
	
	static public MInvoiceSchedule getMInvoiceSchedule(int AD_Client_ID, String name, String trxName)
	{	
		MInvoiceSchedule po = null;
		Map<String,MInvoiceSchedule> cachedMap = c_MInvoiceSchedule.get(AD_Client_ID);
		if (cachedMap == null)
		{
			cachedMap = new HashMap<String,MInvoiceSchedule>();
			c_MInvoiceSchedule.put(AD_Client_ID, cachedMap);	
		}
		
		po = cachedMap.get(name);
		if(po == null)
		{
			StringBuilder whereClause = new StringBuilder("AD_Client_ID=? AND Name=?");
			po = new Query(Env.getCtx(), MInvoiceSchedule.Table_Name, whereClause.toString(), trxName)
								.setParameters(AD_Client_ID, name)
								.firstOnly();
			cachedMap.put(name, po);
		}
		return po;
	}
	
	private static CCache<Integer,Map<String,MPaymentTerm>>c_MPaymentTerm = new CCache<Integer,Map<String,MPaymentTerm>>(Table_Name, 60);
	
	static public MPaymentTerm getMPaymentTerm(int AD_Client_ID, String value, String trxName)
	{	
		MPaymentTerm po = null;
		Map<String,MPaymentTerm> cachedMap = c_MPaymentTerm.get(AD_Client_ID);
		if (cachedMap == null)
		{
			cachedMap = new HashMap<String,MPaymentTerm>();
			c_MPaymentTerm.put(AD_Client_ID, cachedMap);	
		}
		
		po = cachedMap.get(value);
		if(po == null)
		{
			StringBuilder whereClause = new StringBuilder("AD_Client_ID=? AND Value=?");
			po = new Query(Env.getCtx(), MPaymentTerm.Table_Name, whereClause.toString(), trxName)
								.setParameters(AD_Client_ID, value)
								.firstOnly();
			cachedMap.put(value, po);
		}
		return po;
	}
	
	private static CCache<Integer,Map<String,PO>>c_MBillSchema = new CCache<Integer,Map<String,PO>>(Table_Name, 60);
	
	static public PO getMBillSchema(int AD_Client_ID, String value, String trxName)
	{	
		PO po = null;
		Map<String,PO> cachedMap = c_MBillSchema.get(AD_Client_ID);
		if (cachedMap == null)
		{
			cachedMap = new HashMap<String,PO>();
			c_MBillSchema.put(AD_Client_ID, cachedMap);	
		}
		
		po = cachedMap.get(value);
		if(po == null)
		{
			StringBuilder whereClause = new StringBuilder("AD_Client_ID=? AND Value=?");
			po = new Query(Env.getCtx(), "JP_BillSchema", whereClause.toString(), trxName)
								.setParameters(AD_Client_ID, value)
								.firstOnly();
			cachedMap.put(value, po);
		}
		return po;
	}
	
	private static CCache<Integer,Map<String,MPriceList>>c_MPriceList = new CCache<Integer,Map<String,MPriceList>>(Table_Name, 60);
	
	static public MPriceList getMPriceList(int AD_Client_ID, String name, String trxName)
	{	
		MPriceList po = null;
		Map<String,MPriceList> cachedMap = c_MPriceList.get(AD_Client_ID);
		if (cachedMap == null)
		{
			cachedMap = new HashMap<String,MPriceList>();
			c_MPriceList.put(AD_Client_ID, cachedMap);	
		}
		
		po = cachedMap.get(name);
		if(po == null)
		{
			StringBuilder whereClause = new StringBuilder("AD_Client_ID=? AND Name=?");
			po = new Query(Env.getCtx(), MPriceList.Table_Name, whereClause.toString(), trxName)
								.setParameters(AD_Client_ID, name)
								.firstOnly();
			cachedMap.put(name, po);
		}
		return po;
	}
	
	private static CCache<Integer,Map<String,MDiscountSchema>>c_MDiscountSchema = new CCache<Integer,Map<String,MDiscountSchema>>(Table_Name, 60);
	
	static public MDiscountSchema getMDiscountSchema(int AD_Client_ID, String name, String trxName)
	{	
		MDiscountSchema po = null;
		Map<String,MDiscountSchema> cachedMap = c_MDiscountSchema.get(AD_Client_ID);
		if (cachedMap == null)
		{
			cachedMap = new HashMap<String,MDiscountSchema>();
			c_MDiscountSchema.put(AD_Client_ID, cachedMap);	
		}
		
		po = cachedMap.get(name);
		if(po == null)
		{
			StringBuilder whereClause = new StringBuilder("AD_Client_ID=? AND Name=?");
			po = new Query(Env.getCtx(), MDiscountSchema.Table_Name, whereClause.toString(), trxName)
								.setParameters(AD_Client_ID, name)
								.firstOnly();
			cachedMap.put(name, po);
		}
		return po;
	}
	
	private static CCache<Integer,Map<String,MDunning>>c_MDunning = new CCache<Integer,Map<String,MDunning>>(Table_Name, 60);
	
	static public MDunning getMDunning(int AD_Client_ID, String name, String trxName)
	{	
		MDunning po = null;
		Map<String,MDunning> cachedMap = c_MDunning.get(AD_Client_ID);
		if (cachedMap == null)
		{
			cachedMap = new HashMap<String,MDunning>();
			c_MDunning.put(AD_Client_ID, cachedMap);	
		}
		
		po = cachedMap.get(name);
		if(po == null)
		{
			StringBuilder whereClause = new StringBuilder("AD_Client_ID=? AND Name=?");
			po = new Query(Env.getCtx(), MDunning.Table_Name, whereClause.toString(), trxName)
								.setParameters(AD_Client_ID, name)
								.firstOnly();
			cachedMap.put(name, po);
		}
		return po;
	}
	
	private static CCache<Integer,Map<String,PO>>c_M1099Box = new CCache<Integer,Map<String,PO>>(Table_Name, 60);
	
	static public PO getM1099Box(int AD_Client_ID, String value, String trxName)
	{	
		PO po = null;
		Map<String,PO> cachedMap = c_M1099Box.get(AD_Client_ID);
		if (cachedMap == null)
		{
			cachedMap = new HashMap<String,PO>();
			c_M1099Box.put(AD_Client_ID, cachedMap);	
		}
		
		po = cachedMap.get(value);
		if(po == null)
		{
			StringBuilder whereClause = new StringBuilder("AD_Client_ID=? AND Value=?");
			po = new Query(Env.getCtx(), "C_1099Box", whereClause.toString(), trxName)
								.setParameters(AD_Client_ID, value)
								.firstOnly();
			cachedMap.put(value, po);
		}
		return po;
	}
	
	private static CCache<Integer,Map<String,PO>>c_MGreeting = new CCache<Integer,Map<String,PO>>(Table_Name, 60);
	
	static public PO getMGreeting(int AD_Client_ID, String name, String trxName)
	{	
		PO po = null;
		Map<String,PO> cachedMap = c_MGreeting.get(AD_Client_ID);
		if (cachedMap == null)
		{
			cachedMap = new HashMap<String,PO>();
			c_MGreeting.put(AD_Client_ID, cachedMap);	
		}
		
		po = cachedMap.get(name);
		if(po == null)
		{
			StringBuilder whereClause = new StringBuilder("AD_Client_ID=? AND Value=?");
			po = new Query(Env.getCtx(), "C_Greeting", whereClause.toString(), trxName)
								.setParameters(AD_Client_ID, name)
								.firstOnly();
			cachedMap.put(name, po);
		}
		return po;
	}
	
	private static CCache<Integer,Map<String,MPrintFormat>>c_MPrintFormat = new CCache<Integer,Map<String,MPrintFormat>>(Table_Name, 60);
	
	static public MPrintFormat getMPrintFormat(int AD_Client_ID, String name, String trxName)
	{	
		MPrintFormat po = null;
		Map<String,MPrintFormat> cachedMap = c_MPrintFormat.get(AD_Client_ID);
		if (cachedMap == null)
		{
			cachedMap = new HashMap<String,MPrintFormat>();
			c_MPrintFormat.put(AD_Client_ID, cachedMap);	
		}
		
		po = cachedMap.get(name);
		if(po == null)
		{
			StringBuilder whereClause = new StringBuilder("AD_Client_ID=? AND Name=?");
			po = new Query(Env.getCtx(), MPrintFormat.Table_Name, whereClause.toString(), trxName)
								.setParameters(AD_Client_ID, name)
								.firstOnly();
			cachedMap.put(name, po);
		}
		return po;
	}
	
	static public MBPartner createMBPartner(Properties ctx, MClient m_Client, MCMBPartner cm_BPartner, String trxName) throws Exception
	{
		if(m_Client == null || cm_BPartner == null)
			return null;
		
		//Copy ctx and Override #AD_Client_ID are for cross tenant check at WF.
		Properties cloneCtx = (Properties)ctx.clone();
		cloneCtx.setProperty("#AD_Client_ID", String.valueOf(m_Client.getAD_Client_ID()));
		
		String msg = null;
		MBPartner m_BPartner = new MBPartner(cloneCtx, 0, trxName);
		PO.copyValues(cm_BPartner, m_BPartner);
		m_BPartner.set_ValueNoCheck("AD_Client_ID", m_Client.getAD_Client_ID());
		m_BPartner.setAD_Org_ID(0);
		m_BPartner.setValue(cm_BPartner.getValue());
		m_BPartner.setName(cm_BPartner.getName());
		m_BPartner.setName2(cm_BPartner.getName2());
		m_BPartner.setDescription(cm_BPartner.getDescription());
		m_BPartner.setIsCustomer(cm_BPartner.isCustomer());
		m_BPartner.setIsVendor(cm_BPartner.isVendor());
		m_BPartner.setIsEmployee(cm_BPartner.isEmployee());
		m_BPartner.setIsProspect(cm_BPartner.isProspect());
		m_BPartner.setIsSalesRep(cm_BPartner.isSalesRep());
		m_BPartner.setIsSummary(cm_BPartner.isSummary());
		m_BPartner.setReferenceNo(cm_BPartner.getReferenceNo());
		m_BPartner.setRating(cm_BPartner.getRating());
		m_BPartner.setSOCreditStatus(cm_BPartner.getSOCreditStatus());
		m_BPartner.setTaxID(cm_BPartner.getTaxID());
		m_BPartner.setIsTaxExempt(cm_BPartner.isTaxExempt());
		m_BPartner.setIsPOTaxExempt(cm_BPartner.isPOTaxExempt());
		m_BPartner.setURL(cm_BPartner.getURL());
		
		if(m_BPartner.get_ColumnIndex("JP_SOTaxRounding") > -1 && cm_BPartner.getJP_SOTaxRounding() != null)
			m_BPartner.set_ValueNoCheck("JP_SOTaxRounding", cm_BPartner.getJP_SOTaxRounding());

		if(m_BPartner.get_ColumnIndex("JP_POTaxRounding") > -1 && cm_BPartner.getJP_POTaxRounding() != null)
			m_BPartner.set_ValueNoCheck("JP_POTaxRounding", cm_BPartner.getJP_POTaxRounding());
		
		if(m_BPartner.get_ColumnIndex("JP_Corporation_ID") > -1 && cm_BPartner.getJP_Corporation_ID() != 0)
			m_BPartner.set_ValueNoCheck("JP_Corporation_ID", cm_BPartner.getJP_Corporation_ID());
		
		//Set C_BP_Group_ID
		MBPGroup m_BPGroup = getMBPGroup(m_Client.getAD_Client_ID(), cm_BPartner.getGroupValue(), trxName);
		if(m_BPGroup == null)
		{
			//C_BP_Group_ID is Mandatory
			msg = Msg.getMsg(ctx, "invalid")
						+ " : " + Msg.getElement(ctx, COLUMNNAME_GroupValue)
						+ " = " + cm_BPartner.getGroupValue();
			throw new Exception(msg);
			
		}else {
			m_BPartner.setC_BP_Group_ID(m_BPGroup.getC_BP_Group_ID());
		}
		
		/**Customer Information*/
		m_BPartner.setInvoiceRule(cm_BPartner.getInvoiceRule());
		m_BPartner.setPaymentRule(cm_BPartner.getPaymentRule());
		m_BPartner.setDeliveryRule(cm_BPartner.getDeliveryRule());
		m_BPartner.setDeliveryViaRule(cm_BPartner.getDeliveryViaRule());
		m_BPartner.setFlatDiscount(cm_BPartner.getFlatDiscount());
		m_BPartner.setDunningGrace(cm_BPartner.getDunningGrace());
		m_BPartner.setShelfLifeMinPct(cm_BPartner.getShelfLifeMinPct());
		//Set C_InvoiceSchedule_ID
		if(!Util.isEmpty(cm_BPartner.getJP_InvoiceSchedule_Name()))
		{
			MInvoiceSchedule m_InvoiceSchedule = getMInvoiceSchedule(m_Client.getAD_Client_ID(), cm_BPartner.getJP_InvoiceSchedule_Name(), trxName);
			if(m_InvoiceSchedule == null)
			{
				if(!cm_BPartner.isIgnore_NMMaster_NotFoundJP())
				{
					msg = Msg.getMsg(ctx, "invalid")
								+ " : " + Msg.getElement(ctx, COLUMNNAME_JP_InvoiceSchedule_Name)
								+ " = " + cm_BPartner.getJP_InvoiceSchedule_Name();
					throw new Exception(msg);
				}
			}else {
				m_BPartner.setC_InvoiceSchedule_ID(m_InvoiceSchedule.getC_InvoiceSchedule_ID());
			}
		}
		//Set C_PaymentTerm_ID
		if(!Util.isEmpty(cm_BPartner.getJP_PaymentTerm_Value()))
		{
			MPaymentTerm m_PaymentTerm = getMPaymentTerm(m_Client.getAD_Client_ID(), cm_BPartner.getJP_PaymentTerm_Value(), trxName);
			if(m_PaymentTerm == null)
			{
				if(!cm_BPartner.isIgnore_NMMaster_NotFoundJP())
				{
					msg = Msg.getMsg(ctx, "invalid")
								+ " : " + Msg.getElement(ctx, COLUMNNAME_JP_PaymentTerm_Value)
								+ " = " + cm_BPartner.getJP_PaymentTerm_Value();
					throw new Exception(msg);
				}
			}else {
				m_BPartner.setC_PaymentTerm_ID(m_PaymentTerm.getC_PaymentTerm_ID());
			}
		}
		//Set JP_BillSchema_ID
		if(m_BPartner.get_ColumnIndex("JP_BillSchema_ID") > -1 && !Util.isEmpty(cm_BPartner.getJP_BillSchema_Value()))
		{
			PO m_BillSchema = getMBillSchema(m_Client.getAD_Client_ID(), cm_BPartner.getJP_BillSchema_Value(), trxName);
			if(m_BillSchema == null)
			{
				if(!cm_BPartner.isIgnore_NMMaster_NotFoundJP())
				{
					msg = Msg.getMsg(ctx, "invalid")
								+ " : " + Msg.getElement(ctx, COLUMNNAME_JP_BillSchema_Value)
								+ " = " + cm_BPartner.getJP_BillSchema_Value();
					throw new Exception(msg);
				}
			}else {
				m_BPartner.set_ValueNoCheck("JP_BillSchema_ID", m_BillSchema.get_ValueAsInt("JP_BillSchema_ID"));
			}
		}
		//Set M_PriceList_ID
		if(!Util.isEmpty(cm_BPartner.getJP_PriceList_Name()))
		{
			MPriceList m_PriceList = getMPriceList(m_Client.getAD_Client_ID(), cm_BPartner.getJP_PriceList_Name(), trxName);
			if(m_PriceList == null)
			{
				if(!cm_BPartner.isIgnore_NMMaster_NotFoundJP())
				{
					msg = Msg.getMsg(ctx, "invalid")
								+ " : " + Msg.getElement(ctx, COLUMNNAME_JP_PriceList_Name)
								+ " = " + cm_BPartner.getJP_PriceList_Name();
					throw new Exception(msg);
				}
			}else {
				m_BPartner.setM_PriceList_ID(m_PriceList.getM_PriceList_ID());
			}
		}
		//Set M_DiscountSchema_ID
		if(!Util.isEmpty(cm_BPartner.getJP_DiscountSchema_Name()))
		{
			MDiscountSchema m_DiscountSchema = getMDiscountSchema(m_Client.getAD_Client_ID(), cm_BPartner.getJP_DiscountSchema_Name(), trxName);
			if(m_DiscountSchema == null)
			{
				if(!cm_BPartner.isIgnore_NMMaster_NotFoundJP())
				{
					msg = Msg.getMsg(ctx, "invalid")
								+ " : " + Msg.getElement(ctx, COLUMNNAME_JP_DiscountSchema_Name)
								+ " = " + cm_BPartner.getJP_DiscountSchema_Name();
					throw new Exception(msg);
				}
			}else {
				m_BPartner.setM_DiscountSchema_ID(m_DiscountSchema.getM_DiscountSchema_ID());
			}
		}
		//Set C_Dunning_ID
		if(!Util.isEmpty(cm_BPartner.getJP_Dunning_Name()))
		{
			MDunning m_Dunning = getMDunning(m_Client.getAD_Client_ID(), cm_BPartner.getJP_Dunning_Name(), trxName);
			if(m_Dunning == null)
			{
				if(!cm_BPartner.isIgnore_NMMaster_NotFoundJP())
				{
					msg = Msg.getMsg(ctx, "invalid")
								+ " : " + Msg.getElement(ctx, COLUMNNAME_JP_Dunning_Name)
								+ " = " + cm_BPartner.getJP_Dunning_Name();
					throw new Exception(msg);
				}
			}else {
				m_BPartner.setC_Dunning_ID(m_Dunning.getC_Dunning_ID());
			}
		}
		
		/**Vendor Information*/
		m_BPartner.setPaymentRulePO(cm_BPartner.getPaymentRulePO());
		m_BPartner.setIs1099Vendor(cm_BPartner.is1099Vendor());
		m_BPartner.setIsManufacturer(cm_BPartner.isManufacturer());
		if(m_BPartner.get_ColumnIndex("IsQualifiedInvoiceIssuerJP") > -1 )
		{
			m_BPartner.set_ValueNoCheck("IsQualifiedInvoiceIssuerJP", cm_BPartner.isQualifiedInvoiceIssuerJP());
		}
		if(m_BPartner.get_ColumnIndex("JP_RegisteredNumberOfQII") > -1 && !Util.isEmpty(cm_BPartner.getJP_RegisteredNumberOfQII()) )
		{
			m_BPartner.set_ValueNoCheck("JP_RegisteredNumberOfQII", cm_BPartner.getJP_RegisteredNumberOfQII());
		}
		if(m_BPartner.get_ColumnIndex("JP_RegisteredDateOfQII") > -1 && cm_BPartner.getJP_RegisteredDateOfQII() != null)
		{
			m_BPartner.set_ValueNoCheck("JP_RegisteredDateOfQII", cm_BPartner.getJP_RegisteredDateOfQII());
		}
		//Set PO_PaymentTerm_ID
		if(!Util.isEmpty(cm_BPartner.getJP_PO_PaymentTerm_Value()))
		{
			MPaymentTerm m_PaymentTerm = getMPaymentTerm(m_Client.getAD_Client_ID(), cm_BPartner.getJP_PO_PaymentTerm_Value(), trxName);
			if(m_PaymentTerm == null)
			{
				if(!cm_BPartner.isIgnore_NMMaster_NotFoundJP())
				{
					msg = Msg.getMsg(ctx, "invalid")
								+ " : " + Msg.getElement(ctx, COLUMNNAME_JP_PO_PaymentTerm_Value)
								+ " = " + cm_BPartner.getJP_PO_PaymentTerm_Value();
					throw new Exception(msg);
				}
			}else {
				m_BPartner.setPO_PaymentTerm_ID(m_PaymentTerm.getC_PaymentTerm_ID());
			}
		}
		//Set JP_BillSchemaPO_ID
		if(m_BPartner.get_ColumnIndex("JP_BillSchemaPO_ID") > -1 && !Util.isEmpty(cm_BPartner.getJP_BillSchemaPO_Value()))
		{
			PO m_BillSchema = getMBillSchema(m_Client.getAD_Client_ID(), cm_BPartner.getJP_BillSchemaPO_Value(), trxName);
			if(m_BillSchema == null)
			{
				if(!cm_BPartner.isIgnore_NMMaster_NotFoundJP())
				{
					msg = Msg.getMsg(ctx, "invalid")
								+ " : " + Msg.getElement(ctx, COLUMNNAME_JP_BillSchemaPO_Value)
								+ " = " + cm_BPartner.getJP_BillSchemaPO_Value();
					throw new Exception(msg);
				}
			}else {
				m_BPartner.set_ValueNoCheck("JP_BillSchemaPO_ID", m_BillSchema.get_ValueAsInt("JP_BillSchema_ID"));
			}
		}
		//Set PO_PriceList_ID
		if(!Util.isEmpty(cm_BPartner.getJP_PO_PriceList_Name()))
		{
			MPriceList m_PriceList = getMPriceList(m_Client.getAD_Client_ID(), cm_BPartner.getJP_PO_PriceList_Name(), trxName);
			if(m_PriceList == null)
			{
				if(!cm_BPartner.isIgnore_NMMaster_NotFoundJP())
				{
					msg = Msg.getMsg(ctx, "invalid")
								+ " : " + Msg.getElement(ctx, COLUMNNAME_JP_PO_PriceList_Name)
								+ " = " + cm_BPartner.getJP_PO_PriceList_Name();
					throw new Exception(msg);
				}
			}else {
				m_BPartner.setPO_PriceList_ID(m_PriceList.getM_PriceList_ID());
			}
		}
		//Set PO_DiscountSchema_ID
		if(!Util.isEmpty(cm_BPartner.getJP_PO_DiscountSchema_Name()))
		{
			MDiscountSchema m_DiscountSchema = getMDiscountSchema(m_Client.getAD_Client_ID(), cm_BPartner.getJP_PO_DiscountSchema_Name(), trxName);
			if(m_DiscountSchema == null)
			{
				if(!cm_BPartner.isIgnore_NMMaster_NotFoundJP())
				{
					msg = Msg.getMsg(ctx, "invalid")
								+ " : " + Msg.getElement(ctx, COLUMNNAME_JP_PO_DiscountSchema_Name)
								+ " = " + cm_BPartner.getJP_PO_DiscountSchema_Name();
					throw new Exception(msg);
				}
			}else {
				m_BPartner.setPO_DiscountSchema_ID(m_DiscountSchema.getM_DiscountSchema_ID());
			}
		}
		//Set Default1099Box_ID
		if(!Util.isEmpty(cm_BPartner.getJP_Default1099Box_Value()))
		{
			PO m_1099Box = getM1099Box(m_Client.getAD_Client_ID(), cm_BPartner.getJP_Default1099Box_Value(), trxName);
			if(m_1099Box == null)
			{
				if(!cm_BPartner.isIgnore_NMMaster_NotFoundJP())
				{
					msg = Msg.getMsg(ctx, "invalid")
								+ " : " + Msg.getElement(ctx, COLUMNNAME_JP_Default1099Box_Value)
								+ " = " + cm_BPartner.getJP_Default1099Box_Value();
					throw new Exception(msg);
				}
			}else {
				m_BPartner.setDefault1099Box_ID(m_1099Box.get_ValueAsInt("C_1099Box_ID"));
			}
		}
		
		/**Document Preferences*/
		m_BPartner.setPOReference(cm_BPartner.getPOReference());
		m_BPartner.setAD_Language(cm_BPartner.getAD_Language());
		m_BPartner.setSO_Description(cm_BPartner.getSO_Description());
		m_BPartner.setDocumentCopies(cm_BPartner.getDocumentCopies());
		m_BPartner.setIsDiscountPrinted(cm_BPartner.isDiscountPrinted());
		//Set C_Greeting_ID
		if(!Util.isEmpty(cm_BPartner.getJP_Greeting_Name()))
		{
			PO m_1099Box = getM1099Box(m_Client.getAD_Client_ID(), cm_BPartner.getJP_Greeting_Name(), trxName);
			if(m_1099Box == null)
			{
				if(!cm_BPartner.isIgnore_NMMaster_NotFoundJP())
				{
					msg = Msg.getMsg(ctx, "invalid")
								+ " : " + Msg.getElement(ctx, COLUMNNAME_JP_Greeting_Name)
								+ " = " + cm_BPartner.getJP_Greeting_Name();
					throw new Exception(msg);
				}
			}else {
				m_BPartner.setC_Greeting_ID(m_1099Box.get_ValueAsInt("C_Greeting_ID"));
			}
		}
		//Set Invoice_PrintFormat_ID
		if(!Util.isEmpty(cm_BPartner.getJP_Invoice_PrintFormat_Name()))
		{
			MPrintFormat m_PrintFormat = getMPrintFormat(m_Client.getAD_Client_ID(), cm_BPartner.getJP_Invoice_PrintFormat_Name(), trxName);
			if(m_PrintFormat == null)
			{
				if(!cm_BPartner.isIgnore_NMMaster_NotFoundJP())
				{
					msg = Msg.getMsg(ctx, "invalid")
								+ " : " + Msg.getElement(ctx, COLUMNNAME_JP_Invoice_PrintFormat_Name)
								+ " = " + cm_BPartner.getJP_Invoice_PrintFormat_Name();
					throw new Exception(msg);
				}
			}else {
				m_BPartner.setInvoice_PrintFormat_ID(m_PrintFormat.getAD_PrintFormat_ID());
			}
		}
		//Set JP_Bill_PrintFormat_ID
		if(!Util.isEmpty(cm_BPartner.getJP_Bill_PrintFormat_Name()))
		{
			MPrintFormat m_PrintFormat = getMPrintFormat(m_Client.getAD_Client_ID(), cm_BPartner.getJP_Bill_PrintFormat_Name(), trxName);
			if(m_PrintFormat == null)
			{
				if(!cm_BPartner.isIgnore_NMMaster_NotFoundJP())
				{
					msg = Msg.getMsg(ctx, "invalid")
								+ " : " + Msg.getElement(ctx, COLUMNNAME_JP_Bill_PrintFormat_Name)
								+ " = " + cm_BPartner.getJP_Bill_PrintFormat_Name();
					throw new Exception(msg);
				}				
			}else {
				m_BPartner.set_ValueNoCheck("JP_Bill_PrintFormat_ID", m_PrintFormat.getAD_PrintFormat_ID());
			}
		}
		
		/**Sales Information*/
		m_BPartner.setDUNS(cm_BPartner.getDUNS());
		m_BPartner.setNAICS(cm_BPartner.getNAICS());
		m_BPartner.setNumberEmployees(cm_BPartner.getNumberEmployees());
		
		m_BPartner.set_ValueNoCheck("JP_CM_BPartner_ID", cm_BPartner.getJP_CM_BPartner_ID());
		m_BPartner.saveEx(trxName);
		
		MCMBPartnerLocation[] locations = cm_BPartner.getMCMBPartnerLocations();
		if(locations.length > 0)
		{
			for(int i = 0; locations.length > i; i++)
			{
				try {
					MCMBPartnerLocation.createMBPartnerLocation(ctx, m_Client, cm_BPartner, locations[i], m_BPartner, true, trxName);
				}catch (Exception e) {
					;//Nothing to do
				}
			}
		}
		
		return m_BPartner;
	}
	


}
