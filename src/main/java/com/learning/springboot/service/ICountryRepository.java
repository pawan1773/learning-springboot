package com.learning.springboot.service;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.learning.springboot.model.Country;

//@RepositoryRestResource(path = "country", collectionResourceRel = "country")
@Repository
public interface ICountryRepository extends JpaRepository<Country, Long> {

	Optional<Country> findByCode(String code);
	
	@Query("select c from Country c join fetch c.languages l where c.id = :cid and l.id = :lid")
	Optional<Country> getSpecificLanguageOfCountry(@Param("cid")Long cid, @Param("lid")Long lid);

	// http://localhost:8081/country/search/findByName?name=India
	// List<Country> findByName(@Param("name") String role);
}
