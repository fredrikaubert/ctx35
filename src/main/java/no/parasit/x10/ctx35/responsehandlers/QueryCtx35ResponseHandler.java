package no.parasit.x10.ctx35.responsehandlers;

import java.util.List;
import java.util.Queue;

import no.parasit.x10.Transmission;
import no.parasit.x10.ctx35.Ctx35QueryResponseParser;
import no.parasit.x10.ctx35.Ctx35ResponseHandler;

public class QueryCtx35ResponseHandler implements Ctx35ResponseHandler {

	private String expected ="$<2800! ";//note the trailing space
	private Ctx35QueryResponseParser queryResponseParser = new Ctx35QueryResponseParser();
	private Queue<Transmission> responseQueue;
	
	public QueryCtx35ResponseHandler(Queue<Transmission> responseQueue) {
		this.responseQueue = responseQueue;
	}
	
	@Override
	public boolean canHandleResponse(String response) {
		return response.startsWith(expected);
	}

	@Override
	public void handleResponse(String response) {
		System.out.println("RESPONSE:" + response);
		 List<Transmission> transmissions = queryResponseParser.parseResponse(response);
		 for (Transmission transmission : transmissions)
		{
			responseQueue.offer( transmission );
		}
		
	}

}
