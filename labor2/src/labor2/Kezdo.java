package labor2;

public class Kezdo extends Jatekos{
String nev;
	
	public Kezdo(String n) 
	{
		nev = n;
	}
	
	public void lep ()
	{
		int k = asztal.getKor();
		
		System.out.println("Jatekos neve: " + toString());
		System.out.println("Kor: " + k);
		
		if (k % 2 == 0)
			asztal.emel(1.0);
		else
			asztal.emel(0.0);
	}
	
	public String toString()
	{
		return nev;
	}
}
