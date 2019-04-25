//On my honor:
//
//- I have not used code obtained from another student,
//or any other unauthorized source, either modified or unmodified.
//
//- All code and documentation used in my program is either my original
//work, or was derived, by me, from the source code provided by the assignment.
//
//- I have not discussed coding details about this project with anyone
//other than my instructor, teaching assistants assigned to this
//course, except via the message board for the course. I understand that I
//may discuss the concepts of this program with other students, and that
//another student may help me debug my program so long as neither of us
//writes anything during the discussion or modifies any computer file
//during the discussion. I have violated neither the spirit nor letter of this restriction.
//

import java.io.*;

public class BinaryTree<E extends Comparable<E>> implements Serializable{
    
	private boolean addReturn;
	private boolean deleteReturn;
    
    // initialize tree (head and size)
    public BinaryTree() {
        root = null;
    }
    
    public BinaryTree(Node<E> root) {
    	this.root = root;
    }
    
    public BinaryTree(E data, BinaryTree<E> leftTree, BinaryTree<E> rightTree) {
    	root = new Node<E>(data);
    	if (leftTree != null) {
    		root.left = leftTree.root;
    	} else {
    		root.left = null;
    	}
    	if (rightTree != null) {
    		root.right = rightTree.root;
    	} else {
    		root.right = null;
    	}
	}
    
    // class for Node
    protected static class Node<E> implements Serializable {
		protected E data;
		protected Node<E> left;
		protected Node<E> right;

		public Node(E data) {
			this.data = data;
			left = null;
			right = null;
		}

		public String toString() {
			return data.toString();
		}
    }
    
    protected Node<E> root;
    
    /** isEmpty method.
    @return if BinaryTree is empty, returns true
    		else, returns false.
    */
    public boolean isEmpty() {
    	if (root == null) {
    		return true;
    	} else {
    		return false;
    	}
    }
    
    /** Recursive add method.
    post: The item is not in the tree;
          addReturn is true after item stored in tree
          addReturn is false if item in tree
    @param localRoot The root of the current subtree
    @param item The item to be added
    @return The modified local root leading to the
    		proper node that item will be connected to
    */
    private Node<E> add(Node<E> localRoot, E item) {
        if (localRoot == null) {
	    	// item is not in the tree — insert it.
	    	addReturn = true;
	    	return new Node<E>(item);
        } else if ((item).compareTo(localRoot.data) == 0) {
	    	// item is equal to localRoot.data
	    	addReturn = false;
	    	return localRoot;
        } else if (item.compareTo(localRoot.data) < 0) {
	    	// item is less than localRoot.data
	    	localRoot.left = add(localRoot.left, item);
	    	return localRoot;
        } else {
	    	// item is greater than localRoot.data
	    	localRoot.right = add(localRoot.right, item);
	    	return localRoot;
        }
    }
    
    public boolean add(E item) {
    	root = add(root, item);
    	return addReturn;
    }
    
    
    /** Recursive delete method.
    post: The item is not in the tree;
          deleteReturn is equal to the deleted item
          as it was stored in the tree or null
          if the item was not found.
    @param localRoot The root of the current subtree
    @param item The item to be deleted
    @return The modified local root that does not contain
            the item
    */
    private Node < E > remove(Node < E > localRoot, E item) {
        if (localRoot == null) {
        	// item is not in the tree - can't delete it.
        	deleteReturn = false;
        	return localRoot;
        } 
        
        else if (item.compareTo(localRoot.data) < 0) {
	    	// item is less than localRoot.data
	    	localRoot.left = remove(localRoot.left, item);
	    	return localRoot;
        } 
        else if (item.compareTo(localRoot.data) > 0) {
	    	// item is greater than localRoot.data
	    	localRoot.right = remove(localRoot.right, item);
	    	return localRoot;
        }
        
        else {
        	deleteReturn = true;
        	if(localRoot.left == null && localRoot.right == null) {
    			localRoot = null;
        		return localRoot;
        	} else if(localRoot.left == null && localRoot.right != null) {
    			localRoot = localRoot.right;
        		return localRoot;
        	} else if(localRoot.left != null && localRoot.right == null) {
    			localRoot = localRoot.left;
        		return localRoot;
        	} else if(localRoot.left != null && localRoot.right != null) {
        		
        		// method 2 in class reading "Binary Search Trees.pdf (smallest element in right subtree)
        		if (localRoot.right.left == null && localRoot.right.right == null) {
        			localRoot.data = localRoot.right.data;
        			localRoot.right = null;
        		} else if(localRoot.right.left == null && localRoot.right.right != null) {
        			localRoot = localRoot.right;
        		} else if(localRoot.right.left != null) {
    				Node<E> tmp_node = new Node<E>(null);
            		tmp_node = localRoot.right;
            		while(tmp_node.left.left != null) { tmp_node = tmp_node.left; }
            		localRoot.data = tmp_node.left.data;
            		tmp_node.left = null;
        		}
        		return localRoot;
        	}
    		
    		else {
    			return localRoot;
    		}	
        }
    }
    
	public boolean remove(E target) {
	  root = remove(root, target);
	  return deleteReturn;
	} 
	
    /** Starter method contains.
     * pre: The target object must implement
     *      the comparable interface (compareTo).
     * @param target The Comparable object being sought     	 
     * @return The object, if found, otherwise null
     */    
    private E contains(Node<E> localRoot, E target) {
    	if (localRoot == null) { return null; }
    	int compResult = target.compareTo(localRoot.data);
    	if (compResult == 0) { return localRoot.data; }
		else if (compResult < 0) { return contains(localRoot.left, target); }
		else { return contains(localRoot.right, target); }
    }
    
    public boolean contains(E target) {
    	if (contains(root, target) == null) { return false; } 
    	else { return true; }
    }

    /** preOrderTraverse method
     * @param node The node to be added to order   	 
     */ 
    
    private void preOrderTraverse(Node<E> node, StringBuilder sb) {
    	if (node == null) { } 
		else {
    		sb.append(node.toString());
    		sb.append(", ");
    		preOrderTraverse(node.left, sb);
    		preOrderTraverse(node.right,  sb);
    	}
    }
    
    
    /** toString method
     * @return string containing preOrderTraverse set of data in all nodes
     */    
    public String toString() {
    	  StringBuilder sb = new StringBuilder();
    	  //preOrderTraverse(root, 1, sb);
    	  //return sb.toString();
    	  preOrderTraverse(root, sb);
    	  return sb.toString().substring(0, sb.length() - 2);
	}

}