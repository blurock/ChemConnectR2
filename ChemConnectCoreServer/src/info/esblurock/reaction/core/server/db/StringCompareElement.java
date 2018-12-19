package info.esblurock.reaction.core.server.db;

import org.apache.commons.lang3.StringUtils;

import info.esblurock.reaction.chemconnect.core.data.contact.NameOfPerson;

public class StringCompareElement implements Comparable<StringCompareElement> {
	
	String stringToCompare;
	NameOfPerson name;
	int score;
	
	public StringCompareElement() {
	}
	
	public StringCompareElement(String stringToCompare, NameOfPerson name) {
		this.stringToCompare = stringToCompare;
		this.name = name;
		score = StringUtils.getLevenshteinDistance(stringToCompare, name.getFamilyName());
	}

	public int getScore() {
		return score;
	}
	
	public NameOfPerson getNameOfPerson() {
		return name;
	}
	
	@Override
	public int compareTo(StringCompareElement o) {
		int diff = score - o.getScore();
		return diff;
	}
	

}
