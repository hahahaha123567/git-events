package org.example.api.github.entity;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

import java.util.List;

/**
 * @author zhangyaoxin@yiwise.com
 */
@Data
class Payload {

	@JsonAlias("repository_id")
	private Long repositoryId;

	@JsonAlias("push_id")
	private Long pushId;

	private Integer size;

	@JsonAlias("distinct_size")
	private Integer distinctSize;

	private String ref;

	@JsonAlias("ref_type")
	private String refType;

	@JsonAlias("master_branch")
	private String masterBranch;

	private String description;

	@JsonAlias("pusher_type")
	private String pusherType;

	private String head;

	private String before;

	private List<Commit> commits;
}
