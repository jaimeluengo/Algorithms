import java.util.*;

/** This program computes the maximum benefit in two given methods.
 * First one just by one single transaction, and the other from multiple,
 * from a sequence of daily prices. It exemplifies dynamic programming
 * and backtracking
 * Jaime Luengo Rozas
 * 28/02/2018 */

public class Stock{
	
	private int[] s;		// known sequence of prices
	private int l;			// length of the sequence
	private int max; 		// maximum benefit
	LinkedList<int[]> opts; //sequence of optimal transactions
	
	/** Constructor of Company stock prices */
	public Stock(int[] prices, int l) {
		s = prices;
		this.l=l;  // calling l from the fields
		opts = new LinkedList<int[]>();
	}
	
	/** Gets the maximum profit and stores the days involving the transaction
	 * including the fee that is paid per transaction f */
	public void solve1(int f) {
		int lo=s[0],hi=s[0],lop=0;
		max=0;//initialize max profit to 0
		
		for(int i=1;i<l;i++) {
			//If  a new position is reached with a lower value than lo, update
			//it and move the pointer to a new position
			if(lo>s[i] && i<l-1) {
				lo=s[i];
				lop=i;
				i++;
			}
			hi=s[i];
			//If a new higher profit is found, uptade the maximum profit
			if(max<hi-lo-f) {
				max=hi-lo-f;
			}
		}
		
		/** Recovery of pair
		 * Note that this is quite unnecessary but done for learning */
		lo=s[0];
		lop=0;
		for(int i=1;i<l;i++) {
			if(lo>s[i] && i<l-1) {
				lo=s[i];
				lop=i;
				i++;
			}
			hi=s[i];
			if(hi-lo-f==max) {
				int[] opt1= {lop,s[lop]};
				opts.addFirst(opt1);
				int[] opt2= {i,s[i]};
				opts.add(opt2);
			}
		}
	}
	
	/** solveMult returns the maximum profit by doing transactions in which the
	 * maximum number of stocks hold at a time is one, and there is a fee per
	 * transaction.
	 * It is solved using a 2D Dynamic Programming table were the row with index
	 * 0 simbolizes the choice of not holding stock and 1 is the opposite. It
	 * traverses the sequence from the end to the beginning since you can only
	 * sell after buying. 
	 */
	public void solveMult(int f) {
		//Create Dynamic Programming 2D table
		int[][] opt= new int[2][l];
		boolean hold = false;
		//Initialize the table with the base cases
		opt[0][l-1]=0;
		opt[1][l-1]=s[l-1]-f; //If you are holding a stock during the last day
							  //you must sell it
		
		/** The logic here is:
		 * If you don't hold an stock on day i, the two options you have is to 
		 * buy one and and hold one on day i++ or do not hold one
		 * the next day.
		 * If you hold an stock on day i, the two options you have is to 
		 * sell it and do not hold one on day i++ or do not sell it and hold one
		 * the next day.
		 * Finally you will get the value of opt[0][0] since on the first day
		 * you starting without holding any stock
		 */
		for (int i=l-2; i>-1;i--) {
			opt[0][i]=Math.max(opt[1][i+1]-s[i], opt[0][i+1]);
			opt[1][i]=Math.max(opt[0][i+1]+s[i]-f, opt[1][i+1]);
		}
		
		/** Backtrack through the table to get the sequence applying
		 * the same logic as before
		 */
		for(int i=0; i<l-1; i++) {
			if(hold==false) {
				if(opt[1][i+1]-s[i]>opt[0][i+1]) {
					int[] pair = {i,s[i]};
					opts.add(pair);
					hold=true;
				}
			}else {
				if(opt[0][i+1]+s[i]-f>opt[1][i+1]) {
					int[] pair = {i,s[i]};
					opts.add(pair);
					hold=false;
				}
			}
		}
		
		//If we hold an stock for the last day we must sell it
		if(hold) {
			int[] pair = {l-1,s[l-1]};
			opts.add(pair);
		}
		
		max = opt[0][0];//maximum profit is stored in the last element	
	}
	
	public static void main(String[] args) {
		/** They could be read from user as well */
		int[] p	= {8, 3, 6 ,50, 2,32,16, 40, 100,4,25,90,1,5,2,54,32,34,23,123,2,2000};
		int l=p.length;
		int f=15; //fee per transaction
		
		Stock JLuengoLLC = new Stock(p,l);
		
		JLuengoLLC.solve1(f);
		
		System.out.printf("THe maximum profit is $%d, achieved by buying on day"
				+" %d at price $%d"+" and selling on day %d at price $%d. \n\n",
				JLuengoLLC.max,JLuengoLLC.opts.get(0)[0],
				JLuengoLLC.opts.get(0)[1],
				JLuengoLLC.opts.get(1)[0],JLuengoLLC.opts.get(1)[1]);
		
		JLuengoLLC = new Stock(p,l);
		
		JLuengoLLC.solveMult(f);

		System.out.printf("You could earn $%d! follow the DP scheme below:\n",
				JLuengoLLC.max);
		System.out.println("\tDay\tBuy\tDay\tSell");
		
		for(int i=0;i<JLuengoLLC.opts.size();i+=2) {
			System.out.printf("\t%d\t$%d\t%d\t$%d \n",
					JLuengoLLC.opts.get(i)[0],JLuengoLLC.opts.get(i)[1],
					JLuengoLLC.opts.get(i+1)[0],JLuengoLLC.opts.get(i+1)[1]);
		}
		
	}
	
}
