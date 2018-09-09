package com.learning.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.learning.springboot.model.Language;

@RepositoryRestResource(path = "language", collectionResourceRel = "language")
public interface ILanguageRepository extends JpaRepository<Language, Long> {

}
