package org.hyunseokcheong.catalogservice.service;

import org.hyunseokcheong.catalogservice.jpa.CatalogEntity;

public interface CatalogService {
	
	Iterable<CatalogEntity> getAllCatalogs();
}
