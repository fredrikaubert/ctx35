package no.parasit.x10.ctx35;

import java.util.ArrayList;
import java.util.List;

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

public class Ctx35Gateway implements X10Sender {

	private Ctx35SerialTransmitter ctx35SerialTransmitter = new Ctx35SerialTransmitter();
	private Ctx35TransmissionCreator ctx35TransmissionCreator = new Ctx35TransmissionCreator();
	private int numberOfRetries = 3;
	Ctx35ResponseHandler[] responseHandlers = new Ctx35ResponseHandler[] {
		new OkMessageCtxResponseHandler(),
	};
	
	
	
	public void transmit(Transmission transmission) {
		transmit(transmission.getAddressing(), transmission.getCommand(), transmission.getCommandRepeat() );
	}

	public void transmit(Addressing commandAddressing, Command command) {
		transmit(commandAddressing, command, 1);
	}
	
	
	public void transmit(Addressing commandAddressing, Command command, Integer commandRepeat) {
		int remainingRetries = numberOfRetries;
		String response = null;
		boolean responseAccepted;
		do {
			try {
				String transmission = ctx35TransmissionCreator.createTransmission(commandAddressing, command, commandRepeat == null ? 1 : commandRepeat);
				response = ctx35SerialTransmitter.transmit(transmission);
				responseAccepted = false;
				for (Ctx35ResponseHandler handler : responseHandlers) {
					if( handler.canHandleResponse(response)) {
						handler.handleResponse(response);
						responseAccepted = true;
						break;
					}
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
