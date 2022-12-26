package org.partners.foodpackhub.data;

import org.partners.foodpackhub.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.batch.item.ItemProcessor;

public class ProductItemProcessor implements ItemProcessor<ProductInput, Product> {

  private static final Logger log = LoggerFactory.getLogger(ProductItemProcessor.class);

  @Override
  public Product process(final ProductInput productInput) throws Exception {
    
	Product product = new Product();
	product.setProductId(Long.parseLong(productInput.getProductId()));
	product.setProductName(productInput.getProductName());
	product.setProductType(productInput.getProductType());
	product.setPrice(Long.parseLong(productInput.getPrice()));
	
    log.info("Converting (" + productInput + ") into (" + product + ")");

    return product;
  }


}