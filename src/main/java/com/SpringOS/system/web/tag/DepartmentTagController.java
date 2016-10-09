package com.SpringOS.system.web.tag;

import com.SpringOS.system.entity.Department;
import com.SpringOS.system.service.DepartmentService;
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
@RequestMapping(value = {"/dept/tag"})
public class DepartmentTagController {
    private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentTagController.class);

    @Autowired
    private DepartmentService departmentService;

    @RequestMapping(method = RequestMethod.GET)
    public String index(){
        return "system/home/left";
    }

    @RequestMapping(value = "/dept_tree.json" )
    @ResponseBody
    public Map getTreeData(@RequestParam(defaultValue = "1",value = "parentid") Long id){
        List<Department> departmentList = departmentService.findAllBySuper(getLevelTwodept(id).getId());//二级菜单

        Map jsonMap = new HashMap();
        jsonMap.put("total",departmentList.size());
        jsonMap.put("rows",this.test(departmentList));
        return jsonMap;
    }

    @RequestMapping(value = "/dept_tree_url.json" )
    @ResponseBody
    public Map getTreeDataUrl(@RequestParam(defaultValue = "1",value = "id") Long id){
        Map jsonMap = new HashMap();
        jsonMap.put("success", true);

        Department department = departmentService.findOne(id);
        Department osPartdept = getLevelTwodept(department.getParentid());

        if(id == 0l){//默认
            jsonMap.put("title", "首页");
            jsonMap.put("id", 0);
            jsonMap.put("url", "/index");
            jsonMap.put("parentName", "菜单");
            jsonMap.put("parentId", 1);
        }else{
            jsonMap.put("title", department.getName());
            jsonMap.put("id", department.getId());
            jsonMap.put("url", department.getRemark());
            jsonMap.put("parentName", osPartdept.getName());
            jsonMap.put("parentId", department.getParentid());
        }
        return jsonMap;
    }

    private Department getLevelTwodept(Long id){
        Department osPartdept;
        if(id > 1l) {
            osPartdept = departmentService.findOne(id);
            if (osPartdept.getParentid() != 1l) {
                return getLevelTwodept(osPartdept.getParentid());
            }
        }else {
            osPartdept = new Department();
            osPartdept.setId(id);
        }
        return osPartdept;
    }

    public Map[] test(List<Department> departmentList){
        Map[] jsonMap = new HashMap[departmentList.size()];
        Department department;
        for (int i = 0; i < jsonMap.length; i++) {
            department = departmentList.get(i);
            jsonMap[i] = new HashMap();

            jsonMap[i].put("id",department.getId());
            jsonMap[i].put("text",department.getName());
            if(department.getDepartmentList().size() > 0){
                jsonMap[i].put("children",this.test(department.getDepartmentList()));
            }
        }
        return jsonMap;
    }
}
