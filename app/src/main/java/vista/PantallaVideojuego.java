package vista;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.ejemplovideojuego2d.R;

import modelo.Motor;
import modelo.Sprite;

public class PantallaVideojuego extends SurfaceView
{
    private Bitmap imagenspritebueno, imagenspritemalo, fondo;
    private SurfaceHolder holder;
    private Motor motor;
    private Sprite spritebueno, spritemalo;
    private BitmapDrawable fondorepetido;

    public PantallaVideojuego(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        funcionalidad();
    }

    public PantallaVideojuego(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        funcionalidad();
    }

    public PantallaVideojuego(Context context)
    {
        super(context);
        funcionalidad();
    }

    @Override
    /**
     * Método donde se dibujan las cosas en la pantalla
     * @param canvas Lienzo de la pantalla donde se dibuja
     */
    public void onDraw(Canvas canvas)
    {
        if(canvas != null)
        {
            // Dibujo el fondo del juego
            if(fondorepetido != null)
                fondorepetido.draw(canvas);
            // Dibujo el personaje
            if(spritebueno != null)
                spritebueno.onDraw(canvas);
            // Dibujo el enemigo
            if(spritemalo != null)
                spritemalo.onDraw(canvas);

            // Compruebo si hay colisiones entre los personajes
            if( spritebueno.hayColision(spritemalo.getX(), spritemalo.getY()) )
            {
                System.out.println("COLISION DETECTADA");
            }

            // Dibujo el texto
            dibujarTexto("Hola", 20, 50, 50, 255, 0, 0, canvas);
        }
    }

    /**
     * Dibuja texto en el lienzo
     * @param texto Texto a dibujar
     * @param x Coordenada x donde se situará texto
     * @param y Coordenada y donde se situará texto
     * @param tamatexto Tamaño del texto
     * @param rojo Componente rojo del color del texto
     * @param verde Componente verde del color del texto
     * @param azul Componente azul del color del texto
     * @param canvas Lienzo donde se dibujará el texto
     */
    private void dibujarTexto(String texto, int x, int y, int tamatexto, int rojo, int verde, int azul, Canvas canvas)
    {
        int transparencia = 255;
        Paint pincel1 = new Paint();
        /*
         * Configuramos el color del texto, que ha de estar en formato ARGB
         */
        pincel1.setARGB(transparencia, rojo, verde, azul);
        pincel1.setTextSize(tamatexto);
        pincel1.setTypeface(Typeface.SERIF);
        @SuppressLint("WrongConstant") Typeface tf = Typeface.create(Typeface.SERIF, Typeface.ITALIC | Typeface.BOLD);
        pincel1.setTypeface(tf);
        canvas.drawText(texto, x, y, pincel1);
    }

    /**
     * Funcionalidad de la pantalla de juego
     */
    public void funcionalidad()
    {
        imagenspritebueno = BitmapFactory.decodeResource(getResources(), R.drawable.good1);
        imagenspritemalo = BitmapFactory.decodeResource(getResources(), R.drawable.enemigo);
        fondo = BitmapFactory.decodeResource(getResources(), R.drawable.background);
        fondorepetido = new BitmapDrawable(fondo);
        spritebueno = new Sprite(this, imagenspritebueno);
        spritemalo = new Sprite(this, imagenspritemalo);
        motor = new Motor(this);
        holder = getHolder();

        holder.addCallback(new SurfaceHolder.Callback()
        {
            @Override
            public void surfaceDestroyed(SurfaceHolder holder)
            {
                motor.setRunning(false);
                motor.matar();
            }
            @Override
            public void surfaceCreated(SurfaceHolder holder)
            {
                // Creo el sprite del personaje bueno
                spritebueno.generarPosicionAleatoria(getWidth(), getHeight());
                // Creo el sprite del personaje malo
                spritemalo.generarPosicionAleatoria(getWidth(), getHeight());
                // Creo la imagen escalada del fondo
                fondorepetido.setBounds(0, 0, getWidth(), getHeight());
                fondorepetido.setTileModeX(Shader.TileMode.REPEAT);
                fondorepetido.setTileModeY(Shader.TileMode.REPEAT);
                // Arranco el motor del juego
                motor.setRunning(Boolean.TRUE);
                motor.start();
            }
            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
            {
            }
        });
    }

    /**
     * Pausa o reanuda el juego
     */
    public void pausar()
    {
        motor.pause();
    }

    /**
     * Indica si el juego está en marcha o no
     * @return Verdadero si el juego está en marcha, falso en caso contrario
     */
    public Boolean isRunning()
    {
        return motor.getRunning();
    }

    /**
     * Cambia la dirección del sprite a la derecha
     */
    public void cambiarDireccionDerecha()
    {
        spritebueno.cambiarDireccionDerecha();
    }

    /**
     * Cambia la dirección del sprite a la izquierda
     */
    public void cambiarDireccionIzquierda()
    {
        spritebueno.cambiarDireccionIzquierda();
    }

    /**
     * Cambia la dirección del sprite hacia abajo
     */
    public void cambiarDireccionAbajo()
    {
        spritebueno.cambiarDireccionAbajo();
    }

    /**
     * Cambia la dirección del sprite hacia arriba
     */
    public void cambiarDireccionArriba()
    {
        spritebueno.cambiarDireccionArriba();
    }
}
