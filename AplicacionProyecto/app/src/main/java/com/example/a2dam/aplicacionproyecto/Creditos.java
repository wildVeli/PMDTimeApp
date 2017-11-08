package com.example.a2dam.aplicacionproyecto;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class Creditos extends AppCompatActivity {

    ImageButton gitHub;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creditos);
        gitHub=(ImageButton)findViewById(R.id.btnGitHub);
        gitHub.setImageResource(R.drawable.github);
        gitHub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pagina=new Intent(Intent.ACTION_VIEW);
                pagina.setData(Uri.parse("https://github.com/wildVeli"));
                startActivity(pagina);
            }
        });
    }
}
