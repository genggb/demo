package zzl.z20201210;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @ClassName TestThreadPool
 * @Description TODO
 * @Author aa
 * @Date 2021-1-11 11:33
 * @Version 1.0
 */
public class TestThreadPool {
//    private static int CORES = Runtime.getRuntime().availableProcessors();
//    public static final ThreadPoolExecutor POOL = new ThreadPoolExecutor(
//            // 核心线程数
//            CORES * 2,
//            // 最大线程数
//            CORES * 3,
//            // 非核心线程持有时间
//            120,
//            // 持有时间参数单位
//            TimeUnit.SECONDS,
//            // 任务队列，常用有界阻塞队列，需要考虑任务规模
//            new ArrayBlockingQueue<Runnable>(1000),
//            // 自定义线程工程，通常设置一个便于日志追踪的名字
//            new ThreadFactory() {
//                @Override
//                public Thread newThread(Runnable r) {
//                    return new Thread(r, "示例线程");
//                }
//            }
//            // 忽略了最后个拒绝策略，默认抛出任务被拒绝异常
//    );

    public static void main(String[] args) throws Exception {
        String[] filePaths = {"11.txt", "22.txt", "33.txt", "44.txt", "55.txt", "66.txt"};
        //1. 创建线程池
        ExecutorService pool = Executors.newFixedThreadPool(6);
//        CyclicBarrier barrier = new CyclicBarrier(filePaths.length);
//        CountDownLatch countDownLatch = new CountDownLatch(filePaths.length);
        //模拟获取最大的卷宗序号
        Integer maxXh = TestThreadPool.getMaxJzXh();
        List<Future<String>> list = new ArrayList<>();
        for (int i = 0; i < filePaths.length; i++) {
            Future<String> future = pool.submit(new putJz(maxXh + i, filePaths[i]));
            list.add(future);
        }
//        countDownLatch.await();
        for (Future<String> future : list) {
            System.out.println(future.get());
        }
        pool.shutdown();
        System.out.println("入卷完成");
    }

    public static Integer getMaxJzXh() {
        return (int) (1 + Math.random() * (100 - 1 + 1));
    }

    public static class putJz implements Callable<String> {
        private int xh;
        private String filePath;
        private CyclicBarrier barrier;
        private CountDownLatch countDownLatch;

        public putJz(int xh, String filePath, CyclicBarrier barrier, CountDownLatch countDownLatch) {
            super();
            this.xh = xh;
            this.filePath = filePath;
            this.barrier = barrier;
            this.countDownLatch = countDownLatch;
        }

        public putJz(int xh, String filePath) {
            super();
            this.xh = xh;
            this.filePath = filePath;
        }

        @Override
        public String call() throws Exception {
            try {
//                System.out.println("开始" + filePath);
//                barrier.await();
                File file = new File(filePath);
                System.out.println("入卷操作=>文件" + filePath + "入卷成功,xh=" + xh);
                return "文件" + filePath + "入卷成功对应序号为" + xh;
            } catch (Exception e) {
                e.printStackTrace();
                return "fail";
            } finally {
//                countDownLatch.countDown();
            }
        }
    }
}

