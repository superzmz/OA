
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>打卡信息</title>
    <%@ include file="/WEB-INF/jsp/common/commons.jspf" %>
</head>
<body>
<!-- 标题显示 -->
<div id="Title_bar">
    <div id="Title_bar_Head">
        <div id="Title_Head"></div>
        <div id="Title"><!--页面标题-->
            <img border="0" width="13" height="13" src="${pageContext.request.contextPath}/style/images/title_arrow.gif"/> 打卡信息
        </div>
        <div id="Title_End"></div>
    </div>
</div>
<!--显示表单内容-->
<div id=MainArea>
    <form action="/timecard_timeCard.action" method="post">
        <input type="hidden" name="id" value="${user.id}"/>

        <div class="ItemBlock_Title1">
            <!-- 信息说明 -->
            <div class="ItemBlock_Title1">
                <img border="0" width="4" height="7" src="${pageContext.request.contextPath}/style/blue/images/item_point.gif" /> 用户信息
            </div>
        </div>
        <!-- 表单内容显示 -->
        <div class="ItemBlockBorder">
            <div class="ItemBlock">
                <table cellpadding="0" cellspacing="0" class="mainForm">
                    <tr><td>所属部门</td>
                        <td>
                            <input name="departmentName" class="InputStyle" value="${departmentName}"/> *</td>
                    </tr>
                    <tr><td>登录名</td>
                        <td>
                            <input name="loginName" class="InputStyle" value="${requestScope.user.loginName}"/> *
                            （登录名要唯一）
                        </td>
                    </tr>
                    <tr>
                        <td>姓名</td>
                        <td><input type="text" name="name" class="InputStyle" value="${requestScope.user.name}"/> *</td>
                    </tr>
                </table>
            </div>
        </div>
        <!-- 表单操作 -->
        <div id="InputDetailBar">
            <input type="image" src="${pageContext.request.contextPath}/style/images/save.png"/>
            <a href="javascript:history.go(-1);"><img src="${pageContext.request.contextPath}/style/images/goBack.png"/></a>
        </div>
    </form>
</div>


</body>
</html>
