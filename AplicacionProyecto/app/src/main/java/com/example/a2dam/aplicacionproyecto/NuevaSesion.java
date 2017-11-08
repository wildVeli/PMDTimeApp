package com.example.a2dam.aplicacionproyecto;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class NuevaSesion extends AppCompatActivity {

    Button btnStartSesion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_sesion);

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
