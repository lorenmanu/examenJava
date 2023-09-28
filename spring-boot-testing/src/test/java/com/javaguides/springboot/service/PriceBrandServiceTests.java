package com.javaguides.springboot.service;

import com.javaguides.springboot.exception.ResourceNotFoundException;
import com.javaguides.springboot.model.BRAND;
import com.javaguides.springboot.model.PRICES;
import com.javaguides.springboot.repository.BrandRepository;
import com.javaguides.springboot.repository.PriceRepository;
import com.javaguides.springboot.service.impl.PriceBrandServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/*
 * Clase PriceBrandServiceTests para comprobar el correcto funcionamiento PriceBrandService, el cual comunica Controller con Repository
 */


@ExtendWith(MockitoExtension.class)
public class PriceBrandServiceTests {
    @Mock
    private PriceRepository priceRepository;
    @Mock
    private BrandRepository brandRepository;

    @InjectMocks
    private PriceBrandServiceImpl priceBrandService;

    private BRAND brandOne;

    private PRICES priceOne;

    /*
        Ejecución previa a cada tests para ejecutarlos con los datos correctos, en este caso se crea brandone y price al no usar BD
     */
    @BeforeEach
    public void setup(){
        // priceRepository = Mockito.mock(PriceRepository.class);
        // brandRepository = Mockito.mock(BrandRepository.class);
        // priceBrandService = new PriceBrandServiceImpl(priceRepository, brandRepository);

        brandOne = BRAND.builder()
                .id(1)
                .name("Example")
                .description("Example description")
                .build();

        priceOne = PRICES.builder()
                .brand(brandOne)
                .START_DATE( LocalDateTime.of(2020, Month.JUNE, 14,0,0,0))
                .END_DATE( LocalDateTime.of(2020, Month.DECEMBER, 31,23,59,59))
                .PRICE_LIST(1)
                .PRODUCT_ID(35455)
                .PRIORITY(0)
                .price(35.50)
                .CURR("EUR").build();

        Set<PRICES> pricesSet = new HashSet<>();
        pricesSet.add(priceOne);
        brandOne.setPrices(pricesSet);
    }

    /*
     * Test givenPricesObjectBrandObject_whenSave_thenReturnSavedPricesBrand
     *   --> Entrada: PriceObject BrandObject a guardar
     *   --> Proceso:
     *       --> Se simula con Mocks que se guarda PriceObject Y brandobject en la bd
     *   --> Salida:
     *       --> PriceObject BrandObejct guardado
     */
    // Junit test for save Precie Object and Brand Object
    @DisplayName("Junit test for save Precie Object and Brand Object")
    @Test
    public void givenPriceObjectBrandObject_whenSavePriceObjectBrandObject_thenReturnPriceObjectBrandObject(){
        // given - precondition or setup
        BDDMockito.given(priceRepository.findById(priceOne.getPRODUCT_ID())).willReturn(Optional.empty());
        BDDMockito.given(brandRepository.findById(brandOne.getId())).willReturn(Optional.empty());

        BDDMockito.given(brandRepository.save(brandOne)).willReturn(brandOne);

        // when - action or the behavior that we are going test
        BRAND brandSave = priceBrandService.savePriceBrand(priceOne, brandOne);
        PRICES[] arrayPrices = brandSave.getPrices().toArray(new PRICES[brandSave.getPrices().size()]);

        // then - verify the output
        Assertions.assertThat(arrayPrices).isNotNull();
        Assertions.assertThat(arrayPrices.length).isEqualTo(1);

        Assertions.assertThat(brandSave).isNotNull();
    }
    /*
     * Test givenPricesObjectBrandObject_whenSave_thenReturnSavedPricesBrand
     *   --> Entrada: PriceObject BrandObject a guardar
     *   --> Proceso:
     *       --> Se simula con Mocks que existe en la bd , no se guarda PriceObject Y brandobject y se lanza excepcion
     *   --> Salida:
     *       --> PriceObject BrandObejct guardado
     */
    // Junit test for save Price Object and Brand Object with throws exception
    @DisplayName("Junit test for save Precie Object and Brand Object with throws exception")
    @Test
    public void givenExistingPriceObjectBrandObject_whenSavePriceObjectBrandObject_thenThrowsException(){
        // given - precondition or setup
        BDDMockito.given(priceRepository.findById(priceOne.getPRODUCT_ID())).willReturn(Optional.of(priceOne));
        BDDMockito.given(brandRepository.findById(brandOne.getId())).willReturn(Optional.of(brandOne));

        // BDDMockito.given(brandRepository.save(brandOne)).willReturn(brandOne);

        // when - action or the behavior that we are going test
        org.junit.jupiter.api.Assertions.assertThrows(ResourceNotFoundException.class, ()->{
            priceBrandService.savePriceBrand(priceOne, brandOne);
        });

        // then
        verify(brandRepository, Mockito.never()).save(any(BRAND.class));
    }
    /*
     * Test givenPriceListBrandList_whenGetAllPriceBrand_thenReturnPriceListBrandList
     *   --> Entrada: List de PriceObject List BrandObject
     *   --> Proceso:
     *       --> Se simula con Mocks que se obtienen todos los Prices y Brands de la base de datos
     *   --> Salida:
     *       --> Se devuelve la lista de Prices y Brands
     */
    // Junit test for get all PRICES and BRAND operation
    @DisplayName("Junit test for get all PRICES and BRAND operation")
    @Test
    public void givenPriceListBrandList_whenGetAllPriceBrand_thenReturnPriceListBrandList(){
        // given - precondition or setup

        PRICES priceTwo = PRICES.builder()
                .brand(brandOne)
                .START_DATE( LocalDateTime.of(2020, Month.JUNE, 14,0,0,0))
                .END_DATE( LocalDateTime.of(2020, Month.DECEMBER, 31,23,59,59))
                .PRICE_LIST(1)
                .PRODUCT_ID(35455)
                .PRIORITY(0)
                .price(35.50)
                .CURR("EUR").build();

        brandOne.getPrices().add(priceTwo);

        BDDMockito.given(priceRepository.findAll()).willReturn(List.of(priceOne,priceTwo));
        BDDMockito.given(brandRepository.findAll()).willReturn(List.of(brandOne));


        // when - action or the behavior that we are going test
        List<BRAND> brandList = priceBrandService.getAllBrands();
        List<PRICES> priceList = priceBrandService.getAllPrices();

        // then
        Assertions.assertThat(brandList).isNotNull();
        Assertions.assertThat(brandList.size()).isEqualTo(1);

        Assertions.assertThat(priceList).isNotNull();
        Assertions.assertThat(priceList.size()).isEqualTo(2);
    }
    /*
     * Test givenEmptyPriceListEmptyBrandList_whenGetAllPriceBrand_thenReturnEmptyPriceListEmptyBrandList
     *   --> Entrada: List de PriceObject List BrandObject
     *   --> Proceso:
     *       --> Se simula con Mocks que se obtienen lista vaciia de Prices y Brands de la base de datos
     *   --> Salida:
     *       --> Se devuelve la lista vacia de Prices y Brands
     */
    // Junit test for get all PRICES and BRAND operation
    @DisplayName("Junit test for get all PRICES and BRANDS operation (negative scenario)")
    @Test
    public void givenEmptyPriceListEmptyBrandList_whenGetAllPriceBrand_thenReturnEmptyPriceListEmptyBrandList(){
        // given - precondition or setup

        PRICES priceTwo = PRICES.builder()
                .brand(brandOne)
                .START_DATE( LocalDateTime.of(2020, Month.JUNE, 14,0,0,0))
                .END_DATE( LocalDateTime.of(2020, Month.DECEMBER, 31,23,59,59))
                .PRICE_LIST(1)
                .PRODUCT_ID(35455)
                .PRIORITY(0)
                .price(35.50)
                .CURR("EUR").build();

        brandOne.getPrices().add(priceTwo);

        BDDMockito.given(priceRepository.findAll()).willReturn(Collections.emptyList());
        BDDMockito.given(brandRepository.findAll()).willReturn(Collections.emptyList());


        // when - action or the behavior that we are going test
        List<BRAND> brandList = priceBrandService.getAllBrands();
        List<PRICES> priceList = priceBrandService.getAllPrices();

        // then
        Assertions.assertThat(brandList).isEmpty();
        Assertions.assertThat(brandList.size()).isEqualTo(0);

        Assertions.assertThat(priceList).isEmpty();
        Assertions.assertThat(priceList.size()).isEqualTo(0);
    }
    /*
     * Test givenPriceIdBrandId_whenFindPriceBrandById_thenReturnPriceByIdBrandById
     *   --> Entrada: Id de Price y Brand
     *   --> Proceso:
     *       --> Se simula con Mocks que se obtienen Price y Brand por id
     *   --> Salida:
     *       --> Se devuelve Price y Brand por Id
     */
    // Junit test for get by Id PRICE and BRAND operation
    @DisplayName("Junit test for get by Id PRICE and BRAND operation")
    @Test
    public void givenPriceIdBrandId_whenFindPriceBrandById_thenReturnPriceByIdBrandById(){
        // given - precondition or setup
        BDDMockito.given(priceRepository.findById(1L)).willReturn(Optional.of(priceOne));
        BDDMockito.given(brandRepository.findById(1L)).willReturn(Optional.of(brandOne));


        // when - action or the behavior that we are going test
        Optional<PRICES> brandByIdOptional = priceBrandService.getPriceById(1L);
        Optional<BRAND> priceByIdOptional = priceBrandService.getBrandById(1L);

        // then
        Assertions.assertThat(brandByIdOptional).isNotNull();

        Assertions.assertThat(priceByIdOptional).isNotNull();
    }
    /*
     * Test givenPriceObjectBrandObject_whenUpdatePriceObjectBrandObject_thenReturnUpdatedPriceObjectBrandObject
     *   --> Entrada: Price Object y Brand Object
     *   --> Proceso:
     *       --> Se simula con Mocks que se guarda Brand y Price en la base de datos
     *   --> Salida:
     *       --> Se devuelve Price y Brand guardado
     */
    // Junit test for get by Id PRICE and BRAND operation
    @DisplayName("Junit test for update PRICE and BRAND operation")
    @Test
    public void givenPriceObjectBrandObject_whenUpdatePriceObjectBrandObject_thenReturnUpdatedPriceObjectBrandObject(){
        // given - precondition or setup
        BDDMockito.given(priceRepository.save(priceOne)).willReturn(priceOne);
        BDDMockito.given(brandRepository.save(brandOne)).willReturn(brandOne);

        priceOne.setPrice(33);
        priceOne.setPRICE_ID(33333);
        brandOne.setName("Example Updated");
        brandOne.setDescription("Example description updated");


        // when - action or the behavior that we are going test
        PRICES priceUpdate = priceBrandService.updatePrice(priceOne);
        BRAND brandUpdate = priceBrandService.updateBrand(brandOne);

        // then
        Assertions.assertThat(priceUpdate.getPrice()).isEqualTo(33);
        Assertions.assertThat(priceUpdate.getPRICE_ID()).isEqualTo(33333);

        Assertions.assertThat(brandUpdate.getName()).isEqualTo("Example Updated");
        Assertions.assertThat(brandUpdate.getDescription()).isEqualTo("Example description updated");
    }
    /*
     * Test givenPriceIdBrandId_whenDeletePriceObjectBrandObject_thenCheckDelete
     *   --> Entrada: Price Id y Brand Id a eliminar
     *   --> Proceso:
     *       --> Se simula con Mocks que se elimina Brand y Price en la base de datos
     *   --> Salida:
     *       --> Se comprueba la eliminación de Brand y Price
     */
    // Junit test for get by Id PRICE and BRAND operation
    @DisplayName("Junit test for delete PRICE and BRAND by Id operation")
    @Test
    public void givenPriceIdBrandId_whenDeletePriceObjectBrandObject_thenCheckDelete(){
        // given - precondition or setup
        long priceId = 1L;
        long brandId = 1L;
        BDDMockito.willDoNothing().given(priceRepository).deleteById(1L);
        BDDMockito.willDoNothing().given(brandRepository).deleteById(1L);

        // when - action or the behavior that we are going test
        priceBrandService.deleteBrand(brandId);
        priceBrandService.deletePrice(priceId);

        // then
        verify(brandRepository, times(1)).deleteById(brandId);
        verify(priceRepository, times(1)).deleteById(priceId);
    }
}
