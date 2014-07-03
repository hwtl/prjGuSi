package com.gusi.boms.service;

import com.gusi.boms.helper.AttachHelper;
import com.gusi.boms.model.Attachment;
import com.dooioo.web.service.BaseService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Title:  com.dooioo.boms.service
 * Author: Jerry.hu
 * Create: Jerry.hu (2013-05-29 18:46)
 * Description:员工附件业务逻辑层
 */
@Service
public class AttachmentService extends BaseService<Attachment> {

    /**
     * 保存文件到本地，并入库
     * @param attachFile 文件流
     * @param request 客户端请求
     * @param attachment  附件对象
     * @return   Attachment
     * @throws java.io.IOException
     */
    public Attachment uploadAttach(MultipartFile attachFile,HttpServletRequest request,Attachment attachment) throws IOException {
        Map<String,String> attachMap = AttachHelper.saveAttachAndResultParam(attachFile);
        attachment.setAttachFilename(attachMap.get("attachName"));
        attachment.setAttachPath(attachMap.get("attachUrl"));
        attachment.setId(this.insertAndReturnId(attachment));
        return attachment;
    }

    /**
     * 获取附件集合
     * @return
     */
    public List<Attachment> getAttachList(int userCode){
        return this.queryForList(userCode);
    }

    /**
     * 根据附件类型判断是否存在此类型的附件
     * @param attachType  附件类型
     * @param userCode    用户
     * @return  boolean
     */
    public boolean hasAttach(String attachType,int userCode) {
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userCode",userCode);
        paramMap.put("attachType",attachType);
        return this.count(sqlId("hasAttach"),paramMap) > 0;
    }

    @Transactional
    public void updateAttach(String addAttachIds, String delAttachIds) {
        if(!StringUtils.isBlank(addAttachIds)){
            Map<String,Object> addAttachMap = new HashMap<String, Object>();
            addAttachMap.put("attachIds",AttachHelper.getAttachIds(addAttachIds));
            this.update(sqlId("updateAttach"),addAttachMap);
        }
        if(!StringUtils.isBlank(delAttachIds)){
            Map<String,Object> delAttachMap = new HashMap<String, Object>();
            delAttachMap.put("attachIds",AttachHelper.getAttachIds(delAttachIds));
            this.update(sqlId("delAttach"),delAttachMap);
        }
    }
    public boolean updateValidById(Integer attachId)
    {
    	return update(sqlId("updateValidById"), attachId);
    }
    public List<Attachment> findAttachmentPaths(Map<String,Object> params){
        return  this.queryForList(sqlId("findAttachmentPaths"), params);
    }

    public List<Attachment> findByValid(){
        return this.queryForListBySqlId(sqlId("findByValid"));
    }

    public boolean deleteById(int attachId) {
        return delete(sqlId("deleteById"),attachId);
    }
}
