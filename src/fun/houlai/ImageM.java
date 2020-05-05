package fun.houlai;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;

import java.io.*;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class ImageM {
    static Map qwe(String path) {
        System.out.println("获取信息");
        File file = new File(path);
        Map map = new HashMap();
        try {
            Metadata metadata = ImageMetadataReader.readMetadata(file);
            for (Directory directory : metadata.getDirectories()){
                for (Tag tag : directory.getTags()){
                    String tagName = tag.getTagName();  //标签名
                    String desc = tag.getDescription(); //标签信息
                    switch (tagName){
                        case "IExif Image Height":
                            map.put("高度",desc);
                            continue;
                        case "Exif Image Width":
                            map.put("宽度",desc);
                            continue;
                        case "Date/Time":
                            map.put("拍摄时间",desc);
                            continue;
                        case "GPS Latitude":
                            map.put("纬度",desc);
                            map.put("纬度x",pointToLatlong(desc));
                            continue;
                        case "GPS Longitude":
                            map.put("经度",desc);
                            map.put("经度x",pointToLatlong(desc));
                            continue;
                        case "Software":
                            map.put("软件",desc);
                            continue;
                        case "Make":
                            map.put("设备厂家",desc);
                            continue;
                        case "Model":
                            map.put("设备型号",desc);
                            continue;
                        case "Color space":
                            map.put("色域",desc);
                            continue;
                        case "Resolution Unit":
                            map.put("分辨率单位",desc);
                            continue;
                        case "Y Resolution":
                            map.put("Y轴分辨率",desc);
                            continue;
                        case "X Resolution":
                            map.put("X轴分辨率",desc);
                            continue;
                        case "GPS Img Direction":
                            map.put("GPS方向",desc);
                            continue;
                        case "GPS Latitude Ref":
                            map.put("GPS纬度参考",desc);
                            continue;
                        case "File Size":
                            map.put("文件大小",desc);
                            continue;
                        case "Detected MIME Type":
                            map.put("MIME类型",desc);
                            continue;
                        case "Flash":
                            map.put("闪光灯",desc);
                            continue;
                        case "Blue TRC":
                            continue;
                        case "File Name":
                            continue;
                        case "Green TRC":
                            continue;
                        case "Red TRC":
                            continue;
                        default:
                            map.put(tagName,desc);
                    }

                }
            }
            return  map;
        } catch (ImageProcessingException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    };

    public static String pointToLatlong (String point ) {
        Double du = Double.parseDouble(point.substring(0, point.indexOf("°")).trim());
        Double fen = Double.parseDouble(point.substring(point.indexOf("°")+1, point.indexOf("'")).trim());
        Double miao = Double.parseDouble(point.substring(point.indexOf("'")+1, point.indexOf("\"")).trim());
        Double duStr = du + fen / 60 + miao / 60 / 60 ;
        return duStr.toString();
    }

    public static Map GenerateImage(String imgStr,String fileName)
    {//对字节数组字符串进行Base64解码并生成图片
        System.out.println("转换file");
        if (imgStr == null) //图像数据为空
            return null;
        try
        {
            //Base64解码
            byte[] b = Base64.getDecoder().decode(imgStr.split(",")[1]);
         //   String b = new String(Base64.getDecoder().decode(imgStr.split(",")[1].replace("\r\n", "")),"utf-8");
            for(int i=0;i<b.length;++i)
            {
                if(b[i]<0)
                {//调整异常数据
                    b[i]+=256;
                }
            }
            //生成jpeg图片
            String imgFilePath = fileName;//新生成的图片
//            File file= File.createTempFile("fileName","jpg");
//           BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(imgFilePath)));
            OutputStream out = new FileOutputStream(imgFilePath);
            out.write(b);
            out.flush();
            out.close();
            File file = new File(imgFilePath);
            System.out.println(file.getName());
            return qwe(imgFilePath);

        }
        catch (Exception e)
        {
            System.out.println("生成图片失败："+e);
            return null;
        }
    }
}
//@lu 1270123648@qq.com
