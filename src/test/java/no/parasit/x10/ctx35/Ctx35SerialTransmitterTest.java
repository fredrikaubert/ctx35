package no.parasit.x10.ctx35;

import gnu.io.CommPortIdentifier;

import java.io.IOException;

import no.parasit.x10.ctx35.Ctx35CheckSumCalculator;
import no.parasit.x10.ctx35.Ctx35SerialTransmitter;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class Ctx35SerialTransmitterTest {

	Ctx35SerialTransmitter gateway;

	@Before
	public void setUp() throws Exception {
		gateway = new Ctx35SerialTransmitter();
		gateway.setCommPortIdentifier(CommPortIdentifier.getPortIdentifier("/dev/tty.PL2303-00001004"));
		gateway.init();
	}

	@Test
	@Ignore
	public void testOnOffSerie() throws InterruptedException, IOException {

		String switchCommand = "ON";
		int counter = 30;
		do {
			String transmission = "$>28001A01 A";
			switchCommand = "ON".equals(switchCommand) ? "OFF" : "ON";

			transmission += switchCommand;
			transmission += new Ctx35CheckSumCalculator().calculate(transmission);
			transmission += "#";
			gateway.transmit(transmission);
			if ("ON".equals(switchCommand)) {
				Thread.sleep(5000);
			}

		} while (counter-- > 0);

	}

}
