package com.example.ejemplovideojuego2d;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import vista.PantallaVideojuego;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static PantallaVideojuego pantallajuego;
    private ImageButton ibDerecha, ibIzquierda, ibArriba, ibAbajo, ibPausa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ***** Ligo los recursos de la actividad *****
        pantallajuego = (PantallaVideojuego)findViewById(R.id.pantallajuego);
        ibDerecha = (ImageButton)findViewById(R.id.ibDerecha);
        ibIzquierda = (ImageButton)findViewById(R.id.ibIzquierda);
        ibArriba = (ImageButton)findViewById(R.id.ibArriba);
        ibAbajo = (ImageButton)findViewById(R.id.ibAbajo);
        ibPausa = (ImageButton)findViewById(R.id.ibPausa);
        // ************************************************

        ibAbajo.setOnClickListener(this);
        ibArriba.setOnClickListener(this);
        ibDerecha.setOnClickListener(this);
        ibIzquierda.setOnClickListener(this);
        ibPausa.setOnClickListener(this);
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.ibAbajo:
                pantallajuego.cambiarDireccionAbajo();
                break;
            case R.id.ibArriba:
                pantallajuego.cambiarDireccionArriba();
                break;
            case R.id.ibDerecha:
                pantallajuego.cambiarDireccionDerecha();
                break;
            case R.id.ibIzquierda:
                pantallajuego.cambiarDireccionIzquierda();
                break;
            case R.id.ibPausa:
                pantallajuego.pausar();
                break;
        }
    }
}