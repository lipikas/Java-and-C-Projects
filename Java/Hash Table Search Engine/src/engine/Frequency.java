package engine;

public class Frequency {

	String doc;

	int occurrence;

	//Initializes with document,occurrence pair.
	 
	public Frequency(String document, int freq) {
		doc = document;
		occurrence = freq;
	}

	public String toString() {
		return "(" + doc + "," + frequency + ")";
	}
}
