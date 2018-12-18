/*
 * Respecto a UML: he creado esta clase porque tiene una condicion diferente del resto de planetas
 */
package planet;

/**
 *
 * @author Yuli
 */
public class Vulcano extends Planet {

    public Vulcano(String name, boolean flowerRed) {
        super(name, flowerRed);
    }

    /**
     * Method that lets you know if the concret Race can live on this planet
     * 
     * @param race Object of type Race
     * @return boolean true/false
     */
    public boolean canLive(Object race) {
        return !(race.getClass().getSimpleName().equals("Andorian") || race.getClass().getSimpleName().equals("Klingon"));
    }
}
