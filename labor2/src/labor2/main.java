package labor2;

public class main {

	public static void main(String[] args) {
		{
			Asztal asztal = new Asztal();
			
			asztal.addJatekos(new Kezdo("Adam"));
			asztal.addJatekos(new Kezdo("Pista"));
			asztal.addJatekos(new Robot());
			
			try
			{
				asztal.ujJatek();
			} 	catch (Exception e1)
				{
					System.out.println("Hiba1: " + e1);
				}
				
			
			try
			{
				while (asztal.getKor() != -1)
					asztal.kor();
			} 	catch (Exception e2)
				{
					System.out.println("Hiba2: " + e2);
				}		
		}

	}

}
