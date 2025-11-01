package com.docsarea.mappers;

import org.modelmapper.ModelMapper;

public class DefaultMapper {

    private final static ModelMapper modelMapper ;

    static{
        modelMapper = new ModelMapper() ;
    }

    private DefaultMapper(){}

    public static ModelMapper getModelMapper(){
        return modelMapper ;
    }

}
