package com.niuma.usercenter.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.niuma.usercenter.common.ErrorCode;
import com.niuma.usercenter.exception.BusinessException;
import com.niuma.usercenter.model.domain.Team;
import com.niuma.usercenter.model.domain.User;
import com.niuma.usercenter.service.TeamService;
import com.niuma.usercenter.mapper.TeamMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
* @author 20406
* @description 针对表【team(队伍)】的数据库操作Service实现
* @createDate 2024-06-05 22:39:33
*/
@Service
public class TeamServiceImpl extends ServiceImpl<TeamMapper, Team>
    implements TeamService{

    @Override
    public long addTeam(Team team, User loginUser) {
        final long userId = loginUser.getId();
        // 1. 请求参数是否为空
        if (team == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 2. 是否登录
        if (loginUser == null) {
            throw new BusinessException(ErrorCode.NOT_AUTH);
        }
        // 3. 校验信息
        // 3.1 队伍人数 > 1 且 <= 20
        int maxNum = Optional.ofNullable(team.getMaxNum()).orElse(0);
        if (maxNum < 1 || maxNum > 20) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "队伍人数不满足要求");
        }
        // 3.2 队伍标题长度 <= 20  并且不为空
        String name = team.getName();
        if (StringUtils.isBlank(name) || name.length() > 20) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "队伍标题过长或为空");
        }
        // 3.3 描述长度 <= 512
        String description = team.getDescription();
        if (StringUtils.isNotBlank(description) &&description.length() > 512) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "队伍描述过长");
        }
        // 3.4 status 是否公开 (int) 不默认为 0(公开)
        int status = Optional.ofNullable(team.getStatus()).orElse(0);

        return 0;
    }
}




