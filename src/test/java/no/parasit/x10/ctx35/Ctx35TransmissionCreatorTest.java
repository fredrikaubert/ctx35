package no.parasit.x10.ctx35;

import static org.junit.Assert.assertEquals;
import no.parasit.x10.Addressing;
import no.parasit.x10.Command;
import no.parasit.x10.ctx35.Ctx35TransmissionCreator;

import org.junit.Test;

public class Ctx35TransmissionCreatorTest {

	private Ctx35TransmissionCreator ctx35TransmissionCreator = new Ctx35TransmissionCreator();

	@Test
	public void offEventShouldBeCreatedCorrectly() {
		String created = ctx35TransmissionCreator.createTransmission(new Addressing('A', 1), Command.off, 1);
		assertEquals("$>28001A01 AOFF3B#", created);
	}

	
	@Test(expected = IllegalArgumentException.class)
	public void exceptionsIfUnitCodeIsRequiredButNotProvided() {
		ctx35TransmissionCreator.createTransmission(new Addressing('A'), Command.off, 1);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void exceptionsIfUnitCodeIsNotRequiredAndProvided() {
		ctx35TransmissionCreator.createTransmission(new Addressing('A', 1), Command.all_lights_off, 1);
	}
	
	
	@Test
	public void onEventShouldBeCreatedCorrectly() {
		String created = ctx35TransmissionCreator.createTransmission(new Addressing('A', 1), Command.on, 1);
		assertEquals("$>28001A01 AONFD#", created);
	}
	
	
	
}
