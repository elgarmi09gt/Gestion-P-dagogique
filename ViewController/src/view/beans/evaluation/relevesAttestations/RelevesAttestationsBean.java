package view.beans.evaluation.relevesAttestations;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.Serializable;

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

import oracle.adf.controller.TaskFlowId;
import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;

import oracle.binding.BindingContainer;

import oracle.jbo.Row;

public class RelevesAttestationsBean implements Serializable {
    private String taskFlowId = "/evaluation/relevesAttestations/releves-tf.xml#releves-tf";
    private Map<String, Integer> parameterMap = new HashMap<String, Integer>();
    private Integer id_parcours;

    public RelevesAttestationsBean() {
        setParameterVal();
    }

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public TaskFlowId getDynamicTaskFlowId() {
        return TaskFlowId.parse(taskFlowId);
    }

    public void setDynamicTaskFlowId(String taskFlowId) {
        this.taskFlowId = taskFlowId;
    }

    public String relevestf() {
        BindingContext cntx = BindingContext.getCurrent();
        DCBindingContainer cbinding = (DCBindingContainer) cntx.getCurrentBindingsEntry();
        DCIteratorBinding dciter = (DCIteratorBinding) cbinding.get("ParcoursIterator");
        Row currentParcours = dciter.getCurrentRow();
        id_parcours = Integer.parseInt(currentParcours.getAttribute("IdNiveauFormationParcours").toString());
        System.out.println("id_parcours : " + id_parcours);
        setParameterVal();
        setDynamicTaskFlowId("/evaluation/relevesAttestations/releves-tf.xml#releves-tf");
        return null;
    }

    public String attestationsTF() {
        BindingContext cntx = BindingContext.getCurrent();
        DCBindingContainer cbinding = (DCBindingContainer) cntx.getCurrentBindingsEntry();
        DCIteratorBinding dciter = (DCIteratorBinding) cbinding.get("ParcoursIterator");
        Row currentParcours = dciter.getCurrentRow();
        id_parcours = Integer.parseInt(currentParcours.getAttribute("IdNiveauFormationParcours").toString());
        System.out.println("id_parcours : " + id_parcours);
        setParameterVal();
        setDynamicTaskFlowId("/evaluation/relevesAttestations/attestations-TF.xml#attestations-TF");
        return null;
    }

    public void setParameterMap(Map<String, Integer> parameterMap) {
        this.parameterMap = parameterMap;
    }

    public Map<String, Integer> getParameterMap() {
        return parameterMap;
    }

    public void setId_parcours(Integer id_parcours) {
        this.id_parcours = id_parcours;
    }

    public Integer getId_parcours() {
        return id_parcours;
    }

    private void setParameterVal() {
        parameterMap.put("id_annee", 2);
        parameterMap.put("id_parcours", getId_parcours());
        parameterMap.put("id_hs", 5);
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

    @SuppressWarnings({ "unchecked", "oracle.jdeveloper.java.insufficient-catch-block" })
    public String OnPrintAttestationClicked() {
        System.out.println("Printing Attestation...");
        DCIteratorBinding locIter = (DCIteratorBinding) getBindings().get("ParcoursIterator");
        Row currentetu = locIter.getCurrentRow();
        Long parcours = Long.parseLong(currentetu.getAttribute("IdNiveauFormationParcours").toString());
        System.out.println("Parcours : "+parcours);
        Long anne_univers = new Long(2);
        Long id_hs = new Long(5);
        Map m = new HashMap();
        m.put("parcours", parcours);
        m.put("annee_univers", anne_univers);
        m.put("id_hs", id_hs);
        try {
            runReport("attestation.jasper", m);
        } catch (Exception e) {
        }
        return null;
    }

    @SuppressWarnings({ "unchecked", "oracle.jdeveloper.java.insufficient-catch-block" })
    public String OnPrintRelevesClicked() {
        System.out.println("Printing Releves...");
        DCIteratorBinding locIter = (DCIteratorBinding) getBindings().get("ParcoursIterator");
        Row currentetu = locIter.getCurrentRow();
        Long parcours = Long.parseLong(currentetu.getAttribute("IdNiveauFormationParcours").toString());
        System.out.println("Parcours : "+parcours);
        Long anne_univers = new Long(2);
        Long id_hs = new Long(5);
        Map m = new HashMap();
        m.put("parcours", parcours);
        m.put("annee_univers", anne_univers);
        m.put("id_hs", id_hs);
        try {
            runReport("releves_standard.jasper", m);
        } catch (Exception e) {
        }
        return null;
    }
}
