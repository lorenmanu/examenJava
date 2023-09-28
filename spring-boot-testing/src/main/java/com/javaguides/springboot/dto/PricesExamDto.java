package com.javaguides.springboot.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


/*
 * Clase PricesExamDto usada en /src/main/java/com.javaguides.springboot/controller/PriceBrandController para la correcta
 * serialización y envío de datos REST, contiene todos los datos relevantes de la Clase Price con los datos precisos del metodo
 * pedido en el examen "findByDateRequestIdProductIdBrand". Según descripción examen:
 *
 * "Construir una aplicación/servicio en SpringBoot que provea una end point rest de consulta  tal que:
 * Acepte como parámetros de entrada: fecha de aplicación, identificador de producto, identificador de cadena.
 * Devuelva como datos de salida: identificador de producto, identificador de cadena, tarifa a aplicar, fechas
 *  de aplicación y precio final a aplicar."
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PricesExamDto {
    @Schema(
            description = "Identificador código de producto."
    )
    private long PRODUCT_ID;
    @Schema(
            description = "Price BRAND_ID, foreign key de la cadena."
    )
    private long BRAND_ID;
    @Schema(
            description = "Identificador de la tarifa de precios aplicable."
    )
    private int PRICE_LIST;
    @Schema(
            description = "Precio final de venta."
    )
    private double price;
    @Schema(
            description = "rango de fechas en el que aplica el precio tarifa indicado, Fecha Inicio"
    )
    private LocalDateTime START_DATE;
    @Schema(
            description = "rango de fechas en el que aplica el precio tarifa indicado, Fecha Fin"
    )
    private LocalDateTime END_DATE;
}
