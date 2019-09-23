package com.animals.care.animalscare.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;

public interface IBuildHeaderService {
	
	String pageLinkHeader(HttpServletRequest request, Page<?> page);
}
