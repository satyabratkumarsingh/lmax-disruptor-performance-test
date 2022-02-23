package com.performance.test.common.models;

import com.lmax.disruptor.EventFactory;

public class OrderMessage {

    private Order order;

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public void clear() {
        this.order = null;
    }

    public static final EventFactory<OrderMessage> EVENT_FACTORY = OrderMessage::new;
}
