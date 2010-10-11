package no.parasit.x10.ctx35;

import java.io.IOException;

import no.parasit.x10.Addressing;
import no.parasit.x10.Command;
import no.parasit.x10.Transmission;
import no.parasit.x10.ctx35.queue.CommandTransmission;
import no.parasit.x10.ctx35.queue.QueuedCtx35Gateway;
import no.parasit.x10.ctx35.responsehandlers.OkMessageCtxResponseHandler;
import no.parasit.x10.ctx35.responsehandlers.QueryCtx35ResponseHandler;

import org.junit.Before;
import org.junit.Test;

public class Ctx35GatewayTest {
	QueuedCtx35Gateway gateway;

	@Before
	public void setUp() throws Exception {
		gateway = new QueuedCtx35Gateway();
		Ctx35Gateway g  = new Ctx35Gateway();
		g.setCommport("/dev/tty.PL2303-00002006");
		g.init();
		gateway.setX10Gateway(g);
		gateway.init();
	}

	@Test
//	@Ignore
	public void testOnOffSerie() throws InterruptedException, IOException {
//		{
//			String transmission = "$>28001SON";
//			transmission += new Ctx35CheckSumCalculator().calculate( transmission );
//			transmission +="#";
//			gateway.getQueue().add( new CommandTransmission( transmission, new OkMessageCtxResponseHandler() ) );
//		}
		
		
		
		Thread.sleep(10000);
		String transmission = "$>280000";
		transmission += new Ctx35CheckSumCalculator().calculate( transmission );
		transmission +="#";
		gateway.getQueue().add( new CommandTransmission( transmission, new QueryCtx35ResponseHandler() ) );
		
		Thread.sleep(10000);
		gateway.transmit(new Transmission(new Addressing('H'), Command.all_lights_off));
		gateway.transmit(new Transmission(new Addressing('H',8, 9,10,11), Command.on));
		Thread.sleep(2000);
		gateway.transmit(new Transmission(new Addressing('H',8, 9,10,11), Command.off));
		Thread.sleep(2000);
		gateway.transmit(new Transmission(new Addressing('H',8, 9,10,11), Command.on));
		Thread.sleep(2000);
		
		
//		gateway.transmit(new HouseCode('H'), new UnitCode[] { new UnitCode(9), new UnitCode(10), new UnitCode(11)}, Command.off, 1);
//		gateway.transmit(new HouseCode('H'), new UnitCode[] { new UnitCode(9)}, Command.bright, 16);
//		Thread.sleep(2000);
		
//		gateway.transmit(new HouseCode('H'), new UnitCode[] {new UnitCode(9)}, Command.dim, 16);
//		gateway.transmit(new Device("H9"), Command.dim);
//		gateway.transmit(new Device("H9"), Command.dim);
		// gateway.transmit(new Device("H9"), Command.dim);

	}
}
