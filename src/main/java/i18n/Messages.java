package i18n;

import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

public final class Messages {
	private static final String BUNDLE_BASE_NAME = "messages";
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

	public static String get(String key) {
		Objects.requireNonNull(key);
		ResourceBundle bundle = ResourceBundle.getBundle(
			BUNDLE_BASE_NAME, currentLocale,
			ResourceBundle.Control.getNoFallbackControl(ResourceBundle.Control.FORMAT_PROPERTIES));
		return bundle.getString(key);
	}
}
