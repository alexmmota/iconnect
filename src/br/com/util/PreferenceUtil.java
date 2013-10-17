package br.com.util;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceUtil {
	
	public static void setPreferences(Context context, String chave, String valor){
		SharedPreferences settings = context.getSharedPreferences("IConnectPreferences", 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString(chave, valor);
		editor.commit();
	}
	
	public static String getPreferences(Context context, String chave){
		SharedPreferences settings = context.getSharedPreferences("IConnectPreferences", 0);
		return settings.getString(chave, "");
	}
}
