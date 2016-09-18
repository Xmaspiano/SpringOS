package com.SpringOS.system.web;

import com.SpringOS.system.util.AjaxMsgUtil;
import com.SpringOS.util.MessageKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by AlbertXmas on 16/8/25.
 */
@Controller
public abstract class BasicController {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass().getName());

    /** 基于@ExceptionHandler异常处理 **/
    @ExceptionHandler
    @ResponseBody
    public Map exp(HttpServletRequest request, Exception ex) {
        LOGGER.info(MessageKey.getMsg("60001"), ex);
        return AjaxMsgUtil.AjaxMsg(
                AjaxMsgUtil.ERROR,
                MessageKey.getMsg(BasicController.class.getName()+".exp.error") + ex.getMessage(),
                ex);

    }

    public String getMsgByKey(String Key){
//        System.out.println(this.getClass().getName()+"."+Key);
        return MessageKey.getMsg(this.getClass().getName()+"."+Key);
    }
}
