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

/** Generated Interface for JP_CM_Product
 *  @author iDempiere (generated) 
 *  @version Release 11
 */
@SuppressWarnings("all")
public interface I_JP_CM_Product 
{

    /** TableName=JP_CM_Product */
    public static final String Table_Name = "JP_CM_Product";

    /** AD_Table_ID=1000326 */
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

    /** Column name BPartnerValue */
    public static final String COLUMNNAME_BPartnerValue = "BPartnerValue";

	/** Set Business Partner Key.
	  * Key of the Business Partner
	  */
	public void setBPartnerValue (String BPartnerValue);

	/** Get Business Partner Key.
	  * Key of the Business Partner
	  */
	public String getBPartnerValue();

    /** Column name C_Currency_ID */
    public static final String COLUMNNAME_C_Currency_ID = "C_Currency_ID";

	/** Set Currency.
	  * The Currency for this record
	  */
	public void setC_Currency_ID (int C_Currency_ID);

	/** Get Currency.
	  * The Currency for this record
	  */
	public int getC_Currency_ID();

	public org.compiere.model.I_C_Currency getC_Currency() throws RuntimeException;

    /** Column name C_UOM_ID */
    public static final String COLUMNNAME_C_UOM_ID = "C_UOM_ID";

	/** Set UOM.
	  * Unit of Measure
	  */
	public void setC_UOM_ID (int C_UOM_ID);

	/** Get UOM.
	  * Unit of Measure
	  */
	public int getC_UOM_ID();

	public org.compiere.model.I_C_UOM getC_UOM() throws RuntimeException;

    /** Column name Classification */
    public static final String COLUMNNAME_Classification = "Classification";

	/** Set Classification.
	  * Classification for grouping
	  */
	public void setClassification (String Classification);

	/** Get Classification.
	  * Classification for grouping
	  */
	public String getClassification();

    /** Column name CostPerOrder */
    public static final String COLUMNNAME_CostPerOrder = "CostPerOrder";

	/** Set Cost per Order.
	  * Fixed Cost Per Order
	  */
	public void setCostPerOrder (BigDecimal CostPerOrder);

	/** Get Cost per Order.
	  * Fixed Cost Per Order
	  */
	public BigDecimal getCostPerOrder();

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

    /** Column name CustomsTariffNumber */
    public static final String COLUMNNAME_CustomsTariffNumber = "CustomsTariffNumber";

	/** Set Customs Tariff Number.
	  * Customs Tariff Number, usually the HS-Code
	  */
	public void setCustomsTariffNumber (String CustomsTariffNumber);

	/** Get Customs Tariff Number.
	  * Customs Tariff Number, usually the HS-Code
	  */
	public String getCustomsTariffNumber();

    /** Column name DeliveryTime_Promised */
    public static final String COLUMNNAME_DeliveryTime_Promised = "DeliveryTime_Promised";

	/** Set Promised Delivery Time.
	  * Promised days between order and delivery
	  */
	public void setDeliveryTime_Promised (int DeliveryTime_Promised);

	/** Get Promised Delivery Time.
	  * Promised days between order and delivery
	  */
	public int getDeliveryTime_Promised();

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

    /** Column name DescriptionURL */
    public static final String COLUMNNAME_DescriptionURL = "DescriptionURL";

	/** Set Description URL.
	  * URL for the description
	  */
	public void setDescriptionURL (String DescriptionURL);

	/** Get Description URL.
	  * URL for the description
	  */
	public String getDescriptionURL();

    /** Column name Discontinued */
    public static final String COLUMNNAME_Discontinued = "Discontinued";

	/** Set Discontinued.
	  * This product is no longer available
	  */
	public void setDiscontinued (boolean Discontinued);

	/** Get Discontinued.
	  * This product is no longer available
	  */
	public boolean isDiscontinued();

    /** Column name DiscontinuedAt */
    public static final String COLUMNNAME_DiscontinuedAt = "DiscontinuedAt";

	/** Set Discontinued At.
	  * Discontinued At indicates Date when product was discontinued
	  */
	public void setDiscontinuedAt (Timestamp DiscontinuedAt);

	/** Get Discontinued At.
	  * Discontinued At indicates Date when product was discontinued
	  */
	public Timestamp getDiscontinuedAt();

    /** Column name DocumentNote */
    public static final String COLUMNNAME_DocumentNote = "DocumentNote";

	/** Set Document Note.
	  * Additional information for a Document
	  */
	public void setDocumentNote (String DocumentNote);

	/** Get Document Note.
	  * Additional information for a Document
	  */
	public String getDocumentNote();

    /** Column name Group1 */
    public static final String COLUMNNAME_Group1 = "Group1";

	/** Set Group1	  */
	public void setGroup1 (String Group1);

	/** Get Group1	  */
	public String getGroup1();

    /** Column name Group2 */
    public static final String COLUMNNAME_Group2 = "Group2";

	/** Set Group2	  */
	public void setGroup2 (String Group2);

	/** Get Group2	  */
	public String getGroup2();

    /** Column name GuaranteeDays */
    public static final String COLUMNNAME_GuaranteeDays = "GuaranteeDays";

	/** Set Guarantee Days.
	  * Number of days the product is guaranteed or available
	  */
	public void setGuaranteeDays (int GuaranteeDays);

	/** Get Guarantee Days.
	  * Number of days the product is guaranteed or available
	  */
	public int getGuaranteeDays();

    /** Column name GuaranteeDaysMin */
    public static final String COLUMNNAME_GuaranteeDaysMin = "GuaranteeDaysMin";

	/** Set Min Guarantee Days.
	  * Minimum number of guarantee days
	  */
	public void setGuaranteeDaysMin (int GuaranteeDaysMin);

	/** Get Min Guarantee Days.
	  * Minimum number of guarantee days
	  */
	public int getGuaranteeDaysMin();

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

    /** Column name ImageURL */
    public static final String COLUMNNAME_ImageURL = "ImageURL";

	/** Set Image URL.
	  * URL of  image
	  */
	public void setImageURL (String ImageURL);

	/** Get Image URL.
	  * URL of  image
	  */
	public String getImageURL();

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

    /** Column name IsAutoProduce */
    public static final String COLUMNNAME_IsAutoProduce = "IsAutoProduce";

	/** Set Auto Produce.
	  * Auto create production to fulfill shipment
	  */
	public void setIsAutoProduce (boolean IsAutoProduce);

	/** Get Auto Produce.
	  * Auto create production to fulfill shipment
	  */
	public boolean isAutoProduce();

    /** Column name IsBOM */
    public static final String COLUMNNAME_IsBOM = "IsBOM";

	/** Set Bill of Materials.
	  * Bill of Materials
	  */
	public void setIsBOM (boolean IsBOM);

	/** Get Bill of Materials.
	  * Bill of Materials
	  */
	public boolean isBOM();

    /** Column name IsCurrentVendor */
    public static final String COLUMNNAME_IsCurrentVendor = "IsCurrentVendor";

	/** Set Current vendor.
	  * Use this Vendor for pricing and stock replenishment
	  */
	public void setIsCurrentVendor (boolean IsCurrentVendor);

	/** Get Current vendor.
	  * Use this Vendor for pricing and stock replenishment
	  */
	public boolean isCurrentVendor();

    /** Column name IsDropShip */
    public static final String COLUMNNAME_IsDropShip = "IsDropShip";

	/** Set Drop Shipment.
	  * Drop Shipments are sent directly to the Drop Shipment Location
	  */
	public void setIsDropShip (boolean IsDropShip);

	/** Get Drop Shipment.
	  * Drop Shipments are sent directly to the Drop Shipment Location
	  */
	public boolean isDropShip();

    /** Column name IsExcludeAutoDelivery */
    public static final String COLUMNNAME_IsExcludeAutoDelivery = "IsExcludeAutoDelivery";

	/** Set Exclude Auto Delivery.
	  * Exclude from automatic Delivery
	  */
	public void setIsExcludeAutoDelivery (boolean IsExcludeAutoDelivery);

	/** Get Exclude Auto Delivery.
	  * Exclude from automatic Delivery
	  */
	public boolean isExcludeAutoDelivery();

    /** Column name IsIgnore_NMMaster_NotFoundJP */
    public static final String COLUMNNAME_IsIgnore_NMMaster_NotFoundJP = "IsIgnore_NMMaster_NotFoundJP";

	/** Set Ignore non-mandatory masters if not found	  */
	public void setIsIgnore_NMMaster_NotFoundJP (boolean IsIgnore_NMMaster_NotFoundJP);

	/** Get Ignore non-mandatory masters if not found	  */
	public boolean isIgnore_NMMaster_NotFoundJP();

    /** Column name IsInvoicePrintDetails */
    public static final String COLUMNNAME_IsInvoicePrintDetails = "IsInvoicePrintDetails";

	/** Set Print detail records on invoice.
	  * Print detail BOM elements on the invoice
	  */
	public void setIsInvoicePrintDetails (boolean IsInvoicePrintDetails);

	/** Get Print detail records on invoice.
	  * Print detail BOM elements on the invoice
	  */
	public boolean isInvoicePrintDetails();

    /** Column name IsKanban */
    public static final String COLUMNNAME_IsKanban = "IsKanban";

	/** Set Kanban controlled.
	  * This part is Kanban controlled
	  */
	public void setIsKanban (boolean IsKanban);

	/** Get Kanban controlled.
	  * This part is Kanban controlled
	  */
	public boolean isKanban();

    /** Column name IsManufactured */
    public static final String COLUMNNAME_IsManufactured = "IsManufactured";

	/** Set Manufactured.
	  * This product is manufactured
	  */
	public void setIsManufactured (boolean IsManufactured);

	/** Get Manufactured.
	  * This product is manufactured
	  */
	public boolean isManufactured();

    /** Column name IsOwnBox */
    public static final String COLUMNNAME_IsOwnBox = "IsOwnBox";

	/** Set Own Box	  */
	public void setIsOwnBox (boolean IsOwnBox);

	/** Get Own Box	  */
	public boolean isOwnBox();

    /** Column name IsPhantom */
    public static final String COLUMNNAME_IsPhantom = "IsPhantom";

	/** Set Phantom.
	  * Phantom Component
	  */
	public void setIsPhantom (boolean IsPhantom);

	/** Get Phantom.
	  * Phantom Component
	  */
	public boolean isPhantom();

    /** Column name IsPickListPrintDetails */
    public static final String COLUMNNAME_IsPickListPrintDetails = "IsPickListPrintDetails";

	/** Set Print detail records on pick list.
	  * Print detail BOM elements on the pick list
	  */
	public void setIsPickListPrintDetails (boolean IsPickListPrintDetails);

	/** Get Print detail records on pick list.
	  * Print detail BOM elements on the pick list
	  */
	public boolean isPickListPrintDetails();

    /** Column name IsPurchased */
    public static final String COLUMNNAME_IsPurchased = "IsPurchased";

	/** Set Purchased.
	  * Organization purchases this product
	  */
	public void setIsPurchased (boolean IsPurchased);

	/** Get Purchased.
	  * Organization purchases this product
	  */
	public boolean isPurchased();

    /** Column name IsSelfService */
    public static final String COLUMNNAME_IsSelfService = "IsSelfService";

	/** Set Self-Service.
	  * This is a Self-Service entry or this entry can be changed via Self-Service
	  */
	public void setIsSelfService (boolean IsSelfService);

	/** Get Self-Service.
	  * This is a Self-Service entry or this entry can be changed via Self-Service
	  */
	public boolean isSelfService();

    /** Column name IsSold */
    public static final String COLUMNNAME_IsSold = "IsSold";

	/** Set Sold.
	  * Organization sells this product
	  */
	public void setIsSold (boolean IsSold);

	/** Get Sold.
	  * Organization sells this product
	  */
	public boolean isSold();

    /** Column name IsStocked */
    public static final String COLUMNNAME_IsStocked = "IsStocked";

	/** Set Stocked.
	  * Organization stocks this product
	  */
	public void setIsStocked (boolean IsStocked);

	/** Get Stocked.
	  * Organization stocks this product
	  */
	public boolean isStocked();

    /** Column name IsSummary */
    public static final String COLUMNNAME_IsSummary = "IsSummary";

	/** Set Summary Level.
	  * This is a summary entity
	  */
	public void setIsSummary (boolean IsSummary);

	/** Get Summary Level.
	  * This is a summary entity
	  */
	public boolean isSummary();

    /** Column name IsVerified */
    public static final String COLUMNNAME_IsVerified = "IsVerified";

	/** Set Verified.
	  * The BOM configuration has been verified
	  */
	public void setIsVerified (boolean IsVerified);

	/** Get Verified.
	  * The BOM configuration has been verified
	  */
	public boolean isVerified();

    /** Column name IsWebStoreFeatured */
    public static final String COLUMNNAME_IsWebStoreFeatured = "IsWebStoreFeatured";

	/** Set Featured in Web Store.
	  * If selected, the product is displayed in the initial or any empty search
	  */
	public void setIsWebStoreFeatured (boolean IsWebStoreFeatured);

	/** Get Featured in Web Store.
	  * If selected, the product is displayed in the initial or any empty search
	  */
	public boolean isWebStoreFeatured();

    /** Column name JP_AttributeSet_Name */
    public static final String COLUMNNAME_JP_AttributeSet_Name = "JP_AttributeSet_Name";

	/** Set Attribute Set(Name).
	  * Product Attribute Set
	  */
	public void setJP_AttributeSet_Name (String JP_AttributeSet_Name);

	/** Get Attribute Set(Name).
	  * Product Attribute Set
	  */
	public String getJP_AttributeSet_Name();

    /** Column name JP_CM_ProductCategory_ID */
    public static final String COLUMNNAME_JP_CM_ProductCategory_ID = "JP_CM_ProductCategory_ID";

	/** Set Consolidated Product Category.
	  * JPIERE-0629:JPPS
	  */
	public void setJP_CM_ProductCategory_ID (int JP_CM_ProductCategory_ID);

	/** Get Consolidated Product Category.
	  * JPIERE-0629:JPPS
	  */
	public int getJP_CM_ProductCategory_ID();

	public I_JP_CM_ProductCategory getJP_CM_ProductCategory() throws RuntimeException;

    /** Column name JP_CM_Product_ID */
    public static final String COLUMNNAME_JP_CM_Product_ID = "JP_CM_Product_ID";

	/** Set Consolidated Product	  */
	public void setJP_CM_Product_ID (int JP_CM_Product_ID);

	/** Get Consolidated Product	  */
	public int getJP_CM_Product_ID();

    /** Column name JP_CM_Product_UU */
    public static final String COLUMNNAME_JP_CM_Product_UU = "JP_CM_Product_UU";

	/** Set Consolidated Product (UU)	  */
	public void setJP_CM_Product_UU (String JP_CM_Product_UU);

	/** Get Consolidated Product (UU)	  */
	public String getJP_CM_Product_UU();

    /** Column name JP_FreightCategory_Value */
    public static final String COLUMNNAME_JP_FreightCategory_Value = "JP_FreightCategory_Value";

	/** Set Freight Category(Search Key).
	  * Category of the Freight
	  */
	public void setJP_FreightCategory_Value (String JP_FreightCategory_Value);

	/** Get Freight Category(Search Key).
	  * Category of the Freight
	  */
	public String getJP_FreightCategory_Value();

    /** Column name JP_Locator_Value */
    public static final String COLUMNNAME_JP_Locator_Value = "JP_Locator_Value";

	/** Set Locator(Search Key).
	  * Warehouse Locator
	  */
	public void setJP_Locator_Value (String JP_Locator_Value);

	/** Get Locator(Search Key).
	  * Warehouse Locator
	  */
	public String getJP_Locator_Value();

    /** Column name JP_MailText_Name */
    public static final String COLUMNNAME_JP_MailText_Name = "JP_MailText_Name";

	/** Set Mail Template(Name).
	  * Text templates for mailings
	  */
	public void setJP_MailText_Name (String JP_MailText_Name);

	/** Get Mail Template(Name).
	  * Text templates for mailings
	  */
	public String getJP_MailText_Name();

    /** Column name JP_PartType_Name */
    public static final String COLUMNNAME_JP_PartType_Name = "JP_PartType_Name";

	/** Set Part Type(Name)	  */
	public void setJP_PartType_Name (String JP_PartType_Name);

	/** Get Part Type(Name)	  */
	public String getJP_PartType_Name();

    /** Column name JP_Product_Category_Value */
    public static final String COLUMNNAME_JP_Product_Category_Value = "JP_Product_Category_Value";

	/** Set Product Category(Search Key).
	  * Category of a Product
	  */
	public void setJP_Product_Category_Value (String JP_Product_Category_Value);

	/** Get Product Category(Search Key).
	  * Category of a Product
	  */
	public String getJP_Product_Category_Value();

    /** Column name JP_ResourceType_Value */
    public static final String COLUMNNAME_JP_ResourceType_Value = "JP_ResourceType_Value";

	/** Set Resource Type(Search Key)	  */
	public void setJP_ResourceType_Value (String JP_ResourceType_Value);

	/** Get Resource Type(Search Key)	  */
	public String getJP_ResourceType_Value();

    /** Column name JP_RevenueRecognition_Name */
    public static final String COLUMNNAME_JP_RevenueRecognition_Name = "JP_RevenueRecognition_Name";

	/** Set Revenue Recognition(Name).
	  * Method for recording revenue
	  */
	public void setJP_RevenueRecognition_Name (String JP_RevenueRecognition_Name);

	/** Get Revenue Recognition(Name).
	  * Method for recording revenue
	  */
	public String getJP_RevenueRecognition_Name();

    /** Column name JP_TaxCategory_Name */
    public static final String COLUMNNAME_JP_TaxCategory_Name = "JP_TaxCategory_Name";

	/** Set Tax Category(name).
	  * Tax Category
	  */
	public void setJP_TaxCategory_Name (String JP_TaxCategory_Name);

	/** Get Tax Category(name).
	  * Tax Category
	  */
	public String getJP_TaxCategory_Name();

    /** Column name JP_VendorUOM_ID */
    public static final String COLUMNNAME_JP_VendorUOM_ID = "JP_VendorUOM_ID";

	/** Set UOM(Vendor).
	  * Unit of Measure
	  */
	public void setJP_VendorUOM_ID (int JP_VendorUOM_ID);

	/** Get UOM(Vendor).
	  * Unit of Measure
	  */
	public int getJP_VendorUOM_ID();

	public org.compiere.model.I_C_UOM getJP_VendorUOM() throws RuntimeException;

    /** Column name JP_VendorUPC */
    public static final String COLUMNNAME_JP_VendorUPC = "JP_VendorUPC";

	/** Set UPC/EAN(Vendor).
	  * Bar Code (Universal Product Code or its superset European Article Number)
	  */
	public void setJP_VendorUPC (String JP_VendorUPC);

	/** Get UPC/EAN(Vendor).
	  * Bar Code (Universal Product Code or its superset European Article Number)
	  */
	public String getJP_VendorUPC();

    /** Column name JP_Warehouse_Value */
    public static final String COLUMNNAME_JP_Warehouse_Value = "JP_Warehouse_Value";

	/** Set Org Warehouse(Search Key)	  */
	public void setJP_Warehouse_Value (String JP_Warehouse_Value);

	/** Get Org Warehouse(Search Key)	  */
	public String getJP_Warehouse_Value();

    /** Column name LowLevel */
    public static final String COLUMNNAME_LowLevel = "LowLevel";

	/** Set Low Level.
	  * The Low Level is used to calculate the material plan and determines if a net requirement should be exploited
	  */
	public void setLowLevel (int LowLevel);

	/** Get Low Level.
	  * The Low Level is used to calculate the material plan and determines if a net requirement should be exploited
	  */
	public int getLowLevel();

    /** Column name Manufacturer */
    public static final String COLUMNNAME_Manufacturer = "Manufacturer";

	/** Set Manufacturer.
	  * Manufacturer of the Product
	  */
	public void setManufacturer (String Manufacturer);

	/** Get Manufacturer.
	  * Manufacturer of the Product
	  */
	public String getManufacturer();

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

    /** Column name Order_Min */
    public static final String COLUMNNAME_Order_Min = "Order_Min";

	/** Set Minimum Order Qty.
	  * Minimum order quantity in UOM
	  */
	public void setOrder_Min (BigDecimal Order_Min);

	/** Get Minimum Order Qty.
	  * Minimum order quantity in UOM
	  */
	public BigDecimal getOrder_Min();

    /** Column name Order_Pack */
    public static final String COLUMNNAME_Order_Pack = "Order_Pack";

	/** Set Order Pack Qty.
	  * Package order size in UOM (e.g. order set of 5 units)
	  */
	public void setOrder_Pack (BigDecimal Order_Pack);

	/** Get Order Pack Qty.
	  * Package order size in UOM (e.g. order set of 5 units)
	  */
	public BigDecimal getOrder_Pack();

    /** Column name PriceEffective */
    public static final String COLUMNNAME_PriceEffective = "PriceEffective";

	/** Set Price effective.
	  * Effective Date of Price
	  */
	public void setPriceEffective (Timestamp PriceEffective);

	/** Get Price effective.
	  * Effective Date of Price
	  */
	public Timestamp getPriceEffective();

    /** Column name PriceList */
    public static final String COLUMNNAME_PriceList = "PriceList";

	/** Set List Price.
	  * List Price
	  */
	public void setPriceList (BigDecimal PriceList);

	/** Get List Price.
	  * List Price
	  */
	public BigDecimal getPriceList();

    /** Column name PricePO */
    public static final String COLUMNNAME_PricePO = "PricePO";

	/** Set PO Price.
	  * Price based on a purchase order
	  */
	public void setPricePO (BigDecimal PricePO);

	/** Get PO Price.
	  * Price based on a purchase order
	  */
	public BigDecimal getPricePO();

    /** Column name Processing */
    public static final String COLUMNNAME_Processing = "Processing";

	/** Set Process Now	  */
	public void setProcessing (boolean Processing);

	/** Get Process Now	  */
	public boolean isProcessing();

    /** Column name ProductType */
    public static final String COLUMNNAME_ProductType = "ProductType";

	/** Set Product Type.
	  * Type of product
	  */
	public void setProductType (String ProductType);

	/** Get Product Type.
	  * Type of product
	  */
	public String getProductType();

    /** Column name QualityRating */
    public static final String COLUMNNAME_QualityRating = "QualityRating";

	/** Set Quality Rating.
	  * Method for rating vendors
	  */
	public void setQualityRating (int QualityRating);

	/** Get Quality Rating.
	  * Method for rating vendors
	  */
	public int getQualityRating();

    /** Column name RoyaltyAmt */
    public static final String COLUMNNAME_RoyaltyAmt = "RoyaltyAmt";

	/** Set Royalty Amount.
	  * (Included) Amount for copyright, etc.
	  */
	public void setRoyaltyAmt (BigDecimal RoyaltyAmt);

	/** Get Royalty Amount.
	  * (Included) Amount for copyright, etc.
	  */
	public BigDecimal getRoyaltyAmt();

    /** Column name SKU */
    public static final String COLUMNNAME_SKU = "SKU";

	/** Set SKU.
	  * Stock Keeping Unit
	  */
	public void setSKU (String SKU);

	/** Get SKU.
	  * Stock Keeping Unit
	  */
	public String getSKU();

    /** Column name ShelfDepth */
    public static final String COLUMNNAME_ShelfDepth = "ShelfDepth";

	/** Set Shelf Depth.
	  * Shelf depth required
	  */
	public void setShelfDepth (int ShelfDepth);

	/** Get Shelf Depth.
	  * Shelf depth required
	  */
	public int getShelfDepth();

    /** Column name ShelfHeight */
    public static final String COLUMNNAME_ShelfHeight = "ShelfHeight";

	/** Set Shelf Height.
	  * Shelf height required
	  */
	public void setShelfHeight (BigDecimal ShelfHeight);

	/** Get Shelf Height.
	  * Shelf height required
	  */
	public BigDecimal getShelfHeight();

    /** Column name ShelfWidth */
    public static final String COLUMNNAME_ShelfWidth = "ShelfWidth";

	/** Set Shelf Width.
	  * Shelf width required
	  */
	public void setShelfWidth (int ShelfWidth);

	/** Get Shelf Width.
	  * Shelf width required
	  */
	public int getShelfWidth();

    /** Column name UPC */
    public static final String COLUMNNAME_UPC = "UPC";

	/** Set UPC/EAN.
	  * Bar Code (Universal Product Code or its superset European Article Number)
	  */
	public void setUPC (String UPC);

	/** Get UPC/EAN.
	  * Bar Code (Universal Product Code or its superset European Article Number)
	  */
	public String getUPC();

    /** Column name UnitsPerPack */
    public static final String COLUMNNAME_UnitsPerPack = "UnitsPerPack";

	/** Set Units Per Pack.
	  * The Units Per Pack indicates the no of units of a product packed together.
	  */
	public void setUnitsPerPack (int UnitsPerPack);

	/** Get Units Per Pack.
	  * The Units Per Pack indicates the no of units of a product packed together.
	  */
	public int getUnitsPerPack();

    /** Column name UnitsPerPallet */
    public static final String COLUMNNAME_UnitsPerPallet = "UnitsPerPallet";

	/** Set Units Per Pallet.
	  * Units Per Pallet
	  */
	public void setUnitsPerPallet (BigDecimal UnitsPerPallet);

	/** Get Units Per Pallet.
	  * Units Per Pallet
	  */
	public BigDecimal getUnitsPerPallet();

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

    /** Column name VendorCategory */
    public static final String COLUMNNAME_VendorCategory = "VendorCategory";

	/** Set Partner Category.
	  * Product Category of the Business Partner
	  */
	public void setVendorCategory (String VendorCategory);

	/** Get Partner Category.
	  * Product Category of the Business Partner
	  */
	public String getVendorCategory();

    /** Column name VendorProductNo */
    public static final String COLUMNNAME_VendorProductNo = "VendorProductNo";

	/** Set Partner Product Key.
	  * Product Key of the Business Partner
	  */
	public void setVendorProductNo (String VendorProductNo);

	/** Get Partner Product Key.
	  * Product Key of the Business Partner
	  */
	public String getVendorProductNo();

    /** Column name VersionNo */
    public static final String COLUMNNAME_VersionNo = "VersionNo";

	/** Set Version No.
	  * Version Number
	  */
	public void setVersionNo (String VersionNo);

	/** Get Version No.
	  * Version Number
	  */
	public String getVersionNo();

    /** Column name Volume */
    public static final String COLUMNNAME_Volume = "Volume";

	/** Set Volume.
	  * Volume of a product
	  */
	public void setVolume (BigDecimal Volume);

	/** Get Volume.
	  * Volume of a product
	  */
	public BigDecimal getVolume();

    /** Column name Weight */
    public static final String COLUMNNAME_Weight = "Weight";

	/** Set Weight.
	  * Weight of a product
	  */
	public void setWeight (BigDecimal Weight);

	/** Get Weight.
	  * Weight of a product
	  */
	public BigDecimal getWeight();
}
