package com.example.a2dam.aplicacionproyecto;

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
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class Estasdisticas extends AppCompatActivity {

    ListView taskList;
    ListView taskTime;

    TabHost tabsHost;
    HashMap<String,Double> tasks = new HashMap<String,Double>();
    ArrayList<String> tasksName= new ArrayList<String>();

    ArrayAdapter<String>adapter = null;
    ArrayAdapter<String>adapter2 = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estasdisticas);
        tabsHost=(TabHost)findViewById(R.id.tabsHost);
        taskList=(ListView)findViewById(R.id.taskList);
        taskTime = (ListView)findViewById(R.id.taskTime);

        tabsHost.setup();
        TabSpec ts1=tabsHost.newTabSpec("Tab1");
        ts1.setIndicator(getApplicationContext().getString(R.string.estadistica1));
        ts1.setContent(R.id.tab1);
        tabsHost.addTab(ts1);

        tabsHost.setup();
        TabSpec ts2=tabsHost.newTabSpec("Tab2");
        ts2.setIndicator(getApplicationContext().getString(R.string.estadistica2));
        ts2.setContent(R.id.tab2);
        tabsHost.addTab(ts2);
        try {

            getTasks();
            List<String> taskNameList=new ArrayList<String>();
            List<String> taskTimeList=new ArrayList<>();

            Collections.sort(tasksName, new Comparator<String>()
            {
                @Override
                public int compare(String text1, String text2)
                {
                    return text1.compareToIgnoreCase(text2);
                }
            });
            for(String task: tasksName){
                taskNameList.add(task);
                taskTimeList.add(tasks.get(task).toString());
            }
            adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.lista_final_estadisticas,taskNameList);
            adapter2 = new ArrayAdapter<String>(getApplicationContext(), R.layout.lista_final_estadisticas,taskTimeList);
            taskList.setAdapter(adapter);
            taskTime.setAdapter(adapter2);

        } catch (IOException e) {
           // e.printStackTrace();
        }

    }
    private void getTasks() throws IOException {




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
                    tasks.put(tarea.getName(),tarea.getTime());
                    tasksName.add(tarea.getName());


                    //Guardar en la lista
                }while(true);

            }else{
            }

        } catch(EOFException e) {
            ois.close();
            fis.close();


        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        }catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

    }
}
