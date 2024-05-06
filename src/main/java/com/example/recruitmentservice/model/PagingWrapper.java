package com.example.recruitmentservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PagingWrapper<T> {
    private int page;
    private int pageSize;
    private long totalElements;
    private long totalPages;
    private List<T> data;

}
