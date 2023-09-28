package com.javaguides.springboot.repository;


import com.javaguides.springboot.model.PRICES;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

/*
 * Clase PriceRepository usada realizar consultas en la base de datos a la tabla PRICES
 */
public interface PriceRepository extends JpaRepository<PRICES, Long> {
    List<PRICES> findByPrice(long price);

    @Query("SELECT p from PRICES p where (:fechaApli BETWEEN p.START_DATE AND p.END_DATE) AND p.PRODUCT_ID = :idProd AND p.brand.id = :idBrand")
    List<PRICES> findByDateRequestIdProductIdBrand(@Param("fechaApli") LocalDateTime fechaApli,
                                                 @Param("idProd") long idProd, @Param("idBrand") long idBrand);


}
