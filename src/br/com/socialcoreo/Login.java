package br.com.socialcoreo;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Login extends Activity {

	Button btLogin, btCadastro;
	EditText etLogin, etSenha;
	String login, senha;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_activity);
		
		btLogin = (Button)findViewById(R.id.btLogin);
		btCadastro = (Button)findViewById(R.id.btCadastro);
		etLogin = (EditText)findViewById(R.id.etLogin);
		etSenha = (EditText)findViewById(R.id.etSenha);
		
		btLogin.setOnClickListener(new View.OnClickListener(){
			public void onClick(View v){
				
				login = etLogin.getText().toString();
				senha = etSenha.getText().toString();
				
				//validaUsuario();
				
				Intent it = new Intent(Login.this, Main.class);
				startActivity(it);
				finish();
			}
		});
		
		btCadastro.setOnClickListener(new View.OnClickListener(){
			public void onClick(View v){
				Intent it = new Intent(Login.this, Cadastro.class);
				startActivity(it);
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
