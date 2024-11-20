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
public class MCMProductCategoryL1 extends X_JP_CM_ProductCategoryL1 {

	private static final long serialVersionUID = -7710124433343751708L;

	public MCMProductCategoryL1(Properties ctx, int JP_CM_ProductCategoryL1_ID, String trxName) 
	{
		super(ctx, JP_CM_ProductCategoryL1_ID, trxName);
	}

	public MCMProductCategoryL1(Properties ctx, int JP_CM_ProductCategoryL1_ID, String trxName, String... virtualColumns) 
	{
		super(ctx, JP_CM_ProductCategoryL1_ID, trxName, virtualColumns);
	}

	public MCMProductCategoryL1(Properties ctx, String JP_CM_ProductCategoryL1_UU, String trxName) 
	{
		super(ctx, JP_CM_ProductCategoryL1_UU, trxName);
	}

	public MCMProductCategoryL1(Properties ctx, String JP_CM_ProductCategoryL1_UU, String trxName, String... virtualColumns) 
	{
		super(ctx, JP_CM_ProductCategoryL1_UU, trxName, virtualColumns);
	}

	public MCMProductCategoryL1(Properties ctx, ResultSet rs, String trxName) 
	{
		super(ctx, rs, trxName);
	}

}
