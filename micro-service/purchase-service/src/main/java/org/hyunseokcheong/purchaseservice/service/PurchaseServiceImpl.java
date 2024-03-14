package org.hyunseokcheong.purchaseservice.service;

import java.util.UUID;

import org.hyunseokcheong.purchaseservice.dto.PurchaseDto;
import org.hyunseokcheong.purchaseservice.jpa.PurchaseEntity;
import org.hyunseokcheong.purchaseservice.jpa.PurchaseRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PurchaseServiceImpl implements PurchaseService {
	
	private PurchaseRepository purchaseRepository;
	
	@Autowired
	public PurchaseServiceImpl(PurchaseRepository purchaseRepository) {
		this.purchaseRepository = purchaseRepository;
	}
	
	@Override
	public PurchaseDto createPurchase(PurchaseDto purchaseDto) {
		purchaseDto.setPurchaseId(UUID.randomUUID().toString());
		purchaseDto.setTotalPrice(purchaseDto.getQuantity() * purchaseDto.getUnitPrice());
		
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		PurchaseEntity purchaseEntity = modelMapper.map(purchaseDto, PurchaseEntity.class);
		
		purchaseRepository.save(purchaseEntity);
		
		return modelMapper.map(purchaseEntity, PurchaseDto.class);
	}
	
	@Override
	public PurchaseDto getPurchaseByPurchaseId(String purchaseId) {
		PurchaseEntity purchaseEntity = purchaseRepository.findByPurchaseId(purchaseId);
		
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		
		return modelMapper.map(purchaseEntity, PurchaseDto.class);
	}
	
	@Override
	public Iterable<PurchaseEntity> getPurchasesByUserId(String userId) {
		return purchaseRepository.findByUserId(userId);
	}
}
