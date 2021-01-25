package com.ggb.toclass;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class JavaToclass {
    public static String classPath = "D:\\workspace_idea\\fk\\safkNew\\target\\classes";
    public static String javaPath = "C:\\Users\\aa\\Desktop\\safk\\src\\main\\java";
    static int count = 0;

    public static void main(String[] args) throws Exception {
        count = 0;
        File file = new File(javaPath);
        createPath(file);//将java文件替换为class文件
        delFile(javaPath);//删除原来的java文件
        System.out.println("共替换" + count + "个文件");
    }


    public static void createPath(File file)
            throws Exception {
        if (file.exists()) {
            File[] files = file.listFiles();
            for (File f : files)
                if (f.isDirectory()) {
                    createPath(f);
                } else {
                    if (!f.getName().endsWith(".java"))
                        continue;
                    String temp = f.getName().substring(0, f.getName().indexOf(".java"));

                    String path = classPath + f.getPath().substring(javaPath.length(), f.getPath().lastIndexOf("\\") + 1);
                    File[] filels = new File(path).listFiles();
                    for (int i = 0; i < filels.length; i++) {
                        if ((!filels[i].isFile()) || (
                                (!(temp + ".class").equals(filels[i].getName())) && (filels[i].getName().indexOf(temp + "$") != 0))) {
                            continue;
                        }
                        count += 1;
                        File classfile = new File(path + filels[i].getName());
                        String path_1 = f.getPath().substring(0, f.getPath().lastIndexOf("\\") + 1).replace("\\src\\main\\java\\", "\\src\\main\\webapp\\WEB-INF\\classes\\");
                        File file_1 = new File(path_1);
                        if (!file_1.exists()) {
                            file_1.mkdirs();
                        }
                        File javafile = new File(path_1 + filels[i].getName());
                        System.out.println("将" + classfile.getPath() + "移动至" + javafile.getPath());
                        copyFile(classfile, javafile);
                    }
                }
        }
    }

    public static void copyFile(File f1, File f2)
            throws Exception {
        int length = 2097152;
        FileInputStream in = new FileInputStream(f1);
        FileOutputStream out = new FileOutputStream(f2);
        byte[] buffer = new byte[length];
        int len = 0;
        while ((len = in.read(buffer)) != -1) {
            out.write(buffer, 0, len);
        }
        in.close();
        out.flush();
        out.close();
    }

    public static boolean delFile(String path) {
        boolean flag = true;
        try {
            File myDelFile = new File(path);
            if (myDelFile.exists()) {
                if (myDelFile.isDirectory()) {
                    File[] fs = myDelFile.listFiles();
                    for (int i = 0; i < fs.length; i++) {
                        delFile(fs[i].getAbsolutePath());
                    }
                }
                flag = myDelFile.delete();
            }
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    public static void changeDirectory(File file) {

    }
}
