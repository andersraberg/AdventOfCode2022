package se.anders_raberg.adventofcode2022;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import se.anders_raberg.adventofcode2022.DaysMain;

class TestRun {
	DaysMain testee = new DaysMain();

	@Test
	void testMain() throws IOException {
		DaysMain.main(null);
		assertTrue(true);
	}

}
