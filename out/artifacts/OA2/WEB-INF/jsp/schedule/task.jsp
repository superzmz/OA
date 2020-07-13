<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>分配任务页面</title>
    <%@ include file="/WEB-INF/jsp/common/adminLTE.jspf" %>
    <link href="${pageContext.request.contextPath}/static/datepicker-custom/custom.css"
          rel="stylesheet"/>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/datepicker-custom/datepicker-custom.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/js/wj.js"></script>


</head>
<body>

<div class="wrapper">
    <div class="row">
        <div class="col-lg-12">
            <section class="panel">
                <header class="panel-heading"></header>
                <div class="panel-body">
                    <div class="alert alert-block alert-danger fade in">
                        <button type="button" class="close close-sm" data-dismiss="alert"><i class="fa fa-times"></i>
                        </button>
                    </div>
                    <form action="task.action" class="form-horizontal adminex-form" id="leave-form">
<%--                        <div class="form-group">--%>
<%--                            <label class="col-sm-2 col-sm-2 control-label"><span>*</span>类型</label>--%>
<%--                            <div class="col-sm-10">--%>
<%--                                <select name="type" class="form-control">--%>
<%--                                    <option value="">请选择类型</option>--%>
<%--                                    <option value="1">事假</option>--%>
<%--                                    <option value="2">病假</option>--%>
<%--                                    <option value="3">年假</option>--%>
<%--                                    <option value="4">调休</option>--%>
<%--                                    <option value="5">婚假</option>--%>
<%--                                    <option value="6">产假</option>--%>
<%--                                    <option value="7">陪产假</option>--%>
<%--                                    <option value="8">路途假</option>--%>
<%--                                    <option value="9">其他</option>--%>
<%--                                </select>--%>
<%--                            </div>--%>
<%--                        </div>--%>
                        <div class="form-group">
                            <label class="col-sm-2 col-sm-2 control-label"><span>*</span>任务期限</label>
                            <div class="col-sm-10">
                                <div class="input-group input-large custom-date-range" data-date="${currentDate}"
                                     data-date-format="yyyy-mm-dd">
                                    <input type="text" class="form-control dpd1" name="startTime" placeholder="开始日期"
                                           value="">
                                    <span class="input-group-addon">To</span>
                                    <input type="text" class="form-control dpd2" name="endTime" placeholder="结束日期"
                                           value="">
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 col-sm-2 control-label">完成天数</label>
                            <div class="col-sm-10">
                                <input type="number" name="days" value="0" class="form-control" placeholder="请输入数字">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 col-sm-2 control-label"><span>*</span>工作任务</label>
                            <div class="col-sm-10">
                                <textarea name="workLog" placeholder="请输入工作安排！" style="height:200px;"
                                          class="form-control"></textarea>
                            </div>
                        </div>


                        <div class="form-group">
                            <label class="col-sm-2 col-sm-2 control-label">分配的员工(点击头像可删除)</label>
                            <div class="col-sm-10 js-selectuserbox"> <a class="addAvatar" href="#acceptModal" data-toggle="modal"><i class="fa fa-plus-circle"></i></a> </div>
                        </div>

                        <div class="form-group">
                            <label class="col-lg-2 col-sm-2 control-label"></label>
                            <div class="col-lg-10">
                                <input type="hidden" name="staffid" id="staffid" value="">
                                <input type="hidden" name="id" id="schedeleid" value="0">
                                <button type="submit" class="btn btn-primary">提交保存</button>
                            </div>
                        </div>
                    </form>
                </div>
            </section>
        </div>
    </div>
</div>


<!--Dialog -->

<div aria-hidden="true" aria-labelledby="acceptModalLabel" role="dialog" tabindex="-1" id="acceptModal" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">分配员工</h4>
            </div>
            <div class="modal-body">
                <ul class="list-unstyled">
                    <c:forEach var="staff" items="${staffs}">
                        <li><a href="javascript:;" data-id="${staff.id}" data-name="${staff.name}" class="js-selectuser"><img src="${pageContext.request.contextPath}/static/images/ic_profle.png">${staff.name}（${staff.rolesName}）</a></li>
                    </c:forEach>
                </ul>
            </div>
        </div>
    </div>
</div>

<style>
    .modal-body{ max-height: 470px;    overflow: auto;}
    .modal-body img{width:50px;height:50px;    border-radius: 50%;margin-right:20px;}
    .modal-body li {    margin-bottom: 6px;    border-bottom: 1px #ddd solid;    padding-bottom: 6px;}
    .modal-body a{display:block;    text-decoration: none;}
</style>
<script>
    $(function () {

        $('.js-selectuser').on('click', function(){
            var that = $(this);
            var userid = that.attr('data-id');
            var realname = that.attr('data-name');
            var avatar = that.find('img').attr('src');

            var staffid = $('#staffid').val();
            if(staffid.indexOf(userid) > 0 ){
                $('#acceptModal').modal('hide')
                dialogInfo('该员工已经添加过');
                return false;
            }

            var html = '';
            html += '<a href="javascript:;" data-id="'+userid+'"><img src="'+avatar+'" width="33" height="33"><span>'+realname+'</span></a><span>..........</span>';
            if ($('.js-selectuserbox').find('a img').size()) {
                $('.addAvatar').before(html);
            } else {
                $('.js-selectuserbox').prepend(html);
            }
            $('#staffid').val(staffid+','+userid);//把所有approve审批人追加，逗号分隔

            $('#acceptModal').modal('hide')
        });

        $('.js-selectuserbox').delegate('a img', 'click',function(){
            var that = $(this);

            var staffid = $('#staffid').val();
            var userid = that.parent().attr('data-id');
            result = staffid.replace(eval("/,?"+userid+",?/"),' ').trim(' ').replace(' ',',');
            $('#staffid').val(result);

            that.parent().next('span').remove();
            that.parent().remove();
        });


        $('.addAvatar').on('show.bs.modal', function (e) {
        })
    });
</script>
<!--Dialog -->


<!-- DatePicker -->
<script>
    $(function () {
        var nowTemp = new Date();
        var now = new Date(nowTemp.getFullYear(), nowTemp.getMonth(), nowTemp.getDate(), 0, 0, 0, 0);

        var checkin = $('.dpd1').datepicker({
            format: 'yyyy-mm-dd',
            onRender: function (date) {
                return date.valueOf() < now.valueOf() ? 'disabled' : '';
            }
        }).on('changeDate', function (ev) {
            if (ev.date.valueOf() > checkout.date.valueOf()) {
                var newDate = new Date(ev.date)
                newDate.setDate(newDate.getDate() + 1);
                checkout.setValue(newDate);
            }
            checkin.hide();
            $('.dpd2')[0].focus();
        }).data('datepicker');
        var checkout = $('.dpd2').datepicker({
            format: 'yyyy-mm-dd',
            onRender: function (date) {
                return date.valueOf() <= checkin.date.valueOf() ? 'disabled' : '';
            }
        }).on('changeDate', function (ev) {
            checkout.hide();
        }).data('datepicker');
    })
</script>
<!-- DatePicker -->

</body>
</html>
