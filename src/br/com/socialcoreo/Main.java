package br.com.socialcoreo;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;
import br.com.Dialog.DialogAjuda;
import br.com.Dialog.DialogFacebook;
import br.com.Dialog.DialogMail;
import br.com.Dialog.DialogMais;
import br.com.Dialog.DialogTwitter;
import br.com.util.ClientREST;
import br.com.util.PreferenceUtil;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class Main extends Activity {

	static String CONSUMER_KEY = "wLPBthXbNd6Dr79tGOhZQ";
	static String CONSUMER_SECRET = "5yiSvOEFKraZBL9jtKIpYbWZ6sXWeftGmC0c8nmjRg";
	static String PREFERENCE_NAME = "twitter_oauth";
	static final String PREF_KEY_SECRET = "oauth_token_secret";
	static final String PREF_KEY_TOKEN = "oauth_token";
	static final String CALLBACK_URL = "oauth://t4jsample";
	static final String IEXTRA_AUTH_URL = "auth_url";
	static final String IEXTRA_OAUTH_VERIFIER = "oauth_verifier";
	static final String IEXTRA_OAUTH_TOKEN = "oauth_token";

	private String user;
	private boolean flagFacebook = false;
	private boolean flagTwitter;
	private static Twitter twitter;
	private static RequestToken requestToken;
	private ImageButton btExit;
	private LinearLayout btFacebook, btTwitter, btEmail, btAjuda;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);
		
		this.enviaFeedback();

		btFacebook = (LinearLayout) findViewById(R.id.btFacebook);
		btTwitter = (LinearLayout) findViewById(R.id.btTwitter);
		btEmail = (LinearLayout) findViewById(R.id.btEmail);
		btAjuda = (LinearLayout) findViewById(R.id.btAjuda);
		btExit = (ImageButton) findViewById(R.id.btExit);

		user = PreferenceUtil.getPreferences(Main.this, "USER");
		
		btExit.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				new DialogMais(Main.this);
			}
		});

		btFacebook.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				autenticaFacebook();
			}
		});

		btTwitter.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				autenticaTwitter();
			}
		});

		btEmail.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				new DialogMail(Main.this);
			}
		});

		btAjuda.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				new DialogAjuda(Main.this);
			}
		});

		Uri uri = getIntent().getData();
		if (uri != null && uri.toString().startsWith(CALLBACK_URL)) {
			String verifier = uri.getQueryParameter(IEXTRA_OAUTH_VERIFIER);
			try {
				AccessToken accessToken = twitter.getOAuthAccessToken(requestToken, verifier);
				
				ClientREST.getInstance().callWebServiceTwitter(getApplicationContext(), 
						accessToken.getToken(), accessToken.getTokenSecret(), ""+accessToken.getUserId());

				Log.i("TOKEN_TWITTER", "twitter " + accessToken.getToken());
				Log.i("TOKEN_TWITTER", "secret " + accessToken.getTokenSecret());
			} catch (Exception e) {}
		}

	}

	private void enviaFeedback(){
		if(isConnected() && PreferenceUtil.getPreferences(this, "FEEDBACK") != null && (!PreferenceUtil.getPreferences(this, "FEEDBACK").equals(""))){
			String[] feeds = PreferenceUtil.getPreferences(this, "FEEDBACK").split("#2#");
			for(int i=0; i<feeds.length; i++){
				String values[] = feeds[i].split("#1#");
				ClientREST.getInstance().callWebServiceFeedback(this, values[0], values[1], values[2]);	
			}
			PreferenceUtil.setPreferences(this, "FEEDBACK", null);
		}
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		if ((PreferenceUtil.getPreferences(this, user+"TOKEN_FACEBOOK") != null)&&(flagFacebook)) {
			flagFacebook = false;
			new DialogFacebook(Main.this);
		}
		if ((PreferenceUtil.getPreferences(this, user+"TOKEN_TWITTER") != null)&&(flagTwitter)) {
			flagTwitter = false;
			new DialogTwitter(Main.this);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    @SuppressWarnings("unused")
		MenuInflater inflater = getMenuInflater();
	    return super.onCreateOptionsMenu(menu);
	}

	private void autenticaFacebook() {
		Log.i("TOKEN","face: "+PreferenceUtil.getPreferences(this, user+"TOKEN_FACEBOOK"));
		if (PreferenceUtil.getPreferences(this, user+"TOKEN_FACEBOOK") == null) {
			if(isConnected()){
				flagFacebook = true;
				Intent it = new Intent(Main.this, FacebookAuth.class);
				startActivity(it);
			}else{
				new AlertDialog.Builder(this).setTitle("Atenção")
				.setMessage(getResources().getString(R.string.main_act_mess))
				.setNeutralButton("Fechar", null)
				.show();				
			}
		} else {
			new DialogFacebook(Main.this);
		}
	}

	private void autenticaTwitter() {
		if (PreferenceUtil.getPreferences(this, user+"TOKEN_TWITTER") == null) {
			if(isConnected()){
				flagTwitter = true;
				ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
				configurationBuilder.setOAuthConsumerKey(CONSUMER_KEY);
				configurationBuilder.setOAuthConsumerSecret(CONSUMER_SECRET);
				Configuration configuration = configurationBuilder.build();
				twitter = new TwitterFactory(configuration).getInstance();
		
				new Thread(new Runnable() {
					public void run() {
						try {
							requestToken = twitter.getOAuthRequestToken(CALLBACK_URL);
							startActivityForResult(new Intent(Intent.ACTION_VIEW, Uri.parse(requestToken.getAuthenticationURL())), 1);							
						} catch (TwitterException e) {
							e.printStackTrace();
						}						
					}
				}).start();
			}else{
				new AlertDialog.Builder(this).setTitle("Atenção")
				.setMessage(getResources().getString(R.string.main_act_mess))
				.setNeutralButton("Fechar", null)
				.show();		
			}
		} else {
			new DialogTwitter(Main.this);
		}
	}

	private boolean isConnected() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting()) {
			return true;
		}

		return false;
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    
	}
}
