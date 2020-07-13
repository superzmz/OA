<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>薪资结算</title>
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


<div class="container-fluid ItemBlockBorder ">

    <div class="row-fluid">
        <div class="span12">
            <form class="form-horizontal" action="#" method="post">

                <div class="control-group">
                    <div class="controls">
                        <h6>薪资结算</h6>
                    </div>
                </div>

                <div class="control-group">
                    <label class="control-label">&nbsp;姓名&nbsp;</label>
                    <div class="controls">
                        ${name}
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label">&nbsp;日结薪资&nbsp;</label>
                    <div class="controls">
                        ${salary}
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label">&nbsp;打卡天数&nbsp;</label>
                    <div class="controls">
                        ${count}
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label">&nbsp;迟到天数&nbsp;</label>
                    <div class="controls">
                        ${tardy}
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label">&nbsp;结算公式&nbsp;</label>
                    <div class="controls">
                        （打卡天数 * 日结薪资）-(迟到天数 * 50)
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label">&nbsp;结算结果&nbsp;</label>
                    <div class="controls">
                        ${result}
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>

</body>
</html>
