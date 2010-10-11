package no.parasit.x10.ctx35;

import no.parasit.x10.Transmission;

public interface Ctx35GatewayInterface
{

	public abstract void transmit(String transmission,
			Ctx35ResponseHandler responseHandler);

}