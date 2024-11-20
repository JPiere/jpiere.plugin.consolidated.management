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
package jpiere.plugin.consolidated.management.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import org.compiere.model.*;
import org.compiere.util.KeyNamePair;

/** Generated Interface for JP_CM_ProductCategoryL1
 *  @author iDempiere (generated) 
 *  @version Release 11
 */
@SuppressWarnings("all")
public interface I_JP_CM_ProductCategoryL1 
{

    /** TableName=JP_CM_ProductCategoryL1 */
    public static final String Table_Name = "JP_CM_ProductCategoryL1";

    /** AD_Table_ID=1000324 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 4 - System 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(4);

    /** Load Meta Data */

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/** Get Tenant.
	  * Tenant for this installation.
	  */
	public int getAD_Client_ID();

    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/** Set Organization.
	  * Organizational entity within tenant
	  */
	public void setAD_Org_ID (int AD_Org_ID);

	/** Get Organization.
	  * Organizational entity within tenant
	  */
	public int getAD_Org_ID();

    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/** Get Created.
	  * Date this record was created
	  */
	public Timestamp getCreated();

    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/** Get Created By.
	  * User who created this records
	  */
	public int getCreatedBy();

    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/** Set Description.
	  * Optional short description of the record
	  */
	public void setDescription (String Description);

	/** Get Description.
	  * Optional short description of the record
	  */
	public String getDescription();

    /** Column name Help */
    public static final String COLUMNNAME_Help = "Help";

	/** Set Comment/Help.
	  * Comment or Hint
	  */
	public void setHelp (String Help);

	/** Get Comment/Help.
	  * Comment or Hint
	  */
	public String getHelp();

    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/** Set Active.
	  * The record is active in the system
	  */
	public void setIsActive (boolean IsActive);

	/** Get Active.
	  * The record is active in the system
	  */
	public boolean isActive();

    /** Column name JP_CM_ProductCategoryL1_ID */
    public static final String COLUMNNAME_JP_CM_ProductCategoryL1_ID = "JP_CM_ProductCategoryL1_ID";

	/** Set Consolidated Product Category Level1.
	  * JPIERE-0629:JPPS
	  */
	public void setJP_CM_ProductCategoryL1_ID (int JP_CM_ProductCategoryL1_ID);

	/** Get Consolidated Product Category Level1.
	  * JPIERE-0629:JPPS
	  */
	public int getJP_CM_ProductCategoryL1_ID();

    /** Column name JP_CM_ProductCategoryL1_UU */
    public static final String COLUMNNAME_JP_CM_ProductCategoryL1_UU = "JP_CM_ProductCategoryL1_UU";

	/** Set Consolidated Product Category Level1(UU)	  */
	public void setJP_CM_ProductCategoryL1_UU (String JP_CM_ProductCategoryL1_UU);

	/** Get Consolidated Product Category Level1(UU)	  */
	public String getJP_CM_ProductCategoryL1_UU();

    /** Column name JP_CM_ProductCategoryL2_ID */
    public static final String COLUMNNAME_JP_CM_ProductCategoryL2_ID = "JP_CM_ProductCategoryL2_ID";

	/** Set Consolidated Product Category Level2.
	  * JPIERE-0629:JPPS
	  */
	public void setJP_CM_ProductCategoryL2_ID (int JP_CM_ProductCategoryL2_ID);

	/** Get Consolidated Product Category Level2.
	  * JPIERE-0629:JPPS
	  */
	public int getJP_CM_ProductCategoryL2_ID();

	public I_JP_CM_ProductCategoryL2 getJP_CM_ProductCategoryL2() throws RuntimeException;

    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/** Set Name.
	  * Alphanumeric identifier of the entity
	  */
	public void setName (String Name);

	/** Get Name.
	  * Alphanumeric identifier of the entity
	  */
	public String getName();

    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/** Get Updated.
	  * Date this record was updated
	  */
	public Timestamp getUpdated();

    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/** Get Updated By.
	  * User who updated this records
	  */
	public int getUpdatedBy();

    /** Column name Value */
    public static final String COLUMNNAME_Value = "Value";

	/** Set Search Key.
	  * Search key for the record in the format required - must be unique
	  */
	public void setValue (String Value);

	/** Get Search Key.
	  * Search key for the record in the format required - must be unique
	  */
	public String getValue();
}
