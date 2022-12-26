package org.partners.foodpackhub.controller;

import java.util.ArrayList;
import java.util.List;

import org.partners.foodpackhub.model.Product;
import org.partners.foodpackhub.service.FbhService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FphController {
	
	@Autowired
	private FbhService fbhService;
	@RequestMapping("/Products")
	public List<Product> getProductList() {
		List<Product> productList = fbhService.getProductList();		
		return productList;
	}

}
