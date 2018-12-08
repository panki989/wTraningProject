package com.wipro.sales.main;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;

import com.wipro.sales.bean.Product;
import com.wipro.sales.bean.Sales;
import com.wipro.sales.bean.SalesReport;
import com.wipro.sales.service.Administrator;

public class SalesApplication {

	private static Scanner sc;

	public static void main(String[] args) throws ClassNotFoundException, SQLException, ParseException {
		System.out.println("   Welcome to Sales Application");
		Administrator ad = new Administrator();

		char ch;
		do {
			System.out.println("\t 1.Insert Stock");
			System.out.println("\t 2.Delete Stock");
			System.out.println("\t 3.Insert Sales");
			System.out.println("\t 4.View Sales Report");
			System.out.println("Enter your choice(1-4)");
			sc = new Scanner(System.in);
			int choice = sc.nextInt();
			switch (choice) {
			case 1:
				Product prdt = new Product();
				System.out.println("Please Enter the Product name");
				prdt.setProductName(sc.next());
				System.out.println("How much of quantity is it?");
				prdt.setQuantityOnHand(sc.nextInt());
				System.out.println("What is it's unit cost?");
				prdt.setProductUnitPrice(sc.nextDouble());
				System.out.println("Reorder level?");
				prdt.setReorderLevel(sc.nextInt());

				System.out.println("Status/ProductId is " + ad.insertStock(prdt)+"!!!");
				break;
			
			case 2:
				System.out.println("Please enter the ProductId you want to delete:");
				System.out.println(ad.deleteStock(sc.next()));
				break;
			
			case 3:
				Sales sls =new Sales();	
				System.out.println("Please enter sales date (dd/MM/yy):");
				String cindate = sc.next();
				SimpleDateFormat myFormat = new SimpleDateFormat("dd/MM/yy");
				java.util.Date date1=null;
				date1 = myFormat.parse(cindate);
				sls.setSalesDate(date1);
				System.out.println("ProductID you want to sell?");
				sls.setProductID(sc.next());
				System.out.println("Quantity sold?");
				sls.setQuantitySold(sc.nextInt());
				System.out.println("Per unit price of product?");
				sls.setSalesPricePerUnit(sc.nextDouble());
				
				System.out.println("Sales status is "+ad.insertSales(sls)+"!!!");
				break;
			
			case 4:
				ArrayList<SalesReport> ar=ad.getSalesReport();
				String head = "|%1$-10s|%2$-12s|%3$-10s|%4$-20s|%5$-15s|%6$-20s|%7$-20s|%8$-15s|\n\n";
				
				System.out.format(head,"SalesID", "SalesDate", "ProductID",
						"ProductName", "QuantitySold", "ProductUnitPrice",
						 "SalesPrice/Unit", "ProfitAmount");
				
				for(SalesReport sr : ar) {
					String format1 = "|%1$-10s|%2$-12s|%3$-10s|%4$-20s|%5$-15d|%6$-20f|%7$-20f|%8$-15f|\n";
					System.out.format(format1, sr.getSalesID(), new SimpleDateFormat("dd-MM-yyyy").format(sr.getSalesDate()), 
							sr.getProductID(),sr.getProductName(), sr.getQuantitySold(), sr.getProductUnitPrice(),
							sr.getSalesPricePerUnit(), sr.getProfitAmount());
					
					//new SimpleDateFormat("dd-MM-yyyy").format(sr.getSalesDate())
				}
				
				break;
			}
			System.out.println("Want to perform more operations!!! Enter (Y/y)");
			ch = sc.next().charAt(0);
		} while (ch == 'y' || ch == 'Y');

	}

}
