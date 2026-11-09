import java.util.Arrays;
import java.util.Random;

/**
 * 各快速排序实现的随机化正确性测试。
 *
 * 覆盖：空数组、单元素、全相同、已排序、逆序、负数、
 * 大量重复（小值域）与普通随机数组，结果与 Arrays.sort 逐一比对；
 * 另含 100 万随机整数的性能冒烟测试。
 *
 * 运行：javac *.java && java QuickSortTest
 * 单独查看某个实现的演示：java QuickSortLomuto（其余同理）
 */
public class QuickSortTest {

    interface IntSorter {
        void sort(int[] a);
    }

    public static void main(String[] args) {
        String[] names = {
                "QuickSortLomuto",
                "QuickSortHoare",
                "QuickSortThreeWay",
                "QuickSortRandomized",
                "QuickSortIterative",
                "QuickSortDualPivot",
        };
        IntSorter[] sorters = {
                QuickSortLomuto::sort,
                QuickSortHoare::sort,
                QuickSortThreeWay::sort,
                QuickSortRandomized::sort,
                QuickSortIterative::sort,
                QuickSortDualPivot::sort,
        };

        int failures = 0;
        for (int s = 0; s < sorters.length; s++) {
            failures += testIntSorter(names[s], sorters[s]);
        }
        failures += testGeneric();
        failures += perfSmoke(names, sorters);

        System.out.println();
        if (failures == 0) {
            System.out.println("全部测试通过");
        } else {
            System.out.println(failures + " 项测试未通过");
            System.exit(1);
        }
    }

    private static int testIntSorter(String name, IntSorter sorter) {
        Random rnd = new Random(42);
        int failures = 0;
        int cases = 0;

        // 固定边界用例
        int[][] fixed = {
                {},                        // 空数组
                {1},                       // 单元素
                {2, 1}, {1, 2},            // 两元素
                {7, 7, 7, 7, 7},           // 全相同
                {3, -1, 0, -5, 2},         // 含负数
                {1, 2, 3, 4, 5, 6, 7},     // 已排序
                {7, 6, 5, 4, 3, 2, 1},     // 逆序
        };
        for (int[] c : fixed) {
            cases++;
            if (!check(sorter, c)) {
                failures++;
            }
        }

        // 随机用例：小值域（大量重复）与大值域交替
        for (int t = 0; t < 2000; t++) {
            int n = rnd.nextInt(65);
            int range = t % 3 == 0 ? 10 : 1000;
            int[] a = new int[n];
            for (int i = 0; i < n; i++) {
                a[i] = rnd.nextInt(range) - range / 2;
            }
            cases++;
            if (!check(sorter, a)) {
                failures++;
            }
        }

        // 中等规模结构化用例
        int n = 5000;
        int[][] structured = new int[5][];
        structured[0] = new int[n];                 // 全相同
        Arrays.fill(structured[0], 9);
        structured[1] = new int[n];                 // 已排序
        for (int i = 0; i < n; i++) {
            structured[1][i] = i;
        }
        structured[2] = new int[n];                 // 逆序
        for (int i = 0; i < n; i++) {
            structured[2][i] = n - i;
        }
        structured[3] = new int[n];                 // 仅 3 个不同值
        for (int i = 0; i < n; i++) {
            structured[3][i] = 1 + rnd.nextInt(3);
        }
        structured[4] = new int[n];                 // 宽值域随机
        for (int i = 0; i < n; i++) {
            structured[4][i] = rnd.nextInt();
        }
        for (int[] c : structured) {
            cases++;
            if (!check(sorter, c)) {
                failures++;
            }
        }

        System.out.printf("%-20s %s（%d 个用例）%n",
                name, failures == 0 ? "通过" : "失败 " + failures + " 项", cases);
        return failures;
    }

    private static boolean check(IntSorter sorter, int[] original) {
        int[] actual = original.clone();
        int[] expected = original.clone();
        Arrays.sort(expected);
        sorter.sort(actual);
        if (!Arrays.equals(actual, expected)) {
            System.out.printf("  失败用例: 输入=%s 期望=%s 实际=%s%n",
                    Arrays.toString(original), Arrays.toString(expected), Arrays.toString(actual));
            return false;
        }
        return true;
    }

    private static int testGeneric() {
        Random rnd = new Random(123);
        int failures = 0;
        int cases = 0;

        // Integer 数组
        for (int t = 0; t < 500; t++) {
            Integer[] a = new Integer[rnd.nextInt(65)];
            for (int i = 0; i < a.length; i++) {
                a[i] = rnd.nextInt(100) - 50;
            }
            Integer[] expected = a.clone();
            Arrays.sort(expected);
            QuickSortGeneric.sort(a);
            cases++;
            if (!Arrays.equals(a, expected)) {
                failures++;
                System.out.printf("  QuickSortGeneric 失败用例: 期望=%s 实际=%s%n",
                        Arrays.toString(expected), Arrays.toString(a));
            }
        }

        // String 数组
        String[] words = {"banana", "apple", "cherry", "date", "fig",
                "elderberry", "grape", "apple", "香蕉", "苹果"};
        String[] expectedWords = words.clone();
        Arrays.sort(expectedWords);
        QuickSortGeneric.sort(words);
        cases++;
        if (!Arrays.equals(words, expectedWords)) {
            failures++;
            System.out.println("  QuickSortGeneric 字符串用例失败");
        }

        System.out.printf("%-20s %s（%d 个用例）%n",
                "QuickSortGeneric", failures == 0 ? "通过" : "失败 " + failures + " 项", cases);
        return failures;
    }

    /** 100 万随机整数：验证正确性并给出各实现的耗时参考。 */
    private static int perfSmoke(String[] names, IntSorter[] sorters) {
        int n = 1_000_000;
        Random rnd = new Random(7);
        int[] base = new int[n];
        for (int i = 0; i < n; i++) {
            base[i] = rnd.nextInt();
        }
        int[] expected = base.clone();
        Arrays.sort(expected);

        int failures = 0;
        System.out.println("---- 100 万随机整数性能冒烟 ----");
        for (int s = 0; s < sorters.length; s++) {
            int[] a = base.clone();
            long start = System.nanoTime();
            sorters[s].sort(a);
            long ms = (System.nanoTime() - start) / 1_000_000L;
            if (!Arrays.equals(a, expected)) {
                failures++;
                System.out.printf("%-20s 结果错误%n", names[s]);
            } else {
                System.out.printf("%-20s %5d ms%n", names[s], ms);
            }
        }
        return failures;
    }
}
