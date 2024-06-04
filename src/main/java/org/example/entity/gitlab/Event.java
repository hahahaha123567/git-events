package org.example.entity.gitlab;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author zhangyaoxin@yiwise.com
 */
@Data
public class Event {

	private Long id;

	@JsonAlias("project_id")
	private Long projectId;

	@JsonAlias("action_name")
	private String actionName;

	@JsonAlias("target_id")
	private Long targetId;

	@JsonAlias("target_iid")
	private Long targetIid;

	@JsonAlias("target_type")
	private String targetType;

	@JsonAlias("author_id")
	private Long authorId;

	@JsonAlias("target_title")
	private String targetTitle;

	@JsonAlias("created_at")
	private LocalDateTime createdAt;

	private Author author;

	@JsonAlias("push_data")
	private PushData pushData;

	@JsonAlias("author_username")
	private String authorUsername;
}
