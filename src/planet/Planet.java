/*
 * Respecto a UML he quitado la propiedad calurosa porque no aporta ningun valor para la clase, el metodo puedeVivir() ahora se encuentra en 
la clase del planeta que se encarga de controlar los habitantes que pueden vivir en ella (planetas Andoria y Vulcano).
He añadido el metodo order() que ordena array de habitantes del planeta por 2 parametros
    
 */
package planet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import race.Race;

/**
 *
 * @author Yuli
 */
public class Planet {

    private String name;
    private boolean flowerRed;
    private ArrayList<Race> population = new ArrayList<>();

    public Planet(String name, boolean flowerRed) {
        this.name = name;
        this.flowerRed = flowerRed;
    }

    /**
     * Get the value of flowerRed
     *
     * @return the value of flowerRed
     */
    public boolean isFlowerRed() {
        return flowerRed;
    }

    /**
     * Set the value of flowerRed
     *
     * @param flowerRed new value of flowerRed
     */
    public void setFlowerRed(boolean flowerRed) {
        this.flowerRed = flowerRed;
    }

    /**
     * Get the value of name
     *
     * @return the value of name
     */
    public String getName() {
        return name;
    }

    /**
     * Set the value of name
     *
     * @param name new value of name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the value of population
     *
     * @return the value of population
     */
    public ArrayList getPopulation() {
        return population;
    }

    /**
     * Set the value of population
     *
     * @param creature Object of type Race
     */
    public void setPopulation(Race creature) {
        this.population.add(creature);
    }

    /**
     *
     * Method that returns quantity of creatures of the planet
     *
     * @return size of population ArrayList
     */
    public int sizePopulation() {
        return this.population.size();
    }

    /**
     * Method that orders Population of planet by two parameters: first by species, then by name
     */
    public void order() {
        Collections.sort(this.population, new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                String e1 = ((Race) o1).getClass().getSimpleName();
                String e2 = ((Race) o2).getClass().getSimpleName();
                int sComp = e1.compareTo(e2);
                if (sComp != 0) {
                    return sComp;
                }
                String n1 = ((Race) o1).getName();
                String n2 = ((Race) o2).getName();
                return n1.compareTo(n2);
            }
        });
    }

}
