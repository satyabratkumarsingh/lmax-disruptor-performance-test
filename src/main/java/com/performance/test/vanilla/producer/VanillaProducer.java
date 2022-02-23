package com.performance.test.vanilla.producer;

import com.performance.test.common.models.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

import static com.performance.test.common.CommonConstants.MESSAGE_COUNT;

public class VanillaProducer {

    Logger logger = LoggerFactory.getLogger(VanillaProducer.class);

    private final ConcurrentLinkedQueue<Order> queue;
    private final AtomicInteger count = new AtomicInteger(0);

    public VanillaProducer(ConcurrentLinkedQueue<Order> queue) {
        this.queue = queue;
    }

    private Order createNewOrder(int orderId) {
        Order order = new Order();
        order.setOrderId(orderId);
        return order;
    }
    public void publishOrder() {
        while(count.getAndIncrement() <= MESSAGE_COUNT){
            queue.offer(createNewOrder(count.get()));
        }
    }

}
