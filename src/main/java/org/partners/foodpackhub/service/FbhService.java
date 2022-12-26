package org.partners.foodpackhub.service;

import java.util.List;

import org.partners.foodpackhub.model.Product;
import org.springframework.stereotype.Service;
@Service
public interface FbhService {

	public List<Product> getProductList();
}
