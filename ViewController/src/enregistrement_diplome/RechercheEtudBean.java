package enregistrement_diplome;

import java.util.Map;

import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.component.rich.data.RichTable;

import oracle.jbo.uicli.binding.JUCtrlHierBinding;

import org.apache.myfaces.trinidad.model.CollectionModel;

public class RechercheEtudBean {
    private RichTable table;

    public RechercheEtudBean() {
    }

    public String onValueSelect() {
        // Add event code here...
        CollectionModel model = (CollectionModel) table.getValue();
        JUCtrlHierBinding tableBinding = (JUCtrlHierBinding) model.getWrappedData();
        //table synchronizes row selection with current binding row
        DCIteratorBinding tableIterator = tableBinding.getDCIteratorBinding();
        if (tableIterator.getCurrentRow() != null) {
            Object id_etud =
                tableIterator.getCurrentRow().getAttribute("IdEtudiant");
            //copy value into the pageFlowScope, which is returned in an task
            //flow param output
            ADFContext adfCtx = ADFContext.getCurrent();
            Map pageFlowScope = adfCtx.getPageFlowScope();
            pageFlowScope.put("id_etud", id_etud);
        }
        return "return";
    }

    public void setTable(RichTable table) {
        this.table = table;
    }

    public RichTable getTable() {
        return table;
    }
}
