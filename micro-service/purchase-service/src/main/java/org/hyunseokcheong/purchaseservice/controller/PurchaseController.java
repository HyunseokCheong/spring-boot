package org.hyunseokcheong.purchaseservice.controller;

import java.util.ArrayList;
import java.util.List;

import org.hyunseokcheong.purchaseservice.dto.PurchaseDto;
import org.hyunseokcheong.purchaseservice.jpa.PurchaseEntity;
import org.hyunseokcheong.purchaseservice.service.PurchaseService;
import org.hyunseokcheong.purchaseservice.vo.RequestPurchase;
import org.hyunseokcheong.purchaseservice.vo.ResponsePurchase;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/purchase")
@RequiredArgsConstructor
public class PurchaseController {
	
	private PurchaseService purchaseService;
	
	@Autowired
	public PurchaseController(PurchaseService purchaseService) {
		this.purchaseService = purchaseService;
	}
	
	@PostMapping("/{userId}/purchase")
	public ResponseEntity<Object> createPurchase(@PathVariable("userId") String userId, @RequestBody RequestPurchase requestPurchase) {
		
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		
		PurchaseDto purchaseDto = modelMapper.map(requestPurchase, PurchaseDto.class);
		purchaseDto.setUserId(userId);
		PurchaseDto createdPurchase = purchaseService.createPurchase(purchaseDto);
		
		ResponsePurchase responsePurchase = modelMapper.map(createdPurchase, ResponsePurchase.class);
		
		return ResponseEntity.status(HttpStatus.CREATED)
			.body(responsePurchase);
	}
	
	@GetMapping("/{purchaseId}")
	public ResponseEntity<Object> getPurchaseByPurchaseId(@PathVariable("purchaseId") String purchaseId) {
		
		PurchaseDto purchaseDto = purchaseService.getPurchaseByPurchaseId(purchaseId);
		
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		
		ResponsePurchase responsePurchase = modelMapper.map(purchaseDto, ResponsePurchase.class);
		
		return ResponseEntity.status(HttpStatus.OK)
			.body(responsePurchase);
	}
	
	@GetMapping("/{userId}/purchase")
	public ResponseEntity<Object> getPurchasesByUserId(@PathVariable("userId") String userId) {
		
		Iterable<PurchaseEntity> purchases = purchaseService.getPurchasesByUserId(userId);
		
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		
		List<ResponsePurchase> result = new ArrayList<>();
		purchases.forEach(v -> result.add(modelMapper.map(v, ResponsePurchase.class)));
		
		return ResponseEntity.status(HttpStatus.OK)
			.body(result);
	}
}
