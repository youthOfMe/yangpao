package com.niuma.yangpao.service;

import com.niuma.yangpao.model.domain.Team;
import com.baomidou.mybatisplus.extension.service.IService;
import com.niuma.yangpao.model.domain.User;
import com.niuma.yangpao.model.dto.TeamQuery;
import com.niuma.yangpao.model.request.TeamJoinRequest;
import com.niuma.yangpao.model.request.TeamQuitRequest;
import com.niuma.yangpao.model.request.TeamUpdateRequest;
import com.niuma.yangpao.model.vo.TeamUserVO;

import java.util.List;

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

    /**
     * 搜索队伍的功能
     * @param teamQuery
     * @param isAdmin
     * @return
     */
    List<TeamUserVO> listTeams(TeamQuery teamQuery, boolean isAdmin);

    /**
     * 加入队伍
     * @param teamJoinRequest
     * @param loginUser
     * @return
     */
    boolean joinTeam(TeamJoinRequest teamJoinRequest, User loginUser);

    /**
     * 退出队伍
     * @param teamQuitRequest
     * @param loginUser
     * @return
     */
    boolean quitTeam(TeamQuitRequest teamQuitRequest, User loginUser);
}
