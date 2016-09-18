package com.SpringOS.system.web;

import com.SpringOS.system.entity.OsMenu;
import com.SpringOS.system.service.OsMenuService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = {"/menu"})
public class MenuController {
    private static final Logger LOGGER = LoggerFactory.getLogger(MenuController.class);

    @Autowired
    private OsMenuService osMenuService;

    @RequestMapping(method = RequestMethod.GET)
    public String index(){
        return "system/menu/menu";
    }

    @RequestMapping(value = "/save" )
    @ResponseBody
    @RequiresPermissions("menu:save:add,edit")
    public Map saveInfo(OsMenu osMenu){
        Map jsonMap = new HashMap();
        try {
            osMenuService.save(osMenu);
            jsonMap.put("success", true);
            jsonMap.put("message", "保存成功...");
        }catch(Exception e){
            e.printStackTrace();
            jsonMap.put("success", false);
            jsonMap.put("message", "保存失败..."+e.getMessage());
        }
        return jsonMap;
    }

    @RequestMapping(value = "/delete" )
    @ResponseBody
    @RequiresPermissions("menu:delete")
    public Map deleteInfo(@RequestParam("id") long id){
        Map jsonMap = new HashMap();
        try {
            if(osMenuService.findAllBySuper(id).size() > 0){
                throw new Exception("包含子菜单");
            }
            osMenuService.delete(id);
            jsonMap.put("success", true);
            jsonMap.put("message", "删除成功...");
        }catch(Exception e){
            e.printStackTrace();
            jsonMap.put("success", false);
            jsonMap.put("message", "删除失败..."+e.getMessage());
        }
        return jsonMap;
    }

    @RequestMapping(value = "/date_treegrid.json" )
    @ResponseBody
    public Map getTreeGridDate(){
        Map jsonMap = new HashMap();
        List<OsMenu> osmList = osMenuService.findAll();

        Map[] treeMap = new HashMap[osmList.size()];
        OsMenu sonVO;
        jsonMap.put("total",treeMap.length);
        for (int i = 0; i < treeMap.length; i++) {
            treeMap[i] = new HashMap();
            sonVO = osmList.get(i);
            treeMap[i].put("id",sonVO.getId());
            treeMap[i].put("appid",sonVO.getAppid());
            treeMap[i].put("name",sonVO.getName());
            treeMap[i].put("parentid",sonVO.getParentid());
            treeMap[i].put("remark",sonVO.getRemark());
            treeMap[i].put("life",sonVO.isLife());
            treeMap[i].put("dateremark",sonVO.getDatemark());
            if(sonVO.getParentid() != 1l) {
                treeMap[i].put("_parentId", sonVO.getParentid());
            }
        }
        jsonMap.put("rows",treeMap);
        return jsonMap;
    }

//    @ModelAttribute
//    public void ssss(@RequestParam(value = "Id",defaultValue = "-1")Long id, Model model){
//
////        model.addAttribute("",);
//
//    }

}
