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

public class HashTableChain<K, V> {
	
    private int numKeys;        // The number of keys in HashTableChain
    private int CAPACITY;       // The capacity of HashTableChain
    private static final double LOAD_THRESHOLD = .75;   // The max load factor
    private LinkedList<Entry<K, V>>[] table;

	/*	Constructor for when no size is provided */
    public HashTableChain() {
    	CAPACITY = 11;
    	table = new LinkedList[CAPACITY];
    }
    
    /* 	Constructor for when size is provided */
    public HashTableChain(int size_of_table) {
        CAPACITY = size_of_table;
    	table = new LinkedList[CAPACITY];
    }
    
    /* 	Entry class that holds key:value pair */
	private static class Entry<K, V> {

        private K key;
        private V value;

    	/* Entry constructor class */
        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }
        
        /* Get key from entry */
        public K getKey() {
            return key;
        }

        /* Get val from entry */
        public V getValue() {
            return value;
        }

    	/* Set val in entry */
        public V setValue(V val) {
            V oldVal = value;
            value = val;
            return oldVal;
        }
        
        /* Return string of key:val pair */
        public String toString() {
            return key + "=" + value;
        }
    }

	/* Get value at a specified key */
    public V get(Object key) {
        int index = key.hashCode() % table.length;
        if (index < 0) {
            index += table.length;
        }
        if (table[index] == null) {
            return null; // key is not in the table.
        }
        // Search the list at table[index] to find the key.
        for (Entry<K, V> nextItem : table[index]) {
            if (nextItem.key.equals(key)) {
                return nextItem.value;
            }
        }

        return null;
    }

	/* Add key:val pair to HashTableChain */
    public V add(K key, V value) {
        int index = key.hashCode() % table.length;

        if (index < 0) {
            index += table.length;
        }
        
        if (table[index] == null) {
            table[index] = new LinkedList<Entry<K, V>>();   // Create a new linked list at table[index].
        }

        // Search the list at table[index] to find the key.
        boolean found_val = false;
        for (Entry<K, V> nextItem : table[index]) {
            if (nextItem.value.equals(value)) {
            	found_val = true;
            }
        }
        
        if (!found_val) {
        	table[index].add(new Entry<K, V>(key, value));
        	numKeys = numKeys + 1;
        }
        
        if (numKeys > (LOAD_THRESHOLD * table.length)) {
            rehash();
        }
        
        return null;
    }

    /* Check if HashTable chain is empty */
    public boolean isEmpty() {
        return numKeys == 0;
    }
    
    /* Double size + 1 of HashTableChain and reinsert key:val pairs */
    private void rehash() {
        LinkedList<Entry<K,V>>[] oldTable = table;
        table = new LinkedList[oldTable.length * 2 + 1];
        numKeys = 0;
        for (LinkedList<Entry<K, V>> list : oldTable) {
            if (list != null) {
                for (Entry<K,V> entry : list) {
                    add(entry.getKey(), entry.getValue());
                }
            }
        }
    }

	/* CHeck if HashTableChain contains key:val pair */
    public boolean contains(Object key, Object value) {
        int index = key.hashCode() % table.length;
        if (index < 0) {
            index += table.length;
        }
        if (table[index] == null) {
            return false; // key is not in table
        }

        for (Entry<K, V> entry : table[index]) {
            if (entry.getValue().equals(value)) {
                return true;
            }
        }
        return false;
    }
    
    /* Remove key:val pair from HashTableChain */
    public boolean remove(Object key, Object value) {
        int index = key.hashCode() % table.length;
        if (index < 0) {
            index += table.length;
        }
        if (table[index] == null) {
            return false; // key is not in table
        }
        for (Entry<K, V> entry : table[index]) {
            if (entry.getValue().equals(value)) {
                table[index].remove(entry);
                numKeys = numKeys - 1;;
                if (table[index].isEmpty()) {
                    table[index] = null;
                }
                return true;
            }
        }
        return false;
    }

    /* Return size of HashTableChain */
    public int size() {
        return numKeys;
    }
    
    /* Get a preview (row 0-2) of HashTableChain */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        
        //for (int i = 0; i < table.length; i++) {
        for (int i = 0; i < 3; i++) {
            sb.append("row:" + i + "\t");
            if (table[i] != null){
                for (Entry<K,V> entry : table[i]) {
                    sb.append("-> " + entry + " ");
                }
            }
            else {
              sb.append("null");  
            }
            sb.append("\n");
        }
        return sb.toString();
    }
    
    
    /* 	Generate near misses for a given string 
    [x] Construct every string that can be made by deleting one letter from the word. (n possibilities, where n is the length of the word)
    [x] Construct every string that can be made by inserting any letter of the alphabet at any position in the string. (26*(n+1) possibilities)
    [x] Construct every string that can be made by swapping two neighboring characters in the string. (n-1 possibilities)
    [x] Construct every string that can be made by replacing each letter in the word with some letter of the alphabet. (26*n possibilities (including the original word n times, which is probably easier than avoiding it))
    [x] Construct every pair of strings that can be made by inserting a space into the word. (n-1 pairs of words; you have to check separately in the dictionary for each word in the pair)
    */
    public ArrayList<String> generateNearMisses(String str) {
        ArrayList<String> NearMisses = new ArrayList<String>();
        int i_tot = 0; 

        // Delete single char (n possibilities)
        for (int i = 0; i < str.length(); i++) {
            StringBuilder buildStr = new StringBuilder(str);
            buildStr.deleteCharAt(i);
            NearMisses.add(buildStr.toString());
            i_tot = i_tot + 1;
        }

        // insert any letter of the alphabet (26*(n+1) possibilities)
        char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        for (int i = 0; i <= str.length(); i++) {
            StringBuilder buildStr = new StringBuilder(str);
            for (int i_let = 0; i_let < alphabet.length; i_let++){
                buildStr = new StringBuilder(str);
                if (i < str.length() && buildStr.charAt(i) != alphabet[i_let]) {
                    buildStr.insert(i, alphabet[i_let]);
                    NearMisses.add(buildStr.toString());
                    i_tot = i_tot + 1;
                } else {
                    buildStr.insert(i, alphabet[i_let]);
                    NearMisses.add(buildStr.toString());
                    i_tot = i_tot + 1;
                }
            }
        }

        // Swap two char (n-1 possibilities)
        for (int i = 0; i < str.length()-1; i++) {
            StringBuilder buildStr = new StringBuilder(str);
            if (buildStr.charAt(i) != buildStr.charAt(i+1)){
                String old_buildStr = buildStr.toString();
                buildStr.setCharAt(i, old_buildStr.charAt(i+1));
                buildStr.setCharAt(i+1, old_buildStr.charAt(i));
                NearMisses.add(buildStr.toString());
                i_tot = i_tot + 1;
            }
        }

        // Replace char with any letter of the alphabet (26*n possibilites)
        for (int i = 0; i < str.length(); i++) {
            StringBuilder buildStr = new StringBuilder(str);
            for (int i_let = 0; i_let < alphabet.length; i_let++){
                buildStr = new StringBuilder(str);
                //if (buildStr.charAt(i) != alphabet[i_let]) {
                buildStr.setCharAt(i, alphabet[i_let]);
                NearMisses.add(buildStr.toString());
                i_tot = i_tot + 1;
                //}
            }
        }

        // insert a space (n-1 pairs, 2*(n-1) words)
        for (int i = 1; i < str.length(); i++) {
            StringBuilder buildStr = new StringBuilder(str);
            buildStr.insert(i, " ");
            String[] split_buildStr = buildStr.toString().split(" ");
            for (int i_split= 0; i_split < split_buildStr.length; i_split++){
                if(split_buildStr[i_split] != null && !split_buildStr[i_split].isEmpty()) {
                    NearMisses.add(split_buildStr[i_split]);
                    i_tot = i_tot + 1;
                }
            }  
        }

        /*
        // Caps
        for (int i = 0; i < str.length(); i++) {
            StringBuilder buildStr = new StringBuilder(str);
            char testChar = str.charAt(i);
            if (Character.isUpperCase(testChar)){
                testChar = Character.toLowerCase(testChar);
            } else if (Character.isLowerCase(testChar)) {
                testChar = Character.toUpperCase(testChar);
            } else {
                continue;
            }
            buildStr.setCharAt(i, testChar);
             NearMisses.add(buildStr.toString());
            i_tot = i_tot + 1;
        }
        */
        
        //return Collections.sort(NearMisses);		//with repeats
        return this.removeDuplicates(NearMisses);	//without repeats
    }
    
    /* Remove duplicates from an Arraylist (aka generateNearMisses result)*/
    public ArrayList<String> removeDuplicates (ArrayList<String> NearMissList) {
        ArrayList<String> NearMissListReturn = new ArrayList<String>();
    	for (String str : NearMissList) {
    		if (!NearMissListReturn.contains(str)) {
    			NearMissListReturn.add(str);
    		}
    	}
    	Collections.sort(NearMissListReturn);
    	return NearMissListReturn;
    }
    
}