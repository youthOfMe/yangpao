package com.niuma.usercenter.service;

import com.niuma.usercenter.mapper.UserMapper;
import com.niuma.usercenter.model.domain.User;
import org.junit.jupiter.api.Test;
import org.junit.rules.Stopwatch;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StopWatch;

import javax.annotation.Resource;
import java.util.Date;

@SpringBootTest
public class InsertUsersTest {

    @Resource
    private UserService userService;

    @Resource
    private UserMapper userMapper;

    // 普通循环insert插入 10万条数据 耗时55秒
    @Test
    public void  doInsertUsers() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        for (int i = 0; i < 100000; i++) {
            User user = new User();
            user.setUsername("牛马洋");
            user.setUserAccount("fakeniuma");
            user.setAvatarUrl("https://chenhai-misty-rain-test.oss-cn-beijing.aliyuncs.com/5ed2d23fd474593a3ee433ab73d84aa.jpg");
            user.setGender(0);
            user.setUserPassword("d948e8ce556f1de1a3a824bcd86cfa5a");
            user.setPhone("123");
            user.setEmail("123");
            user.setUserStatus(0);
            user.setUserRole(0);
            user.setCreateTime(new Date());
            user.setUpdateTime(new Date());
            user.setIsDelete(0);
            user.setPlanetCode("111111");
            user.setTags("[\"JAVA\", \"GO\"]");
            userMapper.insert(user);
        }
        stopWatch.stop();
        System.out.println(stopWatch.getTotalTimeMillis());
    }
}
