package com.example.multithreading.stampedLock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.StampedLock;

public class TryConvertToOptimisticReadExample {

    private StampedLock lock = new StampedLock();

    private  int burgerStock;

    public TryConvertToOptimisticReadExample (int initialStock){
        this.burgerStock = initialStock;

    }


    public void updateQty(String threadName , int qty){
        // first acquire the writeLock

        long stamp = lock.writeLock();
        System.out.println("[" + threadName +  "]" + "Current Qty = " + burgerStock);
        try {
            burgerStock+=qty;
            System.out.println("[" + threadName +  "]" + "Qty Updated "  + "Updated Qty = " + burgerStock );

            // now lets try to convert the write lock to the optimistic read lock

            long orstamp  = lock.tryConvertToOptimisticRead(stamp);
            if(orstamp!=0){
                // conversion is successfully
                stamp = orstamp;
                System.out.println("[" + threadName +  "]" + "Lock is successfully Converted"  );
            }
            else {
                // failed to convert
                // release the write lock
                lock.unlockWrite(stamp);
               }

            System.out.println("[" + threadName +  "]" + "Updated Qty is = " + burgerStock);

        }
        finally {
            // first validate then release
            if(!lock.validate(stamp)){
                lock.unlock(stamp);
            }

        }

    }

    public static void main(String[] args) {
        TryConvertToOptimisticReadExample example  = new TryConvertToOptimisticReadExample(10);
        ExecutorService service = Executors.newFixedThreadPool(5);

        for(int i = 1 ; i <= 5 ; ++i){
            int finalI = i;
            service.submit(() -> example.updateQty("user" + finalI, finalI * 10));
        }

        service.shutdown();
    }
}
