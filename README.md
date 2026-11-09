# super-agent

Demo repository for trying dsh-superagent.

## Java 快速排序实现

每种实现都是一个可直接运行的独立类（`javac *.java && java <类名>`），各含一个演示用的 `main`。

| 文件 | 特点 |
| --- | --- |
| `QuickSortLomuto.java` | 经典递归实现，Lomuto 分区，末尾元素为基准 |
| `QuickSortHoare.java` | Hoare 分区，中间元素为基准，交换次数更少 |
| `QuickSortThreeWay.java` | 三路划分，大量重复元素时接近 O(n) |
| `QuickSortRandomized.java` | 随机选取基准，避免有序输入退化 |
| `QuickSortIterative.java` | 显式栈代替递归，栈深度 O(log n) |
| `QuickSortDualPivot.java` | 双轴（dual-pivot）分区，JDK `Arrays.sort` 的思路 |
| `QuickSortGeneric.java` | 泛型实现，支持任意 `Comparable` 对象数组 |

### 测试

`QuickSortTest.java` 用随机化用例（空数组、单元素、全相同、已排序、逆序、负数、大量重复等）把上述全部 `int[]` 实现与 `Arrays.sort` 逐一比对，并测试泛型实现和 100 万随机整数的性能冒烟：

```bash
javac *.java && java QuickSortTest
```

