package com.gusi.boms.controller;

import com.gusi.base.controller.BomsBaseController;
import com.gusi.boms.common.Configuration;
import com.gusi.boms.helper.AttachHelper;
import com.gusi.boms.model.Attachment;
import com.gusi.boms.service.AttachmentService;
import com.gusi.boms.util.FileUtil;
import com.dooioo.plus.oms.dyHelper.CompanyHelper;
import com.dooioo.plus.oms.dyHelper.StringBuilderHelper;
import com.dooioo.web.common.WebUtils;
import com.dooioo.web.helper.JsonResult;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Title:  com.dooioo.boms.controller
 * Author: Jerry.hu
 * Create: Jerry.hu (2013-05-30 11:51)
 * Description: To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping(value = "/attachment")
public class AttachmentController extends BomsBaseController{
    @Autowired
    AttachmentService attachmentService;
    /**
     * 相关材料上传
     * @param request  客户端的请求
     * @param response 服务器响应
     * @throws java.io.IOException
     */
    @RequestMapping(value = "/upload/{userCode}",method = RequestMethod.POST)
    public @ResponseBody
    void uploadPicture(@PathVariable int userCode,HttpServletRequest request,HttpServletResponse response) throws IOException {
        MultipartHttpServletRequest multipartRequest  =  (MultipartHttpServletRequest) request;
        MultipartFile attachFile =  multipartRequest.getFile("attachFile");
        uploadFile(attachFile, userCode, multipartRequest, response);
    }
    /**
     * @param userCode
     * @param request
     * @param response
     * @throws IOException
     *  黑名单上传附件
     */
    @RequestMapping(value = "/black/upload/{userCode}",method = RequestMethod.POST)
    public @ResponseBody
    void uploadBlackPicture(@PathVariable int userCode,HttpServletRequest request,HttpServletResponse response) throws IOException {
        MultipartHttpServletRequest multipartRequest  =  (MultipartHttpServletRequest) request;
        MultipartFile attachFile =  multipartRequest.getFile("file");
        uploadFile(attachFile, userCode, multipartRequest, response);
    }
    private void uploadFile(MultipartFile attachFile,Integer userCode,HttpServletRequest request,HttpServletResponse response) throws IOException
    {
    	 String uploadingId = WebUtils.findParamStr(request, "uploadingId");
         String attachType = WebUtils.findParamStr(request,"attachType");
         Attachment attachment = new Attachment(userCode,attachType,this.getSesionUser(request).getUserCode(),this.getSesionUser(request).getUserName());
         String previewUrl=Configuration.getInstance().getAttachPreviewUrl(CompanyHelper.getDomainCompany(request));
         JSONObject json = new JSONObject();
         attachment = attachmentService.uploadAttach(attachFile, request,attachment);
         attachment.setAttachPreviewUrl(previewUrl);
         json.put("attachment",attachment);
         json.put("status","ok");
         json.put("uploadingId",uploadingId);
         try {
             response.setContentType("text/html;charset=utf-8");
             response.getWriter().write(json.toString());
         }catch(Exception e){
             e.printStackTrace();
         }
    }
    /**
     * @param userCode
     * @param request
     * @param response
     * @throws IOException
     *  黑名单上传附件
     */
    @RequestMapping(value = "/{attachId}/delete",method = RequestMethod.POST)
    public @ResponseBody
    JsonResult deleteAttachById(@PathVariable(value="attachId")int attachId,HttpServletResponse response) throws IOException {
       return ok().put("result",attachmentService.deleteById(attachId));
    }
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public @ResponseBody JsonResult save(HttpServletRequest request){
        String addAttachIds = WebUtils.findParamStr(request, "addAttachIds"); //获取上传的文件id
        String delAttachIds = WebUtils.findParamStr(request,"delAttachIds"); //获取需要删除的文件id
        attachmentService.updateAttach(addAttachIds,delAttachIds);
        return ok();
    }
    /**
     * @param id
     * @param model
     * @return 返回预览页面
     */
    @RequestMapping(value="/{attachType}/preview/{userCode}")
    public String previewOne(@PathVariable("attachType") Integer attachType,
                             @PathVariable("userCode") Integer userCode,Model model,HttpServletRequest request,HttpServletResponse response)
    {
        Map<String,Object> params=new HashMap<>();
        params.put("attachType", attachType);
        params.put("userCode",userCode);
        StringBuilder builder=new StringBuilder(200);
        String previewUrl=Configuration.getInstance().getAttachPreviewUrl(CompanyHelper.getDomainCompany(request));
        List<Attachment> paths=attachmentService.findAttachmentPaths(params);
        if (paths != null && !paths.isEmpty()) {
            String currentPath=paths.get(0).getAttachPath();
            //如果当前预览的第一张图片是图像，则将其他图片也显示出来
            if (AttachHelper.isImage(currentPath)) {
                int count=0;
                for (Attachment attachment : paths) {
                	attachment.setAttachPreviewUrl(previewUrl);
                    if (AttachHelper.isImage(attachment.getAttachFilename())) {
                        builder.append(previewUrl+attachment.getAttachPath()).append("|");
                        count++;
                    }
                }
                model.addAttribute("count",count);
                model.addAttribute("attachs", StringBuilderHelper.trimLast(builder, "|"));
                return "/attachment/attach_preview";
            }else
            {
                //如果第一个文件名不是图片，则下载
                String mime=getFileMIME(currentPath);
                download(response, currentPath.replace("/","_"),previewUrl+currentPath, mime);
            }
        }
        return null;
    }
    @RequestMapping(value="/{id}/preview")
    public String previewTemp( @PathVariable("id") int id,Model model,HttpServletRequest request,HttpServletResponse response){
        Attachment attachment = attachmentService.findById(id);
        if(attachment == null){
            return error(model,"对不起,此附件已经不存在，请联系管理员");
        }
        String previewUrl=Configuration.getInstance().getAttachPreviewUrl(CompanyHelper.getDomainCompany(request));
        attachment.setAttachPreviewUrl(previewUrl);
        if (AttachHelper.isImage(attachment.getAttachPath())) {
            model.addAttribute("count",1);
            model.addAttribute("attachs",previewUrl+
            		attachment.getAttachPath());
            return "/attachment/attach_preview";
        }else
        {
            //如果第一个文件名不是图片，则下载
            String mime=getFileMIME(attachment.getAttachPath());
            download(response, attachment.getAttachPath(),previewUrl+attachment.getAttachPath(), mime);
        }
        return null;
    }
    /**
     * @param model
     * @param request
     * @return 查看原图
     */
    @RequestMapping("/original")
    public String previewOriginal(Model model,HttpServletRequest request)
    {
        model.addAttribute("attach", request.getParameter("attach"));
        return "/attachment/original_view";
    }

    /**
     * @param fileName
     * @return 获取文件的mime
     */
    private String getFileMIME(String fileName)
    {
        String ext= FileUtil.getFileSuffixes(fileName).toLowerCase();
        if (ext.equals(".txt")) {
            return "text/plain";
        }else if (ext.startsWith(".doc") ||ext.equals(".wps")) {
            return "application/msword";
        }else if (ext.startsWith(".xls")) {
            return "application/vnd.ms-excel";
        }else if (ext.equals(".pdf")) {
            return "application/pdf";
        }else
        {
            return "text/plain";
        }
    }

    /**
     * @param response
     * @param fileName
     * 下载文件
     */
    private void download(HttpServletResponse response,String fileName,String filePath,String mime)
    {
        java.io.BufferedInputStream bis = null;
        java.io.BufferedOutputStream bos = null;
        try {
            URL url=new URL(filePath);
            response.setContentType(mime);
            response.setHeader("Content-disposition", "attachment; filename="+fileName);
            bis = new BufferedInputStream(url.openStream());
            bos = new BufferedOutputStream(response.getOutputStream());
            byte[] buff = new byte[1024];
            int bytesRead;
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length)))
            {
                bos.write(buff, 0, bytesRead);
            }
            bos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            if (bis != null)
            {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                bis=null;
            }
            if (bos != null)
            {

                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                bos=null;
            }
        }
    }
}
