package info.esblurock.reaction.chemconnect.core.data.base;

import java.text.DateFormat;
import java.util.Date;

public class DateAsString {
	static DateFormat format = DateFormat.getInstance();
	public static String dateAsString(Date date) {
		if(date != null) {
		return format.format(date);
		} else {
			return format.format(new Date());
		}
	}
}
