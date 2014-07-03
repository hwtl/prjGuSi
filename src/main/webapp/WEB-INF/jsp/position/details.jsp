<%@ page language="java"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/_includes.jsp" %>

<jsp:include page="/WEB-INF/jsp/common/_head.jsp">
    <jsp:param name="title" value="岗位详情-岗位管理"/>
    <jsp:param name="css" value="position/fronterp"/>
</jsp:include>
	 <div class="right">
       <span id="operateSpan_${position.id}">
            <appel:checkPrivilege url="oms_om_position_add">
                <a href="/position/${position.id}/edit" class="btnOpH24 h24Silver in_block ml_5 mb_5">编辑</a>
            </appel:checkPrivilege>
            <appel:checkPrivilege url="oms_om_position_switch">
                <c:choose>
                    <c:when test="${position.status == 1}">
                        <a href="javascript:;" positionId="${position.id}" positionName="${position.positionName}" class="btnOpH24 h24Red in_block ml_5 mb_5 js_closePositionStepOne">停用</a>
                    </c:when>
                    <c:when test="${position.status == 0}">
                        <a href="javascript:;" positionId="${position.id}" positionName="${position.positionName}" class="btnOpH24 h24Green in_block ml_5 mb_5 js_openPositionStepOne">启用</a>
                    </c:when>
                </c:choose>
            </appel:checkPrivilege>
        </span>
        </div>
		<table border="0" cellpadding="0" cellspacing="0" class="tableFormNew mt_15">
			<tbody>
				<tr>
					<td class="tdTitle" width="160">岗位名称：</td>
					<td class="request" width="20">&nbsp;</td>
					<td width="250" class="bdR">${position.positionName}</td>
					<td class="tdTitle" width="140">岗位英文名称：</td>
					<td class="request" width="20">&nbsp;</td>
					<td width="250">${position.ePositionName}</td>
				</tr>
                <tr>
                    <td class="tdTitle">类型：</td>
                    <td class="request">&nbsp;</td>
                    <td class="bdR">${position.positionType}</td>
                    <td colspan="3">&nbsp;</td>
                </tr>
                <c:forEach var="titleSerial" items="${titleSerialList}">
                    <tr>
                        <td class="tdTitle">${titleSerial.serialName }：</td>
                        <td class="request">&nbsp;</td>
                        <td class="bdR" colspan="4">
                            <ul>
                            <c:forEach var="title" items="${titleList}">
                                <li>
                                <c:if test="${title.serialId == titleSerial.id}">
                                    <apptl:checkbox checkName="titleName" newValue="${title.id}" checkValue="${positionTitles}" checkText="${title.titleName}" disabled="true"/>
                                </c:if>
                                </li>
                            </c:forEach>
                            </ul>
                        </td>
                    </tr>
                </c:forEach>
			</tbody>
		</table>
<appel:checkPrivilege url="oms_om_position_switch">
<!--停用弹层 begin -->
<div class="popLayer" id="layer_close_position_no">
    <div class="popTitle clearfix">
        <h1>停用岗位</h1>
        <div class="cls"><a href="javascript:;" class="xx" onclick="hidePopupDiv($('#layer_close_position_no')); return false"></a></div>
    </div>
    <div class="popCon">
        <div class="form">
            <div>该岗位下还有员工，不能停用。若要停用请转移员工到其他岗位。</div>
        </div>
    </div><br><br>
    <div class="popBtn">
        <a href="javascript:;" class="btnOpH24 h24Blue in_block" onclick="hidePopupDiv($('#layer_close_position_no')); return false">确定</a>
    </div>
</div>
<!--停用弹层 end -->

<!--停用弹层 begin -->
<div class="popLayer" id="layer_close_position_ok">
    <div class="popTitle clearfix">
        <h1>停用岗位</h1>
        <div class="cls"><a href="javascript:;" class="xx" onclick="hidePopupDiv($('#layer_close_position_ok')); return false"></a></div>
    </div>
    <div class="popCon">
        <div class="form">
            <div id="closeInfo">确定停用“交互设计”岗位？</div>
            <input type="hidden" id="closeId">
        </div>
    </div><br><br>
    <div class="popBtn">
        <a href="javascript:;" class="btnOpH24 h24Blue in_block js_closePositionStepTwo">提交</a>
    </div>
</div>
<!--停用弹层 end -->

<!--启用弹层 begin -->
<div class="popLayer" id="layer_open_position_ok">
    <div class="popTitle clearfix">
        <h1>启用岗位</h1>
        <div class="cls"><a href="javascript:;" class="xx" onclick="hidePopupDiv($('#layer_open_position_ok')); return false"></a></div>
    </div>
    <div class="popCon">
        <div class="form">
            <div>确定启用“<span id="openInfo">行政事务</span>”岗位？</div>
            <input type="hidden" id="openId">
        </div>
    </div><br><br>
    <div class="popBtn">
        <a href="javascript:;" class="btnOpH24 h24Blue in_block js_openPositionStepTwo" onclick="openPosition(); return false">确定</a>
    </div>
</div>
<!--启用弹层 end -->
</appel:checkPrivilege>

<jsp:include page="/WEB-INF/jsp/common/_foot.jsp">
    <jsp:param name="duiJs" value="validation"/>
    <jsp:param name="js" value="portal,position/jobManage_common"/>
</jsp:include>

<script type="text/javascript">

    <appel:checkPrivilege url="oms_om_position_switch">
    $(function(){

        //停用岗位弹层
        $("a.js_closePositionStepOne").live('click', function(){
            var self = $(this);
            ajaxGet({
                url: '/position/' + self.attr('positionId') + '/countEmployee',
                ok:function(data){
                    $('#closeInfo').text('确定停用“' + self.attr('positionName') + '”岗位？');
                    $('#closeId').val(self.attr('positionId'));
                    showPopupDiv($('#layer_close_position_ok'));
                },
                fail:function(data){
                    showPopupDiv($('#layer_close_position_no'));
                }
            });
        });

        //停用岗位
        $("a.js_closePositionStepTwo").live('click', function(){
            ajaxGet({
                url: '/position/' +  $('#closeId').val() + '/close',
                ok:function(data){
                    $("#operateSpan_" + $('#closeId').val()).html(data.operateStr);
                },
                fail:function(data){
                    alert(data.message);
                }
            });
            hidePopupDiv($('#layer_close_position_ok'));
        });

        //启用岗位弹层
        $("a.js_openPositionStepOne").live('click', function(){
            var self = $(this);
            $('#openInfo').text(self.attr('positionName'));
            $('#openId').val(self.attr('positionId'));
            showPopupDiv($('#layer_open_position_ok'));
        });

        //启用岗位
        $("a.js_openPositionStepTwo").live('click', function(){
            ajaxGet({
                url: '/position/' +  $('#openId').val() + '/open',
                ok:function(data){
                    $("#operateSpan_" + $('#openId').val()).html(data.operateStr);
                },
                fail:function(data){
                    alert(data.message);
                }
            });
            hidePopupDiv($('#layer_open_position_ok'));
        });

    })
    </appel:checkPrivilege>

</script>