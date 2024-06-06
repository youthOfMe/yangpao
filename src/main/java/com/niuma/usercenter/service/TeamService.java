package com.niuma.usercenter.service;

import com.niuma.usercenter.model.domain.Team;
import com.baomidou.mybatisplus.extension.service.IService;
import com.niuma.usercenter.model.domain.User;
import com.niuma.usercenter.model.request.TeamUpdateRequest;

/**
* @author 20406
* @description 针对表【team(队伍)】的数据库操作Service
* @createDate 2024-06-05 22:39:33
*/
public interface TeamService extends IService<Team> {

    /**
     * 创建队伍
     * @param team 队伍请求体参数
     * @param loginUser 登录用户
     * @return
     */
    long addTeam(Team team, User loginUser);

    /**
     * 更新队伍
     * @param teamUpdateRequest
     * @param loginUser
     * @return
     */
    boolean updateTeam(TeamUpdateRequest teamUpdateRequest, User loginUser);
}
