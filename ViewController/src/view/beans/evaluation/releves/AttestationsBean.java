package view.beans.evaluation.releves;

import javax.faces.event.ActionEvent;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import java.sql.Connection;

import java.util.HashMap;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

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

import oracle.adf.model.AttributeBinding;
import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.rich.component.rich.data.RichTable;

import oracle.adf.view.rich.component.rich.output.RichPanelCollection;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Row;

public class AttestationsBean {
    private RichPanelCollection etuAttestation;

    public AttestationsBean() {
    }

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    @SuppressWarnings({ "oracle.jdeveloper.java.insufficient-catch-block", "unchecked" })
    public void OnPrintAttestationClicked(ActionEvent actionEvent) {
        //System.out.println("Printing ActionListener Attestation...");
        DCIteratorBinding locIter = (DCIteratorBinding) getBindings().get("EtudiantAttestationIterator");
        Integer pcrs = Integer.parseInt(locIter.getCurrentRow().getAttribute("IdNiveauFormationParcours").toString()) ;
        Integer an = 2 ;
        String etu = locIter.getCurrentRow().getAttribute("Numero").toString() ;
        Integer hs = 5 ;
        /*nfp.ID_NIVEAU_FORMATION_PARCOURS=$P{parcours} 
         * AND ia.ID_ANNEES_UNIVERS=$P{annee_univers}
         * etu.NUMERO=$P{num_etudiant} 
         * AND hs.ID_HISTORIQUES_STRUCTURE=$P{id_hs}
         * AND ra.RESULTAT=1
         * 
        System.out.println("Parcours : "+pcrs);
        System.out.println("Anne univers : "+an);
        System.out.println("Etudiant : "+etu);
        System.out.println("Historique Struct : "+hs);*/
        Map m = new HashMap();
        /*m.put("parcours", pcrs);
        m.put("annee_univers", an);
        m.put("num_etudiant", etu);
        m.put("id_hs", hs);
        try {
            runReport("attestation.jasper", m);
        } catch (Exception e) {
        }*/
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

    public String OnPrintActionClick() {
        //System.out.println("Printing Action Attestation...");
        DCIteratorBinding locIter = (DCIteratorBinding) getBindings().get("EtudiantAttestationIterator");
        Integer pcrs = Integer.parseInt(locIter.getCurrentRow()
                                               .getAttribute("IdNiveauFormationParcours")
                                               .toString());
        Integer an = Integer.parseInt(locIter.getCurrentRow()
                                             .getAttribute("IdAnneesUnivers")
                                             .toString());
        String etu = locIter.getCurrentRow()
                            .getAttribute("Numero")
                            .toString();
        Integer hs = Integer.parseInt(locIter.getCurrentRow()
                                             .getAttribute("IdHistoriquesStructure")
                                             .toString());
        /*System.out.println("Parcours : " + pcrs);
        System.out.println("Anne univers : " + an);
        System.out.println("Etudiant : " + etu);
        System.out.println("Historique Struct : " + hs);*/
        return null;
    }

    @SuppressWarnings("unchecked")
    public void OnParcoursValueChange(ValueChangeEvent valueChangeEvent) {
        BindingContext cntx = BindingContext.getCurrent();
        DCBindingContainer cbinding = (DCBindingContainer) cntx.getCurrentBindingsEntry();
        UIComponent comp = valueChangeEvent.getComponent();
        comp.processUpdates(FacesContext.getCurrentInstance());
        //get current row and save its rowKey in view scope for later use
        DCIteratorBinding dciter = (DCIteratorBinding) cbinding.get("ParcoursIterator");
        Row currentDept = dciter.getCurrentRow();
        System.out.print("Parcours " + currentDept.getAttribute("IdNiveauFormationParcours"));
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("ExecuteWithParams");
        operationBinding.getParamsMap().put("anne", 2);
        operationBinding.getParamsMap().put("id_hs", 5);
        operationBinding.getParamsMap().put("parcours", Integer.parseInt(currentDept.getAttribute("IdNiveauFormationParcours").toString()));
        Object result = operationBinding.execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getEtuAttestation());
    }

    public void setEtuAttestation(RichPanelCollection etuAttestation) {
        this.etuAttestation = etuAttestation;
    }

    public RichPanelCollection getEtuAttestation() {
        return etuAttestation;
    }

    public void Test(ActionEvent actionEvent) {
        // Add event code here... ParcoursIterator
        DCIteratorBinding locIter = (DCIteratorBinding) getBindings().get("ParcoursIterator");
        String etu = locIter.getCurrentRow()
                            .getAttribute("LibParcours")
                            .toString();
        System.out.println("LibParcours : "+etu);
    }
}
