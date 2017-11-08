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
        chrono.setBase(SystemClock.elapsedRealtime() - (5 * -60000 + sec * -1000));
        //setCountDown para llevar el reloj hacia atras es de la API 24, para anteriores CountDownTimer
        chrono.setCountDown(true);

        chrono.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                if(chronometer.getText().equals("00:00")){
                   saveTaskTime();
                }
            }
        });

        chrono.start();

    }
    /*
    public void onAnswer(String s){
        if(s.equals("si")){
            chrono.stop();
        }
    }
    */
    private void saveTaskTime(){
        FileInputStream fis= null;;
        ObjectInputStream ois= null;;
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try{
            Tarea tarea;
            File file= Environment.getExternalStorageDirectory();
            file=new File(file,"tasks");

            File save= Environment.getExternalStorageDirectory();
            save=new File(save,"save");

            fis=new FileInputStream(file);
            ois=new ObjectInputStream(fis);

            fos=new FileOutputStream(save);
            oos=new ObjectOutputStream(fos);

            while(true){
                tarea= (Tarea) ois.readObject();
                if(tarea.getName().equals(nameCurrentTask)){
                    //Los tiempos serán minutos exactos siempre
                    tarea.setTime(tarea.getTime()+min);
                }
                oos.writeObject(tarea);

            }

        } catch(EOFException e){

        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            oos.close();
            fos.close();
            ois.close();
            fis.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
