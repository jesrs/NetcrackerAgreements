package com.netcracker.utilities;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/*
 * Definition of the Agreement
 * Jesús Rodríguez Salazar jesrs@yahoo.com
 * v1.0
 * Date: 27/07/2021
 */
public class Agreement {
	
	private String name;
	private String signedBy;
	private List<Product> products;
	
	
	public Agreement() {
		super();
		
		Date date = Calendar.getInstance().getTime();
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String today = formatter.format(date);
        name = "Agreement " + today;
		
	}

	public void setName(String name) {
		this.name = name;
	}
	

	public String getName() {
		return name;
	}


	public String getSignedBy() {
		return signedBy;
	}


	public void setSignedBy(String signedBy) {
		this.signedBy = signedBy;
	}


	public List<Product> getProducts() {
		return products;
	}


	public void setProducts(List<Product> products) {
		this.products = products;
	}
	
	
	
	
}
