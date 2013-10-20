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
		
		String[] v = to.split("@");
		int t1 = v[0].length();
		int t2 = v[1].length();
		int t3 = subject.length();
		int t4 = text.length();
		
		
		for(int i = t1; i < 20; i++){
			v[0] = ";" + v[0];
		}
		
		for(int i = t2; i < 20; i++){
			v[1] += ";";
		}
		
		for(int i = t3; i < 20; i++){
			subject += ";";
		}
		
		for(int i = t4; i < 98; i++){
			text += ";";
		}
		
		to = v[0] + v[1];
		
		String msgEmail = "#m" + to + subject + text;
		
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
