package com.kn.citylist.api;

import java.util.Optional;

import com.kn.citylist.model.City;
import com.kn.citylist.model.CityPage;
import com.kn.citylist.model.CitySearchCriteria;
import com.kn.citylist.service.CityService;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cities")
public class CityController {

  private final CityService cityService;

  public CityController(CityService cityService) {
    this.cityService = cityService;
  }

  @GetMapping
  public ResponseEntity<Page<City>> getCities(CityPage cityPage, CitySearchCriteria citySearchCriteria) {
    return new ResponseEntity<>(
      cityService.getCities(cityPage, citySearchCriteria), HttpStatus.OK
    );
  }

  @PatchMapping(path = "{id}")
  public ResponseEntity<Optional<City>> updateCity(
    @RequestBody City city, 
    @PathVariable("id") Long id) {
    return new ResponseEntity<>(
      cityService.updateCity(id, city), HttpStatus.OK
    );
  }
}
