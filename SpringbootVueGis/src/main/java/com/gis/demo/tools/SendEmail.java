package com.gis.demo.tools;

import java.security.GeneralSecurityException;
import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.sun.mail.util.MailSSLSocketFactory;
/*
 * @author 邓村
 * 发送邮箱的工具类
 * 2017年11月1日18:39:28
 */
public class SendEmail {
	public static final String HOST = "smtp.qq.com";
//  public static final String PROTOCOL = "smtp";  
//  public static final int PORT = 25;

  private static final String FROM = "307695664@qq.com";//发送方邮箱
  private static final String PWD = "248248850";//授权码
  

  /**
   * 获取Session
   * @return
   */
  private static Session getSession() {
      Properties props = new Properties();
      props.put("mail.smtp.host", HOST);//服务器地址
      props.put("mail.transport.protocol" , "smtp");//协议
      //props.put("mail.smtp.port", "465");//端口
      props.put("mail.smtp.auth" , "true");
      MailSSLSocketFactory sf=null;
	try {
		sf = new MailSSLSocketFactory();
	} catch (GeneralSecurityException e) {
		e.printStackTrace();
	}
      sf.setTrustAllHosts(true);
      props.put("mail.smtp.ssl.enable", "true");
      props.put("mail.smtp.ssl.socketFactory", sf);
      Authenticator authenticator = new Authenticator() {

          @Override
          protected PasswordAuthentication getPasswordAuthentication() {
              return new PasswordAuthentication(FROM, PWD);
          }

      };
      Session session = Session.getDefaultInstance(props , authenticator);

      return session;
  }
 /*
  * 发送指定内容到指定邮箱
  */
  public static void send(String toEmail , String content) {
      Session session = getSession();
      try {
          Message msg = new MimeMessage(session);
          //Set message attributes
          msg.setFrom(new InternetAddress(FROM));
          InternetAddress[] address = {new InternetAddress(toEmail)};
          msg.setRecipients(Message.RecipientType.TO, address);
          msg.setSubject("账户激活邮件");
        
          msg.setSentDate(new Date());
          msg.setContent(content , "text/html;charset=utf-8");

          //Send the message
          Transport transport = session.getTransport();
          transport.connect("smtp.qq.com", FROM, PWD);
          transport.sendMessage(msg,address );
          transport.close();
          //Transport.send(msg);
      }
      catch (MessagingException mex) {
          mex.printStackTrace();
      }
  }
}
