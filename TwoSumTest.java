import java.util.Arrays;

/** Simple assertion-based tests for {@link TwoSum}; run with {@code java TwoSumTest}. */
public class TwoSumTest {

    private static int failures = 0;

    public static void main(String[] args) {
        check("example 1", new int[] {2, 7, 11, 15}, 9, new int[] {0, 1});
        check("example 2", new int[] {3, 2, 4}, 6, new int[] {1, 2});
        check("example 3", new int[] {3, 3}, 6, new int[] {0, 1});
        check("negatives", new int[] {-3, 4, 3, 90}, 0, new int[] {0, 2});
        check("zero target", new int[] {-1, -2, -3, -4, -5}, -8, new int[] {2, 4});
        check("large values", new int[] {Integer.MAX_VALUE, 1, Integer.MIN_VALUE}, -1, new int[] {0, 2});
        check("pair at end", new int[] {1, 5, 9, 2}, 11, new int[] {2, 3});
        check("no pair", new int[] {1, 2, 3}, 100, new int[0]);
        check("empty array", new int[0], 5, new int[0]);
        check("null array", null, 5, new int[0]);

        if (failures == 0) {
            System.out.println("All tests passed.");
        } else {
            System.out.println(failures + " test(s) FAILED.");
            System.exit(1);
        }
    }

    private static void check(String name, int[] nums, int target, int[] expected) {
        int[] actual = TwoSum.twoSum(nums, target);
        if (Arrays.equals(actual, expected)) {
            System.out.println("PASS " + name);
        } else {
            failures++;
            System.out.println("FAIL " + name + ": expected " + Arrays.toString(expected)
                    + ", got " + Arrays.toString(actual));
        }
    }
}
