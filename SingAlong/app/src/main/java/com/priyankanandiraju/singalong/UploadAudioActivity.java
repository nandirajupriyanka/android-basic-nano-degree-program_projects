package com.priyankanandiraju.singalong;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class UploadAudioActivity extends AppCompatActivity {

    private static final String GET_AUDIO = "http://stackoverflow.com/questions/17944987/picking-up-an-audio-file-android";
    private Button btnSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_audio);

        TextView tvUploadAudioDescription = (TextView) findViewById(R.id.tv_upload_audio_description);
        tvUploadAudioDescription.setText(R.string.upload_audio_desc);
        btnSource = (Button) findViewById(R.id.btn_source);
        btnSource.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.openWebPage(view.getContext(), GET_AUDIO);
            }
        });
    }
}
