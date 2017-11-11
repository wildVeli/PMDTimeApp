package com.example.a2dam.aplicacionproyecto;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class NuevaSesion extends AppCompatActivity {

    Button btnStartSesion;
    Button btnNewTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_sesion);

        btnNewTask=(Button)findViewById(R.id.btnNewTask);
        btnNewTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent (getApplicationContext(),NuevaTarea.class);
                startActivity(i);
            }
        });
        btnStartSesion=(Button)findViewById(R.id.btnStartSesion);
        btnStartSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),Cronometro.class);
                startActivity(i);
            }
        });
    }
}
