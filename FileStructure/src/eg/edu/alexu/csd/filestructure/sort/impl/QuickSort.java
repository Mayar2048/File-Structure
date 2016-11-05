package eg.edu.alexu.csd.filestructure.sort.impl;

import java.util.ArrayList;

/**
 * The Class QuickSort.
 *
 * @param <T>
 *            the generic type
 */
public class QuickSort<T extends Comparable<T>> {

	/**
	 * Partition.
	 *
	 * @param list
	 *            the list
	 * @param low
	 *            the low
	 * @param high
	 *            the high
	 * @return the int
	 */
	public int partition(ArrayList<T> list, int low, int high) {
		T pivot = list.get(high);
		int i = (low - 1);
		for (int j = low; j <= high - 1; j++) {
			if (((Comparable<T>) list.get(j)).compareTo(pivot) <= 0) {
				i++;
				if (i != j) {
					T temp = list.get(i);
					list.set(i, list.get(j));
					list.set(j, temp);
				}
			}
		}

		T temp = list.get(i + 1);
		list.set(i + 1, list.get(high));
		list.set(high, temp);
		return i + 1;
	}

	/**
	 * Quick sort.
	 *
	 * @param unsorted
	 *            the unsorted
	 * @param low
	 *            the low
	 * @param high
	 *            the high
	 */
	public void quickSort(ArrayList<T> unsorted, int low, int high) {
		if (low < high) {
			int pivot = partition(unsorted, low, high);
			quickSort(unsorted, low, pivot - 1);
			quickSort(unsorted, pivot + 1, high);
		}
	}
}
