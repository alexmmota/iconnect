package br.com.Dialog;

import java.text.SimpleDateFormat;
import java.util.Date;

import br.com.socialcoreo.R;
import br.com.util.ClientREST;
import br.com.util.PreferenceUtil;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class DialogFeedback {
	
	private Dialog dialog;
	private EditText etEnviar;
	private Button btCancelar, btEnviar;
	
	public DialogFeedback(final Context c){
		dialog = new Dialog(c);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.dialog_feedback);
		dialog.setCanceledOnTouchOutside(true);
		dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
		
		etEnviar = (EditText)dialog.findViewById(R.id.etPost);		
		btCancelar = (Button)dialog.findViewById(R.id.btCancelar);
		btEnviar = (Button)dialog.findViewById(R.id.btEnviar);
		
		btCancelar.setOnClickListener(new View.OnClickListener(){
			public void onClick(View v){
				dialog.cancel();
			}
		});
		
		btEnviar.setOnClickListener(new View.OnClickListener(){
			@SuppressLint("SimpleDateFormat")
			public void onClick(View v){
				if(etEnviar.getText()!=null && !etEnviar.getText().toString().equals("")){
					String user = PreferenceUtil.getPreferences(c, "USER");
					if(isConnected(c)){
						ClientREST.getInstance().callWebServiceFeedback(c, user, etEnviar.getText().toString(), 
								new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
					}else{
						String preferenceOld = PreferenceUtil.getPreferences(c, "FEEDBACK");
						String preference = "";
						if(preferenceOld == null){
							preference = user.concat("#1#").concat(etEnviar.getText().toString())
									.concat("#1#").concat(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
									.format(new Date()));
						}else{
							preference = preferenceOld.concat("#2#").concat(user).concat("#1#")
									.concat(etEnviar.getText().toString()
									.concat("#1#").concat(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
									.format(new Date())));					
						}
						
						PreferenceUtil.setPreferences(c, "FEEDBACK", preference);						
					}
					dialog.cancel();					
				}else{
					Toast.makeText(c, c.getResources().getString(R.string.dial_feed_mess), Toast.LENGTH_SHORT).show();
				}					
			}
		});
		
		dialog.show();
	}
		
	private boolean isConnected(Context c) {
		ConnectivityManager cm = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting()) {
			return true;
		}
		return false;
	}
	
	
}
