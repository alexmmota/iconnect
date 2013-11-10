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

public class Cadastro extends Activity {

	Button btCadastro, btVoltar;
	EditText etUsuario, etSenha, etSenha2;
	CheckBox ckManter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cadastro_activity);
		
		btCadastro = (Button)findViewById(R.id.btCadastro);
		btVoltar   = (Button)findViewById(R.id.btVoltar);
		etUsuario  = (EditText)findViewById(R.id.etUsuario);
		etSenha    = (EditText)findViewById(R.id.etSenha);
		etSenha2   = (EditText)findViewById(R.id.etSenha2);
		ckManter   = (CheckBox)findViewById(R.id.ckMaterCon);
		
		btCadastro.setOnClickListener(new View.OnClickListener(){
			public void onClick(View v){
				if(validaCampos(etUsuario, etSenha, etSenha2)){
					PreferenceUtil.setPreferences(Cadastro.this, "f/" + etUsuario.getText().toString().trim() + "/" + etSenha.getText().toString().trim(), "");
					PreferenceUtil.setPreferences(Cadastro.this, "t/" + etUsuario.getText().toString().trim() + "/" + etSenha.getText().toString().trim(), "");
					PreferenceUtil.setPreferences(Cadastro.this, "USER", etUsuario.getText().toString());

					if(ckManter.isChecked()){
						PreferenceUtil.setPreferences(Cadastro.this, "UsuarioAtivo", etUsuario.getText().toString()+ "/" + etSenha.getText().toString());
					}else{
						PreferenceUtil.setPreferences(Cadastro.this, "UsuarioAtivo", null);
					}
					
					
					Intent it = new Intent(Cadastro.this, Main.class);
					startActivity(it);
					
					finish();
				}
			}
		});
		
		btVoltar.setOnClickListener(new View.OnClickListener(){
			public void onClick(View v){
				Intent it = new Intent(Cadastro.this, Login.class);
				startActivity(it);
				
				finish();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return true;
	}
	
	public boolean validaCampos(EditText x, EditText y, EditText z){
		
		if(x.getText().toString().length() < 4){
			Toast.makeText(Cadastro.this, getResources().getString(R.string.cad_act_mess1), Toast.LENGTH_LONG).show();
		}else if(y.getText().toString().length() < 4){
			Toast.makeText(Cadastro.this, getResources().getString(R.string.cad_act_mess2), Toast.LENGTH_LONG).show();
		}else if(!y.getText().toString().equals(z.getText().toString())){
			Toast.makeText(Cadastro.this, getResources().getString(R.string.cad_act_mess3), Toast.LENGTH_LONG).show();
			z.setText("");
		}else{
			return true;
		}
		
		return false;
	}

}
