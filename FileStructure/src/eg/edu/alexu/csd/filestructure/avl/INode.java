package eg.edu.alexu.csd.filestructure.avl;

/**
 * The Interface INode.
 *
 * @param <T>
 *            the generic type
 */
public interface INode<T extends Comparable<T>> {

	/**
	 * Returns the left child of the current element/node in the heap tree.
	 *
	 * @return INode wrapper to the left child of the current element/node
	 */
	INode<T> getLeftChild();

	/**
	 * Returns the right child of the current element/node in the heap tree.
	 *
	 * @return INode wrapper to the right child of the current element/node
	 */
	INode<T> getRightChild();

	/**
	 * Set/Get the value of the current node.
	 *
	 * @return Value of the current node
	 */
	T getValue();

	/**
	 * Sets the value.
	 *
	 * @param value
	 *            the new value
	 */
	void setValue(T value);
}