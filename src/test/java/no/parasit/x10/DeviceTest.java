package no.parasit.x10;

import static org.junit.Assert.assertEquals;

import no.parasit.x10.Device;

import org.junit.Test;

public class DeviceTest {

	@Test
	public void shouldAllowLegalHouseAndUnitCodes() {
		{
			Device device = new Device("A1");
			assertEquals('A', device.getHouse());
			assertEquals(1, device.getUnit());
		}
		{
			Device device = new Device("P16");
			assertEquals('P', device.getHouse());
			assertEquals(16, device.getUnit());
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowIllegalArgumentExceptionWhenIllegalUnitCode() {
		new Device("A0");
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowIllegalArgumentExceptionWhenIllegalHouseCode() {
		new Device("Q1");
	}
}
