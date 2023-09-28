package com.javaguides.springboot.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javaguides.springboot.dto.PricesDto;
import com.javaguides.springboot.mapper.PricesMapper;
import com.javaguides.springboot.model.BRAND;
import com.javaguides.springboot.model.PRICES;
import com.javaguides.springboot.service.PriceBrandService;
import org.assertj.core.api.Assertions;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcResultMatchersDsl;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.anything;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

/*
 * Clase PriceBrandControllerTest usada para comprobar con Mocks el correcto funcionamiento de
 *  /src/main/java/com.javaguides.springboot/controller/PriceBrandController
 */
@WebMvcTest
public class PriceBrandControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PriceBrandService priceBrandService;

    @Autowired
    private ObjectMapper objectMapper;

    /*
     * Test givenPriceObjectBrandObject_whenSavePriceObjectBrandObject_thenReturnPriceObjectBrandObjectBadScenario(
     *   --> Entrada: PriceObject BrandObject
     *   --> Proceso:
     *       --> Se simula con Mocks que se guarda en la base de datos Price y Mock, en este caso el escenario sera negativo,
     *          ya que no existe al no trabajar con la base de datos, no existe relacion One-To-Many entre Brand y Price
     *          , por lo tanto devolvera pagina NOT_FOUND
     *   --> Salida:
     *       --> Pagina NOT_FOUND
     */

    // Junit test for save Precie Object and Brand Object
    @DisplayName("Junit test for save Precie Object and Brand Object")
    @Test
    public void givenPriceObjectBrandObject_whenSavePriceObjectBrandObject_thenReturnPriceObjectBrandObjectBadScenario() throws Exception {
        // given - precondition or setup
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

        Set<PRICES> pricesSet = new HashSet<>();
        pricesSet.add(pricesOne);
        brandOne.setPrices(pricesSet);

        BDDMockito.given(priceBrandService.savePriceBrand(any(PRICES.class), any(BRAND.class))).willAnswer((invocations)-> invocations.getArgument(0));

        PricesDto pricesDto = PricesMapper.mapToPricesDto(pricesOne);

        // when - action or the behavior that we are going test
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.
                post("/api/prices/price")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pricesDto)));

        // then - verify the output
        response.andDo(print()).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /*
     * Test givenListPrices_whenGetAllPrices_thenReturnPricesList()
     *   --> Entrada: ListPriceObject
     *   --> Proceso:
     *       --> Se simula con Mocks que se devuelven todos los precios
     *   --> Salida:
     *       --> ListPriceObject
     */
    // Junit test for Get All Precies Object
    @DisplayName("Junit test for save Price Object and Brand Object")
    @Test
    public void givenListPrices_whenGetAllPrices_thenReturnPricesList() throws Exception {
        // given - precondition or setup
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

        ArrayList<PRICES> listPrices = new ArrayList<PRICES>();
        listPrices.add(pricesOne);
        listPrices.add(pricesTwo);
        BDDMockito.given(priceBrandService.getAllPrices()).willReturn(listPrices);

        // when - action or the behavior that we are going test
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.
                get("/api/prices/"));

        // then - verify the output
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(listPrices.size())));

        ;
    }

    /*
     * Test givenIdPrice_whenFindByIdPrice_thenReturnPrice()
     *   --> Entrada: Id Price
     *   --> Proceso:
     *       --> Se simula con Mocks que se busca precios por Id
     *   --> Salida:
     *       --> PriceObject por ID
     */
    // positive scenario - void price id
    // Junit test for get Price Object by Id
    @DisplayName("Junit test for get Precie Object by Id")
    @Test
    public void givenIdPrice_whenFindByIdPrice_thenReturnPrice() throws Exception {
        // given - precondition or setup
        DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        long priceId = 1L;
        BRAND brandOne = BRAND.builder()
                .id(1)
                .name("Example")
                .description("Example description")
                .build();

        PRICES pricesOne = PRICES.builder()
                .brand(brandOne)
                .START_DATE( LocalDateTime.of(2020, Month.JUNE, 14,0,0))
                .END_DATE( LocalDateTime.of(2020, Month.DECEMBER, 31,23,59))
                .PRICE_LIST(1)
                .PRODUCT_ID(35455)
                .PRIORITY(0)
                .price(35.50)
                .CURR("EUR").build();

        ArrayList<PRICES> listPrices = new ArrayList<PRICES>();
        listPrices.add(pricesOne);
        BDDMockito.given(priceBrandService.getPriceById(priceId)).willReturn(Optional.of(pricesOne));

        // when - action or the behavior that we are going test
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.
                get("/api/prices/{id}", priceId));

        // then - verify the output
        System.out.println(response.toString());
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.curr", CoreMatchers.is(pricesOne.getCURR())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.priority", CoreMatchers.is(pricesOne.getPRIORITY())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.start_DATE", CoreMatchers.is(pricesOne.getSTART_DATE().format(formatterDate))))
                .andExpect(MockMvcResultMatchers.jsonPath("$.end_DATE", CoreMatchers.is(pricesOne.getEND_DATE().format(formatterDate))));
    }

    /*
     * Test givenInvalidIdPrice_whenFindByIdPrice_thenReturnHttpNotFound():
     *   --> Entrada: Id Price
     *   --> Proceso:
     *       --> Se simula con Mocks que se busca precios por Id y no se encuentra
     *   --> Salida:
     *       --> HTTP no found
     */
    // negative scenario - void price id
    // Junit test for get Precie Object by Id
    @DisplayName("Junit test for get Precie Object by Id")
    @Test
    public void givenInvalidIdPrice_whenFindByIdPrice_thenReturnPriceEmpty() throws Exception {
        // given - precondition or setup
        DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        long priceId = 1L;
        BRAND brandOne = BRAND.builder()
                .id(1)
                .name("Example")
                .description("Example description")
                .build();

        PRICES pricesOne = PRICES.builder()
                .brand(brandOne)
                .START_DATE( LocalDateTime.of(2020, Month.JUNE, 14,0,0))
                .END_DATE( LocalDateTime.of(2020, Month.DECEMBER, 31,23,59))
                .PRICE_LIST(1)
                .PRODUCT_ID(35455)
                .PRIORITY(0)
                .price(35.50)
                .CURR("EUR").build();

        ArrayList<PRICES> listPrices = new ArrayList<PRICES>();
        listPrices.add(pricesOne);
        BDDMockito.given(priceBrandService.getPriceById(priceId)).willReturn(Optional.empty());

        // when - action or the behavior that we are going test
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.
                get("/api/prices/{id}", priceId));

        // then - verify the output
        response.andExpect(MockMvcResultMatchers.status().isNotFound()).andDo(print());
    }


    /*
     * Test givenUpdatedPrice_whenUpdatedPrice_thenReturnUpdatedPrice():
     *   --> Entrada: PRICE a actualizar
     *   --> Proceso:
     *       --> Se simula con Mocks que se actualiza Price en este caso el escenario sera negativo,
     *          ya que no existe al no trabajar con la base de datos, no existe relacion One-To-Many entre Brand y Price
     *          , por lo tanto devolvera pagina NOT_FOUND
     *   --> Salida:
     *       --> pagina NOT_FOUND
     */
    // Junit test for update Precie REST API
    @DisplayName("Junit test for put Precie")
    @Test
    public void givenUpdatedPrice_whenUpdatedPrice_thenReturnUpdatedPrice() throws Exception {
        // given - precondition or setup
        DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        long priceId = 1L;
        BRAND brandOne = BRAND.builder()
                .id(1)
                .name("Example")
                .description("Example description")
                .build();

        PRICES priceSaved = PRICES.builder()
                .brand(brandOne)
                .START_DATE( LocalDateTime.of(2020, Month.JUNE, 14,0,0))
                .END_DATE( LocalDateTime.of(2020, Month.DECEMBER, 31,23,59))
                .PRICE_LIST(1)
                .PRODUCT_ID(35455)
                .PRIORITY(0)
                .price(35.50)
                .PRICE_ID(1)
                .CURR("EUR").build();

        PRICES priceUpdated = PRICES.builder()
                .brand(brandOne)
                .START_DATE( LocalDateTime.of(2020, Month.JUNE, 14,0,0))
                .END_DATE( LocalDateTime.of(2020, Month.DECEMBER, 31,23,59))
                .PRICE_LIST(2)
                .PRODUCT_ID(35466)
                .PRIORITY(0)
                .price(37.50)
                .PRICE_ID(1)
                .CURR("EUR").build();

        ArrayList<PRICES> listPrices = new ArrayList<PRICES>();
        listPrices.add(priceSaved);


        BDDMockito.given(priceBrandService.getPriceById(priceId)).willReturn(Optional.of(priceSaved));
        BDDMockito.given(priceBrandService.updatePrice(any(PRICES.class))).willAnswer((invocation)->invocation.getArgument(0));

        // when - action or the behavior that we are going test
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.
                put("/api/prices//price/{id}", priceId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(priceUpdated)));

        // then - verify the output
        System.out.println(response.toString());
        response.andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(print());
    }

    /*
     * Test givenUpdatedPrice_whenUpdatedPrice_thenReturn404():
     *   --> Entrada: PRICE a actualizar
     *   --> Proceso:
     *       --> Se simula con Mocks que no se actualiza Price correctamente en la BD
     *   --> Salida:
     *       --> HTTP_404
     */
    // Junit test for update Precie REST API - negative sceneario
    @DisplayName("Junit test for put Price - negative sceneario")
    @Test
    public void givenUpdatedPrice_whenUpdatedPrice_thenReturn404() throws Exception {
        // given - precondition or setup
        DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        long priceId = 1L;
        BRAND brandOne = BRAND.builder()
                .id(1)
                .name("Example")
                .description("Example description")
                .build();

        PRICES priceSaved = PRICES.builder()
                .PRICE_ID(1)
                .brand(brandOne)
                .START_DATE( LocalDateTime.of(2020, Month.JUNE, 14,0,0))
                .END_DATE( LocalDateTime.of(2020, Month.DECEMBER, 31,23,59))
                .PRICE_LIST(1)
                .PRODUCT_ID(35455)
                .PRIORITY(0)
                .price(35.50)
                .CURR("EUR").build();

        PRICES priceUpdated = PRICES.builder()
                .PRICE_ID(1)
                .brand(brandOne)
                .START_DATE( LocalDateTime.of(2020, Month.JUNE, 14,0,0))
                .END_DATE( LocalDateTime.of(2020, Month.DECEMBER, 31,23,59))
                .PRICE_LIST(2)
                .PRODUCT_ID(35466)
                .PRIORITY(0)
                .price(37.50)
                .CURR("EUR").build();

        ArrayList<PRICES> listPrices = new ArrayList<PRICES>();
        listPrices.add(priceSaved);


        BDDMockito.given(priceBrandService.getPriceById(priceId)).willReturn(Optional.empty());
        BDDMockito.given(priceBrandService.updatePrice(any(PRICES.class))).willAnswer((invocation)->invocation.getArgument(0));

        // when - action or the behavior that we are going test
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.
                put("/api/prices//price/{id}", priceId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(priceUpdated)));

        // then - verify the output
        System.out.println(response.toString());
        response.andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(print());
    }

    /*
     * Test givenIdPrice_whenDeletePrice_thenReturn202():
     *   --> Entrada: PRICE a eliminar
     *   --> Proceso:
     *       --> Se simula con Mocks que se eliminar PRICE
     *   --> Salida:
     *       --> HTTP_OK
     */
    // Junit test for update Precie REST API - negative sceneario
    @DisplayName("Junit test for put Price - negative sceneario")
    @Test
    public void givenIdPrice_whenDeletePrice_thenReturn202() throws Exception {
        // given - precondition or setup
        DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        long priceId = 1L;

        BDDMockito.willDoNothing().given(priceBrandService).deletePrice(priceId);

        // when - action or the behavior that we are going test
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.delete("/api/prices/price/{id}", priceId));

        // then - verify the output
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());
    }
}
