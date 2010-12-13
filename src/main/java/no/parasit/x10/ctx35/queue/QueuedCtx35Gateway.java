package no.parasit.x10.ctx35.queue;

import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import no.parasit.x10.Addressing;
import no.parasit.x10.Command;
import no.parasit.x10.Transmission;
import no.parasit.x10.X10Sender;
import no.parasit.x10.ctx35.Ctx35CheckSumCalculator;
import no.parasit.x10.ctx35.Ctx35GatewayInterface;
import no.parasit.x10.ctx35.Ctx35TransmissionCreator;
import no.parasit.x10.ctx35.responsehandlers.OkMessageCtxResponseHandler;
import no.parasit.x10.ctx35.responsehandlers.QueryCtx35ResponseHandler;

/**
 * 
 * @author fredrik
 * 
 */
public class QueuedCtx35Gateway implements X10Sender
{

	protected static final int PAUSE_BETWEEN_QUERY = 1000;

	private Ctx35GatewayInterface x10Gateway;
	private BlockingQueue<CommandTransmission> transmissionQueue = new LinkedBlockingQueue<CommandTransmission>();
	private Queue<Transmission> responseQueue = new LinkedBlockingQueue<Transmission>();
	private Ctx35TransmissionCreator ctx35TransmissionCreator = new Ctx35TransmissionCreator();
	private boolean listenForCommands = true;

	@Override
	public void transmit(Transmission transmission)
	{
		try
		{
			transmissionQueue.put( new CommandTransmission(
					ctx35TransmissionCreator.createTransmission( transmission ),
					new OkMessageCtxResponseHandler() ) );
		} catch (InterruptedException e)
		{
			throw new RuntimeException( e );
		}
	}

	public void init()
	{
		final QueryCtx35ResponseHandler queryCtx35ResponseHandler = new QueryCtx35ResponseHandler(responseQueue);
		new Thread( new Runnable()
		{
			@Override
			public void run()
			{
				try
				{
					while (true)
					{
						CommandTransmission commandTransmission = transmissionQueue.take();
						x10Gateway.transmit( commandTransmission.getTransmission(),
								commandTransmission.getResponseHandler() );
					}
				} catch (InterruptedException e)
				{
					throw new RuntimeException( e );
				}

			}
		}, "Ctx35-Transmit" ).start();
		if( listenForCommands )
		{
			new Thread( new Runnable()
			{
				@Override
				public void run()
				{
					try
					{
						while(true) {
						String transmission = "$>280000";
						transmission += new Ctx35CheckSumCalculator().calculate( transmission );
						transmission += "#";
						transmissionQueue.offer( new CommandTransmission( transmission,
								queryCtx35ResponseHandler) );
						Thread.sleep(PAUSE_BETWEEN_QUERY);
						}
					} catch (InterruptedException e)
					{
						System.out.println( "Got exception:" + e );
						throw new RuntimeException();
					}

				}

			}, "Ctx35-Query" ).start();
		}

	}

	
	

	public void setResponseQueue(Queue<Transmission> responseQueue)
	{
		this.responseQueue = responseQueue;
	}

	public void setTransmissionQueue(
			BlockingQueue<CommandTransmission> transmissionQueue)
	{
		this.transmissionQueue = transmissionQueue;
	}

	public void setX10Gateway(Ctx35GatewayInterface x10Gateway)
	{
		this.x10Gateway = x10Gateway;
	}

	@Deprecated //Should reveal the queue, really...
	public BlockingQueue<CommandTransmission> getQueue()
	{
		return transmissionQueue;
	}

}
