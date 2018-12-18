/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Yuli
 */
public class MyException extends Exception {

    private int numException;
    
    /**
     * Possible error type
     */

    private List<String> exceptionType = Arrays.asList(
            "< ERROR 001: N.º de argumentos inválido >",
            "< ERROR 002: Especie incorrecta >",
            "< ERROR 003: Planeta incorrecto >",
            "< ERROR 004: Dato incorrecto >",
            "< ERROR 005: No se puede registrar ese ser en ese planeta >",
            "< ERROR 006: Ya existe un ser censado con ese nombre >",
            "< ERROR 007: No existe ningún ser con ese nombre >",
            "< ERROR 008: El ser no permite ser modificado >",
            "< ERROR 009: Operación incorrecta >",
            "< ERROR 010: Edad incorrecta >",
            "< ERROR 011: Nivel de meditación incorrecto >",
            "< ERROR 012: Valor de fuerza incorrecto >",
            "< Nobody registered yet >"
    );

    /**
     * Class Constructor
     * @param numException 
     */
    public MyException(int numException) {
        this.numException = numException;
    }

    /**
     * Get the value of numException
     *
     * @return the value of exceptionType[numException]
     */
    public String getNumException() {
        return exceptionType.get(numException);
    }
    
    

}
