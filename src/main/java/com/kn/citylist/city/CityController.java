package com.kn.citylist.city;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cities")
public class CityController {
  @Autowired
  CityRepository cityRepository;

  @GetMapping
  public Page<City> getCities(
    @RequestParam Optional<Integer> size,
    @RequestParam Optional<Integer> page,
    @RequestParam Optional<String> sortBy
  ) {
    return cityRepository.findAll(
      PageRequest.of(
        page.orElse(0),
        size.orElse(10),
        Sort.Direction.ASC, sortBy.orElse("name")
      )
      );
  }
}
