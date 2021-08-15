package com.kn.citylist.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.kn.citylist.model.City;
import com.kn.citylist.model.CityPage;
import com.kn.citylist.model.CitySearchCriteria;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

@Repository
public class CityCriteriaRepository {
  private final EntityManager entityManager;
  private final CriteriaBuilder criteriaBuilder;

  public CityCriteriaRepository(EntityManager entityManager) {
    this.entityManager = entityManager;
    this.criteriaBuilder = entityManager.getCriteriaBuilder();
  }

  public Page<City> findAllWithFilters(CityPage cityPage, CitySearchCriteria citySearchCriteria) {
    CriteriaQuery<City> criteriaQuery = criteriaBuilder.createQuery(City.class);
    Root<City> cityRoot = criteriaQuery.from(City.class);

    Predicate predicate = getPredicate(citySearchCriteria, cityRoot);
    criteriaQuery.where(predicate);

    setOrder(cityPage, criteriaQuery, cityRoot);

    TypedQuery<City> typedQuery = entityManager.createQuery(criteriaQuery);
    typedQuery.setFirstResult(cityPage.getPageNumber() * cityPage.getPageSize());
    typedQuery.setMaxResults(cityPage.getPageSize());

    Pageable pageable = getPageable(cityPage);

    Long cityCount = getCityCount(predicate);

    return new PageImpl<>(typedQuery.getResultList(), pageable, cityCount);
  }

  private Predicate getPredicate(CitySearchCriteria citySearchCriteria, Root<City> cityRoot) {
    List<Predicate> predicates = new ArrayList<>();

    if(Objects.nonNull(citySearchCriteria.getName())) {
      String searchString = "%" + citySearchCriteria.getName() + "%";

      predicates.add(
        criteriaBuilder.like(
          criteriaBuilder.lower(cityRoot.get("name")),
          searchString.toLowerCase()
        )
      );
    }

    return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
  }

  private void setOrder(CityPage cityPage, CriteriaQuery<City> criteriaQuery, Root<City> cityRoot) {
    if(cityPage.getSortDirection().equals(Sort.Direction.ASC)) {
      criteriaQuery.orderBy(criteriaBuilder.asc(cityRoot.get(cityPage.getSortBy())));
    } else {
      criteriaQuery.orderBy(criteriaBuilder.desc(cityRoot.get(cityPage.getSortBy())));
    }
  }

  private Pageable getPageable(CityPage cityPage) {
    Sort sort = Sort.by(cityPage.getSortDirection(), cityPage.getSortBy());
    return PageRequest.of(cityPage.getPageNumber(), cityPage.getPageSize(), sort);
  }

  private Long getCityCount(Predicate predicate) {
    CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
    Root<City> countRoot = countQuery.from(City.class);
    countQuery.select(criteriaBuilder.count(countRoot)).where(predicate);

    return entityManager.createQuery(countQuery).getSingleResult();
  }
}
