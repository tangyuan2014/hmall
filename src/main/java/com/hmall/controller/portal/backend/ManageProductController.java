package com.hmall.controller.portal.backend;


import com.google.common.collect.Maps;
import com.hmall.Utils.PropsUtil;
import com.hmall.common.Const;
import com.hmall.common.ServerResponse;
import com.hmall.pojo.Product;
import com.hmall.pojo.User;
import com.hmall.service.IFileService;
import com.hmall.service.IProductService;
import com.hmall.service.IUserService;
import com.hmall.vo.ProductVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
@RequestMapping("manage/product/")
public class ManageProductController {

    @Autowired
    private IUserService iUserService;

    @Autowired
    private IProductService iProductService;

    @Autowired
    private IFileService iFileService;

    @RequestMapping("save.do")
    @ResponseBody
    public ServerResponse productSave(HttpSession session, Product product) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorMessage("PLEASE LOG IN");
        }
        if (iUserService.checkAdmin(user).isSuccess()) {
            return iProductService.saveProduct(product);
        }
        return ServerResponse.createByErrorMessage("YOU have no permission");
    }

    @RequestMapping("set_salestatus")
    @ResponseBody
    public ServerResponse<String> setSalseStatus(HttpSession session, Integer productId, Integer status) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorMessage("PLEASE LOG IN");
        }
        if (iUserService.checkAdmin(user).isSuccess()) {
            return iProductService.setSaleStatus(productId, status);
        }
        return ServerResponse.createByErrorMessage("YOU have no permission");
    }

    @RequestMapping("detail.do")
    @ResponseBody
    public ServerResponse<ProductVo> getDetail(HttpSession session, Integer productId) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorMessage("PLEASE LOG IN");
        }
        if (iUserService.checkAdmin(user).isSuccess()) {
            return iProductService.getDetail(productId);
        }
        return ServerResponse.createByErrorMessage("YOU have no permission");
    }

    @RequestMapping("get_list.do")
    @ResponseBody
    public ServerResponse getList(HttpSession session, @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum, @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorMessage("PLEASE LOG IN");
        }
        if (iUserService.checkAdmin(user).isSuccess()) {
            return iProductService.getList(pageNum, pageSize);
        }
        return ServerResponse.createByErrorMessage("YOU have no permission");
    }

    @RequestMapping("search.do")
    @ResponseBody
    public ServerResponse searchList(HttpSession session, String productName, Integer productId, @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum, @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorMessage("PLEASE LOG IN");
        }
        if (iUserService.checkAdmin(user).isSuccess()) {
            return iProductService.searchList(productName, productId, pageNum, pageSize);
        }
        return ServerResponse.createByErrorMessage("YOU have no permission");
    }

    @RequestMapping("upload.do")
    @ResponseBody
    public ServerResponse upload(HttpSession session,@RequestParam(value ="upload_file",required = false) MultipartFile multipartFile, HttpServletRequest httpServletRequest) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorMessage("PLEASE LOG IN");
        }
        if (iUserService.checkAdmin(user).isSuccess()) {
            String path = httpServletRequest.getSession().getServletContext().getRealPath("upload");
            String targetFileName=iFileService.upload(multipartFile,path);
            String url=targetFileName;
            Map fileMap= Maps.newHashMap();
            fileMap.put("uri",targetFileName);
            fileMap.put("url","ftp://192.168.56.101/"+url);
            return ServerResponse.createBySuccess(fileMap);
       }
        return ServerResponse.createByErrorMessage("YOU have no permission");
    }

    /**
     * simditor
     * @param session
     * @param multipartFile
     * @param httpServletRequest
     * @param response
     * @return
     */
    @RequestMapping("rich_text_imag_upload.do")
    @ResponseBody
    public Map richTextImagUpload(HttpSession session, @RequestParam(value ="upload_file",required = false)MultipartFile multipartFile, HttpServletRequest httpServletRequest, HttpServletResponse response) {
        Map resultMap=Maps.newHashMap();
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
           resultMap.put("success",false);
           resultMap.put("msg","PLEASE LOG IN");
            return resultMap;
        }
        if (iUserService.checkAdmin(user).isSuccess()) {
            String path = httpServletRequest.getSession().getServletContext().getRealPath("upload");
            String targetFileName=iFileService.upload(multipartFile,path);
            if (StringUtils.isBlank(targetFileName)){
                resultMap.put("success",false);
                resultMap.put("msg","uploading fail");
                return resultMap;

            }
            String url=PropsUtil.getProerty("ftp.server.http.prefix")+targetFileName;

            resultMap.put("success",true);
            resultMap.put("msg","uploading success");
            resultMap.put("url",url);
            response.addHeader("Access-Control-Allow-Headers","X-File-Name");
            return resultMap;

        }
        resultMap.put("success",false);
        resultMap.put("msg","No permission");
        return resultMap;
    }

}
