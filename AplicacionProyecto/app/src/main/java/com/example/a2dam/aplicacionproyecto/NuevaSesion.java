package com.example.a2dam.aplicacionproyecto;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;



public class NuevaSesion extends AppCompatActivity {

    Button btnStartSesion;
    Button btnNewTask;
    Spinner spSelectTask;
    Spinner spSelectTime;
    Button btnDeleteTask;
    String spTaskActualSelected;

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
                int sec=Integer.valueOf(spSelectTime.getSelectedItem().toString().substring(3,5));
                int min=Integer.valueOf(spSelectTime.getSelectedItem().toString().substring(0,2));
                spTaskActualSelected=spSelectTask.getSelectedItem().toString();


                Intent i = new Intent(getApplicationContext(),Cronometro.class);
                i.putExtra("minutes",min);
                i.putExtra("seconds",sec);
                i.putExtra("currentTask",spTaskActualSelected);
                startActivity(i);
            }
        });

        spSelectTask=(Spinner)findViewById(R.id.spSelectTask);
        spSelectTime=(Spinner)findViewById(R.id.spSelectTime);
        try {
            getTasks();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String [] arraySpinner = new String[] {
                "05:00", "15:00", "25:00", "40:00", "55:00"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                R.layout.lista_final_estadisticas, arraySpinner);
        spSelectTime.setAdapter(adapter);

        btnDeleteTask=(Button)findViewById(R.id.btnDeleteTask);
        btnDeleteTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    deleteTask();
                    getTasks();
                    spSelectTask.refreshDrawableState();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void deleteTask() throws IOException {

        spTaskActualSelected=spSelectTask.getSelectedItem().toString();

        File newFile = null;
        FileOutputStream fos= null;
        ObjectOutputStream oos=null;

        File file = null;
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try{
            Tarea tarea;
            //File path= Environment.getExternalStorageDirectory();
            newFile = new File("/storage/emulated/0/newTaskFile.txt");
            file = new File("/storage/emulated/0/tasks.txt");

            if(file.exists()){

                fos = new FileOutputStream(newFile);
                oos = new ObjectOutputStream(fos);
                fis = new FileInputStream(file);
                ois = new ObjectInputStream(fis);
                do{
                    tarea=new Tarea();
                    tarea= (Tarea) ois.readObject();
                    if(!tarea.getName().equals(spTaskActualSelected)){
                        oos.writeObject(tarea);
                    }
                }while(true);

            }else{
            }

        } catch(EOFException e) {
            ois.close();
            fis.close();
            fos.close();
            oos.close();
            file.delete();
            newFile.renameTo(file);


        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getTasks() throws IOException {


        ArrayAdapter<String>adapter = null;
        ArrayList<String> listSpinner=new ArrayList<String>();
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try{
            Tarea tarea;
            //File path= Environment.getExternalStorageDirectory();
            File file=new File("/storage/emulated/0/tasks.txt");

            if(file.exists()){

                fis = new FileInputStream(file);
                ois = new ObjectInputStream(fis);
                do{
                    tarea=new Tarea();
                    tarea= (Tarea) ois.readObject();
                    listSpinner.add(tarea.getName());
                    //Guardar en la lista
                }while(true);

            }else{
            }

        } catch(EOFException e) {
            ois.close();
            fis.close();
            adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.lista_final_estadisticas,listSpinner);
            spSelectTask.setAdapter(adapter);

        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
