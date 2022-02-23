package com.performance.test.lmax.producer;

import com.lmax.disruptor.RingBuffer;
import com.performance.test.common.models.Order;
import com.performance.test.common.models.OrderMessage;

import static com.performance.test.common.CommonConstants.MESSAGE_COUNT;

public class LMaxOrderProducer {

    private RingBuffer<OrderMessage> ringBuffer;

    public LMaxOrderProducer(RingBuffer<OrderMessage> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    private synchronized Order createNewOrder(int orderId) {
      Order order = new Order();
      order.setOrderId(orderId);
      return order;
    }

    public void  publishOrders() {
        for(int i=0; i< MESSAGE_COUNT; i++) {
            long sequence = this.ringBuffer.next();
            OrderMessage orderMessage = this.ringBuffer.get(sequence);
            orderMessage.setOrder(createNewOrder(i));
            this.ringBuffer.publish(sequence);
        }
    }
}
