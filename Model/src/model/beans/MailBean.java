package model.beans;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import oracle.adf.model.BindingContext;

import oracle.adf.model.binding.DCIteratorBinding;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;

public class MailBean {
    public MailBean() {
        super();
    }
    public BindingContainer getBindings() {
        return (BindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
    }
    
    public void sendEmail(String to, String subject, String body) {
            final String username = "noreplyexamen@ucad.edu.sn";
            final String password = "P@290942831046of";
            final String smtpHost = "smtp.office365.com"; // Remplacez par votre SMTP

            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true"); // Changez si n�cessaire

            //#For sending email
            props.put("mail.smtp.host", smtpHost);
            props.put("mail.smtp.port", "587");

            //# Other properties
            props.put("mail.smtp.connectiontimeout", "5000");
            props.put("mail.smtp.timeout", "5000");
            props.put("mail.smtp.writetimeout", "5000");

            //# TLS , port 587
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.ssl.trust", "smtp.office365.com");

            Session session = Session.getInstance(props, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });

            try {
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(username));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
                message.setSubject(subject);// D�finir le contenu en HTML
                message.setContent(body, "text/html; charset=UTF-8");
                // message.setText(body);
                Transport.send(message);
                System.out.println("Email envoy� � "+to+" avec succ�s !");
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
    public void SendMail() {
        DCIteratorBinding iterMail = (DCIteratorBinding) getBindings().get("EtudiantsToMailIterator");
        System.out.println("size : " + iterMail.getEstimatedRowCount());
        RowSetIterator rsi = iterMail.getViewObject().createRowSetIterator(null);
        String text =
            "<span style = \"font-family : Century Gothic;font-size : 16px;line-height : 30px;\">Bonjour<br/>" +
            "Vos notes ont �t� publi�es sur le centre �tudiant (<a href=\"https://studentcenter.ucad.sn/\">Studentcenter</a>). " +
            "Vous pouvez consulter votre relev� de notes et faire vos r�clamations uniquement <b>sur " +
            "les EC dont�les moyennes sont gel�es ou �gales � z�ro.</b><br/>" +
            "Vous devez faire votre r�clamation dans un d�lai <b>de 72 heures apr�s publication des r�sultats.</b><br/>" +
            "Si vous rencontrez des difficult�s pour se connecter sur le centre �tudiant merci de vous rapprocher du service informatique de votre �tablissement ou envoyer un mail � l'adresse <b>support@ucad.edu.sn.</b><br/>" +
            "La vid�o ci-dessous vous montre comment faire une r�clamation sur le centre �tudiant.<br/>" +
            "<a href=\"https://youtu.be/IwXxLnY5BHE\">Voir Vid�o</a></span>";
        String email_inst = null;
        String email_pers = null;
        String obj = "Publication Note";
        while (rsi.hasNext()) {
            Row nextRow = rsi.next();
            if (null != nextRow.getAttribute("EmailInstitutionnel")) {
                email_inst = nextRow.getAttribute("EmailInstitutionnel").toString();
            }
            if (null != nextRow.getAttribute("EmailPersonnel")) {
                email_pers = nextRow.getAttribute("EmailPersonnel").toString();
            }
            try {
                if (null != email_inst) {
                    sendEmail(email_inst, obj, text);
                    /*OperationBinding opInst = getBindings().getOperationBinding("sendEmail");
                    opInst.getParamsMap().put("to", email_inst);
                    opInst.getParamsMap().put("subject", obj);
                    opInst.getParamsMap().put("body", text);
                    opInst.execute();*/
                }
                if (null != email_pers) {
                    sendEmail(email_pers, obj, text);
                    /*OperationBinding opPers = getBindings().getOperationBinding("sendEmail");
                    opPers.getParamsMap().put("to", email_pers);
                    opPers.getParamsMap().put("subject", obj);
                    opPers.getParamsMap().put("body", text);
                    opPers.execute();*/
                }
                OperationBinding op = getBindings().getOperationBinding("UpdateToMail");
                op.getParamsMap().put("prcrs_maq", nextRow.getAttribute("IdParcoursMaquetAnnee"));
                op.getParamsMap().put("cal_id", "IdCalendrierDeliberation");
                op.execute();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        rsi.closeRowSetIterator();
    }
}
