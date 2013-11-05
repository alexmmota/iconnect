package br.com.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import android.content.Context;
import android.telephony.TelephonyManager;
import android.util.Log;

public class ClientREST {
	private static ClientREST clientREST;
	private HttpContext localContext = null;
	private HttpClient client = null;
	private HttpGet get = null;
	private HttpResponse responseHttp = null;
	private String URL_REST_FACE = "https://br-com-iconnected.herokuapp.com/facebook/savetoken/";
	private String URL_REST_TWITTER = "https://br-com-iconnected.herokuapp.com/twitter/savetoken/";;

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
					URL url = new URL(URL_REST_FACE);
					HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
					urlConnection.setRequestMethod("POST");
					urlConnection.setDoOutput(true);
				    
				    OutputStream os = new BufferedOutputStream(urlConnection.getOutputStream());
				    PrintStream ps = new PrintStream(os);
				    ps.println("token:"+accessToken);
				    ps.println("userId:"+user);
				    ps.println("phone:"+telefone);
				    
				    InputStream is = new BufferedInputStream(urlConnection.getInputStream());
				    BufferedReader br = new BufferedReader(new InputStreamReader(is));
				    
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
	
	public void callWebServiceTwitter(final Context c, String accessToken, String secretToken){		
    	localContext = new BasicHttpContext();
    	client = new DefaultHttpClient();
    	get = new HttpGet(URL_REST_TWITTER+accessToken+"/"+secretToken);
    	get.setHeader("Content-type", "text/plain");
		
    	new Thread(new Runnable(){
			    @Override
			    public void run() {
			        try {
						responseHttp = client.execute(get,localContext);
                    	String responseBody = EntityUtils.toString(responseHttp.getEntity());
                		Log.i("TESTE","token_twitter: "+responseBody);
            			PreferenceUtil.setPreferences(c, "TOKEN_TWITTER", responseBody);
			        } catch (Exception e) {
			            e.printStackTrace();
			        }
			    }
			}).start();			
	}

	private String getPhone(Context c){
    	TelephonyManager tMgr =(TelephonyManager)c.getSystemService(Context.TELEPHONY_SERVICE);
    	String telefone = tMgr.getLine1Number();
    	
    	if((telefone == null)||(telefone.equals("")))
    		telefone = "123";
    	
    	return telefone;		
	}


}
