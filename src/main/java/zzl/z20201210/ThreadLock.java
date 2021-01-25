package zzl.z20201210;

/**
 * @ClassName ThreadLock
 * @Description TODO
 * @Author aa
 * @Date 2021-1-11 10:13
 * @Version 1.0
 */
public class ThreadLock implements Runnable {
    private static A a = new A();
    private static B b = new B();

    private boolean flag = false;
    public void run() {
        if (flag) {
            synchronized (a) {
                a.say();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (b) {
                    b.say();
                }
            }
        } else {
            synchronized (b) {
                b.say();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (a) {
                    a.say();
                }
            }
        }
    }

    public static void main(String args[]) {
        ThreadLock t1 = new ThreadLock();
        ThreadLock t2 = new ThreadLock();
        t1.flag = true;
        t2.flag = false;
        Thread thA = new Thread(t1);
        Thread thB = new Thread(t2);
        thA.start();
        thB.start();
    }

    public static class A {
        public void say() {
            System.out.println("A等待B完成再开始工作");
        }

        public void get() {
            System.out.println("A完成");
        }
    };

    public static class B {
        public void say() {
            System.out.println("B等待A完成再开始工作");
        }

        public void get() {
            System.out.println("B完成");
        }
    }

}

