package com.denisfeier.lib;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CustomQueue<T> {
    private final int queueSize;
    private Queue<T> queue = new LinkedList<T>();

    private static Lock lock = new ReentrantLock();
    private Condition queueEmpty = lock.newCondition();
    private Condition queueFull = lock.newCondition();


    public CustomQueue(int queueSize) {
        this.queueSize = queueSize;
    }

    public boolean isEmpty(){
        return this.queue.isEmpty();
    }

    public void put(T element){
        lock.lock();
        System.out.println("Queued by::" + Thread.currentThread().getName());
        try {
            while (queue.size() == queueSize) {
                queueFull.await();
            }
            Thread.sleep(300);
            queue.add(element);
            queueEmpty.signal();
        }catch(InterruptedException e){
            e.printStackTrace();
        }finally {
            System.out.println("Unlocked Q::" + Thread.currentThread().getName());
            lock.unlock();
        }
    }

    public T take() throws InterruptedException{
        lock.lock();
        System.out.println("Queued by::" + Thread.currentThread().getName());
        try {
            while (queue.isEmpty()) {
                queueEmpty.await();
            }
            Thread.sleep(300);
            T item = queue.remove();
            queueFull.signal();
            return item;
        }catch (InterruptedException e){
            e.printStackTrace();
        }finally {
            System.out.println("Unlocked Q::" + Thread.currentThread().getName());
            lock.unlock();
        }
        return null;
    }
}
