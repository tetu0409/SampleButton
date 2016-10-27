package com.example.teppeiyajima.samplebutton;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements SensorEventListener, View.OnTouchListener {
    private static final String TAG = "SampleButton";
    SensorManager manager;
    Sensor sensor;
    private TextView textView;
    private boolean flag = false;
    double touchTimes;
    Button judgeButton1;
    private Button judgeButton2;
    private Button judgeButton3;
    HashMap<Integer, Long> startTimeMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        judgeButton1 = (Button) findViewById(R.id.button);
        judgeButton2 = (Button) findViewById(R.id.button2);
        judgeButton3 = (Button) findViewById(R.id.button3);

        // リスナーをボタンに登録
        judgeButton1.setOnTouchListener(this);
        judgeButton2.setOnTouchListener(this);
        judgeButton3.setOnTouchListener(this);

        // TextView の設定
        textView = (TextView) findViewById(R.id.textView);

        manager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

    }

    @Override
    public void onSensorChanged(SensorEvent event) {

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        manager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        super.onPause();
        manager.unregisterListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            startTimeMap.put(v.getId(), event.getEventTime());
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            long time = event.getEventTime() - startTimeMap.get(v.getId());
            if(time<140){
                Button btn = (Button)v;
                String text = textView.getText()+btn.getText().toString();
                textView.setText(text);
            }
            Log.d(TAG, "onTouch: " + time);
        }
        return false;
    }
}
