package com.doggis.api.utils;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class BuildHeaderUtils {

	private static final String NEXT = "next";
	private static final String LAST = "last";
	
	private static String link = "<%s?page=%s&size=%s> rel=%s;";

	private BuildHeaderUtils() {
	}
	
	public static String createPageNextLink(Page<?> page, HttpServletRequest request) {
		Pageable nextPageable = page.getPageable();
		if (page.hasNext()) {
			nextPageable = page.nextPageable();
		}
		return String.format(link
				, request.getRequestURL().toString()
				, nextPageable.getPageNumber()
				, nextPageable.getPageSize()
				, NEXT);
	}
	
	public static String createPageLastLink(Page<?> page, HttpServletRequest request) {
		return String.format(link
				, request.getRequestURL().toString()
				, page.getTotalPages() - 1
				, page.getSize()
				, LAST);
		
	}
}
