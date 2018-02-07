package com.example.a2dam.aplicacionproyecto;

import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Environment;
import android.os.SystemClock;
import android.os.Vibrator;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;

import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import static android.widget.LinearLayout.*;

/**
 * Se encarga de la cuenta atras de la sesión que se tiene seleccionada
 */
//SIN PROBAR EL FICHERO
public class Cronometro extends AppCompatActivity implements DialogFragmentCancelSesion.answerDialogFragmentCancelSesion{

    Chronometer chrono;
    Button stopChrono;
    int min;
    int sec;
    String nameCurrentTask;
    Boolean chronoStoped=false;
    MediaPlayer mediaPlayer;
    ArrayList<Integer> songs;
    AnimationDrawable flami;
    TransitionDrawable transitionDrawable;

    Chronometer chronometer;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cronometro);
        stopChrono=(Button)findViewById(R.id.btnStopChrono);
        chrono=(Chronometer)findViewById(R.id.chronometer);
        //Añadido de musica
        songs = new ArrayList<Integer>();
        songs.add(R.raw.titlescreen);

        min=getIntent().getIntExtra("minutes",0);
        sec=getIntent().getIntExtra("seconds",0);
        nameCurrentTask=getIntent().getStringExtra("currentTask");

        stopChrono.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!chronoStoped){
                    DialogFragmentCancelSesion cs=new DialogFragmentCancelSesion();
                    cs.show(getFragmentManager(),"cancelar");
                    //chrono.stop();
                }else{
                    //Si se termina la sesión antes de acabar la musica se para el reproductor
                    if(mediaPlayer.isPlaying()) {
                        mediaPlayer.release();
                    }
                    endActivityApp();
                }


            }
        });
        //Tiempo modificado para probar aplicación
        min=0;
        sec=10;



        //Establece donde inicia el chronometro, dado que se debe utilizar elapsedRealtime() para iniciar el chronometro
        //Recogemos la hora del sistema actual y le restamos (minutos*60000 que será la cantidad de minutos con la que iniciará el reloj
        // + segundos*1000 que será la cantidad de segundos con los que iniciara el reloj)
        //debido a que es en milisegundos se utilizan dichas cifras, y en este caso que nos interesa que cuente hacia atras desde un tiempo alto
        // multiplicaremos por números negativos para conseguirlo
        chrono.setBase(SystemClock.elapsedRealtime() - (min * -60000 + sec * -1000));
        //setCountDown para llevar el reloj hacia atras es de la API 24, para anteriores CountDownTimer
        chrono.setCountDown(true);
        chrono.start();

        final Button button=new Button(getApplicationContext());
        button.setText(R.string.continueChrono);
        final LinearLayout layout=(LinearLayout)findViewById(R.id.activity_cronometro);
        final LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        //Animación
        //Donde se añadira la animación
        final ImageView image =(android.widget.ImageView) findViewById(R.id.imageViewFlami);
        image.setBackgroundResource(R.drawable.animationflami);
        flami = (AnimationDrawable) image.getBackground();
        image.setVisibility(View.INVISIBLE);

        //Transition
        ImageView transition = (android.widget.ImageView)findViewById(R.id.transitionFlami);
        transitionDrawable = (TransitionDrawable) getDrawable(R.drawable.transitionflami);
        transition.setImageDrawable(transitionDrawable);
        transitionDrawable.setCrossFadeEnabled(true);


        chrono.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {

                if(chronometer.getText().equals("00:00")&&!chronoStoped){
                    chronoStoped=true;
                    chrono.stop();
                    layout.addView(button,lp);
                    stopChrono.setText(R.string.finishSesion);
                    Vibrator v = (Vibrator) getSystemService(getApplicationContext().VIBRATOR_SERVICE);
                    // Vibrate for 500 milliseconds
                    v.vibrate(500);

                    //Animación
                    image.setVisibility(View.VISIBLE);
                    flami.start();
                    //Transition
                    transitionDrawable.startTransition(2000);


                    //Comienza la música al acabar la tarea
                    mediaPlayer= MediaPlayer.create(getApplicationContext(),songs.get(0));
                    mediaPlayer.start();

                    try {
                        saveTaskTime();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
        //Reinicia la sesión de cronometro
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
              //  chrono.stop();
                chrono.setBase(SystemClock.elapsedRealtime() - (min* -60000 + sec * -1000));
                chrono.start();
                chronoStoped=false;
                layout.removeView(button);

                stopChrono.setText(R.string.stopChrono);

                //Para y esconde la animación
                flami.stop();
                image.setVisibility(View.INVISIBLE);

                //Si la musica sigue sonando al reiniciar la sesión, para
                if(mediaPlayer.isPlaying()) {
                    mediaPlayer.release();
                }

                //Transition reverse
                transitionDrawable.reverseTransition(3000);
            }
        });


    }

    private void endActivityApp() {
        this.finish();
    }

    public void onAnswer(String s){
        switch (s){
            case("si"):
                chrono.stop();
                this.finish();
                break;
            case("no"):

                break;

        }
    }

    private void saveTaskTime() throws IOException {

        File newFile = null;
        FileOutputStream fos= null;
        ObjectOutputStream oos=null;

        File file = null;
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try{
            Tarea tarea;

            File path= Environment.getExternalStorageDirectory();
            newFile = new File(path,"newTaskFile.txt");
            file = new File(path,"tasks.txt");

            if(file.exists()){

                fos = new FileOutputStream(newFile);
                oos = new ObjectOutputStream(fos);
                fis = new FileInputStream(file);
                ois = new ObjectInputStream(fis);
                do{
                    tarea=new Tarea();
                    tarea= (Tarea) ois.readObject();

                    if(tarea.getName().equals(nameCurrentTask)){
                        tarea.setTime(tarea.getTime()+min);
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
