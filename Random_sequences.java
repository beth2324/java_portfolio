package first_lab;
//import random
import java.util.Random;

public class Random_sequences {
	public static void main(String args[])
	{
		Random random = new Random(); //sets up random to be usable
		System.out.println("RUNNING NORMAL DISTRIBUTION NOW");
		int count_AAA=0;
		for(int x=0; x<1000; x++)
		{
			String sequence="";
			//make 3mer with normal distribution
			for(int y=0;y<3;y++)
			{
				int choice=0;
				choice=random.nextInt(4);
				if (choice==0)
				{
					sequence+="A";
				}
				else if (choice==1)
				{
					sequence+="T";
				}
				else if (choice ==2)
				{
					sequence+="G";
				}
				else
				{
					sequence+="C";
				}
			}
			if(sequence.equals("AAA"))
			{
				System.out.println(sequence); //CHECK BY BETH
				count_AAA+=1;
			}
			//System.out.println(sequence);	
		}
		//System.out.println("Starting new output with set probabilities");
		
		//SECOND SET WITH NON-RANDOM DISTRIBUTION----------------------------------------------------------------------------
		System.out.println("RUNNING SET DISTRIBUTION NOW");
		int second_count_AAA=0;
		for(int n=0; n<1000; n++)
		{
			String second_sequence="";
			for(int z=0;z<3;z++)
			{
				//choose letter based on probability of presence - helped by this website: https://stackoverflow.com/questions/9330394/how-to-pick-an-item-by-its-probability
				int second_choice=random.nextInt(100);
				if (second_choice<=12)
				{
					second_sequence+="A";
				}
				else if (second_choice<=50)
				{
					second_sequence+="C";
				}
				else if (second_choice <=89)
				{
					second_sequence+="G";
				}
				else
				{
					second_sequence+="T";
				}
			}
			if(second_sequence.equals("AAA"))
			{
				System.out.println(second_sequence); //CHECK BY BETH
				second_count_AAA+=1;
			}
			//System.out.println(second_sequence);	
		}
		//print "AAA" counts across both runs
		System.out.println(count_AAA+" 'AAA' in first 1000 3-mers, and " + second_count_AAA+" 'AAA' in the second 1000 3-mers.");	
		System.out.println("Considering'AAA' has an expected normal distibution representation of 15.6 and set probability representation of 1.7,"+"\n"+"This supports the accuracy of our code.");
		System.out.println("RUNNING CHAT GPT CODE NOW");
		
		/*CHAT GPT CODE  ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
        based on this input: "n java, output 1000 dna 3-mers with an expected frequency of A=0.12, C=0.38, G=0.39, and T=0.11. 
        count the total number of "AAA" 3-mers in the output"*/
		
        // Frequencies of nucleotides
        double freqA = 0.12;
        double freqC = 0.38;
        double freqG = 0.39;
        double freqT = 0.11;

        // Number of 3-mers to generate
        int num3mers = 1000;
        String target3mer = "AAA";
        int countTarget3mer = 0;

        // Random number generator
        Random rand = new Random();

        for (int i = 0; i < num3mers; i++) {
            // Generate a 3-mer
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < 3; j++) {
                double r = rand.nextDouble();
                if (r < freqA) {
                    sb.append('A');
                } else if (r < freqA + freqC) {
                    sb.append('C');
                } else if (r < freqA + freqC + freqG) {
                    sb.append('G');
                } else {
                    sb.append('T');
                }
            }
            String threeMer = sb.toString();
            
            // Check if the generated 3-mer is "AAA"
            if (threeMer.equals(target3mer)) {
                countTarget3mer++;
            System.out.println(threeMer); //THIS IS A CHECK BY BETH
            }
            //System.out.println(threeMer); //THIS IS A CHECK BY BETH
        }

        // Output the count of "AAA"
        System.out.println("Number of occurrences of 'AAA': " + countTarget3mer);
        //beth evaluation notes
        System.out.println("Considering this opperates in the same way that my code opperates, it can be evaluated visually."+"\n"+"In addition to making sure it is similar to the expected representation.");
	}
	
}


