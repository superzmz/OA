
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>薪资管理</title>
    <%@ include file="/WEB-INF/jsp/common/commons.jspf" %>
</head>
<body>
<div id="Title_bar">
    <div id="Title_bar_Head">
        <div id="Title_Head"></div>
        <div id="Title"><!--页面标题-->
            <img border="0" width="13" height="13" src="${pageContext.request.contextPath}/style/images/title_arrow.gif"/> 薪资管理
        </div>
        <div id="Title_End"></div>
    </div>
</div>
<div id="MainArea">
    <table cellspacing="0" cellpadding="0" class="TableStyle">
        <!--表头-->
        <thead>
        <tr align="center" valign="middle" id="TableTitle">
            <td width="100">登录名</td>
            <td width="100">姓名</td>
            <td width="100">所属部门</td>
            <td width="100">岗位</td>
            <td width="100">日结薪资</td>
            <td width="100">打卡天数</td>
            <td>相关操作</td>
        </tr>
        </thead>
        <!--显示数据列表-->
        <tbody id="TableData" class="dataContainer" datakey="userList">

        <c:forEach var="user" items="${pageBean.recordList}">
            <tr class="TableDetail1 template" style="text-align: center;">
                <td>${user.loginName}&nbsp;</td>
                <td>${user.name}&nbsp;</td>
                <td>${user.department.name}&nbsp;</td>
                <td>
                    <c:forEach var="role" items="${user.roles}">
                        ${role.name}
                    </c:forEach>
                </td>
                <td>${user.salary}</td>
                <td>${user.count}</td>
                <td>
                    <a href="salary_settingUI.action?userId=${user.id}">薪资修改</a>
                    <a href="salary_settleUI.action?userId=${user.id}">薪资结算</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <!-- 其他功能超链接 -->

</div>
<!-- 分页信息 -->
<%@ include  file="/WEB-INF/jsp/common/pageView.jspf"%>
<form action="salary_list.action" method="post"/>
</body>
</html>
