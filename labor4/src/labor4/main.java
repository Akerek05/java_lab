package labor4;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.io.IOException;
import java.util.Scanner;

public class main extends functions {

	
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
	             if (input.equals("exit")) {
	                 continue;
	             }
	             String[] line = input.split(" ");

	             if (line[0].equals("add")) {
	                 add(line, beers);
	             } else if (line[0].equals("read")) {
	                 if (line.length != 2) {
	                     System.out.println("Usage: read <filename>");
	                 } else {
	                     readFromFile(line[1], beers);
	                 }
	             } else if (line[0].equals("write")) {
	                 if (line.length != 2) {
	                     System.out.println("Usage: write <filename>");
	                 } else {
	                     writeToFile(line[1], beers);
	                 }
	             } else if (line[0].equals("list")) {
	                 if (line.length != 2) {
	                     System.out.println("Usage: list <sortBy>");
	                 } else {
	                     list(beers, line[1]);
	                 }
	             } else if (line[0].equals("search")) {
	                 if (line.length != 2) {
	                     System.out.println("Usage: search <name>");
	                 } else {
	                     search(beers, line[1]);
	                 }
	             } else if (line[0].equals("find")) {
	                 if (line.length != 2) {
	                     System.out.println("Usage: find <substring>");
	                 } else {
	                     find(beers, line[1]);
	                 }
	             } else if (line[0].equals("delete")) {
	                 if (line.length != 2) {
	                     System.out.println("Usage: delete <name>");
	                 } else {
	                     delete(beers, line[1]);
	                 }
	             } else {
	                 System.out.println("Unknown command. Please use 'add', 'read', 'write', 'list', 'search', 'find', or 'delete'.");
	             }

	         } while (!input.equalsIgnoreCase("exit"));

	        scanner.close();
		
		 
		 		System.exit(0);
	}
	
	 
	

}
