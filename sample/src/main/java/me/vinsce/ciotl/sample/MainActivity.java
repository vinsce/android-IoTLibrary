package me.vinsce.ciotl.sample;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import me.vinsce.ciotl.Pipeline;
import me.vinsce.ciotl.collectors.AccelerometerCollector;
import me.vinsce.ciotl.collectors.BatteryCollector;
import me.vinsce.ciotl.collectors.LightCollector;
import me.vinsce.ciotl.encoders.JsonEncoder;
import me.vinsce.ciotl.exporters.AbstractExporter;
import me.vinsce.ciotl.exporters.LogExporter;

public class MainActivity extends AppCompatActivity {
    private TextView logView;
    private Pipeline testPipeline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        logView = findViewById(R.id.log);

        // Create a test pipeline
        testPipeline = createTestPipeline();

        // Start the pipeline
        testPipeline.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        testPipeline.stop();
    }

    private Pipeline createTestPipeline() {
        AccelerometerCollector accelerometerDataCollector = new AccelerometerCollector(this, 1000);
        // GpsCollector gpsDataCollector = new GpsCollector(this);

        Pipeline pipeline = new Pipeline();
        pipeline.addCollector(accelerometerDataCollector);
        pipeline.addCollector(new LightCollector(this, 5000));
        pipeline.addCollector(new BatteryCollector(this));
        pipeline.addExporter(new LogExporter(new JsonEncoder()));
        // pipeline.addExporter(new MqttExporter(new JsonByteEncoder(), this, "tcp://192.168.0.104:1883", "test_client_1", "test_client"));
        AbstractExporter<String> logViewExporter = new AbstractExporter<String>(new JsonEncoder()) {
            @Override
            public void close() {}

            @Override
            public void exportEncoded(String encodedSample) {
                runOnUiThread(() -> logView.setText(String.format("%s%s\n\n", logView.getText(), encodedSample)));
            }
        };
        logViewExporter.setUseGenericSamples(false);
        pipeline.addExporter(logViewExporter);
        return pipeline;
    }
}