/** This program computes the maximum benefit by buying and selling one 
 * single stock from a sequence of daily prices. It is the most simple 
 * example of dynamic programming
 * Jaime Luengo Rozas
 * 28/02/2018 */

public class OneStock{
	
	private int[] s;		// known sequence of prices
	private int l;			// length of the sequence
	private int max; 		// maximum benefit
	private int[] opt; 		// pair of days  with max profit.
							// index 0 is bought and 1 is sold
	
	/** Constructor of Company stock prices */
	public OneStock(int[] prices, int l) {
		s = prices;
		this.l=l;
		opt = new int[2]; // length is 2 since there can be just 1 transaction
	}
	
	/** Gets the maximum profit and stores the days involving the transaction */
	public void solve() {
		int lo=s[0],hi=s[0],lop=0;
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
			if(max<hi-lo) {
				opt[0]=lop;
				opt[1]=i;
				max=hi-lo;
			}
		}
	}
	public static void main(String[] args) {
		/** They could be read from user as well */
		int[] p	= {8, 3, 6 ,50, 2,32,16, 40, 100,4,25,90,1};
		int l=p.length;
		
		OneStock JLuengoLLC = new OneStock(p,l);
		
		JLuengoLLC.solve();
		
		System.out.printf("THe maximum profit is %d, achieved by buying on day"
				+" %d at price $%d"+" and selling on day %d at price $%d",
				JLuengoLLC.max,JLuengoLLC.opt[0],
				JLuengoLLC.s[JLuengoLLC.opt[0]],
				JLuengoLLC.opt[1],JLuengoLLC.s[JLuengoLLC.opt[1]]);
	}

}
