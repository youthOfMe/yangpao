package com.niuma.usercenter.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.niuma.usercenter.common.BaseResponse;
import com.niuma.usercenter.common.ErrorCode;
import com.niuma.usercenter.common.ResultUtils;
import com.niuma.usercenter.exception.BusinessException;
import com.niuma.usercenter.model.domain.User;
import com.niuma.usercenter.model.request.UserLoginRequest;
import com.niuma.usercenter.model.request.UserRegsiterRequest;
import com.niuma.usercenter.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.niuma.usercenter.contant.UserContant.USER_LOGIN_STATE;

/**
 * 用户接口
 *
 * @author wobushiXingHai
 */
@CrossOrigin(origins = { "http://localhost:3000" })
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Resource
    private UserService userService;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @PostMapping("/register")
    public BaseResponse<Long> userRegister(@RequestBody UserRegsiterRequest userRegsiterRequest) {
        if (userRegsiterRequest == null) {
            // return ResultUtils.error(ErrorCode.PARAMS_ERROR);
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String userAccount = userRegsiterRequest.getUserAccount();
        String userPassword = userRegsiterRequest.getUserPassword();
        String checkPassword = userRegsiterRequest.getUserPassword();
        String planetCode = userRegsiterRequest.getPlanetCode();
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {
            return ResultUtils.error(ErrorCode.PARAMS_ERROR);
        }
        long result = userService.userResgister(userAccount, userPassword, checkPassword, planetCode);
        // return new BaseResponse<Long>(0, result, "ok");
        return ResultUtils.success(result);
    }

    @PostMapping("/login")
    public BaseResponse<User> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        if (userLoginRequest == null) {
            return ResultUtils.error(ErrorCode.PARAMS_ERROR);
        }
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        if (StringUtils.isAnyBlank(userAccount, userPassword)){
            return ResultUtils.error(ErrorCode.PARAMS_ERROR);
        }
        User user = userService.userLogin(userAccount, userPassword, request);
        return new BaseResponse<>(0, user, "ok");
    }

    @PostMapping("/logout")
    public BaseResponse<Integer> userLogout(HttpServletRequest request) {
        if (request == null) {
            return ResultUtils.error(ErrorCode.PARAMS_ERROR);
        }
        int result = userService.userLogout(request);
        return ResultUtils.success(result);
    }

    @PostMapping("/search")
    public BaseResponse<List<User>> searchUsers(String username, HttpServletRequest request) {
        // 仅管理员可以查询
        if (!userService.isAdmin(request)) {
            return ResultUtils.success(new ArrayList<>());
        }

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        // isNoneBlank 不为""/null/" "
        if (StringUtils.isNoneBlank(username)) {
            queryWrapper.like("username", username);
        }
        List<User> userList = userService.list(queryWrapper);
        List<User> list = userList.stream().map(user -> userService.getSafetyUser(user)).collect(Collectors.toList());
        return ResultUtils.success(list);
    }

    @GetMapping("/current")
    public BaseResponse<User> getCurrentUser(HttpServletRequest request) {
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User currentUser = (User) userObj;
        if (currentUser == null) {
            return ResultUtils.error(ErrorCode.PARAMS_ERROR);
        }
        long userId = currentUser.getId();
        // todo 校验用户是否合法
        User user = userService.getById(userId);
        return ResultUtils.success(user);
    }

    @DeleteMapping("/delete")
    public BaseResponse<Boolean> deleteUsers(@RequestBody int id, HttpServletRequest request) {
        // 仅管理员可以删除
        if (!userService.isAdmin(request)) {
            return ResultUtils.error(ErrorCode.NOT_AUTH);
        }

        if (id <= 0) {
            return ResultUtils.error(ErrorCode.PARAMS_ERROR);
        }
        Boolean b = userService.removeById(id);
        return ResultUtils.success(b);
    }

    /**
     * 根据标签搜索用户
     * @param tagNameList
     * @return
     */
    @GetMapping("/search/tags")
    public BaseResponse<List<User>> searchUserByTags(@RequestParam(required = false) List<String> tagNameList) {
        if(CollectionUtils.isEmpty(tagNameList)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        List<User> userList = userService.searchUserByTags(tagNameList);
        return ResultUtils.success(userList);
    }

    /**
     * 获取主页推荐数据
     * @param pageSize
     * @param pageNum
     * @param request
     * @return
     */
    @GetMapping("/recommend")
    public BaseResponse<Page<User>> recommendUsers(long pageSize, long pageNum, HttpServletRequest request) {
        if (pageSize <= 0 || pageNum <=0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "分页参数错误");
        }
        User loginUser = userService.getLoginUser(request);
        // 如果有缓存, 直接读缓存
        String redisKey = String.format("yangpao:user:recommend:%s", loginUser.getId());
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        Page<User> userPage = (Page<User>) valueOperations.get(redisKey);
        if (userPage != null) {
            return ResultUtils.success(userPage);
        }
        // 无数据, 查询数据库
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        userPage = userService.page(new Page<>(pageNum, pageSize), queryWrapper);
        // 写缓存
        try {
            valueOperations.set(redisKey, userPage, 5, TimeUnit.MINUTES);
        } catch (Exception e) {
            log.error("redis set key error", e);
        }
        return ResultUtils.success(userPage);
    }

}
