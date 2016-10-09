package com.SpringOS.system.web;

import com.SpringOS.system.entity.ShiroResources;
import com.SpringOS.system.service.ShiroResourcesService;
import com.SpringOS.system.util.AjaxMsgUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = {"/resources"})
public class ShiroResourcesController extends BasicController {

    @Autowired
    private ShiroResourcesService shiroResourcesService;

    @RequestMapping(method = RequestMethod.GET)
    public String index(){
        return "system/resources/resources";
    }

    @RequestMapping(value = "/save" )
    @ResponseBody
    @RequiresPermissions("resources:save:add,edit")
    public Map saveInfo(ShiroResources shiroResources){
        shiroResourcesService.save(shiroResources);
        return AjaxMsgUtil.AjaxMsg(AjaxMsgUtil.SUCCESS, getMsgByKey("saveInfo.success"));
    }

    @RequestMapping(value = "/delete" )
    @ResponseBody
    @RequiresPermissions("resources:delete")
    public Map deleteInfo(@RequestParam("id") long id){
        shiroResourcesService.delete(id);
        return AjaxMsgUtil.AjaxMsg(AjaxMsgUtil.SUCCESS, getMsgByKey("deleteInfo.success"));
    }

    @RequestMapping(value = "/date_grid.json" )
    @ResponseBody
    public Map getGridData(){
        Map jsonMap = new HashMap();
        List<ShiroResources> osmList = shiroResourcesService.findAll();

        Map[] treeMap = new HashMap[osmList.size()];
        ShiroResources sonVO;
        jsonMap.put("total",treeMap.length);
        for (int i = 0; i < treeMap.length; i++) {
            treeMap[i] = new HashMap();
            sonVO = osmList.get(i);
            treeMap[i].put("id",sonVO.getId());
            treeMap[i].put("realName",sonVO.getRealName());
            treeMap[i].put("name",sonVO.getName());
            treeMap[i].put("method",sonVO.getMethod());
            treeMap[i].put("shiroAuth",sonVO.getShiroAuth());
            treeMap[i].put("available",sonVO.isAvailable());
//            treeMap[i].put("datetime",sonVO.getDatetime());
        }
        jsonMap.put("rows",treeMap);
        return jsonMap;
    }

}
