package com.github.hcsp.multithread;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class ProducerConsumer3 {
    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<Integer> queue = new LinkedBlockingDeque<>(1);
        BlockingQueue<Integer> signalQueue = new LinkedBlockingDeque<>(1);
        Producer producer = new Producer(queue, signalQueue);
        Consumer consumer = new Consumer(queue, signalQueue);

        producer.start();
        consumer.start();

        producer.join();
        producer.join();
    }

    public static class Producer extends Thread {
        BlockingQueue<Integer> queue;
        BlockingQueue<Integer> signalQueue;

        public Producer(BlockingQueue<Integer> queue, BlockingQueue<Integer> signalQueue) {
            this.queue = queue;
            this.signalQueue = signalQueue;
        }

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                Integer r = new Random().nextInt();
                try {
                    System.out.println("product:" + r);
                    queue.put(r);
                    signalQueue.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static class Consumer extends Thread {
        BlockingQueue<Integer> queue;
        BlockingQueue<Integer> signalQueue;

        public Consumer(BlockingQueue<Integer> queue, BlockingQueue<Integer> signalQueue) {
            this.queue = queue;
            this.signalQueue = signalQueue;
        }

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                try {
                    System.out.println("consumer:" + queue.take());
                    signalQueue.put(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
