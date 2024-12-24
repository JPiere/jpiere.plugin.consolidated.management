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
 * JPIERE-0635 Consolidated Corporation type
 *
 *  @author Hideaki Hagiwara
 *
 */
public class MCMCorpType extends X_JP_CM_CorpType {

	private static final long serialVersionUID = 4435214409091029232L;

	public MCMCorpType(Properties ctx, int JP_CM_CorpType_ID, String trxName) 
	{
		super(ctx, JP_CM_CorpType_ID, trxName);
	}

	public MCMCorpType(Properties ctx, int JP_CM_CorpType_ID, String trxName, String... virtualColumns) 
	{
		super(ctx, JP_CM_CorpType_ID, trxName, virtualColumns);
	}

	public MCMCorpType(Properties ctx, String JP_CM_CorpType_UU, String trxName) 
	{
		super(ctx, JP_CM_CorpType_UU, trxName);
	}

	public MCMCorpType(Properties ctx, String JP_CM_CorpType_UU, String trxName, String... virtualColumns) 
	{
		super(ctx, JP_CM_CorpType_UU, trxName, virtualColumns);
	}

	public MCMCorpType(Properties ctx, ResultSet rs, String trxName) 
	{
		super(ctx, rs, trxName);
	}

}
