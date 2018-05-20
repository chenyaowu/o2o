package com.chen.o2o.service;

import com.chen.o2o.dto.ImageHolder;
import com.chen.o2o.dto.ShopExecution;
import com.chen.o2o.entity.Shop;
import com.chen.o2o.exception.ShopOperationException;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.InputStream;

public interface ShopService {
    ShopExecution addShop(Shop shop, ImageHolder imageHolder) throws ShopOperationException;

    Shop getByShopId(long shopId);

    ShopExecution modifyShop(Shop shop, ImageHolder imageHolder) throws ShopOperationException;

    //根据shopCondition返回相应列表数据
    ShopExecution getShopList(Shop shopCondition,int pageIndex,int pageSize);
}
