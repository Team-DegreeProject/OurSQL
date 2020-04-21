package storage.Lock_regulator;

import java.io.File;
import java.util.concurrent.locks.Lock;

import storage.Storage.XMLUtils;

public class test {
    LockRegulator lockRegulator;

    public test(LockRegulator lr) {
        lockRegulator = lr;
    }

    public class a implements  Runnable{
        public a(){

        }

        @Override
        public void run() {
            lockRegulator.tableUnlock("a");
        }
    }

    public class b implements Runnable{

        @Override
        public void run() {
            System.out.println(lockRegulator.AddLock("a",Configuration.SHARELOCK));
        }
    }

    public static void main(String[] args) {
        LockRegulator lr = new LockRegulator();
        test t = new test(lr);
        lr.AddLock("a",Configuration.EXCLUSIVELOCK);


        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    System.out.println(lr.AddLock("a",Configuration.SHARELOCK));
                }

            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    System.out.println("in!!!!!");
                    lr.tableUnlock("a");
                    System.out.println(lr.getSize());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }).start();

//        t1.start();
//        t2.start();
//        lockRegulator.AddLock("a",Configuration.SHARELOCK);
//        while (true){
//            System.out.println(lockRegulator.AddLock("a",Configuration.SHARELOCK));
//        }
    }

}
