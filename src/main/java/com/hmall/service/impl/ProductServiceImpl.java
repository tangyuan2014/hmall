package com.hmall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.hmall.Utils.DateTimeUtil;
import com.hmall.Utils.PropsUtil;
import com.hmall.common.Const;
import com.hmall.common.ResponseCode;
import com.hmall.common.ServerResponse;
import com.hmall.dao.CategoryMapper;
import com.hmall.dao.ProductMapper;
import com.hmall.pojo.Category;
import com.hmall.pojo.Product;
import com.hmall.service.ICategoryService;
import com.hmall.service.IProductService;
import com.hmall.vo.ProductListVo;
import com.hmall.vo.ProductVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("iProductService")
public class ProductServiceImpl implements IProductService {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private ICategoryService iCategoryService;

    public ServerResponse saveProduct(Product product) {
        if (product != null) {
            if (StringUtils.isNoneBlank(product.getSubImages())) {
                String[] subImageArray = product.getSubImages().split(",");
                product.setMainImage(subImageArray[0]);
            }

            if (product.getId() != null) {
                int rowCount = productMapper.updateByPrimaryKey(product);
                if (rowCount > 0) {
                    return ServerResponse.createBySuccessMessage("UPDATE PRODUCT SUCCESS");
                }
                return ServerResponse.createByErrorMessage("UPDATE PRODUCT FAIL");
            } else {
                int rowConut = productMapper.insert(product);
                if (rowConut > 0) {
                    return ServerResponse.createBySuccessMessage("SAVE PRODUCT SUCCESS");
                }
                return ServerResponse.createByErrorMessage("SAVE PRODUCT FAIL");
            }
        }
        return ServerResponse.createByErrorMessage("PRODUCT DATA IS NOR CORRECT");
    }

    public ServerResponse<String> setSaleStatus(Integer productId, Integer status) {
        if (productId == null || status == null) {
            return ServerResponse.createByErrorMessage("Error data, please enter correct data");
        }
        Product product = new Product();
        product.setId(productId);
        product.setStatus(status);

        int rowCount = productMapper.updateByPrimaryKeySelective(product);
        if (rowCount > 0) {
            return ServerResponse.createBySuccessMessage("SALE STATUS UPDATE SUCCESS");
        }
        return ServerResponse.createByErrorMessage("SALE STATUS UPDATE FAIL");
    }

    public ServerResponse<ProductVo> getDetail(Integer productId) {
        if (productId == null) {
            return ServerResponse.createByErrorMessageAndCode(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        Product product = productMapper.selectByPrimaryKey(productId);
        if (product == null) {
            return ServerResponse.createByErrorMessage("NO SUCH PRODUCT FOUND");
        }
        //VALUE OBJECT
        return ServerResponse.createBySuccess("DETAILS SHOWING", assembleProductDetailVo(product));


    }

    private ProductVo assembleProductDetailVo(Product product) {
        ProductVo productVo = new ProductVo();
        productVo.setId(product.getId());
        productVo.setCategoryId(product.getCategoryId());
        productVo.setMainImage(product.getMainImage());
        productVo.setStatus(product.getStatus());
        productVo.setDetail(product.getDetail());
        productVo.setName(product.getName());
        productVo.setPrice(product.getPrice());
        productVo.setStock(product.getStock());
        productVo.setSubImages(product.getSubImages());
        productVo.setSubtitle(product.getSubtitle());

        productVo.setImageHost(PropsUtil.getProerty("ftp.server.http.prefix", "http://img.happymmall.com/"));

        Category category = categoryMapper.selectByPrimaryKey(product.getCategoryId());
        if (category == null) {
            productVo.setCategoryParentId(0);
        } else {
            productVo.setCategoryParentId(category.getParentId());
        }

        productVo.setCreateTime(DateTimeUtil.dateTimeToStr(product.getCreateTime()));
        productVo.setUpdateTime(DateTimeUtil.dateTimeToStr(product.getUpdateTime()));

        return productVo;
    }

    public ServerResponse<PageInfo> getList(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Product> productList = productMapper.selectList();
        List<ProductListVo> productListVoList = Lists.newArrayList();
        for (Product productItem : productList) {
            ProductListVo productListVo = assembleProductListVo(productItem);
            productListVoList.add(productListVo);
        }
        PageInfo pageInfo = new PageInfo(productList);
        pageInfo.setList(productListVoList);
        return ServerResponse.createBySuccess(pageInfo);

    }

    private ProductListVo assembleProductListVo(Product product) {
        ProductListVo productListVo = new ProductListVo();
        productListVo.setId(product.getId());
        productListVo.setCategoryId(product.getCategoryId());
        productListVo.setMainImage(product.getMainImage());
        productListVo.setStatus(product.getStatus());
        productListVo.setName(product.getName());
        productListVo.setPrice(product.getPrice());
        productListVo.setSubtitle(product.getSubtitle());
        productListVo.setImageHost(PropsUtil.getProerty("ftp.server.http.prefix", "http://img.happymmall.com/"));
        return productListVo;
    }

    public ServerResponse<PageInfo> searchList(String productName, Integer productId, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        if (StringUtils.isNoneBlank(productName)) {
            productName = new StringBuilder().append("%").append(productName).append("%").toString();
        }
        List<Product> productList = productMapper.selectByNameAndProductId(productName, productId);
        List<ProductListVo> productListVoList = Lists.newArrayList();
        for (Product productItem : productList) {
            ProductListVo productListVo = assembleProductListVo(productItem);
            productListVoList.add(productListVo);
        }
        PageInfo pageInfo = new PageInfo(productList);
        pageInfo.setList(productListVoList);
        return ServerResponse.createBySuccess(pageInfo);
    }

    public ServerResponse<ProductVo> userGetDetail(Integer productId) {
        if (productId == null) {
            return ServerResponse.createByErrorMessageAndCode(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        Product product = productMapper.selectByPrimaryKey(productId);
        if (product == null) {
            return ServerResponse.createByErrorMessage("NO SUCH PRODUCT FOUND");
        }
        if (product.getStatus() != Const.ProductStatusEnum.ON_SALE.getStatusCode()) {
            return ServerResponse.createByErrorMessage("product has been deleted ");
        }
        return ServerResponse.createBySuccess("DETAILS SHOWING", assembleProductDetailVo(product));
    }

    public ServerResponse<PageInfo> userGetList(String keyword, Integer categoryId, int pageNum, int pageSize, String orderBy) {
        if (StringUtils.isBlank(keyword) && categoryId == null) {
            return ServerResponse.createByErrorMessageAndCode(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        List<Integer> categoryList = new ArrayList<Integer>();
        if (categoryId != null) {
            Category category = categoryMapper.selectByPrimaryKey(categoryId);
            if (category == null && StringUtils.isBlank(keyword)) {
                PageHelper.startPage(pageNum, pageSize);
                List<ProductListVo> productListVos = new ArrayList<>();
                PageInfo pageInfo = new PageInfo(productListVos);
                return ServerResponse.createBySuccess(pageInfo);
            }
            categoryList = iCategoryService.getCategoryAndChildrenCategory(categoryId).getData();
        }
        if (StringUtils.isNoneBlank(keyword)) {
            keyword = new StringBuilder().append("%").append(keyword).append("%").toString();
        }
        PageHelper.startPage(pageNum, pageSize);
        if (StringUtils.isNoneBlank(orderBy)) {
            if (Const.OrderBy.price_asc_desc.contains(orderBy)) {
                String[] split = orderBy.split("_");
                PageHelper.orderBy(split[0] + " " + split[1]);
            }
        }

        List<Product> productList = productMapper.selectByNameAndCategoryId(StringUtils.isBlank(keyword) ? null : keyword, categoryList.size() == 0 ? null:categoryList);
        List<ProductListVo> productListVoList = Lists.newArrayList();
        for (Product productItem : productList
                ) {
            ProductListVo productListVo = assembleProductListVo(productItem);
            productListVoList.add(productListVo);
        }
        PageInfo pageInfo = new PageInfo(productList);
        pageInfo.setList(productListVoList);
        return ServerResponse.createBySuccess(pageInfo);
    }
}
