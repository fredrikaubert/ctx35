package no.parasit.x10.ctx35.responsehandlers;

import no.parasit.x10.ctx35.Ctx35QueryResponseParser;
import no.parasit.x10.ctx35.Ctx35ResponseHandler;

public class QueryCtx35ResponseHandler implements Ctx35ResponseHandler {

	private String expected ="$<2800! ";//note the trailing space
	private Ctx35QueryResponseParser queryResponseParser = new Ctx35QueryResponseParser();
	
	@Override
	public boolean canHandleResponse(String response) {
		return response.startsWith(expected);
	}

	@Override
	public void handleResponse(String response) {
		queryResponseParser.parseResponse(response);
		
	}

}
