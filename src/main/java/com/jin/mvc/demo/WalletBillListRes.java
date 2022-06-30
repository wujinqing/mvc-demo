package com.jin.mvc.demo;

import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author wu.jinqing
 * @date 2022年03月08日
 */
@Data
public class WalletBillListRes extends WalletBillListBaseRes {
    /**
     * 查询时间：2019.06.01 - 2019.08.30
     */
    private String queryDateText;

    /**
     * 账单列表
     */
    private Set<BillItemDto> bills;
    private Map<String,    WalletBillListBaseRes> billsmap;
    private WalletBillListRes bill;

    /**
     * 当前页
     */
    private int page;

    /**
     * 是否有下一页
     */
    private boolean hasNextPage;
}
