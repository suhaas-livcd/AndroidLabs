package com.idlikadai.tuneinn;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void GoToPreviousActvity(View view){
        Intent intent = new Intent(MenuActivity.this,MainActivity.class);
        startActivity(intent);
    }

    public void ChnagePlaytoPause(View view) {
        ImageView imageView = (ImageView) findViewById(R.id.ImageViewMenuPlay);
        imageView.setImageResource(R.drawable.ic_pause_black_24px);
    }
}
