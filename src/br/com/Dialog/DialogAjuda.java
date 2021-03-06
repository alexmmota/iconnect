package br.com.Dialog;

import br.com.socialcoreo.R;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.webkit.WebView;

public class DialogAjuda {
	
	Dialog dialog;
	WebView webView;

	@SuppressLint("SetJavaScriptEnabled")
	public DialogAjuda(final Context c){
		dialog = new Dialog(c);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.dialog_ajuda);
		dialog.setCanceledOnTouchOutside(true);
		dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
		
		String url = c.getResources().getString(R.string.html_help_file);
		
		webView = (WebView) dialog.findViewById(R.id.webView1);
		webView.loadUrl(url);
		
		dialog.show();
	}
	
}
