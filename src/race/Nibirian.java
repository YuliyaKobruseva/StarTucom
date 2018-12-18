/*
Respecto a UML :
He añadido el metodo toString() que permite representar datos del objeto como String
He omitido el metodo set porque los datos del ser de esta especie no pueden ser modificados
 */
package race;

import exceptions.MyException;

/**
 *
 * @author Yuli
 */
public class Nibirian extends Race{
    
    private final String FOODTYPE;

    public Nibirian(String name, String foodType) throws MyException {
        super(name);
        if(!foodType.equalsIgnoreCase("vegetarian") && !foodType.equalsIgnoreCase("novegetarian")){
            throw new MyException(3);
        }
        this.FOODTYPE=foodType;
    }
    

    /**
     * Get the value of foodType
     *
     * @return the value of foodType
     */
    public String getFoodType() {
        return FOODTYPE;
    }
    
     /**
     * Method that allows to represent any object as a string
     * 
     * @return string representation of the object
     */
    @Override
    public String toString() {
//        String vege = null;
//        if(foodType.equalsIgnoreCase("vegetarian")){
//            vege="vegetarian";
//        }else if(foodType.equalsIgnoreCase("novegetarian")){
//            vege="novegetarian";
//        }
        return getClass().getSimpleName()+"-"+super.getName()+"-"+this.FOODTYPE;
    }

   }
