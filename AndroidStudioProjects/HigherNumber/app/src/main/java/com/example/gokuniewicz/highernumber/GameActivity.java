package com.example.gokuniewicz.highernumber;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class GameActivity extends AppCompatActivity {

    private Game game;
    private TextView scoreView;
    public Button leftButton, rightButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        game = new Game();
        scoreView = (TextView)findViewById(R.id.scoreView);
        leftButton = (Button)findViewById(R.id.leftButton);
        rightButton = (Button)findViewById(R.id.rightButton);
        updateView();
    }

    public void onLeftButtonTap(View view) {
        game.checkAnswer(true);
        updateView();
    }

    public void onRightButtonTap(View view) {
        game.checkAnswer(false);
        updateView();
    }

    private void updateView() {
        scoreView.setText("Score: " + game.getScore());
        leftButton.setText("" + game.getLeftNumber());
        rightButton.setText("" + game.getRightNumber());
    }
}
