package com.example.fbstoriesheaderview;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private FBStoriesHeaderView fbStoriesHeaderView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fbStoriesHeaderView = findViewById(R.id.header);
    }
}
