package com.example.sarahwada.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


public class EndSceneActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_scene);
        String score = getIntent().getExtras().getString("score");

        TextView scoreText = (TextView) findViewById(R.id.score);
        scoreText.setText("You scored " + score + " :)");
        ImageView startGame = (ImageView) findViewById(R.id.restart_game);
        startGame.setImageResource(R.drawable.ic_action_replay);
        startGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), GameActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
    }

}
