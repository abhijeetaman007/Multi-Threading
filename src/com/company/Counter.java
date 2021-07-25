package com.company;
//Class implementing counter
public class Counter {
    private int value=0;
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void increment()
    {
        //Explicitly waiting for higher chance of concurrency bugs
        int v = value;
        try {
            Thread.sleep(0);
        }catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        value=v+1;   //This is most atomic which can be thought but in reality it isn't atomic it will compile down to several lines of codes leading to concurrency issue
    }

    public synchronized void  newIncrement()  //Using Synchronised keyword
    {
        value++;
    }

    //Explanation of above synchronised keyword

    private final Object key = new Object();  //Creating an object of super class of all class: object class
    public void newIncrement1()
    {
       synchronized (key)    //since key is final object of object class(object class is just use because we don't want to make a general object) so it is just one
       // for all object of class counter so here there will be a lock on this object key and can be used just once at a time or by one thread
       {
           // critical section
           value++;
       } //Here lock from key is released so other thread can now use key and acquire lock and work on critical section
    }

    //Another way to implement
    public void newIncrement2()
    {
        synchronized (this)  //Here we have put a lock on the object using this so all threads of this object will access below code one by one as this object has lock
        {
            // critical section
            value++;
        }
    }

    //Implement lock at class level
    public void newIncrement3()
    {
        synchronized (Counter.class)  //All objects with all thread will use it once at a time since lock is there at class
        {
            //critical section
            value++;
        }
    }

    //Another implementation of class level lock
    private final static Object key1 = new Object();
    //Since key1 acquires lock and it is final and static so among all classes it will be same so there will be lock at class level, so just one thread of object at a time
    public void newIncrement4()
    {
        synchronized (key1)
        {
            value++;
        }
    }

    //Even using synchronised keyword with method can implement a class level lock by using static and synchronised
    public synchronized static void  newIncrement5()
    {
        //Critical Section
    }

}


//Currently we worked on 1-system lock
// But we even have locks on distributed systems lock