/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package globals;


import java.io.Serializable;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public final class SendMail implements Serializable
{
    private String to;
    private String from;
    private String subject;
    private String body;
    public static Properties props;
    public static Session session = null;

    public SendMail()
    {
        to = null;
        from = null;
        subject = null;
        body = null;
    }
    public void messTo(String s)
    {
        to = s;
    }
    public void messFrom(String s)
    {
        from = s;
    }
    public void messSubject(String s)
    {
        subject = s;
    }
    public void messBody(String s)
    {
        body = s;
    }
    public void sendMail()
        throws Exception
    {
        if(!everythingIsSet())
            throw new Exception("Mail couldn't be sent as all options like To/From/Subject/Message are not set");
        try
        {
            MimeMessage mimemessage = new MimeMessage(session);
            mimemessage.setRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(to));
            mimemessage.setFrom(new InternetAddress(from));
            mimemessage.setSubject(subject);
            mimemessage.setText(body);
            Transport.send(mimemessage);
        }
        catch(MessagingException messagingexception)
        {
            throw new Exception(messagingexception.getMessage());
        }
    }
    private boolean everythingIsSet()
    {
        if(to == null || from == null || subject == null || body == null)
            return false;
        if(to.indexOf("@") == -1 || to.indexOf(".") == -1)
            return false;
        return from.indexOf("@") != -1 && from.indexOf(".") != -1;
    }
    static
    {
        props = null;
        props = System.getProperties();
        props.put("mail.smtp.host", "10.146.66.200");
        session = Session.getDefaultInstance(props, null);
    }
}