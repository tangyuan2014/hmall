package com.hmall.service;

import com.github.pagehelper.PageInfo;
import com.hmall.common.ServerResponse;
import com.hmall.pojo.Product;
import com.hmall.vo.ProductVo;

public interface IProductService {
    ServerResponse saveProduct(Product product);

    ServerResponse<String> setSaleStatus(Integer productId, Integer status);

    ServerResponse<ProductVo> getDetail(Integer productId);

    ServerResponse<PageInfo> getList(Integer pageNum, Integer pageSize);

    ServerResponse<PageInfo> searchList(String productName, Integer productId, Integer pageNum, Integer pageSize);

    ServerResponse<ProductVo> userGetDetail(Integer productId);
}
