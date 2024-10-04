package labor2;

public class Asztal {
	private int kor;
	private double tet;
	private double goal;
	private Jatekos[] jatekosok = new Jatekos[0];
	protected GoalSet gs = new GoalSet();
	public double getTet() 
	{
		return tet;
	}
	public int getKor() 
	{
		return kor;
	}
	public void emel(double d) 
	{
		tet = tet + d;
	}
	public void addJatekos(Jatekos j) 
	{
		if (jatekosok.length < 10 - 1) 
		{
			Jatekos[] t = new Jatekos[jatekosok.length + 1];
			for (int i = 0; i < jatekosok.length; i++)
				t[i] = jatekosok[i];
			t[jatekosok.length] = j;
			jatekosok = t;
			j.asztal = this;
		}
		
		if (jatekosok.length >= 10)
			System.out.println("Ennel az asztalnal megteltek a helyek.");
	}
	public void ujJatek() 
	{
		tet = 0;
		kor = 0;
		goal = gs.main(0, 100);
		System.out.println(goal);
	}
	public void kor() throws NincsJatekos 
	{
		if (jatekosok.length == 0)
			throw new NincsJatekos("Az asztalnal nem ul senki.");
		
		if (kor == -1)
		{
			System.out.println("Vege a jateknak.");
			return;
		}
			
		kor++;
		
		for (int i = 0; i < jatekosok.length; i++) 
		{
			jatekosok[i].lep();
		
		
			if (tet >= (goal*1.1))
			{
				Jatekos nyero = jatekosok[i];
				System.out.println("A gyoztes jatekos " + nyero);
				kor = -1;
				return;
			}
		}	
		System.out.println("Aktualis tet: " + tet);
		
		
	}
}
