/**
 * Student: Truc Ngo
 * Implement Huffman Tree using
 * Stack()
 * PriorityQueue
 * Hashmap()
 */
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Stack;

public class Hauffman {
		
		public static class Node implements Comparable<Node> {
			int id;
			Character ch;
			int frequency;
		    Node left;
		    Node right;
		    Node parent;
		   
		    //constructor
		    public Node(int an_id, Character charac, int freq) {
		    	id = an_id;
		        ch = charac;
		        frequency = freq;
		    }
		    
		    //constructor
		    public Node(int an_id, int frequencyIn) {
		    	id = an_id;
		    	ch = ' ';
		        frequency = frequencyIn;
		    }
		    
			@Override
			public int compareTo(Node n) {
				 return (this.frequency - n.frequency);
			}
		}
	
		private static Node rootNode;
		private static String textString;
		private static String codeString;
		private static boolean showSteps = false;
		private static final char replacementCharForSpace = '$';
		private static HashMap<Character, Integer> hm;

		public Hauffman(String text, boolean show, String fileName) {
			showSteps = show;
			textString = text;
			hm = buildHashMap(text);
			rootNode = buildHauffman(text, show, fileName);
		}
	
		// Reference: page 261, textBook	
		// build hashmap of the given text {key:value}
		private static HashMap<Character, Integer> buildHashMap(String text) {
			hm = new HashMap<Character, Integer>();
			text=text.replace(' ', replacementCharForSpace);
			for (int i = 0;i<text.length();i++)
			{
				char ch = text.charAt(i);
				boolean x = hm.containsKey(ch);
				if (x) {
					int v = hm.get(ch);
					v++;
					hm.put(ch, v);
				} else {
					hm.put(ch, 1);
				}
			}
			if (showSteps) {
				for (char item : hm.keySet()) {
					if (item != replacementCharForSpace) {
						System.out.println("\"" + item + "\" has frequency of: " + hm.get(item));
					} else {
						System.out.println("\"" + " " + "\" has frequency of: " + hm.get(item));
					}
				}
			}
			return hm;
		}
	
			
		//build Huffman tree
		private static Node buildHauffman(String text, boolean show, String fileName) {
			Node root = null;
			Stack<String> dotFileDataStack = null;
			try {
				FileWriter o = new FileWriter(fileName);
				o.write("## dot -Tpdf " + fileName + " -o " + fileName + ".pdf\n");
				o.write("digraph g {\n");
				String label = " label = \"" + text + "\"";
				o.write(label);
				o.write("\n");
				
				//sort the hashMap using Comparable interface; 
				//the smallest value will be at the front/top of the queue
				//using MIN HEAP
				PriorityQueue<Node> queue = new PriorityQueue<Node>();
				int id = 0;
				for (char item : hm.keySet()) {
					id++;
					Node n = new Node(id, item, hm.get(item));
					queue.add(n);
				}
				//build the Huffman tree
				//Node root = null;
			    //if there is only one character in String
			    if (queue.size() == 1) {
			    	root = new Node(9999, text.charAt(0), 1);
			    }
			    dotFileDataStack = new Stack<String>();
			    while (queue.size() > 1)  {
			        Node smallestNode1 = queue.poll();
			        Node smallestNode2 = queue.poll();
			        id++;
			        Node combinedNode = new Node(id, smallestNode1.frequency + smallestNode2.frequency);
			        combinedNode.left = smallestNode1;
			        combinedNode.right = smallestNode2;
			        smallestNode1.parent = combinedNode;
			        smallestNode2.parent = combinedNode;
			        
			        String dataStr = null;
			        /* "15\n30" ->"2\n11\na" [color=red] */
			        if (smallestNode1.ch == ' ') {
			        	dataStr = " \"" + combinedNode.id + "\\n" + combinedNode.frequency + "\" ->\"" + 
				        		smallestNode1.id + "\\n" + smallestNode1.frequency + "\"" +
				        		" [color=red]" + "\n";
			        	dotFileDataStack.push(dataStr);
			        } else {
			        	//replace $ with 'blank" as for space
			        	String blankStr;
			        	if (smallestNode1.ch == '$') {
			        		blankStr = "blank";
			        	} else {
			        		blankStr = String.valueOf(smallestNode1.ch);
			        	}
			        	dataStr = " \"" + combinedNode.id + "\\n" + combinedNode.frequency + "\" ->\"" + 
			        			smallestNode1.id + "\\n" + smallestNode1.frequency + "\\n" + blankStr + "\"" +
				        		" [color=red]" + "\n";
			        	dotFileDataStack.push(dataStr);
				    }
			        
			        /*  "15\n30" ->"14\n19" [color=blue] */
			        if (smallestNode2.ch == ' ') {
			        	dataStr = " \"" + combinedNode.id + "\\n" + combinedNode.frequency + "\" ->\"" + 
			        			 smallestNode2.id + "\\n" + smallestNode2.frequency +  "\"" +
					        		" [color=blue]" + "\n";
			        	dotFileDataStack.push(dataStr);
			        } else {
			        	//replace $ with 'blank" as for space
			        	String blankStr;
			        	if (smallestNode2.ch == '$') {
			        		blankStr = "blank";
			        	} else {
			        		blankStr = String.valueOf(smallestNode2.ch);
			        	}
			        	dataStr = " \"" + combinedNode.id + "\\n" + combinedNode.frequency + "\" ->\"" + 
			        			smallestNode2.id + "\\n" + smallestNode2.frequency + "\\n" + blankStr + "\"" +
				        		" [color=blue]" + "\n";
			        	dotFileDataStack.push(dataStr);
			        }
			        //add new node to the queue
			        queue.add(combinedNode);
			        root = combinedNode;
			    }
			    
			    //pop data from the stack and write to file
			    while (!dotFileDataStack.empty()) {
			    	String temp = dotFileDataStack.pop();
			    	o.write(temp);
			    }
			    o.write("}\n");
			    //save & close file
			    o.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		    return root;
		}
	
		// Convert (decode) a text string to Huffman codes
		public String decode() {
			textString = textString.replace(' ', replacementCharForSpace);
			String allCodes = "";
			String [] codeArray = new String[256]; //ASCII 8-bit
			//if there is only one character in the given text
			if (rootNode.id == 9999) {
				preOrderTraverse(codeArray, rootNode, Character.toString('0'));
			} else {
				preOrderTraverse(codeArray, rootNode, "");
			}
			
			for (int i = 0; i < textString.length(); i++ ) {
				for (char item : hm.keySet()) {
					if (item == textString.charAt(i)) {
						allCodes = allCodes + codeArray[item];
					}
				}
	        }
			if (showSteps) {
				System.out.print("Huffman Codes for the given text is:" + allCodes);
			}
			codeString = allCodes;
			return allCodes;
		}
	
		//reverse a Huffman code back to the given String
		public String encode() {
			String str = "";
			for (int i = 0; i < codeString.length();) {
				Node node = rootNode;
				//Handle the case where there is only one character in the given String
				if (isLeaf(node)) {
					i += 1;
				}
				while (!isLeaf(node)) {
					char bit = codeString.charAt(i);
					if (bit == '0') {
						node = node.left;
					} else {
						node = node.right;
					}
					i++;
				}
				str = str + node.ch;
			}
			str = str.replace(replacementCharForSpace, ' ');
			if (showSteps) {
				System.out.println();
				System.out.println("The original text is:" + str);
			}
			return str;
		}
	
	
		//Traverse the tree and store Huffman code in array of String
		//Visit->Left->Right
		private static void preOrderTraverse(String [] str, Node n, String s) {
			if (!isLeaf(n)) {
				preOrderTraverse(str, n.left, s + '0');
				preOrderTraverse(str, n.right, s + '1');
			} else {
				str[n.ch] = s;
			}
			if ((n.ch != ' ') && (showSteps) && (n.ch != replacementCharForSpace)) {
				System.out.println("Code for char: \"" + n.ch + "\" is:" + s);
			} else if ((n.ch != ' ') && (showSteps) && (n.ch == replacementCharForSpace)) {
				System.out.println("Code for char: \"" + " " + "\" is:" + s);
			}
		}
		
		//Is the current node is the leaf node?
		private static boolean isLeaf(Node n) {
			return (n.left == null) && (n.right == null);
		}
	/*
	public static void main(String[] args) {
		String text = "Long years ago we made a tryst with destiny, and now the time comes when we shall redeem our pledge, not wholly or in full measure, but very substantially.At the stroke of the midnight hour, when the world sleeps, India will awake to life and freedom. A moment comes, which comes but rarely in history, when we step out from the old to the new, when an age ends, and when the soul of a nation, long suppressed, finds utterance.";
		Hauffman hf = new Hauffman(text, true);
		hf.decode();
		hf.encode();
	}
	*/
}
