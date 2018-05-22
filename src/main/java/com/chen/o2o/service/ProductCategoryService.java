package com.chen.o2o.service;

import com.chen.o2o.dto.ProductCategoryExecution;
import com.chen.o2o.entity.ProductCategory;
import com.chen.o2o.exception.ProductCategoryOperationException;

import java.util.List;

public interface ProductCategoryService {
    List<ProductCategory> getProductCategoryList(long shopId);

    ProductCategoryExecution batchAddProductCategory(List<ProductCategory> productCategoryList) throws ProductCategoryOperationException;

    ProductCategoryExecution deleteProductCategory(long productCategoryId,long shopId) throws ProductCategoryOperationException;

    List<ProductCategory> getByShopId(long shopId);
}
