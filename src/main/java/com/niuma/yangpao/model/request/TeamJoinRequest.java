package com.niuma.yangpao.model.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户请求加入队伍请求体
 */
@Data
public class TeamJoinRequest implements Serializable {

    /**
     * id
     */
    private Long teamId;

    /**
     * 密码
     */
    private String password;
}
