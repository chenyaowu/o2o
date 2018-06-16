package com.chen.o2o.service.impl;

import com.chen.o2o.dao.ShopDao;
import com.chen.o2o.dto.ImageHolder;
import com.chen.o2o.dto.ShopExecution;
import com.chen.o2o.entity.Shop;
import com.chen.o2o.enums.ShopStateEnum;
import com.chen.o2o.exception.ShopOperationException;
import com.chen.o2o.service.ShopService;
import com.chen.o2o.util.ImgUtil;
import com.chen.o2o.util.PageCalculator;
import com.chen.o2o.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class ShopServiceImpl implements ShopService {
    @Autowired
    private ShopDao shopDao;
    @Override
    @Transactional
    public ShopExecution addShop(Shop shop, ImageHolder imageHolder) {
        //空值判断
        if (shop == null) {
            return new ShopExecution(ShopStateEnum.NULL_SHOP);
        }
        try {
            //给店铺信息赋初始值
            shop.setEnableStatus(0);
            shop.setCreateTime(new Date());
            shop.setLastEditTime(new Date());
            int effectedNum = shopDao.insertShop(shop);
            if(effectedNum<=0){
                throw new ShopOperationException("店铺创建失败");
            }else{
                if(imageHolder.getImage() != null){
                    //存储图片
                    try {
                        addShopImg(shop,imageHolder);
                    } catch (Exception e) {
                        throw new ShopOperationException("addShopImgError"+e.getMessage());
                    }
                    //更新图片地址
                    effectedNum = shopDao.updateShop(shop);
                    if(effectedNum<=0){
                        throw new ShopOperationException("更新图片失败");
                    }
                }
            }
        } catch (Exception e){
            throw new ShopOperationException("addShop error"+e.getMessage());
        }

        return new ShopExecution(ShopStateEnum.CHECK,shop);
    }

    @Override
    public Shop getByShopId(long shopId) {
        return shopDao.queryByShopId(shopId);
    }

    @Override
    public ShopExecution modifyShop(Shop shop, ImageHolder imageHolder) throws ShopOperationException {
        if(shop == null || shop.getShopId() == null){
            return new ShopExecution(ShopStateEnum.NULL_SHOP);
        }else{
            try {
                //1.判断是否需要处理图片
                if(imageHolder.getImage() != null && imageHolder.getImageName() != null && !"".equals(imageHolder.getImageName())){
                    Shop tempShop =shopDao.queryByShopId(shop.getShopId());
                    if(tempShop.getShopImg() != null){
                        ImgUtil.deleteFile(tempShop.getShopImg());
                    }
                    addShopImg(shop,imageHolder);
                }
                //2.更新店铺信息
                shop.setLastEditTime(new Date());
                int effectedNum = shopDao.updateShop(shop);
                if(effectedNum<=0){
                    return new ShopExecution(ShopStateEnum.INNER_ERROR);
                }else{
                    shop = shopDao.queryByShopId(shop.getShopId());
                    return new ShopExecution(ShopStateEnum.SUCCESS,shop);
                }
            } catch (Exception e) {
                throw new ShopOperationException("modifyShop Error"+e.getMessage());
            }
        }
    }

    @Override
    public ShopExecution getShopList(Shop shopCondition, int pageIndex, int pageSize) {
        int rowIndex = PageCalculator.calculateRowIndex(pageIndex,pageSize);
        List<Shop> shopList = shopDao.queryShopList(shopCondition,rowIndex,pageSize);
        int count = shopDao.queryShopCount(shopCondition);
        ShopExecution shopExecution = new ShopExecution();
        if(shopList != null){
            shopExecution.setShopList(shopList);
            shopExecution.setCount(count);
        }else{
            shopExecution.setState(ShopStateEnum.INNER_ERROR.getState());
        }
        return shopExecution;
    }

    @Override
    public ShopExecution getByEmployeeId(Long userId) {
        return null;
    }

    //添加图片
    private void addShopImg(Shop shop, ImageHolder imageHolder) {
        //获取shop图片目录的相对值路径
        String dest = PathUtil.getShopImagePath(shop.getShopId());
        String shopImgAddr = ImgUtil.generateThumbnail(imageHolder,dest);
        shop.setShopImg(shopImgAddr);
    }
}
