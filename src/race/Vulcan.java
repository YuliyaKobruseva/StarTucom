/*
 * Respecto a UML he añadido el metodo toString() que permite representar datos del objeto como String
 */
package race;

import exceptions.MyException;

/**
 *
 * @author Yuli
 */
public class Vulcan extends Race {

    private int meditationLevel;

    public Vulcan(String name,int meditationLevel) throws MyException {
        super(name);
        setMeditationLevel(meditationLevel);
    }

    /**
     * Get the value of meditationLevel
     *
     * @return the value of meditationLevel
     */
    public int getMeditationLevel() {
        return meditationLevel;
    }

    /**
     * Set the value of meditationLevel
     *
     * @param meditationLevel new value of meditationLevel
     * @throws exceptions.MyException
     */
    public void setMeditationLevel(int meditationLevel) throws MyException {

        if (meditationLevel < 0 || meditationLevel > 10) {
            throw new MyException(10);
        }
        this.meditationLevel = meditationLevel;
    }
    
     /**
     * Method that allows to represent any object as a string
     * 
     * @return string representation of the object
     */
    @Override
    public String toString() {
        return getClass().getSimpleName()+"-"+super.getName()+"-"+this.meditationLevel;
    }

}
