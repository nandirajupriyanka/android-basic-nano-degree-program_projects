package com.priyankanandiraju.singalong;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class RecordAudioActivity extends AppCompatActivity {

    public static final String RECORD_AUDIO_URL = "https://developer.android.com/guide/topics/media/audio-capture.html";
    private Button btnSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_audio);

        TextView tvRecordAudioDescription = (TextView) findViewById(R.id.tv_record_audio_description);
        tvRecordAudioDescription.setText(R.string.record_audio_desc);
        btnSource = (Button) findViewById(R.id.btn_source);

        btnSource.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.openWebPage(view.getContext(), RECORD_AUDIO_URL);
            }
        });

    }
}
