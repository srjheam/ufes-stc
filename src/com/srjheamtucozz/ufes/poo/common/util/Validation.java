package com.srjheamtucozz.ufes.poo.common.util;

import java.util.LinkedList;

public class Validation {
    private LinkedList<String> errors;

    public Validation(){
        errors = new LinkedList<String>();
    }

    public LinkedList<String> getErrors() {
        return new LinkedList<>(errors);
    }

    public void addError(String error){
        errors.add(error);
    }

    public boolean hasErrors(){
        return errors.size() > 0;
    }
}
