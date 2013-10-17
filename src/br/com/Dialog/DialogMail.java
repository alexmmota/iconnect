package br.com.Dialog;

import br.com.socialcoreo.R;
import br.com.util.SendSMS;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

public class DialogMail {
	
	Dialog dialog;
	EditText etDestinatario, etAssunto, etMensagem;
	Button btCancelar, btEnviar;
	
	public DialogMail(final Context c){
		dialog = new Dialog(c);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.dialog_mail);
		dialog.setCanceledOnTouchOutside(true);
		dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
		

		etDestinatario = (EditText)dialog.findViewById(R.id.etDestinatario);
		etAssunto = (EditText)dialog.findViewById(R.id.etAssunto);
		etMensagem = (EditText)dialog.findViewById(R.id.etMensagem);
		
		btCancelar = (Button)dialog.findViewById(R.id.btCancelar);
		btEnviar = (Button)dialog.findViewById(R.id.btEnviar);
		
		btCancelar.setOnClickListener(new View.OnClickListener(){
			public void onClick(View v){
				dialog.cancel();
			}
		});
		
		btEnviar.setOnClickListener(new View.OnClickListener(){
			public void onClick(View v){
				String to 		= etDestinatario.getText().toString();
				String subject 	= etAssunto.getText().toString();
				String text		= etMensagem.getText().toString();
				SendSMS.sendSMSEmail(to, subject, text);
			}
		});
		
		dialog.show();
	}
	
}
