package domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class KnightTest {
	@Test
	public void Constructor_WithWhiteColor_ExpectWhiteKnight(){
		Knight knight = new Knight(Color.WHITE);
		Color expectedColor = Color.WHITE;
		PieceType expectedType = PieceType.KNIGHT;
		Assertions.assertEquals(expectedColor, knight.color());
		Assertions.assertEquals(expectedType, knight.type());
	}
}
