import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 快速排序 —— 随机化基准实现。
 *
 * 思路：每轮随机选取区间内的一个元素作为基准（换到末尾后复用
 * Lomuto 分区）。任何固定输入（如已排序数组）都不再必然触发
 * 最坏情况，期望时间复杂度 O(n log n)，是实践中最常用的防退化手段。
 *
 * 时间复杂度：期望 O(n log n)
 * 空间复杂度：O(log n) 递归栈
 * 稳定性：不稳定
 */
public class QuickSortRandomized {

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

    private static int partition(int[] a, int lo, int hi) {
        int randomIndex = ThreadLocalRandom.current().nextInt(lo, hi + 1);
        swap(a, randomIndex, hi); // 随机基准换到末尾
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
