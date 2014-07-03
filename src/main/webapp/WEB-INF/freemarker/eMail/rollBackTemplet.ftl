<table boder="0" cellpadding="0" cellspacing="0" width="100%" class="tableListNew mt_15">
    <tbody>
    <tr class="trHead">
        <td>工号/异动id</td>
        <td>生效日期</td>
        <td>异动类型</td>
        <td>原岗位信息</td>
        <td>新岗位信息</td>
    </tr>
    <tr>
        <td>
            ${change.userCode}/${change.changeId}
        </td>
        <td>
            ${change.activeDate?string("yyyy-MM-dd")}
        </td>
        <td>${change.changeType}</td>
        <td>
            <p>
                部门：${change.oldOrgName}
                <br>职系：${change.oldSerialName}
                <br>职等职级：${change.oldTitleLevelFullName}
                <br>岗位：${change.oldPositionName}
                <br>任职状态：${change.oldStatus}
            </p>
        </td>
        <td>
            部门：${change.newOrgName}
            <br>职系：${change.newSerialName}
            <br>职等职级：${change.newTitleLevelFullName}
            <br>岗位：${change.newPositionName}
            <br>任职状态：${change.newStatus}
        <#if change.changeType=='离职'>
            <br>离职分类：${change.leaveType}
            <br>离职原因：${change.leaveReason}
        </#if>
        </td>
        <td>
            <span class="red bold">已回滚</span>
            <br>操作人：${updator},操作时间：${now?string("yyyy年M月dd日 HH:mm")}
        </td>
        <td>&nbsp;
        <td>
    </tr>
    </tbody>
</table>