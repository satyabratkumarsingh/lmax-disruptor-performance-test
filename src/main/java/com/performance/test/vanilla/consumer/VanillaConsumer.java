package com.performance.test.vanilla.consumer;

import com.performance.test.common.models.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

import static com.performance.test.common.CommonConstants.MESSAGE_COUNT;

public class VanillaConsumer {

    Logger logger = LoggerFactory.getLogger(VanillaConsumer.class);

    private final ConcurrentLinkedQueue<Order> queue;
    private final AtomicInteger count = new AtomicInteger(MESSAGE_COUNT);

    public VanillaConsumer(ConcurrentLinkedQueue<Order> queue) {
        this.queue = queue;
    }

    private void logOrder(Order order) {
        //logger.info("Vanilla Consumer, Thread ID {}, OrderId {} ", Thread.currentThread().getId(),
                //order.getOrderId());
    }

    public void consume() {
        while (count.decrementAndGet() > 0 && !queue.isEmpty()) {
            Order order = queue.poll();
            logOrder(order);
        }
    }
}
