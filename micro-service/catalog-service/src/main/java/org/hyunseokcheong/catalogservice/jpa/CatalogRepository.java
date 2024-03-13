package org.hyunseokcheong.catalogservice.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CatalogRepository extends JpaRepository<CatalogEntity, Long> {
	
	CatalogEntity findByProductId(String productId);
}
