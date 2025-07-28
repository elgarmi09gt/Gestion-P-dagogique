package view.beans.resetpass;

import javax.faces.event.ActionEvent;

import model.services.gestdapAppImpl;

import oracle.adf.view.rich.component.rich.RichPopup;

import oracle.jbo.ApplicationModule;
import oracle.jbo.client.Configuration;

public class resetBean {
    private RichPopup popEmailSended;

    public resetBean() {
    }
    String username;
    public void envoyerEmailReset(ActionEvent actionEvent) {
        System.out.println(username);
        ApplicationModule am = Configuration.createRootApplicationModule("model.services.gestdapApp", "gestdapAppLocal");

        if (am instanceof gestdapAppImpl) {
            gestdapAppImpl appModule = (gestdapAppImpl) am;
            boolean succes = appModule.genererJetonEtEnvoyerEmail(username);
            if (succes) {
                System.out.println("Email envoyé !");
                RichPopup popup = this.getPopEmailSended();
                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                popup.show(hints);
            } else {
                System.out.println("Utilisateur introuvable !");
            }
        }
        Configuration.releaseRootApplicationModule(am, true);
        }

        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }

    public void setPopEmailSended(RichPopup popEmailSended) {
        this.popEmailSended = popEmailSended;
    }

    public RichPopup getPopEmailSended() {
        return popEmailSended;
    }
}
