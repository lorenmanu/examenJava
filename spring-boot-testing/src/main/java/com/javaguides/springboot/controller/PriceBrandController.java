package com.javaguides.springboot.controller;


import com.javaguides.springboot.dto.PricesDto;
import com.javaguides.springboot.dto.PricesExamDto;
import com.javaguides.springboot.mapper.PricesExamMapper;
import com.javaguides.springboot.mapper.PricesMapper;
import com.javaguides.springboot.model.BRAND;
import com.javaguides.springboot.model.PRICES;
import com.javaguides.springboot.service.PriceBrandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;


@Tag(
        name = "PriceBrand Service - PriceBrandController",
        description = "PriceBrand Service - PriceBrandController Exposes REST APIs for PriceBrand Service"
)
@RestController
@RequestMapping("/api/prices")
public class PriceBrandController {
    @Autowired
    private PriceBrandService priceBrandService;

    @Operation(
            summary = "Get method all Prices Rest API",
            description = "Get method all Prices Rest REST API is used to get all Prices in BD"
    )
    @ApiResponse(
            responseCode = "201",
            description = "HTTP Status 201 CREATED"
    )
    /*
    * Metodo GET para obtener todos los precios:
    *   --> Url: http://localhost:8080/api/prices/
    *   --> Entrada: nada
    *   --> Salida: lista de PricesDto( estructura que usamos para serializar correctamente Prices )
     */
    @GetMapping("/")
    public List<PricesDto> allPrices(){
        List<PricesDto> listPricesDto = new ArrayList<>();
        List<PRICES> listPrices = priceBrandService.getAllPrices();

        for(int i=0; i<listPrices.size(); i++){
            listPricesDto.add(PricesMapper.mapToPricesDto(listPrices.get(i)));
        }
        return listPricesDto;
    }

    @Operation(
            summary = "Get method Price by Id Rest API",
            description = "Get method Price by Id Rest REST API is used to get Prices by Id in BD"
    )
    @ApiResponse(
            responseCode = "201",
            description = "HttpStatus.OK"
    )
    /*
     * Metodo GET By Id para precio por Id:
     *   --> Url: http://localhost:8080/api/prices/{id}
     *   --> Entrada: id del Producto a buscar
     *   --> Salida:
     *               -> Se encuentra producto: PricesDto( estructura que usamos para serializar correctamente el precio encontrado por id )
     *               -> No se encuentra producto: página Http de "NOT_FOUND"
     *      PricesDto Localización: /src/main/java/com.javaguides.springboot/dto/PricesDto
     */
    @GetMapping("/{id}")
    public ResponseEntity<PricesDto> findById(@PathVariable("id") long id) {
        PricesDto pricesDto = new PricesDto();
        Optional<PRICES> priceFound = priceBrandService.getPriceById(id);

        if(!priceFound.isEmpty()){
            pricesDto = PricesMapper.mapToPricesDto(priceFound.get());
            return ResponseEntity.status(HttpStatus.OK).body(pricesDto);
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

    }

    @Operation(
            summary = "Get method Price by fechaAplicacion prodId brandId  Rest API with Priority Higher",
            description = "Get method Price by fechaAplicacion prodId brandId  Rest API with Priority Higher"
    )
    @ApiResponse(
            responseCode = "PricesExamDto",
            description = "HttpStatus.OK"
    )
    /*
     * Metodo GET By fechaAppli, prodId, brandId para precio:
     *   --> Url: http://localhost:8080/api/prices/fechaAppli/2020-07-14T14:00:11/35455/1
     *   --> Entrada: fechaAppli, prodId, brandId
     *   --> Salida:
     *               -> Se encuentra producto: PricesExamDto( estructura que usamos para serializar correctamente el precio
     *                                                         encontrado por fechaAppli, prodId, brandId, estructura personalida
     *                                                          para devolver los datos pedidos en el examen)
     *               -> No se encuentra producto: estructrua PricesExamDto vacia
     *      PricesExamDto Localización: /src/main/java/com.javaguides.springboot/dto/PricesExamDto
     */

    @GetMapping("/fechaAppli/{fechaAplicacion}/{prodId}/{brandId}")
    public PricesExamDto findByFechaAplicacion(@PathVariable("fechaAplicacion") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS") LocalDateTime fechaAplicacion, @PathVariable("prodId") long prodId, @PathVariable("brandId") long brandId) {
        PRICES price = priceBrandService.findByDateRequestIdProductIdBrand(fechaAplicacion, prodId, brandId);

        if(price!=null){
            return PricesExamMapper.mapToPricesExamDto(priceBrandService.findByDateRequestIdProductIdBrand(fechaAplicacion, prodId, brandId));
        }
        else{
            return new PricesExamDto();
        }
    }

    @Operation(
            summary = "Post method to Save Price in BD",
            description = "Post method to Save Price in BD"
    )
    @ApiResponse(
            responseCode = "PricesExamDto",
            description = "HttpStatus.OK"
    )
    /*
     * Metodo Post para crear Price y Brand a partir de Json:
     *   --> Url: http://localhost:8080/api/prices/price
     *   --> Entrada: archivo Json con los datos para crear Price y Brand
     *   --> Salida:
     *               -> Se devuelve Price creado PriceDto
     *      PricesDto Localización: /src/main/java/com.javaguides.springboot/dto/PricesDto
     *   --> curl -i -X POST http://localhost:8080/api/prices/price -H 'Content-Type: application/json' -d
     *                  '{"price": 22.2, "curr": "EUR", "price_ID": 8, "start_DATE": "2020-06-14T00:00:00", "end_DATE":
     *                  "2020-12-31T23:59:59", "price_LIST":1, "product_ID": 35455, "priority":0, "brand_ID": 1}'
     */

    @PostMapping("/price")
    @ResponseStatus(HttpStatus.CREATED)
    public PricesDto createPrice(@RequestBody PricesDto priceDto) {
        PRICES priceToSave = PricesMapper.mapToPrices(priceDto);
        BRAND brand = new BRAND();
        brand.setName("");
        brand.setDescription("");

        if(!priceBrandService.getBrandById(priceDto.getBRAND_ID()).isEmpty()){
            brand = priceBrandService.getBrandById(priceDto.getBRAND_ID()).get();

            Set<PRICES> pricesSet = new HashSet<>();
            pricesSet.add(priceToSave);
            brand.setPrices(pricesSet);
            System.out.println("Se mete aqui");
        }

        priceToSave.setBrand(brand);

        priceBrandService.savePriceBrand(priceToSave, brand);

        return PricesMapper.mapToPricesDto(priceToSave);
    }

    @Operation(
            summary = "PUT method to update Price in BD",
            description = "PUT method to update Price in BD"
    )
    @ApiResponse(
            responseCode = "PricesExamDto",
            description = "HttpStatus.OK"
    )
    /*
     * Metodo PUT para crear Price y Brand a partir de Json:
     *   --> Url: http://localhost:8080/api/prices/price
     *   --> Entrada: archivo Json con los datos para actualizar Price
     *   --> Salida:
     *               -> Se devuelve Price actualizado PriceDto
     *      PricesDto Localización: /src/main/java/com.javaguides.springboot/dto/PricesDto
     *   --> curl -i -X PUT http://localhost:8080/api/prices/price/1 -H 'Content-Type: application/json'
     *                  -d '{"prices": 30.5, "curr": "EUR", "price_ID": 1, "start_DATE": "2020-06-14T00:00:00",
     *                  "end_DATE": "2020-12-31T23:59:59", "price_LIST":1, "product_ID": 35455, "priority":0, "brand_ID": 1}'
     */

    @PutMapping("/price/{id}")
    public ResponseEntity<PricesDto> updatePrice(@PathVariable int id ,@RequestBody PRICES price) {
        return priceBrandService.getPriceById(id).map(savedPrice ->{
            savedPrice.setPrice(price.getPrice());
            savedPrice.setPRICE_ID(price.getPRICE_ID());
            savedPrice.setPRICE_LIST(price.getPRICE_LIST());
            savedPrice.setCURR(price.getCURR());
            savedPrice.setPRODUCT_ID(price.getPRODUCT_ID());
            savedPrice.setSTART_DATE(price.getSTART_DATE());
            savedPrice.setEND_DATE(price.getEND_DATE());
            savedPrice.setPRIORITY(price.getPRIORITY());

            if(price.getBrand()!=null){
                savedPrice.setBrand(price.getBrand());
            }

            PRICES updatedPrice = priceBrandService.updatePrice(savedPrice);
            return new ResponseEntity<>(PricesMapper.mapToPricesDto(updatedPrice), HttpStatus.OK);
        }).orElseGet(()-> ResponseEntity.notFound().build());
    }

    /*
     * Metodo DELETE para eliminar Price a partir de Id:
     *   --> Url: http://localhost:8080/api/prices/{id}
     *   --> Entrada: id del Price que se quiere eliminar
     *   --> Salida:
     *               -> Se devuelve ResponseEntity con el mensaje "Price deleted succesfully"
     *   --> curl -i -X DELETE http://localhost:8080/api/prices/price/{id}
     */
    @Operation(
            summary = "DELETE method to delete Price in BD",
            description = "DELETE method to delete Price in BD"
    )
    @ApiResponse(
            responseCode = "PricesExamDto",
            description = "HttpStatus.OK"
    )
    @DeleteMapping("/price/{id}")
    public ResponseEntity<String> deletePrice(@PathVariable("id") Long id) {
        priceBrandService.deletePrice(id);

        return new ResponseEntity<String>("Price deleted succesfully", HttpStatus.OK);
    }
}
