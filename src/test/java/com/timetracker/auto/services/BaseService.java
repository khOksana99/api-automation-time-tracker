package com.timetracker.auto.services;


import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.*;
import io.restassured.specification.*;
import lombok.Getter;

import static com.timetracker.auto.utils.PropertyUtil.getPropertyValueByKey;

public class BaseService {
    @Getter private String BASE_URI = getPropertyValueByKey("baseUrl");

    private static RequestSpecification getRequestSpec(String uri) {
        return new RequestSpecBuilder()
                .setBaseUri(uri)
                .setAccept(ContentType.JSON)
                .setContentType(ContentType.JSON)
                .build();
    }
}
