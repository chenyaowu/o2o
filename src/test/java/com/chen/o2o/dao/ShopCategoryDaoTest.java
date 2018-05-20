package com.chen.o2o.dao;

import com.chen.o2o.BaseTest;
import com.chen.o2o.entity.ShopCategory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class ShopCategoryDaoTest extends BaseTest {
    @Autowired
    private ShopCategoryDao shopCategoryDao;
    @Test
    public void queryShopCategoryTest(){
        List<ShopCategory> shopCategoryList = shopCategoryDao.queryShopCategory(null);
        assertEquals(1,shopCategoryList.size());
        ShopCategory shopCategory = new ShopCategory();
        ShopCategory shopCategory1 = new ShopCategory();
        shopCategory1.setShopCategoryId(1L);
        shopCategory.setParent(shopCategory1);
        shopCategoryList  = shopCategoryDao.queryShopCategory(shopCategory);
        assertEquals(1,shopCategoryList.size());
    }
}
