package view.beans.listeners;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import java.sql.Connection;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.faces.context.FacesContext;

import javax.naming.Context;
import javax.naming.InitialContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletResponse;

import model.services.gestdapAppImpl;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.type.WhenNoDataTypeEnum;
import net.sf.jasperreports.engine.util.JRLoader;

import oracle.jbo.ApplicationModule;
import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewObject;
import oracle.jbo.client.Configuration;

@WebListener
public class AppStartupListener implements ServletContextListener {
    private ServletContext context = null;
    
    public Connection getDataSourceConnection(String dataSourceName) throws Exception {
        Context ctx = new InitialContext();
        javax.sql.DataSource ds = (javax.sql.DataSource) ctx.lookup(dataSourceName);
        return ds.getConnection();
    }

    private Connection getConnection() throws Exception {
        return getDataSourceConnection("jdbc/refDS"); // datasource name should be defined in weblogic
    }
/*
    public ServletContext getContext() {
        return (ServletContext) getFacesContext().getExternalContext().getContext();
    }

    public HttpServletResponse getResponse() {
        return (HttpServletResponse) getFacesContext().getExternalContext().getResponse();
    }

    public static FacesContext getFacesContext() {
        return FacesContext.getCurrentInstance();
    }
    */
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
                //HttpServletResponse response = getResponse();
                //out = response.getOutputStream();
                //response.setHeader("Cache-Control", "max-age=0");
                //response.setContentType("application/pdf");
                //ServletContext context = getContext();
                //InputStream fs = getClass().getResourceAsStream("/reports/" + repPath);
                InputStream fs = getContext().getResourceAsStream("/reports/" + repPath);
                JasperReport template = (JasperReport) JRLoader.loadObject(fs);
                template.setWhenNoDataType(WhenNoDataTypeEnum.ALL_SECTIONS_NO_DETAIL);
                conn = getConnection();
                @SuppressWarnings("unchecked")
                JasperPrint print = JasperFillManager.fillReport(template, param, conn);
                baos = new ByteArrayOutputStream();
                JasperExportManager.exportReportToPdfStream(print, baos);
                //out.write(baos.toByteArray());
                baos.close();
                //FacesContext.getCurrentInstance().responseComplete();
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
    /*
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
                
                
                String realpath = null;//"C:\\Works\\GestionPedagogique\\ViewController\\public_html\\reports\\"
                Map m = new HashMap();
                m.put("parcours", 4330);
                m.put("numero", "20200A6RV");
                m.put("id_semestre", 3);
                m.put("id_calendrier", 5781);
                try {
                    System.out.println("getting path");
                    realpath = getContext().getResource("/reports/").getPath();
                    System.out.println("Path to reports : "+realpath);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                m.put("SUBREPORT_DIR", realpath);
                System.out.println("Sending mail");
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
                    System.out.println("Send to "+to);
                    Transport.send(message);
                    System.out.println("Email envoyé à "+to+" avec succès !");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                // message.setText(body);
                System.out.println("Email envoyé à "+to+" avec succès !");
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
*/
    @SuppressWarnings("unchecked")
    public void contextInitialized(ServletContextEvent event) {
        context = event.getServletContext();

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
                // Exécuter la tache tout les 10h pour la 1ére publication
                scheduler.scheduleAtFixedRate(() -> {
                    // System.out.println("Exécution périodique Publication grlobal : " + System.currentTimeMillis());
                    ApplicationModule am =
                        Configuration.createRootApplicationModule("model.services.gestdapApp", "gestdapAppLocal");
                    if (am instanceof gestdapAppImpl) {
                        gestdapAppImpl appModule = (gestdapAppImpl) am;
                        String realpath = null;
                        try {
                            // System.out.println("Geting path...");
                            realpath = context.getResource("/reports/").getPath();
                            // System.out.println("Path to reports : "+realpath);
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                        Map m = new HashMap();
                        m.put("SUBREPORT_DIR", realpath);
                        try {
                            
                            try {
                                ViewObject vo = appModule.findViewObject("EtudiantsToMail");
                                if (vo != null) {
                                    vo.clearCache();
                                    vo.executeQuery();
                                    // Itérer sur les résultats
                                    RowSetIterator iterator = vo.createRowSetIterator(null);
                                    //String text =
                                        //"<span style = \"font-family : Century Gothic;font-size : 16px;line-height : 30px;\">Bonjour<br/>" +
                                        //"Vos notes ont été publiées sur le centre étudiant (<a href=\"https://studentcenter.ucad.sn/\">Studentcenter</a>). " +
                                        //"Vous pouvez consulter votre relevé de notes et faire vos réclamations uniquement <b>sur " +
                                        //"les EC dont les moyennes sont gelées ou égales à zéro.</b><br/>" +
                                        //"Vous devez faire votre réclamation dans un délai <b>de 72 heures après publication des résultats.</b><br/>" +
                                        //"Si vous rencontrez des difficultés pour se connecter sur le centre étudiant merci de vous rapprocher du service informatique de votre établissement ou envoyer un mail à l'adresse <b>support@ucad.edu.sn.</b><br/>" +
                                        //"La vidéo ci-dessous vous montre comment faire une réclamation sur le centre étudiant.<br/>" +
                                        //"<a href=\"https://youtu.be/IwXxLnY5BHE\">Voir Vidéo</a></span>";
                                    String email_inst = null;
                                    String email_pers = null;
                                    while (iterator.hasNext()) {
                                        Row row = iterator.next();
                                        m.put("parcours", Long.parseLong(row.getAttribute("IdParcoursMaquetAnnee").toString()));
                                        m.put("numero", row.getAttribute("Numero").toString());
                                        m.put("id_semestre", Long.parseLong(row.getAttribute("IdSemestre").toString()));
                                        m.put("id_calendrier", Long.parseLong(row.getAttribute("IdCalendrierDeliberation").toString()));
                                        ByteArrayOutputStream baos = runReport("releves_standard_smstre_test.jasper", m);
                                        String obj = "Publication Résultat "+row.getAttribute("LibSemestre").toString()+" "+row.getAttribute("LibSession").toString();
                                        String text = "<span style =\"font-family : Century Gothic;font-size : 16px;line-height : 30px;\">Bonjour "+row.getAttribute("Prenoms")+" "+row.getAttribute("Nom")+"<br/>" +
                                            "Merci de trouver ci-joint votre relevé de notes provisoire.<br/>" +
                                            "Vous pouvez faire vos réclamations sur le centre étudiant <a href=\"https://studentcenter.ucad.sn/\">https://studentcenter.ucad.sn/</a> mais uniquement sur le <b>contrôle terminal gelé ou égal à zéro.</b>" +
                                            "<br/>La vidéo ci-dessous vous montre comment faire une réclamation sur le centre étudiant.<br/>" + 
                                            "<a href=\"https://disi.ucad.sn/fr/comment-faire-une-reclamation-de-notes-sur-student-center\">Voir Vidéo</a>" +
                                            "<br/><br/><b><i>La Direction de Affaires Pédagogiques</i></b>" +
                                            "</span>";
                                        //appModule.sendEmail("papeousseynou.ngom@ucad.edu.sn", obj, text,baos);
                                        //appModule.sendEmail("mohamadou.dieye@ucad.edu.sn", obj, text,baos);
                                        //appModule.sendEmail("fatou.dieng@ucad.edu.sn", obj, text,baos);
                                        
                                        //appModule.sendEmail("samba.diaw@ucad.edu.sn", obj, text,baos);
                                        //appModule.sendEmail("issa.sow@ucad.edu.sn", obj, text,baos);
                                        //appModule.sendEmail(row.getAttribute("NoReplay").toString(),"aladji.niang@ucad.edu.sn", 
                                                            //obj, text, baos);
                                        
                                        if (null != row.getAttribute("EmailInstitutionnel")) {
                                            email_inst = row.getAttribute("EmailInstitutionnel").toString();
                                        }
                                        //if (null != row.getAttribute("EmailPersonnel")) {
                                            //email_pers = row.getAttribute("EmailPersonnel").toString();
                                        //}
                                        try {
                                            if (null != email_inst) {
                                                appModule.sendEmail(row.getAttribute("NoReplay").toString(),email_inst, 
                                                                    obj, text, baos);
                                            }
                                            //if (null != email_pers) {
                                                //appModule.sendEmail(email_pers, obj, text, baos);
                                            //}
                                            appModule.UpdateToMailSem(Long.parseLong(row.getAttribute("IdResultatSemestre").toString()));
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    iterator.closeRowSetIterator();
                                } else {
                                    System.out.println("Erreur : View Object non trouvé !");
                                }
                                //
                            } catch (Exception e) {
                                System.out.println(e);
                            }
                            //appModule.sendEmail("aladji.niang@ucad.edu.sn", "PubNote", "<h1>Test</h1>",baos);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    // Libérer les ressources
                    Configuration.releaseRootApplicationModule(am, true);
                }, 1, 10, TimeUnit.HOURS);
         // Exécuter la tache toutes les heures pour les réclamation
        scheduler.scheduleAtFixedRate(() -> {
            System.out.println("Exécution périodique Réclamation : " + System.currentTimeMillis());
            ApplicationModule am =
                Configuration.createRootApplicationModule("model.services.gestdapApp", "gestdapAppLocal");
            if (am instanceof gestdapAppImpl) {
                gestdapAppImpl appModule = (gestdapAppImpl) am;
                String realpath = null;
                try {
                    //System.out.println("Geting path...");
                    realpath = context.getResource("/reports/").getPath();
                    //System.out.println("Path to reports : "+realpath);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                Map m = new HashMap();
                m.put("SUBREPORT_DIR", realpath);
                try {
                    try {
                        ViewObject vo = appModule.findViewObject("EtudiantsToMailRec");
                        if (vo != null) {
                            vo.clearCache();
                            vo.executeQuery();
                            RowSetIterator iterator = vo.createRowSetIterator(null);
                            String email_inst = null;
                            String email_pers = null;
                            while (iterator.hasNext()) {
                                Row row = iterator.next();
                                m.put("parcours", Long.parseLong(row.getAttribute("IdParcoursMaquetAnnee").toString()));
                                m.put("numero", row.getAttribute("Numero").toString());
                                m.put("id_semestre", Long.parseLong(row.getAttribute("IdSemestre").toString()));
                                m.put("id_calendrier", Long.parseLong(row.getAttribute("IdCalendrierDeliberation").toString()));
                                ByteArrayOutputStream baos = runReport("releves_standard_smstre_test.jasper", m);
                                String obj = "Publication Résultat "+row.getAttribute("LibSemestre").toString()+" "+row.getAttribute("LibSession").toString();
                                String text = "<span style =\"font-family : Century Gothic;font-size : 16px;line-height : 30px;\">Bonjour "+row.getAttribute("Prenoms")+" "+row.getAttribute("Nom")+"<br/>" +
                                    "Merci de trouver ci-joint votre relevé de notes provisoire.<br/>" +
                                    "Vous pouvez faire vos réclamations sur le centre étudiant <a href=\"https://studentcenter.ucad.sn/\">https://studentcenter.ucad.sn/</a> mais uniquement sur le <b>contrôle terminal gelé ou égal à zéro.</b>" +
                                    "<br/>La vidéo ci-dessous vous montre comment faire une réclamation sur le centre étudiant.<br/>" + 
                                    "<a href=\"https://disi.ucad.sn/fr/comment-faire-une-reclamation-de-notes-sur-student-center\">Voir Vidéo</a>" +
                                    "<br/><br/><b><i>La Direction de Affaires Pédagogiques</i></b>" +
                                    "</span>";
                                if (null != row.getAttribute("EmailInstitutionnel")) {
                                    email_inst = row.getAttribute("EmailInstitutionnel").toString();
                                }
                                
                                try {
                                    if (null != email_inst) {
                                        appModule.sendEmail(row.getAttribute("NoReplay").toString(),email_inst, 
                                                            obj, text, baos);
                                    }
                                    
                                    appModule.UpdateToMailSem(Long.parseLong(row.getAttribute("IdResultatSemestre").toString()));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            iterator.closeRowSetIterator();
                        } else {
                            System.out.println("Erreur : View Object non trouvé !");
                        }
                        //
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            // Libérer les ressources
            Configuration.releaseRootApplicationModule(am, true);
        }, 1, 2, TimeUnit.HOURS);
    }

    public void contextDestroyed(ServletContextEvent event) {
        context = event.getServletContext();
    }

    public ServletContext getContext() {
        return context;
    }
    public HttpServletResponse getResponse() {
        return (HttpServletResponse) getFacesContext().getExternalContext().getResponse();
    }
    
    public static FacesContext getFacesContext() {
        return FacesContext.getCurrentInstance();
    }
}
