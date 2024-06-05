package org.example;

import org.example.api.GitHubApi;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDate;

public class Main {

	private static final String username = "hahahaha123567";
	private static final LocalDate startDate = LocalDate.of(2024, 6, 3);
	private static final LocalDate endDate = LocalDate.of(2024, 6, 5);

	public static void main(String[] args) throws URISyntaxException, IOException {
		Api api = new GitHubApi();
		printPrompt(api.getEventsYaml(username, startDate, endDate));
	}

	private static void printPrompt(String yaml) {
		System.out.println("请根据我几天内的git活动日志生成这几天的周报，要求你做到以下几点");
		System.out.println("1. 要将同一个系统的改动进行汇总而不是机械地重复日志内容");
		System.out.println("2. 要将内容相似的改动进行汇总而不是机械地重复日志内容");
		System.out.println("3. 如果涉及的id找不到对应的实体的话不输出id的值");
		System.out.println("以下是yaml格式的日志内容");
		System.out.println(yaml);
	}
}