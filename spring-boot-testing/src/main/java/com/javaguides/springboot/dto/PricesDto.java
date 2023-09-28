package com.javaguides.springboot.dto;

import com.javaguides.springboot.model.BRAND;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/*
 * Clase PricesDto usada en /src/main/java/com.javaguides.springboot/controller/PriceBrandController para la correcta
 * serialización y envío de datos REST, contiene todos los datos relevantes de la Clase Price
 *
 */
@Schema(
        description = "PrecieDto Model Information"
)
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PricesDto {
    @Schema(
            description = "Identificador precio en la tabla."
    )
    private long PRICE_ID;

    @Schema(
            description = "Price BRAND_ID, foreign key de la cadena."
    )
    private long BRAND_ID;

    @Schema(
            description = "rango de fechas en el que aplica el precio tarifa indicado, Fecha Inicio"
    )
    private LocalDateTime START_DATE;

    @Schema(
            description = "rango de fechas en el que aplica el precio tarifa indicado, Fecha Fin"
    )
    private LocalDateTime END_DATE;

    @Schema(
            description = "Identificador de la tarifa de precios aplicable."
    )
    private int PRICE_LIST;

    @Schema(
            description = "Identificador código de producto."
    )
    private long PRODUCT_ID;

    @Schema(
            description = "Desambiguador de aplicación de precios. Si dos tarifas coinciden en un rago de fechas se aplica la de mayor prioridad (mayor valor numérico)."
    )
    private int PRIORITY;

    @Schema(
            description = "Precio final de venta."
    )
    private double price;

    @Schema(
            description = "Iso de la moneda.."
    )
    private String CURR;
}
