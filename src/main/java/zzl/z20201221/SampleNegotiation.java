package zzl.z20201221;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 线程协调通信
 * 例：所有工人先集合，然后排队领取工具开始干活，等待所有人完成工作后结束
 */
public class SampleNegotiation {
    private static final Lock toolLock = new ReentrantLock();
    private static int i = 0;

    public static class Worker implements Callable<String> {
        private int no;
        private CyclicBarrier barrier;
        private CountDownLatch countDownLatch;
        private String filepath;
        private Connection conn;

        public Worker(int no, CyclicBarrier barrier, CountDownLatch countDownLatch, String filePath, Connection conn) {
            this.no = no;
            this.barrier = barrier;
            this.countDownLatch = countDownLatch;
            this.filepath = filePath;
            this.conn = conn;
        }

        @Override
        public String call() {

            // 1、等待所有人集合完毕
            try {
                barrier.await();
                System.out.println("文件路径：" + filepath + ",文件序号-" + no + "：@" + System.currentTimeMillis());
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }


            PreparedStatement st = null;
            // 3、干活
            try {
                File file = new File(filepath);
                FileInputStream in = new FileInputStream(file);
                byte[] b = new byte[1024];
                int n;
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                while ((n = in.read(b)) != -1) {
                    baos.write(b, 0, n);
                }
                in.close();
                byte[] data = baos.toByteArray();
                baos.close();
                int i = 1;
                st = conn.prepareStatement("insert into EAJ_JZ_3(AHDM,XH,NR) VALUES(?,?,?)");
                st.setString(i++, "115120201002000003");
                st.setString(i++, String.valueOf(no));
                st.setBytes(i++, data);
                int x = st.executeUpdate();
                System.out.println(x);
                return String.valueOf(x);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            } finally {
                try {
                    st.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                countDownLatch.countDown();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        // 注意，使用CyclicBarrier时如果需等待的线程超过线程池核心线程数，可能会无限等待因为线程池在核心线程全部休眠时不会创建新线程

        String jzFilePath = "C:\\Users\\aa\\Desktop\\20201207\\新疆高院案款系统需求20201126（杜一飞）支付确认功能启用状态调整需求.doc\n" +
                "C:\\Users\\aa\\Desktop\\20201207\\江苏高院_电子票据需求20201204（顾原翔）.doc\n" +
                "C:\\Users\\aa\\Desktop\\20201207\\诉讼费案款管理系统博思银行模式增加退费票据需求20201022（谭建超）.doc\n" +
                "C:\\Users\\aa\\Desktop\\20201207\\贵州高院案款系统需求20201127（黄秀荣）.doc\n" +
                "C:\\Users\\aa\\Desktop\\20201207\\费款系统需求20201204（刘郁） - ).doc\n" +
                "C:\\Users\\aa\\Desktop\\20201207\\辽阳县法院预缴通知.doc\n";
        String[] jzpathList = jzFilePath.split("\n");
        int numOfWorkers = jzpathList.length;
        CyclicBarrier barrier = new CyclicBarrier(numOfWorkers);
        CountDownLatch countDownLatch = new CountDownLatch(numOfWorkers);
        List<Future<String>> list = new ArrayList<>();
        Connection conn = null;
        conn = getConnFromJdbcPropertiesFile("ssfk");
        long time = System.currentTimeMillis();
        for (int i = 0; i < jzpathList.length; i++) {

            list.add(SampleCreate.POOL.submit(new Worker(i, barrier, countDownLatch, jzpathList[i], conn)));
        }

        // 等待所有工作完成，另外一种方法是使用Future.get()
        countDownLatch.await();
        long time2 = System.currentTimeMillis() - time;
        System.out.println("耗时:" + time2 / 1000 + "s");
        try {
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("所有人都完成了工作！");

        // 关闭线程池观察效果，日常使用无特殊要求无需关闭
        //SampleCreate.POOL.shutdown();
    }

    public static Connection getConnFromJdbcPropertiesFile(String jdbcname) {
        Connection conn = null;
        String jdbcPath = "";

        try {
            String driver = "com.sybase.jdbc3.jdbc.SybDriver";
            String url = "jdbc:sybase:Tds:192.168.0.224:5000/ssfk?charset=cp936";
            String user = "ecourt";
            String password = "ecourtsa";

            Class.forName(driver);
            conn = DriverManager.getConnection(url, user, password);
        } catch (Exception var9) {
            var9.printStackTrace();
        }

        return conn;
    }
}


