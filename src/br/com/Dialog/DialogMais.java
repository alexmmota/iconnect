package br.com.Dialog;

import br.com.socialcoreo.Login;
import br.com.socialcoreo.R;
import br.com.util.PreferenceUtil;
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
	LinearLayout bt1, bt2, bt3;
	CheckBox cb1;

	@SuppressLint("SetJavaScriptEnabled")
	public DialogMais(final Context c){
		dialog = new Dialog(c);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.dialog_mais);
		dialog.setCanceledOnTouchOutside(true);
		dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
			
		bt1 = (LinearLayout)dialog.findViewById(R.id.btDesatFacebook);
		bt2 = (LinearLayout)dialog.findViewById(R.id.btDesatTwitter);
		bt3 = (LinearLayout)dialog.findViewById(R.id.btLogoff);
		cb1 = (CheckBox)dialog.findViewById(R.id.checkBox1);
		
		bt1.setOnClickListener(new View.OnClickListener(){
			public void onClick(View v){
				new AlertDialog.Builder(c)
		           .setMessage("Deseja cancelar as publicações no seu Facebook pelo Iconnect?")
		           .setCancelable(false)
		           .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
		               public void onClick(DialogInterface dialog, int id) {
		                    
		               }
		           })
		           .setNegativeButton("Não", null)
		           .show();
			}
		});

		bt2.setOnClickListener(new View.OnClickListener(){
			public void onClick(View v){
				new AlertDialog.Builder(c)
		           .setMessage("Deseja cancelar as publicações no seu Twitter pelo Iconnect?")
		           .setCancelable(false)
		           .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
		               public void onClick(DialogInterface dialog, int id) {
		                    
		               }
		           })
		           .setNegativeButton("Não", null)
		           .show();
			}
		});

		bt3.setOnClickListener(new View.OnClickListener(){
			public void onClick(View v){
				new AlertDialog.Builder(c)
		           .setMessage("Deseja mesmo sair?")
		           .setCancelable(false)
		           .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
		               public void onClick(DialogInterface dialog, int id) {
		            	   	PreferenceUtil.setPreferences(c, "UsuarioAtivo", null);
		                    ((Activity)c).finish();
		               }
		           })
		           .setNegativeButton("Não", null)
		           .show();
			}
		});
		
		dialog.show();
	}
	
}
