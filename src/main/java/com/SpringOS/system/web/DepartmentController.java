package com.SpringOS.system.web;

import com.SpringOS.system.entity.Department;
import com.SpringOS.system.service.DepartmentService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@RequestMapping(value = {"/dept"})
public class DepartmentController {
    private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentController.class);

    @Autowired
    private DepartmentService departmentService;

    @RequestMapping(method = RequestMethod.GET)
    public String index(){
        return "system/dept/dept";
    }

    @RequestMapping(value = "/save" )
    @ResponseBody
    @RequiresPermissions("dept:save:add,edit")
    public Map saveInfo(Department department){
        Map jsonMap = new HashMap();
        try {
            departmentService.save(department);
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
            if(departmentService.findAllBySuper(id).size() > 0){
                throw new Exception("包含子菜单");
            }
            departmentService.delete(id);
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
        List<Department> osmList = departmentService.findAll();

        Map[] treeMap = new HashMap[osmList.size()];
        Department sonVO;
        jsonMap.put("total",treeMap.length);
        for (int i = 0; i < treeMap.length; i++) {
            treeMap[i] = new HashMap();
            sonVO = osmList.get(i);
            treeMap[i].put("id",sonVO.getId());
            treeMap[i].put("code",sonVO.getCode());
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

}
