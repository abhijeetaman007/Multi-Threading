package com.company;

class InsufficientAmountException extends RuntimeException
{
    public InsufficientAmountException(double amount,double cost)
    {
        System.out.println("You have insufficient amount");
        System.out.println("You require more "+(cost-amount));
    }
}



class Account
{
    private String name;
    private double amount;

    public void setName(String name) {
        this.name = name;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    Account(String name)
    {
        this.name = name;
        this.amount = 0;
    }

    public synchronized void  purchase(String item,double cost)
    {
        //Check If amount is sufficient
        double a = getAmount(); //Like fetching from DB
        System.out.println("Thread "+Thread.currentThread().getName()+" is trying to purchase "+item+ " cost "+cost);
        if(a<cost)
        {
            throw new InsufficientAmountException(amount,cost);
        }
        a -= cost;  //Even this looks atomic but it is not, it will be divided into many internally and context switch can happen anywhere
        System.out.println("Thread "+Thread.currentThread().getName()+" purchases "+item+ " and has amount "+amount);
        setAmount(a); //Like updating DB
    }

}


public class Main {

    public static void main(String[] args) throws  Exception {
//      SampleThreading s = new SampleThreading();
//      s.testSampleThreading();


        //Demonstrating Concurrency Issue
        //(We get to see concurrency issues in this code, by putting synchronised (in java) before method it will just allow one thread to enter that method)
        Account alex = new Account("Alex");
        for(int i=0; i < 1000 ; i++)
        {
            alex.setAmount(100);
            Thread t1 = new Thread(()->{
                alex.purchase("Food",40);
            });
            Thread t2 = new Thread(()->{
                alex.purchase("Clothes",20);
            });
            t1.start();
            t2.start();  //Starting both the thread
            t1.join();
            t2.join();  // Waiting for both the threads to wait before main thread terminates.
            System.out.println("Amount left : "+alex.getAmount());
        }

    }
}
