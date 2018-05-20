package com.chen.o2o.service;

import com.chen.o2o.BaseTest;
import com.chen.o2o.entity.Area;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


public class AreaServiceTest extends BaseTest{
    @Autowired
    private AreaService areaService;

    @Test
    public void testGetAreaList(){
        List<Area> areaList = areaService.getAreaList();
        Area area = areaList.get(0);
        assertEquals("惠州",area.getAreaName());
    }
}
