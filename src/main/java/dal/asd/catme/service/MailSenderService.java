package dal.asd.catme.service;

import dal.asd.catme.beans.Course;
import dal.asd.catme.beans.Student;
import dal.asd.catme.beans.User;
import dal.asd.catme.util.CatmeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.util.ArrayList;

@Service
public class MailSenderService implements IMailSenderService
{
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from = "adv.sdc.g.16@gmail.com";

    @Autowired
    public MailSenderService(JavaMailSender mailSender)
    {
        this.mailSender = mailSender;
    }

    @Override
    public void sendMail(User user, String subject, String bodyText) throws MailException, MessagingException
    {
        //code taken from https://stackoverflow.com/questions/5289849/how-do-i-send-html-email-in-spring-mvc
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

        helper.setText(bodyText, true);
        helper.setTo(user.getEmail());
        helper.setSubject(subject);
        helper.setFrom(from);

        mailSender.send(mimeMessage);
    }

    @Override
    public void sendCredentialsToStudent(Student s, Course c) throws MessagingException
    {
        String bodyText = getFormattedEmailForNewStudent(s,c);

        if(bodyText==null)
            throw new MessagingException("Error Creating Mail Text From Template");

        String subject = CatmeUtil.NEW_STUDENT_EMAIL_SUBJECT;

        sendMail(s,subject,bodyText);
    }

    @Override
    public void sendNewPassword(User u) throws MailException, MessagingException
    {
        sendMail(u,CatmeUtil.FORGOT_PASSWORD_EMAIL_SUBJECT,getFormattedEmailForForgotPassword(u));
    }

    public String getFormattedEmailForNewStudent(Student s, Course c)
    {
        try
        {

            //read html email template file
            File file = new File(CatmeUtil.PATH_TO_NEW_STUDENT_TEMPLATE);
            FileInputStream fis = new FileInputStream(file);
            byte[] data = new byte[(int) file.length()];
            fis.read(data);
            fis.close();

            String str = new String(data, "UTF-8");

            //replace student details in the content
            str = str.replace(CatmeUtil.TEMPLATE_USERNAME,s.getFirstName());
            str = str.replace(CatmeUtil.TEMPLATE_BANNERID,s.getBannerId());
            str = str.replace(CatmeUtil.TEMPLATE_PASSWORD,s.getPassword());
            str = str.replace(CatmeUtil.TEMPLATE_COURSE,c.getCourseId());

            return str;
        }
        catch (FileNotFoundException e)
        {
            return null;
        } catch (UnsupportedEncodingException e)
        {
            return null;
        } catch (IOException e)
        {
            return null;
        }
    }

    public String getFormattedEmailForForgotPassword(User u)
    {
        try
        {

            //read html email template file
            File file = new File(CatmeUtil.PATH_TO_FORGOT_PASSWORD_TEMPLATE);
            FileInputStream fis = new FileInputStream(file);
            byte[] data = new byte[(int) file.length()];
            fis.read(data);
            fis.close();

            String str = new String(data, "UTF-8");

            //replace student details in the content
            str = str.replace(CatmeUtil.TEMPLATE_USERNAME,u.getFirstName());
            str = str.replace(CatmeUtil.TEMPLATE_PASSWORD,u.getPassword());

            return str;
        }
        catch (FileNotFoundException e)
        {
            return null;
        } catch (UnsupportedEncodingException e)
        {
            return null;
        } catch (IOException e)
        {
            return null;
        }
    }
}