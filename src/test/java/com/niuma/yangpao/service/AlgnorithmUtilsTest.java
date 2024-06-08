package com.niuma.yangpao.service;

import com.niuma.yangpao.utils.AlgorithmUtils;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

/**
 * 算法工具类测试
 */
public class AlgnorithmUtilsTest {

    @Test
    void test() {
        String str1 = "鱼皮是狗";
        String str2 = "鱼皮不是猪";
        String str3 = "鱼皮是猫不是狗";
        int score1 = AlgorithmUtils.minDistance(str1, str2);
        int score2 = AlgorithmUtils.minDistance(str1, str3);
        System.out.println(score1);
        System.out.println(score2);
    }

    @Test
    void testCompareTags() {
        List<String> list1 = Arrays.asList("JAVA", "cpp", "python");
        List<String> list2 = Arrays.asList("JAVA", "cpp", "男", "大二");
        List<String> list3 = Arrays.asList("男");
        int score1 = AlgorithmUtils.minDistance(list1, list2);
        int score2 = AlgorithmUtils.minDistance(list1, list3);
        System.out.println(score1);
        System.out.println(score2);
    }
}
