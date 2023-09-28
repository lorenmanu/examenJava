package com.javaguides.springboot.repository;


import com.javaguides.springboot.model.BRAND;
import com.javaguides.springboot.model.PRICES;
import com.javaguides.springboot.model.PRICES;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/*
 * Clase PriceBrandRepositoryTests para comprobar el correcto funcionamiento PriceRepository y BrandRepository
 */

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class PriceBrandRepositoryTests {
    @Autowired
    private PriceRepository pricesRepository;

    @Autowired
    private BrandRepository brandRepository;

    /*
     * Test givenPricesObjectBrandObject_whenSave_thenReturnSavedPricesBrand
     *   --> Entrada: PriceObject BrandObject a guardar
     *   --> Proceso:
     *       --> Se GUARDA PriceObject Y brandobject en la bd
     *   --> Salida:
     *       --> PriceObject BrandObejct guardado
     */
    // Junit test for save prices operation
    @DisplayName("JUnit test for save Prices and Brand")
    @Test
    public void givenPricesObjectBrandObject_whenSave_thenReturnSavedPricesBrand(){
        // given - precondition or setup
        BRAND brand = BRAND.builder()
                .id(1)
                .name("Example")
                .description("Example description")
                .build();

        PRICES prices = PRICES.builder()
                .brand(brand)
                .START_DATE( LocalDateTime.of(2020, Month.JUNE, 14,0,0,0))
                .END_DATE( LocalDateTime.of(2020, Month.DECEMBER, 31,23,59,59))
                .PRICE_LIST(1)
                .PRODUCT_ID(35455)
                .PRIORITY(0)
                .price(35.50)
                .CURR("EUR").build();

        Set<PRICES> pricesSet = new HashSet<>();
        pricesSet.add(prices);
        brand.setPrices(pricesSet);

        // when - action or the behavior that we are going test
        BRAND savedBrand = brandRepository.save(brand);

        // then - verify the output

        Assertions.assertThat(savedBrand).isNotNull();
        Assertions.assertThat(savedBrand.getId()).isGreaterThan(0);


        PRICES[] arrayPrices = savedBrand.getPrices().toArray(new PRICES[savedBrand.getPrices().size()]);

        Assertions.assertThat(arrayPrices).isNotNull();
        Assertions.assertThat(arrayPrices[0].getPRODUCT_ID()).isGreaterThan(0);
    }

    /*
     * Test givenPricesListAndBrandList_whenFindAll_thenPricesListAndBrandList
     *   --> Entrada: List de PriceObject y List de BrandObejct
     *   --> Proceso:
     *       --> Se devuelve lista de PriceObject y BrandObject
     *   --> Salida:
     *       --> Lista de PriceObject y BrandObject
     */
    // Junit test for get all PRICES and BRAND operation
    @DisplayName("Junit test for get all PRICES and BRAND operation")
    @Test
    public void givenPricesListAndBrandList_whenFindAll_thenPricesListAndBrandList(){
        // given - precondition or setup
        brandRepository.deleteAll();
        pricesRepository.deleteAll();

        BRAND brandOne = BRAND.builder()
                .id(1)
                .name("Example")
                .description("Example description")
                .build();

        PRICES pricesOne = PRICES.builder()
                .brand(brandOne)
                .START_DATE( LocalDateTime.of(2020, Month.JUNE, 14,0,0,0))
                .END_DATE( LocalDateTime.of(2020, Month.DECEMBER, 31,23,59,59))
                .PRICE_LIST(1)
                .PRODUCT_ID(35455)
                .PRIORITY(0)
                .price(35.50)
                .CURR("EUR").build();

        PRICES pricesTwo = PRICES.builder()
                .brand(brandOne)
                .START_DATE( LocalDateTime.of(2020, Month.JUNE, 14,15,0,0))
                .END_DATE( LocalDateTime.of(2020, Month.JUNE, 14,18,30,59))
                .PRICE_LIST(1)
                .PRODUCT_ID(35455)
                .PRIORITY(0)
                .price(35.50)
                .CURR("EUR").build();

        Set<PRICES> pricesSet = new HashSet<>();
        pricesSet.add(pricesOne);
        pricesSet.add(pricesTwo);
        brandOne.setPrices(pricesSet);

        brandRepository.save(brandOne);

        // when - action or the behavior that we are going test
        List<PRICES> pricesList = pricesRepository.findAll();
        List<BRAND> brandList = brandRepository.findAll();

        // then - verify the output


        Assertions.assertThat(pricesList).isNotNull();
        Assertions.assertThat(pricesList.size()).isEqualTo(2);


        Assertions.assertThat(brandList).isNotNull();
        Assertions.assertThat(brandList.size()).isEqualTo(1);
    }

    /*
     * Test givenPricesIdBrandId_whenfindById_thenReturnPriceBrand
     *   --> Entrada: Id de Price Y Brand a buscar
     *   --> Proceso:
     *       --> Sebuscar price y Brand a partir de ID
     *   --> Salida:
     *       --> PriceObject y BrandObject encontrados a partir de ID
     */
    // Junit test for get all PRICES and BRAND by Id operation
    @DisplayName("Junit test for get all PRICES and BRAND by Id operation")
    @Test
    public void givenPricesIdBrandId_whenfindById_thenReturnPriceBrand(){
        // given - precondition or setup
        brandRepository.deleteAll();
        pricesRepository.deleteAll();

        BRAND brandOne = BRAND.builder()
                .id(1)
                .name("Example")
                .description("Example description")
                .build();

        PRICES pricesOne = PRICES.builder()
                .brand(brandOne)
                .START_DATE( LocalDateTime.of(2020, Month.JUNE, 14,0,0,0))
                .END_DATE( LocalDateTime.of(2020, Month.DECEMBER, 31,23,59,59))
                .PRICE_LIST(1)
                .PRODUCT_ID(35455)
                .PRIORITY(0)
                .price(35.50)
                .CURR("EUR").build();

        PRICES pricesTwo = PRICES.builder()
                .brand(brandOne)
                .START_DATE( LocalDateTime.of(2020, Month.JUNE, 14,15,0,0))
                .END_DATE( LocalDateTime.of(2020, Month.JUNE, 14,18,30,59))
                .PRICE_LIST(1)
                .PRODUCT_ID(35455)
                .PRIORITY(0)
                .price(35.50)
                .CURR("EUR").build();

        Set<PRICES> pricesSet = new HashSet<>();
        pricesSet.add(pricesOne);
        brandOne.setPrices(pricesSet);

        BRAND brandSave = brandRepository.save(brandOne);

        PRICES[] pricesList = brandSave.getPrices().toArray(new PRICES[brandSave.getPrices().size()]);

        // when - action or the behavior that we are going test
        PRICES priceDB = pricesRepository.findById(pricesList[0].getPRICE_ID()).get();
        BRAND brandUpdate = brandRepository.findById(brandSave.getId()).get();

        // then - verify the output

        Assertions.assertThat(priceDB).isNotNull();
        // Assertions.assertThat(priceDB).isNotNull();
    }

    /*
     * Test givenPriceObjectBrandObject_whenUpdatePriceUpdateBrand_thenReturnPriceBrand
     *   --> Entrada: Price Y Brand a actualizar
     *   --> Proceso:
     *       --> Se actualiza Price y Brand en la BD
     *   --> Salida:
     *       --> PriceObject y BrandObject actualizados
     */
    // Junit test for get all PRICES and BRAND by Id operation
    @DisplayName("Junit test for update Price Object and Brand Object")
    @Test
    public void givenPriceObjectBrandObject_whenUpdatePriceUpdateBrand_thenReturnPriceBrand(){
        // given - precondition or setup
        brandRepository.deleteAll();
        pricesRepository.deleteAll();

        BRAND brandOne = BRAND.builder()
                .id(1)
                .name("Example")
                .description("Example description")
                .build();

        PRICES pricesOne = PRICES.builder()
                .brand(brandOne)
                .START_DATE( LocalDateTime.of(2020, Month.JUNE, 14,0,0,0))
                .END_DATE( LocalDateTime.of(2020, Month.DECEMBER, 31,23,59,59))
                .PRICE_LIST(1)
                .PRODUCT_ID(35455)
                .PRIORITY(0)
                .price(35.50)
                .CURR("EUR").build();

        PRICES pricesTwo = PRICES.builder()
                .brand(brandOne)
                .START_DATE( LocalDateTime.of(2020, Month.JUNE, 14,15,0,0))
                .END_DATE( LocalDateTime.of(2020, Month.JUNE, 14,18,30,59))
                .PRICE_LIST(1)
                .PRODUCT_ID(35455)
                .PRIORITY(0)
                .price(35.50)
                .CURR("EUR").build();

        Set<PRICES> pricesSet = new HashSet<>();
        pricesSet.add(pricesOne);
        brandOne.setPrices(pricesSet);

        BRAND brandSave = brandRepository.save(brandOne);

        PRICES[] pricesList = brandSave.getPrices().toArray(new PRICES[brandSave.getPrices().size()]);

        // when - action or the behavior that we are going test
        PRICES priceDB = pricesRepository.findById(pricesList[0].getPRICE_ID()).get();
        priceDB.setPRICE_ID(33333);
        priceDB.setCURR("US");
        PRICES priceUpdate = pricesRepository.save(priceDB);

        BRAND brandDB = brandRepository.findById(brandSave.getId()).get();
        brandDB.setName("Example update");
        BRAND brandUpdate = brandRepository.save(brandDB);

        // then - verify the output

        Assertions.assertThat(priceUpdate.getPRICE_ID()).isEqualTo(33333);
        Assertions.assertThat(priceUpdate.getCURR()).isEqualTo("US");

        Assertions.assertThat(brandUpdate.getName()).isEqualTo("Example update");
        // Assertions.assertThat(priceDB).isNotNull();
    }

    /*
     * Test givenPriceObjectBrandObject_whenDelectPriceBrand_thenCheckDelete
     *   --> Entrada: Id Price y Brand a eliminar
     *   --> Proceso:
     *       --> Se elimina Price y Brand en la BD
     *   --> Salida:
     *       --> Se comprueba su correcta eliminación
     */
    // Junit test for delete Price Brand operation
    @DisplayName("Junit test for delete Price Object and Brand Object")
    @Test
    public void givenPriceObjectBrandObject_whenDelectPriceBrand_thenCheckDelete(){
        // given - precondition or setup
        brandRepository.deleteAll();
        pricesRepository.deleteAll();

        BRAND brandOne = BRAND.builder()
                .id(1)
                .name("Example")
                .description("Example description")
                .build();

        PRICES pricesOne = PRICES.builder()
                .brand(brandOne)
                .START_DATE( LocalDateTime.of(2020, Month.JUNE, 14,0,0,0))
                .END_DATE( LocalDateTime.of(2020, Month.DECEMBER, 31,23,59,59))
                .PRICE_LIST(1)
                .PRODUCT_ID(35455)
                .PRIORITY(0)
                .price(35.50)
                .CURR("EUR").build();

        PRICES pricesTwo = PRICES.builder()
                .brand(brandOne)
                .START_DATE( LocalDateTime.of(2020, Month.JUNE, 14,15,0,0))
                .END_DATE( LocalDateTime.of(2020, Month.JUNE, 14,18,30,59))
                .PRICE_LIST(1)
                .PRODUCT_ID(35455)
                .PRIORITY(0)
                .price(35.50)
                .CURR("EUR").build();

        Set<PRICES> pricesSet = new HashSet<>();
        pricesSet.add(pricesOne);
        brandOne.setPrices(pricesSet);

        BRAND brandSave = brandRepository.save(brandOne);

        PRICES[] pricesList = brandSave.getPrices().toArray(new PRICES[brandSave.getPrices().size()]);

        // when - action or the behavior that we are going test
        PRICES priceDB = pricesRepository.findById(pricesList[0].getPRICE_ID()).get();
        pricesRepository.delete(priceDB);

        BRAND brandDB = brandRepository.findById(brandSave.getId()).get();
        brandRepository.delete(brandDB);

        // then - verify the output

        Assertions.assertThat(pricesRepository.findById(priceDB.getPRICE_ID())).isEmpty();
        Assertions.assertThat(brandRepository.findById(brandDB.getId())).isEmpty();
    }

    /*
     * Test givenDateRequestIdProductIdBrand_whenfindByDateRequestIdProductIdBrand_thenReturnPrice
     *   --> Entrada: fechaAplicacion, idProd, idBrand
     *   --> Proceso:
     *       --> Se busca Price en la base de datos según criterios de examen
     *   --> Salida:
     *       --> Se devuelve resultado de la busqueda
     */
    // Junit petición a las 10:00 del día 14 del producto 35455   para la brand 1 (CADENA1)
    @DisplayName("Junit petición a las 10:00 del día 14 del producto 35455   para la brand 1 (CADENA1)")
    @Test
    public void givenDateRequestIdProductIdBrand_whenfindByDateRequestIdProductIdBrand_thenReturnPrice(){
        // given - precondition or setup
        brandRepository.deleteAll();
        pricesRepository.deleteAll();

        BRAND brandOne = BRAND.builder()
                .id(1)
                .name("Example")
                .description("Example description")
                .build();

        LocalDateTime START_DATE = LocalDateTime.of(2020, Month.JUNE, 14,0,0,0);
        LocalDateTime END_DATE = LocalDateTime.of(2020, Month.DECEMBER, 31,23,59,59);

        PRICES pricesOne = PRICES.builder()
                .brand(brandOne)
                .START_DATE( LocalDateTime.of(2020, Month.JUNE, 14,0,0,0))
                .END_DATE( LocalDateTime.of(2020, Month.DECEMBER, 31,23,59,59))
                .PRICE_LIST(1)
                .PRODUCT_ID(35455)
                .PRIORITY(0)
                .price(35.50)
                .CURR("EUR").build();

        Set<PRICES> pricesSet = new HashSet<>();
        pricesSet.add(pricesOne);
        brandOne.setPrices(pricesSet);

        BRAND brandSave = brandRepository.save(brandOne);

        List<PRICES> listPricesDB = pricesRepository.findByDateRequestIdProductIdBrand(LocalDateTime.of(2020, Month.JULY, 14,10,0,0), 35455, 1);

        // when - action or the behavior that we are going test
        List<BRAND> brandList = brandRepository.findAll();

        // then - verify the output

        Assertions.assertThat(listPricesDB.size()).isGreaterThan(0);
        // Assertions.assertThat(priceDB).isNotNull();
    }

}
