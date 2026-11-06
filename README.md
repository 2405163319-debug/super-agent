# super-agent

Demo repository for trying dsh-superagent.

## Two Sum

`TwoSum.java` solves the classic two-sum problem: given an integer array `nums`
and a `target`, return the indices of the two numbers that add up to `target`.
Three approaches are provided:

| Method | Approach | Time | Space |
| --- | --- | --- | --- |
| `TwoSum.twoSum` | One-pass hash map (recommended) | O(n) | O(n) |
| `TwoSum.twoSumBruteForce` | Nested loops over all pairs | O(n^2) | O(1) |
| `TwoSum.twoSumTwoPointers` | Sort value/index pairs, then two pointers | O(n log n) | O(n) |

All methods return the two indices in ascending order, or an empty array when
no pair exists (`null` and empty inputs included). They sum values as `long`
internally, so extreme `int` values are handled without wraparound.

Build and verify:

```
javac TwoSum.java TwoSumTest.java
java TwoSumTest   # runs a 12-case suite against all three implementations
java TwoSum       # prints a small demo
```
