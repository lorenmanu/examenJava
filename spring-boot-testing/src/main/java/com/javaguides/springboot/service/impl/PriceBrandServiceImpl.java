package com.javaguides.springboot.service.impl;

import com.javaguides.springboot.exception.ResourceNotFoundException;
import com.javaguides.springboot.model.BRAND;
import com.javaguides.springboot.model.PRICES;
import com.javaguides.springboot.repository.BrandRepository;
import com.javaguides.springboot.repository.PriceRepository;
import com.javaguides.springboot.service.PriceBrandService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/*
 * Clase PriceBrandServiceImpl usada para comunicar:
 *          --> /src/main/java/com.javaguides.springboot/controller/PriceBrandController
 *          --> /src/main/java/com.javaguides.springboot/repository/BrandRepository
 *          --> /src/main/java/com.javaguides.springboot/repository/PricesRepository
 */
@Service
public class PriceBrandServiceImpl implements PriceBrandService {
    @Autowired
    private PriceRepository priceRepository;
    @Autowired
    private BrandRepository brandRepository;

    /*
     * Constructor de la clase PriceBrandServiceImpl para inicializar PriceRepository y brandRepository
     */
    public PriceBrandServiceImpl(PriceRepository pricesRepository, BrandRepository brandRepository) {
        this.priceRepository = pricesRepository;
        this.brandRepository = brandRepository;
    }

    /*
     * Metodo savePriceBrand para guardar Price y Brand en la base de datos

     *   --> Entrada: Price y Brand a guardar
     *   --> Proceso:
     *       --> Se comprueba si existe Price en la BD, en caso correcto se devuelve excepcion de
     *           ResourceNotFoundException("Price already exists in BD, ID -- "+price.getPRICE_ID());
     *           Ver /src/main/java/com.javaguides.springboot/exception/ResourceNotFoundException
     *       --> Si el objecto anterior no existe, se comprueba si su BRAND existe en la base de datos, si no existe
     *           se asigna ese BRAND al PRICE
     *       --> Se guarda BRAND y PRICE en la base de datos
     *   --> Salida:
     *        -> Se devuelve BRAND, al tener una relación OneToMany con PRICE, dentro del BRAND esta PRICE
     */
    @Override
    public BRAND savePriceBrand(PRICES price, BRAND brand) {
        Optional<PRICES> priceBD = priceRepository.findById(price.getPRICE_ID());
        Optional<BRAND> brandBD = brandRepository.findById(brand.getId());

        if(priceBD.isPresent()){
            throw new ResourceNotFoundException("Price already exists in BD, ID -- "+price.getPRICE_ID());
        }


        if(price.getBrand() == null || price.getBrand().getId() != brand.getId() || brand.getPrices()==null){
            Set<PRICES> pricesSet = new HashSet<>();
            pricesSet.add(price);
            brand.setPrices(pricesSet);

            price.setBrand(brand);
        }

        BRAND savedBrand = brandRepository.save(brand);
        PRICES savedPrices = priceRepository.save(price);

        return savedBrand;
    }

    @Override
    public BRAND saveBrand(BRAND brand) {
        return brandRepository.save(brand);
    }

    /*
     * Metodo getAllBrands para obtener todos los BRANDS en la base de datos
     *   --> Entrada: nada
     *   --> Proceso:
     *       --> Se devuelve todos los BRANDS de la BD
     *   --> Salida:
     *       --> List<BRAND>
     */
    @Override
    public List<BRAND> getAllBrands() {
        return brandRepository.findAll();
    }

    /*
     * Metodo getAllPrices para obtener todos los PRICES en la base de datos
     *   --> Entrada: nada
     *   --> Proceso:
     *       --> Se devuelve todos los PRICES de la BD
     *   --> Salida:
     *       --> List<PRICES>
     */
    @Override
    public List<PRICES> getAllPrices() {
        return priceRepository.findAll();
    }

    /*
     * Metodo getPriceById para obtener PRICE a partir de la Id
     *   --> Entrada: priceId
     *   --> Proceso:
     *       --> Se devuelve PRICE encontrado a partir de ID en la base de datos
     *   --> Salida:
     *       --> Optional<PRICES>
     */
    @Override
    public Optional<PRICES> getPriceById(long id) {
        return priceRepository.findById(id);
    }

    /*
     * Metodo getBrandById para obtener BRAND a partir de la Id
     *   --> Entrada: brandId
     *   --> Proceso:
     *       --> Se devuelve BRAND encontrado a partir de ID en la base de datos
     *   --> Salida:
     *       --> Optional<BRAND>
     */
    @Override
    public Optional<BRAND> getBrandById(long id) {
        return brandRepository.findById(id);
    }

    /*
     * Metodo updatePrice para actualizar PRICE en la base de datos
     *   --> Entrada: PRICES
     *   --> Proceso:
     *       --> Se actualiza PRICES en la BD
     *   --> Salida:
     *       --> PRICES
     */
    @Override
    public PRICES updatePrice(PRICES price) {
        return priceRepository.save(price);
    }

    /*
     * Metodo updateBrand para actualizar BRAND en la base de datos
     *   --> Entrada: BRAND
     *   --> Proceso:
     *       --> Se actualiza BRAND en la BD
     *   --> Salida:
     *       --> BRAND
     */
    @Override
    public BRAND updateBrand(BRAND brand) {
        return brandRepository.save(brand);
    }

    /*
     * Metodo deletePrice para eliminar Price en la base de datos
     *   --> Entrada: id
     *   --> Proceso:
     *       --> Se elimina price en la BD a partir de id
     *   --> Salida:
     *       --> nada
     */
    @Override
    public void deletePrice(long id) {
        priceRepository.deleteById(id);
    }

    /*
     * Metodo deleteBrand para eliminar Brand en la base de datos
     *   --> Entrada: id
     *   --> Proceso:
     *       --> Se elimina Brand en la BD a partir de id
     *   --> Salida:
     *       --> nada
     */
    @Override
    public void deleteBrand(long id) {
        brandRepository.deleteById(id);
    }


    /*
     * Metodo findByDateRequestIdProductIdBrand para obtener Prices en la base de datos según requisitos de examen
     *   --> Entrada: LocalDateTime fechaApli, long idProd, long idBrand
     *   --> Proceso:
     *       --> Se busca PRICES en la base de datos según LocalDateTime fechaApli, long idProd, long idBrand
     *          --> Si se encuentra, y hay más de uno, se coge el que más prioridad tenga, getPRIORITY()
     *   --> Salida:
     *       --> PRICES encontrado
     */

    @Override
    public PRICES findByDateRequestIdProductIdBrand(LocalDateTime fechaApli, long idProd, long idBrand) {
        List<PRICES> pricesList = priceRepository.findByDateRequestIdProductIdBrand(fechaApli, idProd, idBrand);
        PRICES priceFound = new PRICES();

        if(pricesList.size()>0){
            priceFound = pricesList.get(0);
        }

        for(int i = 0; i<pricesList.size(); i++){
            if(pricesList.get(i).getPRIORITY()>priceFound.getPRIORITY()){
                priceFound = pricesList.get(i);
            }
        }

        return priceFound;
    }


}
