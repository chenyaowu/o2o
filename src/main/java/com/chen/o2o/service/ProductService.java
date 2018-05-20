package com.chen.o2o.service;

import com.chen.o2o.dto.ImageHolder;
import com.chen.o2o.dto.ProductExcution;
import com.chen.o2o.entity.Product;
import com.chen.o2o.exception.ProductOperationException;

import java.util.List;
public interface ProductService {
    /**
     * 添加商品已经图片处理
     * @param product
     * @param imageHolder
     * @param imageHolderList
     * @return
     * @throws ProductOperationException
     */
    ProductExcution addProduct(Product product, ImageHolder imageHolder, List<ImageHolder> imageHolderList) throws ProductOperationException;

    Product getProductById(long id);

    ProductExcution modifyProduct(Product product, ImageHolder imageHolder, List<ImageHolder> imageHolderList) throws ProductOperationException;

    /**
     * 查询商品列表并分页，可以输入条件有：商品名称（模糊），商品状态，店铺Id,商品类别
     * @param productCondition
     * @param pageIndex
     * @param pageSize
     * @return
     */
    ProductExcution getProductList(Product productCondition,int pageIndex,int pageSize);



}
