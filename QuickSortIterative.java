import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

/**
 * 快速排序 —— 非递归（显式栈）实现。
 *
 * 思路：用显式栈保存待排序区间，代替系统递归栈。每次分区后
 * 只把较大的子区间压栈、较小的子区间继续循环处理，
 * 使栈深度保持在 O(log n)，彻底避免递归过深导致栈溢出。
 *
 * 时间复杂度：平均 O(n log n)，最坏 O(n^2)
 * 空间复杂度：O(log n) 显式栈
 * 稳定性：不稳定
 */
public class QuickSortIterative {

    public static void sort(int[] a) {
        if (a == null || a.length < 2) {
            return;
        }
        Deque<int[]> stack = new ArrayDeque<>();
        int lo = 0;
        int hi = a.length - 1;
        while (lo < hi || !stack.isEmpty()) {
            if (lo < hi) {
                int p = partition(a, lo, hi);
                if (p - lo > hi - p) {
                    // 左侧更长：左侧压栈，右侧继续
                    stack.push(new int[]{lo, p - 1});
                    lo = p + 1;
                } else {
                    // 右侧更长：右侧压栈，左侧继续
                    stack.push(new int[]{p + 1, hi});
                    hi = p - 1;
                }
            } else {
                int[] range = stack.pop();
                lo = range[0];
                hi = range[1];
            }
        }
    }

    private static int partition(int[] a, int lo, int hi) {
        swap(a, lo + (hi - lo) / 2, hi); // 取中间元素为基准，避免有序输入退化
        int pivot = a[hi];
        int i = lo;
        for (int j = lo; j < hi; j++) {
            if (a[j] < pivot) {
                swap(a, i++, j);
            }
        }
        swap(a, i, hi);
        return i;
    }

    private static void swap(int[] a, int i, int j) {
        if (i == j) {
            return;
        }
        int t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    public static void main(String[] args) {
        int[] a = {5, 2, 9, 1, 7, 3, 8, 2, 6};
        System.out.println("排序前: " + Arrays.toString(a));
        sort(a);
        System.out.println("排序后: " + Arrays.toString(a));
    }
}
