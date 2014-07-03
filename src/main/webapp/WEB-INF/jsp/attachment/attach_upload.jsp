<%@page import="com.gusi.boms.model.Attachment"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page language="java" pageEncoding="UTF-8" %>


<div class="mb_10 upload">
	<span class="attach_lnk in_block">
		<a href="#" class="upload_lnk bold upload_attach" attachList="attachList_${param.attachType}" type="${param.attachType}" >上传</a>
	</span>
</div>
<%
	String attachType = request.getParameter("attachType");
	List<Attachment> attachmentList = (List<Attachment>)request.getAttribute("attachList");
	List<Attachment> attachLst = new ArrayList<Attachment>();
	for(Attachment attachment : attachmentList){
		if(attachment.getAttachType().equalsIgnoreCase(attachType)){
			attachLst.add(attachment);
		}
	}
%>
<div class="file_list" id="attachList_${param.attachType}">
	  <%
			for(Attachment attachment : attachLst){
		%>
		<div class="attThumbWrap">
            <div class="attThumbWrapInner">
                <a class="attach_thumbnail" style="background:url('<%= attachment.getSmallAttachPath() %>') no-repeat center center;"
                    href="/attachment/<%= attachType %>/preview/<%=attachment.getUserCode() %>" target="_blank" >
                </a>
                <div class="op"><a href="#" class="red true_del"  type="<%=attachType %>" pic_id = "<%=attachment.getId() %>">删除</a></div>
		    </div>
            <div class="detail">
                <a target="_blank" href="/attachment/<%= attachType %>/preview/<%=attachment.getUserCode() %>"><%=attachment.getAttachFilename() %></a>
            </div>
        </div>
		<%
			}
		%>

	<input type="hidden" id="attach_${param.attachType}" name="attach_${param.attachType}" value="${param.attachType}" />

</div>
