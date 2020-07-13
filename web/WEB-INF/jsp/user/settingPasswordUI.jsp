
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>修改密码</title>
    <%@ include file="/WEB-INF/jsp/common/commons.jspf"%>
    <%@ include file="/WEB-INF/jsp/common/bootstrap.jspf"%>
    <style>
        body {
            padding: 16px;
            background-color: #F3F9FD;
            color: #004779;
            font-size: 12px;
            font-family: 宋体;
            scrollbar-base-color: #dbecf8;
            scrollbar-arrow-color: #2a8ed1;
            scrollbar-track-color: #bdddf2;
            scrollbar-3dlight-color: #2886c5;
            scrollbar-darkshadow-color: #2886c5;
            scrollbar-face-color: #dbecf8;
            scrollbar-shadow-color: #dbecf8;
            margin-left: 1px;
            margin-right: 1px;
            margin-top: 0;
        }

        .ItemBlockBorder {
            padding-top: 16px;
            width: 90%;
            text-align: left;
            border: 1px solid #91c0e3;
        }


    </style>
</head>
<body>
    <div class="container-fluid ItemBlockBorder">
        <div class="row-fluid">
            <div class="span12">
                <form class="form-horizontal" action="/user_settingPassword.action" method="post">
                    <div class="control-group">
                        <div class="controls">
                            <h6>密码设置</h6>
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label" for="password">密&nbsp;&nbsp;码</label>
                        <div class="controls">
                            <input id="password" type="text" name="password" placeholder="请输入你要重新设置的密码！"/>
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label" for="password1">确认密码</label>
                        <div class="controls">
                            <input id="password1" type="text" name="password1" placeholder="请再次输入你要设置的密码！">
                        </div>
                    </div>
                    <div class="control-group">
                        <div class="controls">
                            <button type="submit" class="btn">确认提交</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</body>
</html>
