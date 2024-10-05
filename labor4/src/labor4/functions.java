package labor4;
import java.io.*;
import java.util.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Collections;

public class functions {
	protected static void add (String[] line,ArrayList<Beer> beers) {
		 if (line.length < 4) {
	            System.out.println("Not enough arguments. Usage: add <name> <style> <strength>");
	            return;
	        }
		String name = line[1];
		String style = line[2];
		String strength =line[3];
		double number = Double.parseDouble(strength);
		Beer beer= new Beer(name,style,number);
		beers.add(beer);
		System.out.println("Size: "+beers.size());
		System.out.println("added beer: "+beer.toString());
	}
	protected static void list(ArrayList<Beer> beers, String sortBy) {
	    switch (sortBy.toLowerCase()) {
	        case "name":
	            Collections.sort(beers, new NameComparator());
	            break;
	        case "style":
	            Collections.sort(beers, new StyleComparator());
	            break;
	        case "strength":
	            Collections.sort(beers, new StrengthComparator());
	            break;
	        default:
	            System.out.println("Unknown sorting criterion. Available options: name, style, strength.");
	            return;
	    }

	    // Print the sorted list
	    for (Beer beer : beers) {
	        System.out.print(beer.toString());
	    }
	}
	protected static void readFromFile(String filename, ArrayList<Beer> beers) {
        // Clear the existing beers list
        beers.clear();

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" ");
                if (parts.length != 3) {
                    System.out.println("Invalid line format: " + line);
                    continue;
                }

                try {
                    String name = parts[0];
                    String style = parts[1];
                    double strength = Double.parseDouble(parts[2]);
                    Beer beer = new Beer(name, style, strength);
                    beers.add(beer);
                    System.out.println("Added beer from file: " + beer.toString());
                } catch (NumberFormatException e) {
                    System.out.println("Invalid strength value in line: " + line);
                }
            }
            System.out.println("Total beers read from file: " + beers.size());
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
     }
	protected static void writeToFile(String filename, ArrayList<Beer> beers) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            for (Beer beer : beers) {
                bw.write(beer.getName() + " " + beer.getStyle() + " " + beer.getStrength());
                bw.newLine();
            }
            System.out.println("Successfully wrote " + beers.size() + " beers to " + filename);
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }
	

	public static class NameComparator implements Comparator<Beer> {
	    @Override
	    public int compare(Beer b1, Beer b2) {
	        return b1.getName().compareTo(b2.getName());
	    }
	}
	public static class StyleComparator implements Comparator<Beer> {
	    @Override
	    public int compare(Beer b1, Beer b2) {
	        return b1.getStyle().compareTo(b2.getStyle());
	    }
	}	
	public static class StrengthComparator implements Comparator<Beer> {
	    @Override
	    public int compare(Beer b1, Beer b2) {
	        return Double.compare(b1.getStrength(), b2.getStrength());
	    }
	}
	protected static void find(ArrayList<Beer> beers, String substring) {
        boolean found = false;
        for (Beer beer : beers) {
            if (beer.getName().toLowerCase().contains(substring.toLowerCase())) {
                System.out.print(beer.toString());
                found = true;
            }
        }
        if (!found) {
            System.out.println("No beers found containing: " + substring);
        }
    }
	protected static void search(ArrayList<Beer> beers, String name) {
        boolean found = false;
        for (Beer beer : beers) {
            if (beer.getName().equals(name)) {
                System.out.print(beer.toString());
                found = true;
            }
        }
        if (!found) {
            System.out.println("No beers found with the name: " + name);
        }
    }
	protected static void delete(ArrayList<Beer> beers, String name) {
        Iterator<Beer> iterator = beers.iterator();
        boolean found = false;

        while (iterator.hasNext()) {
            Beer beer = iterator.next();
            if (beer.getName().equals(name)) {
                iterator.remove();
                System.out.println("Deleted beer: " + beer.toString());
                found = true;
            }
        }
        if (!found) {
            System.out.println("No beers found with the name: " + name);
        }
    }
}

