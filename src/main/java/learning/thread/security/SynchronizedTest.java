package learning.thread.security;

/**
 * @ClassName SynchronizedTest
 * @Description TODO
 * @Author aa
 * @Date 2021-1-21 17:34
 * @Version 1.0
 */
public class SynchronizedTest implements Runnable {
    private static int count = 0;

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(new SynchronizedTest());
            thread.start();
        }
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("result: " + count);
    }

    @Override
    public void run() {
        synchronized (SynchronizedTest.class) {
            for (int i = 0; i < 1000000; i++)
                count++;
        }
    }
}
