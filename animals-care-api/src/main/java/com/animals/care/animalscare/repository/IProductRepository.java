package com.animals.care.animalscare.repository;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.animals.care.animalscare.domain.Product;
import com.animals.care.animalscare.domain.QProduct;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;

public interface IProductRepository extends PagingAndSortingRepository<Product, Long>, QuerydslPredicateExecutor<Product>
, QuerydslBinderCustomizer<QProduct> {
	
	@Override
    default public void customize(
      QuerydslBindings bindings, QProduct root) {
        bindings.bind(String.class)
          .first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
      }
}
