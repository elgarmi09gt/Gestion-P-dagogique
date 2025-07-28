package view.beans.mails;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import java.net.MalformedURLException;

import java.sql.Connection;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.activation.DataHandler;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import javax.naming.Context;
import javax.naming.InitialContext;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import javax.sql.DataSource;

import model.services.gestdapAppImpl;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.type.WhenNoDataTypeEnum;
import net.sf.jasperreports.engine.util.JRLoader;

import oracle.adf.model.BindingContext;

import oracle.binding.BindingContainer;

import oracle.jbo.ApplicationModule;
import oracle.jbo.client.Configuration;

public class SendMail {
    public SendMail() {
    }
    
    public BindingContainer getBindings() {
        return (BindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
    }
    
    public Connection getDataSourceConnection(String dataSourceName) throws Exception {
        Context ctx = new InitialContext();
        DataSource ds = (DataSource) ctx.lookup(dataSourceName);
        return ds.getConnection();
    }

    private Connection getConnection() throws Exception {
        return getDataSourceConnection("jdbc/refDS"); // datasource name should be defined in weblogic
    }

    public ServletContext getContext() {
        return (ServletContext) getFacesContext().getExternalContext().getContext();
    }

    public HttpServletResponse getResponse() {
        return (HttpServletResponse) getFacesContext().getExternalContext().getResponse();
    }

    public static FacesContext getFacesContext() {
        return FacesContext.getCurrentInstance();
    }
    
    @SuppressWarnings("oracle.jdeveloper.java.insufficient-catch-block")
    public void close(Connection con) {
        if (con != null) {
            try {
                con.close();
            } catch (Exception e) {
            }
        }
    }
    
    @SuppressWarnings("unchecked")
    public ByteArrayOutputStream runReport(String repPath, java.util.Map param) throws Exception {
            Connection conn = null;
            ByteArrayOutputStream baos = null;
            //ServletOutputStream out = null;
                try {
                HttpServletResponse response = getResponse();
                //out = response.getOutputStream();
                response.setHeader("Cache-Control", "max-age=0");
                response.setContentType("application/pdf");
                ServletContext context = getContext();
                InputStream fs = context.getResourceAsStream("/reports/" + repPath);
                JasperReport template = (JasperReport) JRLoader.loadObject(fs);
                template.setWhenNoDataType(WhenNoDataTypeEnum.ALL_SECTIONS_NO_DETAIL);
                conn = getConnection();
                @SuppressWarnings("unchecked")
                JasperPrint print = JasperFillManager.fillReport(template, param, conn);
                baos = new ByteArrayOutputStream();
                JasperExportManager.exportReportToPdfStream(print, baos);
                //out.write(baos.toByteArray());
                baos.close();
                FacesContext.getCurrentInstance().responseComplete();
            } catch (Exception jex) {
                jex.printStackTrace();
            } finally {
                baos.close();
                //out.flush();
                //out.close();
                close(conn);
            }
            return baos;
        }

    @SuppressWarnings("unchecked")
    public void sendEmail(String to, String subject, String body) {
            final String username = "noreplyexamen@ucad.edu.sn";
            final String password = "P@290942831046of";
            final String smtpHost = "smtp.office365.com"; // Remplacez par votre SMTP

            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true"); // Changez si nécessaire

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
                message.setSubject(subject);// Définir le contenu en HTML
                message.setContent(body, "text/html; charset=UTF-8");
                
                /*MimeBodyPart textBodyPart = new MimeBodyPart();
                textBodyPart.setText("Veuillez recevoir votre releve provisoir");*/
                String realpath = null;
                Map m = new HashMap();
                m.put("parcours", 1);
                m.put("SUBREPORT_DIR", realpath);
                m.put("annee_univers", 1);
                m.put("id_hs", 1);
                m.put("numero", "20200A6RV");
                m.put("id_semestre", 1);
                m.put("id_calendrier", 5781);
                try {
                    realpath = getContext().getResource("/reports/").getPath();
                    //System.out.println("Path to reports : "+realpath);
                } catch (MalformedURLException e) {
                    System.out.println(e.getMessage());
                }
                try {
                    MimeBodyPart textBodyPart = new MimeBodyPart();
                    ByteArrayOutputStream baos = runReport("releves_standard_smstre_test.jasper", m);
                    byte[] bytes = baos.toByteArray();
                    javax.mail.util.ByteArrayDataSource dataSourceFile = new ByteArrayDataSource(bytes, "application/pdf");
                    MimeBodyPart pdfBodyPart = new MimeBodyPart();
                    pdfBodyPart.setDataHandler(new DataHandler(dataSourceFile));
                    pdfBodyPart.setFileName("Releves.pdf");
                    MimeMultipart mimeMultipart = new MimeMultipart();
                    mimeMultipart.addBodyPart(textBodyPart);
                    mimeMultipart.addBodyPart(pdfBodyPart);
                    message.setContent(mimeMultipart);
                    //javaMailSender.send(javaXmessage);
                    Transport.send(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                // message.setText(body);
                //System.out.println("Email envoyé à "+to+" avec succès !");
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }

    @SuppressWarnings({ "unchecked", "oracle.jdeveloper.java.constructor-method-name" })
    public void SendMailTo() {
        
        ApplicationModule am = Configuration.createRootApplicationModule("model.services.gestdapApp", "Gdap");
        if (am instanceof gestdapAppImpl) {
            gestdapAppImpl appModule = (gestdapAppImpl) am;
            appModule.UpdateToMailSem(new Long(35051));
        }
        // Libérer les ressources
        Configuration.releaseRootApplicationModule(am, true);
        /*DCIteratorBinding iterMail = (DCIteratorBinding) getBindings().get("EtudiantsToMailIterator");
        System.out.println("size : " + iterMail.getEstimatedRowCount());
        RowSetIterator rsi = iterMail.getViewObject().createRowSetIterator(null);
        String text =
            "<span style = \"font-family : Century Gothic;font-size : 16px;line-height : 30px;\">Bonjour<br/>" +
            "Vos notes ont été publiées sur le centre étudiant (<a href=\"https://studentcenter.ucad.sn/\">Studentcenter</a>). " +
            "Vous pouvez consulter votre relevé de notes et faire vos réclamations uniquement <b>sur " +
            "les EC dont les moyennes sont gelées ou égales à zéro.</b><br/>" +
            "Vous devez faire votre réclamation dans un délai <b>de 72 heures après publication des résultats.</b><br/>" +
            "Si vous rencontrez des difficultés pour se connecter sur le centre étudiant merci de vous rapprocher du service informatique de votre établissement ou envoyer un mail à l'adresse <b>support@ucad.edu.sn.</b><br/>" +
            "La vidéo ci-dessous vous montre comment faire une réclamation sur le centre étudiant.<br/>" +
            "<a href=\"https://youtu.be/IwXxLnY5BHE\">Voir Vidéo</a></span>";
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
                }
                if (null != email_pers) {
                    sendEmail(email_pers, obj, text);
                    //OperationBinding opPers = getBindings().getOperationBinding("sendEmail");
                    //opPers.getParamsMap().put("to", email_pers);
                    //opPers.getParamsMap().put("subject", obj);
                    //opPers.getParamsMap().put("body", text);
                    //opPers.execute();
                }
                OperationBinding op = getBindings().getOperationBinding("UpdateToMail");
                op.getParamsMap().put("prcrs_maq", nextRow.getAttribute("IdParcoursMaquetAnnee"));
                op.getParamsMap().put("cal_id", "IdCalendrierDeliberation");
                op.execute();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        rsi.closeRowSetIterator();*/
    }

    @SuppressWarnings("unchecked")
    public void OnSendMailAction(ActionEvent actionEvent) {
        // Création d'un scheduler avec un pool d'un thread
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        // Exécuter une tâche toutes les 2 secondes avec un délai initial de 0
               scheduler.scheduleAtFixedRate(() -> {
                   System.out.println("Exécution périodique : " + System.currentTimeMillis());
               }, 0, 1, TimeUnit.MINUTES);

               // Pour arrêter après 10 secondes (optionnel)
               scheduler.schedule(() -> {
                   scheduler.shutdown();
                   System.out.println("Arrêt du scheduler.");
               }, 5, TimeUnit.MINUTES);

        /*DCIteratorBinding iterMail = (DCIteratorBinding) getBindings().get("EtudiantsToMailIterator");
        System.out.println("size : "+iterMail.getEstimatedRowCount());
        RowSetIterator rsi = iterMail.getViewObject().createRowSetIterator(null);
        while (rsi.hasNext()){
            Row nextRow = rsi.next();
            if(null != nextRow.getAttribute("EmailInstitutionnel")){
                try {
                    String text = "<span style = \"font-family : Century Gothic;font-size : 16px;line-height : 30px;\">Bonjour<br/>" +
                        "Vos notes ont été publiées sur le centre étudiant (<a href=\"https://studentcenter.ucad.sn/\">Studentcenter</a>). " +
                        "Vous pouvez consulter votre relevé de notes et faire vos réclamations uniquement <b>sur " +
                        "les EC dont les moyennes sont gelées ou égales à zéro.</b><br/>" +
                        "Vous devez faire votre réclamation dans un délai <b>de 72 heures après publication des résultats.</b><br/>" +
                        "Si vous rencontrez des difficultés pour se connecter sur le centre étudiant merci de vous rapprocher du service informatique de votre établissement ou envoyer un mail à l'adresse <b>support@ucad.edu.sn.</b><br/>" +
                        "La vidéo ci-dessous vous montre comment faire une réclamation sur le centre étudiant.<br/>"+
                        "<a href=\"https://youtu.be/IwXxLnY5BHE\">Voir Vidéo</a></span>";
                    OperationBinding op = getBindings().getOperationBinding("sendEmail");
                    op.getParamsMap().put("to", nextRow.getAttribute("EmailInstitutionnel"));
                    op.getParamsMap().put("subject", "Pubnote");
                    op.getParamsMap().put("body", text);
                    op.execute();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if(null != nextRow.getAttribute("EmailPersonnel")){
                try {
                    String text = "<span style = \"font-family : Century Gothic;font-size : 16px;line-height : 30px;\">Bonjour<br/>" +
                        "Vos notes ont été publiées sur le centre étudiant (<a href=\"https://studentcenter.ucad.sn/\">Studentcenter</a>). " +
                        "Vous pouvez consulter votre relevé de notes et faire vos réclamations uniquement <b>sur " +
                        "les EC dont les moyennes sont gelées ou égales à zéro.</b><br/>" +
                        "Vous devez faire votre réclamation dans un délai <b>de 72 heures après publication des résultats.</b><br/>" +
                        "Si vous rencontrez des difficultés pour se connecter sur le centre étudiant merci de vous rapprocher du service informatique de votre établissement ou envoyer un mail à l'adresse <b>support@ucad.edu.sn.</b><br/>" +
                        "La vidéo ci-dessous vous montre comment faire une réclamation sur le centre étudiant.<br/>"+
                        "<a href=\"https://youtu.be/IwXxLnY5BHE\">Voir Vidéo</a></span>";
                    OperationBinding op = getBindings().getOperationBinding("sendEmail");
                    op.getParamsMap().put("to", nextRow.getAttribute("EmailPersonnel"));
                    op.getParamsMap().put("subject", "Publication Note");
                    op.getParamsMap().put("body", text);
                    op.execute();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            
        }
        try {
            OperationBinding op = getBindings().getOperationBinding("UpdateToMail");
            op.getParamsMap().put("prcrs_maq", nextRow.getAttribute("IdParcoursMaquetAnnee"));
            op.getParamsMap().put("cal_id", "IdCalendrierDeliberation");
            op.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
        rsi.closeRowSetIterator();*/
        /*Map<Integer, String> map = new HashMap<>();
        map.put(1, "aladji.niang@ucad.edu.sn");
        map.put(2, "papeousseynou.ngom@ucad.edu.sn");
        map.put(3, "fatou.dieng@ucad.edu.sn");
        // map.put(1, "arg1");
        // Add event code here...
        for (Map.Entry<Integer, String> entry : map.entrySet()) {
            // System.out.println(entry.getKey() + " a " + entry.getValue() + " ans.");
            try {
                String text = "<span style = \"font-family : Century Gothic;font-size : 16px;line-height : 30px;\">Bonjour<br/>" +
                    "Vos notes ont été publiées sur le centre étudiant (<a href=\"https://studentcenter.ucad.sn/\">Studentcenter</a>). " +
                    "Vous pouvez consulter votre relevé de notes et faire vos réclamations uniquement <b>sur " +
                    "les EC dont les moyennes sont gelées ou égales à zéro.</b><br/>" +
                    "Vous devez faire votre réclamation dans un délai <b>de 72 heures après publication des résultats.</b><br/>" +
                    "Si vous rencontrez des difficultés pour se connecter sur le centre étudiant merci de vous rapprocher du service informatique de votre établissement ou envoyer un mail à l'adresse <b>support@ucad.edu.sn.</b><br/>" +
                    "La vidéo ci-dessous vous montre comment faire une réclamation sur le centre étudiant.<br/>"+
                    "<a href=\"https://youtu.be/IwXxLnY5BHE\">Voir Vidéo</a></span>";
                OperationBinding op = getBindings().getOperationBinding("sendEmail");
                op.getParamsMap().put("to", entry.getValue());
                op.getParamsMap().put("subject", "Pubnote");
                op.getParamsMap().put("body", text);
                op.execute();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }*/
        
    }
}
