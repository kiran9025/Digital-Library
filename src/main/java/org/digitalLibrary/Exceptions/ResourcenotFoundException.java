package org.digitalLibrary.Exceptions;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ResourcenotFoundException extends RuntimeException{
    public ResourcenotFoundException(Class<?> clazz, String fieldname, String fieldvalue){
        super("Resource of type " + clazz.getSimpleName() + " with field name " + fieldname + " = " + fieldvalue + " could not be found");
        log.error("Resource of type {} wiht field name{} = {} could not be found ",clazz,fieldname,fieldvalue);
    }

    public ResourcenotFoundException(String message){
        super(message);
        log.error(message);
    }
}




