import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;
import edu.rit.pj.Comm;
import edu.rit.pj.IntegerForLoop;
import edu.rit.pj.ParallelRegion;
import edu.rit.pj.ParallelTeam;
/**
 * This program generates a list of Palindrome and its count
 * 
 * @author Roshan Balaji Nindrai SenthilNathan
 *
 */
public class hw1q2smp {
	// A string array to store the strings from input file
	public static String[] list;
	// A vector to store the resulting palindrome
	public static Vector<String> finallist=new Vector<String>();
	static int number_of_palindrome=0;
	/**
	 * Prints the error message 
	 */
	public static void usage(){
		System.err.println("Usage:java hw1q2smp <file_name>");
		System.err.println("file_name - enter a valid file name");
		System.exit(1);
	}
	/**
	 * Main Runs first
	 * to Run the program java hw1q2smp <file Name>
	 * @param args Command line arguments
	 * @throws IOException
	 */
public static void main(String args[]) throws Exception{
	Comm.init (args);
	// File to hold the input file
	File inputfile = null;
	// Scanner to read the file inputs
	Scanner scan = null;
	int number_of_strings=0;
	// Checking the number of arguments being passed
	if(args.length!=1)usage();
	try{
		String file_name=args[0];
		 inputfile=new File(file_name);
	}
	catch(Exception E){
		System.err.println("Enter a valid file name");
		System.exit(1);
	}
	try {
		scan=new Scanner(inputfile);
	} catch (FileNotFoundException e) {
		System.err.println("File not Found");
		System.exit(1);
	}
	// to get the number of number of input strings
	number_of_strings=scan.nextInt();
	// List array to store all the strings to check
	 list=new String[number_of_strings+1];
	 hw1q2smp object=new hw1q2smp();
	object.initialize(number_of_strings, scan);
	long starttime=System.currentTimeMillis();
	object.check(number_of_strings);
	long finishtime=System.currentTimeMillis();
	System.out.println("Index "+"\t"+"Palindrome");
	object.display();
	System.out.println("Number Of Palindrome :"+finallist.size());
	System.out.println("Total Time :"+(finishtime-starttime)+" milliseconds");
}
/**
 * This function reads and initialize the list array with strings
 * @param _number_of_strings The number of string in the file
 * @param _scan	Scanner to read the string from the file
 */
public void initialize(final int _number_of_strings, Scanner _scan){
	int counter=0;
	while(_scan.hasNext()){
		String _input=_scan.nextLine().trim();
		list[counter]=_input.replaceAll("[^\\p{L}\\p{N}]", "");
		counter++;
		}
}
/**
 * This function is parallelized to check a subset of
 * inputs strings for Palindrome
 * @param _number_of_strings The number of strings in the array
 * @throws Exception
 */
public void check(final int _number_of_strings) throws Exception{
	new ParallelTeam().execute (new ParallelRegion()
	{
		public void run() throws Exception
		{
		execute (0,_number_of_strings,new IntegerForLoop()
		{
		String input;
		public void start()
			{
			//Initializing local variable
			input=null;
			}
			public void run(int first,int last){
				for(int counter=first;counter<=last;counter++){
					input=list[counter];
					// Checking the strings for comparison
		if(input.length()==1&&!input.equals("")){
			finallist.add(input);
			}
		// Converting the input to lower cases before comparison
		else if(input.trim().toLowerCase().equals(reverse(input.trim().toLowerCase()))&&!input.equals("")){
			finallist.add(input);
			}
		
	}
}
});
		}
	});
	
}
/**
 * This function is used to display the Palindrome strings 
 */
public void display(){
	for(int counter=0;counter<finallist.size();counter++){
		System.out.println(counter+"\t"+finallist.elementAt(counter));
	}
}
/**
 * 
 * @param _input	The input string to reverse
 * @return String that is reverse of input
 */
public String reverse(final String _input){
	return new StringBuffer(_input).reverse().toString();
	
}
}