package com.grysta.Luyin;

import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.util.Date;

public class Shake extends Service implements SensorEventListener
{
	private SensorManager sensorManager = null;
	private SensorEventListener sel=null;
	@Override
	public IBinder onBind(Intent intent)
	{
		return null;
	}

	@Override
	public void onCreate()
	{
		super.onCreate();
		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
	}

	@Override
	public void onDestroy()
	{
		sensorManager = null;
		sel=null;
		super.onDestroy();
	}

	void mrStop(MediaRecorder mediaRecorder){
		mediaRecorder.stop();
		mediaRecorder.release();
		mediaRecorder = null;
	}

	@Override
	public void onSensorChanged(SensorEvent event)
	{
		boolean recording;
		recording=false;
		MediaRecorder mediaRecorder = null;
		if (event.sensor.getType()==Sensor.TYPE_ACCELEROMETER)
		{
			if (Math.abs(event.values[0]) > 20 || Math.abs(event.values[1]) > 20 || Math.abs(event.values[2]) > 20)
				if(!recording)
				{
					Toast.makeText(Shake.this, "录制开始", Toast.LENGTH_SHORT).show();
					sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION)
							,SensorManager.SENSOR_DELAY_UI);
					mediaRecorder=new MediaRecorder();
					settings(mediaRecorder);
					recording=true;
				}
				else
				{

					mrStop(mediaRecorder);
					sensorManager.unregisterListener(this);
					recording = false;
				}
		}
	}

	void settings(MediaRecorder mediaRecorder){
		mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_WB);
		mediaRecorder.setOutputFile(Environment.getExternalStorageDirectory() + "/" + new Date() + ".amr");
		mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_WB);
		mediaRecorder.start();
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy)
	{
		//我也不知道该写啥
	}
}