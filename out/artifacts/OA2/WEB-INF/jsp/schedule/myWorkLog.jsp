<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>我的任务</title>
    <%@ include file="/WEB-INF/jsp/common/adminLTE.jspf" %>
</head>
<body>

<div class="wrapper">
    <div class="row">
        <div class="col-sm-12">
            <section class="panel">
                <header class="panel-heading"> 历史记录 <span class="tools pull-right"><a href="javascript:;" class="fa fa-chevron-down"></a> </span> </header>
                <div class="panel-body">
                    <table class="table table-hover general-table">
                        <thead>
                        <tr>
                            <th class="hidden-phone hidden-xs">任务期限</th>
                            <th>天数</th>
                            <th>状态</th>
                            <th>结果</th>
                        </tr>
                        </thead>
                        <tbody>

                        <c:if test="${fn:length(scheduleList ) == 0 }">
                            <tr style="text-align: center">
                                <td colspan="6">暂无申请</td>
                            </tr>
                        </c:if>

                        <c:forEach var="schedule" items="${scheduleList }">
                            <tr>
                                <td class="hidden-phone hidden-xs">${schedule.createTime}</td>
                                <td>${schedule.days }天 </td>
                                <td>  <span class="label label-success label-mini">正常</span>  </td>
                                <td> <span class="label label-warning label-mini">${schedule.result==2?"未完成":(schedule.result==1?"完成":"处理中")}</span>  </td>
                                    <%--<td><div class="js-selectuserbox"> <a href='javascript:;' title='lock'><img class='gray' src='/static/img/avatar/1.jpg' alt='lock'>未处</a><span>..........</span><a href='javascript:;' title='张三'><img class='gray' src='/static/img/avatar/3.jpg' alt='张三'>未处</a><span>..........</span><a href='javascript:;' title='李浩'><img class='gray' src='/static/img/avatar/1.jpg' alt='李浩'>未处</a> </div></td>--%>
                            </tr>
                        </c:forEach>

                        </tbody>

                    </table>

                </div>
            </section>
        </div>
    </div>
</div>

</body>
</html>