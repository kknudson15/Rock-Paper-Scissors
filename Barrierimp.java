import java.util.Random;
import java.util.Scanner;

interface Barrier
{
    public void waitForOthers();
    public void freeAll();
}
//used as the wait for the threads
class Barrierimp implements Barrier
{
    private int barriersize;
    private int waitingT;
    private int save;

    public Barrierimp(int thread)
    {
        waitingT = 0;
        barriersize = thread;
        save = 0;
    }

    //has the threads wait until all threads finish
    //and then resets for the next run
    //not sure if perfectly sycronized, but so far it works.
    public synchronized void waitForOthers()
    {
        waitingT++;

        if(waitingT < barriersize)
        {
            try
            {
                wait();
            }catch (InterruptedException e) {}
        }
        else
        {
            waitingT = 0;
            notifyAll();
        }
    }
    //used for nothing
    public void freeAll()
    {
        waitingT = barriersize;
    }
}
