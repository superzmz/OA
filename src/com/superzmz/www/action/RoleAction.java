package com.superzmz.www.action;

import com.superzmz.www.bean.Privilege;
import com.superzmz.www.bean.Role;
import com.superzmz.www.service.PrivilegeService;
import com.superzmz.www.service.RoleService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

@Controller
@Scope("prototype")
public class RoleAction {

    @Resource
    private RoleService roleService;

    @Resource
    private PrivilegeService privilegeService;


    /** 列表 */
    @RequestMapping("/role_list.action")
    public String list(Map map) throws Exception {
        List<Role> roleList = roleService.findAll();
        map.put("roleList", roleList);
        return "role/list";
    }

    /** 删除 */
    @RequestMapping("/role_delete.action")
    public String delete(Long id) throws Exception {
        roleService.delete(id);
        return "redirect:/role_list.action";
    }

    /** 添加页面 */
    @RequestMapping("/role_addUI.action")
    public String addUI() throws Exception {
        return "role/saveUI";
    }

    /** 添加 */
    @RequestMapping("/role_add.action")
    public String add(Role model) throws Exception {
        roleService.save(model);
        return "redirect:/role_list.action";
    }

    /** 修改页面 */
    @RequestMapping("/role_editUI.action")
    public String editUI(Long id,Map map) throws Exception {
        // 准备回显的数据
        Role role = roleService.getById(id);
        map.put("role",role);

        return "role/saveUI";
    }

    /** 修改 */
    @RequestMapping("/role_edit.action")
    public String edit(Role model) throws Exception {
        // 1，从数据库中获取原对象
        Role role = roleService.getById(model.getId());

        // 2，设置要修改的属性
        role.setName(model.getName());
        role.setDescription(model.getDescription());

        // 3，更新到数据库
        roleService.update(role);
        return "redirect:/role_list.action";
    }

    /** 设置权限页面 */
    @RequestMapping("/role_setPrivilegeUI.action")
    public String setPrivilegeUI(Long id,Map map) throws Exception {
        // 准备回显的数据
        Role role = roleService.getById(id);
        map.put("role", role);

        if (role.getPrivileges() != null) {
            Long[] privilegeIds = new Long[role.getPrivileges().size()];

            int index = 0;
            for (Privilege priv : role.getPrivileges()) {
                privilegeIds[index++] = priv.getId();
            }
            map.put("privilegeIds", privilegeIds);
        }


        // 准备数据 privilegeList
        List<Privilege> privilegeList = privilegeService.findAll();
        map.put("privilegeList", privilegeList);

        return "role/setPrivilegeUI";
    }

    /** 设置权限 */
    @RequestMapping("/role_setPrivilege.action")
    public String setPrivilege(Long [] privilegeIds,Long id) throws Exception {
        // 1，从数据库中获取原对象
        Role role = roleService.getById(id);

        // 2，设置要修改的属性
        List<Privilege> privilegeList = privilegeService.getByIds(privilegeIds);
        role.setPrivileges(new HashSet<Privilege>(privilegeList));

        // 3，更新到数据库
        roleService.update(role);
        return "redirect:/role_list.action";
    }


}
