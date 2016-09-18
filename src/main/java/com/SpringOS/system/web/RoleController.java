package com.SpringOS.system.web;

import com.SpringOS.system.entity.Role;
//import com.SpringOS.system.entity.User;
import com.SpringOS.system.service.RoleService;
//import com.SpringOS.system.service.UserService;
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
@RequestMapping(value = {"/role"})
public class RoleController extends BasicController {

    @Autowired
    private RoleService roleService;

    @RequestMapping(method = RequestMethod.GET)
    public String index(){
        return "system/role/role";
    }

    @RequestMapping(value = "/save" )
    @ResponseBody
    @RequiresPermissions("role:save:add,edit")
    public Map saveInfo(Role role){
        roleService.save(role);
        return AjaxMsgUtil.AjaxMsg(AjaxMsgUtil.SUCCESS, getMsgByKey("saveInfo.success"));
    }

    @RequestMapping(value = "/delete" )
    @ResponseBody
    @RequiresPermissions("role:delete")
    public Map deleteInfo(@RequestParam("id") long id){
        roleService.delete(id);
        return AjaxMsgUtil.AjaxMsg(AjaxMsgUtil.SUCCESS, getMsgByKey("deleteInfo.success"));
    }

    @RequestMapping(value = "/date_grid.json" )
    @ResponseBody
    public Map getGridDate(){
        Map jsonMap = new HashMap();
        List<Role> osmList = roleService.findAll();

        Map[] treeMap = new HashMap[osmList.size()];
        Role sonVO;
        jsonMap.put("total",treeMap.length);
        for (int i = 0; i < treeMap.length; i++) {
            treeMap[i] = new HashMap();
            sonVO = osmList.get(i);
            treeMap[i].put("id",sonVO.getId());
            treeMap[i].put("role",sonVO.getRole());
            treeMap[i].put("description",sonVO.getDescription());
            treeMap[i].put("available",sonVO.getAvailable());
            treeMap[i].put("roleList",sonVO.getRelationshipUserRoleList());
        }
        jsonMap.put("rows",treeMap);
        return jsonMap;
    }

}
