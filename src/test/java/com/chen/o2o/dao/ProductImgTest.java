package com.chen.o2o.dao;

import com.chen.o2o.BaseTest;
import com.chen.o2o.entity.ProductImg;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductImgTest extends BaseTest {

    @Autowired
    private ProductImgDao productImgDao;

    @Test
    public void testQueryProductImgList(){
        List<ProductImg> productImgList = productImgDao.queryProductImgList(3);
        assertEquals(1,productImgList.size());
    }
    @Test
    public void testABatchInsertProductImg(){
        ProductImg productImg = new ProductImg();
        productImg.setImgAddr("图片1");
        productImg.setImgDesc("测试图片1");
        productImg.setPriority(1);
        productImg.setCreateTime(new Date());
        productImg.setProductId(2l);
        ProductImg productImg2 = new ProductImg();
        productImg2.setImgAddr("图片2");
        productImg2.setImgDesc("测试图片2");
        productImg2.setPriority(2);
        productImg2.setCreateTime(new Date());
        productImg2.setProductId(2L);
        List<ProductImg> productImgList = new ArrayList<ProductImg>();
        productImgList.add(productImg);
        productImgList.add(productImg2);
        int effectedNum = productImgDao.batchInsertProductImg(productImgList);
        assertEquals(2,effectedNum);
    }
    @Test
    public void testBDeleteProductImgByProductId(){
        long productId = 2;
        int effectedNum= productImgDao.deleteProductImgByProductId(productId);
        assertEquals(2,effectedNum);
    }
}
