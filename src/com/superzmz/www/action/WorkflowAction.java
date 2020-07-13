package com.superzmz.www.action;

import com.superzmz.www.bean.*;
import com.superzmz.www.service.LeaveApproverService;
import com.superzmz.www.service.MessageService;
import com.superzmz.www.service.UserService;
import com.superzmz.www.service.WorkflowService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@Scope("prototype")
public class WorkflowAction {

    @Resource
    private WorkflowService workflowService ;

    @Resource
    private MessageService messageService ;

    @Resource
    private UserService userService;

    @Resource
    private LeaveApproverService leaveApproverService;


    // ===================================������===================================

    /** ��������ѡ���б�:��� �Ӱ� ���� ���� ��� ��Ʒ�� */
    @RequestMapping("/flow_applyTypeListUI")
    public String applyTypeListUI(Map map) throws Exception {
//        �������������������
        List<ApplyType> list = new ArrayList<ApplyType>();
        list.add(new ApplyType("���", "flow_submitUI.action"));
        list.add(new ApplyType("�Ӱ�", "flow_submitUI.action"));
        list.add(new ApplyType("����", "flow_submitUI.action"));
        list.add(new ApplyType("����", "flow_submitUI.action"));
        list.add(new ApplyType("���", "flow_submitUI.action"));
        list.add(new ApplyType("��Ʒ", "flow_submitUI.action"));

        map.put("applyTypeList", list);

        return "flow/applyTypeListUI";
    }

    /** �ύ����ҳ�� */
    @RequestMapping("/flow_submitUI.action")
    public String submitUI(Map map,HttpSession session) throws Exception {
        Date currentDate = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sf.format(currentDate);
        map.put("currentDate", date);

        User user = getCurrentUser(session);
        List<User> approvers = workflowService.getApproversByDepart(user,user.getDepartment());
        for (User approver : approvers) {

            Set<Role> roles  = approver.getRoles();
            StringBuffer stringBuffer = new StringBuffer();
            for (Role r : roles) {
                stringBuffer.append(r.getName()+",");
            }
            String rolesName  =  stringBuffer.toString();
            approver.setRolesName(rolesName.substring(0,rolesName.length()-1));

        }


        map.put("approvers", approvers);
        return "flow/submitUI";
    }

    /** �ύ���� */
    @RequestMapping("/flow_submit.action")
    public String submit(LeaveBean leave,HttpSession session) throws Exception {
        // ��װ������Ϣ
        User user = getCurrentUser(session);

        leave.setCreateTime(new Date());
        leave.setUserId(user.getId());

        // ����ҵ�񷽷�������������Ϣ�����������̿�ʼ��ת��
        workflowService.save(leave);
        workflowService.submit(leave,user.getDepartment());

        //����֪ͨ
        String approverStr= leave.getApproverid();
        String approvers [] = approverStr.split(",");

        messageService.sendMessage(leave.getId(),user.getName()+"����һ����Ϣ��Ҫ���Ĵ���",approvers);

        return "redirect:/flow_leaveList.action";// �ɹ���ת��"�ҵ������ѯ"
    }

    /** �ҵ������ѯ */
    @RequestMapping("/flow_leaveList.action")
    public String flow_leaveList(HttpSession session,Map map) throws Exception {
        User user = getCurrentUser(session);
        List<LeaveBean> list = workflowService.getLeaveListByUser(user.getId());
        map.put("leaveList", list);

        return "flow/leaveList";
    }


    /** �ҵ�����Detail������  */
    @RequestMapping("/flow_leaveDetail.action")
    public String flow_leaveDetail(Long leaveId,Map map) throws Exception {
        LeaveBean leaveBean = workflowService.getById(leaveId);
        List<LeaveApprover> leaveApprovers =  leaveApproverService.getByLeaveId(leaveBean.getId());

        User leaveUser = userService.getById(leaveBean.getUserId());

        map.put("leaveApprovers", leaveApprovers);
        map.put("leaveBean", leaveBean);
        map.put("leaveUser", leaveUser);

        List<String> list = new ArrayList<String>();
        list.add("���");
        list.add("�Ӱ�");
        list.add("����");
        list.add("����");
        list.add("���");
        list.add("��Ʒ");
        int index = leaveBean.getType();
        map.put("type", list.get(index));
        return "flow/leaveDetail";
    }



    // ===================================������===================================

    /** �����������ҵ� [��Ϣ/����] �б� */
    @RequestMapping("/flow_myMessageList.action")
    public String myMessageList(Map map,boolean disable,HttpSession session) throws Exception {

        List<Message> messagesList = messageService.getMessageList(getCurrentUser(session),disable);
        map.put("messageList", messagesList);
        return "flow/myMessageList";
    }

    /** ��������ҳ�� */
    @RequestMapping("/flow_approveUI.action")
    public String approveUI(Long messageId,Map map) throws Exception {
        Message message = messageService.getById(messageId);

        Long leaveId = message.getLeaveId();
        LeaveBean leaveBean = workflowService.getById(leaveId);

        Long userId = leaveBean.getUserId();//�����
        User approverUser= userService.getById(userId);


        map.put("message", message);
        map.put("leaveBean", leaveBean);
        map.put("approverUser", approverUser);
        return "flow/approveUI";
    }

    /** �������� */
    @RequestMapping("/flow_approve.action")
    public String approve(HttpSession session,Long messageId,LeaveApprover model) throws Exception {
        User currentUser = getCurrentUser(session);

        // ��Ϣ����
        Message message = messageService.getById(messageId);
        message.setDisable(true);
        messageService.update(message);

        //����������
        model.setUserId(currentUser.getId());
        model.setUsername(currentUser.getName());
        model.setCreateTime(new Date());
        leaveApproverService.save(model);

        // ��ٵ�����(������ or ͬ�� or �ܾ� )
        Long leaveId = model.getLeaveId();
        LeaveBean leaveBean = workflowService.getById(leaveId);
        if (model.getStatus() == LeaveApprover.STATUS_REFUSE) {
            //���һ�������˾ܾ�����ֱ�Ӿܾ�����
            leaveBean.setResult(LeaveBean.STATUS_REFUSE);
            workflowService.update(leaveBean);
        }else{ //���ȫ��ͨ����ͬ������
            int sendRequestLength = leaveBean.getApproverid().split(",").length-1; //����������
            List<LeaveApprover> leaveApprovers =  leaveApproverService.getByLeaveId(leaveBean.getId());//����������Ա
            if (leaveApprovers.size() == sendRequestLength) {
                List<Integer> tempResults = new ArrayList<Integer>();
                for (LeaveApprover approver : leaveApprovers) {
                    int status = approver.getStatus();
                    tempResults.add(status);
                }
                if (tempResults.contains(LeaveApprover.STATUS_REFUSE)) {
                    leaveBean.setResult(LeaveBean.STATUS_REFUSE);
                    workflowService.update(leaveBean);
                } else {
                    leaveBean.setResult(LeaveBean.STATUS_AGREE);
                    workflowService.update(leaveBean);
                    // �����˶�ͬ���ˣ������ʼ�����ţ�����������
                    // MainManager.sendTo(User);
                }
            }

        }
        return "redirect:/flow_myMessageList.action";// // �ɹ���ת����������ҳ��
    }

    private User getCurrentUser(HttpSession session){
        User user = (User) session.getAttribute("user");
        return user;
    }



}
