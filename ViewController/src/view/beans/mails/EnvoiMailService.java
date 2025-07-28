package view.beans.mails;


public class EnvoiMailService {
    public EnvoiMailService() {
        super();
    }
    
   /* 
    private String emailSender;
       
    private String mailSupport;
       
    private String mailObject;

    private JavaMailSender javaMailSender;
    private JdbcTemplate jdbcTemplate;
    private static final Logger logger = LoggerFactory.getLogger(PersonnelDTOService.class);??????
    public void recievedMessage(String  p) {
    ObjectMapper mapper = new ObjectMapper() ;
    try {
//PersonnelDTO p1 = mapper.readValue(p, PersonnelDTO.class) ;
    sendEmail(emailSender,p1);
//logger.info("Recieved Message From QUEUE : " + p1.getCodeMatricule()+"  "+p1.getEmailUcad());
    } catch (IOException | JRException | MessagingException e) {
e.printStackTrace();
throw new RuntimeException();
}
}

public void exportReportToByteOutPutStream(Integer an,Integer mm,String mat,ByteArrayOutputStream out) throws JRException, IOException {
    JasperPrint jasperPrint= exportPDF(an,mm,mat);
    JasperExportManager.exportReportToPdfStream(jasperPrint, out);
    out.flush();
    out.close();
}

public JasperPrint exportPDF(Integer an,Integer mm,String mat) throws FileNotFoundException, JRException {
try {
Connection connection = jdbcTemplate.getDataSource().getConnection();

InputStream fs = getClass().getResourceAsStream("/BulletinPortraitFinalsThies.jasper");
JasperReport jasperReport = (JasperReport) JRLoader.loadObject(fs);

Map<String,Object> parameters = new HashMap<>();
//parameters.put("p_id_ins_adm", id);
parameters.put("pmois", mm);
parameters.put("pannee", an);
parameters.put("pmatricule", mat);
//parameters.put("realPath",repPath);
parameters.put("realPath",null);
JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters,connection);
jasperPrint.setProperty("net.sf.jasperreports.export.pdf.encrypted", "True");
jasperPrint.setProperty("net.sf.jasperreports.export.pdf.128.bit.key", "True");
jasperPrint.setProperty("net.sf.jasperreports.export.pdf.permissions.allowed", "PRINTING");                  
jasperPrint.setProperty("net.sf.jasperreports.export.pdf.user.password", mat);????????????
connection.close();
return jasperPrint;
} catch (SQLException e) {
e.printStackTrace();
return null;
}
}
public void sendEmail(String from,PersonnelDTO p) throws IOException, JRException, AddressException, MessagingException {

String[] libMois = {"Janvier","Février","Mars","Avril","Mai","Juin","Juillet","Aôut","Septembre","Octobre","Novembre","Décembre"};
ByteArrayOutputStream baos = new ByteArrayOutputStream();
exportReportToByteOutPutStream(p.getAnnee(),p.getMois(),p.getCodeMatricule(),baos);

MimeMessage javaXmessage = javaMailSender.createMimeMessage();
javaXmessage.addFrom(InternetAddress.parse(from));
javaXmessage.setRecipients(javax.mail.Message.RecipientType.TO,
InternetAddress.parse(p.getEmailUcad()));
javaXmessage.setSubject(mailObject+" "+libMois[p.getMois()-1]+" "+p.getAnnee()+"");
MimeBodyPart textBodyPart = new MimeBodyPart();
textBodyPart.setText("Merci de recevoir votre bulletin de salaire mensuel numérisé ("+libMois[p.getMois()-1]+" "+p.getAnnee()+")." +" Utilisez votre matricule comme mot de passe pour l'ouvrir!" +
" Pour toute question, envoyer un message à "+mailSupport+".");

byte[] bytes = baos.toByteArray();
javax.mail.util.ByteArrayDataSource dataSourceFile = new ByteArrayDataSource(bytes, "application/pdf");
MimeBodyPart pdfBodyPart = new MimeBodyPart();
pdfBodyPart.setDataHandler(new DataHandler(dataSourceFile));
pdfBodyPart.setFileName("Bulletin mensuel "+libMois[p.getMois()-1]+" "+p.getAnnee()+".pdf");
MimeMultipart mimeMultipart = new MimeMultipart();
mimeMultipart.addBodyPart(textBodyPart);
mimeMultipart.addBodyPart(pdfBodyPart);
javaXmessage.setContent(mimeMultipart);
javaMailSender.send(javaXmessage);
}*/
}
