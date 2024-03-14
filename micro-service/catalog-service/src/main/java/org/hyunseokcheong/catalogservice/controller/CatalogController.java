package org.hyunseokcheong.catalogservice.controller;

import java.util.ArrayList;
import java.util.List;

import org.hyunseokcheong.catalogservice.jpa.CatalogEntity;
import org.hyunseokcheong.catalogservice.service.CatalogService;
import org.hyunseokcheong.catalogservice.vo.ResponseCatalog;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/catalog")
@RequiredArgsConstructor
public class CatalogController {
	
	private CatalogService catalogService;
	
	@Autowired
	public CatalogController(CatalogService catalogService) {
		this.catalogService = catalogService;
	}
	
	@GetMapping
	public ResponseEntity<Object> getAllCatalogs() {
		Iterable<CatalogEntity> catalogs = catalogService.getAllCatalogs();
		
		List<ResponseCatalog> result = new ArrayList<>();
		catalogs.forEach(v -> result.add(new ModelMapper().map(v, ResponseCatalog.class)));
		
		return ResponseEntity.status(HttpStatus.OK)
			.body(result);
	}
}
