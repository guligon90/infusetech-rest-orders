package com.infusetech.rest.orders.common.mappers;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.modelmapper.ModelMapper;

public class ModelDtoMapper {
    public static Map<String, Object> entityToMap(Object entity) {
        Map<String, Object> map = new HashMap<>();
        
        for (Field field : entity.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            
            try {
                map.put(field.getName(), field.get(entity));
            } catch (Exception e) {
                continue;
            }
        }
        
        return map;
    }

    public static <Model, DTO> Model getModel(DTO dto, Class<Model> modelClass){
        if(dto == null) return null;

        ModelMapper modelMapper = new ModelMapper();
        
        return modelMapper.map(dto, modelClass);
    }

    public static <Model, DTO> DTO getDTO(Model model, Class<DTO> dtoClass){
        if(model == null) return null;

        ModelMapper modelMapper = new ModelMapper();
        
        return modelMapper.map(model, dtoClass);
    }


    public static <Model, DTO> Model updateModelWithDTOData(Model model, DTO dto, Class<Model> modelClass) {
        Map<String, Object> dtoMap = entityToMap(dto);
        Map<String, Object> modelMap = entityToMap(model);

        for (Entry<String, Object> entry : dtoMap.entrySet()) {
            if (entry.getValue() != null) 
                modelMap.put(entry.getKey(), entry.getValue());
            
        }

        ModelMapper modelMapper = new ModelMapper();
        
        return modelMapper.map(modelMap, modelClass);
    }
}
