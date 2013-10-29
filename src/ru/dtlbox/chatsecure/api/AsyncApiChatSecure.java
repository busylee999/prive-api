package ru.dtlbox.chatsecure.api;

public class AsyncApiChatSecure extends ApiChatSecure {

    public void registerNewAccount(final RegistrationListener listener){
		(new Thread(new Runnable() {
			
			@Override
			public void run() {
				AsyncApiChatSecure.super.registerNewAccount(listener);
			}
		})).start();
	}
}
