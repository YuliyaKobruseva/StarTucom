/*
 * Respecto a UML he añadido el metodo toString() que permite representar datos del objeto como String
 */
package race;

import exceptions.MyException;

/**
 *
 * @author Yuli
 */
public class Human extends Race{
    
        private int age;

    public Human(String name,int age) throws MyException {
        super(name);
        setAge(age);
    }
        

    /**
     * Get the value of age
     *
     * @return the value of age
     */
    public int getAge() {
        return age;
    }

    /**
     * Set the value of age
     *
     * @param age new value of age
     * @throws exceptions.MyException
     */
    public void setAge(int age) throws MyException {
        if(age < 0 || age > 130){
            throw new MyException(9);
        }
        this.age = age;
    }

    /**
     * Method that allows to represent any object as a string
     * 
     * @return string representation of the object
     */
    @Override
    public String toString() {
        return getClass().getSimpleName()+"-"+super.getName()+"-"+this.age;
    }
    
}
