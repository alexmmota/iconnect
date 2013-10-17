package br.com.util;

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
	private String URL_REST_FACE = "http://br-com-iconnected.herokuapp.com/facebook/savetoken/";
	private String URL_REST_TWITTER = "http://br-com-iconnected.herokuapp.com/twitter/savetoken/";
	
	private ClientREST(){		
	}

	public static ClientREST getInstance(){
		if(clientREST == null)
			clientREST = new ClientREST();
		return clientREST;
	}
	
	public void callWebServiceFace(final Context c, String accessToken, String user){
		String telefone = getPhone(c);
    	localContext = new BasicHttpContext();
    	client = new DefaultHttpClient();
    	get = new HttpGet(URL_REST_FACE+accessToken+"/"+user+"/"+telefone);
    	get.setHeader("Content-type", "text/plain");
		
    	new Thread(new Runnable(){
			    @Override
			    public void run() {
			        try {
						responseHttp = client.execute(get,localContext);
                    	String responseBody = EntityUtils.toString(responseHttp.getEntity());
                		Log.i("TESTE","token_face: "+responseBody);
            			PreferenceUtil.setPreferences(c, "TOKEN_FACEBOOK", responseBody);
			        } catch (Exception e) {
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
