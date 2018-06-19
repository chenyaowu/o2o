package com.chen.o2o.web.shopadmin;

import com.chen.o2o.dto.ImageHolder;
import com.chen.o2o.dto.ShopExecution;
import com.chen.o2o.entity.*;
import com.chen.o2o.enums.ShopStateEnum;
import com.chen.o2o.service.AreaService;
import com.chen.o2o.service.ProductCategoryService;
import com.chen.o2o.service.ShopCategoryService;
import com.chen.o2o.service.ShopService;
import com.chen.o2o.util.CodeUtil;
import com.chen.o2o.util.HttpServletRequestUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/shopadmin",method = {RequestMethod.GET})
public class ShopManagementController {
    @Autowired
    private ShopService shopService;
    @Autowired
    private ShopCategoryService shopCategoryService;
    @Autowired
    private AreaService areaService;


    @RequestMapping(value = "/registershop",method = RequestMethod.POST)
    @ResponseBody
    private Map<String,Object> registerShop(HttpServletRequest request){
        //1.接收并转化相应的参数，包括店铺信息和图片信息
        Map<String,Object> modelMapper =new HashMap<String,Object>();
        if(!CodeUtil.checkVerifyCode(request)){
            modelMapper.put("success",false);
            modelMapper.put("errMsg","验证码错误");
            return modelMapper;
        }
        String shopStr = HttpServletRequestUtil.getString(request,"shopStr");
        ObjectMapper mapper = new ObjectMapper();
        Shop shop = new Shop();
        try {
            shop = mapper.readValue(shopStr,Shop.class);
        } catch (Exception e) {
           modelMapper.put("success",false);
           modelMapper.put("errMsg",e.getMessage());
           return modelMapper;
        }
        CommonsMultipartFile shopImg = null;
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        if(commonsMultipartResolver.isMultipart(request)){
            MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
            shopImg = (CommonsMultipartFile) multipartHttpServletRequest.getFile("shopImg");
        }else{
            modelMapper.put("success",false);
            modelMapper.put("errMsg","上传图片不能为空");
            return modelMapper;
        }
        //2.注册店铺
        //3.返回结果
        if(shop != null && shopImg != null){
            PersonInfo owner = (PersonInfo) request.getSession().getAttribute("user");
            shop.setOwner(owner);
            ShopExecution shopExecution = null;
            try {
                shopExecution = shopService.addShop(shop,new ImageHolder(shopImg.getOriginalFilename(),shopImg.getInputStream()));
            } catch (IOException e) {
                modelMapper.put("success",false);
                modelMapper.put("errMsg",e.getMessage());
            }
            if(shopExecution.getState() == ShopStateEnum.CHECK.getState()){
                modelMapper.put("success",true);
                //该用户可以操作的店铺列表
                List<Shop> shopList = (List<Shop>) request.getSession().getAttribute("shopList");
                if(shopList == null || shopList.size() == 0){
                    shopList = new ArrayList<Shop>();
                }
                shopList.add(shopExecution.getShop());
                request.getSession().setAttribute("shopList",shopList);
            }else{
                modelMapper.put("success",false);
                modelMapper.put("errMsg",shopExecution.getStateInfo());
            }
            return  modelMapper;
        }else{
            modelMapper.put("success",false);
            modelMapper.put("errMsg","请输入店铺信息");
            return modelMapper;
        }

  }

    @RequestMapping(value = "/getshopinitinfo",method = RequestMethod.GET)
    @ResponseBody
    private Map<String,Object> getShopInitInfo(){
        Map<String,Object> modelMap = new HashMap<String ,Object>();
        List<ShopCategory> shopCategoryList = new ArrayList<ShopCategory>();
        List<Area> areaList = new ArrayList<Area>();

        try {
            shopCategoryList = shopCategoryService.getShopCategoryList(new ShopCategory());
            areaList = areaService.getAreaList();
            modelMap.put("shopCategoryList",shopCategoryList);
            modelMap.put("areaList",areaList);
            modelMap.put("success",true);
        } catch (Exception e) {
           modelMap.put("success",false);
           modelMap.put("errMsg",e.getMessage());

        }
        return modelMap;
    }

    @RequestMapping(value = "/getshopbyid",method = RequestMethod.GET)
    @ResponseBody
    private Map<String,Object> getShopById(HttpServletRequest request){
        Map<String,Object> modelMap = new HashMap<String ,Object>();
        Long shopId = HttpServletRequestUtil.getLong(request,"shopId");
        if(shopId>-1){
            try {
                Shop shop = shopService.getByShopId(shopId);
                List<Area> areaList = areaService.getAreaList();
                modelMap.put("shop",shop);
                modelMap.put("areaList",areaList);
                modelMap.put("success",true);
            } catch (Exception e) {
                modelMap.put("success",false);
                modelMap.put("errMsg",e.toString());
            }
        }else{
            modelMap.put("success",false);
            modelMap.put("error","empty shopId");
        }
        return modelMap;
    }

    @RequestMapping(value = "/modifyshop",method = RequestMethod.POST)
    @ResponseBody
    private Map<String,Object> modifyShop(HttpServletRequest request){
        //1.接收并转化相应的参数，包括店铺信息和图片信息
        Map<String,Object> modelMapper =new HashMap<String,Object>();
        if(!CodeUtil.checkVerifyCode(request)){
            modelMapper.put("success",false);
            modelMapper.put("errMsg","验证码错误");
            return modelMapper;
        }
        String shopStr = HttpServletRequestUtil.getString(request,"shopStr");
        ObjectMapper mapper = new ObjectMapper();
        Shop shop = null;
        try {
            shop = mapper.readValue(shopStr,Shop.class);
        } catch (Exception e) {
            modelMapper.put("success",false);
            modelMapper.put("errMsg",e.getMessage());
            return modelMapper;
        }
        CommonsMultipartFile shopImg = null;
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        if(commonsMultipartResolver.isMultipart(request)){
            MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
            shopImg = (CommonsMultipartFile) multipartHttpServletRequest.getFile("shopImg");
        }
        //2.修改店铺
        //3.返回结果
        if(shop != null &&shop.getShopId() !=null){
            ShopExecution shopExecution = null;
            try {
                if(shopImg == null){
                        shopExecution = shopService.modifyShop(shop,new ImageHolder(null,null));
                }else{
                        shopExecution = shopService.modifyShop(shop,new ImageHolder(shopImg.getName(),shopImg.getInputStream()));
                }
                if(shopExecution.getState() == ShopStateEnum.SUCCESS.getState()){
                    modelMapper.put("success",true);
                }else{
                    modelMapper.put("success",false);
                    modelMapper.put("errMsg",shopExecution.getStateInfo());
                }
            } catch (IOException e) {
                modelMapper.put("success",false);
                modelMapper.put("errMsg",e.getMessage());
            }
            return  modelMapper;
        }else{
            modelMapper.put("success",false);
            modelMapper.put("errMsg","请输入店铺Id");
            return modelMapper;
        }

    }

    @RequestMapping(value = "/getshoplist",method = RequestMethod.GET)
    @ResponseBody
    private Map<String,Object> getShopList(HttpServletRequest request){
        Map<String,Object> modelMap =new HashMap<String ,Object>();
        PersonInfo user = (PersonInfo) request.getSession().getAttribute("user");
        try {
            Shop shopCondition = new Shop();
            shopCondition.setOwner(user);
            ShopExecution shopExecution = shopService.getShopList(shopCondition,0,100);
            request.getSession().setAttribute("shopList",shopExecution.getShopList());
            modelMap.put("success",true);
            modelMap.put("shopList",shopExecution.getShopList());
            modelMap.put("user",user);
        } catch (Exception e) {
            modelMap.put("success",false);
            modelMap.put("errMsg",e.getMessage());
        }
        return modelMap;
    }


    @RequestMapping(value = "/getshopmanagementinfo",method = RequestMethod.GET)
    @ResponseBody
    private Map<String,Object> getShopManagementInfo(HttpServletRequest request){
        Map<String,Object> modelMap = new HashMap<String, Object>();
        long shopId = HttpServletRequestUtil.getLong(request,"shopId");
        if(shopId<=0){
            Object currentShopObj = request.getSession().getAttribute("currentShop");
            if(currentShopObj == null){
                modelMap.put("redirect",true);
                modelMap.put("url","/o2o/shopadmin/shoplist");
            }else{
                Shop currentShop = (Shop) currentShopObj;
                modelMap.put("redirect",false);
                modelMap.put("shopId",currentShop.getShopId());
            }
        }else{
            Shop currentShop = new Shop();
            currentShop.setShopId(shopId);
            request.getSession().setAttribute("currentShop",currentShop);
            modelMap.put("redirect",false);
        }
        return modelMap;
    }


}
