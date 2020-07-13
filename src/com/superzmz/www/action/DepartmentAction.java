package com.superzmz.www.action;

import com.superzmz.www.bean.Department;
import com.superzmz.www.service.DepartmentService;
import com.superzmz.www.tool.DepartmentUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Controller
@Scope("prototype")
public class DepartmentAction {

    @Resource
    private DepartmentService departmentService;


    @RequestMapping("/department_list.action")
    public String list(Long parentId,Map map) throws Exception {
        // �б�ҳ��ֻ��ʾһ��ģ�ͬ���ģ��������ݣ�Ĭ����ʾ����Ĳ����б�
        List<Department> departmentList = null;
        if (parentId == null) { // ���������б�
            departmentList = departmentService.findTopList();
        } else { // �Ӳ����б�
            departmentList = departmentService.findChildren(parentId);
            Department parent = departmentService.getById(parentId);

            map.put("parent", parent);

        }
        map.put("departmentList", departmentList);
        return "department/list";
    }

    @RequestMapping("/department_delete.action")
    public String delete(Long parentId,Long id, RedirectAttributes attr) throws Exception {
        departmentService.delete(id);

        /** ���������ض���
         * addFlashAttributeʵ���Ͻ���Щ���Դ洢��flashmap�У��ڲ�ά�����û��Ự�У�һ����һ���ض��������õ�����ͻᱻɾ������
         * ��һ��addAttributeʵ�����Ǵ����������й���������������ʹ����������ض��������ҳ�� ��
         * �����ŵ��ǿ�����Flash�����д洢�ܶ��κζ�����Ϊ���������л�����������У�������Ϊ����ά������
         * ��ʹ��addAttribute����Ϊ����ӵĶ����ת��Ϊ���� �����������ǳ�������String��primitives�ȶ������͡�
         */
        attr.addFlashAttribute("parentId", parentId);
        return "redirect:/department_list.action";// �ض��������
    }

    @RequestMapping("/department_addUI.action")
    public String addUI(Map map) throws Exception {
        // ׼������, departmentList
        List<Department> topList = departmentService.findTopList();
        List<Department> departmentList = DepartmentUtils.getAllDepartments(topList);
        map.put("departmentList", departmentList);
        return "department/saveUI";
    }

    @RequestMapping("/department_add.action")
    public String add(Department model,Long parentId,RedirectAttributes attributes) throws Exception {

        Department parent = departmentService.getById(parentId);
        model.setParent(parent);
        // ����
        departmentService.save(model);

        attributes.addFlashAttribute("parentId", parentId);
        return "redirect:/department_list.action";// �ض��������
    }

    @RequestMapping("/department_editUI.action")
    public String editUI(Long departId,Map map) throws Exception {
        // ׼������, departmentList
        List<Department> topList = departmentService.findTopList();
        List<Department> departmentList = DepartmentUtils.getAllDepartments(topList);
        map.put("departmentList", departmentList);

        // ׼�����Ե�����
        Department department = departmentService.getById(departId);
        map.put("department",department);
        if (department.getParent() != null) {
            Long parentId= department.getParent().getId();
            //todo
            map.put("parentId", parentId);
        }
        return "department/saveUI";
    }

    @RequestMapping("/department_edit.action")
    public String edit(Long parentId,Department model,RedirectAttributes attributes) throws Exception {
        // 1�������ݿ�ȡ��ԭ����
        Department department = departmentService.getById(model.getId());

        // 2������Ҫ�޸ĵ�����
        department.setName(model.getName());
        department.setDescription(model.getDescription());
        department.setParent(departmentService.getById(parentId)); // �����������ϼ�����

        // 3�����µ����ݿ���
        departmentService.update(department);

        attributes.addFlashAttribute("parentId", parentId);// �ض��������
        return "redirect:/department_list.action";
    }


}
