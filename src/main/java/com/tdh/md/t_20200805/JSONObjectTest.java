package com.tdh.md.t_20200805;


import net.sf.json.JSONObject;

/**
 * @ClassName JSONObjectTest
 * @Description TODO
 * @Author aa
 * @Date 2020-8-5 20:32
 * @Version 1.0
 */
public class JSONObjectTest {

    public static void main(String[] args) {
        String jsonStr = "{\"testa\":null,\"testb\":{\"testb_1\":\"11\"}}";
        JSONObject data = JSONObject.fromObject(jsonStr);


        /**
         * 1、当json对象中没有某个标签时，使用getJSONObject,getString等将会报错
         * 建议使用data.optJSONObject()，data.optString()
         */
        System.out.println("案例1：");
        //**正确写法
        System.out.println("testc=" + data.optString("testc"));
        System.out.println("testa=" + data.optJSONObject("testa"));

        //**错误写法(不存在testc会报错)
//        System.out.println("testc="+data.getString("testc"));
        System.out.println("testa=" + data.getJSONObject("testa"));

        /**
         * 2、当json字符串经过JSONArray.fromObject,JSONObject.fromObject转换之后，
         * 从json对象或者json数组中取出来的数据建议不可以再使用，
         * 经过fromObject后都是新的对象，跟最初获取到的不是同一个对象了
         */
        System.out.println("案例2：");
        //**正确写法
        JSONObject testb_1 = data.optJSONObject("testb");
        JSONObject testb_2 = data.optJSONObject("testb");
        System.out.println(testb_1 == testb_2);

        //**错误写法
        JSONObject _testb_1 = JSONObject.fromObject(data.get("testb"));
        JSONObject _testb_2 = JSONObject.fromObject(data.get("testb"));
        System.out.println(_testb_1 == _testb_2);

        /**
         * 3、当json对象中有个参数是null，使用JSONArray.fromObject,JSONObject.fromObject转换之后，
         * 得到的不是null而是一个对象，需要用isNullObject()方法进行判断
         * 下赋相关源码：
         * if (object != null && !JSONUtils.isNull(object)) {
         *      ......
         * } else {
         *     return new JSONObject(true);
         * }
         *
         * public JSONObject(boolean isNull) {
         *     this();
         *     this.nullObject = isNull;
         * }
         *
         */

        System.out.println("案例3：");
        //        JSONObject _testa = JSONObject.fromObject(data.get("testa"));
        JSONObject _testa = data.optJSONObject("testa");
        System.out.println(_testa);
        //**正确写法
        if (_testa.isNullObject()) {
            System.out.println("_testa.isNullObject()：_testa为空");
        }

        //**错误写法
        if (_testa == null) {
            System.out.println("_testa == null：_testa为空");
        }

    }
}
