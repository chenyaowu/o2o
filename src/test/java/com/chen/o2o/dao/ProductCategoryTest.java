package com.chen.o2o.dao;

import com.chen.o2o.BaseTest;
import com.chen.o2o.entity.ProductCategory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ProductCategoryTest extends BaseTest {
    @Autowired
    private ProductCategoryDao productCategoryDao;
    @Test
    public void testqueryShopCategoryByShopId(){
       List<ProductCategory> productCategoryList = productCategoryDao.queryProductCategoryList(3);
        System.out.println(productCategoryList.size());
    }
    @Test
    public void testBatchInsertProductCategory(){
        ProductCategory productCategory1 = new ProductCategory();
        ProductCategory productCategory2 = new ProductCategory();
        productCategory1.setPriority(1);
        productCategory1.setCreateTime(new Date());
        productCategory1.setShopId(3L);
        productCategory1.setProductCategoryName("商品类别1");

        productCategory2.setPriority(2);
        productCategory2.setCreateTime(new Date());
        productCategory2.setShopId(3L);
        productCategory2.setProductCategoryName("商品类别2");

        List<ProductCategory> list = new ArrayList<ProductCategory>();
        list.add(productCategory1);
        list.add(productCategory2);
        int effectNum = productCategoryDao.batchInsertProductCategory(list);
        org.junit.Assert.assertEquals(effectNum,2);
    }
    @Test
    public void testDelteProductCategory(){
        long shopId = 3L;
        List<ProductCategory> productCategoryList = productCategoryDao.queryProductCategoryList(shopId);
        for (ProductCategory productCategory:productCategoryList){
            if(productCategory.getProductCategoryName().equals("店铺类别3") || productCategory.getProductCategoryName().equals("店铺类别4")){
                int effectedNum = productCategoryDao.deleteProductCategory(productCategory.getProductCategoryId(),shopId);
                assertEquals(1,effectedNum);
            }
        }

    }
}
