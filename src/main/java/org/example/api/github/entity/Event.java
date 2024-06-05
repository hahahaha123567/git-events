package org.example.api.github.entity;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author zhangyaoxin@yiwise.com
 */
@Data
public class Event {

	private String id;

	private String type;

	private Actor actor;

	private Repo repo;

	private Payload payload;

	@JsonAlias("public")
	private Boolean publicField;

	@JsonAlias("created_at")
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
	private LocalDateTime createdAt;

	public String getDescription() {
		return null;
	}
}
