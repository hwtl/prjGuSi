package com.gusi.boms.helper;

import com.gusi.boms.common.Configuration;
import com.gusi.boms.util.CommonUtils;
import com.gusi.boms.util.FileUtil;
import com.gusi.boms.util.ImageCompress;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Author: hw
 * Update: hw(2013-02-27 16:25)
 * Description: IntelliJ IDEA
 * 附件帮助类
 */
public class AttachHelper {

    private final static Log log = LogFactory.getLog(AttachHelper.class);

    public final static String PDF_IMG_PATH = "http://dui.dooioo.com/public/images/pdf.gif";
    public final static String DOC_IMG_PATH = "http://dui.dooioo.com/public/images/msword.gif";
    public final static String TXT_IMG_PATH = "http://dui.dooioo.com/public/images/txt.gif";
    public final static String XLS_IMG_PATH = "http://dui.dooioo.com/public/images/xls.gif";

    /**
     * 默认文件上传路径
     */
    public static final String FOLDER_NAME_ATTACH = "attach";
    /**
     * 社保文件上传路径
     */
    public static final String FOLDER_NAME_SI = "si";

    /**
     * 原始尺寸图片后缀
     */
    public final static String IMAGE_SUFFIX_ORIGINAL = "_original";
    /**
     * 压缩后小图片后缀
     */
    public final static String IMAGE_SUFFIX_SMALL = "_small";
    /**
     * 压缩后大图片后缀
     */
    public final static String IMAGE_SUFFIX_BIG = "_big";

    /**
     * 日期格式
     * @since: 2014-06-27 11:28:51
     */
    public static final String DATE_FORMAT_YYYYMM = "yyyyMM";

    /**
     * 最大上传文件大小(2M)
     * @since: 2014-06-27 11:29:39
     */
    public static final long UPLOAD_FILE_MAX_SIZE = 2097152;

    /**
     * 保存附件到默认的物理路径下并返回相应的参数
     * @since: 2014-06-25 10:07:41
     * @param attachFile 文件流
     * @return
     * @throws IOException
     */
    public static Map<String,String> saveAttachAndResultParam(MultipartFile attachFile) throws IOException {
        return AttachHelper.uploadFileAndResultParam(attachFile, AttachHelper.FOLDER_NAME_ATTACH);
    }

    /**
     * 保存附件到物理路径下并返回相应的参数
     * @udpateTime: 2014-06-25 10:04:12
     * @param attachFile 文件流
     * @param fileName   需要保存的文件夹位置
     * @return
     * @throws IOException
     */
    public static Map<String,String> uploadFileAndResultParam(MultipartFile attachFile, String fileName) throws IOException {
        String timePath = DateFormatUtils.format(new Date(), DATE_FORMAT_YYYYMM);
        //文件保存路径
        String path = Configuration.getInstance().getAttachPath()+File.separator+fileName + File.separator+ timePath;
        log.info("上传文件目录："+path);
        String realAttachName = null;
        //判断是否是图片进行压缩
        if(isImage(attachFile.getOriginalFilename())){
            realAttachName = AttachHelper.getLongCode()+AttachHelper.IMAGE_SUFFIX_ORIGINAL+ FileUtil.getFileSuffixes(attachFile.getOriginalFilename());
        }else{
            realAttachName = AttachHelper.getLongCode()+ FileUtil.getFileSuffixes(attachFile.getOriginalFilename());
        }
        FileUtils.copyInputStreamToFile(attachFile.getInputStream(), new File(path+ File.separator+realAttachName));
        //判断是否是图片进行压缩
        if(isImage(attachFile.getOriginalFilename())){
            ImageCompress.ImageScale(path + File.separator, realAttachName, realAttachName.replace(AttachHelper.IMAGE_SUFFIX_ORIGINAL, AttachHelper.IMAGE_SUFFIX_SMALL), Configuration.getInstance().getPicWidth(), Configuration.getInstance().getPicHight());
        }
        Map<String,String> map = new HashMap<String, String>();
        map.put("attachName",attachFile.getOriginalFilename());
        map.put("attachUrl",(File.separator + fileName + File.separator + timePath + File.separator+ realAttachName).replace("\\","/"));
        return  map;
    }

    /**
     * 上传社保附件
     * @since: 2014-06-25 11:49:13
     * @param attachFile
     * @return
     * @throws IOException
     */
    public static Map<String,String> uploadSIAttachAndResultParam(MultipartFile attachFile) throws IOException {
        //第一层文件夹/attach/si
        String folder = AttachHelper.FOLDER_NAME_ATTACH + File.separator + AttachHelper.FOLDER_NAME_SI;
        //第二次文件夹/201406
        String timePath = DateFormatUtils.format(new Date(), DATE_FORMAT_YYYYMM);
        //最终保存文件夹/flashdisk/oms/attach/si/201406
        String path = Configuration.getInstance().getAttachPath() + File.separator + folder + File.separator + timePath;
        //文件后缀.jpg
        String suffix = "." + FilenameUtils.getExtension(attachFile.getOriginalFilename());
        //新文件名称加上后缀20140627112800321.jpg
        String realAttachName = AttachHelper.getLongCode() + suffix;
        //保存文件
        attachFile.transferTo(new File(path + File.separator + realAttachName));
        //图片处理，进行压缩
        if (isImage(attachFile)) {
            //压缩小图
            ImageCompress.ImageScale(path + File.separator, realAttachName, realAttachName + IMAGE_SUFFIX_SMALL + suffix, Configuration.getInstance().getPicWidth(), Configuration.getInstance().getPicHight());
            //压缩大图（目前只拷贝一份原图）
            FileUtils.copyFile(new File(path + File.separator + realAttachName), new File(path + File.separator + realAttachName + IMAGE_SUFFIX_BIG + suffix));
        }
        //返回数据
        Map<String, String> map = new HashMap<>();
        //文件名称
        map.put("attachName", realAttachName);
        //文件访问路径
        map.put("attachUrl", (File.separator + folder + File.separator + timePath + File.separator + realAttachName).replace("\\", "/"));
        //返回数据
        return map;
    }

    /**
     * 将字符串解析为数组，eg：1，2，45，5
     * @param attachIds 被解析的字符串
     * @return  String []
     */
    public static String[] getAttachIds(String attachIds){
       return CommonUtils.getArray(attachIds);
    }

    /**
     * 是否是图片
     * @param fileName  文件名
     * @return boolean
     */
    public static boolean isImage(String fileName){
       return "image".equals(FileUtil.getFileType(fileName));
    }

    /**
     * 根据文件类型判断是否为图片格式
     * @since: 2014-06-27 11:17:09
     * @param multipartFile
     * @return
     */
    public static boolean isImage(MultipartFile multipartFile){
       return multipartFile.getContentType().startsWith("image");
    }

    /**
     * 删除附件
     * @param path 文件路径
     * @return boolean
     */
    public static boolean deleteAttach(String path){
        try {
            if(isImage(path)){
                FileUtil.deleteFile(path.replace(AttachHelper.IMAGE_SUFFIX_ORIGINAL, AttachHelper.IMAGE_SUFFIX_SMALL));
            }
            FileUtil.deleteFile(path);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 获取时间字符串
     * @since: 2014-06-25 10:51:50
     * @return
     */
    private static String getLongCode(){
        return DateFormatUtils.format(new Date(), "yyMMddHHmmssSSS");
    }

    /**
     * 获取附件的缩略图
     * @param filePath  附件的路径
     * @return   String
     */
    public static String getSmallImage(String filePath){
        String fileType = FileUtil.getFileType(filePath);
        switch (fileType) {
            case "image":
                return filePath.replace(AttachHelper.IMAGE_SUFFIX_ORIGINAL, AttachHelper.IMAGE_SUFFIX_SMALL);
            case "pdf":
                return PDF_IMG_PATH;
            case "doc":
                return DOC_IMG_PATH;
            case "txt":
                return TXT_IMG_PATH;
            case "xls":
                return XLS_IMG_PATH;
            default:
                return "";
        }
    }

}
