package learning.thread.security;

/**
 * @ClassName VolatileTest
 * @Description TODO
 * @Author aa
 * @Date 2021-1-21 15:57
 * @Version 1.0
 */
public class VolatileTest {
    public static volatile int t = 0;

    /**
     * 线程1读取了t的值，假如t = 0。之后线程2读取了t的值，此时t = 0。
     * 然后线程1执行了加1的操作，此时t = 1。但是这个时候，处理器还没有把t = 1的值写回主存中。
     * 这个时候处理器跑去执行线程2，注意，刚才线程2已经读取了t的值，所以这个时候并不会再去读取t的值了，所以此时t的值还是0，
     * 然后线程2执行了对t的加1操作，此时t =1 。
     * 这个时候，就出现了线程安全问题了，两个线程都对t执行了加1操作，但t的值却是1。所以说，volatile关键字并不一定能够保证变量的安全性。
     */
    public static void main(String[] args){

        Thread[] threads = new Thread[10];
        for(int i = 0; i < 10; i++){
            //每个线程对t进行1000次加1的操作
            threads[i] = new Thread(new Runnable(){
                @Override
                public void run(){
                    for(int j = 0; j < 10; j++){
                        System.out.println(Thread.currentThread()+":"+t);
                        t = t + 1;
                    }
                }
            });
            threads[i].start();
        }
    }
}
