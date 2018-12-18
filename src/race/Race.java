/*
 * Respecto a UML:
    He quitado funcion setName() porque el nombre no puede ser modificado
    He quitado parametro planeta porque es reduntante, ya que podemos obtener este valor del planeta en vive ser
 */
package race;

/**
 *
 * @author Yuli
 */
public abstract class Race {

    private final String NAME;

    public Race(String name) {
        this.NAME = name;
    }

    /**
     * Get the value of name
     *
     * @return the value of name
     */
    public String getName() {
        return NAME;
    }

}
