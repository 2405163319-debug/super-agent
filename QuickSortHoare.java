import java.util.Arrays;

/**
 * 快速排序 —— Hoare 分区实现。
 *
 * 思路：双指针分别从两端向中间扫描，左侧遇到大于等于基准、
 * 右侧遇到小于等于基准的元素就交换，指针相遇处即分割点。
 * 基准取区间中间元素。与 Lomuto 分区相比交换次数更少，
 * 且分割点不一定等于基准本身的位置。
 *
 * 时间复杂度：平均 O(n log n)，最坏 O(n^2)
 * 空间复杂度：O(log n) 递归栈
 * 稳定性：不稳定
 */
public class QuickSortHoare {

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
        sort(a, lo, p);      // 注意：Hoare 分区的分割点 p 仍属于左半部分
        sort(a, p + 1, hi);
    }

    private static int partition(int[] a, int lo, int hi) {
        int pivot = a[lo + (hi - lo) / 2];
        int i = lo - 1;
        int j = hi + 1;
        while (true) {
            do {
                i++;
            } while (a[i] < pivot);
            do {
                j--;
            } while (a[j] > pivot);
            if (i >= j) {
                return j;
            }
            swap(a, i, j);
        }
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
