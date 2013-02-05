import java.io.IOException;
import edu.rit.pj.Comm;
/**
 * This class is to used to produce cos(x) for x 
 * such that 0<=x<=15000 using taylor series
 * @author Roshan Balaji Nindrai SenthilNathan
 *
 */
public class hw1q4seq {
	/**
	 * Main runs first
	 * @param args Command line arguments
	 * @throws IOException
	 */
public static void main(String[] args) throws IOException{
	 Comm.init (args);
	 // Start time for the process
	 long startTime=System.currentTimeMillis();
	 // loop to read the input of x from 0 to 15000 
	for(double i=Double.valueOf(0);i<=Double.valueOf(15000.1);i+=.1)
		// Moding the value with Pi to bring the value back into the field
			taylorSeries(i%(2*Math.PI));
	// The finish time of the process
	long finishTime=System.currentTimeMillis();
	// The total time taken in milliseconds
	System.out.println("Total Time :"+(finishTime-startTime)+" milliseconds");
}
/**
 * This function is used to calculate the cos value using taylor series
 * @param _value
 * @return Sum	The sum is the cos value with three decimal precision
 */
public static double taylorSeries(final double _value){
	
	int offset=0;
	double numerator;
	double denominator;
	double sum=1;
	double value=1;
	do
	{
		numerator=((-1)*(Math.pow(_value,2))*value);
		denominator=((2*(offset)+1)*(2*(offset)+2));
		value=numerator/denominator;
		sum=sum+value;
		offset++;
	}while(Math.abs((value/sum))>0.001);
	System.out.println(_value+": "+sum);
	return sum;
}
}
