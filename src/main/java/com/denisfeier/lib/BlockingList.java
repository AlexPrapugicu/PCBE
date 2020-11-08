package com.denisfeier.lib;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BlockingList<T> {
        private final int listSize;
        private List<T> localList;

        private static Lock lock = new ReentrantLock();
        private Condition queueEmpty = lock.newCondition();
        private Condition queueFull = lock.newCondition();

        public BlockingList(int listSize,ArrayList<T> list) {
            this.listSize = listSize;
            this.localList = list;
        }

        public boolean isEmpty(){
            return this.localList.isEmpty();
        }

        public int getListSize(){
            return this.listSize;
        }


        public T getElement(int index){
            return this.localList.get(index);
        }

        public int getIndexOfElement(T element){
            return this.localList.indexOf(element);
        }

        public void put(T element) throws InterruptedException{
            lock.lock();
            System.out.println("BL by::" + Thread.currentThread().getName());
            try{
                while(localList.size() == listSize){
                    queueFull.await();
                }
                localList.add(element);
                Thread.sleep(300);
                queueEmpty.signal();
            }finally {
                System.out.println("Unlocked list::"+ Thread.currentThread().getName());
                lock.unlock();
            }
        }

        public T take(T element) throws InterruptedException{
            lock.lock();
            System.out.println("BL by::" + Thread.currentThread().getName());
            try{
                while(localList.isEmpty()){
                    queueEmpty.await();
                }
                Iterator iterator = localList.iterator();
                while(iterator.hasNext()){
                    T elem = (T)iterator.next();
                    if(elem.equals(element)){
                        iterator.remove();
                        queueFull.signal();
                        return elem;
                    }
                }
                Thread.sleep(300);
            }finally {
                System.out.println("Unlocked list::"+ Thread.currentThread().getName());
                lock.unlock();
            }
            return null;
        }
}
