package com.fridge.fridgebackend.global.response;

import java.util.List;

public record PageResponse<T>(
    List<T> data,
    Object nextCursor,
    boolean hasNext,
    long totalCount,
    String sortBy,
    String sortDirection
) {

}
