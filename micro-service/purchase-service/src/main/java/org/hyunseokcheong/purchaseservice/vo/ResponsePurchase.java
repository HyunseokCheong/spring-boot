package org.hyunseokcheong.purchaseservice.vo;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponsePurchase {
	
	private String productId;
	private Integer quantity;
	private Integer unitPrice;
	private Integer totalPrice;
	
	private String purchaseId;
	private String userId;
}
