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
	CheckBox ckConectado;
	String login, senha;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_activity);
		
		if(verificarUsuarioAtivo()){
			Intent it = new Intent(Login.this, Main.class);
			startActivity(it);
			overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
			Login.this.finish();
			finish();
		}
		
		if(verificarPrimeiroAcesso()){
			PreferenceUtil.setPreferences(Login.this, "PrimeiroAcesso", "");
			Intent it = new Intent(Login.this, Cadastro.class);
			startActivity(it);
			overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
			Login.this.finish();
			finish();
		}
		
		btLogin = (Button)findViewById(R.id.btLogin);
		btCadastro = (Button)findViewById(R.id.btCadastro);
		etLogin = (EditText)findViewById(R.id.etLogin);
		etSenha = (EditText)findViewById(R.id.etSenha);
		ckConectado = (CheckBox)findViewById(R.id.checkBox1);
		
		btLogin.setOnClickListener(new View.OnClickListener(){
			public void onClick(View v){
				
				login = etLogin.getText().toString();
				senha = etSenha.getText().toString();
				
				if(validaUsuario()){
					
					PreferenceUtil.setPreferences(Login.this, "USER", etLogin.getText().toString());
					
					Intent it = new Intent(Login.this, Main.class);
					startActivity(it);
					overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
					finish();
				}
			}
		});
		
		btCadastro.setOnClickListener(new View.OnClickListener(){
			public void onClick(View v){
				Intent it = new Intent(Login.this, Cadastro.class);
				startActivity(it);
				overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
				finish();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return true;
	}
	
	public boolean validaUsuario(){
		
		String check = PreferenceUtil.getPreferences(Login.this, "f/"+login.trim() + "/" + senha.trim());
		if(check == null){
			Toast.makeText(Login.this, getResources().getString(R.string.log_act_mess), Toast.LENGTH_LONG).show();
			etSenha.setText("");
			return false;
		}
				
		if(ckConectado.isChecked()){
			PreferenceUtil.setPreferences(Login.this, "UsuarioAtivo", login.trim()+ "/" + senha.trim());
		}else{
			PreferenceUtil.setPreferences(Login.this, "UsuarioAtivo", null);
		}
		
		return true;
	}

	public boolean verificarUsuarioAtivo(){
		String check = PreferenceUtil.getPreferences(Login.this, "UsuarioAtivo");
		
		if(check == null){
			return false;
		}
		
		return true;
	}
	
	public boolean verificarPrimeiroAcesso(){
		String check = PreferenceUtil.getPreferences(Login.this, "PrimeiroAcesso");
		
		if(check == null){
			return true;
		}
		
		return false;
	}
	
}
