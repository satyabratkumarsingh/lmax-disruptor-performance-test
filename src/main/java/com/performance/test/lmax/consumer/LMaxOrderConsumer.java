package com.performance.test.lmax.consumer;

import com.lmax.disruptor.EventHandler;
import com.performance.test.common.models.Order;
import com.performance.test.common.models.OrderMessage;

public class LMaxOrderConsumer implements EventHandler<OrderMessage> {

    @Override
    public void onEvent(OrderMessage orderMessage, long sequence, boolean endOfBatch) throws Exception {
        Order order = orderMessage.getOrder();
        orderMessage.clear();
    }
}
