package com.perfectial.goal.pizzahub.dto;

import java.util.List;
import lombok.Data;

@Data
public class PageData<T> {
    private int currentPage;
    private int totalNumberOfPages;
    private List<T> data;
}
