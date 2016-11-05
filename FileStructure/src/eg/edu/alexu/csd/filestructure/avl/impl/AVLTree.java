package eg.edu.alexu.csd.filestructure.avl.impl;

import eg.edu.alexu.csd.filestructure.avl.IAVLTree;
import eg.edu.alexu.csd.filestructure.avl.INode;

/**
 * The Class AVLTree.
 *
 * @param <T>
 *            the generic type
 */
public class AVLTree<T extends Comparable<T>> implements IAVLTree<T> {

	/** The root. */
	private AVLNode root;

	/** The decision. */
	private int decision;

	/** The left subtree height. */
	private int leftSubtreeHeight;

	/** The right subtree height. */
	private int rightSubtreeHeight;

	/** The flag. */
	private boolean flag;

	/**
	 * Instantiates a new AVL tree.
	 */
	public AVLTree() {
		this.root = null;
	}

	/**
	 * The Class AVLNode.
	 */
	private class AVLNode implements INode<T> {

		/** The height. */
		private int height;

		/** The left ptr. */
		private AVLNode leftPtr;

		/** The right ptr. */
		private AVLNode rightPtr;

		/** The parent ptr. */
		private AVLNode parentPtr;

		/** The item. */
		private T item;

		/**
		 * Instantiates a new AVL node.
		 */
		public AVLNode() {
			this.height = 0;
			this.parentPtr = null;
			this.leftPtr = null;
			this.rightPtr = null;
			this.item = null;
		}

		@Override
		public INode<T> getLeftChild() {
			return this.leftPtr;
		}

		@Override
		public INode<T> getRightChild() {
			return this.rightPtr;
		}

		@Override
		public T getValue() {
			return this.item;
		}

		@Override
		public void setValue(final T value) {
			this.item = value;
		}
	}

	@Override
	public void insert(final T key) {
		if (key == null) {
			return;
		}
		this.insertNode(root, key);
	}

	/**
	 * Insert node.
	 *
	 * @param subTreeRoot
	 *            the sub tree root
	 * @param key
	 *            the key
	 */
	public void insertNode(AVLNode subTreeRoot, final T key) {
		AVLNode node = new AVLNode();
		if (subTreeRoot == null) { // insert in an empty tree
			node.setValue(key);
			this.root = node;
			return;
		} else {
			while (true) {
				decision = key.compareTo(subTreeRoot.getValue());
				if (decision < 0) { // we must go left
					if (!this.hasLeft(subTreeRoot)) { // proper place to insert in has been reached
						node.setValue(key);
						node.parentPtr = subTreeRoot;
						subTreeRoot.leftPtr = node;
						this.updateHeight(node, true);
						return;
					}
					subTreeRoot = (AVLTree<T>.AVLNode) subTreeRoot.getLeftChild();
				} else if (decision >= 0) { // we must go right
					if (!this.hasRight(subTreeRoot)) { // proper place to insert in has been reached
						node.setValue(key);
						node.parentPtr = subTreeRoot;
						subTreeRoot.rightPtr = node;
						this.updateHeight(node, true);
						return;
					}
					subTreeRoot = (AVLTree<T>.AVLNode) subTreeRoot.getRightChild();
				}
			}
		}
	}

	@Override
	public boolean delete(final T key) {
		if (key == null) {
			return false;
		}
		if (root == null) {
			return false;
		}
		AVLNode delObj = root;
		AVLNode successor;
		AVLNode predecessor;
		while (delObj != null) {
			decision = key.compareTo(delObj.getValue());
			if (decision == 0) {
				if (this.hasLeft(delObj) && this.hasRight(delObj)) {
					successor = this.successor(delObj.rightPtr);
					delObj.setValue(successor.getValue());
					this.updateHeight(successor, false);
				} else if (this.hasLeft(delObj)) {
					predecessor = this.predecessor(delObj.leftPtr);
					delObj.setValue(predecessor.getValue());
					this.updateHeight(predecessor, false);
				} else if (this.hasRight(delObj)) {
					predecessor = this.predecessor(delObj.rightPtr);
					delObj.setValue(predecessor.getValue());
					this.updateHeight(predecessor, false);
				} else {
					this.updateHeight(delObj, false);
				}
				return true;
			} else if (decision < 0) {
				delObj = (AVLTree<T>.AVLNode) delObj.getLeftChild();
			} else if (decision > 0) {
				delObj = (AVLTree<T>.AVLNode) delObj.getRightChild();
			}
		}
		return false;
	}

	@Override
	public boolean search(final T key) {
		if (key == null) {
			return false;
		}
		AVLNode searchObj = root;
		while (searchObj != null) {
			decision = key.compareTo(searchObj.getValue());
			if (decision == 0) {
				return true;
			} else if (decision < 0) {
				searchObj = (AVLTree<T>.AVLNode) searchObj.getLeftChild();
			} else if (decision > 0) {
				searchObj = (AVLTree<T>.AVLNode) searchObj.getRightChild();
			}
		}
		return false;
	}

	@Override
	public int height() {
		return (root == null) ? 0 : root.height + 1;
	}

	@Override
	public INode<T> getTree() {
		return root;
	}

	/**
	 * Update height.
	 *
	 * @param node
	 *            the node
	 * @param searchVsDelete
	 *            the search vs delete
	 */
	public void updateHeight(AVLNode node, final boolean searchVsDelete) {
		if (searchVsDelete) {
			flag = true;
			if (node.parentPtr.height == 0) {
				while (this.hasParent(node) && flag) {
					if (node.parentPtr.height == node.height) {
						node.parentPtr.height++;
					}
					if (Math.abs(this.nodeBalanceFactor(node.parentPtr)) > 1) {
						flag = false;
						node = this.balance(node.parentPtr); // balance return here
						if (!flag) {
							while (this.hasParent(node)) {
								node = node.parentPtr;
							}
							break;
						}
					}
					node = node.parentPtr;
				}
				root = node;
			}
		} else {
			// delete successor or predecessor
			AVLNode parent = node.parentPtr;
			if (this.hasLeft(node) || this.hasRight(node)) {
				parent = node.parentPtr;
				if (this.hasRight(node)) {
					if (parent.leftPtr == node) {
						parent.leftPtr = node.rightPtr;
					} else {
						parent.rightPtr = node.rightPtr;
					}
					node.rightPtr.parentPtr = parent;
				} else {
					if (parent.leftPtr == node) {
						parent.leftPtr = null;
					} else {
						parent.rightPtr = null;
					}
				}
			} else {
				if (parent == null) {
					root = null;
					return;
				} else {
					if (parent.leftPtr == node) {
						parent.leftPtr = null;
					} else {
						parent.rightPtr = null;
					}
				}
			}

			// rebalance
			while (parent != null) {
				this.nodeBalanceFactor(parent);
				this.nodeHeight(parent);
				flag = false;
				if (Math.abs(this.nodeBalanceFactor(parent)) > 1) {
					node = this.balance(parent); // balance return here
				} else {
					node = parent;
				}
				parent = parent.parentPtr;
			}
			root = node;
		}
	}

	/**
	 * Balance.
	 *
	 * @param imbalancedNode
	 *            the imbalanced node
	 * @return the AVL node
	 */
	public AVLNode balance(AVLNode imbalancedNode) {
		AVLNode leftMiddleNode;
		AVLNode rightMiddleNode;
		int difference = this.nodeBalanceFactor(imbalancedNode);
		if (Math.abs(difference) <= 1) {
			flag = true;
			return imbalancedNode;
		} else if (difference == 2) {
			leftMiddleNode = (AVLTree<T>.AVLNode) imbalancedNode.getLeftChild();
			if (this.nodeBalanceFactor(leftMiddleNode) >= 0) {
				return this.rotateLeftLeft(imbalancedNode, leftMiddleNode); // case 1
			} else {
				return this.rotateLeftRight(imbalancedNode, leftMiddleNode); // case 3
			}
		} else if (difference == -2) {
			rightMiddleNode = (AVLTree<T>.AVLNode) imbalancedNode
					.getRightChild();
			if (this.nodeBalanceFactor(rightMiddleNode) <= 0) {
				return this.rotateRightRight(imbalancedNode, rightMiddleNode); // case 2
			} else {
				return this.rotateRightLeft(imbalancedNode, rightMiddleNode); // case 4
			}
		}
		return null;
	}

	/**
	 * rotateLeftLeft.
	 *
	 * @param imbalancedNode
	 *            the imbalanced node
	 * @param leftMiddleNode
	 *            the left middle node
	 * @return the AVL node
	 */
	public AVLNode rotateLeftLeft(AVLNode imbalancedNode, AVLNode leftMiddleNode) {
		imbalancedNode.leftPtr = leftMiddleNode.rightPtr;
		if (this.hasLeft(imbalancedNode)) {
			imbalancedNode.leftPtr.parentPtr = imbalancedNode;
		}

		if (this.hasParent(imbalancedNode)) {
			if (imbalancedNode.parentPtr.rightPtr == imbalancedNode) {
				imbalancedNode.parentPtr.rightPtr = leftMiddleNode;
			} else {
				imbalancedNode.parentPtr.leftPtr = leftMiddleNode;
			}
			leftMiddleNode.parentPtr = imbalancedNode.parentPtr;
		} else {
			leftMiddleNode.parentPtr = imbalancedNode.parentPtr;
		}

		leftMiddleNode.rightPtr = imbalancedNode;
		imbalancedNode.parentPtr = leftMiddleNode;

		this.nodeBalanceFactor(imbalancedNode);
		this.nodeHeight(imbalancedNode);
		this.nodeBalanceFactor(leftMiddleNode);
		this.nodeHeight(leftMiddleNode);
		return leftMiddleNode;
	}

	/**
	 * rotateRightRight.
	 *
	 * @param imbalancedNode
	 *            the imbalanced node
	 * @param rightMiddleNode
	 *            the right middle node
	 * @return the AVL node
	 */
	public AVLNode rotateRightRight(AVLNode imbalancedNode, AVLNode rightMiddleNode) {
		imbalancedNode.rightPtr = rightMiddleNode.leftPtr;
		if (this.hasRight(imbalancedNode)) {
			imbalancedNode.rightPtr.parentPtr = imbalancedNode;
		}

		if (this.hasParent(imbalancedNode)) {
			if (imbalancedNode.parentPtr.rightPtr == imbalancedNode) {
				imbalancedNode.parentPtr.rightPtr = rightMiddleNode;
			} else {
				imbalancedNode.parentPtr.leftPtr = rightMiddleNode;
			}
			rightMiddleNode.parentPtr = imbalancedNode.parentPtr;
		} else {
			rightMiddleNode.parentPtr = imbalancedNode.parentPtr;
		}

		rightMiddleNode.leftPtr = imbalancedNode;
		imbalancedNode.parentPtr = rightMiddleNode;

		this.nodeBalanceFactor(imbalancedNode);
		this.nodeHeight(imbalancedNode);
		this.nodeBalanceFactor(rightMiddleNode);
		this.nodeHeight(rightMiddleNode);
		return rightMiddleNode;
	}

	/**
	 * rotateLeftRight.
	 *
	 * @param imbalancedNode
	 *            the imbalanced node
	 * @param leftMiddleNode
	 *            the left middle node
	 * @return the AVL node
	 */
	public AVLNode rotateLeftRight(AVLNode imbalancedNode, AVLNode leftMiddleNode) {
		imbalancedNode.leftPtr = this.rotateRightRight(leftMiddleNode,
				leftMiddleNode.rightPtr);
		return this.rotateLeftLeft(imbalancedNode, imbalancedNode.leftPtr);
	}

	/**
	 * rotateRightLeft.
	 *
	 * @param imbalancedNode
	 *            the imbalanced node
	 * @param rightMiddleNode
	 *            the right middle node
	 * @return the AVL node
	 */
	public AVLNode rotateRightLeft(AVLNode imbalancedNode, AVLNode rightMiddleNode) {
		imbalancedNode.rightPtr = this.rotateLeftLeft(rightMiddleNode, rightMiddleNode.leftPtr);
		return this.rotateRightRight(imbalancedNode, imbalancedNode.rightPtr);
	}

	/**
	 * Node balance factor.
	 *
	 * @param node
	 *            the node
	 * @return the int
	 */
	public int nodeBalanceFactor(AVLNode node) {
		leftSubtreeHeight = (node.leftPtr == null) ? 0 : node.leftPtr.height + 1;
		rightSubtreeHeight = (node.rightPtr == null) ? 0 : node.rightPtr.height + 1;
		return (leftSubtreeHeight - rightSubtreeHeight);
	}

	/**
	 * Node height.
	 *
	 * @param node
	 *            the node
	 */
	public void nodeHeight(AVLNode node) {
		node.height = Math.max(leftSubtreeHeight, rightSubtreeHeight);
	}

	/**
	 * Checks for left.
	 *
	 * @param node
	 *            the node
	 * @return true, if successful
	 */
	public boolean hasLeft(AVLNode node) {
		return (node.leftPtr != null);
	}

	/**
	 * Checks for right.
	 *
	 * @param node
	 *            the node
	 * @return true, if successful
	 */
	public boolean hasRight(AVLNode node) {
		return (node.rightPtr != null);
	}

	/**
	 * Checks for parent.
	 *
	 * @param node
	 *            the node
	 * @return true, if successful
	 */
	public boolean hasParent(AVLNode node) {
		return (node.parentPtr != null);
	}

	/**
	 * Successor.
	 *
	 * @param subTreeRoot
	 *            the sub tree root
	 * @return the AVL node
	 */
	public AVLNode successor(AVLNode subTreeRoot) {
		while (this.hasLeft(subTreeRoot)) {
			subTreeRoot = (AVLTree<T>.AVLNode) subTreeRoot.getLeftChild();
		}
		return subTreeRoot;
	}

	/**
	 * Predecessor.
	 *
	 * @param subTreeRoot
	 *            the sub tree root
	 * @return the AVL node
	 */
	public AVLNode predecessor(AVLNode subTreeRoot) {
		while (this.hasRight(subTreeRoot)) {
			subTreeRoot = (AVLTree<T>.AVLNode) subTreeRoot.getRightChild();
		}
		return subTreeRoot;
	}
}
