package org.example.api.gitlab.entity;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

/**
 * @author zhangyaoxin@yiwise.com
 */
@Data
class Author {

	private Long id;

	private String name;

	private String username;

	private String state;

	@JsonAlias("avatar_url")
	private String avatarUrl;

	@JsonAlias("web_url")
	private String webUrl;
}
