package com.priyankanandiraju.singalong;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements OnClickListener {

    private TextView tvSearchSong;
    private TextView tvUploadAudio;
    private TextView tvRecordAudio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvSearchSong = (TextView) findViewById(R.id.tv_search_song);
        tvUploadAudio = (TextView) findViewById(R.id.tv_upload_audio);
        tvRecordAudio = (TextView) findViewById(R.id.tv_record_audio);

        tvSearchSong.setOnClickListener(this);
        tvUploadAudio.setOnClickListener(this);
        tvRecordAudio.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.tv_search_song:
                intent = new Intent(this, SearchSongActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_upload_audio:
                intent = new Intent(this, UploadAudioActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_record_audio:
                intent = new Intent(this, RecordAudioActivity.class);
                startActivity(intent);
                break;
        }
    }
}
