package br.com.util;

import android.content.Context;
import android.telephony.SmsManager;

public class SendSMS {
	private static String numCoreo = "3497980287";

	public static void sendSMSFacebook(String text, Context c){		
		String token = PreferenceUtil.getPreferences(c, "TOKEN_FACEBOOK");
		String msgSMS = "#f"+token+text;
		send(msgSMS);
	}
	
	public static void sendSMSEmail(String to, String subject, String text){
		String msgEmail = "post:/"+to+"/"+subject+"/"+text;
		send(msgEmail);
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
