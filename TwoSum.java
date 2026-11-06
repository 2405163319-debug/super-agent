import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Two Sum.
 *
 * <p>Given an integer array {@code nums} and an integer {@code target}, return
 * the indices of the two numbers such that they add up to {@code target}.
 * The same element may not be used twice, but duplicate values are handled
 * correctly. When no pair exists (including {@code null} or empty input),
 * every implementation returns an empty array.</p>
 *
 * <p>Three approaches are provided, from the naive to the optimal:</p>
 *
 * <ul>
 *   <li>{@link #twoSum(int[], int)} &mdash; one-pass hash map, O(n) time, O(n) space (recommended)</li>
 *   <li>{@link #twoSumBruteForce(int[], int)} &mdash; nested loops over all pairs, O(n&sup2;) time, O(1) space</li>
 *   <li>{@link #twoSumTwoPointers(int[], int)} &mdash; sort value/index pairs then two pointers, O(n log n) time, O(n) space</li>
 * </ul>
 *
 * <p>All implementations use {@code long} arithmetic internally so that sums
 * of extreme {@code int} values (e.g. {@link Integer#MAX_VALUE} plus
 * {@link Integer#MIN_VALUE}) are compared exactly, without the false matches
 * that 32-bit wraparound would produce. Returned indices are always in
 * ascending order.</p>
 */
public final class TwoSum {

    private TwoSum() {
    }

    /**
     * One-pass hash map: for each element, look up the complement
     * {@code target - nums[i]} among the values already seen, then record the
     * current value.
     *
     * <p>Time complexity: O(n). Space complexity: O(n).</p>
     */
    public static int[] twoSum(int[] nums, int target) {
        if (nums == null) {
            return new int[0];
        }
        // Long keys keep the complement lookup exact even when
        // target - nums[i] falls outside the int range.
        Map<Long, Integer> seen = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            long need = (long) target - nums[i];
            Integer j = seen.get(need);
            if (j != null) {
                return new int[] {j, i};
            }
            seen.put((long) nums[i], i);
        }
        return new int[0];
    }

    /**
     * Brute force: check every pair {@code (i, j)} with {@code i < j}.
     *
     * <p>Time complexity: O(n&sup2;). Space complexity: O(1).</p>
     */
    public static int[] twoSumBruteForce(int[] nums, int target) {
        if (nums == null) {
            return new int[0];
        }
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if ((long) nums[i] + nums[j] == target) {
                    return new int[] {i, j};
                }
            }
        }
        return new int[0];
    }

    /**
     * Sort + two pointers: pair each value with its original index, sort by
     * value, then move two pointers inward until their sum hits the target.
     *
     * <p>Time complexity: O(n log n). Space complexity: O(n) for the
     * value/index pairs.</p>
     */
    public static int[] twoSumTwoPointers(int[] nums, int target) {
        if (nums == null || nums.length < 2) {
            return new int[0];
        }
        int[][] pairs = new int[nums.length][2]; // {value, original index}
        for (int i = 0; i < nums.length; i++) {
            pairs[i][0] = nums[i];
            pairs[i][1] = i;
        }
        Arrays.sort(pairs, (a, b) -> Integer.compare(a[0], b[0]));
        int left = 0;
        int right = nums.length - 1;
        while (left < right) {
            long sum = (long) pairs[left][0] + pairs[right][0];
            if (sum == target) {
                int i = pairs[left][1];
                int j = pairs[right][1];
                return i < j ? new int[] {i, j} : new int[] {j, i};
            }
            if (sum < target) {
                left++;
            } else {
                right--;
            }
        }
        return new int[0];
    }

    /** Small demonstration: prints the indices found by each approach. */
    public static void main(String[] args) {
        int[] nums = {2, 7, 11, 15};
        int target = 9;
        System.out.println("nums = [2, 7, 11, 15], target = 9");
        System.out.println("hash map     -> " + Arrays.toString(twoSum(nums, target)));
        System.out.println("brute force  -> " + Arrays.toString(twoSumBruteForce(nums, target)));
        System.out.println("two pointers -> " + Arrays.toString(twoSumTwoPointers(nums, target)));
    }
}
