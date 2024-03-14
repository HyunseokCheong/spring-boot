package org.hyunseokcheong.purchaseservice.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseRepository extends JpaRepository<PurchaseEntity, Long> {
	
	PurchaseEntity findByPurchaseId(String purchaseId);
	
	Iterable<PurchaseEntity> findByUserId(String userId);
}
