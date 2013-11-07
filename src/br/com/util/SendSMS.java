package br.com.util;

import android.content.Context;
import android.telephony.SmsManager;
import android.widget.Toast;

public class SendSMS {
	private static String numCoreo = "3497980287";

	public static void sendSMSFacebook(String text, Context c){
		String token = PreferenceUtil.getPreferences(c, "TOKEN_FACEBOOK");
		String msgSMS = "#f"+token+text;
		send(msgSMS);
	}
	
	public static boolean sendSMSEmail(String to, String fromName, String subject, String text, Context c){		
		String[] toAux = to.split("@");
		if(toAux.length < 2){
			Toast.makeText(c, "Informe um email valido para o destinatário", Toast.LENGTH_SHORT).show();
			return false;
		}else{
			String toUser = toAux[0];
			String toDomain = toAux[1];
	
			String msgEmail = "#m" + toUser + "/" + toDomain + "/" + fromName + "/" + subject + "/" + text;
			send(msgEmail);
		}
		return true;
	}
	
	public static void sendSMSTwitter(String text, Context c){
		String token = PreferenceUtil.getPreferences(c, "TOKEN_TWITTER");		
		String msgSMS = "#t"+token+text;
		send(msgSMS);
	}

	private static void send(String sms){
		SmsManager smsManager = SmsManager.getDefault();
		smsManager.sendTextMessage(numCoreo, null, sms, null, null);
	}
	
}
