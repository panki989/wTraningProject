package com.wipro.sales.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import com.wipro.sales.util.*;
import com.wipro.sales.bean.Sales;
import com.wipro.sales.bean.SalesReport;

public class SalesDao {
	
	/*This method is used to insert the given sales obj into TBL_SALES table*/
	public int insertSales(Sales sales) throws ClassNotFoundException, SQLException {
		
		Connection con=DBUtil.getDBConnection();
		PreparedStatement ps= con.prepareStatement("insert into TBL_SALES values (?, ?, ? , ?, ?)");
		ps.setString(1, sales.getSalesID());
		ps.setDate(2, new java.sql.Date(sales.getSalesDate().getTime()));
		ps.setString(3, sales.getProductID());
		ps.setInt(4, sales.getQuantitySold());
		ps.setDouble(5, sales.getSalesPricePerUnit());
		int t=ps.executeUpdate();
		ps.close();
		con.close();
		return t;
	}
	
	/*This method is used to generate Sales ID using the last2digit of the year part of the given date
	 * concatenated with the SEQ_SALES_ID sequence generated number.*/
	public String generateSalesID(java.util.Date salesDate) throws SQLException, ClassNotFoundException {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(salesDate);
		int year = calendar.get(Calendar.YEAR);
		
		Connection con=DBUtil.getDBConnection();
		PreparedStatement ps= con.prepareStatement("select SEQ_SALES_ID.nextval from dual");
		ResultSet rs = ps.executeQuery();
		int val=0;
		if(rs.next())
		      val = rs.getInt(1);
		String str = String.valueOf(year).substring(2).concat(String.valueOf(val));
		rs.close();
		ps.close();
		con.close();
		return str;
	}
	
	/*This method runs the V_SALES_REPORT view and stores every record in SalesREport Bean adding 
	 * them to an arraylist. Which is return back to the user.*/
	public ArrayList<SalesReport> getSalesReport() throws SQLException, ClassNotFoundException{
		ArrayList<SalesReport> ar= new ArrayList<SalesReport>();
		Connection con=DBUtil.getDBConnection();
		Statement st=con.createStatement();
		ResultSet rs = st.executeQuery("select * from V_SALES_REPORT");
		while(rs.next()) {
			SalesReport sl= new SalesReport();
			sl.setSalesID(rs.getString(1));
			java.util.Date utilDate = new java.util.Date(rs.getDate(2).getTime());
			sl.setSalesDate(utilDate);
			sl.setProductID(rs.getString(3));
			sl.setProductName(rs.getString(4));
			sl.setQuantitySold(rs.getInt(5));
			sl.setProductUnitPrice(rs.getDouble(6));
			sl.setSalesPricePerUnit(rs.getDouble(7));
			sl.setProfitAmount(rs.getDouble(8));
			
			ar.add(sl);
		}
		rs.close();
		st.close();
		con.close();
		return ar;
	}
}
