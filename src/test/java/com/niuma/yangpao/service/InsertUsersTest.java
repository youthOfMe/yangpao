package com.niuma.yangpao.service;

import com.niuma.yangpao.mapper.UserMapper;
import com.niuma.yangpao.model.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StopWatch;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

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

    // 使用循环betch批量执行SQL语句 10万条数据 20秒
    @Test
    public void doBetchInsertUsers() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        int j = 0;
        for (int i = 0; i < 10; i++) {
            List<User> userList = new ArrayList<>();
            while (true) {
                j++;
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
                if (j % 10000 == 1) {
                    break;
                }
            }

            userService.saveBatch(userList, 10000);
        }
        stopWatch.stop();
        System.out.println(stopWatch.getTotalTimeMillis());
    }

    /**
     * 使用默认线程池优化代码 异步化 10万条数据 5秒
     */
    @Test
    public void doThreadPoolInsertUsers() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        int j = 0;
        List<CompletableFuture<Void>> futureList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            List<User> userList = new ArrayList<>();
            while (true) {
                j++;
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
                if (j % 10000 == 1) {
                    break;
                }
            }
            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                userService.saveBatch(userList, 10000);
            });
            futureList.add(future);
        }
        CompletableFuture.allOf(futureList.toArray(new CompletableFuture[]{})).join();
        stopWatch.stop();
        System.out.println(stopWatch.getTotalTimeMillis());
    }

    /**
     * 使用自定义线程池优化代码 异步化 10万条数据 5秒
     */
    @Test
    public void doMyThreadPoolInsertUsers() {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10, 10, 10000, TimeUnit.MINUTES, new ArrayBlockingQueue<>(10000));
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        int j = 0;
        List<CompletableFuture<Void>> futureList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            List<User> userList = new ArrayList<>();
            while (true) {
                j++;
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
                if (j % 10000 == 1) {
                    break;
                }
            }
            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                userService.saveBatch(userList, 10000);
            }, threadPoolExecutor);
            futureList.add(future);
        }
        CompletableFuture.allOf(futureList.toArray(new CompletableFuture[]{})).join();
        stopWatch.stop();
        System.out.println(stopWatch.getTotalTimeMillis());
    }

}
