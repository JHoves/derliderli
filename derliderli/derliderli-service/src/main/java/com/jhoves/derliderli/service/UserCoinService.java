package com.jhoves.derliderli.service;

import com.jhoves.derliderli.dao.UserCoinDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author JHoves
 * @create 2023-02-14 21:53
 */
@Service
public class UserCoinService {
    @Autowired
    private UserCoinDao userCoinDao;

    //获取用户硬币总数
    public Integer getUserCoinsAmount(Long userId){
        return userCoinDao.getUserCoinsAmount(userId);
    }

    //更新用户硬币数量
    public void updateUserCoinsAmount(Long userId,Integer amount){
        Date updateTime = new Date();
        userCoinDao.updateUserCoinsAmount(userId,amount,updateTime);
    }

}
