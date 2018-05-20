package com.chen.o2o.dao;

import com.chen.o2o.entity.Area;

import java.util.List;

public interface AreaDao {
    /*
    列出区域列表
    @Return areaList
     */
    List<Area> queryArea();
}
