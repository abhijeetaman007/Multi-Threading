package com.company;

// Using Peterson Solution to have just one thread in critical section
// All modern system uses multi-cores so independent statements might be present on different cores and even Peterson's solution fails to resolve
// Hence peterson solution works on single core or in theory

class Peterson
{
//    int totalThread=2;
    boolean flag[] = new boolean[2];
    int turn = -1;
    void acquire(int thread)
    {
        flag[thread] = true;
        turn = 1-thread;
        while(turn == 1-thread && flag[1-thread] );

        //spin-lock: Busy Waiting - wastes resources    --> Another disadvantage of mutex/lock

    }
    void release(int thread)
    {
        flag[thread]=false;
    }
}


public class petersonDemo {
    public static void main(String[] args) throws InterruptedException{
        Counter c = new Counter();
        int n = 10000;
        Peterson lock = new Peterson();

        //Thread 1
        Thread t1 = new Thread(()->{
           for(int i = 0;i < n; i++)
           {
               lock.acquire(0);
               c.increment();
               lock.release(0);
           }
        });
        //Thread 2
        Thread t2 =  new Thread(()->{
            for(int i = 0;i < n; i++)
            {
                lock.acquire(1);
                c.increment();
                lock.release(1);
            }
        });
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(c.getValue());
    }
}
