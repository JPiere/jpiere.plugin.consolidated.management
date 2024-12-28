/******************************************************************************
 * Product: iDempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2012 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software, you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY, without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program, if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
/** Generated Model - DO NOT CHANGE */
package jpiere.plugin.consolidated.management.model;

import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.model.*;
import org.compiere.util.KeyNamePair;

/** Generated Model for JP_IndustryTypeL2
 *  @author iDempiere (generated)
 *  @version Release 11 - $Id$ */
@org.adempiere.base.Model(table="JP_IndustryTypeL2")
public class X_JP_IndustryTypeL2 extends PO implements I_JP_IndustryTypeL2, I_Persistent
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20241228L;

    /** Standard Constructor */
    public X_JP_IndustryTypeL2 (Properties ctx, int JP_IndustryTypeL2_ID, String trxName)
    {
      super (ctx, JP_IndustryTypeL2_ID, trxName);
      /** if (JP_IndustryTypeL2_ID == 0)
        {
			setJP_IndustryTypeL2_ID (0);
			setName (null);
			setValue (null);
        } */
    }

    /** Standard Constructor */
    public X_JP_IndustryTypeL2 (Properties ctx, int JP_IndustryTypeL2_ID, String trxName, String ... virtualColumns)
    {
      super (ctx, JP_IndustryTypeL2_ID, trxName, virtualColumns);
      /** if (JP_IndustryTypeL2_ID == 0)
        {
			setJP_IndustryTypeL2_ID (0);
			setName (null);
			setValue (null);
        } */
    }

    /** Standard Constructor */
    public X_JP_IndustryTypeL2 (Properties ctx, String JP_IndustryTypeL2_UU, String trxName)
    {
      super (ctx, JP_IndustryTypeL2_UU, trxName);
      /** if (JP_IndustryTypeL2_UU == null)
        {
			setJP_IndustryTypeL2_ID (0);
			setName (null);
			setValue (null);
        } */
    }

    /** Standard Constructor */
    public X_JP_IndustryTypeL2 (Properties ctx, String JP_IndustryTypeL2_UU, String trxName, String ... virtualColumns)
    {
      super (ctx, JP_IndustryTypeL2_UU, trxName, virtualColumns);
      /** if (JP_IndustryTypeL2_UU == null)
        {
			setJP_IndustryTypeL2_ID (0);
			setName (null);
			setValue (null);
        } */
    }

    /** Load Constructor */
    public X_JP_IndustryTypeL2 (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 7 - System - Client - Org
      */
    protected int get_AccessLevel()
    {
      return accessLevel.intValue();
    }

    /** Load Meta Data */
    protected POInfo initPO (Properties ctx)
    {
      POInfo poi = POInfo.getPOInfo (ctx, Table_ID, get_TrxName());
      return poi;
    }

    public String toString()
    {
      StringBuilder sb = new StringBuilder ("X_JP_IndustryTypeL2[")
        .append(get_ID()).append(",Name=").append(getName()).append("]");
      return sb.toString();
    }

	/** Set Description.
		@param Description Optional short description of the record
	*/
	public void setDescription (String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	/** Get Description.
		@return Optional short description of the record
	  */
	public String getDescription()
	{
		return (String)get_Value(COLUMNNAME_Description);
	}

	/** Set Industry Type L2.
		@param JP_IndustryTypeL2_ID Industry Type L2
	*/
	public void setJP_IndustryTypeL2_ID (int JP_IndustryTypeL2_ID)
	{
		if (JP_IndustryTypeL2_ID < 1)
			set_ValueNoCheck (COLUMNNAME_JP_IndustryTypeL2_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_JP_IndustryTypeL2_ID, Integer.valueOf(JP_IndustryTypeL2_ID));
	}

	/** Get Industry Type L2.
		@return Industry Type L2	  */
	public int getJP_IndustryTypeL2_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_JP_IndustryTypeL2_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set JP_IndustryTypeL2_UU.
		@param JP_IndustryTypeL2_UU JP_IndustryTypeL2_UU
	*/
	public void setJP_IndustryTypeL2_UU (String JP_IndustryTypeL2_UU)
	{
		set_ValueNoCheck (COLUMNNAME_JP_IndustryTypeL2_UU, JP_IndustryTypeL2_UU);
	}

	/** Get JP_IndustryTypeL2_UU.
		@return JP_IndustryTypeL2_UU	  */
	public String getJP_IndustryTypeL2_UU()
	{
		return (String)get_Value(COLUMNNAME_JP_IndustryTypeL2_UU);
	}

	/** Set Name.
		@param Name Alphanumeric identifier of the entity
	*/
	public void setName (String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Alphanumeric identifier of the entity
	  */
	public String getName()
	{
		return (String)get_Value(COLUMNNAME_Name);
	}

	/** Set Search Key.
		@param Value Search key for the record in the format required - must be unique
	*/
	public void setValue (String Value)
	{
		set_Value (COLUMNNAME_Value, Value);
	}

	/** Get Search Key.
		@return Search key for the record in the format required - must be unique
	  */
	public String getValue()
	{
		return (String)get_Value(COLUMNNAME_Value);
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair()
    {
        return new KeyNamePair(get_ID(), getValue());
    }
}