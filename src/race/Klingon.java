/*
 * Respecto a UML he añadido el metodo toString() que permite representar datos del objeto como String
 */
package race;

import exceptions.MyException;

/**
 *
 * @author Yuli
 */
public class Klingon extends Race{
    
    private int forceLevel;

    public Klingon(String name, int forceLevel) throws MyException {
        super(name);
        setForceLevel(forceLevel);
    }
    

    /**
     * Get the value of forceLevel
     *
     * @return the value of forceLevel
     */
    public int getForceLevel() {
        return forceLevel;
    }

    /**
     * Set the value of forceLevel
     *
     * @param forceLevel new value of forceLevel
     * @throws exceptions.MyException
     */
    public void setForceLevel(int forceLevel) throws MyException {
        if(forceLevel < 50 || forceLevel > 350){
            throw new MyException(11);
        }
        this.forceLevel = forceLevel;
    }
    
     /**
     * Method that allows to represent any object as a string
     * 
     * @return string representation of the object
     */
    @Override
    public String toString() {
        return getClass().getSimpleName()+"-"+super.getName()+"-"+this.forceLevel;
    }

}
