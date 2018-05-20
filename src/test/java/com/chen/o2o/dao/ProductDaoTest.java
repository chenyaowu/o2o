package com.chen.o2o.dao;

import com.chen.o2o.BaseTest;
import com.chen.o2o.entity.Product;
import com.chen.o2o.entity.ProductCategory;
import com.chen.o2o.entity.Shop;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ProductDaoTest extends BaseTest{
    @Autowired
    private ProductDao productDao;

    @Test
    public void testInsertProduct(){

    }
    @Test
    public void testqueryProductById(){
        productDao.queryProductById(3l);
    }
    @Test
    public void testUpdateProduct(){
        Product product = new Product();
        ProductCategory productCategory = new ProductCategory();
        Shop shop = new Shop();
        shop.setShopId(3l);
        productCategory.setProductCategoryId(2l);
        product.setProductId(2l);
        product.setProductName("测试商品2");
        product.setProductCategory(productCategory);
        product.setShop(shop);
        int effectNum = productDao.updateProduct(product);
        assertEquals(1,effectNum);
    }

    @Test
    public void testQueryProductList(){
        Product productCondition = new Product();
        List<Product> productList = productDao.queryProductList(productCondition,0,3);
        assertEquals(3,productList.size());

        int count = productDao.queryProductCount(productCondition);
        assertEquals(3,count);

        productCondition.setProductName("测试");
        productList = productDao.queryProductList(productCondition,0,3);
        assertEquals(1,productList.size());

        count = productDao.queryProductCount(productCondition);
        assertEquals(1,count);
    }

    @Test
    public void testUpdateProductCategoryToNull(){
        int effectNum = productDao.updateProductCategoryToNull(2);
        assertEquals(3,effectNum);
    }
}
