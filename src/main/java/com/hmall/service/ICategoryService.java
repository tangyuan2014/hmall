package com.hmall.service;

import com.hmall.common.ServerResponse;

public interface ICategoryService {
    ServerResponse addCategory(Integer parentId, String categoryName);

    ServerResponse setCategoryName(Integer categoryId, String categoryName);

    ServerResponse getChildrenParallelCategory(Integer categoryId);

    ServerResponse getCategoryAndChildrenCategory( Integer categoryId);
}
