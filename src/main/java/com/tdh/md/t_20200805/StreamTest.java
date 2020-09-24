package com.tdh.md.t_20200805;

import java.io.*;

/**
 * @ClassName Stream
 * @Description TODO
 * @Author aa
 * @Date 2020-8-7 9:10
 * @Version 1.0
 */
public class StreamTest {
    public static void main(String[] args) throws Exception {
        /**
         * IO流基本都是装饰者模式：子类的close操作基本都是调用FilterInputStream的close，
         * 所以会将InputStream关闭
         */

//        public void close() throws IOException {
//            byte[] buffer;
//            while ( (buffer = buf) != null) {
//                if (bufUpdater.compareAndSet(this, buffer, null)) {
//                    InputStream input = in;
//                    in = null;
//                    if (input != null)
//                        input.close();
//                    return;
//                }
//                // Else retry in case a new buf was CASed in fill()
//            }
//        }
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(new File("")));
        bis.close();

        InputStream is = new FileInputStream("");
        InputStreamReader isr = new InputStreamReader(is, "utf-8");
        BufferedReader br = new BufferedReader(isr);
        br.close();

        DataInputStream dis = new DataInputStream(new FileInputStream(new File("")));
        dis.close();
    }
}
