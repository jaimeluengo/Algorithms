/**Gale-Shaple Algorithm for stable matching
 * Men-Women Example
 * Takes as input a text file with the number of women (that equals the number of men) and the rankings of both sets
 * Jaime Luengo Rozas
 * 02/03/2018
 */



import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.*;

public class Framework
{
	int n; // number of men (women)

	int MenPrefs[][]; // preference list of men (n*n)
	int WomenPrefs[][]; // preference list of women (n*n)

	ArrayList<MatchedPair> MatchedPairsList; // your output should fill this arraylist which is empty at start

	public class MatchedPair
	{
		int man; // man's number
		int woman; // woman's number

		public MatchedPair(int Man,int Woman)
		{
			man=Man;
			woman=Woman;
		}

		public MatchedPair()
		{
		}
	}

	// reading the input
	void input(String input_name)
	{
		File file = new File(input_name);
		BufferedReader reader = null;

		try
		{
			reader = new BufferedReader(new FileReader(file));

			String text = reader.readLine();

			String [] parts = text.split(" ");
			n=Integer.parseInt(parts[0]);

			MenPrefs=new int[n][n];
			WomenPrefs=new int[n][n];

			for (int i=0;i<n;i++)
			{
				text=reader.readLine();
				String [] mList=text.split(" ");
				for (int j=0;j<n;j++)
				{
					MenPrefs[i][j]=Integer.parseInt(mList[j]);
				}
			}

			for (int i=0;i<n;i++)
			{
				text=reader.readLine();
				String [] wList=text.split(" ");
				for(int j=0;j<n;j++)
				{
					WomenPrefs[i][j]=Integer.parseInt(wList[j]);
				}
			}

			reader.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	// writing the output
	void output(String output_name)
	{
		try
		{
			PrintWriter writer = new PrintWriter(output_name, "UTF-8");

			for(int i=0;i<MatchedPairsList.size();i++)
			{
				writer.println(MatchedPairsList.get(i).man+" "+MatchedPairsList.get(i).woman);
			}

			writer.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public Framework(String []Args)
	{
		input(Args[0]);

		/* NOTE
		 * if you want to declare that man x and woman y will get matched in the matching, you can
		 * write a code similar to what follows:
		 * MatchedPair pair=new MatchedPair(x,y);
		 * MatchedPairsList.add(pair);
		 */

		//YOUR CODE STARTS HERE
		
		MatchedPairsList=new ArrayList<MatchedPair>(); // you should put the final stable matching in this array list
		MatchedPair pair=new MatchedPair(1,1);
		boolean[] WomanIsProposed = new boolean[n]; //initialized to false
		boolean[] ManIsNotFree = new boolean[n]; //initialized to false
		int[] ManProposedIndex = new int[n];
		int[] PositionIteration = new int[n];
		int[][] ReverseIndex = new int[n][n];//Woman and men ordered increasingly from 0
		int i=0;
		int WomanIndex,PreviousMan;
		int[] MarriedCouple = new int[n];
		int EngagedCounter=0; //Counts the number of Engaged man

		//Preprocessing
		for(i=0;i<n;i++) {
			for(int j=0; j<n; j++) {
				ReverseIndex[i][WomenPrefs[i][j]]=j;
			}
		}


		i=0;//initializer

		while(EngagedCounter<n) {
			if(PositionIteration[i]<n && ManIsNotFree[i]==false) {
				WomanIndex=MenPrefs[i][PositionIteration[i]];
				if(WomanIsProposed[WomanIndex]==false) {
					WomanIsProposed[WomanIndex]=true;
					ManProposedIndex[WomanIndex]=i;
					ManIsNotFree[i]=true;
					EngagedCounter++;
				}else{
					PreviousMan = ManProposedIndex[WomanIndex];

					if(ReverseIndex[WomanIndex][i]<ReverseIndex[WomanIndex][PreviousMan]) {
						PositionIteration[PreviousMan]+=1;
						ManIsNotFree[PreviousMan]=false;
						ManIsNotFree[i]=true;
						ManProposedIndex[WomanIndex]=i;
					}else {
						PositionIteration[i]+=1;//next round you go to next position
					}
				}
			}
			if(i==n-1) {
				i=0;
			}else {
				i++;
			}
		}

		for(i=0; i<n; i++) {
			MarriedCouple[ManProposedIndex[i]]=i;
		}
		for(i=0; i<n; i++) {
			pair=new MatchedPair(i,MarriedCouple[i]);
			MatchedPairsList.add(pair);
		}

		//YOUR CODE ENDS HERE

		output(Args[1]);

	}


	public static void main(String [] Args) // Strings in Args are the name of the input file followed by the name of the output file
	{
		new Framework(Args);

	}
}
