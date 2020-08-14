package me.vinsce.ciotl.sample;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import me.vinsce.ciotl.encoders.JsonEncoder;
import me.vinsce.ciotl.exporters.AbstractExporter;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private TextView logView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        logView = findViewById(R.id.log);
    }

    @Override
    protected void onStart() {
        super.onStart();
        bindService(new Intent(this, SampleService.class), connection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        stopService(new Intent(this, SampleService.class));
        super.onStop();
    }

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            Log.d(LOG_TAG, "Service connected");

            SampleService.SampleServiceBinder binder = (SampleService.SampleServiceBinder) service;

            AbstractExporter<String> logViewExporter = new AbstractExporter<String>(new JsonEncoder()) {
                @Override
                public void close() {}

                @SuppressLint("SetTextI18n")
                @Override
                public void exportEncoded(String encodedSample) {
                    runOnUiThread(() -> logView.setText(logView.getText() + encodedSample + "\n\n"));
                }
            };
            logViewExporter.initialize(); // explicit call to initialize because it is added when the pipeline is already started
            binder.getPipeline().addExporters(logViewExporter);
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            Log.d(LOG_TAG, "Service disconnected");
        }
    };
}