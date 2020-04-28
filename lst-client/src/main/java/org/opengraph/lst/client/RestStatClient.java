package org.opengraph.lst.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.opengraph.lst.core.beans.Stat;
import org.opengraph.lst.core.client.StatClient;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.lang.NonNull;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class RestStatClient implements StatClient {

    private static final String PUT_URL = "stat";

    private String webServerUri;
    private RestTemplate restTemplate;

    public RestStatClient(@NonNull String webServerUri) {
        if (!webServerUri.endsWith("/")) {
            webServerUri = webServerUri + "/";
        }
        this.webServerUri = webServerUri + PUT_URL;
        restTemplate = new RestTemplate(new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()));
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        restTemplate.setMessageConverters(List.of(new MappingJackson2HttpMessageConverter(mapper)));
        restTemplate.setInterceptors(List.of(new LoggingRequestInterceptor()));
    }

    @Override
    public void sendStat(Stat stat) {
        restTemplate.put(webServerUri, stat);
    }
}
