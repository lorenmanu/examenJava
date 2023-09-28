package com.javaguides.springboot.model;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/*
 * Clase PRICES usada para crear la tabla PRICES en la base de datos
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "PRICES")
@Table(name= "PRICES")
public class PRICES {
    @Schema(
            description = "Identificador precio en la tabla."
    )
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long PRICE_ID;

    @Schema(
            description = "Variable donde se almacena la Cadena, Brand,  de la relación One-To-Many con prices. One(Price)-To-Many(Brand)"
    )
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BRAND_ID", referencedColumnName = "id")
    private BRAND brand;

    @Schema(
            description = "rango de fechas en el que aplica el precio tarifa indicado, Fecha Inicio"
    )
    @Column(name = "START_DATE", nullable = false)
    private LocalDateTime START_DATE;

    @Schema(
            description = "rango de fechas en el que aplica el precio tarifa indicado, Fecha Fin"
    )
    @Column(name = "END_DATE", nullable = false)
    private LocalDateTime END_DATE;

    @Schema(
            description = "Identificador de la tarifa de precios aplicable."
    )
    @Column(name = "PRICE_LIST", nullable = false)
    private int PRICE_LIST;

    @Schema(
            description = "Identificador código de producto."
    )
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long PRODUCT_ID;

    @Schema(
            description = "Desambiguador de aplicación de precios. Si dos tarifas coinciden en un rago de fechas se aplica la de mayor prioridad (mayor valor numérico)."
    )
    @Column(name = "PRIORITY", nullable = false)
    private int PRIORITY;

    @Schema(
            description = "Precio final de venta."
    )
    @Column(name = "PRICE", nullable = false)
    private double price;

    @Schema(
            description = "Iso de la moneda.."
    )
    @Column(name = "CURR", nullable = false)
    private String CURR;

}
