package eg.edu.alexu.csd.filestructure.sort.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import eg.edu.alexu.csd.filestructure.sort.IHeap;
import eg.edu.alexu.csd.filestructure.sort.INode;

public class BinaryHeap<T extends Comparable<T>> implements IHeap<T> {
	private ArrayList<INode<T>> heap;
	private int heapSize;

	public BinaryHeap() {
		heap = new ArrayList<INode<T>>();
		heapSize = 0;
	}

	private class Node implements INode<T> {
		private INode<T> parent;
		private INode<T> leftChild;
		private INode<T> rightChild;
		private int cargo;
		private T value;
		private int left;
		private int right;
		private int parentIndex;

		public Node(int cargo) {
			this.cargo = cargo;
			this.parent = null;
			this.leftChild = null;
			this.rightChild = null;
			this.left = (2 * cargo) + 1;
			this.right = (2 * cargo) + 2;
			this.parentIndex = (int) (Math.ceil(cargo / 2.0) - 1);
		}

		@Override
		public INode<T> getLeftChild() {
			if (left < BinaryHeap.this.heapSize)
				this.leftChild = BinaryHeap.this.heap.get(left);
			else
				this.leftChild = null;
			return this.leftChild;

		}

		@Override
		public INode<T> getRightChild() {
			if (right < BinaryHeap.this.heapSize)
				this.rightChild = BinaryHeap.this.heap.get(right);
			else
				this.rightChild = null;
			return this.rightChild;
		}

		@Override
		public INode<T> getParent() {
			if (cargo > 0) {
				this.parent = BinaryHeap.this.heap.get(parentIndex);
			} else
				this.parent = null;
			return this.parent;
		}

		@Override
		public T getValue() {
			return this.value;
		}

		@Override
		public void setValue(T value) {
			this.value = value;
		}
	}

	@Override
	public INode<T> getRoot() {
		if (this.heap.isEmpty())
			return null;
		return this.heap.get(0);
	}

	@Override
	public int size() {
		return this.heapSize;
	}

	@Override
	public void heapify(INode<T> node) {
		if (size() == 0 || size() == 1)
			return;
		INode<T> left = node.getLeftChild();
		INode<T> right = node.getRightChild();
		INode<T> largest;

		if (left != null && left.getValue().compareTo(node.getValue()) > 0)
			largest = left;
		else
			largest = node;
		if (right != null && right.getValue().compareTo(largest.getValue()) > 0)
			largest = right;
		if (largest.getValue().compareTo(node.getValue()) != 0) {
			this.swap(node, largest);
			this.heapify(largest);
		}
	}

	@Override
	public T extract() {
		T max = (heap.size() > 0) ? this.heap.get(0).getValue() : null;
		if (heap.size() >= 1) {
			this.heapSize--;
			if (heap.size() == 1)
				this.heap.remove(heap.size() - 1);
			else if (heap.size() == 2) {
				this.heap.get(0).setValue(heap.get(heap.size() - 1).getValue());
				this.heap.remove(heap.size() - 1);
			} else {
				this.heap.get(0).setValue(heap.get(heap.size() - 1).getValue());
				this.heap.remove(heap.size() - 1);
				this.heapify(heap.get(0));
			}
		}
		return max;
	}

	@Override
	public void insert(T element) {
		INode<T> node = new Node(heap.size());
		node.setValue(element);
		this.heap.add(node);
		this.heapSize++;
		while (node.getParent() != null
				&& (node.getParent().getValue().compareTo(node.getValue()) < 0)) {
			this.swap(node, node.getParent());
			node = node.getParent();
		}
	}

	@Override
	public void build(Collection<T> unordered) {
		this.heapSize = unordered.size();
		this.constructTree(unordered);
		int start = (int) Math.floor(heap.size() / 2.0) - 1;
		for (int i = start; i >= 0; i--) {
			this.heapify(heap.get(i));
		}
	}

	public void constructTree(Collection<T> unordered) {
		heap = new ArrayList<INode<T>>();
		int index = 0;
		Iterator<T> iterator = unordered.iterator();
		while (iterator.hasNext()) {
			INode<T> node = new Node(index);
			node.setValue(iterator.next());
			this.heap.add(node);
			index++;
		}
	}

	public void swap(INode<T> small, INode<T> large) {
		T temp = small.getValue();
		small.setValue(large.getValue());
		large.setValue(temp);
	}

	public IHeap<T> sort(ArrayList<T> unordered) {
		this.build(unordered);
		for (int i = heapSize - 1; i >= 1; i--) {
			swap(heap.get(0), heap.get(i));
			heapSize -= 1;
			this.heapify(heap.get(0));
		}
		heapSize = unordered.size();
		return this;
	}
}
