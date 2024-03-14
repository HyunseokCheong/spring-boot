package org.hyunseokcheong.purchaseservice.vo;

import lombok.Data;

@Data
public class RequestPurchase {
	
	private String productId;
	private Integer quantity;
	private Integer unitPrice;
}
