package com.javaguides.springboot.service;

import com.javaguides.springboot.model.BRAND;
import com.javaguides.springboot.model.PRICES;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PriceBrandService {

    BRAND savePriceBrand(PRICES price, BRAND brand);

    List<BRAND> getAllBrands();

    List<PRICES> getAllPrices();

    Optional<PRICES> getPriceById(long id);

    Optional<BRAND> getBrandById(long id);

    PRICES updatePrice(PRICES price);

    BRAND updateBrand(BRAND brand);

    void deletePrice(long id);

    void deleteBrand(long id);


    PRICES findByDateRequestIdProductIdBrand(LocalDateTime fechaApli, long idProd, long idBrand);

}
