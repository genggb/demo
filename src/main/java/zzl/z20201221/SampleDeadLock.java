package zzl.z20201221;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 死锁示例代码
 * 规避策略：
 * （1）、要求所有线程按指定顺序获取锁；
 * （2）、合并锁，即扩大锁定范围
 */
public class SampleDeadLock {


    public static ReentrantLock MoneyLock = new ReentrantLock();

    public static ReentrantLock payMoneyLock = new ReentrantLock();

    public static int money = 5;

    public static class UpdateThreadA implements Runnable {
        @Override
        public void run() {
            System.out.println("++++++++收钱啦:" + this.toString());
            MoneyLock.lock();
            try {
                System.out.println("等待银行入账");
                TimeUnit.SECONDS.sleep(2);
                System.out.println("++++++++不能支付了:" + this.toString());
                payMoneyLock.lock();
                money = money + 100;
                payMoneyLock.unlock();
                MoneyLock.unlock();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static class UpdateThreadB implements Runnable {
        @Override
        public void run() {
            System.out.println("=========交钱啦:" + this.toString());
            payMoneyLock.lock();
            try {
                System.out.println("=========先别收钱:" + this.toString());
                MoneyLock.lock();
                int payMoney = 100;
                if (money > payMoney) {
                    MoneyLock.lock();
                    money = money - payMoney;
                }
                MoneyLock.unlock();
                payMoneyLock.unlock();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        List<Future> futures = new ArrayList<>();
        for (int i = 0; i < 1; i++) {
            futures.add(SampleCreate.POOL.submit(new UpdateThreadA()));
            futures.add(SampleCreate.POOL.submit(new UpdateThreadB()));
        }

        // 关闭线程池观察效果，日常使用无特殊要求无需关闭
        SampleCreate.POOL.shutdown();
    }
}
