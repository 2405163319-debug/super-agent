import java.util.Arrays;

/**
 * 快速排序 —— 三路划分实现（Dijkstra 荷兰国旗问题解法）。
 *
 * 思路：每轮把区间划分为“< 基准 | = 基准 | > 基准”三段，
 * 等于基准的元素一次性归位、不再参与后续递归，
 * 因此数组中存在大量重复元素时效率显著提升。
 *
 * 时间复杂度：平均 O(n log n)；全部元素相同时为 O(n)
 * 空间复杂度：O(log n) 递归栈
 * 稳定性：不稳定
 */
public class QuickSortThreeWay {

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
        int pivot = a[lo + (hi - lo) / 2];
        int lt = lo;  // a[lo..lt-1]   <  pivot
        int gt = hi;  // a[gt+1..hi]  >  pivot
        int i = lo;   // a[lt..i-1]   == pivot，a[i..gt] 待考察
        while (i <= gt) {
            if (a[i] < pivot) {
                swap(a, lt++, i++);
            } else if (a[i] > pivot) {
                swap(a, i, gt--);
            } else {
                i++;
            }
        }
        sort(a, lo, lt - 1);
        sort(a, gt + 1, hi);
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
