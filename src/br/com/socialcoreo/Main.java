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
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class Main extends Activity {

	static String CONSUMER_KEY = "lQYDGR2WVgIDR3epKKxPkg";
	static String CONSUMER_SECRET = "5vAdc8vBHARfOK0ZGXSFE8zniGIbGtIdkE8nVttVwo";
	static String PREFERENCE_NAME = "twitter_oauth";
	static final String PREF_KEY_SECRET = "oauth_token_secret";
	static final String PREF_KEY_TOKEN = "oauth_token";
	static final String CALLBACK_URL = "oauth://t4jsample";
	static final String IEXTRA_AUTH_URL = "auth_url";
	static final String IEXTRA_OAUTH_VERIFIER = "oauth_verifier";
	static final String IEXTRA_OAUTH_TOKEN = "oauth_token";

	private static Twitter twitter;
	private static RequestToken requestToken;
	private ImageButton btExit;
	private LinearLayout btFacebook, btTwitter, btEmail, btAjuda;
	private boolean flagDialog = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);

		btFacebook = (LinearLayout) findViewById(R.id.btFacebook);
		btTwitter = (LinearLayout) findViewById(R.id.btTwitter);
		btEmail = (LinearLayout) findViewById(R.id.btEmail);
		btAjuda = (LinearLayout) findViewById(R.id.btAjuda);
		btExit = (ImageButton) findViewById(R.id.btExit);

		Log.i("TESTE","token: "+ PreferenceUtil.getPreferences(this, "TOKEN_FACEBOOK"));

		btExit.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				//VERIFICAR USUARIO ATIVO PARA DAR OP��O DE LOGOFF SE N�O TIVER USUARIO ATIVO, SIMPLESMENTE FECHA
				finish();
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
						accessToken.getToken(), accessToken.getTokenSecret());

				Log.i("TOKEN_TWITTER", "twitter " + accessToken.getToken());
				Log.i("TOKEN_TWITTER", "secret " + accessToken.getTokenSecret());
			} catch (Exception e) {}
		}

	}

	@Override
	protected void onResume() {
		super.onResume();
		if (flagDialog) {
			new DialogFacebook(Main.this);
			flagDialog = false;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	private void autenticaFacebook() {	
		if (PreferenceUtil.getPreferences(this, "TOKEN_FACEBOOK").equals("")) {
			if(isConnected()){
				flagDialog = true;
				Intent it = new Intent(Main.this, FacebookAuth.class);
				startActivity(it);				
			}
		} else {
			new DialogFacebook(Main.this);
		}
	}

	private void autenticaTwitter() {
		if (PreferenceUtil.getPreferences(this, "TOKEN_TWITTER").equals("")) {
			if(isConnected()){
				ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
				configurationBuilder.setOAuthConsumerKey(CONSUMER_KEY);
				configurationBuilder.setOAuthConsumerSecret(CONSUMER_SECRET);
				Configuration configuration = configurationBuilder.build();
				twitter = new TwitterFactory(configuration).getInstance();
		
				try {
					requestToken = twitter.getOAuthRequestToken(CALLBACK_URL);
					this.startActivityForResult(new Intent(Intent.ACTION_VIEW, Uri.parse(requestToken.getAuthenticationURL())), 1);
				} catch (TwitterException e) {
					e.printStackTrace();
				}
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

		new AlertDialog.Builder(this).setTitle("Atenção")
		.setMessage("Para primeiro acesso é necessário estar conectado à internet.")
		.setNeutralButton("Fechar", null)
		.show();
		return false;
	}
}
