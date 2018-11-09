package com.hmall.controller.portal;

import com.github.pagehelper.PageInfo;
import com.hmall.common.ServerResponse;
import com.hmall.service.IProductService;
import com.hmall.vo.ProductVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("product/")
public class ProductController {

    @Autowired
    private IProductService iProductService;

    @RequestMapping("get_detail")
    @ResponseBody
    public ServerResponse<ProductVo> getDetail(Integer productId) {
        return iProductService.userGetDetail(productId);
    }

    public ServerResponse<PageInfo> productList(@RequestParam(value = "keywords", required = false) String keywords,
                                                @RequestParam(value = "categoryId", required = false) Integer categoryId,
                                                @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                                @RequestParam(value = "pageSize", defaultValue = "10") int PageSize,
                                                @RequestParam(value = "orderBy", defaultValue = "") String orderBy) {

    }

}
