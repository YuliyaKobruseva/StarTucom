/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package startucom;

import exceptions.MyException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import planet.*;
import race.*;

/**
 *
 * @author Yuli
 */
public class StarTucom {

    public static ArrayList<Planet> planets = new ArrayList<>();

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        boolean salir = false;
        String ruta = createFolder();
        createPlanets();
        try {
            for (Planet p : planets) {
                loadDataPlanet(planets, ruta, p.getName());
            }
        } catch (MyException ex) {
            System.out.println(ex.getNumException());
        }
//        messageStartGame();
        while (!salir) {
            try {
                String linea = br.readLine().trim();
                List<String> commandUser = Arrays.asList(linea.split(" "));
                String optionUser = "";
                try {
                    optionUser = checkOption(commandUser.get(0));
                    switch (optionUser.toUpperCase()) {
                        case "C":
                            createCreatureRace(commandUser, planets, ruta);
                            break;
                        case "B":
                            deleteCreature(commandUser, planets, ruta);
                            break;
//                        case "H":
//                            help();
//                            break;
                        case "L":
                            populationByPlanet(commandUser, planets);
                            break;
                        case "M":
                            modifyCreature(commandUser, planets, ruta);
                            break;
                        case "P":
                            populationByRace(commandUser, planets);
                            break;
                        case "X":
                            if (checkNumberOption(commandUser) == 1) {
                                salir = true;
                            }
                            break;
                    }
                } catch (MyException ex) {
                    System.out.println(ex.getNumException());
                }
            } catch (IOException ex) {
                System.out.println("Error al leer: " + ex.getMessage());
            }
        }
    }

    /**
     * method that allows to modify the property of creature
     *
     * @param commandUser list of user commands
     * @param planets arraylist of existing planets
     * @param ruta path where the file with the data is
     */
    public static void modifyCreature(List<String> commandUser, ArrayList<Planet> planets, String ruta) {
        try {
            if (checkNumberOption(commandUser) == 3) {
                if (checkNameCreature(commandUser.get(1), planets)) {
                    for (Planet p : planets) {
                        ArrayList<Race> creaturesPlanet = p.getPopulation();
                        for (Race r : creaturesPlanet) {
                            if (r.getName().equalsIgnoreCase(commandUser.get(1))) {
                                if (r.getClass().getSimpleName().equalsIgnoreCase("Andorian") || r.getClass().getSimpleName().equalsIgnoreCase("Nibirian")) {
                                    throw new MyException(7);
                                } else {

                                    if (r.getClass().getSimpleName().equalsIgnoreCase("Vulcan")) {
                                        ((Vulcan) r).setMeditationLevel(Integer.parseInt(commandUser.get(2)));
                                    } else if (r.getClass().getSimpleName().equalsIgnoreCase("Human")) {
                                        ((Human) r).setAge(Integer.parseInt(commandUser.get(2)));
                                    } else if (r.getClass().getSimpleName().equalsIgnoreCase("Klingon")) {
                                        ((Klingon) r).setForceLevel(Integer.parseInt(commandUser.get(2)));
                                    }
                                    rewriteCreatureOnFile(p.getPopulation(), ruta, p.getName());
                                    System.out.println("< OK: Ser modificado correctamente >");
                                }
                            }
                        }
                    }
                } else {
                    throw new MyException(6);
                }
            }
        } catch (MyException ex) {
            System.out.println(ex.getNumException());
        }
    }

    /**
     * Method that creates the planets when starting program
     */
    public static void createPlanets() {
        planets.add(new Vulcano("Vulcano", false));
        planets.add(new Andoria("Andoria", false));
        planets.add(new Planet("Nibiru", true));
        planets.add(new Planet("Kronos", false));
    }

    /**
     * Method to load data from files
     *
     * @param planets arrayList of existing planets
     * @param ruta path where the file with the data is
     * @param namePlanet name of planet for which to load data
     * @throws exceptions.MyException
     */
    public static void loadDataPlanet(ArrayList<Planet> planets, String ruta, String namePlanet) throws MyException {
        String name = namePlanet;
        File f = new File(ruta + name + ".txt");
        FileReader fr = null;
        if (f.exists()) {
            try {
                fr = new FileReader(f);
                BufferedReader br = new BufferedReader(fr);
                String linea;
                while ((linea = br.readLine()) != null) {
                    List<String> data = Arrays.asList(linea.split("-"));
                    for (Planet p : planets) {
                        if (p.getName().equalsIgnoreCase(name)) {
                            if (data.get(0).equalsIgnoreCase("Andorian")) {
                                if (data.get(2).equalsIgnoreCase("aenar")) {
                                    p.setPopulation(new Andorian(data.get(1), true));
                                } else {
                                    p.setPopulation(new Andorian(data.get(1), false));
                                }
                            } else if (data.get(0).equalsIgnoreCase("Human")) {
                                p.setPopulation(new Human(data.get(1), Integer.parseInt(data.get(2))));
                            } else if (data.get(0).equalsIgnoreCase("Nibirian")) {
                                p.setPopulation(new Nibirian(data.get(1), data.get(2)));
                            } else if (data.get(0).equalsIgnoreCase("Vulcan")) {
                                p.setPopulation(new Vulcan(data.get(1), Integer.parseInt(data.get(2))));
                            } else if (data.get(0).equalsIgnoreCase("Klingon")) {
                                p.setPopulation(new Klingon(data.get(1), Integer.parseInt(data.get(2))));
                            }
                        }
                    }
                }
                br.close();
            } catch (IOException ex) {
                System.out.println("Error al leer: " + ex.getMessage());
            }
        }
    }

    /**
     * method that create the folder for dates
     *
     * @return path of folder
     */
    public static String createFolder() {
        String rutaActual = System.getProperty("user.dir") + File.separator + "datos";
        File folder = new File(rutaActual);
        folder.mkdir();
        return rutaActual + File.separator;
    }

    /**
     * Method that allows to check if the option that the user entered is
     * correct
     *
     * @param option firts parameter of user command
     * @return parameter of user command
     * @throws MyException
     */
    public static String checkOption(String option) throws MyException {
        if (!option.equalsIgnoreCase("b") && !option.equalsIgnoreCase("c") && !option.equalsIgnoreCase("l")
                && !option.equalsIgnoreCase("m") && !option.equalsIgnoreCase("p") && !option.equalsIgnoreCase("x") && !option.equalsIgnoreCase("h")) {
            throw new MyException(8);
        }
        return option;
    }

    /**
     * Method that allows you to create a new creature and add to planet
     *
     * @param commandUser list of user command with creature data
     * @param planets arrayList of planet where save a creature
     * @param ruta path where the file with the data is
     * @throws MyException
     */
    public static void createCreatureRace(List<String> commandUser, ArrayList<Planet> planets, String ruta) throws MyException {
        try {
            if (checkNumberOption(commandUser) == 5) {
                if (checkNameRace(commandUser.get(1))) {
                    if (checkNamePlanet(commandUser.get(2))) {
                        Race newCreature = createCreature(commandUser, planets);
                        if (!checkNameCreature(newCreature.getName(), planets)) {
                            for (Planet p : planets) {
                                if (p.getName().equalsIgnoreCase(commandUser.get(2))) {
                                    if (p.getClass().getSimpleName().equalsIgnoreCase("Vulcano")) {
                                        if (!((Vulcano) p).canLive(newCreature)) {
                                            throw new MyException(4);
                                        }
                                    } else if (p.getClass().getSimpleName().equalsIgnoreCase("Andoria")) {
                                        if (!((Andoria) p).canLive(newCreature)) {
                                            throw new MyException(4);
                                        }
                                    } else if (p.getName().equalsIgnoreCase("Kronos")) {
                                        if (p.sizePopulation() >= 30) {
                                            throw new MyException(4);
                                        }
                                    } else if (newCreature.getClass().getSimpleName().equalsIgnoreCase("Nibirian")) {
                                        if (!p.isFlowerRed()) {
                                            throw new MyException(4);
                                        }
                                    }
                                    writeData(newCreature, ruta, p.getName());
                                    p.setPopulation(newCreature);
                                    System.out.println("< OK: Ser censado correctamente en el planeta >");
                                }
                            }
                        } else {
                            throw new MyException(5);
                        }
                    }
                }
            }
        } catch (MyException ex) {
            System.out.println(ex.getNumException());
        }
    }

    /**
     * Method to write data to file
     *
     * @param creature object that will be written in file
     * @param ruta path where the file with the data is
     * @param namePlanet name of file
     */
    public static void writeData(Race creature, String ruta, String namePlanet) {
        String rutaFichero = ruta + (namePlanet.toLowerCase() + ".txt");
        File f = new File(rutaFichero);
        FileWriter fw = null;
        try {
            fw = new FileWriter(f, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(creature.toString());
            bw.newLine();
            bw.close();
        } catch (IOException ex) {
            System.out.println("Error al escribir: " + ex.getMessage());
        }
    }

    /**
     * method to eliminate a creature from planet and from file
     *
     * @param commandUser list of user command
     * @param planets arrayList of existing plantes
     * @param ruta path where the file with the data is
     * @throws MyException
     */
    public static void deleteCreature(List<String> commandUser, ArrayList<Planet> planets, String ruta) throws MyException {
        Race creature = null;
        Planet planet = null;
        if (checkNumberOption(commandUser) == 2) {
            for (Planet p : planets) {
                if (!p.getPopulation().isEmpty()) {
                    for (Race r : (ArrayList<Race>) p.getPopulation()) {
                        if (commandUser.get(1).equalsIgnoreCase(r.getName())) {
                            creature = r;
                            planet = p;
                        }
                    }
                }
            }
            if (creature == null) {
                throw new MyException(6);
            } else {
                ArrayList<Race> creaturesPlanet = planet.getPopulation();
                creaturesPlanet.remove(creature);
                rewriteCreatureOnFile(creaturesPlanet, ruta, planet.getName());
                System.out.println("< OK: Ser borrado correctamente >");
            }
        }
    }

    /**
     * Method to rewrite a file
     *
     * @param creaturesPlanet arrayList of existing planets
     * @param ruta path where the file with the data is
     * @param namePlanet name of file
     */
    public static void rewriteCreatureOnFile(ArrayList<Race> creaturesPlanet, String ruta, String namePlanet) {
        String rutaFichero = ruta + (namePlanet.toLowerCase() + ".txt");
        File f = new File(rutaFichero);
        FileWriter fw = null;
        try {
            fw = new FileWriter(f);
            BufferedWriter bw = new BufferedWriter(fw);
            for (Race r : creaturesPlanet) {
                bw.write(r.toString());
                bw.newLine();
            }
            bw.close();
        } catch (IOException ex) {
            System.out.println("Error al escribir: " + ex.getMessage());
        }
    }

    /**
     * Method to create object of creature
     *
     * @param commandUser list of user command
     * @param planets arrayList of existing planets
     * @return object of creature
     * @throws MyException
     */
    public static Race createCreature(List<String> commandUser, ArrayList<Planet> planets) throws MyException {
        Race creature = null;
        if (!checkNameCreature(commandUser.get(3), planets)) {
            int number = 0;
            if (commandUser.get(1).equalsIgnoreCase("Human") || commandUser.get(1).equalsIgnoreCase("Klingon") || commandUser.get(1).equalsIgnoreCase("Vulcan")) {
                if (isNumeric(commandUser.get(4)) == true) {
                    number = Integer.parseInt(commandUser.get(4));
                } else {
                    throw new MyException(3);
                }
            }
            if (commandUser.get(1).equalsIgnoreCase("Andorian")) {
                if (commandUser.get(4).equalsIgnoreCase("aenar")) {
                    creature = (new Andorian(commandUser.get(3), true));
                } else {
                    creature = (new Andorian(commandUser.get(3), false));
                }
            } else if (commandUser.get(1).equalsIgnoreCase("Human")) {
                creature = (new Human(commandUser.get(3), number));
            } else if (commandUser.get(1).equalsIgnoreCase("Klingon")) {
                creature = (new Klingon(commandUser.get(3), number));
            } else if (commandUser.get(1).equalsIgnoreCase("Nibirian")) {
                creature = (new Nibirian(commandUser.get(3), commandUser.get(4)));
            } else if (commandUser.get(1).equalsIgnoreCase("Vulcan")) {
                creature = (new Vulcan(commandUser.get(3), number));
            }
        } else {
            throw new MyException(5);
        }
        return creature;
    }

    /**
     * Method to check if the String is a number
     *
     * @param cadena String parameter to validate
     * @return boolean
     */
    public static boolean isNumeric(String cadena) {
        boolean resultado;
        try {
            Integer.parseInt(cadena);
            resultado = true;
        } catch (NumberFormatException excepcion) {
            resultado = false;
        }
        return resultado;
    }

    /**
     * Method to check if the creature name exists
     *
     * @param name name of creature
     * @param planets
     * @return boolean
     */
    public static boolean checkNameCreature(String name, ArrayList<Planet> planets) {
        boolean nameExist = false;
        for (Planet p : planets) {
            for (Race r : (ArrayList<Race>) p.getPopulation()) {
                if (r.getName().equalsIgnoreCase(name)) {
                    nameExist = true;
                }
            }
        }
        return nameExist;
    }

    /**
     * Method that shows the list of creatures of each planet
     *
     * @param commandUser list with parameters of user command
     * @param planets arrayList of existing planets
     */
    public static void populationByPlanet(List<String> commandUser, ArrayList<Planet> planets) {
        boolean populationIsEmpty = true;
        try {
            if (checkNumberOption(commandUser) == 1) {
                for (Planet p : planets) {
                    if (!p.getPopulation().isEmpty()) {
                        populationIsEmpty = false;
                    }
                }
                if (populationIsEmpty) {
                    throw new MyException(12);
                } else {
                    System.out.println("< POPULATION BY PLANET >");
                    for (Planet p : planets) {
                        if (!p.getPopulation().isEmpty()) {
                            p.order();
                            System.out.println("< " + p.getName() + " >");
                            for (Race s : (ArrayList<Race>) p.getPopulation()) {
                                System.out.println(s.toString());
                            }
                        }
                    }
                }

            }
        } catch (MyException ex) {
            System.out.println(ex.getNumException());
        }
    }

    /**
     * Method that shows the list of creatures of the specified race
     *
     * @param commandUser list of user command
     * @param planets arrayList pf existing planets
     * @throws MyException
     */
    public static void populationByRace(List<String> commandUser, ArrayList<Planet> planets) throws MyException {
        try {
            if (checkNumberOption(commandUser) == 2) {
                String nameRace = commandUser.get(1);
                if (checkNameRace(commandUser.get(1))) {
                    System.out.println("< POPULATION BY RACE >");
                    if (raceExist(nameRace, planets)) {
                        for (Planet p : planets) {
                            for (Race r : (ArrayList<Race>) p.getPopulation()) {
                                if (r.getClass().getSimpleName().equalsIgnoreCase(nameRace)) {
                                    System.out.println(r.toString() + "-" + p.getName());
                                }
                            }
                        }
                    }
                }
            }
        } catch (MyException ex) {
            System.out.println(ex.getNumException());
        }
    }

    /**
     * Method to check if the race exists
     *
     * @param nameRace name of race
     * @param plnets arrayList of existing planets
     * @return boolean
     * @throws MyException
     */
    public static boolean raceExist(String nameRace, ArrayList<Planet> plnets) throws MyException {
        boolean exist = false;
        for (Planet p : planets) {
            for (Race r : (ArrayList<Race>) p.getPopulation()) {
                if (nameRace.equalsIgnoreCase(r.getClass().getSimpleName())) {
                    exist = true;
                }
            }
        }
        return exist;
    }

    /**
     * Method that checks if the user command has the correct number of parameters
     *
     * @param commandUser list of user command
     * @return number of parameters in command user
     * @throws MyException
     */
    public static int checkNumberOption(List<String> commandUser) throws MyException {
        int quantityArg = 0;
        if (commandUser.get(0).equalsIgnoreCase("c") && commandUser.size() == 5) {
            quantityArg = 5;
        } else if ((commandUser.get(0).equalsIgnoreCase("b") || commandUser.get(0).equalsIgnoreCase("p")) && commandUser.size() == 2) {
            quantityArg = 2;
        } else if ((commandUser.get(0).equalsIgnoreCase("l") || commandUser.get(0).equalsIgnoreCase("x") || commandUser.get(0).equalsIgnoreCase("h")) && commandUser.size() == 1) {
            quantityArg = 1;
        } else if (commandUser.get(0).equalsIgnoreCase("m") && commandUser.size() == 3) {
            quantityArg = 3;
        } else {
            throw new MyException(0);
        }
        return quantityArg;
    }

    /**
     * Method to check if the race name exists
     *
     * @param nameRace name of race
     * @return boolean
     * @throws MyException
     */
    public static boolean checkNameRace(String nameRace) throws MyException {
        if (!nameRace.equalsIgnoreCase("Andorian") && !nameRace.equalsIgnoreCase("Human") && !nameRace.equalsIgnoreCase("Klingon")
                && !nameRace.equalsIgnoreCase("Nibirian") && !nameRace.equalsIgnoreCase("Vulcan")) {
            throw new MyException(1);
        }
        return true;
    }

    /**
     * Method to check if the planet name exists
     *
     * @param namePlanet name of planet
     * @return boolean
     * @throws MyException
     */
    public static boolean checkNamePlanet(String namePlanet) throws MyException {
        if (!namePlanet.equalsIgnoreCase("Vulcano") && !namePlanet.equalsIgnoreCase("Andoria") && !namePlanet.equalsIgnoreCase("Nibiru")
                && !namePlanet.equalsIgnoreCase("Kronos")) {
            throw new MyException(2);
        }
        return true;
    }

    /**
     * Method that shows the commands available in the program
     */
//    public static void help() {
//        System.out.println("........................................................");
//        System.out.println("Aqui tienes libreta de comandos que te servira de ayuda");
//        System.out.println("........................................................");
//        System.out.println("B - Borrar un ser \n Ejemplo de entrada:'C Spock'");
//        System.out.println("C - Censar un ser \n Ejemplo de entrada:'C Vulcan Vulcano Spock 10'");
//        System.out.println("H - Ayuda \n Ejemplo de entrada:'H'");
//        System.out.println("L - Listado de seres por planeta \n Ejemplo de entrada:'L'");
//        System.out.println("M - Modificar propiedad de un ser \n Ejemplo de entrada:'M Spock 9'");
//        System.out.println("P - Mostrar seres de una especie \n Ejemplo de entrada:'P Vulcan'");
//        System.out.println("X - Salir \n Ejemplo de entrada:'X'");
//    }
    /**
     * Method that shows welcome message to the program
     */
//    public static void messageStartGame() {
//        System.out.println("****************************");
//        System.out.println("   BIENVENIDO A STARTUCOM   ");
//        System.out.println("****************************");
//        System.out.println("El rey Kert Rats del multiverso StarTucom quiere tener un censo organizado "
//                + "de las poblaciones de sus planetas y te pide ayuda.\n"
//                + "Al rey le gustaría empezar por censar los siguientes 4 planetas: Vulcano, "
//                + "Andoria, Nibiru y Kronos. En cada uno de ellos,\n se irán dando de alta seres de "
//                + "diferentes especies y necesita que queden registrados de forma correcta. "
//                + "Además, necesita gestionar ciertos conflictos \n entre especies por lo que la "
//                + "aplicación deberá facilitarle la tarea, para conseguir que su multiverso viva en paz.");
//        help();
//    }
}

