package com.chen.o2o.dao;


import com.chen.o2o.BaseTest;
import com.chen.o2o.entity.Area;
import com.chen.o2o.entity.PersonInfo;
import com.chen.o2o.entity.Shop;
import com.chen.o2o.entity.ShopCategory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ShopDaoTest extends BaseTest {
    @Autowired
    private ShopDao shopDao;
    @Test
    public void testInsertShop(){
        Shop shop = new Shop();
        PersonInfo personInfo = new PersonInfo();
        Area area = new Area();
        ShopCategory shopCategory = new ShopCategory();
        personInfo.setUserId(1L);
        area.setAreaId(2);
        shopCategory.setShopCategoryId(1L);
        shop.setOwner(personInfo);
        shop.setShopName("test");
        shop.setArea(area);
        shop.setShopCategory(shopCategory);
        shop.setShopDesc("test");
        shop.setShopAddr("test");
        shop.setShopImg("test");
        shop.setCreateTime(new Date());
        shop.setEnableStatus(1);
        shop.setAdvice("审核");
        int effectedNum = shopDao.insertShop(shop);
        assertEquals(1,effectedNum);
    }
    @Test
    public void testUpdateShop(){
        Shop shop = new Shop();
        shop.setShopId(1L);
        PersonInfo personInfo = new PersonInfo();
        Area area = new Area();
        ShopCategory shopCategory = new ShopCategory();
        personInfo.setUserId(1L);
        area.setAreaId(2);
        shopCategory.setShopCategoryId(1L);
        shop.setOwner(personInfo);
        shop.setShopName("test2");
        shop.setArea(area);
        shop.setShopCategory(shopCategory);
        shop.setShopDesc("test2");
        shop.setShopAddr("test2");
        shop.setShopImg("test2");
        shop.setLastEditTime(new Date());
        shop.setEnableStatus(1);
        shop.setAdvice("审核");
        int effectedNum = shopDao.updateShop(shop);
        assertEquals(1,effectedNum);
    }
    @Test
    public void testQueryByShopId(){
        long shopId = 3L;
        Shop shop = shopDao.queryByShopId(shopId);
        System.out.println(shop.getArea().getAreaId());
        System.out.println(shop.getArea().getAreaName());
    }
    @Test
    public void testQueryShopListAndCount(){
        Shop shop = new Shop();
        PersonInfo owner = new PersonInfo();
        owner.setUserId(1l);
        shop.setOwner(owner);
        List<Shop> shopList= shopDao.queryShopList(shop,0,2);
        int count  = shopDao.queryShopCount(shop);
        System.out.println(shopList.size());
        System.out.println(count);
    }
}
