package org.example.api.gitlab;

import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.hc.client5.http.fluent.Request;
import org.apache.hc.core5.net.URIBuilder;
import org.example.api.Api;
import org.example.api.gitlab.entity.Event;
import org.example.helper.JsonHelper;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <a href="https://docs.gitlab.com/ee/api/events.html">Events API | GitLab</a>
 * @author zhangyaoxin@yiwise.com
 */
public class GitLabApi implements Api {

	private static final String BASE_URL = "http://gitlab.yiwise.local/api/v4";
	private static final String PRIVATE_TOKEN = "";

	@Override
	public List<String> getEventDescriptions(String username, LocalDate startDate, LocalDate endDate) throws URISyntaxException, IOException {
		List<Event> events = events(startDate, endDate);
		return events.stream().map(Event::getDescription).filter(Objects::nonNull).collect(Collectors.toList());
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
}
