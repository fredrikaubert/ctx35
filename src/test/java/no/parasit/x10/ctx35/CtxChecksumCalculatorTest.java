package no.parasit.x10.ctx35;

import static org.junit.Assert.*;

import no.parasit.x10.ctx35.Ctx35CheckSumCalculator;

import org.junit.Test;

public class CtxChecksumCalculatorTest {
	private Ctx35CheckSumCalculator checkSumCalculator = new Ctx35CheckSumCalculator();

	@Test
	public void testCheckSum() {
		String str = "$>28001A[1]011D31";
		assertEquals("C1", checkSumCalculator.calculate(str));
	}
}
