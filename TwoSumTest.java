import java.util.Arrays;

/**
 * Simple assertion-based tests for {@link TwoSum}; run with {@code java TwoSumTest}.
 *
 * <p>The same 12-case suite is executed against all three implementations
 * (hash map, brute force, two pointers) &mdash; 36 checks in total.</p>
 */
public class TwoSumTest {

    /** Functional signature shared by all TwoSum implementations. */
    private interface Solver {
        int[] solve(int[] nums, int target);
    }

    private static int failures = 0;

    public static void main(String[] args) {
        runSuite("hash map    ", TwoSum::twoSum);
        runSuite("brute force ", TwoSum::twoSumBruteForce);
        runSuite("two pointers", TwoSum::twoSumTwoPointers);

        if (failures == 0) {
            System.out.println("All tests passed (3 implementations x 12 cases).");
        } else {
            System.out.println(failures + " check(s) FAILED.");
            System.exit(1);
        }
    }

    private static void runSuite(String label, Solver solver) {
        check(label, "example 1", solver, new int[] {2, 7, 11, 15}, 9, new int[] {0, 1});
        check(label, "example 2", solver, new int[] {3, 2, 4}, 6, new int[] {1, 2});
        check(label, "duplicate values", solver, new int[] {3, 5, 3}, 6, new int[] {0, 2});
        check(label, "negatives", solver, new int[] {-3, 4, 3, 90}, 0, new int[] {0, 2});
        check(label, "zero target", solver, new int[] {-5, 1, 3, 5, 2}, 0, new int[] {0, 3});
        check(label, "pair at end", solver, new int[] {1, 5, 9, 2}, 11, new int[] {2, 3});
        check(label, "single element", solver, new int[] {5}, 10, new int[0]);
        check(label, "no pair", solver, new int[] {1, 2, 3}, 100, new int[0]);
        check(label, "extreme values", solver,
                new int[] {Integer.MAX_VALUE, 1, Integer.MIN_VALUE}, -1, new int[] {0, 2});
        check(label, "no wraparound false match", solver,
                new int[] {1, Integer.MAX_VALUE}, Integer.MIN_VALUE, new int[0]);
        check(label, "empty array", solver, new int[0], 5, new int[0]);
        check(label, "null array", solver, null, 5, new int[0]);
    }

    private static void check(String label, String name, Solver solver,
            int[] nums, int target, int[] expected) {
        int[] actual = solver.solve(nums, target);
        if (!Arrays.equals(actual, expected)) {
            failures++;
            System.out.printf("[%s] %s FAILED: expected %s but got %s%n",
                    label, name, Arrays.toString(expected), Arrays.toString(actual));
        }
    }
}
