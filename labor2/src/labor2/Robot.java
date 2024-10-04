package labor2;

public class Robot extends Jatekos{
	public static int cnt = 0;
	public int ID = 0;
	
	public Robot ()
	{
		ID = cnt++;
	}
	
	public void lep ()
	{
		System.out.println("Robot neve: " + toString());
		System.out.println("Kor: " + asztal.getKor());
	}
	
	public String toString()
	{
		return "Robot" + ID;
	}
}
