package com.javaguides.springboot.mapper;


import com.javaguides.springboot.dto.PricesDto;
import com.javaguides.springboot.model.PRICES;
import com.javaguides.springboot.repository.BrandRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;


/*
 * Clase PricesMapper usada en /src/main/java/com.javaguides.springboot/controller/PriceBrandController para la correcta
 * serialización y envío de datos REST, contiene todos los datos relevantes de la Clase Price
 */
public class PricesMapper {
    // Convert User JPA Entity into UserDto
    public static PricesDto mapToPricesDto(PRICES prices){
        PricesDto pricesDto = new PricesDto(
                prices.getPRICE_ID(),
                prices.getBrand().getId(),
                prices.getSTART_DATE(),
                prices.getEND_DATE(),
                prices.getPRICE_LIST(),
                prices.getPRODUCT_ID(),
                prices.getPRIORITY(),
                prices.getPrice(),
                prices.getCURR()
        );
        return pricesDto;
    }

    // Convert UserDto into User JPA Entity
    public static PRICES mapToPrices(PricesDto pricesDto){
        PRICES prices = new PRICES(
                pricesDto.getPRICE_ID(),
                null,
                pricesDto.getSTART_DATE(),
                pricesDto.getEND_DATE(),
                pricesDto.getPRICE_LIST(),
                pricesDto.getPRODUCT_ID(),
                pricesDto.getPRIORITY(),
                pricesDto.getPrice(),
                pricesDto.getCURR()
        );
        return prices;
    }
}
