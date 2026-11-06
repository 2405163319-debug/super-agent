# super-agent

Demo repository for trying dsh-superagent.

## Two Sum (Java)

`TwoSum.java` solves the classic two-sum problem: given an integer array `nums`
and a target value `target`, it returns the indices of the two numbers that add
up to `target` (empty array when no such pair exists). Uses a hash map for
O(n) time and O(n) space.

Compile and run:

```sh
javac TwoSum.java TwoSumTest.java
java TwoSumTest   # runs the assertion-based tests
java TwoSum       # prints a sample run: nums=[2,7,11,15], target=9 -> [0, 1]
```
