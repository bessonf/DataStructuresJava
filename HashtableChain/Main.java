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
	
/*						  *--PROFESSOR--*
 *  --*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*
 *	|	PROFESSOR!!!        PROFESSOR!!!       PROFESSOR!!!				|
 * 	|																	|
 * 	|	In order to change where the program looks for the words 		|
 *  |	file change the value of wordFilePath in line 52				|
 * 	|																	|
 * 	|	In order to spell check a file, run main from the command		|
 * 	|	line with the path to the file to be checked as the input arg	|
 * 	|																	|
 * 	|	For near misses, my algorithm generates the proper number of 	|
 * 	|	near misses stated by the assignment description, however 		|
 *  |	repeats are created using this logic. I created a 				|
 *  |	removeDuplicates method in HashTableChain.java to remove these 	|
 *  |	duplicates. If you wish to see the duplicates, uncomment line 	|
 *  |	286 in HashTableChain and comment out line 287					|
 *  |																	|
 *  |	PROFESSOR!!!        PROFESSOR!!!       PROFESSOR!!!				|
 *  --*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*
 * 						   *--PROFESSOR--*
 */

/*
 * 
 * HASH FUNCTION BEING USED IS string.hashcode
 * 
 */

import java.util.*;

import javax.sound.sampled.Line;

import java.io.*;

public class Main {
    
    public static void main(String[] args) {
       
        /*  Handle input arguments
         	more than 1 input argument returns an error message
			1 input argument assumed to be path to file that should be spell-checked
			0 input argument prompts user to enter single word to be spell checked
        */
        if (args.length > 1){
            System.out.println("Wrong number of input arguments provided");
            System.out.println("Please provide 1 or no input arguments");
            return;
        } 
        
        /*  Create HashTableChain with words file (delimiter: new line)
			In order to specify 
        */
        String wordFilePath = "/usr/share/dict/words"; // Path to words file
        HashTableChain<String, String> HashT = new HashTableChain<String, String>();
        //HashTableChain<String, String> HashT = new HashTableChain<String, String>(5);
        try {
            FileInputStream fis = new FileInputStream(wordFilePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
            String line = null;
            while ((line = br.readLine()) != null) {
                HashT.add(line.toLowerCase(), line);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        String in_str = "";
        
        /*	if no arguments are provided
         * 	prompt user to input a single word
         * 	this word will be checked using checkForString
         */
        if (args.length == 0) {
            Scanner reader = new Scanner(System.in);
            //int wordLookup = 0 ;
            //while (wordLookup==0) {
            System.out.print("\nword: ");
            try {
                in_str = reader.nextLine();
                //System.out.println(in_str);
                checkForString(HashT, in_str);
                reader.close();
                //if (in_str.equals("-1")) {
                    //wordLookup = 1;
                    //continue;
                //}
                System.out.println();
            } catch(Exception e) {
                reader.close();
                //wordLookup = 1;
                //continue;
            }
            return;
        } 
          
        /*	if 1 argument is provided
         * 	spellCheckFilePath will be set to the argument provided
         * 	the file at that path will be checked word-by-word using spellCheckFilePath
         */
        if (args.length ==1){   
            try {
                String spellCheckFilePath = args[0];
                FileInputStream fis = new FileInputStream(spellCheckFilePath);
                BufferedReader br = new BufferedReader(new InputStreamReader(fis));
                String line = null;
                while ((line = br.readLine()) != null) {
                    String[] arrOfStr = line.split(" ");
                    for(String indivWord : arrOfStr){
                        indivWord = indivWord.replaceAll("[ .;,:!-]","");
                        System.out.printf("\nword: %s\n", indivWord);
                        checkForString(HashT, indivWord);
                    }
                }
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /*  checkForString
        check for key in HashTableChain using contains method
            - if key found, print "ok"
            - if key not found, generate near misses, 
              search in HashTableChain for each near miss, 
              and print near misses that are in HashTableChain
            - if key not found, and near misses aren't 
              found, print "not found" */
    public static void checkForString(HashTableChain<String, String> HashT, String in_str){
        if (HashT.contains(in_str.toLowerCase(), in_str)) {
            System.out.println("ok");
        } else {
            //LinkedList<String> NearMisses = new LinkedList<String>();
            ArrayList<String> NearMisses = new ArrayList<String>();
            NearMisses = HashT.generateNearMisses(in_str);
            StringBuilder nearMissOutput = new StringBuilder();
            nearMissOutput.append("Near Misses: ");
            for(String nearMiss : NearMisses) {
                if (HashT.contains(nearMiss.toLowerCase(), nearMiss)){
                    nearMissOutput.append(nearMiss + ", ");
                }
            }

            if (nearMissOutput.toString().equals("Near Misses: ")){
                System.out.println("not found");
            } else {
                nearMissOutput.setLength(nearMissOutput.length() - 2);  //delete space and comma at end
                System.out.println(nearMissOutput.toString());
            }
        }
    }

}
