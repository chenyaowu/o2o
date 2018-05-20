package com.chen.o2o.util;

import com.chen.o2o.dto.ImageHolder;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

//图片处理工具类
public class ImgUtil {
    private static String  basePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    private static final Random r = new Random();
    //处理缩略图
    public static String generateThumbnail(ImageHolder imageHolder, String targetAddr){
        String realFileName = getRandomFileName();
        String extension = getFileExtension(imageHolder.getImageName());
        makeDirPath(targetAddr);
        String relativeAddr = targetAddr + realFileName +extension;
        File dest = new File(PathUtil.getImgBasePath()+relativeAddr);
        try {
            Thumbnails.of(imageHolder.getImage()).size(200,200)
                    .watermark(Positions.BOTTOM_RIGHT,ImageIO.read(new File(basePath+"1.png")),0.25f)
                    .outputQuality(0.8f).toFile(dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  relativeAddr;
    }
    /*
    //创建目标路径所涉及到的目录,即/home/work/xiangce/xxx.jpg
        那么 home work xiangce 这三个文件夹都要自动创建
     */
    private static void makeDirPath(String targetAddr) {
        String realFilePath = PathUtil.getImgBasePath()+targetAddr;
        File dirPath = new File(realFilePath);
        if(!dirPath.exists()){
            dirPath.mkdirs();
        }

    }
    /*
    获取输入文件流的扩展名
     */
    private static String getFileExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }
    /*
    随机生成文件名,当前年月日时分秒+5位随机数
     */
    public static String getRandomFileName() {
        int rannum = r.nextInt(89999)+1000;
        String nowTimeStr = simpleDateFormat.format(new Date());
        return  nowTimeStr+rannum;
    }
    /*
    storePath 是文件的路径还是目录路径
    如果是文件路径则删除文件
    如果是目录则删除目录
     */
    public static void deleteFile(String storePath){
        File fileOrPath = new File(PathUtil.getImgBasePath()+storePath);
        if(fileOrPath.exists()){
            if(fileOrPath.isDirectory()){
                File [] files = fileOrPath.listFiles();
                for (int i =0;i<files.length;i++)
                    files[i].delete();
            }
            fileOrPath.delete();
        }
    }



    //处理详情图
    public static String generateNormalThumbnail(ImageHolder imageHolder, String targetAddr) {
        String realFileName = getRandomFileName();
        String extension = getFileExtension(imageHolder.getImageName());
        makeDirPath(targetAddr);
        String relativeAddr = targetAddr + realFileName +extension;
        File dest = new File(PathUtil.getImgBasePath()+relativeAddr);
        try {
            Thumbnails.of(imageHolder.getImage()).size(337,640)
                    .watermark(Positions.BOTTOM_RIGHT,ImageIO.read(new File(basePath+"1.png")),0.25f)
                    .outputQuality(0.9f).toFile(dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  relativeAddr;
    }
}
