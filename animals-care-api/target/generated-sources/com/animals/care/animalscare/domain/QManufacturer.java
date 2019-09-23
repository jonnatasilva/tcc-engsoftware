package com.animals.care.animalscare.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QManufacturer is a Querydsl query type for Manufacturer
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QManufacturer extends EntityPathBase<Manufacturer> {

    private static final long serialVersionUID = 5596540L;

    public static final QManufacturer manufacturer = new QManufacturer("manufacturer");

    public final NumberPath<Long> manufacturerId = createNumber("manufacturerId", Long.class);

    public final StringPath name = createString("name");

    public QManufacturer(String variable) {
        super(Manufacturer.class, forVariable(variable));
    }

    public QManufacturer(Path<? extends Manufacturer> path) {
        super(path.getType(), path.getMetadata());
    }

    public QManufacturer(PathMetadata metadata) {
        super(Manufacturer.class, metadata);
    }

}

