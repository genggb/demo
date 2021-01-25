package com.tdh.codeReview;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * @ClassName demo
 * @Description TODO
 * @Author aa
 * @Date 2020-8-20 17:08
 * @Version 1.0
 */
public class demo {

    public static void main(String[] args) throws Exception {
//        String str = "|!320191|!10132012101420119905000001|!EBG0000022379804|!20201110|!2|!江宁经济技术开发区人民法院执行款|!|!0|!中国交通银行|!交通银行江宁支行|!江宁经济技术开发区人民法院执行款|!320006637018012035840|!618.00|!|!2|!2|!2|!(2020)苏0191执837号|!案款11发放\n" +
//                "618元为诉讼费|!";
//        String[] strs = str.split("");
        String pk = "_";
        String pk1 = pk.split("_",-1)[1];
        System.out.println(pk1);
    }

    static String readFirstLineFromFile(String path) throws IOException {

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            return br.readLine();
        }
    }
}
