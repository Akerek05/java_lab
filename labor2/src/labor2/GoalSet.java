package labor2;

import java.util.*;

public class GoalSet {
	public double main (int a, int b)
	{
		Random rand = new Random();
		
	    int x = a + (int) (rand.nextDouble() * b);
	
	    return x;
	}
}
