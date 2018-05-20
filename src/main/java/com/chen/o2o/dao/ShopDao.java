package com.chen.o2o.dao;

import com.chen.o2o.entity.Shop;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShopDao {
    /**
     * 新增店铺
     *
     * @param shop
     * @return
     */
    int insertShop(Shop shop);

    /**
     * 更新店铺信息
     *
     * @param shop
     * @return
     */
    int updateShop(Shop shop);

    //通过shop id查询店铺
    Shop queryByShopId(long shopId);

    //分页查询店铺，可输入的条件有店铺名（模糊），店铺状态，店铺类别，区域Id,owner
    /*
    rowIndex 从第几行开始取，
    pageSize 返回条数
     */
    List<Shop> queryShopList(@Param("shopCondition") Shop shopCondition, @Param("rowIndex") int rowIntdex, @Param("pageSize") int pageSize);

    /*
        返回queryShopList总数
     */
    int queryShopCount(@Param("shopCondition") Shop shopConditon);
}
