package org.example.api.gitlab;

import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.hc.client5.http.fluent.Request;
import org.apache.hc.core5.net.URIBuilder;
import org.example.api.Api;
import org.example.entity.gitlab.Event;
import org.example.helper.JsonHelper;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <a href="https://docs.gitlab.com/ee/api/events.html">Events API | GitLab</a>
 * @author zhangyaoxin@yiwise.com
 */
public class GitlabApi implements Api {

	private static final String BASE_URL = "http://gitlab.yiwise.local/api/v4";
	private static final String PRIVATE_TOKEN = "";
	
	private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

	@Override
	public List<String> getEventDescriptions(LocalDate startDate, LocalDate endDate) throws URISyntaxException, IOException {
		List<Event> events = events(startDate, endDate);
		return events.stream().map(this::getDescription).filter(Objects::nonNull).collect(Collectors.toList());
	}

	private List<Event> events(LocalDate startDate, LocalDate endDate) throws URISyntaxException, IOException {
		String api = "/events";
		URIBuilder uriBuilder = new URIBuilder(BASE_URL + api);
		uriBuilder.addParameter("action", null);
		uriBuilder.addParameter("target_type", null);
		uriBuilder.addParameter("sort", "asc");
		uriBuilder.addParameter("page", "1");
		uriBuilder.addParameter("per_page", "200");
		if (startDate != null) {
			uriBuilder.addParameter("after", startDate.toString());
		}
		if (endDate != null) {
			uriBuilder.addParameter("before", endDate.toString());
		}
		String responseString = Request.get(uriBuilder.build())
				.addHeader("PRIVATE-TOKEN", PRIVATE_TOKEN)
				.execute().returnContent().asString(StandardCharsets.UTF_8);
		return JsonHelper.string2Object(responseString, new TypeReference<List<Event>>() {});
	}

	private String getDescription(Event event) {
		if ("MergeRequest".equals(event.getTargetType())) {
			return String.format("%s, %s %s a merge request, whose commit message is %s",
					dateTimeFormatter.format(event.getCreatedAt()), event.getAuthorUsername(), event.getActionName(), event.getTargetTitle());
		} else if ("pushed to".equals(event.getActionName())) {
			return String.format("%s, %s %s a branch to %s, whose commit message is %s",
					dateTimeFormatter.format(event.getCreatedAt()), event.getAuthorUsername(),
					event.getPushData().getAction(), event.getPushData().getRef(), event.getPushData().getCommitTitle());
		} else if ("pushed new".equals(event.getActionName())) {
			return String.format("%s, %s %s a branch to %s, whose commit message is %s",
					dateTimeFormatter.format(event.getCreatedAt()), event.getAuthorUsername(),
					event.getPushData().getAction(), event.getPushData().getRef(), event.getPushData().getCommitTitle());
		} else if ("deleted".equals(event.getActionName())) {
			return String.format("%s, %s %s a branch %s",
					dateTimeFormatter.format(event.getCreatedAt()), event.getAuthorUsername(), event.getPushData().getAction(), event.getPushData().getRef());
		} else if ("joined".equals(event.getActionName())) {
			return null;
		}
		System.out.println("event not found " + event);
		return null;
	}
}
