"""Quicksort implementations in Python.

Three variants are provided:

1. ``quicksort_simple``     - functional style, easy to read, O(n) extra space.
2. ``quicksort_inplace``    - classic in-place quicksort with Lomuto partition.
3. ``quicksort_three_way``  - in-place quicksort with 3-way partitioning,
                              efficient for arrays with many duplicates.

Run ``python3 quicksort.py`` to execute a small self-test that compares each
implementation against the built-in :func:`sorted` on random inputs.
"""

from __future__ import annotations

import random
from typing import List, Sequence


def quicksort_simple(items: Sequence[int]) -> List[int]:
    """Return a new sorted list using the classic functional quicksort.

    Picks the last element as the pivot and partitions the remaining
    elements into ``less`` / ``greater`` sub-lists.  Simple to read, but
    allocates O(n) extra memory per level.
    """
    if len(items) <= 1:
        return list(items)

    pivot = items[-1]
    less = [x for x in items[:-1] if x < pivot]
    greater = [x for x in items[:-1] if x >= pivot]
    return quicksort_simple(less) + [pivot] + quicksort_simple(greater)


def _partition_lomuto(items: List[int], low: int, high: int) -> int:
    """Lomuto partition: place ``items[high]`` (the pivot) at its final
    position and return that position."""
    pivot = items[high]
    i = low - 1
    for j in range(low, high):
        if items[j] <= pivot:
            i += 1
            items[i], items[j] = items[j], items[i]
    items[i + 1], items[high] = items[high], items[i + 1]
    return i + 1


def quicksort_inplace(items: List[int], low: int = 0, high: int | None = None) -> List[int]:
    """Sort ``items`` in place using Lomuto-partition quicksort.

    The list is mutated and also returned for convenience.  Recursion happens
    only on the smaller partition so the stack depth stays O(log n).
    """
    if high is None:
        high = len(items) - 1

    while low < high:
        pivot_index = _partition_lomuto(items, low, high)
        # Recurse into the smaller side, loop on the larger one.
        if pivot_index - low < high - pivot_index:
            quicksort_inplace(items, low, pivot_index - 1)
            low = pivot_index + 1
        else:
            quicksort_inplace(items, pivot_index + 1, high)
            high = pivot_index - 1
    return items


def quicksort_three_way(items: List[int], low: int = 0, high: int | None = None) -> List[int]:
    """Sort ``items`` in place using 3-way (Dutch national flag) partitioning.

    Groups elements into ``< pivot``, ``== pivot`` and ``> pivot``, which makes
    it fast when the input contains many duplicate values.
    """
    if high is None:
        high = len(items) - 1

    if low >= high:
        return items

    pivot = items[low]
    lt, i, gt = low, low, high
    while i <= gt:
        if items[i] < pivot:
            items[lt], items[i] = items[i], items[lt]
            lt += 1
            i += 1
        elif items[i] > pivot:
            items[i], items[gt] = items[gt], items[i]
            gt -= 1
        else:
            i += 1

    quicksort_three_way(items, low, lt - 1)
    quicksort_three_way(items, gt + 1, high)
    return items


IMPLEMENTATIONS = {
    "simple": quicksort_simple,
    "inplace": quicksort_inplace,
    "three_way": quicksort_three_way,
}


def _self_test() -> None:
    cases = [
        [],
        [1],
        [2, 1],
        [5, 5, 5, 5],
        [3, 1, 2],
        [9, -3, 5, 0, 9, -3, 7],
        list(range(20)),
        list(range(20, 0, -1)),
    ]
    rng = random.Random(42)
    for _ in range(20):
        cases.append([rng.randint(-50, 50) for _ in range(rng.randint(0, 200))])

    for name, sort in IMPLEMENTATIONS.items():
        for case in cases:
            expected = sorted(case)
            actual = sort(list(case))
            assert actual == expected, f"{name} failed on {case!r}: got {actual!r}"
        print(f"{name}: OK ({len(cases)} cases)")


if __name__ == "__main__":
    _self_test()
