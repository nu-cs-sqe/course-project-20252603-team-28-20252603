package i18n;

import java.util.Locale;

public final class Messages {
	private static Locale currentLocale = Locale.ENGLISH;

	private Messages() {
	}

	public static void setLocale(Locale locale) {
		currentLocale = locale;
	}

	public static Locale getLocale() {
		return currentLocale;
	}
}
