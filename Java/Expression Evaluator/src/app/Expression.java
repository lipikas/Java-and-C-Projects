package app;

import java.io.*;
import java.util.*;
import java.util.regex.*;

import structures.Stack;

public class Expression {

	public static String delims = " \t*+-/()[]";
			
    /**
     * Populates the vars list with simple variables, and arrays lists with arrays
     * in the expression. For every variable (simple or array), a SINGLE instance is created 
     * and stored, even if it appears more than once in the expression.
     * At this time, values for all variables and all array items are set to
     * zero - they will be loaded from a file in the loadVariableValues method.
     * 
     * @param expr The expression
     * @param vars The variables array list - already created by the caller
     * @param arrays The arrays array list - already created by the caller
     */
    
	public static void 
    makeVariableLists(String expr, ArrayList<Variable> vars, ArrayList<Array> arrays) {
    	/** COMPLETE THIS METHOD **/
    	/** DO NOT create new vars and arrays - they are already created before being sent in
    	 ** to this method - you just need to fill them in.
    	 **/
		String pali = "+-*/()]";
    	String temp = "";
    	for(int i = 0; i<expr.length();i++) { 
    		
    		if (Character.isDigit(expr.charAt(i))) {
    			continue;
    		}
    		if (Character.isWhitespace(expr.charAt(i))) {
    			continue;
    		}
    		if((delims.contains(Character.toString(expr.charAt(i))) != true)){ 
    			temp += expr.charAt(i);
    		}else if (pali.contains(Character.toString(expr.charAt(i)))){ 
    			if(temp != "") {
    				Variable tempp = new Variable(temp);
    				if(vars.contains(tempp) != true) {
		    			vars.add(tempp); //add to vars arraylist
    				}
		    	temp = "";
    			}
    		}else if (expr.charAt(i) == '[') {
    			if(temp != "") {
	    			Array tempp = new Array(temp);
	    			arrays.add(tempp); //add to vars arraylist
	    			temp = "";
    			}
    		}
    	}
    	Variable tempp = new Variable(temp); 
    	if((vars.contains(tempp) != true) && (delims.contains(temp)!=true)) {
    	vars.add(tempp); 
    	}
    	
    }
    
    /**
     * Loads values for variables and arrays in the expression
     * 
     * @param sc Scanner for values input
     * @throws IOException If there is a problem with the input 
     * @param vars The variables array list, previously populated by makeVariableLists
     * @param arrays The arrays array list - previously populated by makeVariableLists
     */
    public static void 
    loadVariableValues(Scanner sc, ArrayList<Variable> vars, ArrayList<Array> arrays) 
    throws IOException {
        while (sc.hasNextLine()) {
            StringTokenizer st = new StringTokenizer(sc.nextLine().trim());
            int numTokens = st.countTokens();
            String tok = st.nextToken();
            Variable var = new Variable(tok);
            Array arr = new Array(tok);
            int vari = vars.indexOf(var);
            int arri = arrays.indexOf(arr);
            if (vari == -1 && arri == -1) {
            	continue;
            }
            int num = Integer.parseInt(st.nextToken());
            if (numTokens == 2) { // scalar symbol
                vars.get(vari).value = num;
            } else { // array symbol
            	arr = arrays.get(arri);
            	arr.values = new int[num];
                // following are (index,val) pairs
                while (st.hasMoreTokens()) {
                    tok = st.nextToken();
                    StringTokenizer stt = new StringTokenizer(tok," (,)");
                    int index = Integer.parseInt(stt.nextToken());
                    int val = Integer.parseInt(stt.nextToken());
                    arr.values[index] = val;              
                }
            }
        }
    }
    
    /**
     * Evaluates the expression.
     * 
     * @param vars The variables array list, with values for all variables in the expression
     * @param arrays The arrays array list, with values for all array items
     * @return Result of evaluation
     */
    public static float evaluate(String expr, ArrayList<Variable> vars, ArrayList<Array> arrays) {
    	/** COMPLETE THIS METHOD **/
    	// following line just a placeholder for compilation
    
    	Stack<Float> nums = new Stack<Float>();
    	Stack<Character> oper = new Stack<Character>();
    	Stack<String> arr = new Stack<String>();
    	String pali= "+-*/";
    	expr = expr.replaceAll("\\s+", "");
    	char[] ch = expr.toCharArray();
    	String varName = "";
    	String parsedNum = "";
    	for (int i = 0; i < ch.length; i++) 
    	{
    		// Check for number
    		if (ch[i] >= '0' && ch[i] <= '9') 
    		{
    			parsedNum += ch[i];
    			if (i == ch.length-1) {
    				nums.push((float)Integer.parseInt(parsedNum));	
    			}
    		}
    		// Check for variable
    		else if ((ch[i] >= 'a' && ch[i] <= 'z') || (ch[i] >= 'A' && ch[i] <= 'Z')) 
    		{
    			varName += ch[i];
    		
    			if (i ==ch.length-1) {
    				for (int k = 0; k < vars.size(); k++) 
        			{
        				if (varName.equals(vars.get(k).name)) 
    					{
        					nums.push((float)vars.get(k).value);
        					varName = "";
        					break;
        				}
        			}
    			}
    			else if (i <= ch.length-1 && ch[i + 1] == '[') 
    			{
    				for (int k = 0; k < arrays.size(); k++) 
    				{
    					if (varName.equals(arrays.get(k).name)) 
    					{
    						arr.push(arrays.get(k).name);	
    					
    					}
    				}
    				varName = "";
    			}
    		}	
    		else if (pali.contains(Character.toString(ch[i]))) 
    			{
    				if(parsedNum != "")	{
    					nums.push((float)Integer.parseInt(parsedNum));
    					parsedNum = "";
    				
    				}
    				if (varName != "") {
    					for (int k = 0; k < vars.size(); k++) 
            			{
            				if (varName.equals(vars.get(k).name)) 
        					{
            					nums.push((float)vars.get(k).value);
            					break;
            				}
            			}
    					varName = "";
    				}
    				while (!oper.isEmpty() && Priority(ch[i], oper.peek())) 
        			{
        				nums.push(opt(oper.pop(), nums.pop(), nums.pop()));
        			}
        			
    				oper.push(ch[i]);
    			}	
    		
    		// Check for left parenthesis
    		else if (ch[i] == '(') 
    		{
    			oper.push(ch[i]);
    		}
    		
    		// Check for right parenthesis
    		else if (ch[i] == ')') 
    		{
    			if(parsedNum != "")	{
					nums.push((float)Integer.parseInt(parsedNum));
					parsedNum = "";
				}
				if (varName != "") {
					for (int k = 0; k < vars.size(); k++) 
        			{
        				if (varName.equals(vars.get(k).name)) 
    					{
        					nums.push((float)vars.get(k).value);
        					break;
        				}
        			}
					varName = "";
					
				}
    			while (oper.peek() != '(') 
    			{
    				nums.push(opt(oper.pop(), nums.pop(), nums.pop()));
    			}
    			oper.pop();
    		}
    		// Check for left bracket
    		else if (ch[i] == '[') 
    		{
    			oper.push(ch[i]);
    		}
    		
    		// Check for right bracket
    		else if (ch[i] == ']') 
    		{
    			if(parsedNum != "")	{
					nums.push((float)Integer.parseInt(parsedNum));
					parsedNum = "";
				}
				if (varName != "") {
					for (int k = 0; k < vars.size(); k++) 
        			{
        				if (varName.equals(vars.get(k).name)) 
    					{
        					nums.push((float)vars.get(k).value);
        					break;
        				}
        			}
					varName = "";
				}
    			
				while (oper.peek() != '[') 
    			{
    				nums.push(opt(oper.pop(), nums.pop(), nums.pop()));
    			}
    			
    			float temp = nums.pop();
    			
				for (int k = 0; k < arrays.size(); k++) 
				{
					if (arr.peek().equals(arrays.get(k).name)) 
					{
						nums.push((float)arrays.get(k).values[(int) temp]);
						arr.pop();
						break;
					}
				}
    			
				oper.pop();
    		}
    		
    		// Check for operator
    		
    	}    	
    	while (!oper.isEmpty())
    	{
    		nums.push(opt(oper.pop(), nums.pop(), nums.pop()));
    	}
    	
    	return nums.pop();
    }
    private static boolean Priority(char curr, char top) 
    {
    	if (top == '(' || top == ')' || top == '[' || top == ']')
    	{
    		return false;
    	}
    	if ((curr == '*' || curr == '/') && (top == '+' || top == '-')) 
    	{
    		return false;
    	}
    	else return true;
    }
    private static float opt(char op, float num2, float num1) 
    {
    	switch(op) 
    	{
    	case '+':
    		return num1 + num2;
    	case '-': 
    		return num1 - num2;
    	case '*':
    		return num1 * num2;
    	case '/':
    		return num1/num2;
    	}
    	return 0;
    }
}
