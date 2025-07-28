package view.beans.evaluation.releves;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import java.sql.Connection;

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

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCIteratorBinding;

import oracle.binding.BindingContainer;
import java.util.Map;
import java.util.HashMap;

public class RelevesBean {
    public RelevesBean() {
    }

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }
    
    public void OnPrintRelevesClick(ActionEvent actionEvent) {
        System.out.println("ActionListener : Printing Releves ...");
        System.out.println("Action : Printing Releves ...");
        DCIteratorBinding locIter = (DCIteratorBinding) getBindings().get("EtudiantRelevesIterator");
        Integer pcrs = Integer.parseInt(locIter.getCurrentRow()
                                               .getAttribute("IdNiveauFormationParcours")
                                               .toString());
        Integer an = 2;
        String etu = locIter.getCurrentRow()
                            .getAttribute("Numero")
                            .toString();
        Integer hs = 5;
        System.out.println("Parcours : " + pcrs);
        System.out.println("Anne univers : " + an);
        System.out.println("Etudiant : " + etu);
        System.out.println("Historique Struct : " + hs);
        /*Map m = new HashMap();
        m.put("parcours", pcrs);
        m.put("annee_univers", an);
        m.put("num_etudiant", etu);
        m.put("id_hs", hs);
        try {
            runReport("releves.jasper", m);
            } catch (Exception e) {
        }*/
    }

    @SuppressWarnings({ "unchecked", "oracle.jdeveloper.java.insufficient-catch-block" })
    public String OnPrintRelevesClicked() {
        System.out.println("Action : Printing Releves ...");
        DCIteratorBinding locIter = (DCIteratorBinding) getBindings().get("EtudiantRelevesIterator");
        Integer pcrs = Integer.parseInt(locIter.getCurrentRow()
                                               .getAttribute("IdNiveauFormationParcours")
                                               .toString());
        Integer an = 2;
        String etu = locIter.getCurrentRow()
                            .getAttribute("Numero")
                            .toString();
        Integer hs = 5;
        /*System.out.println("Parcours : " + pcrs);
        System.out.println("Anne univers : " + an);
        System.out.println("Etudiant : " + etu);
        System.out.println("Historique Struct : " + hs);*/
        Map m = new HashMap();
        m.put("parcours", pcrs);
        m.put("annee_univers", an);
        m.put("num_etudiant", etu);
        m.put("id_hs", hs);
        try {
            runReport("releves.jasper", m);
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
        return getDataSourceConnection("refDS"); // datasource name should be defined in weblogic
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
