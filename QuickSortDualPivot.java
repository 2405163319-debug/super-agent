import java.util.Arrays;

/**
 * 快速排序 —— 双轴（dual-pivot）分区实现。
 *
 * 思路：每轮取区间两端两个元素作为基准（保证 pivot1 <= pivot2），
 * 一次扫描把数组划分成“< pivot1 | [pivot1, pivot2] | > pivot2”三段，
 * 每轮能多确定一批元素的位置，实践中的比较次数比单轴方案更少
 * （JDK 的 Arrays.sort 对基本类型数组即采用这一思路）。
 *
 * 时间复杂度：平均 O(n log n)，最坏 O(n^2)
 * 空间复杂度：O(log n) 递归栈
 * 稳定性：不稳定
 */
public class QuickSortDualPivot {

    public static void sort(int[] a) {
        if (a == null || a.length < 2) {
            return;
        }
        sort(a, 0, a.length - 1);
    }

    private static void sort(int[] a, int left, int right) {
        if (left >= right) {
            return;
        }
        if (a[left] > a[right]) {
            swap(a, left, right);
        }
        int pivot1 = a[left];
        int pivot2 = a[right];

        if (pivot1 == pivot2 && middleAllEqual(a, left, right, pivot1)) {
            return; // 区间内元素全部相同，无需继续
        }

        int less = left + 1;   // a[left+1..less-1]   <  pivot1
        int great = right - 1; // a[great+1..right-1] >  pivot2
        int k = less;          // a[less..k-1] ∈ [pivot1, pivot2]，a[k..great] 待考察
        while (k <= great) {
            if (a[k] < pivot1) {
                swap(a, k, less++);
                k++;
            } else if (a[k] > pivot2) {
                while (k < great && a[great] > pivot2) {
                    great--;
                }
                swap(a, k, great);
                great--;
                if (a[k] < pivot1) { // 从右侧换来的元素仍可能小于 pivot1
                    swap(a, k, less++);
                }
                k++;
            } else {
                k++;
            }
        }
        // 两个基准归位
        swap(a, left, less - 1);
        swap(a, right, great + 1);
        sort(a, left, less - 2);   // < pivot1
        sort(a, less, great);      // [pivot1, pivot2]
        sort(a, great + 2, right); // > pivot2
    }

    private static boolean middleAllEqual(int[] a, int left, int right, int pivot) {
        for (int i = left + 1; i <= right - 1; i++) {
            if (a[i] != pivot) {
                return false;
            }
        }
        return true;
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
