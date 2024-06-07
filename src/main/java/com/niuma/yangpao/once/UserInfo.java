package com.niuma.yangpao.once;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * 用户信息
 */
@Data
public class UserInfo {

    /**
     * id
     */
    @ExcelProperty("成员编号")
    private Long id;

    /**
     * 用户昵称
     */
    @ExcelProperty("用户昵称")
    private String username;
}