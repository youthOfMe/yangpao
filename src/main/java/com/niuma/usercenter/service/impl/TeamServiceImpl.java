package com.niuma.usercenter.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.niuma.usercenter.model.domain.Team;
import com.niuma.usercenter.service.TeamService;
import com.niuma.usercenter.mapper.TeamMapper;
import org.springframework.stereotype.Service;

/**
* @author 20406
* @description 针对表【team(队伍)】的数据库操作Service实现
* @createDate 2024-06-05 22:39:33
*/
@Service
public class TeamServiceImpl extends ServiceImpl<TeamMapper, Team>
    implements TeamService{

}




