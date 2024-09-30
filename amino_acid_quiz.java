package second_lab;
import java.util.Random;
public class amino_acid_quiz 
{	
	//answer storage
	public static String[] SHORT_NAMES = { "A","R", "N", "D", "C", "Q", "E", "G",  "H", "I", "L", "K", "M", "F", "P", "S", "T", "W", "Y", "V" };
	public static String[] FULL_NAMES = {"alanine","arginine", "asparagine", "aspartic acid", "cysteine", "glutamine",  "glutamic acid",	"glycine" ,"histidine","isoleucine", "leucine",  "lysine", "methionine", "phenylalanine", "proline", "serine","threonine","tryptophan", "tyrosine", "valine"};
	public static void main(String[] args) 
	{
		Random random = new Random();
		
		System.out.println("\n"+"How long would you like the quiz to last in seconds?"); 
		//help for inputting numbers from this website: https://stackoverflow.com/questions/2506077/how-to-read-integer-value-from-the-standard-input-in-java
		int parameter = Integer.parseInt(System.console().readLine());
		System.out.println("Okay, lets begin! \n---------------------------------------------------");
		long start_time = System.currentTimeMillis();
		
		//quiz question
		int score=0;
		int count=0;
		float elapsed_time=0;
		while (elapsed_time<parameter)
		{
			int x=random.nextInt(FULL_NAMES.length);
			System.out.println(FULL_NAMES[x]);
			
			//get and check user's answer
			String input = System.console().readLine().toUpperCase();
			String response = "" + input.charAt(0);
			count++;
			if(response.toUpperCase().equals(SHORT_NAMES[x]))
			{
				score++;
				elapsed_time=(System.currentTimeMillis()-start_time)/1000f;
			}
			else
			{
				System.out.println("WRONG ANSWER - "+SHORT_NAMES[x]);
				break;
			}
		}
		System.out.println("GAME OVER! You scored "+ score +"/"+ count);
	}
}
