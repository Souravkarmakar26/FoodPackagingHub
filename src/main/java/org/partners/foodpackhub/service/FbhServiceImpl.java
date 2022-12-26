package org.partners.foodpackhub.service;

import java.util.ArrayList;
import java.util.List;

import org.partners.foodpackhub.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
@Component
public class FbhServiceImpl implements FbhService{
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public List<Product> getProductList() {
		List<Product>  productList= new ArrayList<>();
		productList = jdbcTemplate.query("select * from product", (rs,row) -> new Product(rs.getLong("product_id"),rs.getString("product_name"),rs.getString("product_type"),rs.getLong("price")));
		return productList;
	}

}
