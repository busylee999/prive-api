package ru.dtlbox.chatsecure.api;

public abstract class RequestHandler {
	
	public final static String LOG_TAG = "RequestHandler"; 
	// post request
		protected String executePostRequest(final String url, JSONObject parameters) {

			Log.i(LOG_TAG + "/executePostRequest", "url: " + url);

			Log.i(LOG_TAG + "/executePostRequest", "parameters: " + parameters.toString());

			try {

				KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
				trustStore.load(null, null);

				SSLSocketFactory sf = new CustomSSLSocketFactory(trustStore);
				sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

				HttpParams params = new BasicHttpParams();
				HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
				HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);

				SchemeRegistry registry = new SchemeRegistry();
				registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
				registry.register(new Scheme("https", sf, 443));

				ClientConnectionManager ccm = new ThreadSafeClientConnManager(params, registry);

				HttpClient client = new DefaultHttpClient(ccm, params);

				HttpPost postUrl = new HttpPost(url);

				// if proxy is using
				@SuppressWarnings("deprecation")
				int portOfProxy = android.net.Proxy.getDefaultPort();
				if (portOfProxy > 0) {
					@SuppressWarnings("deprecation")
					HttpHost proxy = new HttpHost(android.net.Proxy.getDefaultHost(), portOfProxy);
					client.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
				}

				postUrl.setHeader("Content-type", "application/json");

				StringEntity content = new StringEntity(parameters.toString(), HTTP.UTF_8);

				postUrl.setEntity(content);

				// HttpResponse
				HttpResponse response = client.execute(postUrl);

				BufferedReader reader = new BufferedReader(
						new InputStreamReader(response.getEntity().getContent(), "utf-8"));
				StringBuilder sb = new StringBuilder();
				String line = null;
				while ((line = reader.readLine()) != null) {
					sb.append(line + System.getProperty("line.separator"));
				}
				return sb.toString();

			} catch (org.apache.http.client.ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();

			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
}
