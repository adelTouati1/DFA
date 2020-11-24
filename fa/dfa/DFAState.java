package fa.dfa;

import java.util.HashMap;

import fa.State;

/**
 * @author Adel Touati
 *
 */

public class DFAState extends State {

	private HashMap<Character, DFAState> delta;
	private boolean isFinal;
	
	/**
	 * @param name
	 */
	private void initialization(String name){
		this.name = name;
		delta = new HashMap<Character, DFAState>();
	}

	/**
	 * @param name
	 */
	public DFAState(String name){
		initialization(name);
		isFinal = false;
	}

	/**
	 * @param name
	 * @param isFinal
	 */
	public DFAState(String name, boolean isFinal){
		initialization(name);
		this.isFinal = isFinal;
	}


	/**
	 * @return isFinal
	 */
	public boolean isFinal(){
		return isFinal;
	}


	/**
	 * @param onSymb
	 * @param toState
	 */
	public void addTransition(char onSymb, DFAState toState){
		delta.put(onSymb, toState);
	}

	/**
	 * @param symb
	 * @return delta.get(symb)
	 */
	public DFAState getTo(char symb){
		return delta.get(symb);
	}

	
}
