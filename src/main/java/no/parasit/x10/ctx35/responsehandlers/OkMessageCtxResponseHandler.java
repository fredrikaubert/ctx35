package no.parasit.x10.ctx35.responsehandlers;

import no.parasit.x10.ctx35.Ctx35ResponseHandler;

public class OkMessageCtxResponseHandler implements Ctx35ResponseHandler{

	private final String expected = "$<2800!4B#";
	
	@Override
	public boolean canHandleResponse(String response) {
		return expected.equals(response);
	}

	@Override
	public void handleResponse(String response) {
		//do nothing in particular
		
	}

}
