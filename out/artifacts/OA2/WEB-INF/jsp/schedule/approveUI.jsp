<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>任务页面</title>
    <%@ include file="/WEB-INF/jsp/common/adminLTE.jspf" %>

</head>
<body>

<div class="wrapper">
    <div class="row">
        <div class="col-sm-12">
            <section class="panel">
                <header class="panel-heading"> 任务委派<span class="tools pull-right"><a href="javascript:;" class="fa fa-chevron-down"></a> </span> </header>
                <div class="panel-body">
                    <table class="table table-hover general-table">
                        <thead>
                        <tr>
                            <th>任务委派人</th>
                            <th class="hidden-phone hidden-xs">任务开始时间</th>
                            <th>天数</th>
                            <th>工作安排</th>
                            <th>结果</th>
                        </tr>
                        </thead>
                        <tbody>


                        <tr>
                            <td colspan="1">${approverUser.name}</td>
                            <td colspan="1">${schedule.createTime}</td>
                            <td colspan="1">${schedule.days}</td>
                            <td colspan="1">${schedule.workLog}</td>
                            <td colspan="1">${schedule.result==2?"未完成":(schedule.result==1?"完成":"处理中")}</td>
                        </tr>

                        </tbody>

                    </table>

                </div>
            </section>
        </div>
    </div>
</div>

<div class="content">

    <div class="row">
        <!-- left column -->
        <div class="col-md-12">
            <!-- general form elements -->
            <div class="box box-primary">
                <div class="box-header with-border">
                    <h3 class="box-title">工作意见</h3>
                </div>
                <!-- /.box-header -->
                <!-- form start -->
                <form role="form" action="schedule_approve.action" method="post">
                    <div class="box-body">

                        <div class="form-group">
                            <input type="hidden" name="messageId" value="${message.id}"/>
                            <input type="hidden" name="scheduleId" value="${schedule.id}"/>
                        </div>

                        <div class="form-group">
                            <label for="summary">说明</label>
                            <textarea name="summary" id="summary"></textarea>
                            <p class="help-block">你完成任务，请确认信息后提交.</p>
                        </div>

                        <div class="form-group">
                            <input type="radio" name="status" value="1" checked/>完成 <br/>
                        </div>
                        <div class="form-group">
                            <input type="radio" name="status" value="2"/>未完成<br/>
                        </div>
                    </div>
                    <!-- /.box-body -->

                    <div class="box-footer">
                        <button type="submit" class="btn btn-primary">Submit</button>
                    </div>
                </form>
            </div>
            <!-- /.box -->

        </div>
    </div>
</div>



</body>
</html>
