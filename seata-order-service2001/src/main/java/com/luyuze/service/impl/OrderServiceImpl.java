package com.luyuze.service.impl;

import com.luyuze.dao.OrderDao;
import com.luyuze.domain.Order;
import com.luyuze.service.AccountService;
import com.luyuze.service.OrderService;
import com.luyuze.service.StorageService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private StorageService storageService;

    @Autowired
    private AccountService accountService;

    @Override
    @GlobalTransactional(name = "lyz-create-order", rollbackFor = Exception.class)
    public void create(Order order) {
        log.info("------开始新建订单");
        orderDao.create(order);

        log.info("------订单微服务开始调用库存，做扣减Count");
        storageService.decrease(order.getProductId(), order.getCount());
        log.info("------订单微服务开始调用库存，做扣减end");

        log.info("------订单微服务开始调用账户，做扣减Money");
        accountService.decrease(order.getUserId(), order.getMoney());
        log.info("------订单微服务开始调用账户，做扣减end");

        log.info("------修改订单状态开始");
        orderDao.update(order.getUserId(), 0);
        log.info("------修改订单状态结束");

        log.info("------下订单结束，^_^");
    }
}
