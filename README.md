# lmax-disruptor-performance-test


A test to compare performance between using ConcurrentLinkedQueue & LMAX disruptor.

The test are to send 50M messages to be consumed.

In Vanilla test, it uses ConcurrentLinkedQueue, starts 20 parallel producers & consumers, while LMAX it uses one producer and multiple subscribers.

I see substantial improvement using LMAX (1.8 vs 20.56 secs).

Time to switch to LMAX then :) 
