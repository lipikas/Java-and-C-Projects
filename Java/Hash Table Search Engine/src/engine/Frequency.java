package engine;

public class Frequency {

	String doc;

	int order;// alias for frequency

	//Initializes with document,occurrence pair.
	 
	public Frequency(String document, int freq) {
		doc = document;
		order = freq;
	}

	public String toString() {
		return "(" + doc + "," + frequency + ")";
	}
}
