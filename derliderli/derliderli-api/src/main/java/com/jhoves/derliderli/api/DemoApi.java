package com.jhoves.derliderli.api;

import com.jhoves.derliderli.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author JHoves
 * @create 2023-01-04 11:59
 */
@RestController
public class DemoApi {
    @Autowired
    private DemoService demoService;

    @GetMapping("/query")
    public Long query(Long id){
        return demoService.query(id);
    }
}
