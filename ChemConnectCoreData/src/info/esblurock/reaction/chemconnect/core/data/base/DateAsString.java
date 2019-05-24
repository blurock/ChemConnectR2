package info.esblurock.reaction.chemconnect.core.data.base;

import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;

public class DateAsString {
	@SuppressWarnings("deprecation")
	public static String dateAsString(Date date) {
		
		if(date != null) {
		return DateTimeFormat.getShortDateFormat().format(date);
		} else {
			return DateTimeFormat.getShortDateFormat().format(new Date());
		}
	}
}
