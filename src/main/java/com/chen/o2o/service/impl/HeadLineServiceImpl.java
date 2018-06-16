package com.chen.o2o.service.impl;

import com.chen.o2o.cache.JedisUtil;
import com.chen.o2o.dao.HeadLineDao;
import com.chen.o2o.entity.HeadLine;
import com.chen.o2o.service.HeadLineService;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class HeadLineServiceImpl implements HeadLineService {
    @Autowired
    private HeadLineDao headLineDao;
    @Autowired
    private JedisUtil.Keys jedisKeys;
    @Autowired
    private JedisUtil.Strings jedisStrings;

    private static Logger logger = LoggerFactory.getLogger(HeadLineServiceImpl.class);


    @Override
    public List<HeadLine> getHeadLineList(HeadLine headLineCondition) throws IOException {
        //定义redis的key前缀
        String key = HLISTKEY;
        //定义接收对象
        List<HeadLine> headLineList = null;
        //定义jackson数据转换操作类
        ObjectMapper objectMapper = new ObjectMapper();
        //拼接出redis的key
        if(headLineCondition!=null && headLineCondition.getEnableStatus() != null){
            key = key +"_"+headLineCondition.getEnableStatus();
        }
        //判断Key是否存在
        if(!jedisKeys.exists(key)){
            //若不存在，则从数据库里面取出相应的数据
            headLineList = headLineDao.queryHeadLine(headLineCondition);
            //将相关的实体类集合装换成sting存入redis里面对应的key中
            String jsonString= objectMapper.writeValueAsString(headLineList);
            jedisStrings.set(key,jsonString);
        }else{
            //若存在，则直接从redis里面取出相应数据
            String jsonString = jedisStrings.get(key);
            //指定要将string转换成的集合类型
            JavaType javaType = objectMapper.getTypeFactory().constructParametricType(ArrayList.class,HeadLine.class);
            //将相关Key对应的value里的string转换成对象的实体类集合
            headLineList = objectMapper.readValue(jsonString,javaType);
        }

        return headLineList;
    }
}
