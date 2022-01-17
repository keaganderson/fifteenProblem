package temp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;
import java.util.Map;
public final class FifteenProblem {

	static List<Character> goal1;
	static List<Character> goal2;
	static List<List<Character>> dupeList;
	static int totalNodes;
	static int totalExpanded;
	static int maxFringe;
	
	public FifteenProblem() {
		dupeList = new ArrayList<List<Character>>();
		totalNodes = 0;
		totalExpanded = 0;
		maxFringe = 0;
		goal1 = new ArrayList<Character>();
		goal1.add('1');
		goal1.add('2');
		goal1.add('3');
		goal1.add('4');
		goal1.add('5');
		goal1.add('6');
		goal1.add('7');
		goal1.add('8');
		goal1.add('9');
		goal1.add('A');
		goal1.add('B');
		goal1.add('C');
		goal1.add('D');
		goal1.add('E');
		goal1.add('F');
		goal1.add(' ');
		goal2 = new ArrayList<Character>();
		goal2.add('1');
		goal2.add('2');
		goal2.add('3');
		goal2.add('4');
		goal2.add('5');
		goal2.add('6');
		goal2.add('7');
		goal2.add('8');
		goal2.add('9');
		goal2.add('A');
		goal2.add('B');
		goal2.add('C');
		goal2.add('D');
		goal2.add('F');
		goal2.add('E');
		goal2.add(' ');
	}
	
    public static void main(final String[] theArgs) {
    	FifteenProblem temp = new FifteenProblem();
    	String input = theArgs[0];
    	if (input == null) {
    		throw new IllegalArgumentException();
    	}
        String searchType = theArgs[1];
        if (searchType == null) {
        	throw new IllegalArgumentException();
        }
        if (searchType.equals("BFS")) {
        	bfs(input);
        } else if (searchType.equals("DFS")) {
        	dfs(input);
        } else if (searchType.equals("GBFS")) {
        	String heuristic = theArgs[2];
        	if (!(heuristic.equals("h1") || heuristic.equals("h2"))) {
        		throw new IllegalArgumentException();
        	}
        	greedy(input, heuristic);
        } else if (searchType.equals("AStar")) {
        	String heuristic = theArgs[2];
        	if (!(heuristic.equals("h1") || heuristic.equals("h2"))) {
        		throw new IllegalArgumentException();
        	}
        	AStar(input, heuristic);
        } else if (searchType.equals("DLS")) {
        	if (theArgs[2] == null) {
        		throw new IllegalArgumentException();
        	}
        	int limit = Integer.parseInt(theArgs[2]);
        	dls(input, limit);
        }
    }
    
    // Implements breadth first search algorithm. Give start puzzle
    public static void bfs(String input) {
    	int currDepth = 0;
    	List<Character> current = new ArrayList<>();
    	for (int i = 0; i < input.length(); i++) {
            current.add(input.charAt(i));
    	}
    	Queue<List<Object>> neighbors = new LinkedList<List<Object>>();
    	for (List<Object> option : findNeighbors(current, currDepth)) {
    		neighbors.add(option);
    	}
    	Boolean check = false;
    	Boolean outOfOptions = false;
    	while (!check) {
    		if (neighbors.size() > maxFringe) {
    			maxFringe = neighbors.size();
    		}
    		List<Object> temp = neighbors.remove();
    		current = (List<Character>) temp.get(0);
    		currDepth = (int) temp.get(1);
    		totalExpanded++;
    		if (current.equals(goal1) || current.equals(goal2)) {
    			check = true;
    		} else {
    			for (List<Object> option : findNeighbors(current, currDepth)) {
    	    		neighbors.add(option);
    	    	}
    			if (neighbors.size() == 0) {
					check = true;
					outOfOptions = true;
				}
			}
    	}
    	if (outOfOptions) {
    		currDepth = -1;
    		totalNodes = 0;
    		totalExpanded = 0;
    		maxFringe = 0;
    	}
    	System.out.println(currDepth + ", " + totalNodes + ", " + totalExpanded + ", " + maxFringe);
    }
    
    // Implements depth first search algorithm. Give start puzzle
    public static void dfs(String input) {
    	int currDepth = 0;
    	List<Character> current = new ArrayList<>();
    	for (int i = 0; i < input.length(); i++) {
            current.add(input.charAt(i));
    	}
    	Stack<List<Object>> neighbors = new Stack<>();
    	List<List<Object>> tempAdd = findNeighbors(current,currDepth);
    	for (int i = tempAdd.size() - 1; i > 0; i--) {
    		List<Object> option = tempAdd.get(i);
    		neighbors.add(option);
    	}
    	Boolean check = false;
    	Boolean outOfOptions = false;
    	while (!check) {
    		if (neighbors.size() > maxFringe) {
    			maxFringe = neighbors.size();
    		}
    		List<Object> temp = neighbors.pop();
			current = (List<Character>) temp.get(0);
			currDepth = (int) temp.get(1);
			totalExpanded++;
			if (current.equals(goal1) || current.equals(goal2)) {
				check = true;
			} else {
				tempAdd = findNeighbors(current,currDepth);
				for (int i = tempAdd.size() - 1; i >= 0; i--) {
		    		List<Object> option = tempAdd.get(i);
		    		neighbors.add(option);
		    	}
				if (neighbors.size() == 0) {
					//check = true;
					outOfOptions = true;
				}
			}
    	}
    	if (outOfOptions) {
    		currDepth = -1;
    		totalNodes = 0;
    		totalExpanded = 0;
    		maxFringe = 0;
    	}
    	System.out.println(currDepth + ", " + totalNodes + ", " + totalExpanded + ", " + maxFringe);
    }
    
    // Implements depth limited search algorithm. Give start puzzle and a number for the limit
    public static void dls(String input, int limit) {
    	int currDepth = 0;
    	List<Character> current = new ArrayList<>();
    	for (int i = 0; i < input.length(); i++) {
            current.add(input.charAt(i));
    	}
    	Stack<List<Object>> neighbors = new Stack<>();
    	List<List<Object>> tempAdd = findNeighbors(current,currDepth);
    	for (int i = tempAdd.size() - 1; i > 0; i--) {
    		List<Object> option = tempAdd.get(i);
    		if ((int) option.get(1) <= limit) {
    			neighbors.add(option);
    		}
    	}
    	Boolean check = false;
    	Boolean outOfOptions = false;
    	while (!check) {
    		if (neighbors.size() > maxFringe) {
    			maxFringe = neighbors.size();
    		}
    		List<Object> temp = neighbors.pop();
			current = (List<Character>) temp.get(0);
			currDepth = (int) temp.get(1);
			totalExpanded++;
			if (current.equals(goal1) || current.equals(goal2)) {
				check = true;
			} else {
				tempAdd = findNeighbors(current,currDepth);
				for (int i = tempAdd.size() - 1; i >= 0; i--) {
		    		List<Object> option = tempAdd.get(i);
		    		if ((int) option.get(1) <= limit) {
		    			neighbors.add(option);
		    		}
		    	}
				if (neighbors.size() == 0) {
					check = true;
					outOfOptions = true;
				}
			}
    	}
    	if (outOfOptions) {
    		currDepth = -1;
    		totalNodes = 0;
    		totalExpanded = 0;
    		maxFringe = 0;
    	}
    	System.out.println(currDepth + ", " + totalNodes + ", " + totalExpanded + ", " + maxFringe);
    }
    
    // Implements greedy search algorithm. Give start puzzle and either h1 or h2
    public static void greedy(String input, String heuristic) {
    	int currDepth = 0;
    	List<Character> current = new ArrayList<>();
    	for (int i = 0; i < input.length(); i++) {
            current.add(input.charAt(i));
    	}
    	Map<List<Object>, Integer> neighbors = new HashMap<List<Object>, Integer>();
    	for (List<Object> option : findNeighbors(current, currDepth)) {
    		List<Character> temp = (List<Character>) option.get(0);
    		if (heuristic.equals("h1")) {
    			neighbors.put(option, findHeuristic(temp));
    		} else {
    			neighbors.put(option, findHeuristic2(temp));
    		}
    	}
    	Boolean check = false;
    	Boolean outOfOptions = false;
    	while (!check) {
    		if (neighbors.size() > maxFringe) {
    			maxFringe = neighbors.size();
    		}
			Integer min = Collections.min(neighbors.values());
			List<Object> next = null;
			int temp = 0;
			for (List<Object> key : neighbors.keySet()) {
				if (neighbors.get(key) == min) {
					next = key;
					temp = neighbors.get(key);
				}
			}
			neighbors.remove(next);
			current = (List<Character>) next.get(0);
			currDepth = (int) next.get(1);
			totalExpanded++;
			if (current.equals(goal1) || current.equals(goal2)) {
				check = true;
			} else {
				for (List<Object> option : findNeighbors(current, currDepth)) {
		    		List<Character> pattern = (List<Character>) option.get(0);
		    		if (heuristic.equals("h1")) {
		    			neighbors.put(option, findHeuristic(pattern));
		    		} else {
		    			neighbors.put(option, findHeuristic2(pattern));
		    		}
		    	}
				if (neighbors.size() == 0) {
					check = true;
					outOfOptions = true;
				}
			}
    	}
    	if (outOfOptions) {
    		currDepth = -1;
    		totalNodes = 0;
    		totalExpanded = 0;
    		maxFringe = 0;
    	}
    	System.out.println(currDepth + ", " + totalNodes + ", " + totalExpanded + ", " + maxFringe);
    }
    
    // Implements AStar search algorithm. Give start puzzle and either h1 or h2
    public static void AStar(String input, String heuristic) {
    	int currDepth = 0;
    	List<Character> current = new ArrayList<>();
    	for (int i = 0; i < input.length(); i++) {
            current.add(input.charAt(i));
    	}
    	Map<List<Object>, Integer> neighbors = new HashMap<List<Object>, Integer>();
    	for (List<Object> option : findNeighbors(current, currDepth)) {
    		List<Character> temp = (List<Character>) option.get(0);
    		if (heuristic.equals("h1")) {
    			neighbors.put(option, findHeuristic(temp) + temp.get(1));
    		} else {
    			neighbors.put(option, findHeuristic2(temp) + temp.get(1));
    		}
    	}
    	Boolean check = false;
    	Boolean outOfOptions = false;
//    	for (int i = 0; i < 10; i++) {
//    	}
    	while (!check) {
    		if (neighbors.size() > maxFringe) {
    			maxFringe = neighbors.size();
    		}
			Integer min = Collections.min(neighbors.values());
			List<Object> next = null;
			int temp = 0;
			for (List<Object> key : neighbors.keySet()) {
				if (neighbors.get(key) == min) {
					next = key;
					temp = neighbors.get(key);
				}
			}
			neighbors.remove(next);
			System.out.println(current);
			current = (List<Character>) next.get(0);
			System.out.println(current);
			currDepth = (int) next.get(1);
			totalExpanded++;
			if (current.equals(goal1) || current.equals(goal2)) {
				check = true;
			} else {
				for (List<Object> option : findNeighbors(current, currDepth)) {
		    		List<Character> pattern = (List<Character>) option.get(0);
		    		if (heuristic.equals("h1")) {
		    			neighbors.put(option, findHeuristic(pattern) + currDepth);
		    		} else {
		    			neighbors.put(option, findHeuristic2(pattern) + currDepth);
		    		}
		    	}
				if (neighbors.size() == 0) {
					check = true;
					outOfOptions = true;
				}
			}
    	}
    	if (outOfOptions) {
    		currDepth = -1;
    		totalNodes = 0;
    		totalExpanded = 0;
    		maxFringe = 0;
    	}
    	System.out.println(currDepth + ", " + totalNodes + ", " + totalExpanded + ", " + maxFringe);
    }
    
    // Uses Tiles out of place as heuristic
    public static int findHeuristic(List<Character> current) {
    	int total = 0;
    	for (int i = 0; i < current.size(); i++) {
    		if (!(current.get(i).equals(goal1.get(i)) || current.get(i).equals(goal2.get(i)))){
    			total++;
    		}
    	}
    	return total;
    }
    
    // Uses Manhatten distance as heuristic
    public static int findHeuristic2(List<Character> current){
    	int total = 0;
    	for (int i = 0; i < current.size(); i++) {
    		int currNum = 0;
    		int position = current.indexOf(goal1.get(i));
    		while (position != i) {
    			if (position / 4 < i / 4) {
    				position += 4; 
    			} else if (position / 4 > i / 4) {
    				position -= 4;
    			} else if (position < i) {
    				position++;
    			} else {
    				position--;
    			}
    			currNum++;
    		}
    		total += currNum;
    	}
    	return total;
    }
    
    // Finds neighbors of current node (possible moves) and returns them in a list along with current depth they are at
    public static List<List<Object>> findNeighbors(List<Character> current, int level) {
    	int position = current.indexOf(' ');
    	List<List<Object>> newAdditions = new ArrayList<List<Object>>();
    	if (position > 3) {
    		List<Object> temp = new ArrayList<Object>();
    		List<Character> copy = new ArrayList<Character>(current);
    		Collections.swap(copy, position, position - 4);
    		if (!dupeList.contains(copy)) {
    			temp.add(copy);
        		temp.add(level + 1);
        		newAdditions.add(temp);
        		totalNodes++;
        		dupeList.add(copy);
    		}
    	}
    	if (!(position % 4 == 0)) {
    		List<Object> temp = new ArrayList<Object>();
    		List<Character> copy = new ArrayList<Character>(current);
    		Collections.swap(copy, position, position - 1);
    		if (!dupeList.contains(copy)) {
    			temp.add(copy);
        		temp.add(level + 1);
        		newAdditions.add(temp);
        		totalNodes++;
        		dupeList.add(copy);
    		}
    	}
    	if (position < 12) {
    		List<Object> temp = new ArrayList<Object>();
    		List<Character> copy = new ArrayList<Character>(current);
    		Collections.swap(copy, position, position + 4);
    		if (!dupeList.contains(copy)) {
    			temp.add(copy);
        		temp.add(level + 1);
        		newAdditions.add(temp);
        		totalNodes++;
        		dupeList.add(copy);
    		}
    	}
    	if (!((position + 1) % 4 == 0)) {
    		List<Object> temp = new ArrayList<Object>();
    		List<Character> copy = new ArrayList<Character>(current);
    		Collections.swap(copy, position, position + 1);
    		if (!dupeList.contains(copy)) {
    			temp.add(copy);
        		temp.add(level + 1);
        		newAdditions.add(temp);
        		totalNodes++;
        		dupeList.add(copy);
    		}
    	}
    	return newAdditions;
    }
}

