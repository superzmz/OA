
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>个人考勤</title>
    <%@ include file="/WEB-INF/jsp/common/commons.jspf" %>
</head>
<body>
<div id="Title_bar">
    <div id="Title_bar_Head">
        <div id="Title_Head"></div>
        <div id="Title"><!--页面标题-->
            <img border="0" width="13" height="13" src="${pageContext.request.contextPath}/style/images/title_arrow.gif"/> 个人考勤
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
            <td width="100">打卡时间</td>
            <td width="100">打卡类型</td>
            <td width="100">所属部门</td>
        </tr>
        </thead>
        <!--显示数据列表-->
        <tbody id="TableData" class="dataContainer" datakey="timecardList">
            <c:forEach var="timecard" items="${pageBean.recordList}">
                <tr class="TableDetail1 template" style="text-align: center;">
                <td>${timecard.loginName}</td>
                <td>${timecard.username}</td>
                <td>${timecard.createTime}</td>
                <td>
                    <c:if test="${timecard.type == 1}">
                        打卡成功
                    </c:if>
                    <c:if test="${timecard.type == 2}">
                        打卡失败
                    </c:if>
                </td>
                <td>${timecard.departmentName}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    <!-- 其他功能超链接 -->
    <div id="TableTail">
        <div id="TableTail_inside">
            <c:if test="${disable == 1}">
            <a href="/timecard_timeCardUI.action?userId=${user.id}">
                <button>打卡</button>
            </a>
            </c:if>
            <c:if test="${disable == 0}">
                <button>今日已打卡</button>
            </c:if>
            <c:if test="${disable == 2}">
                <button>过期打卡</button>
            </c:if>
        </div>
    </div>
</div>
<!-- 分页信息 -->
<%@ include  file="/WEB-INF/jsp/common/pageView.jspf"%>
<form action="/timecardP_list.action" method="post"/>

</body>
</html>
