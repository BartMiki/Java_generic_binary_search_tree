package generic_binary_search_tree;

import java.util.Iterator;

public class BinarySearchTree<T extends Comparable<T>> implements Iterable<T> {
	class Node {
		private T value;
		private Node left;
		private Node right;
		private Node parent;

		public Node(T value) {
			this.value = value;
			left = right = parent = null;
		}

		public Node(T value, Node parent) {
			this.value = value;
			left = right = null;
			this.parent = parent;
		}
		
		public boolean add(T value) {
			boolean added = true;
			if (value.compareTo(this.value) == 0)
				added = false;
			else {
				if (value.compareTo(this.value) < 0) {
					if (left == null) {
						left = new Node(value, this);
						added = true;
					} else
						added = left.add(value);
				} else {
					if (right == null) {
						right = new Node(value, this);
						added = true;
					} else
						added = right.add(value);
				}
			}
			return added;
		}

		public String toString() {
			String result = "";
			if (left != null)
				result += left.toString();
			result += (value + ", ");
			if (right != null)
				result += right.toString();
			return result;
		}
	}

	class InOrderIterator implements Iterator<T> {

		private Node nextNode;

		public InOrderIterator() {
			nextNode = findMin(root);
		}

		@Override
		public boolean hasNext() {
			return nextNode != null;
		}

		@Override
		public T next() {
			Node returnNode = nextNode;
			if (nextNode.right != null) {
				nextNode = findMin(nextNode.right);
				return returnNode.value;
			}
			Node temp = nextNode.parent;
			while (temp != null && nextNode == temp.right) {
				nextNode = temp;
				temp = temp.parent;
			}
			nextNode = temp;
			return returnNode.value;
		}
	}

	class ReverseInOrderIterator implements Iterator<T> {

		private Node nextNode;

		public ReverseInOrderIterator() {
			nextNode = findMax(root);
		}

		@Override
		public boolean hasNext() {
			return nextNode != null;
		}

		@Override
		public T next() {
			Node returnNode = nextNode;
			if (nextNode.left != null) {
				nextNode = findMax(nextNode.left);
				return returnNode.value;
			}
			Node temp = nextNode.parent;
			while (temp != null && nextNode == temp.left) {
				nextNode = temp;
				temp = temp.parent;
			}
			nextNode = temp;
			return returnNode.value;
		}
	}

	private Node root;
	private int size;

	public BinarySearchTree() {
		root = null;
		size = 0;
	}

	public BinarySearchTree(T value) {
		root = new Node(value);
		size = 1;
	}

	@Override
	public Iterator<T> iterator() {
		return new InOrderIterator();
	}

	public Iterator<T> reverseIterator() {
		return new ReverseInOrderIterator();
	}

	public boolean add(T value) {
		if (root == null){
			root = new Node(value);
			size += 1;
			return true;
		}
		else
			if(root.add(value)){
				size += 1;
				return true;
			}
			else
				return false;
	}

	public String toString() {
		String result = "";
		if (root != null) {
			result += "[";
			result += root.toString();
			StringBuilder sb = new StringBuilder(result);
			sb.setCharAt(result.length() - 2, ']');
			result = sb.toString();
		}
		return result;
	}

	public int size() {
		return size;
	}

	public T findMin() {
		Node temp = root;
		while (temp.left != null)
			temp = temp.left;
		return temp.value;
	}

	public T findMax() {
		Node temp = root;
		while (temp.right != null)
			temp = temp.right;
		return temp.value;
	}

	public boolean contains(T value) {
		Node temp = root;
		while (temp != null) {
			if (temp.value == value)
				return true;
			else {
				if (value.compareTo(temp.value) < 0)
					temp = temp.left;
				else
					temp = temp.right;
			}
		}
		return false;
	}

	private Node findMin(Node node) {
		Node temp = node;
		while (temp.left != null)
			temp = temp.left;
		return temp;
	}

	private Node findMax(Node node) {
		Node temp = node;
		while (temp.right != null)
			temp = temp.right;
		return temp;
	}
	
	private void transplant(Node u, Node v){
		if(u.parent == null)
			root = v;
		else if(u == u.parent.left)
			u.parent.left = v;
		else
			u.parent.right = v;
		if(v != null)
			v.parent = u.parent;
	}
	
	private Node find(T value){
		Node temp = root;
		while(temp!=null){
			if(temp.value == value)
				return temp;
			if(value.compareTo(temp.value)<0)
				temp = temp.left;
			else
				temp = temp.right;
		}
		return temp;
	}
	
	public boolean remove(T value){
		Node temp = find(value);
		if(temp == null)
			return false;
		if(temp.left == null)
			transplant(temp, temp.right);
		else if(temp.right == null)
			transplant(temp, temp.left);
		else{
			Node min = findMin(temp.right);
			if(min.parent != temp){
				transplant(min, min.right);
				min.right = temp.right;
				min.right.parent = min;
			}
			transplant(temp, min);
			min.left = temp.left;
			min.left.parent = min;
		}
		size -= 1;
		return true;
	}
}
