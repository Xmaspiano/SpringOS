package com.SpringOS.system.web;

import com.SpringOS.system.entity.OsMenu;
import com.SpringOS.system.entity.RelationshipDeptRole;
import com.SpringOS.system.entity.Role;
import com.SpringOS.system.service.RelationshipDeptRoleService;
import com.SpringOS.system.util.AjaxMsgUtil;
import com.SpringOS.system.web.pagevo.DeptRoleVO;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = {"/roledept"})
public class RelationshipDeptRoleController extends BasicController {
    private static final Logger LOGGER = LoggerFactory.getLogger(RelationshipDeptRoleController.class);

    @Autowired
    private RelationshipDeptRoleService relationshipDeptRoleService;

    @RequestMapping(method = RequestMethod.GET)
    public String index(){
        return "system/role/roledept";
    }

    @RequestMapping(value = "/save" )
    @ResponseBody
//    @RequiresPermissions("dept:save:add,edit")
    public Map saveInfo(DeptRoleVO deptRoleVO){
        Map jsonMap = new HashMap();
        try {
            LOGGER.info(deptRoleVO.toString());

            RelationshipDeptRole relationshipDeptRole = deptRoleVO.toDeptRoleEntity();
            relationshipDeptRole.setRole(deptRoleVO.toRoleEntity());

            relationshipDeptRoleService.save(relationshipDeptRole);

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
    @RequiresPermissions("dept:delete")
    public Map deleteInfo(@RequestParam("id") long id){
        Map jsonMap = new HashMap();
        try {
            relationshipDeptRoleService.delete(id);
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
    public Map getTreeGridData(){
        Map jsonMap = new HashMap();
        List<RelationshipDeptRole> osmList = relationshipDeptRoleService.findAll();

        Map[] treeMap = new HashMap[osmList.size()];
        RelationshipDeptRole sonVO;
        jsonMap.put("total",treeMap.length);
        for (int i = 0; i < treeMap.length; i++) {
            treeMap[i] = new HashMap();
            sonVO = osmList.get(i);
            treeMap[i].put("id",sonVO.getId());
            treeMap[i].put("userId",sonVO.getDeptId());
            treeMap[i].put("roleId",sonVO.getRoleId());
        }
        jsonMap.put("rows",treeMap);
        return jsonMap;
    }

    @RequestMapping(value = "/date_grid.json" )
    @ResponseBody
    public Map getRoleGridDataByDept(@RequestParam(defaultValue = "0",value = "deptid") Long deptId){
        Map jsonMap = new HashMap();

        List<RelationshipDeptRole> osmList = relationshipDeptRoleService.findRelationshipsByDept(deptId);
        Map[] treeMap = new HashMap[osmList.size()];
        RelationshipDeptRole relationshipDeptRole;
        Role role;
        jsonMap.put("total",treeMap.length);
        for (int i = 0; i < treeMap.length; i++) {
            treeMap[i] = new HashMap();
            relationshipDeptRole = osmList.get(i);
            role = relationshipDeptRole.getRole();

            treeMap[i].put("id",relationshipDeptRole.getId());
            treeMap[i].put("deptid",relationshipDeptRole.getDeptId());
            treeMap[i].put("roleid",relationshipDeptRole.getRoleId());
            treeMap[i].put("role",role.getRole());
            treeMap[i].put("description",role.getDescription());
            treeMap[i].put("available",role.getAvailable());
            treeMap[i].put("roleList",role.getRelationshipUserRoleList());
        }
        jsonMap.put("rows",treeMap);
        return jsonMap;
    }

}
