package com.hmall.service.impl;

import com.hmall.common.ServerResponse;
import com.hmall.dao.CategoryMapper;
import com.hmall.pojo.Category;
import com.hmall.service.ICategoryService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpSession;
import java.util.Collection;
import java.util.List;

@Service("iCategoryService")
public class CategoryServiceImpl implements ICategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    private Logger logger=LoggerFactory.getLogger(CategoryServiceImpl.class);

    public ServerResponse addCategory(Integer parentId, String categoryName) {
        if (parentId == null || StringUtils.isBlank(categoryName)) {
            return ServerResponse.createByErrorMessage("增加品类参数错误");
        }

        Category category = new Category();
        category.setName(categoryName);
        category.setParentId(parentId);
        category.setStatus(true);

        int rowCount = categoryMapper.insert(category);
        if (rowCount > 0) {
            return ServerResponse.createBySuccessMessage("添加成功");
        }
        return ServerResponse.createByErrorMessage("添加失败");
    }

    public ServerResponse setCategoryName(Integer categoryId, String categoryName) {
        if (categoryId == null || StringUtils.isBlank(categoryName)) {
            return ServerResponse.createByErrorMessage("更新参数错误");
        }

        Category category=new Category();
        category.setId(categoryId);
        category.setName(categoryName);

        int rowCount=categoryMapper.updateByPrimaryKeySelective(category);

        if (rowCount > 0) {
            return ServerResponse.createBySuccessMessage("更新名字成功");
        }
        return ServerResponse.createByErrorMessage("更新名字失败");
    }

    public ServerResponse getchildrenParallelCategory(Integer categoryId){
        if (categoryId==null){
            return ServerResponse.createByErrorMessage("查找参数错误");
        }
        List<Category> categoryList=categoryMapper.selectCategoryChilrenByCaegoryId(categoryId);
        if (CollectionUtils.isEmpty(categoryList)){
            logger.info("未找到当前子分类");
        }
        return ServerResponse.createBySuccess(categoryList);
    }
}
