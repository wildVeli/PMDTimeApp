package com.example.a2dam.aplicacionproyecto;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;


/**
 * Se encarga de añadir una nueva tarea a las tareas del usuario.
 */
public class NuevaTarea extends AppCompatActivity {

    EditText taskName;
    EditText taskDescription;
    Button btnSaveTask;
    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_tarea);
        taskName=(EditText)findViewById(R.id.etNewTaksName);
        taskDescription=(EditText)findViewById(R.id.etNewTaskDesc);
        btnSaveTask=(Button)findViewById(R.id.btnSaveTask);

        btnSaveTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                askForPermissions();
                Intent i = new Intent(getApplicationContext(),NuevaSesion.class);
                startActivity(i);
            }

        });

    }

    //En las nuevas versiones de android a partir de la API 23 los permisos se otorgan
    //En funcionamiento no cuando se instala la aplicación
    //Este metodo pide los permisos de lectura y escritura externos
    //REF:https://developer.android.com/training/permissions/requesting.html
    //Pedir permisos de escritura nos otorga los de lectura tambien
    private void askForPermissions() {
        int permisos=0;
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);

        }else{
            saveTask();
        }

    }

    //Recoge el resultado del dialog fragment de los permisos
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    saveTask();
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
            /*
            if(ContextCompat.checkSelfPermission(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);
            }
            */
            // other 'case' lines to check for other
            // permissions this app might request
        }
    }


    //Guarda la nueva tarea en un atrchivo
    private void saveTask(){
        try{

            File path= Environment.getExternalStorageDirectory();
            File file=new File(path,"tasks.txt");
            //File file = new File("hola.txt");
            FileOutputStream fos;
            Tarea tarea=new Tarea(taskName.getText().toString(),taskDescription.getText().toString());
           // file.delete();

            if(file.exists()){
                fos=new FileOutputStream(file,true);
                AppendingObjectOutputStream aoos=new AppendingObjectOutputStream(fos);
                aoos.writeObject(tarea);
                aoos.close();
            }else{
                file.createNewFile();
                fos=new FileOutputStream(file);
                ObjectOutputStream oos= new ObjectOutputStream(fos);
                oos.writeObject(tarea);
                oos.close();
            }
            System.out.println(file.getAbsolutePath());
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
