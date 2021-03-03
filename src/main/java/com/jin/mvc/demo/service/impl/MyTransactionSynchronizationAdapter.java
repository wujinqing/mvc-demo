package com.jin.mvc.demo.service.impl;

import org.springframework.transaction.support.TransactionSynchronizationAdapter;

/**
 * @author wu.jinqing
 * @date 2020年11月16日
 */
public class MyTransactionSynchronizationAdapter extends TransactionSynchronizationAdapter {
    @Override
    public void afterCommit() {
        System.out.println("afterCommit");
    }
}
