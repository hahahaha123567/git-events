package org.example.api.gitlab.entity;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

/**
 * @author zhangyaoxin@yiwise.com
 */
@Data
class PushData {

	@JsonAlias("commit_count")
	private Integer commitCount;

	private String action;

	@JsonAlias("ref_type")
	private String refType;

	@JsonAlias("commit_from")
	private String commitFrom;

	@JsonAlias("commit_to")
	private String commitTo;

	private String ref;

	@JsonAlias("commit_title")
	private String commitTitle;

	@JsonAlias("ref_count")
	private String refCount;
}
