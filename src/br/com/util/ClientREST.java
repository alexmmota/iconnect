package br.com.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import android.content.Context;
import android.telephony.TelephonyManager;
import android.util.Log;

public class ClientREST {
	private static ClientREST clientREST;
	private String URL_REST_FACE = "https://br-com-iconnected.herokuapp.com/facebook/savetoken/";
	private String URL_REST_TWITTER = "https://br-com-iconnected.herokuapp.com/twitter/savetoken/";
	private String URL_REST_FEEDBACK = "https://br-com-iconnected.herokuapp.com/feedback/save/";

	private ClientREST() {
	}

	public static ClientREST getInstance() {
		if (clientREST == null)
			clientREST = new ClientREST();
		return clientREST;
	}

	public void callWebServiceFace(final Context c, final String accessToken, final String user){
		final String telefone = getPhone(c);

		new Thread(new Runnable() {			
			@Override
			public void run() {
				try {
					HttpClient httpclient = new DefaultHttpClient();
			    	HttpPost post = new HttpPost(URL_REST_FACE);
					
					List<NameValuePair> parametros = new ArrayList<NameValuePair>();
					parametros.add(new BasicNameValuePair("token", accessToken));
					parametros.add(new BasicNameValuePair("userid", user));
					parametros.add(new BasicNameValuePair("phone", telefone));
					post.setEntity(new UrlEncodedFormEntity(parametros));
					
					HttpResponse response = httpclient.execute(post);
					BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
		            
				    String res = br.readLine();
				    Log.i("TESTE","token_face: "+res);
					PreferenceUtil.setPreferences(c, "TOKEN_FACEBOOK", res);
        			Log.i("TESTE","token: "+ PreferenceUtil.getPreferences(c, "TOKEN_FACEBOOK"));

				} catch (MalformedURLException e1) {
					e1.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	public void callWebServiceFeedback(final Context c, final String user, final String text, final String data){
		new Thread(new Runnable() {			
			@Override
			public void run() {
				try {
					HttpClient httpclient = new DefaultHttpClient();
			    	HttpPost post = new HttpPost(URL_REST_FEEDBACK);
					
					List<NameValuePair> parametros = new ArrayList<NameValuePair>();
					parametros.add(new BasicNameValuePair("user", user));
					parametros.add(new BasicNameValuePair("message", text));
					parametros.add(new BasicNameValuePair("date", data));
					post.setEntity(new UrlEncodedFormEntity(parametros));

					httpclient.execute(post);
				} catch (MalformedURLException e1) {
					e1.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
	
	public void callWebServiceTwitter(final Context c, final String accessToken, final String secretToken, final String user) {
		final String telefone = getPhone(c);
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					HttpClient httpclient = new DefaultHttpClient();
			    	HttpPost post = new HttpPost(URL_REST_TWITTER);
					
					List<NameValuePair> parametros = new ArrayList<NameValuePair>();
					parametros.add(new BasicNameValuePair("accessToken", accessToken));
					parametros.add(new BasicNameValuePair("secretToken", secretToken));
					parametros.add(new BasicNameValuePair("userId", user));
					parametros.add(new BasicNameValuePair("phone", telefone));
					post.setEntity(new UrlEncodedFormEntity(parametros));
					
					HttpResponse response = httpclient.execute(post);
					BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
		            
				    String res = br.readLine();
				    Log.i("TESTE","token_face: "+res);
					PreferenceUtil.setPreferences(c, "TOKEN_TWITTER", res);
        			Log.i("TESTE","token: "+ PreferenceUtil.getPreferences(c, "TOKEN_TWITTER"));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	private String getPhone(Context c) {
		TelephonyManager tMgr = (TelephonyManager) c
				.getSystemService(Context.TELEPHONY_SERVICE);
		String telefone = tMgr.getLine1Number();

		if ((telefone == null) || (telefone.equals("")))
			telefone = "123";

		return telefone;
	}

}
