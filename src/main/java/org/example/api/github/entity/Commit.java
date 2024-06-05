package org.example.api.github.entity;

import lombok.Data;

/**
 * @author zhangyaoxin@yiwise.com
 */
@Data
public class Commit {

	private String sha;

	private Author author;

	private String message;

	private Boolean distinct;

	private String url;
}
