import java.util.HashMap;
import java.util.Map;

/**
 * Two Sum.
 *
 * Given an integer array {@code nums} and an integer {@code target}, return the
 * indices of the two numbers such that they add up to {@code target}.
 *
 * Assumes exactly one valid answer exists and the same element may not be used
 * twice. Elements may be negative, zero or duplicated; duplicates are handled
 * correctly because each index is inserted into the map only after being
 * checked as a potential complement.
 */
public final class TwoSum {

    private TwoSum() {
    }

    /**
     * Returns the indices of the two numbers in {@code nums} that add up to
     * {@code target}, or an empty array if no such pair exists.
     *
     * Time complexity: O(n). Space complexity: O(n).
     */
    public static int[] twoSum(int[] nums, int target) {
        if (nums == null) {
            return new int[0];
        }
        Map<Integer, Integer> seen = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int need = target - nums[i];
            Integer j = seen.get(need);
            if (j != null) {
                return new int[] {j, i};
            }
            seen.put(nums[i], i);
        }
        return new int[0];
    }

    /** Small demonstration: prints the indices found for a sample input. */
    public static void main(String[] args) {
        int[] nums = {2, 7, 11, 15};
        int target = 9;
        int[] result = twoSum(nums, target);
        System.out.println("nums = [2, 7, 11, 15], target = 9");
        System.out.println("indices: [" + result[0] + ", " + result[1] + "]");
    }
}
