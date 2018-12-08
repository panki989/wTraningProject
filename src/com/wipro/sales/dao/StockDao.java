package com.wipro.sales.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.wipro.sales.bean.Product;
import com.wipro.sales.util.DBUtil;

public class StockDao {

	/* This method is used to insert the given stock obj into TBL_STOCK table */
	public int insertStock(Product prdt) throws ClassNotFoundException, SQLException {
		Connection con = DBUtil.getDBConnection();
		PreparedStatement ps = con.prepareStatement("insert into TBL_STOCK values (?, ?, ? , ?, ?)");
		ps.setString(1, prdt.getProductID());
		ps.setString(2, prdt.getProductName());
		ps.setInt(3, prdt.getQuantityOnHand());
		ps.setDouble(4, prdt.getProductUnitPrice());
		ps.setInt(5, prdt.getReorderLevel());
		int t = ps.executeUpdate();
		ps.close();
		con.close();
		return t;
	}

	/*
	 * This method is used to generate Stock ID using the First 2 letters of the
	 * given product name concatenated with the SEQ_PRODUCT_ID sequence generated
	 * number.
	 */
	public String generateProductID(String productName) throws ClassNotFoundException, SQLException {

		Connection con = DBUtil.getDBConnection();
		PreparedStatement ps = con.prepareStatement("select SEQ_PRODUCT_ID.nextval from dual");
		ResultSet rs = ps.executeQuery();
		int val = 0;
		if (rs.next())
			val = rs.getInt(1);
		String str = productName.substring(0, 2).concat(String.valueOf(val));
		rs.close();
		ps.close();
		con.close();
		return str;
	}

	/*
	 * This method is used to update the Stock table by subtracting the current
	 * Quantity_On_Hand by the given soldQty of the given productID.
	 */
	public int updateStock(String productID, int soldQty) throws ClassNotFoundException, SQLException {

		Connection con = DBUtil.getDBConnection();
		PreparedStatement ps = con
				.prepareStatement("UPDATE TBL_STOCK SET Quantity_On_Hand=Quantity_On_Hand - ? WHERE Product_ID = ?");
		ps.setInt(1, soldQty);
		ps.setString(2, productID);
		int t = ps.executeUpdate();
		ps.close();
		con.close();
		return t;
	}

	/*
	 * This method is used to fetch a specific record details from the Stock table
	 * for the given productID, store the information to a Stock bean object the
	 * return the same.
	 */
	public Product getStock(String productID) throws ClassNotFoundException, SQLException {

		Product pd = new Product();
		Connection con = DBUtil.getDBConnection();
		PreparedStatement ps = con.prepareStatement("select * from TBL_STOCK WHERE Product_ID = ?");
		ps.setString(1, productID);
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			pd.setProductID(rs.getString(1));
			pd.setProductName(rs.getString(2));
			pd.setQuantityOnHand(rs.getInt(3));
			pd.setProductUnitPrice(rs.getDouble(4));
			pd.setReorderLevel(rs.getInt(5));
		}
		ps.close();
		con.close();
		return pd;
	}

	/* This method is used to delete the stock record of the given ProductID */
	public int deleteStock(String productID) throws ClassNotFoundException, SQLException {
		Connection con = DBUtil.getDBConnection();
		PreparedStatement ps = con.prepareStatement("DELETE FROM TBL_STOCK WHERE Product_ID = ?");
		ps.setString(1, productID);
		int t = ps.executeUpdate();
		ps.close();
		con.close();
		return t;
	}

}
