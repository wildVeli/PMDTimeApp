package com.example.a2dam.aplicacionproyecto;

import android.os.Build;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.SystemClock;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.ChoiceFormat;

/**
 * Se encarga de la cuenta atras de la sesión que se tiene seleccionada
 */
//SIN PROBAR EL FICHERO
public class Cronometro extends AppCompatActivity {

    Chronometer chrono;
    Button stopChrono;
    int min;
    int sec;
    String nameCurrentTask;

    Chronometer chronometer;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cronometro);
        stopChrono=(Button)findViewById(R.id.btnStopChrono);
        chrono=(Chronometer)findViewById(R.id.chronometer);

        min=getIntent().getIntExtra("minutes",0);
        sec=getIntent().getIntExtra("seconds",0);
        nameCurrentTask=getIntent().getStringExtra("currentTask");
        stopChrono.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                DialogFragmentCancelSesion cs=new DialogFragmentCancelSesion();
                cs.show(getFragmentManager(),"cancelar");
                chrono.stop();
                */
            }
        });

        //Establece donde inicia el chronometro, dado que se debe utilizar elapsedRealtime() para iniciar el chronometro
        //Recogemos la hora del sistema actual y le restamos (minutos*60000 que será la cantidad de minutos con la que iniciará el reloj
        // + segundos*1000 que será la cantidad de segundos con los que iniciara el reloj)
        //debido a que es en milisegundos se utilizan dichas cifras, y en este caso que nos interesa que cuente hacia atras desde un tiempo alto
        // multiplicaremos por números negativos para conseguirlo
        chrono.setBase(SystemClock.elapsedRealtime() - (0 * -60000 + 1 * -1000));
        //setCountDown para llevar el reloj hacia atras es de la API 24, para anteriores CountDownTimer
        chrono.setCountDown(true);
        chrono.start();

        chrono.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                if(chronometer.getText().equals("00:00")){
                    chrono.stop();
                    try {
                        saveTaskTime();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        });



    }

   /*
    LO HACE 2 VECES?
     */
    private void saveTaskTime() throws IOException {

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
                    //PENSAR EN COMO PONER EL TIEMPO EN LA CLASE PARA CONTAR LOS MINUTOS Y SEGUNDOS QUE HA UTILIZADO
                    if(tarea.getName().equals(nameCurrentTask)){
                        tarea.setTime(tarea.getTime()+1);
                    }
                    oos.writeObject(tarea);
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
}
