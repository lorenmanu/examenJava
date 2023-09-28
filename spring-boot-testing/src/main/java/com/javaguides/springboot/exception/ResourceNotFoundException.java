package com.javaguides.springboot.exception;


/*
 * Clase "ResourceNotFoundException" para manejar excepciones cuando no se encuentra los datos requeridos en la BD H2, usado por
 *  por ejemplo en:
 *          --> /src/main/java/com.javaguides.springboot/service/impl/PriceBrandServiceImpl linea 38
 *          --> /src/test/java/com.javaguides.springboot/service/PriceBrandServiceTests linea 102
 */
public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String message){
        super(message);
    }

    public ResourceNotFoundException(String message, Throwable cause){
        super(message, cause);
    }
}
