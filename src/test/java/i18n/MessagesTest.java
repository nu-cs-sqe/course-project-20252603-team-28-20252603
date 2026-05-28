package i18n;

import java.util.Locale;
import java.util.MissingResourceException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MessagesTest {

	@BeforeEach
	public void resetLocale() {
		Messages.setLocale(Locale.ENGLISH);
	}

	@Test
	public void setLocaleToKoreanGetterReturnsKorean() {
		Messages.setLocale(Locale.KOREAN);

		Assertions.assertEquals(Locale.KOREAN, Messages.getLocale());
	}

	@Test
	public void setLocaleWithNullThrows() {
		Assertions.assertThrows(NullPointerException.class,
			() -> Messages.setLocale(null));
	}

	@Test
	public void getReturnsEnglishMessageByDefault() {
		String result = Messages.get("app.title");

		Assertions.assertEquals("Chess", result);
	}

	@Test
	public void getReturnsKoreanMessageAfterSetLocale() {
		Messages.setLocale(Locale.KOREAN);

		String result = Messages.get("app.title");

		Assertions.assertEquals("체스", result);
	}

	@Test
	public void getWithMissingKeyThrows() {
		Assertions.assertThrows(MissingResourceException.class,
			() -> Messages.get("key.that.does.not.exist"));
	}

	@Test
	public void getWithNullKeyThrows() {
		Assertions.assertThrows(NullPointerException.class,
			() -> Messages.get(null));
	}
}
