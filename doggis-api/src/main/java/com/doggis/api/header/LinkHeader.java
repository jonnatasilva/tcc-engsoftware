package com.doggis.api.header;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class LinkHeader {

	private final String url;
	private final Map<String, String[]> queryParams;
	private final String rel;

	private LinkHeader(Builder builder) {
		this.url = builder.url;
		this.queryParams = builder.queryParams;
		this.rel = builder.rel;
	}

	public String getLink() {
		StringBuffer link = new StringBuffer("<" + url);
		if (queryParams != null && queryParams.size() >= 1) {
			link.append("?");
			AtomicInteger index = new AtomicInteger();
			queryParams.forEach((k, v) -> {
				link.append(k)
				.append("=")
				.append(Arrays.stream(v).collect(Collectors.joining(",")));
				
				if(index.incrementAndGet() < queryParams.size()) {
					link.append("&");
				}
			});
		}
		return link.append("> rel=\"").append(rel).append("\"").toString();
	}

	public static class Builder {
		private final String url;
		private Map<String, String[]> queryParams = new HashMap<>();
		private String rel = "";

		public Builder(String url) {
			this.url = url;
		}

		public Builder withQueryParams(Map<String, String[]> queryParams) {
			this.queryParams.putAll(queryParams);
			return this;
		}
		
		public Builder addQueryParams(String paramName, String[] value) {
			this.queryParams.put(paramName, value);
			return this;
		}

		public Builder withRel(String rel) {
			this.rel = rel;
			return this;
		}

		public LinkHeader build() {
			return new LinkHeader(this);
		}
	}
}
