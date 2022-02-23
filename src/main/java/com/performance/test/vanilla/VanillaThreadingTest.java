package com.performance.test.vanilla;

import com.performance.test.common.models.Order;
import com.performance.test.vanilla.consumer.VanillaConsumer;
import com.performance.test.vanilla.producer.VanillaProducer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentLinkedQueue;

import static com.performance.test.common.CommonConstants.ONE_BILLION;

public class VanillaThreadingTest {

    public static void main(String[] args) {

        long start = System.nanoTime();
        ConcurrentLinkedQueue<Order> queue = new ConcurrentLinkedQueue<>();
        VanillaConsumer consumer = new VanillaConsumer(queue);
        VanillaProducer producer = new VanillaProducer(queue);

        List<CompletableFuture> producerThreads  = new ArrayList<>();
        List<CompletableFuture> consumerThreads  = new ArrayList<>();

        //Start 20 producers and 20 consumers
        for(int i=0; i< 20; i++) {
            producerThreads.add(CompletableFuture.runAsync(producer::publishOrder));
            consumerThreads.add(CompletableFuture.runAsync(consumer::consume));
        }
        CompletableFuture.allOf(producerThreads.toArray(new CompletableFuture[0])).join();
        CompletableFuture.allOf(consumerThreads.toArray(new CompletableFuture[0])).join();
        double duration = (double) (System.nanoTime() - start) / ONE_BILLION;
        String message = String.format("Total duration : %.2f", duration);
        System.out.println(message);

    }
}
