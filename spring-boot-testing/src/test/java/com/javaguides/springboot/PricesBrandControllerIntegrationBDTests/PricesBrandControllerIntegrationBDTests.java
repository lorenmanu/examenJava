package com.javaguides.springboot.PricesBrandControllerIntegrationBDTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javaguides.springboot.model.PRICES;
import com.javaguides.springboot.repository.BrandRepository;
import com.javaguides.springboot.repository.PriceRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

/*
 * Clase PricesBrandControllerIntegrationBDTests para comprobar el correcto funcionamiento de los casos de prueba descritos
 * en examen:
 *        Test 1: petición a las 10:00 del día 14 del producto 35455   para la brand 1 (CADENA1)
 *        Test 2: petición a las 16:00 del día 14 del producto 35455   para la brand 1 (CADENA1)
 *        Test 3: petición a las 21:00 del día 14 del producto 35455   para la brand 1 (CADENA1)
 *        Test 4: petición a las 10:00 del día 15 del producto 35455   para la brand 1 (CADENA1)
 *        Test 5: petición a las 21:00 del día 16 del producto 35455   para la brand 1 (CADENA1)
 *
 * Aclaración: en mi caso, la ejecución de los tests es correcta, ya que he interpretado que la fecha aplicación dado debe estar
 * entrar las fechas START_DATE y END_DATE de PRICES en la base de datos, además de pertenecer a BRAND_ID y PRODUCT_LIST
 * Consulta sql en /src/main/java/com.javaguides.springboot/repository/PricesRepository
 *  *     @Query("SELECT p from PRICES p where (:fechaApli BETWEEN p.START_DATE AND p.END_DATE) AND p.PRODUCT_ID = :idProd
 *              AND p.brand.id = :idBrand")
 *       List<PRICES> findByDateRequestIdProductIdBrand(@Param("fechaApli") LocalDateTime fechaApli,
 *                                               @Param("idProd") long idProd, @Param("idBrand") long idBrand);
 */

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
public class PricesBrandControllerIntegrationBDTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PriceRepository priceRepository;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private ObjectMapper objectMapper;

    /*
        Ejecución previa a cada tests para ejecutarlos con los datos correctos, en este caso se crea brandone y price al no usar BD
        en este caso no hacemos nada, para usar los datos de la base de datos H2, lo cual indicamos al principio con las etiquetas
        @SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
        @AutoConfigureMockMvc
        @Transactional
    */
    void setup(){
        // brandRepository.deleteAll();
        // priceRepository.deleteAll();
    }

    // "Test 1: petición a las 10:00 del día 14 del producto 35455   para la brand 1 (CADENA1)"
    @DisplayName("Test 1: petición a las 10:00 del día 14 del producto 35455 para la brand 1 (CADENA1)")
    @Test
    public void givenDateApliProdIdBrandId_whenSearch_thenReturnResultSearchOne() throws Exception {
        // given - precondition or setup
        LocalDateTime fechaAplicacion = LocalDateTime.of(2020, Month.JUNE, 14,10,0,0);
        long prodId = 35455;
        long brandId = 1;


        // when - action or the behavior that we are going test
        List<PRICES> listPricesDB = priceRepository.findByDateRequestIdProductIdBrand(fechaAplicacion, prodId, brandId);

        // then - verify the output

        Assertions.assertThat(listPricesDB.size()).isGreaterThan(0);
        Assertions.assertThat(listPricesDB.get(0).getBrand().getId()).isEqualTo(1);
        Assertions.assertThat(listPricesDB.get(0).getPRODUCT_ID()).isEqualTo(35455);
    }

    // "Test 2: petición a las 16:00 del día 14 del producto 35455   para la brand 1 (CADENA1)"
    @DisplayName("Test 2: petición a las 16:00 del día 14 del producto 35455   para la brand 1 (CADENA1)")
    @Test
    public void givenDateApliProdIdBrandId_whenSearch_thenReturnResultSearchTwo() throws Exception {
        // given - precondition or setup
        LocalDateTime fechaAplicacion = LocalDateTime.of(2020, Month.JUNE, 14,16,0,0);
        long prodId = 35455;
        long brandId = 1;


        // when - action or the behavior that we are going test
        List<PRICES> listPricesDB = priceRepository.findByDateRequestIdProductIdBrand(fechaAplicacion, prodId, brandId);

        // then - verify the output

        Assertions.assertThat(listPricesDB.size()).isGreaterThan(0);
        Assertions.assertThat(listPricesDB.get(0).getBrand().getId()).isEqualTo(1);
        Assertions.assertThat(listPricesDB.get(0).getPRODUCT_ID()).isEqualTo(35455);
    }

    // "Test Three: petición a las 21:00 del día 14 del producto 35455   para la brand 1 (CADENA1)"
    @DisplayName("Test Three: petición a las 21:00 del día 14 del producto 35455   para la brand 1 (CADENA1)")
    @Test
    public void givenDateApliProdIdBrandId_whenSearch_thenReturnResultSearchThree() throws Exception {
        // given - precondition or setup
        LocalDateTime fechaAplicacion = LocalDateTime.of(2020, Month.JUNE, 14,21,0,0);
        long prodId = 35455;
        long brandId = 1;


        // when - action or the behavior that we are going test
        List<PRICES> listPricesDB = priceRepository.findByDateRequestIdProductIdBrand(fechaAplicacion, prodId, brandId);

        // then - verify the output

        Assertions.assertThat(listPricesDB.size()).isGreaterThan(0);
        Assertions.assertThat(listPricesDB.get(0).getBrand().getId()).isEqualTo(1);
        Assertions.assertThat(listPricesDB.get(0).getPRODUCT_ID()).isEqualTo(35455);
    }

    // "Test Four: petición a las 10:00 del día 15 del producto 35455   para la brand 1 (CADENA1)"
    @DisplayName("Test Four: petición a las 10:00 del día 15 del producto 35455   para la brand 1 (CADENA1)")
    @Test
    public void givenDateApliProdIdBrandId_whenSearch_thenReturnResultSearchFour() throws Exception {
        // given - precondition or setup
        LocalDateTime fechaAplicacion = LocalDateTime.of(2020, Month.JUNE, 15,10,0,0);
        long prodId = 35455;
        long brandId = 1;


        // when - action or the behavior that we are going test
        List<PRICES> listPricesDB = priceRepository.findByDateRequestIdProductIdBrand(fechaAplicacion, prodId, brandId);

        // then - verify the output

        Assertions.assertThat(listPricesDB.size()).isGreaterThan(0);
        Assertions.assertThat(listPricesDB.get(0).getBrand().getId()).isEqualTo(1);
        Assertions.assertThat(listPricesDB.get(0).getPRODUCT_ID()).isEqualTo(35455);
    }

    // Test Five: petición a las 21:00 del día 16 del producto 35455   para la brand 1 (CADENA1)
    @DisplayName("Test Five: petición a las 21:00 del día 16 del producto 35455   para la brand 1 (CADENA1)")
    @Test
    public void givenDateApliProdIdBrandId_whenSearch_thenReturnResultSearchFive() throws Exception {
        // given - precondition or setup
        LocalDateTime fechaAplicacion = LocalDateTime.of(2020, Month.JUNE, 15,21,0,0);
        long prodId = 35455;
        long brandId = 1;


        // when - action or the behavior that we are going test
        List<PRICES> listPricesDB = priceRepository.findByDateRequestIdProductIdBrand(fechaAplicacion, prodId, brandId);

        // then - verify the output

        Assertions.assertThat(listPricesDB.size()).isGreaterThan(0);
        Assertions.assertThat(listPricesDB.get(0).getBrand().getId()).isEqualTo(1);
        Assertions.assertThat(listPricesDB.get(0).getPRODUCT_ID()).isEqualTo(35455);
    }
}
