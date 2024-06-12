package org.example.api;

import org.apache.hc.client5.http.fluent.Request;
import org.apache.hc.core5.net.URIBuilder;
import org.example.Api;
import org.example.FormatterUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

/**
 * <a href="https://docs.gitlab.com/ee/api/events.html">Events API | GitLab</a>
 * @author hahahaha123567@qq.com
 */
public class GitLabApi implements Api {

	private static final String BASE_URL = "http://gitlab.yiwise.local/api/v4";
	private static final String PRIVATE_TOKEN = "";
	private static final List<String> fieldsToFilter = Arrays.asList("id", "author", "author_id",
			"commit_from", "commit_to", "commit_count", "state", "target_id", "target_iid");

	@Override
	public String getEventsYaml(String username, LocalDate startDate, LocalDate endDate) throws URISyntaxException, IOException {
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

		return FormatterUtils.json2yaml(responseString, fieldsToFilter);
	}
}
