package com.netcracker.utilities;

import java.util.List;

/*
 * Definition of the product
 * Jesús Rodríguez Salazar jesrs@yahoo.com
 * v1.0
 * Date: 27/07/2021
 */
public class Product {
	
	private Object parent;
	private List<Product> products;
	private String name;
	private Double price;
	
	
	
	public Object getParent() {
		return parent;
	}
	public void setParent(Object parent) {
		this.parent = parent;
	}
	public List<Product> getProducts() {
		return products;
	}
	public void setProducts(List<Product> products) {
		this.products = products;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}

	
	
}
