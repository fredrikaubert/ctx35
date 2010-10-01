package no.parasit.x10.queue;

import no.parasit.x10.Addressing;
import no.parasit.x10.Command;
import no.parasit.x10.Transmission;
import no.parasit.x10.queue.QueuedCtx35Gateway;

import org.junit.Test;

public class QueueGatewayTest {
	private QueuedCtx35Gateway queueGateway;
	
	public QueueGatewayTest(){
		this.queueGateway = new QueuedCtx35Gateway();
		queueGateway.setX10Gateway(new DummySysoutX10Gateway());
		queueGateway.init();
	}
	
	@Test
	public void testQueue() throws InterruptedException {
		queueGateway.transmit(new Transmission(new Addressing('H', 1), Command.on));
		System.out.println("Trasmitting one thingy");
		queueGateway.transmit(new Transmission(new Addressing('H', 1), Command.on));
		System.out.println("Trasmitting one thingy2");
		
		Thread.sleep(5000);
		
		
	}
	
	
}
