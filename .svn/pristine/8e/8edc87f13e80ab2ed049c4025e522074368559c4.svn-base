package certificat_inscription;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.OutputStream;

import java.sql.Connection;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import javax.faces.context.FacesContext;

import javax.naming.Context;
import javax.naming.InitialContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import javax.sql.DataSource;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.type.WhenNoDataTypeEnum;
import net.sf.jasperreports.engine.util.JRLoader;

import oracle.adf.model.BindingContext;
import oracle.adf.share.ADFContext;

import oracle.binding.BindingContainer;

public class CertificatBean {
    public CertificatBean() {
    }
    public BindingContainer getBindings() {
        return (BindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
    }
    @SuppressWarnings("unchecked")
    public void runReport(String repPath, java.util.Map param) throws Exception {
        System.out.println("Running ...");
            
        System.out.println("getConnection() : "+getConnection());

        System.out.println("avant ");
        
        Connection conn = null;
        Map sessionScope = ADFContext.getCurrent().getSessionScope();
        
        
        try {
            HttpServletResponse response = getResponse();
            ServletOutputStream out = response.getOutputStream();
            response.setHeader("Cache-Control", "max-age=0");
            response.setContentType("application/pdf");
                            
                                
            FacesContext facesContext = FacesContext.getCurrentInstance();
            ServletContext servletContext = (ServletContext) facesContext.getExternalContext().getContext();
            String path = servletContext.getRealPath(repPath);
            ServletContext context = getContext();
            System.out.println("path "+path);
            ///InputStream stream = this.getClass().getClassLoader().getResourceAsStream(path);
                                
                //ServletContext context = getContext();
            //InputStream fs = context.getResourceAsStream(repPath);
            FileInputStream fs = new FileInputStream(path);
            JasperReport template = (JasperReport) JRLoader.loadObject(fs);
            template.setWhenNoDataType(WhenNoDataTypeEnum.ALL_SECTIONS_NO_DETAIL);
            
            /*JasperReport jasperReport
              = JasperCompileManager.compileReport(path);*/
            ///JRSaver.saveObject(jasperReport, "/reports/attestation_stand_bis.jasper");
            
            conn = getConnection();
            @SuppressWarnings("unchecked")
            JasperPrint print = JasperFillManager.fillReport(template, param, conn);
            //Affichage du report
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            JasperExportManager.exportReportToPdfStream(print, baos);
            out.write(baos.toByteArray());
            //téléchargement du report direct
            //JasperExportManager.exportReportToPdfFile(print,file);
            //JasperExportManager.exportReportToPdfFile(printFileName, "C://sample_report.pdf");
            
            //RichPopup popup = this.getPopupSuccessReport();
            //RichPopup.PopupHints hints = new RichPopup.PopupHints();
            //popup.show(hints);
            
            out.flush();
            out.close();
    
            FacesContext.getCurrentInstance().responseComplete();
            
        } catch (Exception jex) {
            System.out.println("Erreur");
            jex.printStackTrace();
        } finally {
            System.out.println("Finnish");
            close(conn);
        }
            //}
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

    public void onEditerCertificat(FacesContext facesContext, OutputStream outputStream) {
        // Add event code here...
        //id_insc_certificat_insc
        Map sessionScope = ADFContext.getCurrent().getSessionScope();
        Map m = new HashMap();
        System.out.println("sessionScope.get(\"id_insc_certificat_insc\").toString()"+sessionScope.get("id_insc_certificat_insc"));
        m.put("id_insc_ped", Long.parseLong(sessionScope.get("id_insc_certificat_insc").toString()));
        m.put("id_insc_ped_encrypt", encrypt(sessionScope.get("id_insc_certificat_insc").toString()));
        System.out.println("id_insc_ped "+sessionScope.get("id_insc_certificat_insc").toString());
        System.out.println("id_insc_ped_encrypt " +encrypt(sessionScope.get("id_insc_certificat_insc").toString()));
        try {
            runReport("/reports/cerification_inscription.jasper",m);
        } catch (Exception e) {
        }

    }
    
    private static final String encryptionKey           = "ABCDEFGHIJKLMNOP";
    private static final String characterEncoding       = "UTF-8";
    private static final String cipherTransformation    = "AES/CBC/PKCS5PADDING";
    private static final String aesEncryptionAlgorithem = "AES";
    
    
    /**
     * Method for Encrypt Plain String Data
     * @param plainText
     * @return encryptedText
     */
    public static String encrypt(String plainText) {
        String encryptedText = "";
        try {
            Cipher cipher   = Cipher.getInstance(cipherTransformation);
            byte[] key      = encryptionKey.getBytes(characterEncoding);
            SecretKeySpec secretKey = new SecretKeySpec(key, aesEncryptionAlgorithem);
            IvParameterSpec ivparameterspec = new IvParameterSpec(key);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivparameterspec);
            byte[] cipherText = cipher.doFinal(plainText.getBytes("UTF8"));
            Base64.Encoder encoder = Base64.getEncoder();
            encryptedText = encoder.encodeToString(cipherText);

        } catch (Exception E) {
             System.err.println("Encrypt Exception : "+E.getMessage());
        }
        return encryptedText;
    }

    /**
     * Method For Get encryptedText and Decrypted provided String
     * @param encryptedText
     * @return decryptedText
     */
    public static String decrypt(String encryptedText) {
        String decryptedText = "";
        try {
            Cipher cipher = Cipher.getInstance(cipherTransformation);
            byte[] key = encryptionKey.getBytes(characterEncoding);
            SecretKeySpec secretKey = new SecretKeySpec(key, aesEncryptionAlgorithem);
            IvParameterSpec ivparameterspec = new IvParameterSpec(key);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivparameterspec);
            Base64.Decoder decoder = Base64.getDecoder();
            byte[] cipherText = decoder.decode(encryptedText.getBytes("UTF8"));
            decryptedText = new String(cipher.doFinal(cipherText), "UTF-8");

        } catch (Exception E) {
            System.err.println("decrypt Exception : "+E.getMessage());
        }
        return decryptedText;
    }
}
