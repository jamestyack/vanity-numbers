/**
 * 
 */
package net.tyack.vanity_numbers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * @author james
 *
 */
public class VanityPhoneNumbersTest {

	@Test
	public void testFiveDigitNumber() throws InvalidPhoneNumberException {
		VanityPhoneNumbers vanityNumbers = new VanityPhoneNumbers();
		List<String> results = vanityNumbers.getVanityNumbers("89225");
		assertTrue(results.contains("tyack"));
	}
	
	@Test
	public void testSevenDigitNumber() throws InvalidPhoneNumberException {
		VanityPhoneNumbers vanityNumbers = new VanityPhoneNumbers();
		List<String> results = vanityNumbers.getVanityNumbers("2637397");
		assertTrue(results.contains("andrews"));
	}
	
	@Test
	public void testThreeDigitNumber() throws InvalidPhoneNumberException {
		VanityPhoneNumbers vanityNumbers = new VanityPhoneNumbers();
		List<String> results = vanityNumbers.getVanityNumbers("324");
		List<String> expectedResults = createList(
				"dag", "dah", "dai",
				"dbg", "dbh", "dbi",
				"dcg", "dch", "dci",
				"eag", "eah", "eai",  
				"ebg", "ebh", "ebi",
				"ecg", "ech", "eci",
				"fag", "fah", "fai",  
				"fbg", "fbh", "fbi",
				"fcg", "fch", "fci");
		assertEquals(expectedResults, results);
	}
	
	@Test
	public void testEmptyNumber() throws InvalidPhoneNumberException {
		VanityPhoneNumbers vanityNumbers = new VanityPhoneNumbers();
		List<String> results = vanityNumbers.getVanityNumbers("");
		for (String result : results) {
			System.out.println(result);
		}
		assertEquals(new ArrayList<String>(), results);
	}
	
	@Test(expected=InvalidPhoneNumberException.class)
	public void testBadNumber() throws InvalidPhoneNumberException {
		VanityPhoneNumbers vanityNumbers = new VanityPhoneNumbers();
		try {
			vanityNumbers.getVanityNumbers("abc");
		} catch (InvalidPhoneNumberException ipne) {
			assertEquals("Bad digit in phone number: abc when checking a", ipne.getMessage());
			throw ipne;
		}
	}

	/**
	 * @return
	 */
	private List<String> createList(String ... strings) {
		List<String> list = new ArrayList<>();
		for (String string : strings) {
			list.add(string);
		}
		return list;
	}

}
