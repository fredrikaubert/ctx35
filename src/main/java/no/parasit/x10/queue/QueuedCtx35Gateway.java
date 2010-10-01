package no.parasit.x10.queue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import no.parasit.x10.Addressing;
import no.parasit.x10.Command;
import no.parasit.x10.Transmission;
import no.parasit.x10.X10Sender;
import no.parasit.x10.ctx35.Ctx35Gateway;

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

	private X10Sender x10Gateway;
	private BlockingQueue<CommandTransmission> transmissionQueue = new LinkedBlockingQueue<CommandTransmission>();

	@Override
	public void transmit(Transmission transmission) {
		try {
			transmissionQueue.put(new CommandTransmission(transmission.getAddressing(), transmission.getCommand(), transmission.getCommandRepeat()));
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
						Addressing addressing = commandTransmission.getAddressing();
						Command command = commandTransmission.getCommand();
						int commandRepeat = commandTransmission.getCommandRepeat();
						x10Gateway.transmit(new Transmission(addressing, command, commandRepeat));
					}
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}

			}
		}).start();
	}

	public void setX10Gateway(X10Sender x10Gateway) {
		this.x10Gateway = x10Gateway;
	}

}
