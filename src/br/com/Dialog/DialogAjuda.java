package br.com.Dialog;

import br.com.socialcoreo.R;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;

public class DialogAjuda {
	
	Dialog dialog;
	WebView webView;

	public DialogAjuda(final Context c){
		dialog = new Dialog(c);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.dialog_ajuda);
		dialog.setCanceledOnTouchOutside(true);
		dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
		
		webView = (WebView) dialog.findViewById(R.id.webView1);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.loadUrl("file:///android_asset/index.html");
		
		dialog.show();
	}
	
}
