package org.example.api.github;

import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.hc.client5.http.fluent.Request;
import org.apache.hc.core5.net.URIBuilder;
import org.example.api.Api;
import org.example.api.github.entity.Event;
import org.example.helper.JsonHelper;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.time.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <a href="https://docs.github.com/zh/rest/activity/events?apiVersion=2022-11-28#list-events-for-the-authenticated-user">事件的 REST API 终结点 - GitHub 文档</a>
 * @author zhangyaoxin@yiwise.com
 */
public class GitHubApi implements Api {

	private static final String BASE_URL = "https://api.github.com/users";
	private static final String PERSONAL_ACCESS_TOKEN = "";

	@Override
	public List<String> getEventDescriptions(String username, LocalDate startDate, LocalDate endDate) throws URISyntaxException, IOException {
		List<Event> events = events(username, startDate, endDate);
		return events.stream().map(Event::getDescription).filter(Objects::nonNull).collect(Collectors.toList());
	}

	private List<Event> events(String username, LocalDate startDate, LocalDate endDate) throws URISyntaxException, IOException {
		LocalDateTime startDateTime = LocalDateTime.of(startDate, LocalTime.MIN);
		LocalDateTime endDateTime = LocalDateTime.of(endDate, LocalTime.MAX);
		List<Event> events = new ArrayList<>();
		List<Event> tmpEvents;
		int page = 1;
		do {
			tmpEvents = events(username, page);
			events.addAll(tmpEvents);
		} while (tmpEvents.get(tmpEvents.size() - 1).getCreatedAt().isAfter(startDateTime));
		return events.stream().filter(event -> event.getCreatedAt().isAfter(startDateTime) && event.getCreatedAt().isBefore(endDateTime)).collect(Collectors.toList());
	}

	private List<Event> events(String username, int page) throws URISyntaxException, IOException {
		String api = "/events";
		URIBuilder uriBuilder = new URIBuilder(BASE_URL + "/" + username + api);
		uriBuilder.addParameter("page", String.valueOf(page));
		uriBuilder.addParameter("per_page", "100");
		String responseString = Request.get(uriBuilder.build())
				.addHeader("Accept", "application/vnd.github+json")
				.addHeader("Authorization", "Bearer " + PERSONAL_ACCESS_TOKEN)
				.addHeader("X-GitHub-Api-Version", "2022-11-28")
				.execute().returnContent().asString(StandardCharsets.UTF_8);
		return JsonHelper.string2Object(responseString, new TypeReference<List<Event>>() {});
	}
}
