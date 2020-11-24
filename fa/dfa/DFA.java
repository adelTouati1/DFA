package fa.dfa;


import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import fa.State;

/**
 * @author Adel Touati
 *
 */
public class DFA implements DFAInterface {

	private Set<DFAState> allStates;
	private Set<Character> alphabet;
	private DFA complement;
	private DFAState startState;
	

	public DFA(){
		//creating new linkedhashset for states and alphabets
		allStates = new LinkedHashSet<DFAState>();
		alphabet = new LinkedHashSet<Character>();
	}
	
	/* (non-Javadoc)
	 * @see fa.FAInterface#addStartState(java.lang.String)
	 * Adding starting state and assign it so startState variable
	 */
	@Override
	public void addStartState(String name) {
		DFAState start = getState(name);
		//making sure that there is no start state 
		if(start == null){
			start = new DFAState(name);
			addState(start);
		}
		startState = start;
	}

	/* (non-Javadoc)
	 * @see fa.FAInterface#addState(java.lang.String)
	 * adding states 
	 */
	@Override
	public void addState(String name) {
		DFAState state = new DFAState(name);
		addState(state);
	}
	
	/**
	 * @param s
	 * adding states to the linkedhashset
	 */
	private void addState(DFAState s){
		allStates.add(s);
	}

	/* (non-Javadoc)
	 * @see fa.FAInterface#addFinalState(java.lang.String)
	 *adding final state by name 
	 */
	@Override
	public void addFinalState(String name) {	
		DFAState finalState = new DFAState(name, true);
		addState(finalState);
	}

	/* (non-Javadoc)
	 * @see fa.FAInterface#addTransition(java.lang.String, char, java.lang.String)
	 */
	@Override
	public void addTransition(String fromState, char onSymb, String toState) {
		// establishing the transition from what state to what state
		(getState(fromState)).addTransition(onSymb, getState(toState));
		//checking if linkedhashset does not contain the onSymb to add it
		if(!alphabet.contains(onSymb)){
			alphabet.add(onSymb);
		}
	}
	/**
	 * @param name
	 * @return gState
	 */
	private DFAState getState(String name){
		DFAState gState = null;
		DFAState states= null;
		//iterator to iterate all the states
		Iterator<DFAState> iterator = allStates.iterator();
		// flag to quit the loop when is needed
		boolean flag = true;
		//iterate the states
		while(iterator.hasNext()){
		 states = iterator.next();
		 //making sure to return the right state and quit the loop
			if(states.getName().equals(name) && flag == true){
				gState = states;
				flag = false;
			}
		}
		return gState;
	}

	/* (non-Javadoc)
	 * @see fa.FAInterface#getStates()
	 * getting all the states
	 */
	@Override
	public Set<? extends State> getStates() {
		Set<State> gStates = new LinkedHashSet<State>();
		gStates.addAll(allStates);
		return gStates;
	}

	/* (non-Javadoc)
	 * @see fa.FAInterface#getFinalStates()
	 * getting final state
	 */
	@Override
	public Set<? extends State> getFinalStates() {
		Set<State> gfState = new LinkedHashSet<State>();
		DFAState state= null;
		//iterate all the states
		Iterator<DFAState> iterator = allStates.iterator();
		while(iterator.hasNext()){
		 state = iterator.next();
		//making sure the state is final before adding it to the set
			if(state.isFinal()){
				gfState.add(state);
			}
		}
		return gfState;
	}

	/* (non-Javadoc)
	 * @see fa.FAInterface#getStartState()
	 * getting start state
	 */
	@Override
	public State getStartState() {
		return startState;
	}

	/* (non-Javadoc)
	 * @see fa.FAInterface#getABC()
	 * getting the alphabet
	 */
	@Override
	public Set<Character> getABC() {
		return alphabet;
	}

	/* (non-Javadoc)
	 * @see fa.FAInterface#complement()
	 * creating DFA complement
	 */
	@Override
	public DFA complement() {
		complement =  new DFA();
		DFAState states= null;
		Iterator<DFAState> iterator = allStates.iterator();
		//adding starting state
		complement.addStartState(startState.getName());
		//iterate and adding all the states to the complement
		while(iterator.hasNext()){
		 states = iterator.next();
		//making final states as none final states
		 if (states.isFinal() || states.getName()=="e") {
			 complement.addState(states.getName());
		 }else {
				complement.addFinalState(states.getName());
			}
		}
		
		//iterating the states to add all transitions and alphabet
		Iterator<DFAState> iterator1 = allStates.iterator();
		while(iterator1.hasNext()) {
			DFAState newState = iterator1.next();
			Iterator<Character> iteratorC = alphabet.iterator();
			while(iteratorC.hasNext()) {
				char c = iteratorC.next();
			complement.addTransition(newState.getName(),c ,newState.getTo(c).getName());
			}
		}
		return complement;

	}

	/* (non-Javadoc)
	 * @see fa.dfa.DFAInterface#accepts(java.lang.String)
	 */
	@Override
	public boolean accepts(String s) {
		boolean acceptanceState = false;
		char[] inChar = s.toCharArray();
		DFAState currentState = startState;
		//making sure there is not just one state and the first state is not an empty one
		if(! (inChar.length == 1 && inChar[0] == 'e')){
			for(char character : inChar){
				currentState = currentState.getTo(character);
			}
		}
		//checking if is the final state to accept
		if(currentState.isFinal()){
			acceptanceState = true;
		}
		return acceptanceState;
	}

	/* (non-Javadoc)
	 * @see fa.dfa.DFAInterface#getToState(fa.dfa.DFAState, char)
	 * getting to a state using a the alphabet 
	 */
	@Override
	public State getToState(DFAState from, char onSymb) {
		return from.getTo(onSymb);
	}
	/**
	 * printing the test sequence and the DFA machine
	 * 
	 */
	public String toString() {
		// the string to be build on
		String outString = "Q = { ";
		String finalState = "F = { ";
		//start state
		DFAState sState= startState;
		//iterate all the states
		Iterator<DFAState> iterator = allStates.iterator();
		
		while(iterator.hasNext()){
			sState = iterator.next();
			outString = outString+sState.toString();
			outString = outString+ " ";
			//checking for final state
			if(sState.isFinal()){
				finalState = finalState+sState.toString();
				finalState = finalState+" ";
			}
		}
		outString =outString+ "}\n";
		finalState =finalState+ "}\n";
		//Iterating to find sigma 
		outString =outString+ "Sigma = { ";
		for(char cha : alphabet){
			outString = outString+ cha + " ";
		}
		outString =outString+ "}\n";
		//creating delta table
		outString = outString+"delta =\n" + String.format("%15s", "");
		Iterator<Character> iteratorC = alphabet.iterator();
		while(iteratorC.hasNext()) {
			char c = iteratorC.next();
			outString =outString+ String.format("%15s", c);
		}
		outString = outString+ "\n";
		//printing the from states
		for(DFAState pStates : allStates){
			outString = outString+ String.format("%15s", pStates.toString());
			//iterating to print the to state using the alphabet and the from state
			for(char c : alphabet){
				outString = outString+String.format("%15s", pStates.getTo(c).toString());
			}
			outString = outString+"\n";
		}
		outString =outString+"q0 = " + startState + "\n";
		outString =outString+finalState;
		return outString;
	}

}
