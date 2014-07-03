<%@ page language="java"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/_includes.jsp" %>
<jsp:include page="/WEB-INF/jsp/common/_head.jsp">
    <jsp:param name="title" value="岗位列表-岗位管理"/>
    <jsp:param name="css" value="position/fronterp"/>
</jsp:include>
	<div class="boxWrapBlue">
		<div class="boxWrapFilter2">
			<p class="js_searchBox">
				<span class="ml_15">岗位名称：</span><input id="searchPositionName" type="text" class="txt mr_15" value="${position.positionName}"/>
				<span class="ml_15">类型：</span>
				<select id="searchPositionType">
					<option value=""  ${position.positionType == '' ? 'selected=true' : ''}>请选择类型</option>
                       <option value="销售型" ${position.positionType == '销售型' ? 'selected=true' : ''}>销售型</option>
                       <option value="非销售型" ${position.positionType == '非销售型' ? 'selected=true' : ''}>非销售型</option>
				</select>
				<span class="ml_15">职系：</span>
				<select id="searchSerialTitleInfo">
					<option value="" ${position.serialId == 0 ? 'selected=true' : ''}>请选择职系</option>
                       <c:forEach var="titleSerial" items="${titleSerialList}">
                           <option value="${titleSerial.serialName }" ${ position.serialName == titleSerial.serialName ? 'selected=true' : '' }>${titleSerial.serialName }</option>
                       </c:forEach>
				</select>
				<!--
                   <input type="checkbox" id="noDiretion" name="noDiretion" /><label for="noDiretion">缺岗位说明书</label>
				-->
                   <a href="javascript:;" class="btnOpH24 h24Blue in_block js_search">搜索</a>
                   <a href="javascript:;" class="btnOpH24 h24Silver in_block ml_5 js_reset">重置</a>
			</p>
		</div>

	</div>
	<p class="clearfix mt_10">
        <span class="grey999 left mt_10"><b class="red">${paginate.totalCount}</b>&nbsp;符合要求的岗位</span>
        <appel:checkPrivilege url="oms_om_position_add">
           <a href="/position/add" target="_blank" class="btnOpH24 h24Green in_block right js_hrefs">新增岗位</a>
        </appel:checkPrivilege>
    </p>
    <table width="100%" cellspacing="0" class="tableListNew tableNarrow mt_10">
	<tbody>
			<tr class="trHead">
				<td align="left" width="240">岗位名称</td>
                <td align="left" width="100">类型</td>
                <td align="center" width="100">在职人数</td>
                <td align="center" width="100">兼职人数</td>
                <td align="center" width="100">状态</td>
                <td align="left" width="240">职系职等</td>
				<td align="right" width="250">操作</td>
			</tr>
			<c:forEach var="position" items="${paginate.pageList}">
				<tr>
					<td align="left"><a href="/employee/list?keyWords=${position.positionName}">${position.positionName }</a></td>
                    <td align="left" >${position.positionType }</td>
                    <td align="center" >${position.employeeNo }</td>
                    <td align="center" >${position.partTimeNo }</td>
                    <td align="center" name="tr_status${position.id}">${position.statusStr }</td>
                    <td align="left">${position.serialTitleInfo}</td>
					<td align="right">
                        <appel:checkPrivilege url="oms_om_position_add">
                        <a href="/position/${position.id}/details" target="_blank" class="btnOpH24 h24Silver in_block ml_5 js_hrefs">详情</a>
                        </appel:checkPrivilege>
                        <span id="operateSpan_${position.id}">
                            <appel:checkPrivilege url="oms_om_position_add">
                            <a href="/position/${position.id}/edit" target="_blank" class="btnOpH24 h24Silver in_block js_hrefs">编辑</a>
                            </appel:checkPrivilege>
                            <appel:checkPrivilege url="oms_om_position_switch">
                               <c:choose>
                                   <c:when test="${position.status == 1}">
                                       <a href="javascript:;" positionId="${position.id}" positionName="${position.positionName}" class="btnOpH24 h24Red in_block mb_5 js_closePositionStepOne">停用</a>
                                   </c:when>
                                   <c:when test="${position.status == 0}">
                                       <a href="javascript:;" positionId="${position.id}" positionName="${position.positionName}" class="btnOpH24 h24Green in_block mb_5 js_openPositionStepOne">启用</a>
                                   </c:when>
                               </c:choose>
                            </appel:checkPrivilege>
                        </span>
                          <appel:checkPrivilege url="pms_base">
			                <a tabname="${position.positionName}_配置角色" target="_blank" href="/privilege/positionSet?key=${position.id}" class="js_hrefs btnOpH24 h24Green in_block">配角色</a>
			            </appel:checkPrivilege>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<!--分页 -->
<div class="tabConInfoBtm mt_10" style="padding:0px">
    <app:paginate/>
</div>
	<!--分页 -->
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
            <a href="javascript:;" class="btnOpH24 h24Blue in_block js_openPositionStepTwo">确定</a>
        </div>
    </div>
    <!--启用弹层 end -->
</appel:checkPrivilege>

<jsp:include page="/WEB-INF/jsp/common/_foot.jsp">
    <jsp:param name="duiJs" value="validation"/>
    <jsp:param name="js" value="portal,position/jobManage_common"/>
</jsp:include>

<script type="text/javascript">

    $(function(){

        <appel:checkPrivilege url="oms_om_position_switch">
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
                    $("td[name='tr_status" + $('#closeId').val() + "']").html("停用");
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
                    $("td[name='tr_status" + $('#openId').val() + "']").html("正常");
                    $("#operateSpan_" + $('#openId').val()).html(data.operateStr);
                },
                fail:function(data){
                    alert(data.message);
                }
            });
            hidePopupDiv($('#layer_open_position_ok'));
        });
        </appel:checkPrivilege>

        $("#searchPositionName").keyup(function(evt){	//按Enter键快速查询
            var k = window.event ? evt.keyCode : evt.which;
            if(k == 13) {
                $("a.js_search").click();
            }
        });

        //搜索
        $("a.js_search").live('click', function(){
            var url = replaceLocalUrlParams({
                positionName : $('#searchPositionName').val(),
                positionType : $('#searchPositionType').val(),
                serialName : $('#searchSerialTitleInfo').val(),
                pageNo : 1
            });
            if($('#searchPositionName').val() == '') {
                url = removeOneParam(url, "positionName")
            }
            if($('#searchPositionType').val() == '') {
                url = removeOneParam(url, "positionType")
            }
            if($('#searchSerialTitleInfo').val() == '') {
                url = removeOneParam(url, "serialName")
            }
            window.location.href = url;
        });

        //重置
        $("a.js_reset").live('click', function(){
            removeAllParams();
        });

    })

</script>