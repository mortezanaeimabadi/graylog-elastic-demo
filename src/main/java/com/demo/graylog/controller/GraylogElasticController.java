package com.demo.graylog.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@RestController
public class GraylogElasticController {

    @Autowired
    RestTemplate restTemplate;

    private final String elasticQueryBaseUrl="http://172.17.0.3:9200/";

    @GetMapping("/indices")
    public ResponseEntity queryElasticIndices(){
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(elasticQueryBaseUrl + "_cat/indices", String.class);
        return responseEntity;
    }

    @GetMapping("/aliases")
    public ResponseEntity queryElasticAliases(){
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(elasticQueryBaseUrl + "_cat/aliases", String.class);
        return responseEntity;
    }

    @GetMapping("/search")
    public ResponseEntity queryElasticSearch(){

        String url = elasticQueryBaseUrl+"graylog_0/_search";
        String requestBody = "{\n" +
                "  \"from\": 0,\n" +
                "  \"size\": 150,\n" +
                "  \"query\": {\n" +
                "    \"bool\": {\n" +
                "      \"must\": [\n" +
                "        {\n" +
                "          \"query_string\": {\n" +
                "            \"query\": \"message:\\\"02f9a584-76b7-4ec3-b372-23a207f7bd66\\\"\",\n" +
                "            \"fields\": [],\n" +
                "            \"use_dis_max\": true,\n" +
                "            \"tie_breaker\": 0,\n" +
                "            \"default_operator\": \"or\",\n" +
                "            \"auto_generate_phrase_queries\": false,\n" +
                "            \"max_determinized_states\": 10000,\n" +
                "            \"allow_leading_wildcard\": false,\n" +
                "            \"enable_position_increments\": true,\n" +
                "            \"fuzziness\": \"AUTO\",\n" +
                "            \"fuzzy_prefix_length\": 0,\n" +
                "            \"fuzzy_max_expansions\": 50,\n" +
                "            \"phrase_slop\": 0,\n" +
                "            \"escape\": false,\n" +
                "            \"split_on_whitespace\": true,\n" +
                "            \"boost\": 1\n" +
                "          }\n" +
                "        }\n" +
                "      ],\n" +
                "      \"filter\": [\n" +
                "        {\n" +
                "          \"bool\": {\n" +
                "            \"must\": [\n" +
                "              {\n" +
                "                \"range\": {\n" +
                "                  \"timestamp\": {\n" +
                "                    \"from\": \"1970-01-01 00:00:00.000\",\n" +
                "                    \"to\": \"2021-01-30 18:04:16.136\",\n" +
                "                    \"include_lower\": true,\n" +
                "                    \"include_upper\": true,\n" +
                "                    \"boost\": 1\n" +
                "                  }\n" +
                "                }\n" +
                "              }\n" +
                "            ],\n" +
                "            \"disable_coord\": false,\n" +
                "            \"adjust_pure_negative\": true,\n" +
                "            \"boost\": 1\n" +
                "          }\n" +
                "        }\n" +
                "      ],\n" +
                "      \"disable_coord\": false,\n" +
                "      \"adjust_pure_negative\": true,\n" +
                "      \"boost\": 1\n" +
                "    }\n" +
                "  },\n" +
                "  \"sort\": [\n" +
                "    {\n" +
                "      \"timestamp\": {\n" +
                "        \"order\": \"desc\"\n" +
                "      }\n" +
                "    }\n" +
                "  ]\n" +
                "}";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        //headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

        HttpEntity requestParams = new HttpEntity(requestBody,headers);

        ResponseEntity<String> responseEntity =
                restTemplate.postForEntity(url,requestParams,String.class);
        return responseEntity;
    }
}
