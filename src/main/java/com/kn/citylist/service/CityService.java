package com.kn.citylist.service;

import com.kn.citylist.model.City;
import com.kn.citylist.model.CityPage;
import com.kn.citylist.model.CitySearchCriteria;
import com.kn.citylist.repository.CityCriteriaRepository;
import com.kn.citylist.repository.CityRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CityService {

    private final CityRepository cityRepository;
    private final CityCriteriaRepository cityCriteriaRepository;

    public CityService(CityRepository cityRepository, CityCriteriaRepository cityCriteriaRepository) {
        this.cityRepository = cityRepository;
        this.cityCriteriaRepository = cityCriteriaRepository;
    }

    public Page<City> getCities(CityPage cityPage, CitySearchCriteria citySearchCriteria) {
        return cityCriteriaRepository.findAllWithFilters(cityPage, citySearchCriteria);
    }

    public Optional<City> updateCity(Long id, City city) {
        Optional<City> existingCity = cityRepository.findById(id);

        if (existingCity.isPresent()) {
            City oldCity = existingCity.get();
            City cityUpdate = new City();

            cityUpdate.setId(id);
            cityUpdate.setName((city.getName() == null) ? oldCity.getName() : city.getName());
            cityUpdate.setPhoto((city.getPhoto() == null) ? oldCity.getPhoto() : city.getPhoto());

            return Optional.of(
                    cityRepository.save(cityUpdate)
            );
        }
        return existingCity;
    }
}
