/**
 * 
 */
package net.tyack.vanity_numbers;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author jamestyack
 *
 */
public class VanityPhoneNumbers {
	
	public static Map<Character, Character[]> keypad = new HashMap<>();
	static {
		keypad.put('0', new Character[] {'0'});
		keypad.put('1', new Character[] {'1'});
		keypad.put('2', new Character[] {'a', 'b', 'c'});
		keypad.put('3', new Character[] {'d', 'e', 'f'});
		keypad.put('4', new Character[] {'g', 'h', 'i'});
		keypad.put('5', new Character[] {'j', 'k', 'l'});
		keypad.put('6', new Character[] {'m', 'n', 'o'});
		keypad.put('7', new Character[] {'p', 'q', 'r', 's'});
		keypad.put('8', new Character[] {'t', 'u', 'v'});
		keypad.put('9', new Character[] {'w', 'x', 'y', 'z'});
	}
	
	/**
	 * @param telNumber the telephone number
	 * @return a list of strings containing vanity numbers
	 * @throws InvalidPhoneNumberException 
	 */
	public List<String> getVanityNumbers(String telNumber) throws InvalidPhoneNumberException {
		List<String> result = new ArrayList<>();
		List<List<Character>> lettersForEachDigit = constructListOfLettersForEachDigit(telNumber);
		if (lettersForEachDigit.size() > 0) {
			getVanityNumberRecursive(result, lettersForEachDigit, new ArrayDeque<Character>(), 0);			
		}
		return result;
	}
	
	/**
	 * @param results a list of results that is added to
	 * @param lettersForEachDigit
	 * @param stackOfLetters a stack to hold all letters up to last letter; added to on each recursive call
	 * @param digitPosition current position in the phone number
	 * @param result this list of vanity numbers
	 */
	private void getVanityNumberRecursive(List<String> results, List<List<Character>> lettersForEachDigit, Deque<Character> stackOfLetters, int digitPosition) {
		List<Character> thisLevelLetters = lettersForEachDigit.get(digitPosition);
		if (digitPosition == lettersForEachDigit.size() -1) {
			// reached the last number - print a vanity number
			for  (int i = 0; i < thisLevelLetters.size(); i++) {
				results.add(format(stackOfLetters) + thisLevelLetters.get(i));
			}
		} else {
			// not at last number, iterate over the chars at this number, pushing each onto stack and recurse
			for (int i = 0; i < thisLevelLetters.size(); i++) {
				stackOfLetters.push(thisLevelLetters.get(i));
				getVanityNumberRecursive(results, lettersForEachDigit, stackOfLetters, digitPosition + 1);
			}
		}
		if (!stackOfLetters.isEmpty()) {
			stackOfLetters.pop();
		}
	}

	private List<List<Character>> constructListOfLettersForEachDigit(String telNumber) throws InvalidPhoneNumberException {
		List<List<Character>> numberLetterCombos = new ArrayList<>();
		for (int i=0; i<telNumber.length(); i++) {
			List<Character> list = new ArrayList<Character>();
			Character[] keyChars = keypad.get(telNumber.charAt(i));
			if (keyChars != null) {
				list.addAll(Arrays.asList(keyChars));
				numberLetterCombos.add(list);
			} else {
				throw new InvalidPhoneNumberException("Bad digit in phone number: " 
							+ telNumber + " when checking " + telNumber.charAt(i));
			}
		}
		return numberLetterCombos;
	}

	/**
	 * @param otherLetters
	 * @return a string formed of the characters in the deque ('stack')
	 */
	private String format(Deque<Character> characters) {
		StringBuilder sb = new StringBuilder();
		Iterator<Character> iter = characters.descendingIterator();
		while (iter.hasNext()) {
			sb.append(iter.next());
		}
		return sb.toString();
	}
}
