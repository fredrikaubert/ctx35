package no.parasit.x10.ctx35.queue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import no.parasit.x10.Addressing;
import no.parasit.x10.Command;
import no.parasit.x10.Transmission;
import no.parasit.x10.X10Sender;
import no.parasit.x10.ctx35.Ctx35GatewayInterface;
import no.parasit.x10.ctx35.Ctx35TransmissionCreator;
import no.parasit.x10.ctx35.responsehandlers.OkMessageCtxResponseHandler;

/**
 * Proxys a Ctx35Gateway and provides a queue on which to put X10 command
 * transmissions. Creates a thread that pulls the queue transmit them to a
 * X10Gateway. The intention of this class is to provide thread safety to non-thread-safe Ctx35Gateway as well
 * as to make the gateway "less blocking".
 * 
 * @author fredrik
 * 
 */
public class QueuedCtx35Gateway implements X10Sender {

	private Ctx35GatewayInterface x10Gateway;
	private BlockingQueue<CommandTransmission> transmissionQueue = new LinkedBlockingQueue<CommandTransmission>();
	private Ctx35TransmissionCreator ctx35TransmissionCreator = new Ctx35TransmissionCreator();

	@Override
	public void transmit(Transmission transmission) {
		try {
			transmissionQueue.put( new CommandTransmission( ctx35TransmissionCreator.createTransmission( transmission ), new OkMessageCtxResponseHandler()) );
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
	
	


	public void init() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					while (true) {
						CommandTransmission commandTransmission = transmissionQueue.take();
						x10Gateway.transmit(commandTransmission.getTransmission(), commandTransmission.getResponseHandler());
					}
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}

			}
		}, "Ctx35-Thread").start();
	}

	public void setX10Gateway(Ctx35GatewayInterface x10Gateway) {
		this.x10Gateway = x10Gateway;
	}
	
	public BlockingQueue<CommandTransmission> getQueue() {
		return transmissionQueue;
	}

}
