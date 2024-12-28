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

import org.compiere.model.MBPartner;
import org.compiere.model.MBPartnerLocation;
import org.compiere.model.MClient;
import org.compiere.model.MSalesRegion;
import org.compiere.model.PO;
import org.compiere.model.Query;
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
public class MCMBPartnerLocation extends X_JP_CM_BPartner_Location {

	private static final long serialVersionUID = 902561952271882306L;

	public MCMBPartnerLocation(Properties ctx, int JP_CM_BPartner_Location_ID, String trxName) 
	{
		super(ctx, JP_CM_BPartner_Location_ID, trxName);
	}

	public MCMBPartnerLocation(Properties ctx, int JP_CM_BPartner_Location_ID, String trxName, String... virtualColumns) 
	{
		super(ctx, JP_CM_BPartner_Location_ID, trxName, virtualColumns);
	}

	public MCMBPartnerLocation(Properties ctx, String JP_CM_BPartner_Location_UU, String trxName) 
	{
		super(ctx, JP_CM_BPartner_Location_UU, trxName);
	}

	public MCMBPartnerLocation(Properties ctx, String JP_CM_BPartner_Location_UU, String trxName, String... virtualColumns) 
	{
		super(ctx, JP_CM_BPartner_Location_UU, trxName, virtualColumns);
	}

	public MCMBPartnerLocation(Properties ctx, ResultSet rs, String trxName) 
	{
		super(ctx, rs, trxName);
	}
	
	public static MCMBPartnerLocation[] getForBPartner(Properties ctx, int JP_CM_BPartner_ID, String trxName) 
	{
		List<MCMBPartnerLocation> list = new Query(ctx, Table_Name,
				"JP_CM_BPartner_ID=?", trxName).setParameters(JP_CM_BPartner_ID).list();
		MCMBPartnerLocation[] retValue = new MCMBPartnerLocation[list.size()];
		list.toArray(retValue);
		return retValue;
	} // getForBPartner
	
	private static CCache<Integer,Map<String,MSalesRegion>>c_MSalesRegion = new CCache<Integer,Map<String,MSalesRegion>>(Table_Name, 60);
	
	static public MSalesRegion getMSalesRegion(int AD_Client_ID, String value, String trxName)
	{	
		MSalesRegion po = null;
		Map<String,MSalesRegion> cachedMap = c_MSalesRegion.get(AD_Client_ID);
		if (cachedMap == null)
		{
			cachedMap = new HashMap<String,MSalesRegion>();
			c_MSalesRegion.put(AD_Client_ID, cachedMap);	
		}
		
		po = cachedMap.get(value);
		if(po == null)
		{
			StringBuilder whereClause = new StringBuilder("AD_Client_ID=? AND Value=?");
			po = new Query(Env.getCtx(), MSalesRegion.Table_Name, whereClause.toString(), trxName)
								.setParameters(AD_Client_ID, value)
								.firstOnly();
			cachedMap.put(value, po);
		}
		return po;
	}
	
	static public MBPartnerLocation createMBPartnerLocation(Properties ctx, MClient m_Client
			, MCMBPartner cm_BPartner, MCMBPartnerLocation cm_BPLocation, MBPartner m_BPartner, boolean isEnforce, String trxName)throws Exception
	{	
		MBPartnerLocation m_BPLocation = null;
		
		if(!isEnforce)//Check already registered
		{
			MBPartnerLocation[] m_BPLocations = MBPartnerLocation.getForBPartner(ctx, m_BPartner.getC_BPartner_ID(),trxName);
			for(int i = 0; m_BPLocations.length > i; i++)
			{
				if(cm_BPLocation.getC_Location_ID() == m_BPLocations[i].getC_Location_ID())
				{
					m_BPLocation = m_BPLocations[i];
					break;
				}
			}
			
			if(m_BPLocation != null)
				return m_BPLocation;
		}
		
		Properties cloneCtx = (Properties)ctx.clone();
		cloneCtx.setProperty("#AD_Client_ID", String.valueOf(m_Client.getAD_Client_ID()));
		
		String msg = null;
		m_BPLocation = new MBPartnerLocation(ctx, 0, trxName);
		PO.copyValues(cm_BPLocation, m_BPLocation);
		m_BPLocation.setC_BPartner_ID(m_BPartner.getC_BPartner_ID());
		m_BPLocation.setName(cm_BPLocation.getName());
		m_BPLocation.setC_Location_ID(cm_BPLocation.getC_Location_ID());
		m_BPLocation.setIsPreserveCustomName(cm_BPLocation.isPreserveCustomName());
		m_BPLocation.setPhone(cm_BPLocation.getPhone());
		m_BPLocation.setPhone2(cm_BPLocation.getPhone2());
		m_BPLocation.setFax(cm_BPLocation.getFax());
		m_BPLocation.setIsShipTo(cm_BPLocation.isShipTo());
		m_BPLocation.setIsBillTo(cm_BPLocation.isBillTo());
		m_BPLocation.setIsPayFrom(cm_BPLocation.isPayFrom());
		m_BPLocation.setIsRemitTo(cm_BPLocation.isRemitTo());
		m_BPLocation.setCustomerAddressID(cm_BPLocation.getCustomerAddressID());
		//Set C_SalesRegion_ID
		if(!Util.isEmpty(cm_BPLocation.getJP_SalesRegion_Value()))
		{
			MSalesRegion m_SalesRegion = getMSalesRegion(m_Client.getAD_Client_ID(), cm_BPLocation.getJP_SalesRegion_Value(), trxName);
			if(m_SalesRegion == null)
			{
				if(!cm_BPartner.isIgnore_NMMaster_NotFoundJP())
				{
					msg = Msg.getMsg(ctx, "invalid")
								+ " : " + Msg.getElement(ctx, COLUMNNAME_JP_SalesRegion_Value)
								+ " = " + cm_BPLocation.getJP_SalesRegion_Value();
					throw new Exception(msg);
				}
			}else {
				m_BPLocation.setC_SalesRegion_ID(m_SalesRegion.getC_SalesRegion_ID());
			}
		}
		
		m_BPLocation.saveEx(trxName);
		
		return m_BPLocation;
	}

}
