package com.doggis.api.response;

import com.doggis.api.domain.Product;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class StockResponse {

    private Long stockId;
    private Product product;
    private Integer amount;


}
