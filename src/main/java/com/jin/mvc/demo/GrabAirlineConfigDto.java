package com.jin.mvc.demo;

import com.alibaba.fastjson.JSON;
import lombok.Data;

import java.util.Arrays;
import java.util.List;

/**
 * @author wu.jinqing
 * @date 2022年05月15日
 */
@Data
public class GrabAirlineConfigDto {
    /**
     * 航程类型: OW 单程, RT 往返, MT 联程
     */
    private String tripType;
    /**
     * 出发机场三字码
     */
    private String depAirportCode;

    /**
     * 到达机场三字码
     */
    private String arrAirportCode;

    /**
     * 航班号， 多个航班号用逗号分隔: MU501,MU502
     */
    private String flightNoGroup;

    /**
     * 航班出发日期，格式yyyyMMdd：如：20220519 或 20220519-20220529 或 20220519-20220529;20220619-20220629
     */
    private String depDate;

    public GrabAirlineConfigDto(String tripType, String depAirportCode, String arrAirportCode, String flightNoGroup, String depDate) {
        this.tripType = tripType;
        this.depAirportCode = depAirportCode;
        this.arrAirportCode = arrAirportCode;
        this.flightNoGroup = flightNoGroup;
        this.depDate = depDate;
    }

    public static void main(String[] args) {
        GrabAirlineConfigDto dto1 = new GrabAirlineConfigDto("OW", "PVG", "DLC","MU5625","20220519");

        List<GrabAirlineConfigDto> list = Arrays.asList(dto1);

        System.out.println(JSON.toJSONString(list));
    }

}
