package br.com.Dialog;

import br.com.socialcoreo.R;
import br.com.util.PreferenceUtil;
import br.com.util.SendSMS;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.LinearLayout;

public class DialogMais {
	
	Dialog dialog;
	LinearLayout btDesativaFace, btDesativaTwitter, btLogoff, btFeedback;
	CheckBox cb1;

	@SuppressLint("SetJavaScriptEnabled")
	public DialogMais(final Context c){
		dialog = new Dialog(c);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.dialog_mais);
		dialog.setCanceledOnTouchOutside(true);
		dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
			
		btDesativaFace = (LinearLayout)dialog.findViewById(R.id.btDesatFacebook);
		btDesativaTwitter = (LinearLayout)dialog.findViewById(R.id.btDesatTwitter);
		btLogoff = (LinearLayout)dialog.findViewById(R.id.btLogoff);
		btFeedback = (LinearLayout) dialog.findViewById(R.id.btFeedback);
		cb1 = (CheckBox)dialog.findViewById(R.id.checkBox1);
		
		btDesativaFace.setOnClickListener(new View.OnClickListener(){
			public void onClick(View v){
				new AlertDialog.Builder(c)
		           .setMessage(c.getResources().getString(R.string.dial_mais_desat_face))
		           .setCancelable(false)
		           .setPositiveButton(c.getResources().getString(R.string.resp_positiva), new DialogInterface.OnClickListener() {
		               public void onClick(DialogInterface dialog, int id) {
		            	   SendSMS.sendSMSDesativar("facebook",PreferenceUtil.getPreferences(c, "TOKEN_FACEBOOK"));		                    
		               }
		           })
		           .setNegativeButton(c.getResources().getString(R.string.resp_negativa), null)
		           .show();
			}
		});

		btDesativaTwitter.setOnClickListener(new View.OnClickListener(){
			public void onClick(View v){
				new AlertDialog.Builder(c)
		           .setMessage(c.getResources().getString(R.string.dial_mais_desat_twt))
		           .setCancelable(false)
		           .setPositiveButton(c.getResources().getString(R.string.resp_positiva), new DialogInterface.OnClickListener() {
		               public void onClick(DialogInterface dialog, int id) {
		            	   SendSMS.sendSMSDesativar("twitter",PreferenceUtil.getPreferences(c, "TOKEN_TWITTER"));
		               }
		           })
		           .setNegativeButton(c.getResources().getString(R.string.resp_negativa), null)
		           .show();
			}
		});

		btLogoff.setOnClickListener(new View.OnClickListener(){
			public void onClick(View v){
				new AlertDialog.Builder(c)
		           .setMessage(c.getResources().getString(R.string.dial_mais_logoff))
		           .setCancelable(false)
		           .setPositiveButton(c.getResources().getString(R.string.resp_positiva), new DialogInterface.OnClickListener() {
		               public void onClick(DialogInterface dialog, int id) {
		            	   	PreferenceUtil.setPreferences(c, "UsuarioAtivo", null);
		                    ((Activity)c).finish();
		               }
		           })
		           .setNegativeButton(c.getResources().getString(R.string.resp_negativa), null)
		           .show();
			}
		});
		
		btFeedback.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				new DialogFeedback(c);
			}
		});
		
		dialog.show();
	}
	
}
