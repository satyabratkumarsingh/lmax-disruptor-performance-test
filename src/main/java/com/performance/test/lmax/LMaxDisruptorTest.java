package com.performance.test.lmax;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.lmax.disruptor.util.DaemonThreadFactory;
import com.performance.test.common.models.OrderMessage;
import com.performance.test.lmax.consumer.LMaxOrderConsumer;
import com.performance.test.lmax.producer.LMaxOrderProducer;

import java.util.concurrent.CompletableFuture;

import static com.performance.test.common.CommonConstants.ONE_BILLION;
import static com.performance.test.common.CommonConstants.RING_BUFFER_SIZE;

public class LMaxDisruptorTest {

    public static void main(String[] args) {
        long start = System.nanoTime();
        Disruptor<OrderMessage> disruptor = new Disruptor<>(
                OrderMessage.EVENT_FACTORY,
                RING_BUFFER_SIZE,
                DaemonThreadFactory.INSTANCE,
                ProducerType.SINGLE,
                new YieldingWaitStrategy());
        disruptor.handleEventsWith(new LMaxOrderConsumer());
        disruptor.start();
        RingBuffer<OrderMessage> ringBuffer = disruptor.getRingBuffer();
        LMaxOrderProducer producer = new LMaxOrderProducer(ringBuffer);
        CompletableFuture<Void> producerFuture = CompletableFuture.runAsync(producer::publishOrders);
        producerFuture.join();
        double duration = (double) (System.nanoTime() - start) / ONE_BILLION;
        String message = String.format("Total duration : %.2f", duration);
        System.out.println(message);

    }
}
