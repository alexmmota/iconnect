package br.com.socialcoreo;

import java.util.Arrays;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;
import br.com.socialcoreo.R;
import br.com.util.ClientREST;
import br.com.util.PreferenceUtil;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;

public class FacebookAuth extends Activity {
	private UiLifecycleHelper uiHelper;
	private WebView wb1;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.facebook_auth);
		uiHelper = new UiLifecycleHelper(this, callback);
		uiHelper.onCreate(savedInstanceState);
		String data = "<html><body><p style='text-align:justify'>Acesse sua conta do Facebook e autorize o aplicativo IConnect a publicar no seu mural, para que você possa publicar sem acesso à internet.</body></html>";
		wb1 = (WebView)findViewById(R.id.webView1);
		wb1.loadDataWithBaseURL("", data, "text/html", "UTF-8", "");
		
		LoginButton authButton = (LoginButton) findViewById(R.id.authButton);
		authButton.setPublishPermissions(Arrays.asList("publish_actions", "publish_stream"));
	}

	private Session.StatusCallback callback = new Session.StatusCallback() {
		@Override
		public void call(Session session, SessionState state, Exception exception) {
			onSessionStateChange(session, state, exception);
		}		
	};

	private void onSessionStateChange(Session session, SessionState state, Exception exception) {
		if (state.isOpened()) {
			finish();
		} else if (state.isClosed()) {
			Log.i("TAG", "Logged out...");
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		Session session = Session.getActiveSession();
		if (session != null && (session.isOpened() || session.isClosed())) {
			onSessionStateChange(session, session.getState(), null);
		}
		uiHelper.onResume();
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		uiHelper.onActivityResult(requestCode, resultCode, data);
		
		Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
        if (Session.getActiveSession().isOpened()) {
            Request.executeMeRequestAsync(Session.getActiveSession(), new Request.GraphUserCallback() {

                @Override
                public void onCompleted(GraphUser user, Response response) {
                    if (user != null) {
                    	String accessToken = Session.getActiveSession().getAccessToken();
                    	ClientREST.getInstance().callWebServiceFace(getApplicationContext(), accessToken, user.getId());                    	
                    }
                }
            });
        }
	}

	@Override
	public void onPause() {
		super.onPause();
		uiHelper.onPause();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		uiHelper.onDestroy();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		uiHelper.onSaveInstanceState(outState);
	}
	
	public void disconnect(){
		Session.getActiveSession().closeAndClearTokenInformation();
		PreferenceUtil.setPreferences(FacebookAuth.this, "TOKEN_FACEBOOK", "");
	}

}
