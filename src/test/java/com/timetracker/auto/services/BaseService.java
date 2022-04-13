package com.timetracker.auto.services;


import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.*;
import io.restassured.specification.*;

import static com.timetracker.auto.utils.PropertyUtil.getPropertyValueByKey;

public class BaseService {
    protected static final String BASE_URI = getPropertyValueByKey("baseUrl");

    protected static RequestSpecification getRequestSpec(String uri) {
        return new RequestSpecBuilder()
                .setBaseUri(uri)
                .setAccept(ContentType.JSON)
                .setContentType(ContentType.JSON)
                .build();
    }
}
