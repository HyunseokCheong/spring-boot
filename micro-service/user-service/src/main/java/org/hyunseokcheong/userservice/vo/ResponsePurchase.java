package org.hyunseokcheong.userservice.vo;

import java.util.Date;

import lombok.Data;

@Data
public class ResponsePurchase {
	
	private String productId;
	private Integer quantity;
	private Integer unitPrice;
	private Integer totalPrice;
	private Date createdAt;
	
	private String purchaseId;
}
