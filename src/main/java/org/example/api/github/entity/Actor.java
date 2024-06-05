package org.example.api.github.entity;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

/**
 * @author zhangyaoxin@yiwise.com
 */
@Data
class Actor {

	private Long id;

	private String login;

	@JsonAlias("display_login")
	private String displayLogin;

	@JsonAlias("gravatar_id")
	private String gravatarId;

	private String url;

	@JsonAlias("avatar_url")
	private String avatarUrl;
}
