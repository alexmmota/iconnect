package br.com.Dialog;

import br.com.socialcoreo.R;
import br.com.util.PreferenceUtil;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

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
			public void onClick(View v){
				String user = PreferenceUtil.getPreferences(c, "USER");
				String preferenceOld = PreferenceUtil.getPreferences(c, "FEEDBACK");
				String preference = "";
				if(preferenceOld == null){
					preference = user.concat("<|>").concat(etEnviar.getText().toString());
				}else{
					preference = preferenceOld.concat("<||>").concat(user).concat("<|>").concat(etEnviar.getText().toString());					
				}
				PreferenceUtil.setPreferences(c, "FEEDBACK", preference);
				dialog.cancel();
			}
		});
		
		dialog.show();
	}
	
}
