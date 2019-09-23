package com.animals.care.animalscare.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSale is a Querydsl query type for Sale
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QSale extends EntityPathBase<Sale> {

    private static final long serialVersionUID = 487775474L;

    public static final QSale sale = new QSale("sale");

    public final DateTimePath<java.time.LocalDateTime> saleDate = createDateTime("saleDate", java.time.LocalDateTime.class);

    public final NumberPath<Long> saleId = createNumber("saleId", Long.class);

    public final SetPath<SaleProduct, QSaleProduct> saleProduct = this.<SaleProduct, QSaleProduct>createSet("saleProduct", SaleProduct.class, QSaleProduct.class, PathInits.DIRECT2);

    public QSale(String variable) {
        super(Sale.class, forVariable(variable));
    }

    public QSale(Path<? extends Sale> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSale(PathMetadata metadata) {
        super(Sale.class, metadata);
    }

}

