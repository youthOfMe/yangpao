package com.niuma.yangpao.model.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户退出队伍
 */
@Data
public class TeamQuitRequest implements Serializable {

    /**
     * id
     */
    private Long teamId;
}
