package no.parasit.x10.queue;

import no.parasit.x10.Transmission;
import no.parasit.x10.ctx35.Ctx35GatewayInterface;
import no.parasit.x10.ctx35.Ctx35ResponseHandler;

public class DummySysoutX10Gateway implements Ctx35GatewayInterface {

	@Override
	public void transmit(String transmission, Ctx35ResponseHandler responseHandler) {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("dummy-transmit:" + transmission );

	}

}
