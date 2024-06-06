package com.niuma.usercenter.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.niuma.usercenter.model.domain.UserTeam;
import com.niuma.usercenter.service.UserTeamService;
import com.niuma.usercenter.mapper.UserTeamMapper;
import org.springframework.stereotype.Service;

/**
* @author 20406
* @description 针对表【user_team(用户队伍关系)】的数据库操作Service实现
* @createDate 2024-06-06 21:41:19
*/
@Service
public class UserTeamServiceImpl extends ServiceImpl<UserTeamMapper, UserTeam>
    implements UserTeamService{

}




