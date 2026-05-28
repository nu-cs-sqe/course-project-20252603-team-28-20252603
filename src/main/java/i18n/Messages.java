package i18n;

import java.util.Locale;
import java.util.Objects;

public final class Messages {
	private static Locale currentLocale = Locale.ENGLISH;

	private Messages() {
	}

	public static void setLocale(Locale locale) {
		Objects.requireNonNull(locale);
		currentLocale = locale;
	}

	public static Locale getLocale() {
		return currentLocale;
	}
}
