package com.wipro.sales.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import com.wipro.sales.bean.Product;
import com.wipro.sales.bean.Sales;
import com.wipro.sales.bean.SalesReport;
import com.wipro.sales.dao.SalesDao;
import com.wipro.sales.dao.StockDao;

public class Administrator {
	
	/*This method is used to insert the given product object into the 
	 * TBL_STOCK table using StockDao class*/
	public String insertStock(Product prdt) throws ClassNotFoundException, SQLException {
		if(prdt!=null) {
			String name = prdt.getProductName();
			if(name.length()>1) {
				StockDao sd=new StockDao();
				String id=sd.generateProductID(name);
				prdt.setProductID(id);
				sd.insertStock(prdt);
				return id;
			}else {
				return "Data not Valid for insertion";
			}
		}else {
			return "Data not Valid for insertion";
		}
	}
	
	/*Delete the record of the given Product id using StockDao class*/
	public String deleteStock(String ProductID) throws ClassNotFoundException, SQLException {
		StockDao sd =new StockDao();
		if(sd.deleteStock(ProductID)>0)
			return "Deleted";
		return "Record cannot be deleted";
	}
	
	/*This method is used to insert the given salesobj 
	 * into the TBL_SALES table using SalesDao class*/
	public String insertSales(Sales sales) throws ClassNotFoundException, SQLException {
		if(sales!=null) {
			String id = sales.getProductID();
			StockDao sd = new StockDao();
			Product pd=sd.getStock(id);
			if(pd!=null) {
				if(sales.getQuantitySold()<=pd.getQuantityOnHand()) {
					Date cur =new Date();
					Date dt1=sales.getSalesDate();
					if(dt1.before(cur) || dt1.equals(cur)) {
						SalesDao slsd=new SalesDao();
						sales.setSalesID(slsd.generateSalesID(dt1));
						if(slsd.insertSales(sales)>0) {
							if(sd.updateStock(id, sales.getQuantitySold())>0) {
								return "Sales Completed";
							}else {
								return "Error2";
							}
						}else {
							return "Error1";
						}
					}else {
						return "Invalid date";
					}
				}else {
					return "Not enough stock on hand for sales";
				}
			}else {
				return "Unknown Product for sales";
			}			
		}else {
			return "Object not valid for insertion";
		}
	}
	
	/*This method calls the getSalesReport of the 
	 * SalesDao and returns the ArrayList*/
	public ArrayList<SalesReport> getSalesReport() throws ClassNotFoundException, SQLException{
		
		SalesDao sd=new SalesDao();
		return sd.getSalesReport();
	}

}
