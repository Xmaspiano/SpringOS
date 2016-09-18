package com.SpringOS.system.web;

import com.SpringOS.system.entity.Favarite;
import com.SpringOS.system.entity.Language;
import com.SpringOS.system.entity.OsMenu;
import com.SpringOS.system.entity.User;
import com.SpringOS.system.service.FavariteService;
import com.SpringOS.system.service.OsMenuService;
import com.SpringOS.system.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
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
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping(value = {"/favarite"})
public class FavariteController {
    private static final Logger LOGGER = LoggerFactory.getLogger(FavariteController.class);

    @Autowired
    private FavariteService favariteService;

    @Autowired
    private UserService userService;

    @Autowired
    private OsMenuService osMenuService;

//
//    @RequestMapping(method = RequestMethod.GET)
//    public String index(Model model){
//        return "system/home/home";
//    }

    @RequestMapping(value = "/check" )
    @ResponseBody
    public Map onFavarote(
            @RequestParam(defaultValue = "",value = "title") String title,
            @RequestParam(defaultValue = "false",value = "flag") boolean flag,
            @RequestParam(defaultValue = "false",value = "menuid") Long menuId
                          ){
        Map jsonMap = new HashMap();
        Favarite favarite = new Favarite();
        try {

            Subject subject = SecurityUtils.getSubject();
            User user = userService.findByUsername(subject.getPrincipal().toString());

            if(flag) {
                favarite.setUserId(user.getId());
                favarite.setMenuId(menuId);
                favarite.setSort("A");
                favariteService.save(favarite);
            }else{

                favariteService.deleteByUserIdAndMenuId(user.getId(), menuId);
            }

            jsonMap.put("success", true);
            jsonMap.put("message", "保存成功...");
        }catch(Exception e){
            e.printStackTrace();
            jsonMap.put("success", false);
            jsonMap.put("message", "保存失败..."+e.getMessage());
        }
        return jsonMap;

    }

    @RequestMapping(value = "/menu_favarite_url.json" )
    @ResponseBody
    public Map getFavariteDateUrl(){
        Map jsonMap = new HashMap();
        jsonMap.put("success", true);


        Subject subject = SecurityUtils.getSubject();
        User user = userService.findByUsername(subject.getPrincipal().toString());
        List<Favarite> favarites = favariteService.findByUserId(user.getId());

        Map[] treeMap = new HashMap[favarites.size()];
        Favarite favarite;
        jsonMap.put("total",favarites.size());
        for (int i = 0; i < favarites.size(); i++) {
            treeMap[i] = new HashMap();
            favarite = favarites.get(i);
            OsMenu osMenu = osMenuService.findOne(favarite.getMenuId());
            treeMap[i].put("text", osMenu.getName());
            treeMap[i].put("id", osMenu.getId());
        }
        jsonMap.put("rows",treeMap);
        return jsonMap;
    }
}
