package com.company;
//Below mentioned is needed for multithreading:
//- Runnable Interface
//- Thread Class

class MyClass implements Runnable{
    @Override
    public void run() {
        System.out.println("I am in a thread and running");
        System.out.println(Thread.currentThread().getName());
    }
}
class SampleThreading
{
    public void testSampleThreading()
    {
        //Implementing Runnable
        System.out.println("Implementing Runnable");
        MyClass obj = new MyClass();
        obj.run();
        System.out.println(Thread.currentThread().getName());

        //Till now what's done above isn't threading it is just implementing interface runnable and calling it's function run

        //Creating Thread
        System.out.println("Creating Thread");
        Thread t = new Thread(obj);   //Thread class will accept only runnable interface so we implemented runnable
        t.start(); // Instead of calling obj.run() as above we will call t.start()

        //As above in class just one method is being used so instead of creating class separately we will create anonymous inner class -- class without name and one object
        Runnable newRunnableObj = new Runnable() {
            @Override
            public void run() {
                System.out.println("Inside Runnable Anonymous Inner Class");
            }
        };
        newRunnableObj.run();

        //As we implemented above Runnable we can pass to thread as well as we arent doing anything else with thread.
        Thread t1 = new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("New Thread spawned,using Runnable Anonymous Inner Class");
                        System.out.println(Thread.currentThread().getName());
                    }
                }
        );
        t1.start(); //Starting Above thread t1

        //Now as we notice just one run method is being is used so we can use anonymous function  -- below is tne shortest and most used implementation
        Thread t2 = new Thread(()->{
            System.out.println("New Thread spawned,using anonymous function");
            System.out.println(Thread.currentThread().getName());
        });
        t2.start(); //Starting thread t2
    }
}

