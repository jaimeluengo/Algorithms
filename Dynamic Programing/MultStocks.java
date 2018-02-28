import java.util.*;

/** This program computes the maximum benefit by buying and selling one 
 * single stock from a sequence of daily prices. It is the most simple 
 * example of dynamic programming
 * Jaime Luengo Rozas
 * 28/02/2018 */

public class OneStock{
	
	private int[] s;		// known sequence of prices
	private int l;			// length of the sequence
	private int max; 		// maximum benefit
	private int[] opt1; 		// pair of days  with max profit.
								// index 0 is bought and 1 is sold
	
	/** Constructor of Company stock prices */
	public OneStock(int[] prices, int l) {
		s = prices;
		this.l=l;
		opt1 = new int[2]; // length is 2 since there can be just 1 transaction
	}
	
	/** Gets the maximum profit and stores the days involving the transaction
	 * including the fee that is paid per transaction f */
	public void solve1(int f) {
		int lo=s[0],hi=s[0],lop=0;
		max=0;//intialize max profit to 0
		
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
				opt1[0]=lop;
				opt1[1]=i;
				max=hi-lo-f;
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
	public int solveMult(int f) {
		//Create Dynamic Programming 2D table
		int[][] opt= new int[2][l];
		LinkedList<Integer> opts0,opts1;
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
		
		return opt[0][0];
		
		
	}
	
	public static void main(String[] args) {
		/** They could be read from user as well */
		int[] p	= {8, 3, 6 ,50, 2,32,16, 40, 100,4,25,90,1};
		int l=p.length;
		int f=3; //fee per transaction
		
		OneStock JLuengoLLC = new OneStock(p,l);
		
		JLuengoLLC.solve1(f);
		
		System.out.printf("THe maximum profit is %d, achieved by buying on day"
				+" %d at price $%d"+" and selling on day %d at price $%d. \n",
				JLuengoLLC.max,JLuengoLLC.opt1[0],
				JLuengoLLC.s[JLuengoLLC.opt1[0]],
				JLuengoLLC.opt1[1],JLuengoLLC.s[JLuengoLLC.opt1[1]]);
		
		System.out.printf("Maximum benefit for the multiple stocks problem is"
				+ " %d",JLuengoLLC.solveMult(f));
	}
	

}
