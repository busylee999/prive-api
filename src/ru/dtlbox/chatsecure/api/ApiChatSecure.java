package ru.dtlbox.chatsecure.api;

import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;

public class ApiChatSecure extends RequestHandler {
	
	public interface RequestListener{
		
		final static int E_API_CONNECTION = 8001;
		final static int E_SERVER_INTERNAL = 9001;
		
		public void onError(int errorCode, String errorMessage);
		
	}
	
	public interface RegistrationListener extends RequestListener {
		
		public void onRegistrationSuccess(final String login, final String password);
		
	}

    final String JSON_REGISTER_LOGIN = "username";
    final String JSON_REGISTER_PASS = "password";

	public void registerNewAccount(final RegistrationListener listener){
		String response = executePostRequest( "http://dev.priveim.com/api/signup", new JSONObject());
        if (response != null) {
            try {
                JSONObject jsonObject = new JSONObject(response);

                int login = jsonObject.getInt(JSON_REGISTER_LOGIN);
                String pass = jsonObject.getString(JSON_REGISTER_PASS);

                listener.onRegistrationSuccess(Integer.toString(login), pass);
            } catch (JSONException e) {
                e.printStackTrace();
                listener.onError(RequestListener.E_API_CONNECTION, "exсeption: " + e.getLocalizedMessage());
            }
        } else {
            listener.onError(RequestListener.E_API_CONNECTION, "response is empty");
        }

        listener.onRegistrationSuccess(response, response);
	}
}
