package modelo;

public enum Direccion
{
    ARRIBA("ARRIBA", 3),
    ABAJO("ABAJO", 0),
    IZQUIERDA("IZQUIERDA", 1),
    DERECHA("DERECHA", 2);
    private String stringValue;
    private int intValue;

    // El constructor de los enumerados será privado por defecto
    Direccion(String toString, int value)
    {
        stringValue = toString;
        intValue = value;
    }

    @Override
    public String toString()
    {
        return stringValue;
    }

    public int toInt()
    {
        return intValue;
    }
}
