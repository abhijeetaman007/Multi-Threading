package com.company;

//Singleton means one, so singleton class means class with just one object
// We might need such a class to store DB conflict may be
class Singleton
{
    private static Singleton INSTANCE = null;
    private Singleton(){   // We made constructor such that object cant we made directly
        // can have very costly or important computation
        // API calls
        // sending money etc.
    };

    //Below implementation solves our problem but can be problematic as it will increase the runtime as all threads will wait even when a object is already created and
    //INSTANCE is set
//    public synchronized static Singleton getInstance()  //class level synchronised
//    {
//        if(INSTANCE==null)
//        {
//            INSTANCE = new Singleton();
//        }
//        return INSTANCE;
//    }

    //Then what to do? use synchronised block this might help
    //Below code solve some aspect but doesn't always create one object
//    public static Singleton getInstance()
//    {
//        if(INSTANCE==null)
//        {
////            ---------- if context switch happens here then it can create more than one objects which fails purpose of singleton class.
//            synchronized (Singleton.class)
//            {
//                INSTANCE = new Singleton();
//            }
//        }
//        return INSTANCE;
//    }

    //Best solution is using double null design for Singleton class
    // ** It is very important that critical section should be as small as possible
    public static Singleton getInstance()
    {
        //Here in first check for null if INSTANCE is set there isn't need for thread to wait
        if(INSTANCE==null)
        {
            synchronized (Singleton.class)
            {
                //If INSTANCE was not set and few thread enters first check the  won't enter synchronised block hence all threads wont wait if INSTANCE is set and if not then
                //even due to context switch there wont be ant concurrency issue due to synchronised block and will always create one block
                if(INSTANCE==null)
                {
                    INSTANCE = new Singleton();
                }
            }
        }
        return  INSTANCE;
    }
}





public class SingletonDemo {
    public static void main(String[] args) {
        //Just having getInstance function doesn't resolve our problem as there can be multiple thread trying to create thread at a time
        //So we need to synchronize in a way
        Singleton s1 = Singleton.getInstance();
        Singleton s2 = Singleton.getInstance();
        System.out.println(s1);
        System.out.println(s2);
    }
}
