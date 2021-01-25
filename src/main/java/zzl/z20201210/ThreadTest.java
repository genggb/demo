package zzl.z20201210;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @ClassName ThreadTest
 * @Description TODO
 * @Author aa
 * @Date 2021-1-11 14:18
 * @Version 1.0
 */
public class ThreadTest {
    public static void main(String[] args) {
        StringBuffer s1 = new StringBuffer();
        StringBuffer s2 = new StringBuffer();

        ExecutorService pool = Executors.newFixedThreadPool(6);
//        CyclicBarrier barrier = new CyclicBarrier(filePaths.length);
//        CountDownLatch countDownLatch = new CountDownLatch(filePaths.length);
        //模拟获取最大的卷宗序号
        for (int i = 0; i < 100; i++) {
            pool.submit(new Runnable(){
                @Override
                public void run() {
//                    synchronized (s1) {
//                        try {
//                            Thread.sleep(1000);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                        s1.append("a");
                        s2.append("1");
                        System.out.println("1:" + s2);
//                    }
                }
            });
        }

//        new Thread(){
//            @Override
//            public void run() {
//                synchronized (s1){
//                    s1.append("a");
//                    s2.append("1");
//                    System.out.println("1:"+s2);
////                    try {
////                        Thread.sleep(100);
////                    } catch (InterruptedException e) {
////                        e.printStackTrace();
////                    }
////                    synchronized (s2){
////                        s1.append("b");
////                        s2.append("2");
////                        System.out.println(s1);
////                        System.out.println(s2);
////                    }
//                }
//            }
//        }.start();
//
//        new Thread(){
//            @Override
//            public void run() {
//                synchronized (s1){
//                    s1.append("a");
//                    s2.append("1");
//                    System.out.println("2:"+s2);
////                    try {
////                        Thread.sleep(100);
////                    } catch (InterruptedException e) {
////                        e.printStackTrace();
////                    }
////                    synchronized (s2){
////                        s1.append("b");
////                        s2.append("2");
////                        System.out.println(s1);
////                        System.out.println(s2);
////                    }
//                }
//            }
//        }.start();
//
////        new Thread(new Runnable() {
////            @Override
////            public void run() {
////                synchronized (s2){
////                    s1.append("c");
////                    s2.append("3");
////                    try {
////                        Thread.sleep(100);
////                    } catch (InterruptedException e) {
////                        e.printStackTrace();
////                    }
////                    synchronized (s1){
////                        s1.append("d");
////                        s2.append("4");
////
////                        System.out.println(s1);
////                        System.out.println(s2);
////                    }
////                }
////            }
////        }).start();
    }
}

