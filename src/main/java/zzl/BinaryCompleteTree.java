package zzl;


/**
 * 给出一个完全二叉树，求出该树的节点个数。
 * 说明：
 * 完全二叉树的定义如下：在完全二叉树中，除了最底层节点可能没填满外，其余每层节点数都达到最大值，并且最下面一层的节点都集中在该层最左边的若干位置。
 * 若最底层为第 h 层，则该层包含 1~ 2h 个节点。
 * 示例:
 * 输入:
 * 1
 * / \
 * 2   3
 * / \  /
 * 4  5 6
 * <p>
 * 输出: 6
 */
public class BinaryCompleteTree {
    // 深度为2的节点
    static TreeNode t4 = new TreeNode(2, null, null);
    static TreeNode t5 = new TreeNode(5, null, null);
    static TreeNode t6 = new TreeNode(6, null, null);
    // 深度为1的节点
    static TreeNode t2 = new TreeNode(2, t4, t5);
    static TreeNode t3 = new TreeNode(3, t6, null);
    // 根节点
    static TreeNode t1 = new TreeNode(1, t2, t3);

    /**
     * 方法一：
     * 完全二叉树的高度可以直接通过不断地访问左子树就可以获取
     * 判断左右子树的高度:
     * 如果相等说明左子树是满二叉树, 然后进一步判断右子树的节点数(最后一层最后出现的节点必然在右子树中)
     * 如果不等说明右子树是深度小于左子树的满二叉树, 然后进一步判断左子树的节点数(最后一层最后出现的节点必然在左子树中)
     **/
    public static int countNodes(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int ld = getDepth(root.left);
        System.out.println("左节点深度：" + ld);
        int rd = getDepth(root.right);
        System.out.println("右节点深度：" + rd);
        if (ld == rd) {
            return 1 + (1 << ld) - 1 + countNodes(root.right); // 1(根节点) + (1 << ld)-1(左完全左子树节点数) + 右子树节点数量
        } else {
            return 1 + (1 << rd) - 1 + countNodes(root.left);  // 1(根节点) + (1 << rd)-1(右完全右子树节点数) + 左子树节点数量
        }
    }

    //取深度
    private static int getDepth(TreeNode r) {
        int depth = 0;//根节点深度为0
        while (r != null) {
            depth++;
            r = r.left;
        }
        return depth;
    }


    //方法二：直接遍历节点
    public int countNodes2(TreeNode root) {
        if (root == null) {
            return 0;
        }
        return 1 + countNodes(root.left) + countNodes(root.right);
    }


    //a<<b：左移运算    等于 a*2的b次方
    public static void main(String[] args) {
        System.out.println((1 << 3) - 1);//满二叉树的节点数=(2^k) -1，其中k为深度
        //System.out.println("节点数为："+countNodes(t1));
    }
}
