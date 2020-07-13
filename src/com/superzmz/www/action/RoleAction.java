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


    /** �б� */
    @RequestMapping("/role_list.action")
    public String list(Map map) throws Exception {
        List<Role> roleList = roleService.findAll();
        map.put("roleList", roleList);
        return "role/list";
    }

    /** ɾ�� */
    @RequestMapping("/role_delete.action")
    public String delete(Long id) throws Exception {
        roleService.delete(id);
        return "redirect:/role_list.action";
    }

    /** ���ҳ�� */
    @RequestMapping("/role_addUI.action")
    public String addUI() throws Exception {
        return "role/saveUI";
    }

    /** ��� */
    @RequestMapping("/role_add.action")
    public String add(Role model) throws Exception {
        roleService.save(model);
        return "redirect:/role_list.action";
    }

    /** �޸�ҳ�� */
    @RequestMapping("/role_editUI.action")
    public String editUI(Long id,Map map) throws Exception {
        // ׼�����Ե�����
        Role role = roleService.getById(id);
        map.put("role",role);

        return "role/saveUI";
    }

    /** �޸� */
    @RequestMapping("/role_edit.action")
    public String edit(Role model) throws Exception {
        // 1�������ݿ��л�ȡԭ����
        Role role = roleService.getById(model.getId());

        // 2������Ҫ�޸ĵ�����
        role.setName(model.getName());
        role.setDescription(model.getDescription());

        // 3�����µ����ݿ�
        roleService.update(role);
        return "redirect:/role_list.action";
    }

    /** ����Ȩ��ҳ�� */
    @RequestMapping("/role_setPrivilegeUI.action")
    public String setPrivilegeUI(Long id,Map map) throws Exception {
        // ׼�����Ե�����
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


        // ׼������ privilegeList
        List<Privilege> privilegeList = privilegeService.findAll();
        map.put("privilegeList", privilegeList);

        return "role/setPrivilegeUI";
    }

    /** ����Ȩ�� */
    @RequestMapping("/role_setPrivilege.action")
    public String setPrivilege(Long [] privilegeIds,Long id) throws Exception {
        // 1�������ݿ��л�ȡԭ����
        Role role = roleService.getById(id);

        // 2������Ҫ�޸ĵ�����
        List<Privilege> privilegeList = privilegeService.getByIds(privilegeIds);
        role.setPrivileges(new HashSet<Privilege>(privilegeList));

        // 3�����µ����ݿ�
        roleService.update(role);
        return "redirect:/role_list.action";
    }


}
