package org.example.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.apache.hc.client5.http.fluent.Request;
import org.apache.hc.core5.net.URIBuilder;
import org.example.Api;
import org.example.FormatterUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * <a href="https://docs.github.com/zh/rest/activity/events?apiVersion=2022-11-28#list-events-for-the-authenticated-user">事件的 REST API 终结点 - GitHub 文档</a>
 * @author hahahaha123567@qq.com
 */
public class GitHubApi implements Api {

	private static final String BASE_URL = "https://api.github.com/users";
	private static final String PERSONAL_ACCESS_TOKEN = "";
	private static final List<String> fieldsToFilter = Arrays.asList("owner", "sha", "user", "url", "avatar_url", "forkee", "head", "before", "reactions");

	private static final ObjectMapper jsonMapper = new ObjectMapper();
	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");

	@Override
	public String getEventsYaml(String username, LocalDate startDate, LocalDate endDate) throws URISyntaxException, IOException {
		LocalDateTime startDateTime = LocalDateTime.of(startDate, LocalTime.MIN);
		LocalDateTime endDateTime = LocalDateTime.of(endDate, LocalTime.MAX);
		List<String> events = new ArrayList<>();
		String tmpEvents;
		int page = 1;
		LocalDateTime lastEventTime;
		do {
			tmpEvents = events(username, page++);
			ArrayNode arrayNode = (ArrayNode) jsonMapper.readTree(tmpEvents);
			if (arrayNode.isEmpty()) {
				break;
			}
			lastEventTime = LocalDateTime.parse(arrayNode.get(arrayNode.size() - 1).get("created_at").asText(), formatter);
			Iterator<JsonNode> iterator = arrayNode.iterator();
			while (iterator.hasNext()) {
				LocalDateTime createdAt = LocalDateTime.parse(iterator.next().get("created_at").asText(), formatter);
				if (createdAt.isBefore(startDateTime) || createdAt.isAfter(endDateTime)) {
					iterator.remove();
				}
			}
			events.add(FormatterUtils.json2yaml(arrayNode.toString(), fieldsToFilter));
		} while (lastEventTime.isAfter(startDateTime));
		return String.join(System.lineSeparator(), events);
	}

	private String events(String username, int page) throws URISyntaxException, IOException {
		String api = "/events";
		URIBuilder uriBuilder = new URIBuilder(BASE_URL + "/" + username + api);
		uriBuilder.addParameter("page", String.valueOf(page));
		uriBuilder.addParameter("per_page", "100");
		return Request.get(uriBuilder.build())
				.addHeader("Accept", "application/vnd.github+json")
				.addHeader("Authorization", "Bearer " + PERSONAL_ACCESS_TOKEN)
				.addHeader("X-GitHub-Api-Version", "2022-11-28")
				.execute().returnContent().asString(StandardCharsets.UTF_8);
	}
}
