package com.example.a2dam.aplicacionproyecto;

import android.os.Environment;
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
                saveTask();
            }

        });
    }
    private void saveTask(){
        try{
            File file= Environment.getExternalStorageDirectory();
            file=new File(file,"tasks");
            FileOutputStream fos=new FileOutputStream(file);
            Tarea tarea=new Tarea(taskName.getText().toString(),taskDescription.getText().toString());
            if(file.exists()){
                AppendingObjectOutputStream aoos=new AppendingObjectOutputStream(fos);
                aoos.writeObject(tarea);
                aoos.close();
            }else{
                ObjectOutputStream oos= new ObjectOutputStream(fos);
                oos.writeObject(tarea);
                oos.close();
            }
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
