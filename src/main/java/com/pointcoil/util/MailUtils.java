package com.pointcoil.util;

/**
 * Created by WuShuang on 2019/11/2.
 */

import com.pointcoil.dto.map.RegisterDTO;
import lombok.extern.slf4j.Slf4j;

import javax.mail.Authenticator;
import javax.mail.Session;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import javax.mail.Address;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Transport;
import java.util.Arrays;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;


@Slf4j
public class MailUtils {



    // 邮件发送协议
    private final static String PROTOCOL = "smtp";

    // SMTP邮件服务器
    private final static String HOST = "smtp.163.com";

    // SMTP邮件服务器默认端口
    private final static String PORT = "465";

    // 是否要求身份认证
    private final static String IS_AUTH = "true";

    // 是否启用调试模式（启用调试模式可打印客户端与服务器交互过程时一问一答的响应消息）
    private final static String IS_ENABLED_DEBUG_MOD = "true";

    // 发件人
    private static String from = "hzdxqkj@163.com";
    private static String verification_code = "dianxianquan1";

    private final static String Sender = "杭州点线圈网络科技有限公司";


    // 初始化连接邮件服务器的会话信息
    private static Properties props = null;
    static {
        props = new Properties();
        props.setProperty("mail.transport.protocol", PROTOCOL);
        props.setProperty("mail.smtp.host", HOST);
        props.setProperty("mail.smtp.port", PORT);
        props.setProperty("mail.smtp.auth", IS_AUTH);
        props.setProperty("mail.debug",IS_ENABLED_DEBUG_MOD);
        props.setProperty("mail.smtp.socketFactory.port", "465");//设置ssl端口
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
    }

    private static  MailUtils mailUtils = new MailUtils();

    public static MailUtils getInstance(){
        return mailUtils;
    }


    public  void send() throws Exception {
        String charset = "utf-8";   // 指定中文编码格式
        // 创建Session实例对象
        Session session = Session.getInstance(props,new MyAuthenticator(from,verification_code));

        // 创建MimeMessage实例对象
        MimeMessage message = new MimeMessage(session);
        // 设置主题
        message.setSubject("杭州点线圈网络科技有限公司");
        // 设置发送人
        message.setFrom(new InternetAddress(from,"163邮箱",charset));
        // 设置收件人
        message.setRecipients(RecipientType.TO,
                new Address[] {

                        // 参数1：邮箱地址，参数2：姓名（在客户端收件只显示姓名，而不显示邮件地址），参数3：姓名中文字符串编码
                        new InternetAddress("19754443@qq.com", "点线圈",charset)
                }
        );
                try {
                    // 设置抄送
                    message.setRecipient(RecipientType.CC, new InternetAddress("278141196@qq.com","点线圈",charset));
                } catch (MessagingException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
        // 设置密送
        //message.setRecipient(RecipientType.BCC, new InternetAddress("xyang0917@qq.com", "赵六_QQ", charset));
        // 设置发送时间
        message.setSentDate(new Date());
        // 设置回复人(收件人回复此邮件时,默认收件人)
        message.setReplyTo(InternetAddress.parse("\"" + MimeUtility.encodeText("杭州点线圈网络科技有限公司") + "\" <"+from+">"));
        // 设置优先级(1:紧急   3:普通    5:低)
        message.setHeader("X-Priority", "1");
        // 要求阅读回执(收件人阅读邮件时会提示回复发件人,表明邮件已收到,并已阅读)
        message.setHeader("Disposition-Notification-To", from);
        // 创建一个MIME子类型为"mixed"的MimeMultipart对象，表示这是一封混合组合类型的邮件
        MimeMultipart mailContent = new MimeMultipart("mixed");
        message.setContent(mailContent);


        // 内容
        MimeBodyPart mailBody = new MimeBodyPart();
        mailBody.setContent("", "text/html;charset=UTF-8");

        // 将附件和内容添加到邮件当中
//        mailContent.addBodyPart(attach1);
//        mailContent.addBodyPart(attach2);
        mailContent.addBodyPart(mailBody);

//        // 附件1(利用jaf框架读取数据源生成邮件体)
//        DataSource ds1 = new FileDataSource("src/loading.gif");
//        DataHandler dh1 = new DataHandler(ds1);
//        attach1.setFileName(MimeUtility.encodeText("loading.gif"));
//        attach1.setDataHandler(dh1);
//
//        // 附件2
//        DataSource ds2 = new FileDataSource("src/车.png");
//        DataHandler dh2 = new DataHandler(ds2);
//        System.out.println("======================"+dh2.getName()+"====================");
//        attach2.setDataHandler(dh2);
//        attach2.setFileName(MimeUtility.encodeText(dh2.getName()));

        // 邮件正文(内嵌图片+html文本)
        MimeMultipart body = new MimeMultipart("related");  //邮件正文也是一个组合体,需要指明组合关系
        mailBody.setContent(body);
        // 邮件正文由html和图片构成
        MimeBodyPart htmlPart = new MimeBodyPart();
        body.addBodyPart(htmlPart);
//        // 正文图片
//        DataSource ds3 = new FileDataSource("src/psb.jpg");
//        DataHandler dh3 = new DataHandler(ds3);
//        imgPart.setDataHandler(dh3);
//        imgPart.setContentID("psb.jpg");

        // html邮件内容
        MimeMultipart htmlMultipart = new MimeMultipart("alternative");
        htmlPart.setContent(htmlMultipart);
        MimeBodyPart htmlContent = new MimeBodyPart();
      /*  String [] split = registerDTO.getBusinessLicense().split(",");
        StringBuffer businessLicense =new StringBuffer();
        List<String> strings = Arrays.asList(split);
        strings.forEach(
                n-> {
                    businessLicense.append("<img src='"+n+"' /></span>");
                }
        );*/
        htmlContent.setContent(
                "<span style='color:red'>【杭州点线圈】您收到一笔支付订单，请查看！</br>"
                , "text/html;charset=utf-8");
        htmlMultipart.addBodyPart(htmlContent);

        // 保存邮件内容修改
        message.saveChanges();

        // 发送邮件
        Transport.send(message);
    }

    public  void send(String emails, String phone, String format) throws Exception {
        String charset = "utf-8";   // 指定中文编码格式
        // 创建Session实例对象
        Session session = Session.getInstance(props,new MyAuthenticator(from,verification_code));

        // 创建MimeMessage实例对象
        MimeMessage message = new MimeMessage(session);
        // 设置主题
        message.setSubject("杭州点线圈网络科技有限公司注册用户申请");
        // 设置发送人
        message.setFrom(new InternetAddress(from,"163邮箱",charset));
        // 设置收件人
        message.setRecipients(RecipientType.TO,
                new Address[] {

                        // 参数1：邮箱地址，参数2：姓名（在客户端收件只显示姓名，而不显示邮件地址），参数3：姓名中文字符串编码
                        new InternetAddress(emails, phone,charset)
                }
        );
      /*  if(emails.size()-1>0){
            emails.forEach(n -> {
                System.err.println(String.valueOf(n).trim());
                try {
                    // 设置抄送
                    message.setRecipient(RecipientType.CC, new InternetAddress(String.valueOf(n).trim(),"审查员",charset));
                } catch (MessagingException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            });
        }*/
        // 设置密送
        //message.setRecipient(RecipientType.BCC, new InternetAddress("xyang0917@qq.com", "赵六_QQ", charset));
        // 设置发送时间
        message.setSentDate(new Date());
        // 设置回复人(收件人回复此邮件时,默认收件人)
        message.setReplyTo(InternetAddress.parse("\"" + MimeUtility.encodeText("杭州点线圈网络科技有限公司") + "\" <"+from+">"));
        // 设置优先级(1:紧急   3:普通    5:低)
        message.setHeader("X-Priority", "1");
        // 要求阅读回执(收件人阅读邮件时会提示回复发件人,表明邮件已收到,并已阅读)
        message.setHeader("Disposition-Notification-To", from);
        // 创建一个MIME子类型为"mixed"的MimeMultipart对象，表示这是一封混合组合类型的邮件
        MimeMultipart mailContent = new MimeMultipart("mixed");
        message.setContent(mailContent);


        // 内容
        MimeBodyPart mailBody = new MimeBodyPart();
        mailBody.setContent("", "text/html;charset=UTF-8");

        // 将附件和内容添加到邮件当中
//        mailContent.addBodyPart(attach1);
//        mailContent.addBodyPart(attach2);
        mailContent.addBodyPart(mailBody);

//        // 附件1(利用jaf框架读取数据源生成邮件体)
//        DataSource ds1 = new FileDataSource("src/loading.gif");
//        DataHandler dh1 = new DataHandler(ds1);
//        attach1.setFileName(MimeUtility.encodeText("loading.gif"));
//        attach1.setDataHandler(dh1);
//
//        // 附件2
//        DataSource ds2 = new FileDataSource("src/车.png");
//        DataHandler dh2 = new DataHandler(ds2);
//        System.out.println("======================"+dh2.getName()+"====================");
//        attach2.setDataHandler(dh2);
//        attach2.setFileName(MimeUtility.encodeText(dh2.getName()));

        // 邮件正文(内嵌图片+html文本)
        MimeMultipart body = new MimeMultipart("related");  //邮件正文也是一个组合体,需要指明组合关系
        mailBody.setContent(body);
        // 邮件正文由html和图片构成
        MimeBodyPart htmlPart = new MimeBodyPart();
        body.addBodyPart(htmlPart);
//        // 正文图片
//        DataSource ds3 = new FileDataSource("src/psb.jpg");
//        DataHandler dh3 = new DataHandler(ds3);
//        imgPart.setDataHandler(dh3);
//        imgPart.setContentID("psb.jpg");

        // html邮件内容
        MimeMultipart htmlMultipart = new MimeMultipart("alternative");
        htmlPart.setContent(htmlMultipart);
        MimeBodyPart htmlContent = new MimeBodyPart();
      /*  String [] split = registerDTO.getBusinessLicense().split(",");
        StringBuffer businessLicense =new StringBuffer();
        List<String> strings = Arrays.asList(split);
        strings.forEach(
                n-> {
                    businessLicense.append("<img src='"+n+"' /></span>");
                }
        );*/
        htmlContent.setContent(
                "<span style='color:red'>【点线圈】您申请注册的DLA商圈管理系统"+phone+"账号已于"+format+"审核通过。请登录PC端网站hzdxq.cn使用，系统操作手册索取请联系19941207730(微信同号)。欢迎使用！</br>"
                , "text/html;charset=utf-8");
        htmlMultipart.addBodyPart(htmlContent);

        // 保存邮件内容修改
        message.saveChanges();

        // 发送邮件
        Transport.send(message);
    }
    /**
            * 发送带内嵌图片、附件、多收件人(显示邮箱姓名)、邮件优先级、阅读回执的完整的HTML邮件
     */
    public  void sendMultipleEmail(List<String> emails, RegisterDTO registerDTO) throws Exception {
        String charset = "utf-8";   // 指定中文编码格式
        // 创建Session实例对象
        Session session = Session.getInstance(props,new MyAuthenticator(from,verification_code));

        // 创建MimeMessage实例对象
        MimeMessage message = new MimeMessage(session);
        // 设置主题
        message.setSubject("杭州点线圈网络科技有限公司注册用户申请");
        // 设置发送人
        message.setFrom(new InternetAddress(from,"163邮箱",charset));
        // 设置收件人
        message.setRecipients(RecipientType.TO,
                new Address[] {

                        // 参数1：邮箱地址，参数2：姓名（在客户端收件只显示姓名，而不显示邮件地址），参数3：姓名中文字符串编码
                        new InternetAddress(emails.get(0), "审查员",charset)
                }
        );
        if(emails.size()-1>0){
            emails.forEach(n -> {
                System.err.println(String.valueOf(n).trim());
                try {
                    // 设置抄送
                    message.setRecipient(RecipientType.CC, new InternetAddress(String.valueOf(n).trim(),"审查员",charset));
                } catch (MessagingException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            });
        }
        // 设置密送
        //message.setRecipient(RecipientType.BCC, new InternetAddress("xyang0917@qq.com", "赵六_QQ", charset));
        // 设置发送时间
        message.setSentDate(new Date());
        // 设置回复人(收件人回复此邮件时,默认收件人)
        message.setReplyTo(InternetAddress.parse("\"" + MimeUtility.encodeText("杭州点线圈网络科技有限公司") + "\" <"+from+">"));
        // 设置优先级(1:紧急   3:普通    5:低)
        message.setHeader("X-Priority", "1");
        // 要求阅读回执(收件人阅读邮件时会提示回复发件人,表明邮件已收到,并已阅读)
        message.setHeader("Disposition-Notification-To", from);
        // 创建一个MIME子类型为"mixed"的MimeMultipart对象，表示这是一封混合组合类型的邮件
        MimeMultipart mailContent = new MimeMultipart("mixed");
        message.setContent(mailContent);


        // 内容
        MimeBodyPart mailBody = new MimeBodyPart();
        mailBody.setContent("", "text/html;charset=UTF-8");

        // 将附件和内容添加到邮件当中
//        mailContent.addBodyPart(attach1);
//        mailContent.addBodyPart(attach2);
        mailContent.addBodyPart(mailBody);

//        // 附件1(利用jaf框架读取数据源生成邮件体)
//        DataSource ds1 = new FileDataSource("src/loading.gif");
//        DataHandler dh1 = new DataHandler(ds1);
//        attach1.setFileName(MimeUtility.encodeText("loading.gif"));
//        attach1.setDataHandler(dh1);
//
//        // 附件2
//        DataSource ds2 = new FileDataSource("src/车.png");
//        DataHandler dh2 = new DataHandler(ds2);
//        System.out.println("======================"+dh2.getName()+"====================");
//        attach2.setDataHandler(dh2);
//        attach2.setFileName(MimeUtility.encodeText(dh2.getName()));

        // 邮件正文(内嵌图片+html文本)
        MimeMultipart body = new MimeMultipart("related");  //邮件正文也是一个组合体,需要指明组合关系
        mailBody.setContent(body);
        // 邮件正文由html和图片构成
        MimeBodyPart htmlPart = new MimeBodyPart();
        body.addBodyPart(htmlPart);
//        // 正文图片
//        DataSource ds3 = new FileDataSource("src/psb.jpg");
//        DataHandler dh3 = new DataHandler(ds3);
//        imgPart.setDataHandler(dh3);
//        imgPart.setContentID("psb.jpg");

        // html邮件内容
        MimeMultipart htmlMultipart = new MimeMultipart("alternative");
        htmlPart.setContent(htmlMultipart);
        MimeBodyPart htmlContent = new MimeBodyPart();
        String [] split = registerDTO.getBusinessLicense().split(",");
        StringBuffer businessLicense =new StringBuffer();
        List<String> strings = Arrays.asList(split);
        strings.forEach(
                n-> {
                    businessLicense.append("<img src='"+n+"' /></span>");
                }
        );
        htmlContent.setContent(
                "<span style='color:red'>真实姓名："+registerDTO.getUserName()+"</br>"+
                        "<span style='color:red'>联系电话："+registerDTO.getUserPhone()+"</br>"+
                            "<span style='color:red'>邮箱："+registerDTO.getUserEmail()+"</br>"+
                                "<span style='color:red'>企业名称："+registerDTO.getEnterpriseName()+"</br>"+
                                    "<span style='color:red'>企业名称："+registerDTO.getMakeName()+"</br>"+businessLicense
                , "text/html;charset=utf-8");
        htmlMultipart.addBodyPart(htmlContent);

        // 保存邮件内容修改
        message.saveChanges();

        // 发送邮件
        Transport.send(message);
    }

    /**
     * 向邮件服务器提交认证信息
     */
    class MyAuthenticator extends Authenticator {

        private String username = "xuhuaqing1997@163.com";

        private String password = "112300x";

        public MyAuthenticator() {
            super();
        }

        public MyAuthenticator(String username, String password) {
            super();
            this.username = username;
            this.password = password;
        }

        @Override
        protected PasswordAuthentication getPasswordAuthentication() {

            return new PasswordAuthentication(username, password);
        }
    }

    }



