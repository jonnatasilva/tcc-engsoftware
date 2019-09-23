package com.animals.care.animalscare.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSaleProduct is a Querydsl query type for SaleProduct
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QSaleProduct extends EntityPathBase<SaleProduct> {

    private static final long serialVersionUID = 1893757853L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSaleProduct saleProduct = new QSaleProduct("saleProduct");

    public final NumberPath<Integer> amount = createNumber("amount", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QProduct product;

    public QSaleProduct(String variable) {
        this(SaleProduct.class, forVariable(variable), INITS);
    }

    public QSaleProduct(Path<? extends SaleProduct> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSaleProduct(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSaleProduct(PathMetadata metadata, PathInits inits) {
        this(SaleProduct.class, metadata, inits);
    }

    public QSaleProduct(Class<? extends SaleProduct> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.product = inits.isInitialized("product") ? new QProduct(forProperty("product"), inits.get("product")) : null;
    }

}

