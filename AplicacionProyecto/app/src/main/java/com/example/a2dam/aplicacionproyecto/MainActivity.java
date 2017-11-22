package com.example.a2dam.aplicacionproyecto;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button newSesion;
    Button stats;
    Button credits;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setTheme(android.R.style.Theme_Holo);
        setContentView(R.layout.activity_main);
        newSesion= (Button) findViewById(R.id.btnnewSesion);
        stats=(Button)findViewById(R.id.btnstats);
        credits=(Button)findViewById(R.id.btncredits);

        newSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),NuevaSesion.class);
                startActivity(i);
            }
        });
        stats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),Estasdisticas.class);
                startActivity(i);
            }
        });
        credits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Creditos.class);
                startActivity(i);
            }
        });
    }
}
