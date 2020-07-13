
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>公司考勤</title>
    <%@ include file="/WEB-INF/jsp/common/commons.jspf" %>
</head>
<body>
<div id="Title_bar">
<div id="Title_bar_Head">
    <div id="Title_Head"></div>
    <div id="Title"><!--页面标题-->
        <img border="0" width="13" height="13" src="${pageContext.request.contextPath}/style/images/title_arrow.gif"/> 公司考勤
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
            <td width="100">打卡天数</td>
            <td width="100">所属部门</td>
            <td width="100">岗位</td>
        </tr>
        </thead>
<!--显示数据列表-->
        <tbody id="TableData" class="dataContainer" datakey="userList">

        <c:forEach var="user" items="${pageBean.recordList}">
            <tr class="TableDetail1 template" style="text-align: center;">
                <td>${user.loginName}&nbsp;</td>
                <td>${user.name}&nbsp;</td>
                <td>${user.count}</td>
                <td>${user.department.name}&nbsp;</td>
                <td>
                    <c:forEach var="role" items="${user.roles}">
                        ${role.name}
                    </c:forEach>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <!-- 其他功能超链接 -->

</div>

</body>
</html>
