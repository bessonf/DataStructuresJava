// On my honor:
//
// - I have not used code obtained from another student,
// or any other unauthorized source, either modified or unmodified.
//
// - All code and documentation used in my program is either my original
// work, or was derived, by me, from the source code provided by the assignment.
//
// - I have not discussed coding details about this project with anyone
// other than my instructor, teaching assistants assigned to this
// course, except via the message board for the course. I understand that I
// may discuss the concepts of this program with other students, and that
// another student may help me debug my program so long as neither of us
// writes anything during the discussion or modifies any computer file
// during the discussion. I have violated neither the spirit nor letter of this restriction.
//

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public class Main {
    public static void main(String[] args) {
    	
    	
    	BinaryTree<Integer> BT = new BinaryTree<Integer>();	//Create new BST
    	
    	// --- Read file from infile.dat (w/ add method) --- // 
		try {
			FileReader fin = new FileReader("infile.dat");
			Scanner src = new Scanner(fin);
			
			int i;
			double d;
			boolean b;
			String str;
			
	        while (src.hasNext()) {
	        	if (src.hasNextInt()) {
		            i = src.nextInt();
		            BT.add(i);	// Add int to BST
	        	} 
	        	else if (src.hasNextDouble()) {
		            d = src.nextDouble();
	        	} 
	        	else if (src.hasNextBoolean()) {
        			b = src.nextBoolean();
            	} 
	        	else {
	        		str = src.next();
        			try {
    	        		str = str.replace(" ", "");
            			str = str.replace(",", "");
	        			i = Integer.parseInt(str);
	        			BT.add(i);	// Add int to BST
        			}
        			catch(Exception e){
        	        	// do nothing
        			}
	        	}
	        }
	        src.close();
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
    	
		// --- Show BST to user (w/ toString method) --- //
		String BST_str = "\nSorted Set A Contains ";
		BST_str = BST_str + BT.toString();
		System.out.println(BST_str);
		
		// --- Allow user to check for value (w/ contains method) --- //
        Scanner reader = new Scanner(System.in);
        int int_choice ;
        String str_choice = "" ;
        
        System.out.print("User Input = ");
        str_choice = reader.nextLine() ;
        try {
        	int_choice =  Integer.parseInt(str_choice);
        	if (BT.contains(int_choice)) { System.out.println("Yes"); } 	//Value is in BST, print Yes
        	else { System.out.println("No"); }								//Value is not in BST, print No
        }
        catch(Exception e){
        	// If incorrect input is given by user, do nothing
    	}
        
        reader.close();
		return;
    	
    }
}
