package org.example.api.gitlab.entity;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

	private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

	public String getDescription() {
		if ("MergeRequest".equals(getTargetType())) {
			return String.format("%s, %s %s a merge request, whose commit message is %s",
					dateTimeFormatter.format(getCreatedAt()), getAuthorUsername(), getActionName(), getTargetTitle());
		} else if ("pushed to".equals(getActionName())) {
			return String.format("%s, %s %s a branch to %s, whose commit message is %s",
					dateTimeFormatter.format(getCreatedAt()), getAuthorUsername(),
					getPushData().getAction(), getPushData().getRef(), getPushData().getCommitTitle());
		} else if ("pushed new".equals(getActionName())) {
			return String.format("%s, %s %s a branch to %s, whose commit message is %s",
					dateTimeFormatter.format(getCreatedAt()), getAuthorUsername(),
					getPushData().getAction(), getPushData().getRef(), getPushData().getCommitTitle());
		} else if ("deleted".equals(getActionName())) {
			return String.format("%s, %s %s a branch %s",
					dateTimeFormatter.format(getCreatedAt()), getAuthorUsername(), getPushData().getAction(), getPushData().getRef());
		} else if ("joined".equals(getActionName())) {
			return null;
		}
		System.out.println("event not found " + this);
		return null;
	}
}
