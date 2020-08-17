package com.zte.common.utils;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.stereotype.Component;
import java.io.*;
import java.net.MalformedURLException;


/**
 * @author yinsiwei
 * @date 2020-08-03 10:29
 */
@Component
public class FtpUtils {

/*    private static FtpUtils ftpUtils=new FtpUtils();

    @Autowired
    SysConfigService sysConfigService;

    @PostConstruct
    public void init(){
        ftpUtils.sysConfigService = sysConfigService;
    }

    public static String getSysconfig(String cfgkey) {
        return ftpUtils.sysConfigService.selectCfgvalue(cfgkey);
    }*/

    public static FTPClient ftp = null;

    //初始化FTPClient
    public static void initFtpClient() {

        String host=SystemUtils.getSysconfig("FTP.ADDRESS");
        // 端口
        int port= Integer.parseInt(SystemUtils.getSysconfig("FTP.PORT"));
        // ftp用户名
        String userName=SystemUtils.getSysconfig("FTP.USERNAME");
        // ftp用户密码
        String passWord=SystemUtils.getSysconfig("FTP.PASSWORD");


        ftp = new FTPClient();
        ftp.setControlEncoding("UTF-8");
        try {
            System.out.println("connecting...ftp服务器:" + host + ":" + port);
            ftp.connect(host, port); // 连接ftp服务器
            ftp.login(userName, passWord); // 登录ftp服务器
            int replyCode = ftp.getReplyCode(); // 是否成功登录服务器
            if (!FTPReply.isPositiveCompletion(replyCode)) {
                System.out.println("connect failed...ftp服务器:" + host + ":" + port);
            }
            System.out.println("connect successfu...ftp服务器:" + host + ":" + port);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Description: 向FTP服务器上传文件
     * @  host FTP服务器ip
     * @  port FTP服务器端口
     * @  username FTP登录账号
     * @  password FTP登录密码
     * @  basePath FTP服务器基础目录,/home/ftpuser/images
     * @param filePath FTP服务器文件存放路径。例如分日期存放：/2018/05/28。文件的路径为basePath+filePath
     * @param filename 上传到FTP服务器上的文件名
     * @param input 输入流
     * @return 成功返回path，否则返回null
     */

    public static String uploadFile(String filePath, String filename, InputStream input){
        String result = null;
        // 文件在服务器端保存的主目录
        String pathUrl=SystemUtils.getSysconfig("FTP.BASEPATH");
        initFtpClient();


            //切换到上传目录

        try{
            if(!makeDir(ftp,"/statics"+filePath)){
                return result;
            }
            //设置为被动模式
            //ftp.enterLocalPassiveMode();

            //设置主动模式
            ftp.enterLocalActiveMode();
            //设置上传文件的类型为二进制类型
            ftp.setFileType(FTP.BINARY_FILE_TYPE);
            //上传文件
            if (!ftp.storeFile(filename, input)) {
                return result;
            }
            input.close();
            ftp.logout();
            result = pathUrl+filePath+"/"+filename;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException ioe) {
                }
            }
        }
        return result;
    }


    static boolean  makeDir(FTPClient ftp,String path) throws IOException {
        //分割
        String[] paths = path.split("/");
        //创建成功标识
        boolean isMakeSucess=false;
        //遍历每一级路径
        for (String str : paths) {
            if (StringUtils.isBlank(str)) {
                continue;
            }
            //该级路径不存在就创建并切换
            if (!ftp.changeWorkingDirectory(str)) {
                isMakeSucess = ftp.makeDirectory(str);
                boolean changeWorkingDirectory = ftp.changeWorkingDirectory(str);
            } else {
                //切换路径
                boolean changeWorkingDirectory = ftp.changeWorkingDirectory(str);
                String[] split = ftp.printWorkingDirectory().split("/");
                if (str.equals(split[split.length-1])){
                    isMakeSucess=true;
                }
            }
        }
        return isMakeSucess;
    }


    public static boolean deleteFile(String pathname, String filename) {
        boolean flag = false;
        try {
            System.out.println("开始删除文件");
            initFtpClient();
            // 切换FTP目录
            ftp.changeWorkingDirectory(pathname);
            ftp.dele(filename);
            ftp.logout();
            flag = true;
            System.out.println("删除文件成功");
        } catch (Exception e) {
            System.out.println("删除文件失败");
            e.printStackTrace();
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return flag;
    }

    public static void showPathTest(String pathname) {
        initFtpClient();
        try {
            System.out.println(ftp.printWorkingDirectory());
            System.out.println(ftp.changeWorkingDirectory(pathname));
            System.out.println(ftp.printWorkingDirectory());
            FTPFile[] ftpFiles = ftp.listFiles();
            for (FTPFile ftpFile : ftpFiles) {
                System.out.println(ftpFile.getName());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }





    public static void main(String[] args) throws FileNotFoundException {
//      File file = new File("C:\\Users\\Administrator\\Desktop\\专利\\de5128173209c40de42d0eb12bc57ad4.jpg");
//      InputStream fileInputStream = new FileInputStream(file);
//      InputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
//      String fileName = file.getName();

//      String result = uploadFile(DateUtil.getDBDatetime().substring(0,8), "ysw"+fileName, bufferedInputStream);
//      if(!result.isEmpty()) {
//          System.out.println("true");
//      }else {
//          System.out.println("false");
//      }

    }

}