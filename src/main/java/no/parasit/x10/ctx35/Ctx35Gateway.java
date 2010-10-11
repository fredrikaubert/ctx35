package no.parasit.x10.ctx35;

import java.util.ArrayList;
import java.util.List;

import org.omg.CORBA.portable.ResponseHandler;

import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import no.parasit.x10.Addressing;
import no.parasit.x10.Command;
import no.parasit.x10.Device;
import no.parasit.x10.HouseCode;
import no.parasit.x10.Transmission;
import no.parasit.x10.UnitCode;
import no.parasit.x10.X10Sender;
import no.parasit.x10.ctx35.responsehandlers.OkMessageCtxResponseHandler;

public class Ctx35Gateway implements Ctx35GatewayInterface {

	private Ctx35SerialTransmitter ctx35SerialTransmitter = new Ctx35SerialTransmitter();
	
	private int numberOfRetries = 3;

	
	
	
	
	public void transmit(String transmission, Ctx35ResponseHandler responseHandler) {
		int remainingRetries = numberOfRetries;
		String response = null;
		boolean responseAccepted;
		do {
			try {
				response = ctx35SerialTransmitter.transmit(transmission);
				System.out.println("transmission:" + transmission +  " response:" + response);
				responseAccepted = responseHandler.canHandleResponse( response );
				if( responseAccepted) {
					responseHandler.handleResponse( response );
				}
				
			} catch (Ctx35ReadException e) {
				responseAccepted = false;
				response = null;
			}

		} while (--remainingRetries > 0 && !responseAccepted);

	}

	public void init() {
		ctx35SerialTransmitter.init();
	}

	public void destroy() {
		ctx35SerialTransmitter.destroy();
	}

	public void setCommport(String commPort) {
		try {
			ctx35SerialTransmitter.setCommPortIdentifier(CommPortIdentifier.getPortIdentifier(commPort));
		} catch (NoSuchPortException e) {
			throw new RuntimeException("commPort does not exist:" + commPort, e);
		}
	}

}
