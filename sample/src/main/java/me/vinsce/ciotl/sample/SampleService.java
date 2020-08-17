package me.vinsce.ciotl.sample;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.android.volley.Request;

import me.vinsce.ciotl.Pipeline;
import me.vinsce.ciotl.collectors.AccelerometerCollector;
import me.vinsce.ciotl.collectors.BatteryCollector;
import me.vinsce.ciotl.collectors.GpsCollector;
import me.vinsce.ciotl.collectors.LightCollector;
import me.vinsce.ciotl.encoders.JsonByteEncoder;
import me.vinsce.ciotl.encoders.JsonEncoder;
import me.vinsce.ciotl.exporters.LogExporter;
import me.vinsce.ciotl.exporters.SocketExporter;
import me.vinsce.ciotl.http.exporters.HttpExporter;
import me.vinsce.ciotl.mqtt.exporters.MqttExporter;

public class SampleService extends Service {

    private static final String LOG_TAG = SampleService.class.getSimpleName();

    private Pipeline pipeline;
    private Thread pipelineThread;

    private void createTestPipeline() {
        AccelerometerCollector accelerometerDataCollector = new AccelerometerCollector(this, 1000);
        GpsCollector gpsDataCollector = new GpsCollector(this);

        if (pipeline == null)
            pipeline = new Pipeline();
        pipeline.addCollector(accelerometerDataCollector);
        pipeline.addCollector(gpsDataCollector);
        pipeline.addCollector(new LightCollector(this, 5000));
        pipeline.addCollector(new BatteryCollector(this));
        pipeline.addExporter(new MqttExporter(new JsonByteEncoder(), this, "tcp://10.0.2.2:1883", "test_client_1", "test_client"));

        SocketExporter socketExporter = new SocketExporter(new JsonByteEncoder(), 5003);
        socketExporter.setUseGenericSamples(true);
        socketExporter.setEndSequence("\n".getBytes());
        pipeline.addExporters(socketExporter, new LogExporter(new JsonEncoder()));

        pipeline.addExporter(new HttpExporter(new JsonEncoder(), Request.Method.GET, "http://10.0.2.2:5003"));
    }

    private void startPipeline() {
        pipelineThread = new Thread(() -> {
            Log.d(LOG_TAG, "Starting pipeline");
            pipeline.start();
        });
        pipelineThread.start();
    }

    @Override
    public void onDestroy() {
        pipelineThread.interrupt();
        pipeline.stop();
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        createTestPipeline();
        startPipeline();

        return new SampleServiceBinder();
    }

    public class SampleServiceBinder extends Binder {
        public Pipeline getPipeline() {
            return SampleService.this.pipeline;
        }
    }
}