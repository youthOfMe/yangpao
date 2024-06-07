package com.niuma.yangpao.once;

import com.alibaba.excel.EasyExcel;

public class ImportExcel {

    /**
     * 读取数据
     */
    public static void main(String[] args) {
        // todo 记得改为自己的测试文件
        String fileName = "E:\\星球项目\\yupao-backend\\src\\main\\resources\\testExcel.xlsx";
//        readByListener(fileName);
        EasyExcel.read(fileName);
    }
}
