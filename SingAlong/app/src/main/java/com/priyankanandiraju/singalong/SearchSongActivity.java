package com.priyankanandiraju.singalong;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static com.priyankanandiraju.singalong.RecordAudioActivity.RECORD_AUDIO_URL;

public class SearchSongActivity extends AppCompatActivity {

    private static final String SPOTIFY_SDK = "https://developer.spotify.com/technologies/spotify-android-sdk/";
    private Button btnSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_song);

        TextView tvSearchSongDescription = (TextView) findViewById(R.id.tv_search_song_description);
        tvSearchSongDescription.setText(R.string.search_song_desc);
        btnSource = (Button) findViewById(R.id.btn_source);

        btnSource.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.openWebPage(view.getContext(), SPOTIFY_SDK);
            }
        });
    }
}
