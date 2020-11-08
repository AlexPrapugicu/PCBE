package com.denisfeier.lib;

public class ThreadPool {
    private final int poolSize;
    private final CustomWorker[] workers;

    private final CustomQueue<Runnable> queue;

    public ThreadPool(int poolSize) {
        this.poolSize = poolSize;
        this.workers = new CustomWorker[poolSize];
        this.queue = new CustomQueue<Runnable>(10);

        for(int i= 0; i < poolSize; i++){
            workers[i] = new CustomWorker(i,true,this.queue);
            workers[i].start();
        }
    }

    public void doTask(Runnable task) {
        System.out.println("IM DOING MY TASK!" + Thread.currentThread().getName());
            queue.put(task);
    }

    public void stopPool(){
        for (int i = 0; i < poolSize; i++){
            workers[i].setActive(false);
        }
    }
    public void refreshPool(){
        for(int i = 0; i < poolSize; i++){
            if (!workers[i].getActive()){
                workers[i].setActive(true);
                workers[i].start();
            }
        }
    }
}
