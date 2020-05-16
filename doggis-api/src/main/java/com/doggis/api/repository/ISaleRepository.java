package com.doggis.api.repository;

import com.doggis.api.domain.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ISaleRepository extends JpaRepository<Sale, Long> {

}
