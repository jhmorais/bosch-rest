package io.reflectoring.springwebflux.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@ToString
@EqualsAndHashCode(of = {"id","event","targetUrl", "filter"})
@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(value = "subscription")
public class Subscription {

    @Id
    private String id;
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
