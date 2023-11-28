package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.myapplication.adapter.CardStackAdapter;
import com.example.myapplication.models.DataManager;
import com.example.myapplication.models.MovieInfo;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Fresco.initialize(this);

        List<MovieInfo> yourDataList = DataManager.getInstance().popularMovies;

        CardStackLayoutManager layoutManager = new CardStackLayoutManager(this);
        CardStackAdapter adapter = new CardStackAdapter(yourDataList);
        CardStackView cardStackView = findViewById(R.id.card_stack_view);
        cardStackView.setLayoutManager(layoutManager);
        cardStackView.setAdapter(adapter);
    }
}