<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>工作详情</title>
    <%@ include file="/WEB-INF/jsp/common/adminLTE.jspf" %>
    <script src="${pageContext.request.contextPath }/static/js/wj.js"></script>

</head>
<body>


<div class="wrapper">
    <div class="row">
        <div class="col-md-8">
            <div class="blog">
                <div class="single-blog">
                    <div class="panel">
                        <div class="panel-body">
                            <p class="text-center auth-row"> By <a href="#">${scheduleUser.name}</a> | ${schedule.createTime }</p>
                            <div id="print">
                                <table class="table table-bordered" border="1">
                                    <tr class="hide">
                                        <th colspan="6" class="text-center">任务进度</th>
                                    </tr>
                                    <tr>
                                        <td width="72">委派人</td>
                                        <td width="62">${name}</td>
                                        <td width="70">&nbsp;&nbsp;</td>
                                        <td width="77">&nbsp;&nbsp;</td>
                                        <td width="80">&nbsp;&nbsp;</td>
                                        <td width="93">&nbsp;&nbsp;</td>
                                    </tr>
                                    <tr>
                                        <td>任务</td>
                                        <td colspan="5">${schedule.workLog}</td>
                                    </tr>
                                    <tr>
                                        <td>日期</td>
                                        <td colspan="5">${schedule.startTime }&nbsp;&nbsp; 至&nbsp;&nbsp; ${schedule.endTime }</td>
                                    </tr>
                                    <tr>
                                        <td>天数</td>
                                        <td colspan="5">${schedule.days}天</td>
                                    </tr>
                                    <tr  class="hide">
                                        <td>审核人</td>
                                        <td colspan="5">&nbsp;</td>
                                    </tr>



                                </table>
                            </div>

                        </div>
                    </div>
                    <a class="btn btn-xs btn-warning" style="margin-bottom:6px;">员工进度</a>

                    <div class="panel">
                        <div class="panel-body">
                            <h1 class="text-center cmnt-head">员工</h1>
                            <c:forEach var="approver" items="${scheduleApprovers}">
                                <p class="text-center fade-txt">
                                        ${approver.username} : <span class="btn btn-xs btn-info">${approver.status == 1?"完成":"未完成"}</span><br/>
                                    <span>${approver.summary}</span><br/>
                                    <span>${approver.createTime}</span>
                                </p>
                            </c:forEach>

                        </div>
                    </div>

                </div>
            </div>
        </div>
    </div>
</div>





</body>
</html>
