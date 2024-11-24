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

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;
import org.compiere.model.*;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;

/** Generated Model for JP_CM_Product
 *  @author iDempiere (generated)
 *  @version Release 11 - $Id$ */
@org.adempiere.base.Model(table="JP_CM_Product")
public class X_JP_CM_Product extends PO implements I_JP_CM_Product, I_Persistent
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20241123L;

    /** Standard Constructor */
    public X_JP_CM_Product (Properties ctx, int JP_CM_Product_ID, String trxName)
    {
      super (ctx, JP_CM_Product_ID, trxName);
      /** if (JP_CM_Product_ID == 0)
        {
			setC_UOM_ID (0);
			setDiscontinued (false);
// N
			setIsAutoProduce (false);
// N
			setIsBOM (false);
// N
			setIsDropShip (false);
// N
			setIsExcludeAutoDelivery (false);
// N
			setIsInvoicePrintDetails (false);
// N
			setIsKanban (false);
// N
			setIsManufactured (false);
// N
			setIsOwnBox (false);
// N
			setIsPhantom (false);
// N
			setIsPickListPrintDetails (false);
// N
			setIsPurchased (true);
// Y
			setIsSelfService (true);
// Y
			setIsSold (true);
// Y
			setIsStocked (true);
// Y
			setIsSummary (false);
// N
			setIsVerified (false);
// N
			setIsWebStoreFeatured (false);
// N
			setJP_CM_ProductCategory_ID (0);
			setJP_CM_Product_ID (0);
			setJP_Product_Category_Value (null);
			setJP_TaxCategory_Name (null);
			setLowLevel (0);
// 0
			setName (null);
			setProductType (null);
// I
			setValue (null);
        } */
    }

    /** Standard Constructor */
    public X_JP_CM_Product (Properties ctx, int JP_CM_Product_ID, String trxName, String ... virtualColumns)
    {
      super (ctx, JP_CM_Product_ID, trxName, virtualColumns);
      /** if (JP_CM_Product_ID == 0)
        {
			setC_UOM_ID (0);
			setDiscontinued (false);
// N
			setIsAutoProduce (false);
// N
			setIsBOM (false);
// N
			setIsDropShip (false);
// N
			setIsExcludeAutoDelivery (false);
// N
			setIsInvoicePrintDetails (false);
// N
			setIsKanban (false);
// N
			setIsManufactured (false);
// N
			setIsOwnBox (false);
// N
			setIsPhantom (false);
// N
			setIsPickListPrintDetails (false);
// N
			setIsPurchased (true);
// Y
			setIsSelfService (true);
// Y
			setIsSold (true);
// Y
			setIsStocked (true);
// Y
			setIsSummary (false);
// N
			setIsVerified (false);
// N
			setIsWebStoreFeatured (false);
// N
			setJP_CM_ProductCategory_ID (0);
			setJP_CM_Product_ID (0);
			setJP_Product_Category_Value (null);
			setJP_TaxCategory_Name (null);
			setLowLevel (0);
// 0
			setName (null);
			setProductType (null);
// I
			setValue (null);
        } */
    }

    /** Standard Constructor */
    public X_JP_CM_Product (Properties ctx, String JP_CM_Product_UU, String trxName)
    {
      super (ctx, JP_CM_Product_UU, trxName);
      /** if (JP_CM_Product_UU == null)
        {
			setC_UOM_ID (0);
			setDiscontinued (false);
// N
			setIsAutoProduce (false);
// N
			setIsBOM (false);
// N
			setIsDropShip (false);
// N
			setIsExcludeAutoDelivery (false);
// N
			setIsInvoicePrintDetails (false);
// N
			setIsKanban (false);
// N
			setIsManufactured (false);
// N
			setIsOwnBox (false);
// N
			setIsPhantom (false);
// N
			setIsPickListPrintDetails (false);
// N
			setIsPurchased (true);
// Y
			setIsSelfService (true);
// Y
			setIsSold (true);
// Y
			setIsStocked (true);
// Y
			setIsSummary (false);
// N
			setIsVerified (false);
// N
			setIsWebStoreFeatured (false);
// N
			setJP_CM_ProductCategory_ID (0);
			setJP_CM_Product_ID (0);
			setJP_Product_Category_Value (null);
			setJP_TaxCategory_Name (null);
			setLowLevel (0);
// 0
			setName (null);
			setProductType (null);
// I
			setValue (null);
        } */
    }

    /** Standard Constructor */
    public X_JP_CM_Product (Properties ctx, String JP_CM_Product_UU, String trxName, String ... virtualColumns)
    {
      super (ctx, JP_CM_Product_UU, trxName, virtualColumns);
      /** if (JP_CM_Product_UU == null)
        {
			setC_UOM_ID (0);
			setDiscontinued (false);
// N
			setIsAutoProduce (false);
// N
			setIsBOM (false);
// N
			setIsDropShip (false);
// N
			setIsExcludeAutoDelivery (false);
// N
			setIsInvoicePrintDetails (false);
// N
			setIsKanban (false);
// N
			setIsManufactured (false);
// N
			setIsOwnBox (false);
// N
			setIsPhantom (false);
// N
			setIsPickListPrintDetails (false);
// N
			setIsPurchased (true);
// Y
			setIsSelfService (true);
// Y
			setIsSold (true);
// Y
			setIsStocked (true);
// Y
			setIsSummary (false);
// N
			setIsVerified (false);
// N
			setIsWebStoreFeatured (false);
// N
			setJP_CM_ProductCategory_ID (0);
			setJP_CM_Product_ID (0);
			setJP_Product_Category_Value (null);
			setJP_TaxCategory_Name (null);
			setLowLevel (0);
// 0
			setName (null);
			setProductType (null);
// I
			setValue (null);
        } */
    }

    /** Load Constructor */
    public X_JP_CM_Product (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 4 - System
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
      StringBuilder sb = new StringBuilder ("X_JP_CM_Product[")
        .append(get_ID()).append(",Name=").append(getName()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_C_UOM getC_UOM() throws RuntimeException
	{
		return (org.compiere.model.I_C_UOM)MTable.get(getCtx(), org.compiere.model.I_C_UOM.Table_ID)
			.getPO(getC_UOM_ID(), get_TrxName());
	}

	/** Set UOM.
		@param C_UOM_ID Unit of Measure
	*/
	public void setC_UOM_ID (int C_UOM_ID)
	{
		if (C_UOM_ID < 1)
			set_Value (COLUMNNAME_C_UOM_ID, null);
		else
			set_Value (COLUMNNAME_C_UOM_ID, Integer.valueOf(C_UOM_ID));
	}

	/** Get UOM.
		@return Unit of Measure
	  */
	public int getC_UOM_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_UOM_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Classification.
		@param Classification Classification for grouping
	*/
	public void setClassification (String Classification)
	{
		set_Value (COLUMNNAME_Classification, Classification);
	}

	/** Get Classification.
		@return Classification for grouping
	  */
	public String getClassification()
	{
		return (String)get_Value(COLUMNNAME_Classification);
	}

	/** Set Customs Tariff Number.
		@param CustomsTariffNumber Customs Tariff Number, usually the HS-Code
	*/
	public void setCustomsTariffNumber (String CustomsTariffNumber)
	{
		set_Value (COLUMNNAME_CustomsTariffNumber, CustomsTariffNumber);
	}

	/** Get Customs Tariff Number.
		@return Customs Tariff Number, usually the HS-Code
	  */
	public String getCustomsTariffNumber()
	{
		return (String)get_Value(COLUMNNAME_CustomsTariffNumber);
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

	/** Set Description URL.
		@param DescriptionURL URL for the description
	*/
	public void setDescriptionURL (String DescriptionURL)
	{
		set_Value (COLUMNNAME_DescriptionURL, DescriptionURL);
	}

	/** Get Description URL.
		@return URL for the description
	  */
	public String getDescriptionURL()
	{
		return (String)get_Value(COLUMNNAME_DescriptionURL);
	}

	/** Set Discontinued.
		@param Discontinued This product is no longer available
	*/
	public void setDiscontinued (boolean Discontinued)
	{
		set_Value (COLUMNNAME_Discontinued, Boolean.valueOf(Discontinued));
	}

	/** Get Discontinued.
		@return This product is no longer available
	  */
	public boolean isDiscontinued()
	{
		Object oo = get_Value(COLUMNNAME_Discontinued);
		if (oo != null)
		{
			 if (oo instanceof Boolean)
				 return ((Boolean)oo).booleanValue();
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Discontinued At.
		@param DiscontinuedAt Discontinued At indicates Date when product was discontinued
	*/
	public void setDiscontinuedAt (Timestamp DiscontinuedAt)
	{
		set_Value (COLUMNNAME_DiscontinuedAt, DiscontinuedAt);
	}

	/** Get Discontinued At.
		@return Discontinued At indicates Date when product was discontinued
	  */
	public Timestamp getDiscontinuedAt()
	{
		return (Timestamp)get_Value(COLUMNNAME_DiscontinuedAt);
	}

	/** Set Document Note.
		@param DocumentNote Additional information for a Document
	*/
	public void setDocumentNote (String DocumentNote)
	{
		set_Value (COLUMNNAME_DocumentNote, DocumentNote);
	}

	/** Get Document Note.
		@return Additional information for a Document
	  */
	public String getDocumentNote()
	{
		return (String)get_Value(COLUMNNAME_DocumentNote);
	}

	/** Set Group1.
		@param Group1 Group1
	*/
	public void setGroup1 (String Group1)
	{
		set_Value (COLUMNNAME_Group1, Group1);
	}

	/** Get Group1.
		@return Group1	  */
	public String getGroup1()
	{
		return (String)get_Value(COLUMNNAME_Group1);
	}

	/** Set Group2.
		@param Group2 Group2
	*/
	public void setGroup2 (String Group2)
	{
		set_Value (COLUMNNAME_Group2, Group2);
	}

	/** Get Group2.
		@return Group2	  */
	public String getGroup2()
	{
		return (String)get_Value(COLUMNNAME_Group2);
	}

	/** Set Guarantee Days.
		@param GuaranteeDays Number of days the product is guaranteed or available
	*/
	public void setGuaranteeDays (int GuaranteeDays)
	{
		set_Value (COLUMNNAME_GuaranteeDays, Integer.valueOf(GuaranteeDays));
	}

	/** Get Guarantee Days.
		@return Number of days the product is guaranteed or available
	  */
	public int getGuaranteeDays()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_GuaranteeDays);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Min Guarantee Days.
		@param GuaranteeDaysMin Minimum number of guarantee days
	*/
	public void setGuaranteeDaysMin (int GuaranteeDaysMin)
	{
		set_Value (COLUMNNAME_GuaranteeDaysMin, Integer.valueOf(GuaranteeDaysMin));
	}

	/** Get Min Guarantee Days.
		@return Minimum number of guarantee days
	  */
	public int getGuaranteeDaysMin()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_GuaranteeDaysMin);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Comment/Help.
		@param Help Comment or Hint
	*/
	public void setHelp (String Help)
	{
		set_Value (COLUMNNAME_Help, Help);
	}

	/** Get Comment/Help.
		@return Comment or Hint
	  */
	public String getHelp()
	{
		return (String)get_Value(COLUMNNAME_Help);
	}

	/** Set Image URL.
		@param ImageURL URL of  image
	*/
	public void setImageURL (String ImageURL)
	{
		set_Value (COLUMNNAME_ImageURL, ImageURL);
	}

	/** Get Image URL.
		@return URL of  image
	  */
	public String getImageURL()
	{
		return (String)get_Value(COLUMNNAME_ImageURL);
	}

	/** Set Auto Produce.
		@param IsAutoProduce Auto create production to fulfill shipment
	*/
	public void setIsAutoProduce (boolean IsAutoProduce)
	{
		set_Value (COLUMNNAME_IsAutoProduce, Boolean.valueOf(IsAutoProduce));
	}

	/** Get Auto Produce.
		@return Auto create production to fulfill shipment
	  */
	public boolean isAutoProduce()
	{
		Object oo = get_Value(COLUMNNAME_IsAutoProduce);
		if (oo != null)
		{
			 if (oo instanceof Boolean)
				 return ((Boolean)oo).booleanValue();
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Bill of Materials.
		@param IsBOM Bill of Materials
	*/
	public void setIsBOM (boolean IsBOM)
	{
		set_Value (COLUMNNAME_IsBOM, Boolean.valueOf(IsBOM));
	}

	/** Get Bill of Materials.
		@return Bill of Materials
	  */
	public boolean isBOM()
	{
		Object oo = get_Value(COLUMNNAME_IsBOM);
		if (oo != null)
		{
			 if (oo instanceof Boolean)
				 return ((Boolean)oo).booleanValue();
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Drop Shipment.
		@param IsDropShip Drop Shipments are sent directly to the Drop Shipment Location
	*/
	public void setIsDropShip (boolean IsDropShip)
	{
		set_Value (COLUMNNAME_IsDropShip, Boolean.valueOf(IsDropShip));
	}

	/** Get Drop Shipment.
		@return Drop Shipments are sent directly to the Drop Shipment Location
	  */
	public boolean isDropShip()
	{
		Object oo = get_Value(COLUMNNAME_IsDropShip);
		if (oo != null)
		{
			 if (oo instanceof Boolean)
				 return ((Boolean)oo).booleanValue();
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Exclude Auto Delivery.
		@param IsExcludeAutoDelivery Exclude from automatic Delivery
	*/
	public void setIsExcludeAutoDelivery (boolean IsExcludeAutoDelivery)
	{
		set_Value (COLUMNNAME_IsExcludeAutoDelivery, Boolean.valueOf(IsExcludeAutoDelivery));
	}

	/** Get Exclude Auto Delivery.
		@return Exclude from automatic Delivery
	  */
	public boolean isExcludeAutoDelivery()
	{
		Object oo = get_Value(COLUMNNAME_IsExcludeAutoDelivery);
		if (oo != null)
		{
			 if (oo instanceof Boolean)
				 return ((Boolean)oo).booleanValue();
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Print detail records on invoice.
		@param IsInvoicePrintDetails Print detail BOM elements on the invoice
	*/
	public void setIsInvoicePrintDetails (boolean IsInvoicePrintDetails)
	{
		set_Value (COLUMNNAME_IsInvoicePrintDetails, Boolean.valueOf(IsInvoicePrintDetails));
	}

	/** Get Print detail records on invoice.
		@return Print detail BOM elements on the invoice
	  */
	public boolean isInvoicePrintDetails()
	{
		Object oo = get_Value(COLUMNNAME_IsInvoicePrintDetails);
		if (oo != null)
		{
			 if (oo instanceof Boolean)
				 return ((Boolean)oo).booleanValue();
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Kanban controlled.
		@param IsKanban This part is Kanban controlled
	*/
	public void setIsKanban (boolean IsKanban)
	{
		set_Value (COLUMNNAME_IsKanban, Boolean.valueOf(IsKanban));
	}

	/** Get Kanban controlled.
		@return This part is Kanban controlled
	  */
	public boolean isKanban()
	{
		Object oo = get_Value(COLUMNNAME_IsKanban);
		if (oo != null)
		{
			 if (oo instanceof Boolean)
				 return ((Boolean)oo).booleanValue();
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Manufactured.
		@param IsManufactured This product is manufactured
	*/
	public void setIsManufactured (boolean IsManufactured)
	{
		set_Value (COLUMNNAME_IsManufactured, Boolean.valueOf(IsManufactured));
	}

	/** Get Manufactured.
		@return This product is manufactured
	  */
	public boolean isManufactured()
	{
		Object oo = get_Value(COLUMNNAME_IsManufactured);
		if (oo != null)
		{
			 if (oo instanceof Boolean)
				 return ((Boolean)oo).booleanValue();
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Own Box.
		@param IsOwnBox Own Box
	*/
	public void setIsOwnBox (boolean IsOwnBox)
	{
		set_Value (COLUMNNAME_IsOwnBox, Boolean.valueOf(IsOwnBox));
	}

	/** Get Own Box.
		@return Own Box	  */
	public boolean isOwnBox()
	{
		Object oo = get_Value(COLUMNNAME_IsOwnBox);
		if (oo != null)
		{
			 if (oo instanceof Boolean)
				 return ((Boolean)oo).booleanValue();
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Phantom.
		@param IsPhantom Phantom Component
	*/
	public void setIsPhantom (boolean IsPhantom)
	{
		set_Value (COLUMNNAME_IsPhantom, Boolean.valueOf(IsPhantom));
	}

	/** Get Phantom.
		@return Phantom Component
	  */
	public boolean isPhantom()
	{
		Object oo = get_Value(COLUMNNAME_IsPhantom);
		if (oo != null)
		{
			 if (oo instanceof Boolean)
				 return ((Boolean)oo).booleanValue();
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Print detail records on pick list.
		@param IsPickListPrintDetails Print detail BOM elements on the pick list
	*/
	public void setIsPickListPrintDetails (boolean IsPickListPrintDetails)
	{
		set_Value (COLUMNNAME_IsPickListPrintDetails, Boolean.valueOf(IsPickListPrintDetails));
	}

	/** Get Print detail records on pick list.
		@return Print detail BOM elements on the pick list
	  */
	public boolean isPickListPrintDetails()
	{
		Object oo = get_Value(COLUMNNAME_IsPickListPrintDetails);
		if (oo != null)
		{
			 if (oo instanceof Boolean)
				 return ((Boolean)oo).booleanValue();
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Purchased.
		@param IsPurchased Organization purchases this product
	*/
	public void setIsPurchased (boolean IsPurchased)
	{
		set_Value (COLUMNNAME_IsPurchased, Boolean.valueOf(IsPurchased));
	}

	/** Get Purchased.
		@return Organization purchases this product
	  */
	public boolean isPurchased()
	{
		Object oo = get_Value(COLUMNNAME_IsPurchased);
		if (oo != null)
		{
			 if (oo instanceof Boolean)
				 return ((Boolean)oo).booleanValue();
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Self-Service.
		@param IsSelfService This is a Self-Service entry or this entry can be changed via Self-Service
	*/
	public void setIsSelfService (boolean IsSelfService)
	{
		set_Value (COLUMNNAME_IsSelfService, Boolean.valueOf(IsSelfService));
	}

	/** Get Self-Service.
		@return This is a Self-Service entry or this entry can be changed via Self-Service
	  */
	public boolean isSelfService()
	{
		Object oo = get_Value(COLUMNNAME_IsSelfService);
		if (oo != null)
		{
			 if (oo instanceof Boolean)
				 return ((Boolean)oo).booleanValue();
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Sold.
		@param IsSold Organization sells this product
	*/
	public void setIsSold (boolean IsSold)
	{
		set_Value (COLUMNNAME_IsSold, Boolean.valueOf(IsSold));
	}

	/** Get Sold.
		@return Organization sells this product
	  */
	public boolean isSold()
	{
		Object oo = get_Value(COLUMNNAME_IsSold);
		if (oo != null)
		{
			 if (oo instanceof Boolean)
				 return ((Boolean)oo).booleanValue();
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Stocked.
		@param IsStocked Organization stocks this product
	*/
	public void setIsStocked (boolean IsStocked)
	{
		set_Value (COLUMNNAME_IsStocked, Boolean.valueOf(IsStocked));
	}

	/** Get Stocked.
		@return Organization stocks this product
	  */
	public boolean isStocked()
	{
		Object oo = get_Value(COLUMNNAME_IsStocked);
		if (oo != null)
		{
			 if (oo instanceof Boolean)
				 return ((Boolean)oo).booleanValue();
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Summary Level.
		@param IsSummary This is a summary entity
	*/
	public void setIsSummary (boolean IsSummary)
	{
		set_Value (COLUMNNAME_IsSummary, Boolean.valueOf(IsSummary));
	}

	/** Get Summary Level.
		@return This is a summary entity
	  */
	public boolean isSummary()
	{
		Object oo = get_Value(COLUMNNAME_IsSummary);
		if (oo != null)
		{
			 if (oo instanceof Boolean)
				 return ((Boolean)oo).booleanValue();
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Verified.
		@param IsVerified The BOM configuration has been verified
	*/
	public void setIsVerified (boolean IsVerified)
	{
		set_ValueNoCheck (COLUMNNAME_IsVerified, Boolean.valueOf(IsVerified));
	}

	/** Get Verified.
		@return The BOM configuration has been verified
	  */
	public boolean isVerified()
	{
		Object oo = get_Value(COLUMNNAME_IsVerified);
		if (oo != null)
		{
			 if (oo instanceof Boolean)
				 return ((Boolean)oo).booleanValue();
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Featured in Web Store.
		@param IsWebStoreFeatured If selected, the product is displayed in the initial or any empty search
	*/
	public void setIsWebStoreFeatured (boolean IsWebStoreFeatured)
	{
		set_Value (COLUMNNAME_IsWebStoreFeatured, Boolean.valueOf(IsWebStoreFeatured));
	}

	/** Get Featured in Web Store.
		@return If selected, the product is displayed in the initial or any empty search
	  */
	public boolean isWebStoreFeatured()
	{
		Object oo = get_Value(COLUMNNAME_IsWebStoreFeatured);
		if (oo != null)
		{
			 if (oo instanceof Boolean)
				 return ((Boolean)oo).booleanValue();
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Attribute Set(Name).
		@param JP_AttributeSet_Name Product Attribute Set
	*/
	public void setJP_AttributeSet_Name (String JP_AttributeSet_Name)
	{
		set_Value (COLUMNNAME_JP_AttributeSet_Name, JP_AttributeSet_Name);
	}

	/** Get Attribute Set(Name).
		@return Product Attribute Set
	  */
	public String getJP_AttributeSet_Name()
	{
		return (String)get_Value(COLUMNNAME_JP_AttributeSet_Name);
	}

	public I_JP_CM_ProductCategory getJP_CM_ProductCategory() throws RuntimeException
	{
		return (I_JP_CM_ProductCategory)MTable.get(getCtx(), I_JP_CM_ProductCategory.Table_ID)
			.getPO(getJP_CM_ProductCategory_ID(), get_TrxName());
	}

	/** Set Consolidated Product Category.
		@param JP_CM_ProductCategory_ID JPIERE-0629:JPPS
	*/
	public void setJP_CM_ProductCategory_ID (int JP_CM_ProductCategory_ID)
	{
		if (JP_CM_ProductCategory_ID < 1)
			set_Value (COLUMNNAME_JP_CM_ProductCategory_ID, null);
		else
			set_Value (COLUMNNAME_JP_CM_ProductCategory_ID, Integer.valueOf(JP_CM_ProductCategory_ID));
	}

	/** Get Consolidated Product Category.
		@return JPIERE-0629:JPPS
	  */
	public int getJP_CM_ProductCategory_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_JP_CM_ProductCategory_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Consolidated Product.
		@param JP_CM_Product_ID Consolidated Product
	*/
	public void setJP_CM_Product_ID (int JP_CM_Product_ID)
	{
		if (JP_CM_Product_ID < 1)
			set_ValueNoCheck (COLUMNNAME_JP_CM_Product_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_JP_CM_Product_ID, Integer.valueOf(JP_CM_Product_ID));
	}

	/** Get Consolidated Product.
		@return Consolidated Product	  */
	public int getJP_CM_Product_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_JP_CM_Product_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Consolidated Product (UU).
		@param JP_CM_Product_UU Consolidated Product (UU)
	*/
	public void setJP_CM_Product_UU (String JP_CM_Product_UU)
	{
		set_Value (COLUMNNAME_JP_CM_Product_UU, JP_CM_Product_UU);
	}

	/** Get Consolidated Product (UU).
		@return Consolidated Product (UU)	  */
	public String getJP_CM_Product_UU()
	{
		return (String)get_Value(COLUMNNAME_JP_CM_Product_UU);
	}

	/** Set Freight Category(Search Key).
		@param JP_FreightCategory_Value Category of the Freight
	*/
	public void setJP_FreightCategory_Value (String JP_FreightCategory_Value)
	{
		set_Value (COLUMNNAME_JP_FreightCategory_Value, JP_FreightCategory_Value);
	}

	/** Get Freight Category(Search Key).
		@return Category of the Freight
	  */
	public String getJP_FreightCategory_Value()
	{
		return (String)get_Value(COLUMNNAME_JP_FreightCategory_Value);
	}

	/** Set Locator(Search Key).
		@param JP_Locator_Value Warehouse Locator
	*/
	public void setJP_Locator_Value (String JP_Locator_Value)
	{
		set_Value (COLUMNNAME_JP_Locator_Value, JP_Locator_Value);
	}

	/** Get Locator(Search Key).
		@return Warehouse Locator
	  */
	public String getJP_Locator_Value()
	{
		return (String)get_Value(COLUMNNAME_JP_Locator_Value);
	}

	/** Set Mail Template(Name).
		@param JP_MailText_Name Text templates for mailings
	*/
	public void setJP_MailText_Name (String JP_MailText_Name)
	{
		set_Value (COLUMNNAME_JP_MailText_Name, JP_MailText_Name);
	}

	/** Get Mail Template(Name).
		@return Text templates for mailings
	  */
	public String getJP_MailText_Name()
	{
		return (String)get_Value(COLUMNNAME_JP_MailText_Name);
	}

	/** Set Part Type(Name).
		@param JP_PartType_Name Part Type(Name)
	*/
	public void setJP_PartType_Name (String JP_PartType_Name)
	{
		set_Value (COLUMNNAME_JP_PartType_Name, JP_PartType_Name);
	}

	/** Get Part Type(Name).
		@return Part Type(Name)	  */
	public String getJP_PartType_Name()
	{
		return (String)get_Value(COLUMNNAME_JP_PartType_Name);
	}

	/** Set Product Category(Value).
		@param JP_Product_Category_Value Category of a Product
	*/
	public void setJP_Product_Category_Value (String JP_Product_Category_Value)
	{
		set_Value (COLUMNNAME_JP_Product_Category_Value, JP_Product_Category_Value);
	}

	/** Get Product Category(Value).
		@return Category of a Product
	  */
	public String getJP_Product_Category_Value()
	{
		return (String)get_Value(COLUMNNAME_JP_Product_Category_Value);
	}

	/** Set Revenue Recognition(Name).
		@param JP_RevenueRecognition_Name Method for recording revenue
	*/
	public void setJP_RevenueRecognition_Name (String JP_RevenueRecognition_Name)
	{
		set_Value (COLUMNNAME_JP_RevenueRecognition_Name, JP_RevenueRecognition_Name);
	}

	/** Get Revenue Recognition(Name).
		@return Method for recording revenue
	  */
	public String getJP_RevenueRecognition_Name()
	{
		return (String)get_Value(COLUMNNAME_JP_RevenueRecognition_Name);
	}

	/** Set Tax Category(name).
		@param JP_TaxCategory_Name Tax Category
	*/
	public void setJP_TaxCategory_Name (String JP_TaxCategory_Name)
	{
		set_Value (COLUMNNAME_JP_TaxCategory_Name, JP_TaxCategory_Name);
	}

	/** Get Tax Category(name).
		@return Tax Category
	  */
	public String getJP_TaxCategory_Name()
	{
		return (String)get_Value(COLUMNNAME_JP_TaxCategory_Name);
	}

	/** Set Low Level.
		@param LowLevel The Low Level is used to calculate the material plan and determines if a net requirement should be exploited
	*/
	public void setLowLevel (int LowLevel)
	{
		set_Value (COLUMNNAME_LowLevel, Integer.valueOf(LowLevel));
	}

	/** Get Low Level.
		@return The Low Level is used to calculate the material plan and determines if a net requirement should be exploited
	  */
	public int getLowLevel()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_LowLevel);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Process Now.
		@param Processing Process Now
	*/
	public void setProcessing (boolean Processing)
	{
		set_Value (COLUMNNAME_Processing, Boolean.valueOf(Processing));
	}

	/** Get Process Now.
		@return Process Now	  */
	public boolean isProcessing()
	{
		Object oo = get_Value(COLUMNNAME_Processing);
		if (oo != null)
		{
			 if (oo instanceof Boolean)
				 return ((Boolean)oo).booleanValue();
			return "Y".equals(oo);
		}
		return false;
	}

	/** ProductType AD_Reference_ID=270 */
	public static final int PRODUCTTYPE_AD_Reference_ID=270;
	/** Asset = A */
	public static final String PRODUCTTYPE_Asset = "A";
	/** Expense type = E */
	public static final String PRODUCTTYPE_ExpenseType = "E";
	/** Item = I */
	public static final String PRODUCTTYPE_Item = "I";
	/** Online = O */
	public static final String PRODUCTTYPE_Online = "O";
	/** Resource = R */
	public static final String PRODUCTTYPE_Resource = "R";
	/** Service = S */
	public static final String PRODUCTTYPE_Service = "S";
	/** Set Product Type.
		@param ProductType Type of product
	*/
	public void setProductType (String ProductType)
	{

		set_Value (COLUMNNAME_ProductType, ProductType);
	}

	/** Get Product Type.
		@return Type of product
	  */
	public String getProductType()
	{
		return (String)get_Value(COLUMNNAME_ProductType);
	}

	/** Set SKU.
		@param SKU Stock Keeping Unit
	*/
	public void setSKU (String SKU)
	{
		set_Value (COLUMNNAME_SKU, SKU);
	}

	/** Get SKU.
		@return Stock Keeping Unit
	  */
	public String getSKU()
	{
		return (String)get_Value(COLUMNNAME_SKU);
	}

	/** Set Shelf Depth.
		@param ShelfDepth Shelf depth required
	*/
	public void setShelfDepth (int ShelfDepth)
	{
		set_Value (COLUMNNAME_ShelfDepth, Integer.valueOf(ShelfDepth));
	}

	/** Get Shelf Depth.
		@return Shelf depth required
	  */
	public int getShelfDepth()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ShelfDepth);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Shelf Height.
		@param ShelfHeight Shelf height required
	*/
	public void setShelfHeight (BigDecimal ShelfHeight)
	{
		set_Value (COLUMNNAME_ShelfHeight, ShelfHeight);
	}

	/** Get Shelf Height.
		@return Shelf height required
	  */
	public BigDecimal getShelfHeight()
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ShelfHeight);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Shelf Width.
		@param ShelfWidth Shelf width required
	*/
	public void setShelfWidth (int ShelfWidth)
	{
		set_Value (COLUMNNAME_ShelfWidth, Integer.valueOf(ShelfWidth));
	}

	/** Get Shelf Width.
		@return Shelf width required
	  */
	public int getShelfWidth()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ShelfWidth);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UPC/EAN.
		@param UPC Bar Code (Universal Product Code or its superset European Article Number)
	*/
	public void setUPC (String UPC)
	{
		set_Value (COLUMNNAME_UPC, UPC);
	}

	/** Get UPC/EAN.
		@return Bar Code (Universal Product Code or its superset European Article Number)
	  */
	public String getUPC()
	{
		return (String)get_Value(COLUMNNAME_UPC);
	}

	/** Set Units Per Pack.
		@param UnitsPerPack The Units Per Pack indicates the no of units of a product packed together.
	*/
	public void setUnitsPerPack (int UnitsPerPack)
	{
		set_Value (COLUMNNAME_UnitsPerPack, Integer.valueOf(UnitsPerPack));
	}

	/** Get Units Per Pack.
		@return The Units Per Pack indicates the no of units of a product packed together.
	  */
	public int getUnitsPerPack()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UnitsPerPack);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Units Per Pallet.
		@param UnitsPerPallet Units Per Pallet
	*/
	public void setUnitsPerPallet (BigDecimal UnitsPerPallet)
	{
		set_Value (COLUMNNAME_UnitsPerPallet, UnitsPerPallet);
	}

	/** Get Units Per Pallet.
		@return Units Per Pallet
	  */
	public BigDecimal getUnitsPerPallet()
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_UnitsPerPallet);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	/** Set Version No.
		@param VersionNo Version Number
	*/
	public void setVersionNo (String VersionNo)
	{
		set_Value (COLUMNNAME_VersionNo, VersionNo);
	}

	/** Get Version No.
		@return Version Number
	  */
	public String getVersionNo()
	{
		return (String)get_Value(COLUMNNAME_VersionNo);
	}

	/** Set Volume.
		@param Volume Volume of a product
	*/
	public void setVolume (BigDecimal Volume)
	{
		set_Value (COLUMNNAME_Volume, Volume);
	}

	/** Get Volume.
		@return Volume of a product
	  */
	public BigDecimal getVolume()
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Volume);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Weight.
		@param Weight Weight of a product
	*/
	public void setWeight (BigDecimal Weight)
	{
		set_Value (COLUMNNAME_Weight, Weight);
	}

	/** Get Weight.
		@return Weight of a product
	  */
	public BigDecimal getWeight()
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Weight);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}
}