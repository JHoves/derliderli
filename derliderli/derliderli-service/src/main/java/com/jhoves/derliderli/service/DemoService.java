package com.jhoves.derliderli.service;

import com.jhoves.derliderli.dao.DemoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author JHoves
 * @create 2023-01-04 11:56
 */
@Service
public class DemoService {
    @Autowired
    private DemoDao demoDao;

    public Long query(Long id){
        return demoDao.query(id);
    }

}
