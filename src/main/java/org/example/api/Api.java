package org.example.api;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.List;

/**
 * @author zhangyaoxin@yiwise.com
 */
public interface Api {

	List<String> getEventDescriptions(String username, LocalDate startDate, LocalDate endDate) throws URISyntaxException, IOException;
}
