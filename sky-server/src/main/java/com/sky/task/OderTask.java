package com.sky.task;

import com.sky.entity.Orders;
import com.sky.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class OderTask {
    /**
     * TODO 可优化点：1.redis/MQ 处理订单超时情况。 2.使用最新版本JJWT，老版存在缺陷 3.使用虚拟机配置Redis远程连接（已实现） 4.添加日销售额表优化统计报表 5.根据销量优化套餐显示顺序。
     *
     */
    @Autowired
    private OrderMapper orderMapper;

    @Scheduled(cron = "0 */5 * * * *")
    public void processTimeoutOrder(){
        LocalDateTime time = LocalDateTime.now().plusMinutes(-15);

        List<Orders> ordersList = orderMapper.getByStatusAndOrderTime(Orders.PENDING_PAYMENT, time);
        for (Orders orders : ordersList) {
            orders.setStatus(Orders.CANCELLED);
            orders.setCancelReason("订单超时");
            orders.setCancelTime(LocalDateTime.now());
            orderMapper.update(orders);
        }

    }

    @Scheduled(cron = "0 0 1 * * ?")
    public void processDeilveryOrder(){
        LocalDateTime time = LocalDateTime.now().plusMinutes(-60);
        List<Orders> ordersList = orderMapper.getByStatusAndOrderTime(Orders.DELIVERY_IN_PROGRESS, time);
        for (Orders orders : ordersList) {
            orders.setStatus(Orders.COMPLETED);
            orders.setCancelTime(LocalDateTime.now());
            orderMapper.update(orders);
        }
    }
}
