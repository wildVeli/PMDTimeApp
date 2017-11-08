package com.example.a2dam.aplicacionproyecto;

import android.text.Editable;

/**
 * Created by Sergio on 08/11/2017.
 */

/**
 * Clase para guardar las tareas y leerlas
 */
public class Tarea {
    private String name;
    private String description;
    private double time;

    public Tarea(String name, String description) {
        this.name=name;
        this.description=description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }
}
