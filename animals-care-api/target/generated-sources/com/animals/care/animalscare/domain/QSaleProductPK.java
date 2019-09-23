package com.animals.care.animalscare.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QSaleProductPK is a Querydsl query type for SaleProductPK
 */
@Generated("com.querydsl.codegen.EmbeddableSerializer")
public class QSaleProductPK extends BeanPath<SaleProductPK> {

    private static final long serialVersionUID = -1164834216L;

    public static final QSaleProductPK saleProductPK = new QSaleProductPK("saleProductPK");

    public final NumberPath<Long> productId = createNumber("productId", Long.class);

    public final NumberPath<Long> saleId = createNumber("saleId", Long.class);

    public QSaleProductPK(String variable) {
        super(SaleProductPK.class, forVariable(variable));
    }

    public QSaleProductPK(Path<? extends SaleProductPK> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSaleProductPK(PathMetadata metadata) {
        super(SaleProductPK.class, metadata);
    }

}

