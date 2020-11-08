package com.denisfeier.lib;

public class CustomWorker extends Thread{

    private int ID;

    private final CustomQueue<Runnable> queue;
    private boolean active;
    // 1 running | 0 stopped

    public CustomWorker(int ID, boolean active,final CustomQueue<Runnable> queue) {
        this.ID = ID;
        this.active = active;
        this.queue = queue;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean getActive() {
        return active;
    }

    @Override
    public void run() {
        // while available to run
        while(this.active){
                // while queue empty and worker still running wait for queue to fill
                while(queue.isEmpty()){
                    try{
                        System.out.println(this.getName() + " waiting task");
                        this.sleep(500);
                    }catch (Exception e){
                        System.out.println(e);
                    }
                }
                // if it aint empty, get top first item in queue
                Runnable task = null;
                try {
                    System.out.println("took task " + this.getName());
                    task = queue.take();
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (task != null) {
                    System.out.println("running actual task");
                    task.run();
                }
            }
//        }
    }
}
