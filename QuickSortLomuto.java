import java.util.Arrays;

/**
 * 快速排序 —— 经典递归实现（Lomuto 分区）。
 *
 * 思路：每轮选取区间最后一个元素作为基准（pivot），把小于基准的元素
 * 交换到区间前端，最后将基准放到分界点，再对左右子区间递归排序。
 *
 * 时间复杂度：平均 O(n log n)，最坏 O(n^2)（如已按序排列的输入）
 * 空间复杂度：O(log n) 递归栈
 * 稳定性：不稳定
 */
public class QuickSortLomuto {

    public static void sort(int[] a) {
        if (a == null || a.length < 2) {
            return;
        }
        sort(a, 0, a.length - 1);
    }

    private static void sort(int[] a, int lo, int hi) {
        if (lo >= hi) {
            return;
        }
        int p = partition(a, lo, hi);
        sort(a, lo, p - 1);
        sort(a, p + 1, hi);
    }

    /** Lomuto 分区：以 a[hi] 为基准，返回基准最终所在下标。 */
    private static int partition(int[] a, int lo, int hi) {
        int pivot = a[hi];
        int i = lo; // 下一个“小于 pivot”的元素应放的位置
        for (int j = lo; j < hi; j++) {
            if (a[j] < pivot) {
                swap(a, i++, j);
            }
        }
        swap(a, i, hi); // 基准归位
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
