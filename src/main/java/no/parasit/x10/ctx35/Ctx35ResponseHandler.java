package no.parasit.x10.ctx35;

public interface Ctx35ResponseHandler {
	public boolean canHandleResponse(String response);
	public void handleResponse(String response);
}
