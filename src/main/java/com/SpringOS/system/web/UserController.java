package com.SpringOS.system.web;

import com.SpringOS.system.entity.User;
import com.SpringOS.system.service.UserService;
import com.SpringOS.system.service.UserService;
import com.SpringOS.system.util.AjaxMsgUtil;
import com.SpringOS.util.MessageKey;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping(value = {"/user"})
public class UserController extends BasicController {

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public String index(){
        return "system/user/user";
    }

    @RequestMapping(value = "/save" )
    @ResponseBody
    @RequiresPermissions("user:save:add,edit")
    public Map saveInfo(User User){
        userService.save(User);
        return AjaxMsgUtil.AjaxMsg(AjaxMsgUtil.SUCCESS, getMsgByKey("saveMenuInfo.success"));
    }

    @RequestMapping(value = "/delete" )
    @ResponseBody
    @RequiresPermissions("user:delete")
    public Map deleteInfo(@RequestParam("id") long id){
        userService.delete(id);
        return AjaxMsgUtil.AjaxMsg(AjaxMsgUtil.SUCCESS, getMsgByKey("deleteMenuInfo.success"));
    }

    @RequestMapping(value = "/date_grid.json" )
    @ResponseBody
    public Map getGridData(){
        Map jsonMap = new HashMap();
        List<User> osmList = userService.findAll();

        Map[] treeMap = new HashMap[osmList.size()];
        User sonVO;
        jsonMap.put("total",treeMap.length);
        for (int i = 0; i < treeMap.length; i++) {
            treeMap[i] = new HashMap();
            sonVO = osmList.get(i);
            treeMap[i].put("id",sonVO.getId());
            treeMap[i].put("username",sonVO.getUsername());
            treeMap[i].put("password",sonVO.getPassword());
            treeMap[i].put("salt",sonVO.getSalt());
            treeMap[i].put("locked",sonVO.getLocked());
            treeMap[i].put("roleList",sonVO.getRelationshipUserRoleList());
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
