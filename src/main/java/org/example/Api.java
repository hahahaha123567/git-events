package org.example;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDate;

/**
 * @author zhangyaoxin@yiwise.com
 */
public interface Api {

	String getEventsYaml(String username, LocalDate startDate, LocalDate endDate) throws URISyntaxException, IOException;
}
