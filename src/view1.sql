CREATE VIEW V_SALES_REPORT
AS SELECT TBL_SALES.Sales_ID, TBL_SALES.Sales_Date, TBL_SALES.Product_ID, 
TBL_STOCK.Product_Name, TBL_SALES.Quantity_Sold, TBL_STOCK.Product_Unit_Price, 
TBL_SALES.Sales_Price_Per_Unit,
TBL_SALES.Sales_Price_Per_Unit-TBL_STOCK.Product_Unit_Price as Profit_Amount   
FROM TBL_SALES
INNER JOIN TBL_STOCK
ON TBL_SALES.PRODUCT_ID = TBL_STOCK.Product_ID
ORDER BY Profit_Amount DESC, TBL_SALES.Sales_ID ASC;
