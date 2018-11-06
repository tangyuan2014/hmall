package com.hmall.controller.portal.backend;

import com.hmall.common.Const;
import com.hmall.common.ServerResponse;
import com.hmall.pojo.Category;
import com.hmall.pojo.User;
import com.hmall.service.ICategoryService;
import com.hmall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/manage/category/")
public class ManageCategoryController {

    @Autowired
    private IUserService iUserService;

    @Autowired
    private ICategoryService iCategoryService;

    @RequestMapping("add_category.do")
    @ResponseBody
    public ServerResponse addCategory(HttpSession session, String categoryName, @RequestParam(value = "parentId", defaultValue = "0") int parentId) {

        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorMessage("请登录");
        }
        if (iUserService.checkAdmin(user).isSuccess()) {

            return iCategoryService.addCategory(parentId, categoryName);

        }
        return ServerResponse.createBySuccessMessage("无权限操作");
    }

    @RequestMapping("set_categoryname.do")
    @ResponseBody
    public ServerResponse setCateogoryName(HttpSession session, Integer categoryId, String categoryName) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorMessage("请登录");
        }

        if (iUserService.checkAdmin(user).isSuccess()) {

            return iCategoryService.setCategoryName(categoryId, categoryName);

        }
        return ServerResponse.createBySuccessMessage("无权限操作");

    }

    @RequestMapping("get_categorychildrencategory.do")
    @ResponseBody
    public ServerResponse<List<Category>> getChilrenParallelCategory(HttpSession session, @RequestParam(value = "categoryId",defaultValue = "0") Integer categoryId){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorMessage("请登录");
        }

        if (iUserService.checkAdmin(user).isSuccess()) {

            return iCategoryService.getChildrenParallelCategory(categoryId);
        }
        return ServerResponse.createBySuccessMessage("无权限操作");
    }

/**
 * 查询当前category id 和递归查询子节点
 */
@RequestMapping("get_categorychildrendeepcategory.do")
@ResponseBody
     public ServerResponse getCategoryAndChildrenCategory(HttpSession session,Integer categoryId){
         User user = (User) session.getAttribute(Const.CURRENT_USER);
         if (user == null) {
             return ServerResponse.createByErrorMessage("请登录");
         }

         if (iUserService.checkAdmin(user).isSuccess()) {

             return iCategoryService.getCategoryAndChildrenCategory(categoryId);
         }
         return ServerResponse.createBySuccessMessage("无权限操作");

     }


}
