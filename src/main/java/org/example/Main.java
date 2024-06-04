package org.example;

import org.example.api.Api;
import org.example.api.gitlab.GitlabApi;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.List;

public class Main {

	public static void main(String[] args) throws URISyntaxException, IOException {
		Api api = new GitlabApi();
		List<String> descriptions = api.getEventDescriptions(LocalDate.of(2024, 5, 27), LocalDate.of(2024, 6, 2));
		printPrompt(descriptions);
	}

	private static void printPrompt(List<String> descriptions) {
		System.out.println("请根据我几天内的git活动日志生成这几天的周报，要求你做到以下几点");
		System.out.println("1. 要将同一个系统的改动汇总而不是机械地重复日志内容");
		System.out.println("2. 省略具体的时间");
		System.out.println("以下是日志内容");
		descriptions.forEach(System.out::println);
	}
}