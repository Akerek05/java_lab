package labor4;
import java.util.ArrayList;
import java.io.IOException;
import java.util.Scanner;
public class main {

	public static void main(String[] args) throws IOException {
		//Beer first = new Beer("Dreher","Ipa",5);
				//Beer second = new Beer("Soproni","Világos",4);
				//System.out.print(first.toString());
		//System.out.print(second.toString()); 
		 ArrayList<Beer> beers = new ArrayList<>();
	        
		Scanner scanner = new Scanner(System.in);
	        String input;
	        
	        do {
	           
	            
	        	System.out.print("Enter something (type 'exit' to quit): ");
	            input = scanner.nextLine();
	            if(input.equals("exit")){continue;}
	            String[] line = input.split(" ");
		 		String function =line[0];
		 		String name = line[1];
		 		String style = line[2];
		 		String strength =line[3];
		 		double number = Double.parseDouble(strength);
		 		Beer beer= new Beer(name,style,number);
		 		beers.add(beer);
		 		System.out.println("Size: "+beers.size());
		 		System.out.println("added beer: "+beer.toString());		 		
	        } while (!input.equalsIgnoreCase("exit"));

	        scanner.close();
		
		 
		 		System.exit(0);
	}	 		

}
