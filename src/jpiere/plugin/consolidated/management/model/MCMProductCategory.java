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
import java.util.Properties;

/**
 * JPIERE-0629 Consolidated Management - Consolidated Product Master
 *
 *  @author Hideaki Hagiwara
 *
 */
public class MCMProductCategory extends X_JP_CM_ProductCategory {

	private static final long serialVersionUID = 1583656235633656151L;

	public MCMProductCategory(Properties ctx, int JP_CM_ProductCategory_ID, String trxName) 
	{
		super(ctx, JP_CM_ProductCategory_ID, trxName);
	}

	public MCMProductCategory(Properties ctx, int JP_CM_ProductCategory_ID, String trxName, String... virtualColumns) 
	{
		super(ctx, JP_CM_ProductCategory_ID, trxName, virtualColumns);
	}

	public MCMProductCategory(Properties ctx, String JP_CM_ProductCategory_UU, String trxName) 
	{
		super(ctx, JP_CM_ProductCategory_UU, trxName);
	}

	public MCMProductCategory(Properties ctx, String JP_CM_ProductCategory_UU, String trxName, String... virtualColumns) 
	{
		super(ctx, JP_CM_ProductCategory_UU, trxName, virtualColumns);
	}

	public MCMProductCategory(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}

}