package br.com.Dialog;

import br.com.socialcoreo.R;
import br.com.util.SendSMS;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

public class DialogFacebook {
	
	private Dialog dialog;
	private EditText etPost;
	private Button btCancelar, btPublicar;
	
	public DialogFacebook(final Context c){
		dialog = new Dialog(c);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.dialog_facebook);
		dialog.setCanceledOnTouchOutside(true);
		dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
		
		etPost = (EditText)dialog.findViewById(R.id.etPost);		
		btCancelar = (Button)dialog.findViewById(R.id.btCancelar);
		btPublicar = (Button)dialog.findViewById(R.id.btPublicar);
		
		btCancelar.setOnClickListener(new View.OnClickListener(){
			public void onClick(View v){
				dialog.cancel();
			}
		});
		
		btPublicar.setOnClickListener(new View.OnClickListener(){
			public void onClick(View v){
				SendSMS.sendSMSFacebook(etPost.getText().toString(), c);
				dialog.cancel();
			}
		});
		
		dialog.show();
	}
	
}
