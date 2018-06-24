package com.grysta.Luyin;

import android.app.Service;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.io.IOException;
import java.util.Date;

public class Luyin extends Service
{
	private TelephonyManager telephonyManager = null;

	@Override
	public IBinder onBind(Intent intent)
	{
		return null;
	}
	@Override
	public void onCreate()
	{
		super.onCreate();
		telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
		telephonyManager.listen(new psl(), psl.LISTEN_CALL_STATE);
	}

	@Override
	public void onDestroy()
	{
		telephonyManager = null;
		super.onDestroy();
	}

	class psl extends PhoneStateListener
	{
		@Override
		public void onCallStateChanged(int state, String incomingNumber)
		{
			MediaRecorder mediaRecorder = null;
			switch (state)
			{
				case TelephonyManager.CALL_STATE_IDLE:
					if (mediaRecorder != null)
					{
						mrStop(mediaRecorder);
					}
					break;
				case TelephonyManager.CALL_STATE_OFFHOOK:
					Log.d("3", "通话 ");
					mediaRecorder = new MediaRecorder();
					luyinSettings(mediaRecorder);
					try
					{
						mediaRecorder.prepare();
						mediaRecorder.start();
					}
					catch (IOException e)
					{
						e.printStackTrace();
					}
					break;
			}
		}
	}

	void  luyinSettings(MediaRecorder mediaRecorder){
		mediaRecorder.setAudioSource(MediaRecorder.AudioSource.VOICE_CALL);
		mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_WB);
		mediaRecorder.setOutputFile(Environment.getExternalStorageDirectory() + "/" + new Date() + ".amr");
		mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_WB);
	}

	void mrStop(MediaRecorder mediaRecorder){
		mediaRecorder.stop();
		mediaRecorder.release();
		mediaRecorder = null;
	}
}