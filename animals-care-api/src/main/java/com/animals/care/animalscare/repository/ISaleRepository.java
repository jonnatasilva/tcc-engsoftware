package com.animals.care.animalscare.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.animals.care.animalscare.domain.Sale;

public interface ISaleRepository extends JpaRepository<Sale, Long> {

}
