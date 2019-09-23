package com.animals.care.animalscare.service.impl;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.animals.care.animalscare.header.LinkHeader;
import com.animals.care.animalscare.service.IBuildHeaderService;

@Service
public class BuildHeaderServiceImpl implements IBuildHeaderService {
	
	private static final String NEXT = "next";
	private static final String LAST = "last";
	private static final String PAGE = "page";
	private static final String SIZE = "size";

	@Override
	public String pageLinkHeader(HttpServletRequest request, Page<?> page) {
		if (page.hasNext()) {
			LinkHeader next = createNextPageLink(request, page);
			
			LinkHeader last = createLastPageLink(request, page);
			
			return next.getLink() + " " + last.getLink();
		} else if (!page.isEmpty()) {
			LinkHeader last = createLastPageLink(request, page);

			return last.getLink();
		}
		return "";
	}

	private LinkHeader createNextPageLink(HttpServletRequest request, Page<?> page) {
		LinkHeader next = new LinkHeader.Builder(request.getRequestURL().toString())
				.withRel(NEXT)
				.withQueryParams(request.getParameterMap())
				.addQueryParams(PAGE, new String[] { String.valueOf(page.nextPageable().getPageNumber()) })
				.addQueryParams(SIZE, new String[] { String.valueOf(page.nextPageable().getPageSize())})
				.build();
		return next;
	}

	private LinkHeader createLastPageLink(HttpServletRequest request, Page<?> page) {
		LinkHeader last = new LinkHeader.Builder(request.getRequestURL().toString())
				.withRel(LAST)
				.withQueryParams(request.getParameterMap())
				.addQueryParams(PAGE, new String[] { String.valueOf(page.getTotalPages() - 1) })
				.addQueryParams(SIZE, new String[] { String.valueOf(page.getNumberOfElements())})
				.build();
		return last;
	}

}
