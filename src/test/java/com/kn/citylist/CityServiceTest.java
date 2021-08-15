package com.kn.citylist;

import com.kn.citylist.model.City;
import com.kn.citylist.model.CityPage;
import com.kn.citylist.model.CitySearchCriteria;
import com.kn.citylist.repository.CityRepository;
import com.kn.citylist.service.CityService;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
class CityServiceTest {

    @Autowired
    private CityRepository cityRepository;
    
    @Autowired
    private CityService cityService;

		@BeforeAll
		void setup() throws Exception {
			cityRepository.save(new City("Ikeja", "http://ikeja-nigeria.local"));
			cityRepository.save(new City("Tallinn", "http://tallin-estonia.local"));
			cityRepository.save(new City("Helsinki", "http://helsinki-finland.local"));
			cityRepository.save(new City("Riga", "http://riga-latvia.local"));
			cityRepository.save(new City("Rio", "http://rio-brazil.local"));
		}

    @Test
    void retrievingCityByIdWorks() {
        City city = cityRepository.save(new City("Akute", "http://akute.local"));

        Optional<City> foundCity = cityRepository.findById(city.getId());

        assertNotNull(foundCity.get());
        assertEquals(foundCity.get().getName(), city.getName());
    }

    @Test
    void filteringCitiesByCaseInsensitiveName() {
      CityPage cityPage = new CityPage();
      CitySearchCriteria citySearchCriteria = new CitySearchCriteria();
      citySearchCriteria.setName("ikeja");
      
      Page<City> cityPageResult = cityService.getCities(cityPage, citySearchCriteria);

      assertEquals(1, cityPageResult.getNumberOfElements());
      assertEquals("Ikeja", cityPageResult.getContent().get(0).getName());
    }

    @Test
    void filteringCitiesWithMultipleResults() {
      CityPage cityPage = new CityPage();
      CitySearchCriteria citySearchCriteria = new CitySearchCriteria();
      citySearchCriteria.setName("ri");
      
      Page<City> cityPageResult = cityService.getCities(cityPage, citySearchCriteria);

      assertEquals(2, cityPageResult.getNumberOfElements());
      assertEquals("Riga", cityPageResult.getContent().get(0).getName());
    }

    @Test
    void updatingACityIsPossibleWithPartialData() {
      City city = cityRepository.save(new City("Arl", "http://arlanda-sweden.local"));

      City newCity = new City();
      newCity.setName("Arlanda");

      Optional<City> updatedCity = cityService.updateCity(city.getId(), newCity);
      assertEquals(true, updatedCity.isPresent());
      assertEquals("Arlanda", updatedCity.get().getName());
      assertEquals("http://arlanda-sweden.local", updatedCity.get().getPhoto());
    }
}
