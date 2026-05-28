package i18n;

import java.util.Locale;

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
}
