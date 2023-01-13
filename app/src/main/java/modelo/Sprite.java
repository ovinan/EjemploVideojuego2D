package modelo;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import java.util.Random;

import vista.PantallaVideojuego;

/**
 * Esta clase representa un Sprite de un personaje del videojuego
 */
public class Sprite
{
    /*
     * 	Configuración del sprite
     *
     * 	He supuesto las direcciones, casillas del vector
     *
     *  dirección = 0 up, 1 left, 2 down, 3 right
     *  animación = 3 up, 1 left, 0 down, 2 right
     *
     *  En la posición 0 del vector -> imagénes para moverse hacia abajo
     *  En la posición 1 del vector -> imagénes para moverse hacia la izquierda
     *  En la posición 2 del vector -> imagénes para moverse hacia la derecha
     *  En la posición 3 del vector -> imagénes para moverse hacia arriba
     */
    int[] DIRECCION_ANIMACION = { 3, 1, 0, 2 };

    private static final int BMP_ROWS = 4;
    private static final int BMP_COLUMNS = 3;
    private Direccion direccion;
    private int x = 0;
    private int y = 0;
    private static int INCREMENTO_VELOCIDAD = 5;
    private int velocidadEjeX = INCREMENTO_VELOCIDAD;
    private int velocidadEjeY = INCREMENTO_VELOCIDAD;
    private PantallaVideojuego pantalla;
    private Bitmap imagensprite;
    private int frameactual = 0;
    private int ancho;
    private int alto;
    private Random aleatorio;

    /**
     * Constructor
     * @param pantalla Pantalla donde se va a jugar
     * @param imagensprite Imagen del sprite
     */
    public Sprite(PantallaVideojuego pantalla, Bitmap imagensprite)
    {
        this.pantalla = pantalla;
        this.setImagenSprite(imagensprite);
        direccion = Direccion.ABAJO;
        aleatorio = new Random();
    }

    /**
     * Genera una posición aleatoria para pintar el sprite en la pantalla
     * @param anchopantalla Ancho de la pantalla
     * @param altopantalla Alto de la pantalla
     */
    public void generarPosicionAleatoria(int anchopantalla, int altopantalla)
    {
        x = aleatorio.nextInt(anchopantalla) + 1 - 50;
        // Arreglo por el padding
        if( x <= 0 )
            x += 50;
        y = aleatorio.nextInt(altopantalla) + 1 - 50;
        // Arreglo por el padding
        if( y <= 0 )
            y += 50;
    }

    /**
     * Cambia la dirección de movimiento del sprite hacia la derecha
     */
    public void cambiarDireccionDerecha()
    {
        if( !movimientoXDerecha() )                                         // Si el sprite no se está moviendo hacia la derecha
            cambiarSentidoEjeX();                                           // Cambio el sentido hacia la derecha

        if( direccion == Direccion.ARRIBA || direccion == Direccion.ABAJO ) // Si el sprite se está movimiento hacia arriba o hacia abajo
        {
            velocidadEjeX = INCREMENTO_VELOCIDAD;                           // Hago que se mueva en el eje x, hacia la derecha o izquiereda
            velocidadEjeY = 0;                                              // Deja de moverse en el eje y, hacia arriba o abajo
        }

        direccion = Direccion.DERECHA;                                      // Hago que el sprite se mueva hacia la derecha
    }

    /**
     * Cambia la dirección del sprite hacia la izquierda
     */
    public void cambiarDireccionIzquierda()
    {
        if( !movimientoXIzquierda() )                                       // Si el sprite no se está moviendo hacia la derecha
            cambiarSentidoEjeX();                                           // Cambio el sentido hacia la derecha

        if( direccion == Direccion.ARRIBA || direccion == Direccion.ABAJO ) // Si el sprite se está movimiento hacia arriba o hacia abajo
        {
            velocidadEjeX = -INCREMENTO_VELOCIDAD;                          // Hago que se mueva en el eje x, hacia la derecha o izquiereda
            velocidadEjeY = 0;                                              // Deja de moverse en el eje y, hacia arriba o abajo
        }

        direccion = Direccion.IZQUIERDA;                                    // Hago que el sprite se mueva hacia la derecha
    }

    /**
     * Cambia la dirección del sprite hacia arriba
     */
    public void cambiarDireccionArriba()
    {
        if( !movimientoYArriba() )                                                  // Si el sprite no se está moviendo hacia arriba
            cambiarSentidoEjeY();                                                   // Cambio el sentido hacia arriba

        if( direccion == Direccion.DERECHA || direccion == Direccion.IZQUIERDA )    // Si el sprite se está movimiento hacia arriba o hacia abajo
        {
            velocidadEjeY = -INCREMENTO_VELOCIDAD;                                  // Hago que se mueva en el eje y, hacia arriba o abajo
            velocidadEjeX = 0;                                                      // Deja de moverse en el eje x, hacia la derecha o izquierda
        }

        direccion = Direccion.ARRIBA;                                               // Hago que el sprite se mueva hacia arriba
    }

    /**
     * Cambia la dirección del sprite hacia abajo
     */
    public void cambiarDireccionAbajo()
    {
        if( !movimientoYAbajo() )                                                   // Si el sprite no se está moviendo hacia abajo
            cambiarSentidoEjeY();                                                   // Cambio el sentido hacia abajo

        if( direccion == Direccion.DERECHA || direccion == Direccion.IZQUIERDA )    // Si el sprite se está movimiento hacia arriba o hacia abajo
        {
            velocidadEjeY = INCREMENTO_VELOCIDAD;                                   // Hago que se mueva en el eje y, hacia arriba o abajo
            velocidadEjeX = 0;                                                      // Deja de moverse en el eje x, hacia la derecha o izquierda
        }

        direccion = Direccion.ABAJO;                                               // Hago que el sprite se mueva hacia abajo
    }

    /**
     * Operación para dibujar el sprite
     * @param canvas Lienzo donde se va a dibujar el sprite
     */
    public void onDraw(Canvas canvas)
    {
        update();
        int posicionXImagenFrame = frameactual * ancho;
        int posicionYImagenFrame = direccion.toInt() * alto;
        Rect imagenspriteactual = new Rect(posicionXImagenFrame, posicionYImagenFrame, posicionXImagenFrame + ancho, posicionYImagenFrame + alto);
        Rect imagenspriteenpantalla = new Rect(x, y, x + ancho, y + alto);
        canvas.drawBitmap(imagensprite, imagenspriteactual, imagenspriteenpantalla, null);
    }

    /**
     * Cambia la imagen del sprite
     * @param imagensprite
     */
    public void setImagenSprite(Bitmap imagensprite)
    {
        this.imagensprite = imagensprite;
        this.ancho = imagensprite.getWidth() / BMP_COLUMNS;
        this.alto = imagensprite.getHeight() / BMP_ROWS;
    }

    /**
     * Comprueba si hay una colisión con el sprite con las coordenadas pasadas
     * @param x2 Coordenada x para ver si hay colisión
     * @param y2 Coordenada y para ser si hay colisión
     * @return Verdadero si las coordenadas (x2, y2) están dentro del área cubierta por el sprite
     */
    public boolean hayColision(float x2, float y2)
    {
        return (x2 > x) && (x2 < (x + ancho)) && (y2 > y) && (y2 < (y + alto));
    }

    /**
     * Devuelve la coordenada X del sprite
     * @return Posicion X del sprite
     */
    public float getX()
    {
        return x;
    }

    /**
     * Devuelve la coordenada x del sprite
     * @return Posición y del sprite
     */
    public float getY()
    {
        return y;
    }

    // ***** Funciones privadas de la clase *****

    /**
     * Modifica la posición del sprite
     * Mueve el sprite hacia la derecha o a la iquierda, cambiando la dirección cuando choca con los límites de la pantalla de juego
     */
    private void update()
    {
        // Muevo el sprite a la derecha o a la izquierda
        if(direccion == Direccion.DERECHA || direccion == Direccion.IZQUIERDA)
        {
            if (chocoEnElEjeX())
            {
                if (movimientoXDerecha())
                    cambiarDireccionIzquierda();
                else
                    cambiarDireccionDerecha();
            }
            x = x + velocidadEjeX;
        }
        else // Muevo el sprite hacia arriba o abajo
        {
            if( chocoEnElEjeY() )
            {
                if (movimientoYArriba())
                    cambiarDireccionAbajo();
                else
                    cambiarDireccionArriba();
            }
            else
            {
                y = y + velocidadEjeY;
            }
        }

        frameactual = ++frameactual % BMP_COLUMNS;
    }

    /**
     * Obtiene una dirección para el movimiento del sprite, opera según las velocidades en los ejes
     * @return Nueva dirección para el movimiento del sprite
     */
    private int obtenerDireccionMovimiento()
    {
        double direcciondouble = (Math.atan2(velocidadEjeX, velocidadEjeY) / (Math.PI / 2) + 2);
        int direccion = (int) Math.round(direcciondouble) % BMP_ROWS;
        return DIRECCION_ANIMACION[direccion];
    }

    /**
     * Cambia el sentido del movimiento del eje X
     */
    private void cambiarSentidoEjeX()
    {
        velocidadEjeX *= -1;
    }

    /**
     * Cambia el sentido del movimiento del eje Y
     */
    private void cambiarSentidoEjeY()
    {
        velocidadEjeY *= -1;
    }

    /**
     * Calcula si el sprite choca con la pantalla por la izquierda en el eje X
     * @return Verdadero si choca, falso en caso contrario
     */
    private Boolean chocoEnElEjeXIzquierda()
    {
        return (x > pantalla.getWidth() - ancho - velocidadEjeX);
    }

    /**
     * Calcula si el sprite choca con la pantalla por la derecha en el eje X
     * @return Verdadero si choca, falso en caso contrario
     */
    private Boolean chocoEnElEjeXDerecha()
    {
        return (x + velocidadEjeX < 0);
    }

    /**
     * Calcula si el sprite choca con la pantalla en el eje X
     * @return Verdadero si choca, falso en caso contrario
     */
    private Boolean chocoEnElEjeX()
    {
        return (chocoEnElEjeXIzquierda() || chocoEnElEjeXDerecha());
    }

    /**
     * Calcula si el sprite se mueve hacia la izquierda en el eje X
     * @return Verdadero si se mueve hacia la izquierda, falso en caso contrario
     */
    private Boolean movimientoXIzquierda()
    {
        return (velocidadEjeX < 0);
    }

    /**
     * Calcula si el sprite se mueve hacia la derecha en el eje X
     * @return Verdadero si se mueve hacia la derecha, falso en caso contrario
     */
    private Boolean movimientoXDerecha()
    {
        return !movimientoXIzquierda();
    }

    /**
     * Calcula si el sprite se mueve hacia arriba en el eje Y
     * @return Verdadero si se mueve hacia arriba, falso en caso contrario
     */
    private Boolean movimientoYArriba()
    {
        return (velocidadEjeY < 0);
    }

    /**
     * Calcula si el sprite se mueve hacia abajo en el eje Y
     * @return Verdadero si se mueve hacia abajo, falso en caso contrario
     */
    private Boolean movimientoYAbajo()
    {
        return !movimientoYArriba();
    }

    /**
     * Calcula si el sprite choca con la pantalla en el eje Y
     * @return Verdadero si choca, falso en caso contrario
     */
    private Boolean chocoEnElEjeY()
    {
        return (y > pantalla.getHeight() - alto - velocidadEjeY || y + velocidadEjeY < 0);
    }

    // ******************************************
}
