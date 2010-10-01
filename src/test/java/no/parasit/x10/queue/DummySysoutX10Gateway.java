package no.parasit.x10.queue;

import no.parasit.x10.Addressing;
import no.parasit.x10.Command;
import no.parasit.x10.Transmission;
import no.parasit.x10.X10Sender;

public class DummySysoutX10Gateway implements X10Sender {

	@Override
	public void transmit(Transmission transmission) {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("dummy-transmit:" + transmission.getAddressing()+":" + transmission.getCommand() + " repeat:" + transmission.getCommandRepeat());

	}

}
