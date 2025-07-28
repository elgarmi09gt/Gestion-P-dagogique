package view.beans.evaluation.etats;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import java.sql.Connection;

import java.util.HashMap;
import java.util.Map;

import javax.faces.context.FacesContext;

import javax.faces.event.ActionEvent;

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

import oracle.adf.share.ADFContext;

public class EtatsBean {
    Map sessionScope = ADFContext.getCurrent().getSessionScope();
    private Long parcours = Long.parseLong(sessionScope.get("id_niv_form_parcours").toString());
    private Long anne_univers = Long.parseLong(sessionScope.get("id_annee").toString());
    private Long id_hs = Long.parseLong(sessionScope.get("id_smstre").toString());
    public EtatsBean() {
    }

    @SuppressWarnings({ "oracle.jdeveloper.java.insufficient-catch-block", "unchecked" })
    public String OnPrintReleves() {
        Map m = new HashMap();
        m.put("parcours", getParcours());
        m.put("annee_univers", getAnne_univers());
        m.put("id_hs", getId_hs());
        try {
            runReport("releves_standard.jasper", m);
        } catch (Exception e) {
        }
        return null;
    }

    @SuppressWarnings({ "unchecked", "oracle.jdeveloper.java.insufficient-catch-block" })
    public String OnPrintAttestation() {
        Map m = new HashMap();
        m.put("parcours", getParcours());
        m.put("annee_univers", getAnne_univers());
        m.put("id_hs", getId_hs());
        try {
            runReport("attestation_stand_bis.jasper", m);
        } catch (Exception e) {
        }
        return null;
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
    
    public void runReport(String repPath, java.util.Map param) throws Exception {
        System.out.println("Running ...");
            Connection conn = null;
            try {
                HttpServletResponse response = getResponse();
                ServletOutputStream out = response.getOutputStream();
                response.setHeader("Cache-Control", "max-age=0");
                response.setContentType("application/pdf");
                ServletContext context = getContext();
                InputStream fs = context.getResourceAsStream("/reports/" + repPath);
                JasperReport template = (JasperReport) JRLoader.loadObject(fs);
                template.setWhenNoDataType(WhenNoDataTypeEnum.ALL_SECTIONS_NO_DETAIL);
                conn = getConnection();
                @SuppressWarnings("unchecked")
                JasperPrint print = JasperFillManager.fillReport(template, param, conn);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                JasperExportManager.exportReportToPdfStream(print, baos);
                out.write(baos.toByteArray());
                out.flush();
                out.close();
                FacesContext.getCurrentInstance().responseComplete();
            } catch (Exception jex) {
                jex.printStackTrace();
            } finally {
                System.out.println("Finnish");
                close(conn);
            }
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

    public void setParcours(Long parcours) {
        this.parcours = parcours;
    }

    public Long getParcours() {
        return parcours;
    }

    public void setAnne_univers(Long anne_univers) {
        this.anne_univers = anne_univers;
    }

    public Long getAnne_univers() {
        return anne_univers;
    }

    public void setId_hs(Long id_hs) {
        this.id_hs = id_hs;
    }

    public Long getId_hs() {
        return id_hs;
    }

    @SuppressWarnings({ "oracle.jdeveloper.java.insufficient-catch-block", "unchecked" })
    public void OnPrintAttestation(ActionEvent actionEvent) {
        Map m = new HashMap();
        m.put("parcours", getParcours());
        m.put("annee_univers", getAnne_univers());
        m.put("id_hs", getId_hs());
        try {
            runReport("attestation_stand_bis.jasper", m);
        } catch (Exception e) {
        }
    }
}
