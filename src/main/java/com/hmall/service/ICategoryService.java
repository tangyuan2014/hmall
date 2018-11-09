package com.hmall.service;

import com.hmall.common.ServerResponse;

import java.util.List;

public interface ICategoryService {
    ServerResponse addCategory(Integer parentId, String categoryName);

    ServerResponse setCategoryName(Integer categoryId, String categoryName);

    ServerResponse getChildrenParallelCategory(Integer categoryId);

    ServerResponse<List<Integer>> getCategoryAndChildrenCategory(Integer categoryId);
}
