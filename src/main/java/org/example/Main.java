package org.example;

import org.example.api.GitLabApi;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDate;

public class Main {

	private static final String username = "hahahaha123567";
	private static final LocalDate startDate = LocalDate.of(2024, 6, 3);
	private static final LocalDate endDate = LocalDate.of(2024, 6, 7);

	public static void main(String[] args) throws URISyntaxException, IOException {
		Api api = new GitLabApi();
		printPrompt(api.getEventsYaml(username, startDate, endDate));
	}

	private static void printPrompt(String yaml) {
		System.out.println("你是一名资深产品经理，请根据以下Git活动记录，生成一个简要的工作总结，涵盖主要的工作内容、完成的任务、遇到的问题及解决方法，");
		System.out.println("并且根据该工作总结，生成一份工作周报。周报应包括以下章节：工作概述、详细进展、遇到的问题及解决方案、下周计划");
		System.out.println("以下是yaml格式的日志内容");
		System.out.println(yaml);
	}
}