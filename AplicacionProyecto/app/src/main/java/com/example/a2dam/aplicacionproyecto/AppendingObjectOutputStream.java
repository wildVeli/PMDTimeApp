package com.example.a2dam.aplicacionproyecto;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

/**
 * Created by Sergio on 08/11/2017.
 *
 * ObjectOutputStream personalizado para añadir mas objetos al final de un fichero que existe con datos
 */

public class AppendingObjectOutputStream extends ObjectOutputStream {

    public AppendingObjectOutputStream(OutputStream out) throws IOException {
        super(out);
    }

    @Override
    protected void writeStreamHeader() throws IOException {
    }

}