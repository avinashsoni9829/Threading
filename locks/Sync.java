package com.example.demo.learning;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;

public class Sync extends AbstractQueuedSynchronizer {


    // lock is acquired if state = 0 and set it to 1
    public boolean tryAcquire(int acquire){
         assert acquire == 1;

         if(compareAndSetState(0 , 1)){
              setExclusiveOwnerThread(Thread.currentThread());
              return true;
         }
         return false;
    }

    // released the lock by setting the state = 0

    protected boolean tryRelease(int release){
         assert  release == 1;
         if(!isHeldExclusively()){
              throw new IllegalMonitorStateException();
         }

         setExclusiveOwnerThread(null);
         setState(0);
         return true;
    }


    public boolean isLocked(){
         return getState() != 0;
    }

    public boolean isHeldExclusively(){
         return getExclusiveOwnerThread()  == Thread.currentThread();
    }

    public Condition newCondition(){
         return new ConditionObject();
    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
       s.defaultReadObject();
       setState(0);
    }


}
