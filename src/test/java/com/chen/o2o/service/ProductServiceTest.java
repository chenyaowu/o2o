package com.chen.o2o.service;

import com.chen.o2o.BaseTest;
import com.chen.o2o.dto.ImageHolder;
import com.chen.o2o.dto.ProductExecution;
import com.chen.o2o.entity.Product;
import com.chen.o2o.entity.ProductCategory;
import com.chen.o2o.entity.Shop;
import com.chen.o2o.enums.ProductStateEnum;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProductServiceTest extends BaseTest {
    @Autowired
    private ProductService productService;
    @Test
    public void testAddProduct() throws FileNotFoundException {
        Product product = new Product();
        Shop shop = new Shop();
        shop.setShopId(3l);
        ProductCategory pc = new ProductCategory();
        pc.setProductCategoryId(2l);
        product.setShop(shop);
        product.setProductCategory(pc);
        product.setProductName("测试商品1");
        product.setProductDesc("测试商品1");
        product.setPriority(20);
        product.setCreateTime(new Date());
        product.setEnableStatus(ProductStateEnum.SUCCESS.getState());
        //创建缩略图文件流
        File file = new File("E:/aaa/261335545574906956.jpg");
        InputStream is = new FileInputStream(file);
        ImageHolder imageHolder = new ImageHolder(file.getName(),is);
        File file1 = new File("E:/aaa/916492631372800465.jpg");
        InputStream is2 = new FileInputStream(file1);
        ImageHolder imageHolder1 = new ImageHolder(file1.getName(),is2);
        List<ImageHolder> productImgList = new ArrayList<ImageHolder>();
        productImgList.add(imageHolder1);
        ProductExecution productExecution =  productService.addProduct(product,imageHolder,productImgList);
        assertEquals(ProductStateEnum.SUCCESS.getState(), productExecution.getState());

    }
    @Test
    public void testModifyProduct() throws FileNotFoundException {
        Product product = new Product();
        Shop shop = new Shop();
        shop.setShopId(3l);
        ProductCategory productCategory = new ProductCategory();
        productCategory.setProductCategoryId(2l);
        product.setProductId(3l);
        product.setShop(shop);
        product.setProductCategory(productCategory);
        product.setProductName("正式2");
        product.setProductDesc("正式2");
        File thumbnailFile = new File("E:/毕业照/261335545574906956.jpg");
        InputStream is = new FileInputStream(thumbnailFile);
        ImageHolder thumbnail = new ImageHolder(thumbnailFile.getName(),is);

        File productImg1 = new File("E:/毕业照/916492631372800465.jpg");
        InputStream is1 = new FileInputStream(productImg1);
        File productImg2 = new File("E:/毕业照/IMG_3968.JPG");
        InputStream is2 = new FileInputStream(productImg2);
        List<ImageHolder> productImgList = new ArrayList<ImageHolder>();
        productImgList.add(new ImageHolder(productImg1.getName(),is1));
        productImgList.add(new ImageHolder(productImg2.getName(),is2));
        ProductExecution productExecution = productService.modifyProduct(product,thumbnail,productImgList);
        assertEquals(1, productExecution.getState());
    }
}
