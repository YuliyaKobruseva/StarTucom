/*
 He omitido el metodo set porque los datos del ser de esta especie no pueden ser modificados.
Y añadido el metodo toString() que permite representar datos del objeto como String
He cambiado el tipo del parametro AENAR, ahora es boolean y final ya que no puede ser modificado
 */
package race;

/**
 *
 * @author Yuli
 */
public class Andorian extends Race {

    private final boolean AENAR;

    public Andorian(String name, boolean aenar) {
        super(name);
        this.AENAR = aenar;
    }

    /**
     * Get the value of aenar
     *
     * @return the value of aenar
     */
    public boolean isAenar() {
        return AENAR;
    }

    /**
     * Method that allows to represent any object as a string
     *
     * @return string representation of the object
     */
    @Override
    public String toString() {
        String isAenar;
        if (AENAR == true) {
            isAenar = "aenar";
        } else {
            isAenar = "noaenar";
        }
        return getClass().getSimpleName() + "-" + super.getName() + "-" + isAenar;
    }

}
