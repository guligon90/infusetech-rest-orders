package com.infusetech.rest.orders.mappers;

import com.infusetech.rest.orders.models.Order;
import com.infusetech.rest.orders.models.dto.OrderDTO;

import org.modelmapper.ModelMapper;

public class ModelDtoMapper {
    public static Order getOrder(OrderDTO empDto){
        if(empDto == null) return null;

        ModelMapper modelMapper = new ModelMapper();
        
        return modelMapper.map(empDto, Order.class);
    }

    public static OrderDTO getOrderDTO(Order emp){
        if(emp == null) return null;

        ModelMapper modelMapper = new ModelMapper();
        
        return modelMapper.map(emp, OrderDTO.class);
    }
}
