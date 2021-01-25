package zzl.z20201221;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * 创建线程并追踪结果
 *
 * @author tdh-ly
 */
public class SampleCreate {
    private static int CORES = Runtime.getRuntime().availableProcessors();
    private static ThreadLocal<Integer> threadLocal = new ThreadLocal<>();

    public static final ThreadPoolExecutor POOL = new ThreadPoolExecutor(
            // 核心线程数
            CORES * 2,
            // 最大线程数
            CORES * 3,
            // 非核心线程持有时间
            120,
            // 持有时间参数单位
            TimeUnit.SECONDS,
            // 任务队列，常用有界阻塞队列，需要考虑任务规模
            new ArrayBlockingQueue<Runnable>(1000),
            // 自定义线程工程，通常设置一个便于日志追踪的名字
            new ThreadFactory() {
                @Override
                public Thread newThread(Runnable r) {
                    return new Thread(r, "示例线程");
                }
            }
            // 忽略了最后个拒绝策略，默认抛出任务被拒绝异常
    );

    public static void main(String[] args) {
        List<Future<Boolean>> futures = new ArrayList<>(50);
        for (int i = 0; i < 50; i++) {
            // 提交线程并返回Future追踪结果
            Future<Boolean> submit = POOL.submit(() -> {
                return System.currentTimeMillis() % 2 == 0;
            });
            futures.add(submit);
        }

        for (Future<?> future : futures) {
            try {
                Object result = future.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
    }
}
