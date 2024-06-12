package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.util.*;

/**
 * @author hahahaha123567@qq.com
 */
public class FormatterUtils {

	public static String json2yaml(String json) throws JsonProcessingException {
		return json2yaml(json, Collections.emptyList());
	}

	public static String json2yaml(String json, List<String> fieldsToFilter) throws JsonProcessingException {
		ObjectMapper jsonMapper = new ObjectMapper();
		JsonNode rootNode = jsonMapper.readTree(json);
		filterFields(rootNode, fieldsToFilter);

		ObjectMapper yamlMapper = new ObjectMapper(new YAMLFactory());
		return yamlMapper.writeValueAsString(rootNode);
	}

	private static void filterFields(JsonNode node, List<String> fieldsToFilter) {
		if (node.isObject()) {
			ObjectNode objectNode = (ObjectNode) node;
			for (String field : fieldsToFilter) {
				objectNode.remove(field);
			}
			Iterator<Map.Entry<String, JsonNode>> fields = objectNode.fields();
			while (fields.hasNext()) {
				Map.Entry<String, JsonNode> entry = fields.next();
				if (entry.getValue().isNull()) {
					fields.remove();
				}
				filterFields(entry.getValue(), fieldsToFilter);
			}
		} else if (node.isArray()) {
			for (JsonNode item : node) {
				filterFields(item, fieldsToFilter);
			}
		}
	}
}
