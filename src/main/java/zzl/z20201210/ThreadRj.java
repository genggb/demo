package zzl.z20201210;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @ClassName ThreadRj
 * @Description TODO
 * @Author aa
 * @Date 2021-1-11 10:37
 * @Version 1.0
 */
public class ThreadRj {
    public static final ThreadPoolExecutor POOL = new ThreadPoolExecutor(10, 30, 2000, TimeUnit.MILLISECONDS,
            new ArrayBlockingQueue<Runnable>(1000), new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, "入卷线程");
        }
    });

    public static class Rj implements Callable<String> {
        private int xh;
        private String filePath;
        private CyclicBarrier barrier;
        private CountDownLatch countDownLatch;

        public Rj(int xh, String filePath, CyclicBarrier barrier, CountDownLatch countDownLatch) {
            super();
            this.xh = xh;
            this.filePath = filePath;
            this.barrier = barrier;
            this.countDownLatch = countDownLatch;
        }

        @Override
        public String call() throws Exception {
            // 在所有线程都已经在此 barrier 上调用 await 方法前，将一直等待
//            try {
//                System.out.println("文件" + filePath + "入卷成功,xh=" + xh);
//                barrier.await();
//                System.out.println("所有文件入卷完成"+filePath);
//                return "success";
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//                return "fail";
//            } catch (BrokenBarrierException e) {
//                e.printStackTrace();
//                return "fail";
//            }

            // 入卷操作
            try {
//                File file = new File(filePath);
                //....入卷
//                Thread.sleep(5000);
                System.out.println("文件" + filePath + "入卷成功,xh=" + xh);
                return "success";
            } catch (Exception e) {
                e.printStackTrace();
                return "fail";
            } finally {
                // 计数递减
                countDownLatch.countDown();
            }


        }
    }

    public static void main(String[] args) {
        try {
            String[] filePaths = { "11.txt", "22.txt", "33.txt", "44.txt", "55.txt", "66.txt" };

            CyclicBarrier barrier = new CyclicBarrier(filePaths.length);
            CountDownLatch countDownLatch = new CountDownLatch(filePaths.length);

            List<Future<String>> list = new ArrayList<Future<String>>();
            for (int i = 0; i < filePaths.length; i++) {
                list.add(POOL.submit(new Rj(i, filePaths[i], barrier, countDownLatch)));
            }
            //使当前线程进入等待状态直到计数器为零，或线程被中断
            countDownLatch.await();
            System.out.println("所有文件入卷完成2");
            POOL.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

