package com.chen.o2o.service.impl;

import com.chen.o2o.dao.AreaDao;
import com.chen.o2o.entity.Area;
import com.chen.o2o.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AreaServiceImpl implements AreaService {
    @Autowired
    private AreaDao areaDao;
    public List<Area> getAreaList() {
        return areaDao.queryArea();
    }
}
