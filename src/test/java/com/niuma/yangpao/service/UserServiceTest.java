package com.niuma.yangpao.service;

import com.niuma.yangpao.model.domain.User;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * 用户服务测试
 *
 * @author yangge
 */
@SpringBootTest
class UserServiceTest {

    @Resource
    private UserService userService;

    @Test
    public void testAddUser() {
        User user = new User();
        user.setUsername("wobushiXingHai");
        user.setUserAccount("123");
        user.setAvatarUrl("https://thirdwx.qlogo.cn/mmopen/vi_32/aRneVaen8XSWOILibfkW5SHicYZia2oDxA8zKUtnzLRVFbwiclEK8f80QMRk3kviawzL8gTWk4MgA0P0VeF1r0O0upg/132");
        user.setGender(0);
        user.setUserPassword("123456");
        user.setPhone("123");
        user.setEmail("456");

        boolean result = userService.save(user);
        System.out.println(user.getId());
        Assertions.assertTrue(result);
    }

    @Test
    void userResgister() {
        String userAccount = "shehuiren";
        String userPassword = "12345678";
        String checkPassword = "12345678";
        String planetCode = "1";
        long result = userService.userResgister(userAccount, userPassword, checkPassword, planetCode);
        Assertions.assertNotEquals(-1, result);
        userAccount = "yu";
        result = userService.userResgister(userAccount, userPassword, checkPassword, planetCode);
        Assertions.assertEquals(-1, result);
        userAccount = "niuma";
        userPassword = "123456";
        result = userService.userResgister(userAccount, userPassword, checkPassword, planetCode);
        Assertions.assertEquals(-1, result);
        userAccount = "niu ma";
        result = userService.userResgister(userAccount, userPassword, checkPassword, planetCode);
        Assertions.assertEquals(-1, result);
        userPassword = "12345678";
        checkPassword = "123456789";
        result = userService.userResgister(userAccount, userPassword, checkPassword, planetCode);
        Assertions.assertEquals(-1, result);
        userAccount = "123";
        checkPassword = "12345678";
        result = userService.userResgister(userAccount, userPassword, checkPassword, planetCode);
        Assertions.assertEquals(-1, result);
        userAccount = "wobushiXingHai";
        result = userService.userResgister(userAccount, userPassword, checkPassword, planetCode);
        Assertions.assertTrue(result > 0);
    }

    @Test
    void searchUserByTags() {
        List<String> tagNameList = Arrays.asList("java", "python");
        List<User> userList = userService.searchUserByTags(tagNameList);
        Assert.assertNotNull(userList);
    }

}

