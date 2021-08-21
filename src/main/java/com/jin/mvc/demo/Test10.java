package com.jin.mvc.demo;

import com.alibaba.fastjson.JSON;
import lombok.Data;

/**
 * @author wu.jinqing
 * @date 2021年07月27日
 */
@Data
public class Test10 {
        /**
         *  出发机场三字码,必填
         **/
        private String depCode;

        /**
         *  到达机场三字码,必填
         **/
        private String arrCode;

        /**
         * 航班日期,格式：yyyy-MM-dd 必填
         **/
        private String depDt;

        /**
         * 航班号实际航班号不带航空公司 必填
         **/
        private String flightNo;

        /**
         *  渠道号,必填
         **/
        private String channelCode;

        /**
         * 承运方 必填
         **/
        private String carrier;

        /**
         *  舱等
         **/
        private String cabinCode;

    public static void main(String[] args) {
        Test10 d = new Test10();

        d.setDepCode("SHA");
        d.setArrCode("PEK");
        d.setDepDt("2021-07-28");
        d.setCarrier("MU");
        d.setFlightNo("5099");
        d.setChannelCode("7860");
        d.setCabinCode("Y");

        System.out.println(JSON.toJSONString(d));
    }
}
