package org.partners.foodpackhub.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Product {
	@Id
	private long productId;
	private String productName;
	private String productType;
	private long price;
	
	public long getProductId() {
		return productId;
	}
	public void setProductId(long productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public long getPrice() {
		return price;
	}
	public void setPrice(long price) {
		this.price = price;
	}
	public Product(long productId, String productName, String productType, long price) {
		super();
		this.productId = productId;
		this.productName = productName;
		this.productType = productType;
		this.price = price;
	}
	public Product() {
		super();
	}
	
	

}
