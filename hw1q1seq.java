import java.io.IOException;
import java.util.Arrays;
import edu.rit.util.Random;
import edu.rit.pj.Comm;
/**
 * This program generates INS FNS for N student each with M scores and 
 * the FNS contains Grade values
 * @author Roshan Balaji Nindrai SenthilNathan
 *
 */
public class hw1q1seq {
	// stores the values of the scores
	static float matrix_score[][];
	// counter variable
	int _indexcount=0;
	// Stores the INS values for students
	static float []student_INS;
	// Stores the FNS values for students
	static double[] student_FNS;
	// Stores the Ranking order for students
	static int[] ranking;
	// Stores the INS values for students duplicate
	static float[] tempstudent_INS;
	// Stores the range of grade for student list 
	static double graderange[]=new double[5];
	// Stores the grade point for access.
	static double grade[]={4.0,3.0,2.0,1.0,0.0};
	/**
	 * Prints the error message 
	 */
	public static void usage(){
		System.err.println("Usage:java hw1q1seq <m> <n> <seed>");
		System.err.println("m - numer of students");
		System.err.println("n - number of exams");
		System.err.println("seed - seed value for random generator");
		System.exit(1);
	}
	/**
	 * Main Runs first
	 * to Run the program java hw1q1seq <m> <n> <seed>
	 * m - number of students
	 * n - number of exams
	 * seed - seed value for random generator
	 * @param args Command line arguments
	 * @throws IOException
	 */
public static void main(String args[]) throws IOException{
	 Comm.init (args);
	int number_of_students = 0, number_of_exams = 0;
	long seed = 0;
	if(args.length!=3)usage();
	try{
		// getting the number of students from command line arguments
		 number_of_students=Integer.parseInt(args[0]);
		// getting the number of exams from command line arguments
		 number_of_exams=Integer.parseInt(args[1]);
		// Initializing the score matrix
		 matrix_score=new float[number_of_students][number_of_exams];
		// Initializing the FNS array
		 student_FNS=new double[number_of_students];
		// Initializing the ranking array
		 ranking=new int[number_of_students];
		// Initializing the INS array
		 student_INS=new float[number_of_students];
		// Initializing the duplicate INS array
		 tempstudent_INS=new float[number_of_students];
		 seed=Long.parseLong(args[2]);
		}
		catch(NumberFormatException E){
			usage();
		}
		hw1q1seq object=new hw1q1seq();
		object.genMatrix(number_of_students, number_of_exams, seed);
		long starttime=System.currentTimeMillis();
		object.computeINS(number_of_students, number_of_exams);
		object.computeRange(number_of_students);
		object.lookUp();
		object.computeFNS(number_of_students);
		long finishtime=System.currentTimeMillis();
		//object.print();
		System.out.println("Total Time :"+(finishtime-starttime)+" milliseconds");
}
/**
 * This function is used to generate scores from 0 to 100 inclusive
 * @param _number_of_students Number of students
 * @param _number_of_exams	  Number of exams taken by each student
 * @param _seed				  Seed value to generate random numbers
 */
public void genMatrix(final int _number_of_students,final int _number_of_exams,final long _seed){
	if( _number_of_students!=0&& _number_of_exams!=0&&_seed!=0){
	Random prng=Random.getInstance(_seed);
	for(int i=0;i<_number_of_students;i++){
		for(int j=0;j<_number_of_exams;j++){
			//This generates random float values as scores between 0 and 100 inclusive
			matrix_score[i][j]=prng.nextFloat(100)*100;
			}
	}
}
	else{
		usage();
	}
}
/**
 * This function is used to compute the INS scores
 * @param _number_of_students Number of students
 * @param _number_of_exams	  Number of exams taken by each student
 * 
 */
public void computeINS(final int _number_of_students,final int _number_of_exams){
	float temp_score=0;
	for(int i=0;i<_number_of_students;i++){
		temp_score=0;
		for(int j=0;j<_number_of_exams;j++){
			//squaring the values to calculate the INS
			temp_score=temp_score+(matrix_score[i][j]*matrix_score[i][j]);
		}
		// Storing the INS values in both the original and a duplicate
		tempstudent_INS[i]=(temp_score/(float)_number_of_exams);
		student_INS[i]=(temp_score/(float)_number_of_exams);
		}
Arrays.sort(tempstudent_INS);
}
/**
 * THis function in used to identify the number of students
 * for respective grade values
 * @param _number_of_students Number of students
 */
public void computeRange(final int _number_of_students){
	double temp=(double)_number_of_students;
	double FNS_4=Math.round((.1*temp));
	temp-=FNS_4;
	graderange[0]=FNS_4;
	double FNS_3=Math.round(((.2)*temp));
	temp-=FNS_3;
	graderange[1]=FNS_3;
	double FNS_2=Math.round(((.3)*temp));
	temp-=FNS_2;
	graderange[2]=FNS_2;
	double FNS_1=Math.round(((.2)*temp));
	temp-=FNS_1;
	graderange[3]=FNS_1;
	graderange[4]=temp;
	}
/**
 * This function is used to generate the range for the respective students
 * within the class list.
 * @param _number_of_students total number of students
 */
public void computeFNS(final int _number_of_students){
	int temp=_number_of_students;
	for(int i=0;i<5;i++){
		for(int j=(temp-1);j>=((temp)-getRange(i));j--){
			// gets the grade values and stores it in student FNS array
			int value=ranking[j];
			student_FNS[value]=getGrade(i);
			}
		temp=temp-(int)getRange(i);
		}
	
}
/**
 * The computed rank range is retrived from this function
 * @param _index The range index
 * @return
 */
public double getRange(final int _index){
	return graderange[_index];
}
/**
 * The grade values are retrieved from this function
 * @param _index The grade array index to access
 * @return
 */
public double getGrade(final int _index){
	return grade[_index];
}
/**
 * This function is used to print the 
 * INS and FNS values for respective students.
 */
public void print(){
	System.out.println("Student_ID"+"\t"+"INS");
	System.out.println("-------------------");
	for(int i=0;i<student_INS.length;i++){
		System.out.println(i+"\t\t:"+student_INS[i]);
	}
	System.out.println("--------------------------");
	System.out.println("Student_ID"+"\t"+"FNS");
	System.out.println("-------------------");
	for(int i=0;i<student_FNS.length;i++){
		System.out.println(i+"\t\t:"+student_FNS[i]);
	}
}
/**
 * This function is used to 
 * maintain the indexing of the students
 */
public void lookUp(){
	for(int i=0;i<tempstudent_INS.length;i++){
		ranking[i]=getRank(tempstudent_INS[i]);
	}
}
/**
 * This function is used to get the rank values 
 * by matching it to the entries in a duplicate INS array.
 * @param _key The index that is to be found in 
 * INS array
 * @return
 */
public int getRank(final float _key){
	int key=-1;
	for(int i=0;i<student_INS.length;i++){
		if(student_INS[i]==_key){
			key= i;
		}
	}
	return key;
}
}