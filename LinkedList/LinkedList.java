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

import java.util.*;

public class LinkedList<E> 
implements List, Iterable<E>{
    
    Node<E> head;
    int size;
    
    // initialize head and size
    public LinkedList() {
        head = new Node(null);
        size = 0;
    }
    
    // --------------- Private ---------------
    
    // class for node of type
    private static class Node<E> {
        E data;			// initialize data with type E
        Node<E> next;	// initialize node next

        // Creates a new node with a null next field
        private Node(E dataItem) {
            data = dataItem;	// store data item
            next = null;		// for node only dataItem provided, set next equal to null
        }
        
        // Creates a new node that references another node
        private Node(E dataItem, Node<E> nodeRef) {
            data = dataItem;	// store data item
            next = nodeRef;		// set next to reference node
        }
    }
    
    // Get node at specified index
    private Node<E> getNode(int index) {  
    	Node<E> node = head;  
    	for (int i = 0; i < index && node != null; i++) {
    		node = node.next;
		}  
    	return node; //once i reaches index, return current node
	}
    
    // Get at specified index
    public E get(int index) {  
    	Node<E> node = head;  
    	for (int i = 0; i < index && node != null; i++) {
    		node = node.next;
		}  
    	return node.data; //once i reaches index, return current node
	}
    
    // store item at index 0 (in head)
    public void addFirst(E item) {  
    	head = new Node<E>(item, head); // redefine head 
    	size++;	// Increase size by 1 
	}
    
    // remove node at index 0
    private void removeFirst() {  
    	Node<E> temp = head;  
    	if (head != null) {
    		head = head.next; //redefine head as next node
		} 
    	if (temp != null) {   
    		size--;	// decrease size by 1   	
		} 
	}
    
    // add node with item after reference node
    private void addAfter (Node<E> node, E item) {
    	node.next = new Node<E>(item, node.next); // make new node at node.next with item stored
    	size++;	// increase size by 1
	}
    
    // remove node after reference node
    private void removeAfter (Node<E> node) {  
    	Node<E> temp = node.next; // set temp equal to node after reference node
    	if (temp != null) {    
    		node.next = temp.next; // if node after reference node not null, set node after ref node equal to temp   
    		size--;	// decrease size by 1    
		} 
	}
    
    // --------------- Public (List Interface & toString) ---------------
    
    // boolean test if linkedList is empty
    public boolean isEmpty() {
    	if (size == 0) {
    		return true;
    	}
    	else {
    		return false;
    	}
    }

    // Get size of linked list
    public int size() {
    	return size;
    }
    
    // add node with item after last node (index = size)
    public void add (E item) {  
    	add(size, item);   
	}    
    
    // add node at index (index) with item 
    public void add (int index, E item) {
    	// if index outside of accessible indecies, throw err
    	if (index < 0 || index > size) {   
    		throw new                                        
    			IndexOutOfBoundsException(Integer.toString(index)); 
		}
    	// if index == 0, add item using addFirst
    	if (index == 0) {   
    		addFirst(item);  
		} 
    	// else, add node after last node
    	else {   
    		Node<E> node = getNode(index-1);   
    		addAfter(node, item); 
		}
	}
    
    // remove node at index
    public void remove(int index) {
    	if (index < 0 || index > size) {   
    		throw new                                        
    			IndexOutOfBoundsException(Integer.toString(index)); 
		} 
    	if (index == 0) {   
    		removeFirst();  
		} 
    	else {   
    		Node<E> node = getNode(index-1);   
    		removeAfter(node); 
		}
	}	

    // remove node with specific item stored in it
    public void remove(Object item) {
    	int count = 0;
    	Node<E> nodeRef = head;
    	while (nodeRef != null) {
    		if (new String(String.valueOf(nodeRef.data)).equals(String.valueOf(item))) {
    			if (count == 0) {removeFirst();}
    			else {
	        		Node<E> node = getNode(count-1);   
	        		removeAfter(node); 
    			}
    		}
    		nodeRef = nodeRef.next; 
    		count++;
		} 
    }

    // return duplicate of Linkedlist
    public LinkedList duplicate() {
    	
    	LinkedList<E> return_list = new LinkedList<E>();
    	
    	int i_node = 0;
    	Node<E> nodeRef = getNode(i_node);
    			
    	while (nodeRef != null) {
    		if (nodeRef.data != null) {return_list.add(nodeRef.data);}
    		i_node++;
    		nodeRef = getNode(i_node);
		} 

    	return return_list;
    }
    
    // return reverse duplicate of Linkedlist 
    public LinkedList duplicateReversed() {
    	LinkedList<E> return_list = new LinkedList<E>();
    	
    	int i_node = size-1;
    	Node<E> nodeRef = getNode(i_node);
    			
    	while (i_node >= 0) {
    		if (nodeRef.data != null) {return_list.add(nodeRef.data);}
    		i_node--;
    		nodeRef = getNode(i_node);
		} 

    	return return_list;	
    }
    
    // return size and contents of LinkedList as a string 
    public String toString() { 
    	
    	Node<E> nodeRef = head; 
    	StringBuilder result = new StringBuilder(); 
    	
    	result.append("[ size: ");
    	result.append(size);
    	result.append(" ");
    	
    	int count = 0;
    	if (nodeRef.data != null) {
	    	while (nodeRef.data != null) {   
	    		if (count > 0) {
	    			result.append(", ");
				}
	    		if (nodeRef.data != null) {
	    			result.append(nodeRef.data);
				}
	    		nodeRef = nodeRef.next; 
	    		count++;
			} 
	    	result.append(" ]");
	    	return result.toString();
    	}
    	return result.toString();
	}
    
    @Override
    public Iterator<E> iterator() {
    	
    	ArrayList<E> ret_list = new ArrayList<E>();
    	
    	Node<E> nodeRef = head; 
    	while (nodeRef.data != null) {   
    		if (nodeRef.data != null) {
    			ret_list.add(nodeRef.data);
			}
    		nodeRef = nodeRef.next; 
		} 
    	
    	return ret_list.iterator();
    }
    
//    @Override
//    public Iterator<E> iterator() {
//        return new Iterator<E> () {
//            private final Iterator<E> iter = this.iterator();
//
//            @Override
//            public boolean hasNext() {
//                return iter.hasNext();
//            }
//
//            @Override
//            public E next() {
//                return iter.next();
//            }
//
//            @Override
//            public void remove() {
//                throw new UnsupportedOperationException("no changes allowed");
//            }
//        };
//    }
  
}