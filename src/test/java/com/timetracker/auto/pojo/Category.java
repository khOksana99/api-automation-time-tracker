package com.timetracker.auto.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Category {
    private int id;
    private String title;
    private int completedCount;
    private int uncompletedCount;
}
