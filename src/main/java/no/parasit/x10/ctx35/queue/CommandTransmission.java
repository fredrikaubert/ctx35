package no.parasit.x10.ctx35.queue;


import no.parasit.x10.Addressing;
import no.parasit.x10.Command;
import no.parasit.x10.ctx35.Ctx35ResponseHandler;

public class CommandTransmission {
	private String transmission;
	private Ctx35ResponseHandler responseHandler;

	public CommandTransmission(String transmission, Ctx35ResponseHandler responseHandler) {
		super();
		this.transmission = transmission;
		this.responseHandler = responseHandler;
	}
	public Ctx35ResponseHandler getResponseHandler()
	{
		return responseHandler;
	}
	public String getTransmission()
	{
		return transmission;
	}
}
