package com.hmall.service;

import com.hmall.common.ServerResponse;
import com.hmall.pojo.Product;
import com.hmall.vo.ProductVo;

public interface IProductService {
    ServerResponse saveProduct(Product product);

    ServerResponse<String> setSaleStatus(Integer productId, Integer status);

    ServerResponse<ProductVo> getDetail(Integer productId);
}
