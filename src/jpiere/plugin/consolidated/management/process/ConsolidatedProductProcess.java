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

import org.compiere.model.MClient;
import org.compiere.model.MProcessPara;
import org.compiere.model.MProduct;
import org.compiere.model.PO;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.Msg;
import org.compiere.util.Util;

import jpiere.plugin.consolidated.management.model.MCMProduct;

/**
 * JPIERE-0629 Consolidated Management - Consolidated Product Process
 *
 *  @author Hideaki Hagiwara
 *
 */
public class ConsolidatedProductProcess extends SvrProcess {

	private String p_AD_AllClients_V_IDs = null;
	private int p_JP_CM_ProductCategory_ID = 0;
			
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
			else if(name.equals("JP_CM_ProductCategory_ID"))
				p_JP_CM_ProductCategory_ID = para[i].getParameterAsInt();
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
		int[] JP_CM_Product_IDs = null;
		if(getRecord_ID() > 0)// 1 record only.
		{
			JP_CM_Product_IDs = new int[1];
			JP_CM_Product_IDs[0] =getRecord_ID();
			isSuccessLog = true;
			
		}else {
			
			//Selected from Info Window.
			JP_CM_Product_IDs = PO.getAllIDs("T_Selection", " AD_Pinstance_ID="+getAD_PInstance_ID()+" AND T_Selection_ID >= 1000000", get_TrxName());
			
			//Kicked from Process.
			if(JP_CM_Product_IDs.length == 0)
			{
				if(p_JP_CM_ProductCategory_ID == 0)
					JP_CM_Product_IDs = PO.getAllIDs(MCMProduct.Table_Name, " AD_Client_ID = 0 AND JP_CM_Product_ID >= 1000000 AND IsActive='Y' ", get_TrxName());
				else
					JP_CM_Product_IDs = PO.getAllIDs(MCMProduct.Table_Name, " AD_Client_ID = 0 AND JP_CM_ProductCategory_ID=" + p_JP_CM_ProductCategory_ID
																							+ "　AND JP_CM_Product_ID >= 1000000 AND IsActive='Y' ", get_TrxName());
			}
		}
		
		int counter = 0;
		int register = 0;
		int Update = 0;
		int failure = 0;
		int skip = 0;
		
		MCMProduct cm_Product = null;
		MClient m_Client = null;
		MProduct m_Product = null;
		for(int JP_CM_Product_ID : JP_CM_Product_IDs)
		{
			if(JP_CM_Product_ID < 1000000) //If the ID is less than 1000000, it is not target.
				continue;
			
			cm_Product = new MCMProduct(getCtx(), JP_CM_Product_ID, get_TrxName());	
			if(!cm_Product.isActive())
				continue;
			
			if(MCMProduct.PRODUCTTYPE_Asset.equals(cm_Product.getProductType()))//Not supported Asset
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
					processUI.statusUpdate(counter + " : " + cm_Product.getValue());
				
				msg = null;
				m_Product = MCMProduct.getMProduct(AD_Client_ID, cm_Product.getValue(), get_TrxName());
				if(m_Product == null)//New
				{
					try 
					{
						if(MCMProduct.PRODUCTTYPE_Resource.equals(cm_Product.getProductType()))
						{
							m_Product = MCMProduct.createResource(getCtx(), m_Client, cm_Product, get_TrxName());
							
						}else if(MCMProduct.PRODUCTTYPE_ExpenseType.equals(cm_Product.getProductType())) {
							
							m_Product = MCMProduct.createExpense(getCtx(), m_Client, cm_Product, get_TrxName());
							
						}else if(MCMProduct.PRODUCTTYPE_Item.equals(cm_Product.getProductType())
								|| MCMProduct.PRODUCTTYPE_Service.equals(cm_Product.getProductType()) ){
							
							m_Product = MCMProduct.createMProduct(getCtx(), m_Client, cm_Product, get_TrxName());
							
						}else {
							continue;
						}
					
					}catch (Exception e) {
						msg = e.getMessage();
					}
					
					if(m_Product == null)
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
						msg = cm_Product.getValue() + " : " + m_Client.getName() + " : " + (isSuccess? Msg.getMsg(getCtx(), "Register") : Msg.getMsg(getCtx(), "JP_CouldNotRegister")+ " : " + msg);
						addLog(msg);
					}
					
				}else { //Update
				
					try 
					{
						if(m_Product.get_ValueAsInt(MCMProduct.COLUMNNAME_JP_CM_Product_ID) > 0)
						{
							skip++;
							if(isSuccessLog)
							{
								msg = cm_Product.getValue() + " : " + m_Client.getName() + " : " + Msg.getMsg(getCtx(), "JP_AlreadyRegistered2"); //Already registered
								addLog(msg);
							}
						
						}else {
						
							m_Product.set_ValueNoCheck(MCMProduct.COLUMNNAME_JP_CM_Product_ID, cm_Product.getJP_CM_Product_ID());
							m_Product.saveEx(get_TrxName());
							commitEx();
							
							Update++;
							if(isSuccessLog)
							{
								msg = cm_Product.getValue() + " : " + m_Client.getName() + " : " + Msg.getMsg(getCtx(), "Update");
								addLog(msg);
							}
						}
						
					}catch (Exception e) {
						
						failure++;
						msg = cm_Product.getValue() + " : " + m_Client.getName() + " : " + e.toString();
						addLog(msg);
						
					}
				}				
			}//for AD_Client_IDs			
		}//for JP_CM_Product_IDs
		
		return Msg.getMsg(getCtx(), "Register") + " = " + register 
						+ " , " + Msg.getMsg(getCtx(), "Update") + " = " + Update
						+ " , " + Msg.getMsg(getCtx(), "JP_Failure") + " = " + failure
						+ " , " + Msg.getMsg(getCtx(), "JP_AlreadyRegistered2") + " = " + skip;
	}

}
