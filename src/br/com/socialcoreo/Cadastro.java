package br.com.socialcoreo;

import br.com.util.PreferenceUtil;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Cadastro extends Activity {

	Button btCadastro, btVoltar;
	EditText etUsuario, etSenha, etSenha2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cadastro_activity);
		
		btCadastro = (Button)findViewById(R.id.btCadastro);
		btVoltar   = (Button)findViewById(R.id.btVoltar);
		etUsuario  = (EditText)findViewById(R.id.etUsuario);
		etSenha    = (EditText)findViewById(R.id.etSenha);
		etSenha2   = (EditText)findViewById(R.id.etSenha2);
		
		btCadastro.setOnClickListener(new View.OnClickListener(){
			public void onClick(View v){
				if(validaCampos(etUsuario, etSenha, etSenha2)){
					PreferenceUtil.setPreferences(Cadastro.this, "f/" + etUsuario.getText().toString().trim() + "/" + etSenha.getText().toString().trim(), "");
					PreferenceUtil.setPreferences(Cadastro.this, "t/" + etUsuario.getText().toString().trim() + "/" + etSenha.getText().toString().trim(), "");
					Toast.makeText(Cadastro.this, "Usuário cadastrado com sucesso.", Toast.LENGTH_LONG).show();
					
					Intent it = new Intent(Cadastro.this, Main.class);
					startActivity(it);
					finish();
				}
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
		//getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}
	
	public boolean validaCampos(EditText x, EditText y, EditText z){
		
		if(x.getText().toString().length() < 4){
			Toast.makeText(Cadastro.this, "Usuário deve ter pelo menos 4 caracteres.", Toast.LENGTH_LONG).show();
		}else if(y.getText().toString().length() < 4){
			Toast.makeText(Cadastro.this, "Senha deve ter pelo menos 4 caracteres.", Toast.LENGTH_LONG).show();
		}else if(!y.getText().toString().equals(z.getText().toString())){
			Toast.makeText(Cadastro.this, "As senhas não correspondem.", Toast.LENGTH_LONG).show();
			z.setText("");
		}else{
			return true;
		}
		
		return false;
	}

}
