package com.chen.o2o.service;

import com.chen.o2o.BaseTest;
import com.chen.o2o.dto.ImageHolder;
import com.chen.o2o.dto.ShopExecution;
import com.chen.o2o.entity.Area;
import com.chen.o2o.entity.PersonInfo;
import com.chen.o2o.entity.Shop;
import com.chen.o2o.entity.ShopCategory;
import com.chen.o2o.enums.ShopStateEnum;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class ShopServiceTest extends BaseTest {
    @Autowired
    private ShopService shopService;

    @Test
    public void addShopTest() throws FileNotFoundException {
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
        shop.setShopDesc("test3");
        shop.setShopAddr("test3");
        shop.setShopImg("test3");
        shop.setLastEditTime(new Date());
        shop.setEnableStatus(0);
        shop.setAdvice("审核中");
        File shopImg = new File("E:\\1.png");
        InputStream is = new FileInputStream(shopImg);
       ShopExecution shopExecution =  shopService.addShop(shop,new ImageHolder(shopImg.getName(),is));
        assertEquals(ShopStateEnum.CHECK.getState(),shopExecution.getState());
    }
    @Test
    public void testModifyShop() throws FileNotFoundException {
        Shop shop = new Shop();
        shop.setShopId(3L);
        shop.setShopName("修改后");
        File shopImg = new File("E:/java/916492631372800465.jpg");
        InputStream is = new FileInputStream(shopImg);
        ShopExecution shopExecution = shopService.modifyShop(shop,new ImageHolder("ddd.jpg",is));
        System.out.println("新的图片地址："+shopExecution.getShop().getShopImg());
    }

    @Test
    public void testGetShopList(){
        Shop shopCondition = new Shop();
        ShopCategory shopCategory = new ShopCategory();
        shopCategory.setShopCategoryId(3L);
        shopCondition.setShopCategory(shopCategory);
        ShopExecution shopExecution = shopService.getShopList(shopCondition,1,2);
        System.out.println(shopExecution.getShopList().size());
        System.out.println(shopExecution.getCount());
    }
}
