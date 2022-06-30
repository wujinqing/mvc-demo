package com.jin.mvc.demo;

import lombok.Data;

/**
 * @author wu.jinqing
 * @date 2022年03月08日
 */
@Data
public class BillItemDto {
    /**
     * 图标地址
     */
    private String logoUrl;

    /**
     * 标题： 退款、提现等
     */
    private String title;

    /**
     * 副标题： 积分商城戴森。。。
     */
    private String subTitle;

    /**
     * 时间：2019-08-05 11:34:00
     */
    private String date;

    /**
     * 价格：+ 50.00 元
     */
    private String price;

    /**
     * 价格字体颜色: #FF0000， 为空就默认字体颜色
     */
    private String priceColor;

    /**
     * 划掉的价格(原价)：1980.00 元
     */
    private String originalPrice;

}
