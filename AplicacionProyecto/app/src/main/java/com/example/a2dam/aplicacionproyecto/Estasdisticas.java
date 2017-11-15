package com.example.a2dam.aplicacionproyecto;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TabHost;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import android.widget.TabHost.TabSpec;
import java.util.ArrayList;
import java.util.List;

public class Estasdisticas extends AppCompatActivity {

    ListView taskList;
    TabHost tabsHost;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estasdisticas);
        tabsHost=(TabHost)findViewById(R.id.tabsHost);
        taskList=(ListView)findViewById(R.id.taskList);

        tabsHost.setup();
        TabSpec ts1=tabsHost.newTabSpec("Tab1");
        ts1.setIndicator("Estadisticas");
        ts1.setContent(R.id.tab1);
        tabsHost.addTab(ts1);

        tabsHost.setup();
        TabSpec ts2=tabsHost.newTabSpec("Tab2");
        ts2.setIndicator("Estadisticasaa");
        ts2.setContent(R.id.tab2);
        tabsHost.addTab(ts2);
        try {
            getTasks();
        } catch (IOException e) {
           // e.printStackTrace();
        }

    }
    private void getTasks() throws IOException {


        ArrayAdapter<String>adapter = null;
        List<String> list=new ArrayList<String>();
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
                    list.add(tarea.getName()+"             "+tarea.getTime());
                    //Guardar en la lista
                }while(true);

            }else{
            }

        } catch(EOFException e) {
            ois.close();
            fis.close();
            adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.lista_final_estadisticas,list);
            taskList.setAdapter(adapter);

        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        }catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

    }
}
