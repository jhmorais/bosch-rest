package com.example.demo.model;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
public @Data class Subscription {

	@Id
	@NonNull
	private Integer id;

	@NonNull
	private Integer userId;
	
	@ToString.Include
	@NonNull
	private String event;
	
	@ToString.Include
	@NonNull
	private String targetUrl;
	
	@ToString.Include
	@NonNull
	private String filter;

	private boolean status;
}
