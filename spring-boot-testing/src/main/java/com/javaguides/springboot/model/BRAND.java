package com.javaguides.springboot.model;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/*
 * Clase BRAND usada para crear la tabla BRAND en la base de datos
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name= "brand")
public class BRAND {
    @Schema(
            description = "Price BRAND_ID, foreign key de la Cadena."
    )
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Schema(
            description = "Nombre de la Cadena."
    )
    @Column(name = "name", nullable = false)
    private String name;

    @Schema(
            description = "Descripcion de la Cadena."
    )
    @Column(name = "description", nullable = false)
    private String description;

    @Schema(
            description = "Variable donde se almacena la coleccion de precios de la relación One-To-Many con prices. One(Price)-To-Many(Brand)"
    )
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "brand")
    private Set<PRICES> prices = new HashSet<>();
}
