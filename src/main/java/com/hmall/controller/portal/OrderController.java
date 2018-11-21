package com.hmall.controller.portal;


import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.demo.trade.config.Configs;
import com.google.common.collect.Maps;
import com.hmall.common.Const;
import com.hmall.common.ServerResponse;
import com.hmall.pojo.PayInfo;
import com.hmall.pojo.User;
import com.hmall.service.IOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Iterator;
import java.util.Map;

@Controller
@RequestMapping("/order/")
public class OrderController {

    @Autowired
    private IOrderService iOrderService;

    private static final Logger logger=LoggerFactory.getLogger(OrderController.class);

    @RequestMapping("pay")
    @ResponseBody
    public ServerResponse pay(HttpSession session, long orderNo, HttpServletRequest request){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorMessage("PLEASE LOG IN");
        }

        String path=request.getSession().getServletContext().getRealPath("upload");
        return iOrderService.pay(orderNo,user.getId(),path);
    }

    @RequestMapping("alipay_callback.do")
    @ResponseBody
    public Object alipayCallBack(HttpServletRequest request){

        Map<String,String> parmas= Maps.newHashMap();
        Map requestParams=request.getParameterMap();
        for (Iterator iter =requestParams.keySet().iterator();iter.hasNext();){
            String name=(String)iter.next();
            String[] values=(String [])requestParams.get(name);
            String valueStr="";
            for(int i=0;i<values.length;i++){
                valueStr=(i==values.length-1)?valueStr+values[i]:valueStr+values[i]+",";
            }
            parmas.put(name,valueStr);
        }
        logger.info("支付宝回调，sign:{},trade_status:{},参数：{}",parmas.get("sign"),parmas.get("trade_status"),parmas.toString());
        parmas.remove("sign_type");
        try {
            boolean alipayRSACheckedV2= AlipaySignature.rsaCheckV2(parmas,Configs.getAlipayPublicKey(),"utf-8",Configs.getSignType());
        if(!alipayRSACheckedV2){
            return ServerResponse.createByErrorMessage("非法请求,验证不通过");
        }
        } catch (AlipayApiException e) {
            logger.info("支付宝验证异常",e);
        }

        ServerResponse serverResponse=iOrderService.alipayCallBack(parmas);
        if(serverResponse.isSuccess()){
            return Const.TradeStatus.RESPONSE_SUCCESS;
        }
        return Const.TradeStatus.RESPONSE_FAIL;

    }

    @RequestMapping("query_order_pay_stattus.do")
    @ResponseBody
    public ServerResponse<Boolean> queryOrderPayStatus(HttpSession session, Long orderNo){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorMessage("PLEASE LOG IN");
        }
        if(iOrderService.queryOrderPayStatus(user.getId(),orderNo).isSuccess()){
            return ServerResponse.createBySuccess(true);
        }

        return ServerResponse.createBySuccess(false);
    }






}
