package view.beans.test;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import java.sql.Connection;

import java.util.HashMap;
import java.util.Map;

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

public class TestReportBean {
    public TestReportBean() {
    }

    @SuppressWarnings({ "unchecked", "oracle.jdeveloper.java.insufficient-catch-block" })
    public String runReport() {
        Long parcours = new Long(2);
        Long annee_univers = new Long(2);
        Long id_hs = new Long(5);
        Map m = new HashMap();
        m.put("parcours", parcours);
        m.put("annee_univers", annee_univers);
        m.put("id_hs", id_hs);
        try {
            runReport("attestation_simple.jasper", m);
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
}
