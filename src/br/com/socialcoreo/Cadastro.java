package br.com.socialcoreo;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class Cadastro extends Activity {

	Button btCadastro, btVoltar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cadastro_activity);
		
		btCadastro = (Button)findViewById(R.id.btCadastro);
		btVoltar = (Button)findViewById(R.id.btVoltar);
		
		btCadastro.setOnClickListener(new View.OnClickListener(){
			public void onClick(View v){
				
			}
		});
		
		btVoltar.setOnClickListener(new View.OnClickListener(){
			public void onClick(View v){
				finish();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

}
