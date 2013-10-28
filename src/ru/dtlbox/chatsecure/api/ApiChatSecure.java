package ru.dtlbox.chatsecure.api;

public class ApiChatSecure extends RequestHandler {
	
	public interface RequestListener{
		
		final static int E_API_CONNECTION = 8001;
		final static int E_SERVER_INTERNAL = 9001;
		
		public void onError(int errorCode, String errorMessage);
		
	}
	
	public interface RegistrationListener extends RequestListener {
		
		public void onRegistrationResult(final String login, final String password);
		
	}
	
	public void registerNewAccount(final RegistrationListener listener){
		
	}
}
