package com.dbmsproject.dataholders;

public class Grocery {

	private int id;
	private String item_name;
	private int quantity;
	private float price;
	private String date;
	private String orderBy;

	public Grocery(int id, String item_name, int quantity, float price, String date, String orderBy) {
		this.id = id;
		this.item_name = item_name;
		this.quantity = quantity;
		this.price = price;
		this.date = date;
		this.orderBy = orderBy;
	}


	public int getId() {
		return id;
	}

	public String getItem_name() {
		return item_name;
	}

	public int getQuantity() {
		return quantity;
	}

	public float getPrice() {
		return price;
	}

	public String getDate() {
		return date;
	}

	public String getOrderBy() {
		return orderBy;
	}
}
