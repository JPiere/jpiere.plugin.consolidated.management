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

import org.compiere.model.MBPartner;
import org.compiere.model.MClient;
import org.compiere.model.MProcessPara;
import org.compiere.model.PO;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.Msg;
import org.compiere.util.Util;

import jpiere.plugin.consolidated.management.model.MCMBPartner;
import jpiere.plugin.consolidated.management.model.MCMBPartnerLocation;

/**
 * JPIERE-0636 Consolidated Management - Consolidated Business Partner Process
 *
 *  @author Hideaki Hagiwara
 *
 */
public class ConsolidatedBPartnerProcess extends SvrProcess {

	private String p_AD_AllClients_V_IDs = null;
			
	@Override
	protected void prepare() 
	{
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			else if (name.equals("AD_AllClients_V_ID"))
				p_AD_AllClients_V_IDs = para[i].getParameterAsString();
			else
				MProcessPara.validateUnknownParameter(getProcessInfo().getAD_Process_ID(), para[i]);
		}
	}

	@Override
	protected String doIt() throws Exception 
	{
		String msg = null;
		boolean isSuccessLog = false;
		boolean isSuccess = false;
		
		//Get the tenant to register
		int[] AD_Client_IDs = null;
		if(Util.isEmpty(p_AD_AllClients_V_IDs))
		{
			AD_Client_IDs = PO.getAllIDs(MClient.Table_Name, " AD_Client_ID > 0 AND IsActive='Y' ", get_TrxName());
		}else {
			String[] stringIDs = p_AD_AllClients_V_IDs.split(",");
			AD_Client_IDs = new int[stringIDs.length];
			for (int i = 0; i < AD_Client_IDs.length; i++)
				AD_Client_IDs[i] = Integer.parseInt(stringIDs[i]);
		}
		
		//Get the Consolidated Product to register
		int[] JP_CM_BPartner_IDs = null;
		if(getRecord_ID() > 0)// 1 record only.
		{
			JP_CM_BPartner_IDs = new int[1];
			JP_CM_BPartner_IDs[0] =getRecord_ID();
			isSuccessLog = true;
			
		}else {
			
			//Selected from Info Window.
			JP_CM_BPartner_IDs = PO.getAllIDs("T_Selection", " AD_Pinstance_ID="+getAD_PInstance_ID()+" AND T_Selection_ID >= 1000000", get_TrxName());
			
			//Kicked from Process.
			if(JP_CM_BPartner_IDs.length == 0)
			{
				JP_CM_BPartner_IDs = PO.getAllIDs(MCMBPartner.Table_Name, " AD_Client_ID = 0 AND JP_CM_BPartner_ID >= 1000000 AND IsActive='Y' ", get_TrxName());
			}
		}
		
		int counter = 0;
		int register = 0;
		int Update = 0;
		int failure = 0;
		int skip = 0;
		
		MCMBPartner cm_BPartner = null;
		MClient m_Client = null;
		MBPartner m_BPartner = null;
		for(int JP_CM_BPartner_ID : JP_CM_BPartner_IDs)
		{
			if(JP_CM_BPartner_ID < 1000000) //If the ID is less than 1000000, it is not target.
				continue;
			
			cm_BPartner = new MCMBPartner(getCtx(), JP_CM_BPartner_ID, get_TrxName());	
			if(!cm_BPartner.isActive())
				continue;
			
			for(int AD_Client_ID : AD_Client_IDs)
			{
				m_Client = MClient.get(AD_Client_ID);
				if(m_Client.getAD_Client_ID() == 0)
					continue;
				if(!m_Client.isActive())
					continue;
				
				counter++;
				if(processUI != null)
					processUI.statusUpdate(counter + " : " + cm_BPartner.getValue());
				
				msg = null;
				m_BPartner = MCMBPartner.getMBPartner(AD_Client_ID, cm_BPartner.getValue(), get_TrxName());
				if(m_BPartner == null)//New
				{
					try 
					{
						m_BPartner = MCMBPartner.createMBPartner(getCtx(), m_Client, cm_BPartner, get_TrxName());
						
					}catch (Exception e) {
						msg = e.getMessage();
					}
					
					if(m_BPartner == null)
					{
						isSuccess = false;
						failure++;
					}else {
						isSuccess = true;
						register++;
						commitEx();
					}
					
					if(isSuccessLog || !isSuccess)
					{
						msg = cm_BPartner.getValue() + " : " + m_Client.getName() + " : " + (isSuccess? Msg.getMsg(getCtx(), "Register") : Msg.getMsg(getCtx(), "JP_CouldNotRegister")+ " : " + msg);
						addLog(msg);
					}
					
				}else { //Update
				
					try 
					{
						if(m_BPartner.get_ValueAsInt(MCMBPartner.COLUMNNAME_JP_CM_BPartner_ID) > 0)
						{
							skip++;
							if(isSuccessLog)
							{
								msg = cm_BPartner.getValue() + " : " + m_Client.getName() + " : " + Msg.getMsg(getCtx(), "JP_AlreadyRegistered2"); //Already registered
								addLog(msg);
							}
						
						}else {
						
							m_BPartner.set_ValueNoCheck(MCMBPartner.COLUMNNAME_JP_CM_BPartner_ID, cm_BPartner.getJP_CM_BPartner_ID());
							m_BPartner.saveEx(get_TrxName());
							commitEx();
							
							Update++;
							if(isSuccessLog)
							{
								msg = cm_BPartner.getValue() + " : " + m_Client.getName() + " : " + Msg.getMsg(getCtx(), "Update");
								addLog(msg);
							}
						}
						
						MCMBPartnerLocation[] m_CMBPartners = cm_BPartner.getMCMBPartnerLocations();
						for(MCMBPartnerLocation location : m_CMBPartners)
						{
							try {
								MCMBPartnerLocation.createMBPartnerLocation(getCtx(), m_Client, cm_BPartner, location, m_BPartner, false, get_TrxName());
							}catch (Exception e) {
								;//Nothing to do
							}
						}
						
					}catch (Exception e) {
						
						failure++;
						msg = cm_BPartner.getValue() + " : " + m_Client.getName() + " : " + e.toString();
						addLog(msg);
						
					}
				}				
			}//for AD_Client_IDs			
		}//for JP_CM_BPartner_IDs
		
		return Msg.getMsg(getCtx(), "Register") + " = " + register 
						+ " , " + Msg.getMsg(getCtx(), "Update") + " = " + Update
						+ " , " + Msg.getMsg(getCtx(), "JP_Failure") + " = " + failure
						+ " , " + Msg.getMsg(getCtx(), "JP_AlreadyRegistered2") + " = " + skip;
	}

}
