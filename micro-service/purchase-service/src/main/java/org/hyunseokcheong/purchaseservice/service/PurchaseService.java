package org.hyunseokcheong.purchaseservice.service;

import org.hyunseokcheong.purchaseservice.dto.PurchaseDto;
import org.hyunseokcheong.purchaseservice.jpa.PurchaseEntity;

public interface PurchaseService {
	
	PurchaseDto createPurchase(PurchaseDto purchaseDto);
	
	PurchaseDto getPurchaseByPurchaseId(String purchaseId);
	
	Iterable<PurchaseEntity> getPurchasesByUserId(String userId);
}
