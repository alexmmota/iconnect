package br.com.socialcoreo;

import br.com.util.PreferenceUtil;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends Activity {

	Button btLogin, btCadastro;
	EditText etLogin, etSenha;
	CheckBox c1;
	String login, senha;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_activity);
		
		if(verificarUsuarioAtivo()){
			Intent it = new Intent(Login.this, Main.class);
			startActivity(it);
			finish();
		}
		
		btLogin = (Button)findViewById(R.id.btLogin);
		btCadastro = (Button)findViewById(R.id.btCadastro);
		etLogin = (EditText)findViewById(R.id.etLogin);
		etSenha = (EditText)findViewById(R.id.etSenha);
		c1 = (CheckBox)findViewById(R.id.checkBox1);
		
		btLogin.setOnClickListener(new View.OnClickListener(){
			public void onClick(View v){
				
				login = etLogin.getText().toString();
				senha = etSenha.getText().toString();
				
				if(validaUsuario()){
					Intent it = new Intent(Login.this, Main.class);
					startActivity(it);
					finish();
				}
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
	
	public boolean validaUsuario(){
		
		String check = PreferenceUtil.getPreferences(Login.this, "f/" + login.trim() + "/" + senha.trim());
		if(check == null){
			Toast.makeText(Login.this, "Usuário ou Senha Inválidos!", Toast.LENGTH_LONG).show();
			etSenha.setText("");
			return false;
		}
		
		return true;
	}

	public boolean verificarUsuarioAtivo(){
		String check = PreferenceUtil.getPreferences(Login.this, "UsuarioAtivo");
		
		if(check == null){
			return false;
		}
		
		if(c1.isChecked()){
			PreferenceUtil.setPreferences(Login.this, "UsuarioAtivo", login + "/" + senha);
		}else{
			PreferenceUtil.setPreferences(Login.this, "UsuarioAtivo", "");
		}
		
		return true;
	}
	
}
