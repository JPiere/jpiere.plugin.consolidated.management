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
import java.util.Map;
import java.util.Properties;

import org.compiere.model.MAttributeSet;
import org.compiere.model.MBPartner;
import org.compiere.model.MClient;
import org.compiere.model.MExpenseType;
import org.compiere.model.MFreightCategory;
import org.compiere.model.MLocator;
import org.compiere.model.MMailText;
import org.compiere.model.MProduct;
import org.compiere.model.MProductCategory;
import org.compiere.model.MProductPO;
import org.compiere.model.MResource;
import org.compiere.model.MResourceType;
import org.compiere.model.MRevenueRecognition;
import org.compiere.model.MTaxCategory;
import org.compiere.model.MWarehouse;
import org.compiere.model.PO;
import org.compiere.model.Query;
import org.compiere.model.X_M_PartType;
import org.compiere.util.CCache;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.compiere.util.Util;

/**
 * JPIERE-0629 Consolidated Management - Consolidated Product Master
 *
 *  @author Hideaki Hagiwara
 *
 */
public class MCMProduct extends X_JP_CM_Product {

	private static final long serialVersionUID = 7828215517122169995L;

	public MCMProduct(Properties ctx, int JP_CM_Product_ID, String trxName) 
	{
		super(ctx, JP_CM_Product_ID, trxName);
	}

	public MCMProduct(Properties ctx, int JP_CM_Product_ID, String trxName, String... virtualColumns) 
	{
		super(ctx, JP_CM_Product_ID, trxName, virtualColumns);
	}

	public MCMProduct(Properties ctx, String JP_CM_Product_UU, String trxName)
	{
		super(ctx, JP_CM_Product_UU, trxName);
	}

	public MCMProduct(Properties ctx, String JP_CM_Product_UU, String trxName, String... virtualColumns) 
	{
		super(ctx, JP_CM_Product_UU, trxName, virtualColumns);
	}

	public MCMProduct(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}

	@Override
	protected boolean beforeSave(boolean newRecord) 
	{
		return true;
	}

	static public MProduct getMProduct(int AD_Client_ID, String value, String trxName)
	{
		StringBuilder whereClause = new StringBuilder("AD_Client_ID=? AND Value=?");
		MProduct po = new Query(Env.getCtx(), MProduct.Table_Name, whereClause.toString(), trxName)
							.setParameters(AD_Client_ID, value)
							.firstOnly();
		return po;
	}
	
	private static CCache<Integer,Map<String,MProductCategory>>c_MProductCategory = new CCache<Integer,Map<String,MProductCategory>>(Table_Name, 60);
	
	static public MProductCategory getMProductCategory(int AD_Client_ID, String value, String trxName)
	{	
		MProductCategory po = null;
		Map<String,MProductCategory> cachedMap = c_MProductCategory.get(AD_Client_ID);
		if (cachedMap == null)
		{
			cachedMap = new HashMap<String,MProductCategory>();
			c_MProductCategory.put(AD_Client_ID, cachedMap);	
		}
		
		po = cachedMap.get(value);
		if(po == null)
		{
			StringBuilder whereClause = new StringBuilder("AD_Client_ID=? AND Value=?");
			po = new Query(Env.getCtx(), MProductCategory.Table_Name, whereClause.toString(), trxName)
								.setParameters(AD_Client_ID, value)
								.firstOnly();
			cachedMap.put(value, po);
		}
		return po;
	}
	
	private static CCache<Integer,Map<String,MTaxCategory>>c_MTaxCategory = new CCache<Integer,Map<String,MTaxCategory>>(Table_Name, 60);
	
	static public MTaxCategory getMTaxCategory(int AD_Client_ID, String name, String trxName)
	{	
		MTaxCategory po = null;
		Map<String,MTaxCategory> cachedMap = c_MTaxCategory.get(AD_Client_ID);
		if (cachedMap == null)
		{
			cachedMap = new HashMap<String,MTaxCategory>();
			c_MTaxCategory.put(AD_Client_ID, cachedMap);	
		}
		
		po = cachedMap.get(name);
		if(po == null)
		{
			StringBuilder whereClause = new StringBuilder("AD_Client_ID=? AND Name=?");
			po = new Query(Env.getCtx(), MTaxCategory.Table_Name, whereClause.toString(), trxName)
								.setParameters(AD_Client_ID, name)
								.setOrderBy("C_TaxCategory_ID ASC")
								.firstOnly();
			cachedMap.put(name, po);
		}
		return po;
	}
	
	private static CCache<Integer,Map<String,MRevenueRecognition>>c_MRevenueRecognition= new CCache<Integer,Map<String,MRevenueRecognition>>(Table_Name, 60);
	
	static public MRevenueRecognition getMRevenueRecognition (int AD_Client_ID, String name, String trxName)
	{	
		MRevenueRecognition po = null;
		Map<String,MRevenueRecognition> cachedMap = c_MRevenueRecognition.get(AD_Client_ID);
		if (cachedMap == null)
		{
			cachedMap = new HashMap<String,MRevenueRecognition>();
			c_MRevenueRecognition.put(AD_Client_ID, cachedMap);	
		}
		
		po = cachedMap.get(name);
		if(po == null)
		{
			StringBuilder whereClause = new StringBuilder("AD_Client_ID=? AND Name=?");
			po = new Query(Env.getCtx(), MRevenueRecognition.Table_Name, whereClause.toString(), trxName)
								.setParameters(AD_Client_ID, name)
								.setOrderBy("C_RevenueRecognition_ID ASC")
								.firstOnly();
			cachedMap.put(name, po);
		}
		return po;
	}
	
	private static CCache<Integer,Map<String,MMailText>>c_MMailText= new CCache<Integer,Map<String,MMailText>>(Table_Name, 60);
	
	static public MMailText getMMailText (int AD_Client_ID, String name, String trxName)
	{	
		MMailText po = null;
		Map<String,MMailText> cachedMap = c_MMailText.get(AD_Client_ID);
		if (cachedMap == null)
		{
			cachedMap = new HashMap<String,MMailText>();
			c_MMailText.put(AD_Client_ID, cachedMap);	
		}
		
		po = cachedMap.get(name);
		if(po == null)
		{
			StringBuilder whereClause = new StringBuilder("AD_Client_ID=? AND Name=?");
			po = new Query(Env.getCtx(), MMailText.Table_Name, whereClause.toString(), trxName)
								.setParameters(AD_Client_ID, name)
								.setOrderBy("R_MailText_ID ASC")
								.firstOnly();
			cachedMap.put(name, po);
		}
		return po;
	}
	
	private static CCache<Integer,Map<String,MFreightCategory>>c_MFreightCategory = new CCache<Integer,Map<String,MFreightCategory>>(Table_Name, 60);
	
	static public MFreightCategory getMFreightCategory(int AD_Client_ID, String value, String trxName)
	{		
		MFreightCategory po = null;
		Map<String,MFreightCategory> cachedMap = c_MFreightCategory.get(AD_Client_ID);
		if (cachedMap == null)
		{
			cachedMap = new HashMap<String,MFreightCategory>();
			c_MFreightCategory.put(AD_Client_ID, cachedMap);	
		}
		
		po = cachedMap.get(value);
		if(po == null)
		{
			StringBuilder whereClause = new StringBuilder("AD_Client_ID=? AND Value=?");
			po = new Query(Env.getCtx(), MFreightCategory.Table_Name, whereClause.toString(), trxName)
								.setParameters(AD_Client_ID, value)
								.firstOnly();
			cachedMap.put(value, po);
		}
		return po;
	}
	
	private static CCache<Integer,Map<String,X_M_PartType>>c_MPartType = new CCache<Integer,Map<String,X_M_PartType>>(Table_Name, 60);
	
	static public X_M_PartType getMPartType(int AD_Client_ID, String name, String trxName)
	{	
		X_M_PartType po = null;
		Map<String,X_M_PartType> cachedMap = c_MPartType.get(AD_Client_ID);
		if (cachedMap == null)
		{
			cachedMap = new HashMap<String,X_M_PartType>();
			c_MPartType.put(AD_Client_ID, cachedMap);	
		}
		
		po = cachedMap.get(name);
		if(po == null)
		{
			StringBuilder whereClause = new StringBuilder("AD_Client_ID=? AND Name=?");
			po = new Query(Env.getCtx(), X_M_PartType.Table_Name, whereClause.toString(), trxName)
								.setParameters(AD_Client_ID, name)
								.setOrderBy("M_PartType_ID ASC")
								.firstOnly();
			cachedMap.put(name, po);
		}
		return po;
	}
	
	private static CCache<Integer,Map<String,MLocator>>c_MLocator = new CCache<Integer,Map<String,MLocator>>(Table_Name, 60);
	
	static public MLocator getMLocator(int AD_Client_ID, String value, String trxName)
	{	
		MLocator po = null;
		Map<String,MLocator> cachedMap = c_MLocator.get(AD_Client_ID);
		if (cachedMap == null)
		{
			cachedMap = new HashMap<String,MLocator>();
			c_MLocator.put(AD_Client_ID, cachedMap);	
		}
		
		po = cachedMap.get(value);
		if(po == null)
		{
			StringBuilder whereClause = new StringBuilder("AD_Client_ID=? AND Value=?");
			po = new Query(Env.getCtx(), MLocator.Table_Name, whereClause.toString(), trxName)
								.setParameters(AD_Client_ID, value)
								.firstOnly();
			cachedMap.put(value, po);
		}
		return po;
	}
	
	private static CCache<Integer,Map<String,MAttributeSet>>c_MAttributeSet = new CCache<Integer,Map<String,MAttributeSet>>(Table_Name, 60);
	
	static public MAttributeSet getMAttributeSet(int AD_Client_ID, String name, String trxName)
	{	
		MAttributeSet po = null;
		Map<String,MAttributeSet> cachedMap = c_MAttributeSet.get(AD_Client_ID);
		if (cachedMap == null)
		{
			cachedMap = new HashMap<String,MAttributeSet>();
			c_MAttributeSet.put(AD_Client_ID, cachedMap);	
		}
		
		po = cachedMap.get(name);
		if(po == null)
		{
			StringBuilder whereClause = new StringBuilder("AD_Client_ID=? AND Name=?");
			po = new Query(Env.getCtx(), MAttributeSet.Table_Name, whereClause.toString(), trxName)
								.setParameters(AD_Client_ID, name)
								.setOrderBy("M_AttributeSet_ID ASC")
								.firstOnly();
			cachedMap.put(name, po);
		}
		return po;
	}
	
	private static CCache<Integer,Map<String,MResourceType>>c_MResourceType = new CCache<Integer,Map<String,MResourceType>>(Table_Name, 60);
	
	static public MResourceType getMResourceType(int AD_Client_ID, String value, String trxName)
	{	
		MResourceType po = null;
		Map<String,MResourceType> cachedMap = c_MResourceType.get(AD_Client_ID);
		if (cachedMap == null)
		{
			cachedMap = new HashMap<String,MResourceType>();
			c_MResourceType.put(AD_Client_ID, cachedMap);	
		}
		
		po = cachedMap.get(value);
		if(po == null)
		{
			StringBuilder whereClause = new StringBuilder("AD_Client_ID=? AND Value=?");
			po = new Query(Env.getCtx(), MResourceType.Table_Name, whereClause.toString(), trxName)
								.setParameters(AD_Client_ID, value)
								.firstOnly();
			cachedMap.put(value, po);
		}
		return po;
	}
	
	private static CCache<Integer,Map<String,MWarehouse>>c_MWarehouse = new CCache<Integer,Map<String,MWarehouse>>(Table_Name, 60);
	
	static public MWarehouse getMWarehouse(int AD_Client_ID, String value, String trxName)
	{	
		MWarehouse po = null;
		Map<String,MWarehouse> cachedMap = c_MWarehouse.get(AD_Client_ID);
		if (cachedMap == null)
		{
			cachedMap = new HashMap<String,MWarehouse>();
			c_MWarehouse.put(AD_Client_ID, cachedMap);	
		}
		
		po = cachedMap.get(value);
		if(po == null)
		{
			StringBuilder whereClause = new StringBuilder("AD_Client_ID=? AND Value=?");
			po = new Query(Env.getCtx(), MWarehouse.Table_Name, whereClause.toString(), trxName)
								.setParameters(AD_Client_ID, value)
								.firstOnly();
			cachedMap.put(value, po);
		}
		return po;
	}
	
	private static CCache<Integer,Map<String,MBPartner>>c_MBPartner = new CCache<Integer,Map<String,MBPartner>>(Table_Name, 60);
	
	static public MBPartner getMBPartner(int AD_Client_ID, String value, String trxName)
	{	
		MBPartner po = null;
		Map<String,MBPartner> cachedMap = c_MBPartner.get(AD_Client_ID);
		if (cachedMap == null)
		{
			cachedMap = new HashMap<String,MBPartner>();
			c_MBPartner.put(AD_Client_ID, cachedMap);	
		}
		
		po = cachedMap.get(value);
		if(po == null)
		{
			StringBuilder whereClause = new StringBuilder("AD_Client_ID=? AND Value=?");
			po = new Query(Env.getCtx(), MBPartner.Table_Name, whereClause.toString(), trxName)
								.setParameters(AD_Client_ID, value)
								.firstOnly();
			cachedMap.put(value, po);
		}
		return po;
	}
	
	static public MProduct createMProduct(Properties ctx, MClient m_Client, MCMProduct cm_Product, String trxName) throws Exception
	{
		if(m_Client == null || cm_Product == null)
			return null;
		
		String msg = null;
		MProduct m_Product = new MProduct(ctx, 0, trxName);
		PO.copyValues(cm_Product, m_Product);
		m_Product.set_ValueNoCheck("AD_Client_ID", m_Client.getAD_Client_ID());
		m_Product.setAD_Org_ID(0);
		m_Product.setValue(cm_Product.getValue());
		m_Product.setName(cm_Product.getName());
		m_Product.setDescription(cm_Product.getDescription());
		m_Product.setHelp(cm_Product.getHelp());
		m_Product.setDocumentNote(cm_Product.getDocumentNote());
		m_Product.setUPC(cm_Product.getUPC());
		m_Product.setSKU(cm_Product.getSKU());
		m_Product.setIsActive(cm_Product.isActive());
		m_Product.setIsSummary(cm_Product.isSummary());
		//Set M_Product_Category_ID
		MProductCategory m_ProductCategory = getMProductCategory(m_Client.getAD_Client_ID(), cm_Product.getJP_Product_Category_Value(), trxName);
		if(m_ProductCategory == null)
		{
			msg = Msg.getMsg(ctx, "invalid")
						+ " : " + Msg.getElement(ctx, COLUMNNAME_JP_Product_Category_Value)
						+ " = " + cm_Product.getJP_Product_Category_Value();
			throw new Exception(msg);
			
		}else {
			m_Product.setM_Product_Category_ID(m_ProductCategory.getM_Product_Category_ID());
		}
		m_Product.setClassification(cm_Product.getClassification());
		//Set C_TaxCategory_ID
		MTaxCategory m_TaxCategory = getMTaxCategory(m_Client.getAD_Client_ID(), cm_Product.getJP_TaxCategory_Name(), trxName);
		if(m_TaxCategory == null)
		{
			msg = Msg.getMsg(ctx, "invalid")
						+ " : " + Msg.getElement(ctx, COLUMNNAME_JP_TaxCategory_Name)
						+ " = " + cm_Product.getJP_TaxCategory_Name();
			throw new Exception(msg);
			
		}else {
			m_Product.setC_TaxCategory_ID(m_TaxCategory.getC_TaxCategory_ID());
		}
		//Set C_RevenueRecognition_ID
		if(!Util.isEmpty(cm_Product.getJP_RevenueRecognition_Name()))
		{
			MRevenueRecognition m_RevenueRecognition = getMRevenueRecognition(m_Client.getAD_Client_ID(), cm_Product.getJP_RevenueRecognition_Name(), trxName);
			if(m_RevenueRecognition == null)
			{
				msg = Msg.getMsg(ctx, "invalid")
							+ " : " + Msg.getElement(ctx, COLUMNNAME_JP_RevenueRecognition_Name)
							+ " = " + cm_Product.getJP_RevenueRecognition_Name();
				throw new Exception(msg);
				
			}else {
				m_Product.setC_RevenueRecognition_ID(m_RevenueRecognition.getC_RevenueRecognition_ID());
			}
		}
		m_Product.setC_UOM_ID(cm_Product.getC_UOM_ID());
		m_Product.setProductType(cm_Product.getProductType());
		//Set R_MailText_ID
		if(!Util.isEmpty(cm_Product.getJP_MailText_Name()))
		{
			MMailText m_MailText = getMMailText(m_Client.getAD_Client_ID(), cm_Product.getJP_MailText_Name(), trxName);
			if(m_MailText == null)
			{
				msg =  Msg.getMsg(ctx, "invalid")
							+ " : " + Msg.getElement(ctx, COLUMNNAME_JP_MailText_Name)
							+ " = " + cm_Product.getJP_MailText_Name();
				throw new Exception(msg);
				
			}else {
				m_Product.setR_MailText_ID(m_MailText.getR_MailText_ID());
			}
		}
		m_Product.setWeight(cm_Product.getWeight());
		m_Product.setVolume(cm_Product.getVolume());
		m_Product.setIsOwnBox(cm_Product.isOwnBox());
		m_Product.setCustomsTariffNumber(cm_Product.getCustomsTariffNumber());
		//Set M_FreightCategory_ID
		if(!Util.isEmpty(cm_Product.getJP_FreightCategory_Value()))
		{
			MFreightCategory m_FreightCategory = getMFreightCategory(m_Client.getAD_Client_ID(), cm_Product.getJP_FreightCategory_Value(), trxName);
			if(m_FreightCategory == null)
			{
				msg = Msg.getMsg(ctx, "invalid")
							+ " : " + Msg.getElement(ctx, COLUMNNAME_JP_FreightCategory_Value)
							+ " = " + cm_Product.getJP_FreightCategory_Value();
				throw new Exception(msg);
				
			}else {
				m_Product.setM_FreightCategory_ID(m_FreightCategory.getM_FreightCategory_ID());
			}
		}
		m_Product.setIsDropShip(cm_Product.isDropShip());
		m_Product.setIsStocked(cm_Product.isStocked());
		m_Product.setIsManufactured(cm_Product.isManufactured());
		m_Product.setIsPhantom(cm_Product.isPhantom());
		m_Product.setIsKanban(cm_Product.isKanban());
		//Set M_PartType_ID
		if(!Util.isEmpty(cm_Product.getJP_PartType_Name()))
		{
			X_M_PartType m_PartType = getMPartType(m_Client.getAD_Client_ID(), cm_Product.getJP_PartType_Name(), trxName);
			if(m_PartType == null)
			{
				msg = Msg.getMsg(ctx, "invalid")
							+ " : " + Msg.getElement(ctx, COLUMNNAME_JP_PartType_Name)
							+ " = " + cm_Product.getJP_PartType_Name();
				throw new Exception(msg);
				
			}else {
				m_Product.setM_PartType_ID(m_PartType.getM_PartType_ID());
			}
		}
		//Set M_Locator_ID
		if(!Util.isEmpty(cm_Product.getJP_Locator_Value()))
		{
			MLocator m_FreightCategory = getMLocator(m_Client.getAD_Client_ID(), cm_Product.getJP_Locator_Value(), trxName);
			if(m_FreightCategory == null)
			{
				msg = Msg.getMsg(ctx, "invalid")
							+ " : " + Msg.getElement(ctx, COLUMNNAME_JP_Locator_Value)
							+ " = " + cm_Product.getJP_Locator_Value();
				throw new Exception(msg);
				
			}else {
				m_Product.setM_Locator_ID(m_FreightCategory.getM_Locator_ID());
			}
		}
		m_Product.setShelfWidth(cm_Product.getShelfWidth());
		m_Product.setShelfHeight(cm_Product.getShelfHeight());
		m_Product.setShelfDepth(cm_Product.getShelfDepth());
		m_Product.setUnitsPerPallet(cm_Product.getUnitsPerPallet());
		m_Product.setIsBOM(cm_Product.isBOM());
		m_Product.setIsAutoProduce(cm_Product.isAutoProduce());
		m_Product.setIsInvoicePrintDetails(cm_Product.isInvoicePrintDetails());
		m_Product.setIsPickListPrintDetails(cm_Product.isPickListPrintDetails());
		m_Product.setIsPurchased(cm_Product.isPurchased());
		m_Product.setIsSold(cm_Product.isSold());
		m_Product.setDiscontinued(cm_Product.isDiscontinued());
		m_Product.setDiscontinuedAt(cm_Product.getDiscontinuedAt());
		m_Product.setIsExcludeAutoDelivery(cm_Product.isExcludeAutoDelivery());
		m_Product.setImageURL(cm_Product.getImageURL());
		m_Product.setDescriptionURL(cm_Product.getDescriptionURL());
		m_Product.setGuaranteeDays(cm_Product.getGuaranteeDays());
		m_Product.setGuaranteeDaysMin(cm_Product.getGuaranteeDaysMin());
		//Set M_AttributeSet_ID
		if(!Util.isEmpty(cm_Product.getJP_AttributeSet_Name()))
		{
			MAttributeSet m_AttributeSet = getMAttributeSet(m_Client.getAD_Client_ID(), cm_Product.getJP_AttributeSet_Name(), trxName);
			if(m_AttributeSet == null)
			{
				msg =  Msg.getMsg(ctx, "invalid")
							+ " : " + Msg.getElement(ctx, COLUMNNAME_JP_AttributeSet_Name)
							+ " = " + cm_Product.getJP_AttributeSet_Name();
				throw new Exception(msg);
				
			}else {
				m_Product.setM_AttributeSet_ID(m_AttributeSet.getM_AttributeSet_ID());
			}
		}
		m_Product.setIsWebStoreFeatured(cm_Product.isWebStoreFeatured());
		m_Product.setIsSelfService(cm_Product.isSelfService());
		m_Product.setGroup1(cm_Product.getGroup1());
		m_Product.setGroup2(cm_Product.getGroup2());
		m_Product.set_ValueNoCheck(MCMProduct.COLUMNNAME_JP_CM_Product_ID, cm_Product.getJP_CM_Product_ID());
		m_Product.saveEx(trxName);
		
		if(!Util.isEmpty(cm_Product.getBPartnerValue()))
			createMProductPO(ctx, m_Client, cm_Product, m_Product, trxName);
		
		return m_Product;
	}
	
	static public MProduct createResource(Properties ctx, MClient m_Client, MCMProduct cm_Product, String trxName) throws Exception
	{	
		if(m_Client == null || cm_Product == null)
			return null;
		
		String msg = null;		
		MResource m_Resource = new MResource(ctx, 0, trxName);
		PO.copyValues(cm_Product, m_Resource);
		m_Resource.set_ValueNoCheck("AD_Client_ID", m_Client.getAD_Client_ID());
		m_Resource.setAD_Org_ID(0);
		m_Resource.setValue(cm_Product.getValue());
		m_Resource.setName(cm_Product.getName());
		m_Resource.setDescription(cm_Product.getDescription());
		//Set S_ResourceType_ID
		MResourceType m_ResourceType = getMResourceType(m_Client.getAD_Client_ID(), cm_Product.getJP_ResourceType_Value(), trxName);
		if(m_ResourceType == null)
		{
			msg = Msg.getMsg(ctx, "invalid")
						+ " : " + Msg.getElement(ctx, COLUMNNAME_JP_ResourceType_Value)
						+ " = " + cm_Product.getJP_ResourceType_Value();
			throw new Exception(msg);
			
		}else {
			m_Resource.setS_ResourceType_ID(m_ResourceType.getS_ResourceType_ID());
		}
		//Set M_Warehouse_ID
		MWarehouse m_Warehouse = getMWarehouse(m_Client.getAD_Client_ID(), cm_Product.getJP_Warehouse_Value(), trxName);
		if(m_Warehouse == null)
		{
			msg = Msg.getMsg(ctx, "invalid")
						+ " : " + Msg.getElement(ctx, COLUMNNAME_JP_Warehouse_Value)
						+ " = " + cm_Product.getJP_Warehouse_Value();
			throw new Exception(msg);
		}else {
			m_Resource.setM_Warehouse_ID(m_Warehouse.getM_Warehouse_ID());
		}
		
		m_Resource.saveEx(trxName);
		
		MProduct m_Product = m_Resource.getProduct();
		m_Product.set_ValueNoCheck("AD_Client_ID", m_Client.getAD_Client_ID());
		m_Product.saveEx(trxName);
		
		if(!Util.isEmpty(cm_Product.getBPartnerValue()))
			createMProductPO(ctx, m_Client, cm_Product, m_Product, trxName);
		
		return m_Product;
	}
	
	static public MProduct createExpense(Properties ctx, MClient m_Client, MCMProduct cm_Product, String trxName) throws Exception
	{
		if(m_Client == null || cm_Product == null)
			return null;
		
		String msg = null;
		MExpenseType m_ExpenseType = new MExpenseType(ctx, 0, trxName);
		PO.copyValues(cm_Product, m_ExpenseType);
		m_ExpenseType.set_ValueNoCheck("AD_Client_ID", m_Client.getAD_Client_ID());
		m_ExpenseType.setAD_Org_ID(0);
		m_ExpenseType.setValue(cm_Product.getValue());
		m_ExpenseType.setName(cm_Product.getName());
		m_ExpenseType.setDescription(cm_Product.getDescription());
		m_ExpenseType.setC_UOM_ID(cm_Product.getC_UOM_ID());
		//Set M_Product_Category_ID
		MProductCategory m_ProductCategory = getMProductCategory(m_Client.getAD_Client_ID(), cm_Product.getJP_Product_Category_Value(), trxName);
		if(m_ProductCategory == null)
		{
			msg =  Msg.getMsg(ctx, "invalid")
						+ " : " + Msg.getElement(ctx, COLUMNNAME_JP_Product_Category_Value)
						+ " = " + cm_Product.getJP_Product_Category_Value();
			throw new Exception(msg);
			
		}else {
			m_ExpenseType.setM_Product_Category_ID(m_ProductCategory.getM_Product_Category_ID());
		}
		//Set C_TaxCategory_ID
		MTaxCategory m_TaxCategory = getMTaxCategory(m_Client.getAD_Client_ID(), cm_Product.getJP_TaxCategory_Name(), trxName);
		if(m_TaxCategory == null)
		{
			msg =  Msg.getMsg(ctx, "invalid")
						+ " : " + Msg.getElement(ctx, COLUMNNAME_JP_TaxCategory_Name)
						+ " = " + cm_Product.getJP_TaxCategory_Name();
			throw new Exception(msg);
			
		}else {
			m_ExpenseType.setC_TaxCategory_ID(m_TaxCategory.getC_TaxCategory_ID());
		}
		
		m_ExpenseType.saveEx(trxName);
		
		MProduct m_Product = m_ExpenseType.getProduct();
		m_Product.set_ValueNoCheck("AD_Client_ID", m_Client.getAD_Client_ID());
		m_Product.saveEx(trxName);
		
		if(!Util.isEmpty(cm_Product.getBPartnerValue()))
		{
			try {
				createMProductPO(ctx, m_Client, cm_Product, m_Product, trxName);
			}catch (Exception e) {
				;//Nothing to do
			}
		}
		
		return m_Product;
	}
	
	static public MProductPO createMProductPO(Properties ctx, MClient m_Client, MCMProduct cm_Product, MProduct m_Product, String trxName)throws Exception
	{	
		if(m_Client == null || cm_Product == null || m_Product == null)
			return null;
		
		String msg = null;
		MBPartner m_BPartner = getMBPartner(m_Client.getAD_Client_ID(), cm_Product.getBPartnerValue(), trxName);
		if(m_BPartner == null)
		{
			msg = Msg.getMsg(ctx, "invalid")
						+ " : " + Msg.getElement(ctx, COLUMNNAME_BPartnerValue)
						+ " = " + cm_Product.getBPartnerValue();
			throw new Exception(msg);
		}
		
		MProductPO m_ProductPO = new MProductPO(ctx, 0, trxName);
		PO.copyValues(cm_Product, m_ProductPO);
		m_ProductPO.set_ValueNoCheck("AD_Client_ID", m_Client.getAD_Client_ID());
		m_ProductPO.setAD_Org_ID(0);
		m_ProductPO.setM_Product_ID(m_Product.getM_Product_ID());
		m_ProductPO.setC_BPartner_ID(m_BPartner.getC_BPartner_ID());
		m_ProductPO.setQualityRating(cm_Product.getQualityRating());
		m_ProductPO.setIsCurrentVendor(cm_Product.isCurrentVendor());
		m_ProductPO.setUPC(cm_Product.getJP_VendorUPC());
		m_ProductPO.setC_Currency_ID(cm_Product.getC_Currency_ID());
		m_ProductPO.setPriceList(cm_Product.getPriceList());
		m_ProductPO.setPriceEffective(cm_Product.getPriceEffective());
		m_ProductPO.setPricePO(cm_Product.getPricePO());
		m_ProductPO.setRoyaltyAmt(cm_Product.getRoyaltyAmt());
		m_ProductPO.setC_UOM_ID(cm_Product.getJP_VendorUOM_ID());
		m_ProductPO.setOrder_Min(cm_Product.getOrder_Min());
		m_ProductPO.setOrder_Pack(cm_Product.getOrder_Pack());
		m_ProductPO.setDeliveryTime_Promised(cm_Product.getDeliveryTime_Promised());
		m_ProductPO.setCostPerOrder(cm_Product.getCostPerOrder());
		m_ProductPO.setVendorProductNo(cm_Product.getVendorProductNo());
		m_ProductPO.setVendorCategory(cm_Product.getVendorCategory());
		m_ProductPO.setManufacturer(cm_Product.getManufacturer());
		m_ProductPO.saveEx(trxName);
		
		return m_ProductPO;
	}
}
