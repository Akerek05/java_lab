package labor4;

public class Beer {
	private String name;
	private String style;
	private double strength;
	Beer(String name, String style, double strength){
	this.name=name;
	this.style=style;
	this.strength=strength;
	}
	String getName() {
	return name;
	}
	String getStyle() {
		return style;
	}
	double getStrength() {
		return strength;
	}
	public String toString() {
		return ("Name:")+name + (" Style:")+style + (" Strength:")+strength+"\n";
	}
	
}


