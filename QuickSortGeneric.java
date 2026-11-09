import java.util.Arrays;

/**
 * 快速排序 —— 泛型实现。
 *
 * 思路：Lomuto 分区 + “三取一”（median-of-three）基准选取，
 * 取区间首、中、尾三个元素的中位数作为基准，
 * 大幅降低有序输入退化为 O(n^2) 的概率。
 * 适用于任意实现了 Comparable 的对象数组。
 *
 * 时间复杂度：平均 O(n log n)，最坏 O(n^2)
 * 空间复杂度：O(log n) 递归栈
 * 稳定性：不稳定
 */
public class QuickSortGeneric {

    public static <T extends Comparable<? super T>> void sort(T[] a) {
        if (a == null || a.length < 2) {
            return;
        }
        sort(a, 0, a.length - 1);
    }

    private static <T extends Comparable<? super T>> void sort(T[] a, int lo, int hi) {
        if (lo >= hi) {
            return;
        }
        swap(a, medianOf(a, lo, lo + (hi - lo) / 2, hi), hi); // 三取一基准放到末尾
        int p = partition(a, lo, hi);
        sort(a, lo, p - 1);
        sort(a, p + 1, hi);
    }

    private static <T extends Comparable<? super T>> int partition(T[] a, int lo, int hi) {
        T pivot = a[hi];
        int i = lo;
        for (int j = lo; j < hi; j++) {
            if (a[j].compareTo(pivot) < 0) {
                swap(a, i++, j);
            }
        }
        swap(a, i, hi);
        return i;
    }

    /** 返回 a[i]、a[j]、a[k] 三者中位数所在的下标。 */
    private static <T extends Comparable<? super T>> int medianOf(T[] a, int i, int j, int k) {
        if (a[i].compareTo(a[j]) < 0) {
            if (a[j].compareTo(a[k]) < 0) {
                return j;                                // i < j < k
            }
            return a[i].compareTo(a[k]) < 0 ? k : i;     // i < k <= j 或 k <= i < j
        }
        if (a[i].compareTo(a[k]) < 0) {
            return i;                                    // j <= i < k
        }
        return a[j].compareTo(a[k]) < 0 ? k : j;         // j < k <= i 或 k <= j <= i
    }

    private static <T> void swap(T[] a, int i, int j) {
        if (i == j) {
            return;
        }
        T t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    public static void main(String[] args) {
        String[] words = {"banana", "apple", "cherry", "date", "fig", "elderberry", "grape", "apple"};
        System.out.println("排序前: " + Arrays.toString(words));
        sort(words);
        System.out.println("排序后: " + Arrays.toString(words));
    }
}
