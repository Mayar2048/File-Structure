package eg.edu.alexu.csd.filestructure.sort.impl;

import java.util.ArrayList;
import eg.edu.alexu.csd.filestructure.sort.IHeap;
import eg.edu.alexu.csd.filestructure.sort.ISort;

/**
 * The Class Sort.
 *
 * @param <T>
 *            the generic type
 */
public class Sort<T extends Comparable<T>> implements ISort<T> {

	/** The tree. */
	private IHeap<T> tree;

	/** The sort. */
	@SuppressWarnings("rawtypes")
	private QuickSort sort;

	/**
	 * Instantiates a new sort.
	 */
	@SuppressWarnings("rawtypes")
	public Sort() {
		tree = new BinaryHeap<T>();
		sort = new QuickSort();
	}

	@Override
	public IHeap<T> heapSort(ArrayList<T> unordered) {
		return ((BinaryHeap<T>) tree).sort(unordered);
	}

	@Override
	public void sortSlow(ArrayList<T> unordered) {
		// Bubble Sort
		int length = unordered.size();
		for (int i = 0; i < length - 1; i++) {
			for (int j = 0; j < length - i - 1; j++) {
				if (unordered.get(j).compareTo(unordered.get(j + 1)) > 0) {
					T temp = unordered.get(j);
					unordered.set(j, unordered.get(j + 1));
					unordered.set(j + 1, temp);
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void sortFast(ArrayList<T> unordered) {
		// Quick Sort
		sort.quickSort(unordered, 0, unordered.size() - 1);
	}

}
