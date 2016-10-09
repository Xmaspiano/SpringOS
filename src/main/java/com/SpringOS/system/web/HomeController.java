package com.SpringOS.system.web;

import com.SpringOS.system.entity.OsMenu;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.session.HttpServletSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;


@Controller
@RequestMapping(value = {"/home"})
public class HomeController {
    private static final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private CacheManager cacheManager;

    @RequestMapping(method = RequestMethod.GET)
    public String index(Model model){
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();

        Cache cache = cacheManager.getCache("user_meun_tab");

        String userTabJson = cache.get(subject.getPrincipal().toString(), String.class);

//        session.setAttribute("menu", userTabJson);
        LOGGER.info(userTabJson);

        model.addAttribute("userTabJson", userTabJson);
        return "system/home/home";
    }

    @RequestMapping(value = "/opentab" )
    @ResponseBody
    public Map openTab(@RequestParam(defaultValue = "0",value = "id") Long id){
        Map jsonMap = new HashMap();
        try {
            Cache cache = cacheManager.getCache("user_meun_tab");

            String userTabJson = cache.get(id, String.class);
            if(userTabJson == null){
                jsonMap.put("success", true);
                jsonMap.put("tab", userTabJson);
            }else{
                jsonMap.put("success", false);
                jsonMap.put("tab", "");
            }
        }catch(Exception e){
            e.printStackTrace();
            jsonMap.put("success", false);
            jsonMap.put("tab", "");
            jsonMap.put("message", "保存失败..."+e.getMessage());
        }
        return jsonMap;
    }

    /****
     * 保存和移除tab页签一样,服务端只保存最终结果的json数据,有前端由js解析产生tab页签
     * @param jsonTab
     * @return
     */
    @RequestMapping(value = "/savetab" )
    @ResponseBody
    public Map saveTab(
            @RequestParam(defaultValue = "",value = "jsonTab") String jsonTab
            ) {
        Map jsonMap = new HashMap();
        try {
            Cache cache = cacheManager.getCache("user_meun_tab");

            Subject subject = SecurityUtils.getSubject();

            LOGGER.info(jsonTab);
            if(!jsonTab.equals("")){
                cache.put(subject.getPrincipal().toString(), jsonTab);
                jsonMap.put("success", true);
                jsonMap.put("tab", jsonTab);
            }else{
                jsonMap.put("success", false);
                jsonMap.put("tab", jsonTab);
            }
        }catch(Exception e){
            e.printStackTrace();
            jsonMap.put("success", false);
            jsonMap.put("tab", jsonTab);
            jsonMap.put("message", "保存失败..."+e.getMessage());
        }
        return jsonMap;
    }

//    @RequestMapping(value = "/closetab" )
//    @ResponseBody
//    public Map closeTab(OsMenu osMenu){
//        Map jsonMap = new HashMap();
//        try {
//            osMenuService.save(osMenu);
//            jsonMap.put("success", true);
//            jsonMap.put("message", "保存成功...");
//        }catch(Exception e){
//            e.printStackTrace();
//            jsonMap.put("success", false);
//            jsonMap.put("message", "保存失败..."+e.getMessage());
//        }
//        return jsonMap;
//    }

}
