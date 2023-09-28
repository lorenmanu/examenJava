package com.javaguides.springboot.mapper;


import com.javaguides.springboot.dto.PricesDto;
import com.javaguides.springboot.dto.PricesExamDto;
import com.javaguides.springboot.model.PRICES;

/*
 * Clase PricesExamMapper usada en /src/main/java/com.javaguides.springboot/controller/PriceBrandController para la correcta
 * serialización y envío de datos REST, contiene todos los datos relevantes de la Clase Price para el examen
 */

public class PricesExamMapper {
    // Convert User JPA Entity into UserDto
    public static PricesExamDto mapToPricesExamDto(PRICES prices){
        PricesExamDto pricesExamDto = new PricesExamDto(
                prices.getPRODUCT_ID(),
                prices.getBrand().getId(),
                prices.getPRICE_LIST(),
                prices.getPrice(),
                prices.getSTART_DATE(),
                prices.getEND_DATE()
        );
        return pricesExamDto;
    }
}
