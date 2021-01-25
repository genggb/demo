package zzl;

import java.util.ArrayList;

/**
 * 给定一个二叉搜索树, 找到该树中两个指定节点的最近公共祖先。
 * 百度百科中二叉搜索树的定义为：若它的左子树不空，则左子树上所有结点的值均小于它的根结点的值； 若它的右子树不空，则右子树上所有结点的值均大于它的根结点的值。
 * 百度百科中最近公共祖先的定义为：对于有根树T的两个结点u、v，最近公共祖先LCA(T,u,v)表示一个结点x，满足x是u、v的祖先且x的深度尽可能大（一个节点也可以是它自己的祖先）。
 * <p>
 * 列子:
 * 12
 * 5     18
 * 2  9  15   19
 * 1  4
 * <p>
 * 示例 1: u = 1, v = 15
 * 输出: 12
 * 解释: 节点 1和节点 15 的最近公共祖先是 12。
 * <p>
 * 示例 2: u = 4, v = 5
 * 输出: 5
 * 解释: 节点 4和节点5的最近公共祖先是5, 因为根据定义最近公共祖先节点可以为节点本身。
 */


/**
 * 定义树节点
 * val   节点值
 * left  左节点
 * right 右节点
 */
class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int x) {
        val = x;
    }

    public TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }

}

public class BinarySearchTree {
    // 深度为3的节点
    static TreeNode t1 = new TreeNode(1, null, null);
    static TreeNode t4 = new TreeNode(4, null, null);
    // 深度为2的节点
    static TreeNode t2 = new TreeNode(2, t1, t4);
    static TreeNode t9 = new TreeNode(9, null, null);
    static TreeNode t15 = new TreeNode(15, null, null);
    static TreeNode t19 = new TreeNode(19, null, null);
    // 深度为1的节点
    static TreeNode t5 = new TreeNode(5, t2, t9);
    static TreeNode t18 = new TreeNode(18, t15, t19);
    // 根节点
    static TreeNode t12 = new TreeNode(12, t5, t18);
    // 返回节点
    static TreeNode res = null;

    /**
     * 方法1：常规方法
     * 先用递归把找到u,v的路径节点都存到list里面，然后去遍历list找到最近的相同的两个点
     * @param root
     * @param u
     * @param v
     * @return
     */
    public static TreeNode method1(TreeNode root, TreeNode u, TreeNode v) {
        ArrayList<TreeNode> treeNodes_u = new ArrayList<TreeNode>();
        ArrayList<TreeNode> treeNodes_v = new ArrayList<TreeNode>();
        createRoute(root, u, treeNodes_u);
        createRoute(root, v, treeNodes_v);
        for (int i = 0; i < treeNodes_u.size(); i++) {
            for (TreeNode treeNode : treeNodes_v) {
                if (treeNodes_u.get(i).equals(treeNode)) {
                    return treeNode;
                }
            }
        }
        return null;
    }

    /**
     * 将传入节点u的路径都存到list里面
     * @param root
     * @param u
     * @param treeNodes_u
     * @return
     */
    private static boolean createRoute(TreeNode root, TreeNode u, ArrayList<TreeNode> treeNodes_u) {
        if (root == null) {
            return false;
        }
        if (root == u) {
            treeNodes_u.add(root);
            return true;
        }
        if (createRoute(root.left, u, treeNodes_u)) {
            treeNodes_u.add(root);
            return true;
        }
        if (createRoute(root.right, u, treeNodes_u)) {
            treeNodes_u.add(root);
            return true;
        }
        return false;
    }

    /**
     * 方法2：利用搜索二叉树的特性
     * 二叉搜索树的特性，左边的数比右边小，找到一个节点的值的大小在u,v之间，这就是他俩最近的祖先节点
     * @param root
     * @param u
     * @param v
     * @return
     */
    public static TreeNode method2(TreeNode root, TreeNode u, TreeNode v) {
        while (root != null) {
            // 自上而下 找分界点
            if (root.val > u.val && root.val > v.val) {
                root = root.left;     // 在右子树
            } else if (root.val < u.val && root.val < v.val) {
                root = root.right;    // 在左子树
            } else {
                return root;  //一个大于根节点一个小于根节点，说明u,v分别在根节点的左子树和右子树，则当前根节点为最近公共祖先
            }
        }
        return null;
    }

    /**
     * 方法3：类似于方法2，使用递归代替循环
     * @param root
     * @param u
     * @param v
     * @return
     */
    public static TreeNode method3(TreeNode root, TreeNode u, TreeNode v) {
        lca(root, u, v);
        return res;
    }

    private static void lca(TreeNode root, TreeNode u, TreeNode v) {
        if ((root.val - u.val) * (root.val - v.val) <= 0) {
            res = root;
        } else if (root.val < u.val && root.val < v.val) {
            lca(root.right, u, v);
        } else {
            lca(root.left, u, v);
        }
    }


    public static void main(String[] args) {
        System.out.println("示例1，u=1,v=15");
        System.out.println("方法1结果：" + method1(t12, t1, t15).val);
        System.out.println("方法2结果：" + method2(t12, t1, t15).val);
        System.out.println("方法3结果：" + method3(t12, t1, t15).val);
        System.out.println("示例2，u=4,v=5");
        System.out.println("方法1结果：" + method1(t12, t4, t5).val);
        System.out.println("方法2结果：" + method2(t12, t4, t5).val);
        System.out.println("方法3结果：" + method3(t12, t4, t5).val);
    }
}

	
