package com.example.recorder;

import java.io.IOException;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {
	private boolean recording;
	boolean recordedSound;
	private MediaRecorder m;
	private MediaPlayer player;
	private String inputFile;
	private Button recorder;
	private Button playerButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		recorder = (Button) findViewById(R.id.button1);
		playerButton = (Button) findViewById(R.id.button2);
		recorder.setText("Start Recording!");
		inputFile = Environment.getExternalStorageDirectory().getAbsolutePath() + "/recording.mp4";
		recording = false;
		recordedSound = false;
		
		recorder.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (recording) {
					stopRecording();
				} else {
					startRecording();	
				}
				
			}
		});
		
		playerButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if (recordedSound) {
					try {
						playRecording();
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SecurityException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalStateException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			}
		});
	}
	public void playRecording() throws IllegalArgumentException, SecurityException, IllegalStateException, IOException {
		player = new MediaPlayer();
		player.setDataSource(inputFile);
		player.prepare();
		player.start();
		player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
			
			@Override
			public void onCompletion(MediaPlayer mp) {
				mp.stop();
				mp.release();
				mp = null;
				
			}
		});
	}
	public void stopRecording() {
		m.stop();
		m.reset();
		m.release();
		m = null;
		recording = !recording;
		recordedSound = !recordedSound;
		recorder.setText("Start Recording!");
	}
	
	public void startRecording() {
		m = new MediaRecorder();
		m.setAudioSource(MediaRecorder.AudioSource.MIC);
		m.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
		m.setOutputFile(inputFile);
		m.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
		try {
			m.prepare();
			recorder.setText("Stop Recording!");
			m.start();
			recording = !recording;
			recordedSound = false;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	

}
