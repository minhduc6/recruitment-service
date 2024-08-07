package vn.unigap.api.service.logging;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.*;
import java.util.concurrent.CompletableFuture;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import vn.unigap.api.common.Common;
import vn.unigap.api.common.Constants;
import vn.unigap.api.entity.mongo.RequestResponse;
import vn.unigap.api.repository.RequestResponseRepository;

@AllArgsConstructor
@Service
@Log4j2
public class LoggingServiceImpl implements LoggingService{
    private final ObjectMapper objectMapper;
    private final RequestResponseRepository requestResponseRepository;


    @Override
    public void logRequest(HttpServletRequest httpServletRequest, Object body) {
        RequestResponse reqres;
        if (httpServletRequest.getAttribute(Constants.REQUEST_RESPONSE_ATTRIBUTE) == null) {
            String uuidRequest = Common.uuid();

            reqres = RequestResponse.builder().uuidRequest(uuidRequest).requestAt(Common.currentDateTime()).build();

            httpServletRequest.setAttribute(Constants.REQUEST_RESPONSE_ATTRIBUTE, reqres);
        } else {
            reqres = (RequestResponse) httpServletRequest.getAttribute(Constants.REQUEST_RESPONSE_ATTRIBUTE);
        }

        reqres.setMethod(httpServletRequest.getMethod());
        reqres.setPath(httpServletRequest.getRequestURI());
        reqres.setParameters(buildParametersMap(httpServletRequest));
        reqres.setRequestHeaders(buildHeadersMap(httpServletRequest));

        if (body != null) {
            try {
                reqres.setRequestBody(
                        objectMapper.readValue(objectMapper.writeValueAsString(body), new TypeReference<>() {
                        }));
            } catch (JsonProcessingException e) {
                log.warn("could not parse request body: " + body);
            }
        }

        log.info("REQUEST: " + reqres);
    }

    @Override
    public void logResponse(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                            Object body) {
        if (httpServletRequest.getAttribute(Constants.REQUEST_RESPONSE_ATTRIBUTE) == null) {
            // case when request not passed to controller because unauthenticated. So we
            // must recreate
            // request
            logRequest(httpServletRequest, null);
        }

        RequestResponse reqres = (RequestResponse) httpServletRequest
                .getAttribute(Constants.REQUEST_RESPONSE_ATTRIBUTE);
        reqres.setResponseAt(Common.currentDateTime());
        reqres.setStatusCode(httpServletResponse.getStatus());
        reqres.setResponseHeaders(buildHeadersMap(httpServletResponse));
        try {
            reqres.setResponseBody(objectMapper.readValue(objectMapper.writeValueAsString(body),
                    new TypeReference<Map<String, Object>>() {
                    }));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        log.info("RESPONSE: " + reqres);

        // run async for inserting into mongodb
        CompletableFuture.runAsync(() -> {
            try {
                requestResponseRepository.save(reqres);
            } catch (Exception e) {
                log.error("ERROR: could not save reqres into mongodb: ", e);
            }
        });
    }

    private Map<String, String> buildParametersMap(HttpServletRequest httpServletRequest) {
        Map<String, String> resultMap = new HashMap<>();
        Enumeration<String> parameterNames = httpServletRequest.getParameterNames();

        while (parameterNames.hasMoreElements()) {
            String key = parameterNames.nextElement();
            String value = httpServletRequest.getParameter(key);
            resultMap.put(key, value);
        }

        return resultMap;
    }

    private Map<String, List<String>> buildHeadersMap(HttpServletRequest request) {
        Map<String, List<String>> map = new HashMap<>();

        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = headerNames.nextElement();
            List<String> values = map.getOrDefault(key, new ArrayList<>());
            String value = request.getHeader(key);
            values.add(value);
            map.put(key, values);
        }

        return map;
    }

    private Map<String, List<String>> buildHeadersMap(HttpServletResponse response) {
        Map<String, List<String>> map = new HashMap<>();

        Collection<String> headerNames = response.getHeaderNames();
        for (String header : headerNames) {
            List<String> values = map.getOrDefault(header, new ArrayList<>());
            values.add(response.getHeader(header));
            map.put(header, values);
        }

        return map;
    }
}
