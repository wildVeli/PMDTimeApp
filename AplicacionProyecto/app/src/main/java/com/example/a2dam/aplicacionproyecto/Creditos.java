package com.example.a2dam.aplicacionproyecto;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

public class Creditos extends AppCompatActivity {

    ImageButton gitHub;
    AnimationDrawable coin;
    TransitionDrawable transitionDrawable;
    ImageView transitionBlue;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creditos);
        //Git transition
        ImageView gitHub = (android.widget.ImageView)findViewById(R.id.btnGitHub);
        transitionDrawable = (TransitionDrawable) getDrawable(R.drawable.gitmark);
        gitHub.setImageDrawable(transitionDrawable);
        transitionDrawable.setCrossFadeEnabled(true);

        gitHub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                transitionDrawable.startTransition(200);
                Intent pagina=new Intent(Intent.ACTION_VIEW);
                pagina.setData(Uri.parse("https://github.com/wildVeli"));
                startActivity(pagina);
            }
        });

        //Coin animation
        ImageView image =(android.widget.ImageView) findViewById(R.id.coin);
        image.setBackgroundResource(R.drawable.animationcoin);
        coin = (AnimationDrawable) image.getBackground();
        coin.start();



    }
}
