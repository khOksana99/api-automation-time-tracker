package com.timetracker.auto.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Task {
    private int id;
    private String title;
    private int completed; // 1 = true, 0 = false
    private Date date;
    private Priority priority;
    private Category category;
}
