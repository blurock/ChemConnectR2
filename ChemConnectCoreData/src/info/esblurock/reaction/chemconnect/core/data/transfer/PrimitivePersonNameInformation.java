package info.esblurock.reaction.chemconnect.core.data.transfer;

@SuppressWarnings("serial")
public class PrimitivePersonNameInformation extends PrimitiveDataStructureInformation  {

	String personTitle;
	String personGivenName;
	
	public PrimitivePersonNameInformation() {
		super();
		this.personTitle = "Title";
		this.personGivenName = "Name";
	}

	public PrimitivePersonNameInformation(PrimitiveDataStructureInformation info,
			String personTitle, String personGivenName) {
		super(info);
		this.personTitle = personTitle;
		this.personGivenName = personGivenName;
	}

	public void setPersonTitle(String personTitle) {
		this.personTitle = personTitle;
	}

	public void setPersonGivenName(String personGivenName) {
		this.personGivenName = personGivenName;
	}

	public String getPersonTitle() {
		return personTitle;
	}

	public String getPersonGivenName() {
		return personGivenName;
	}
	
	public String getPersonFamiltyName() {
		return value;
	}
	@Override
	public String toString() {
		return toString("");
	}
	public String toString(String prefix) {
		StringBuilder builder = new StringBuilder();
		super.toString(prefix);
		if(personTitle != null ) {
			builder.append(", ");
			builder.append(personTitle);
		}
		builder.append("  ");
		builder.append(personGivenName);
		return builder.toString();
	}
}
