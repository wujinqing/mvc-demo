package com.jin.mvc.demo.service.impl;

import com.jin.mvc.demo.service.MyService;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * @author wu.jinqing
 * @date 2020年11月04日
 */
@Component
@Transactional
public class MyServiceImpl implements MyService {
    @Override
    public String doSomething() {

        TransactionSynchronizationManager.registerSynchronization(new MyTransactionSynchronizationAdapter());
//        throw new RuntimeException("test");
        return "doSomething";
    }
}
