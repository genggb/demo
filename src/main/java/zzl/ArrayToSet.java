package zzl;

import java.util.HashSet;
import java.util.Set;

/**
 * 给定一个整数数组，判断是否存在重复元素。
 * 如果任何值在数组中出现至少两次，函数返回 true。如果数组中每个元素都不相同，则返回 false
 */
public class ArrayToSet {
    public static void main(String[] args) {
        int[] intArray = new int[]{1, 2, 3, 4, 1};
        Set<Integer> set = merge(intArray);
        if (intArray.length > set.size()) {
            System.out.println("有重复数字");
        } else {
            System.out.println("没有重复数字");
        }
    }

    public static Set<Integer> merge(int[] intArray) {
        Set<Integer> set = new HashSet<Integer>();
        for (int i : intArray) {
            set.add(i);
        }
        return set;
    }
}
