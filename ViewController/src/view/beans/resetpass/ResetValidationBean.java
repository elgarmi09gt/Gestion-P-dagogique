package view.beans.resetpass;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.Hashtable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import javax.naming.Context;

@ManagedBean(name = "resetValidationBean")
@RequestScoped
public class ResetValidationBean {

    private String nouveauMotDePasse;

    public void reinitialiserMotDePasse() {
        FacesContext fc = FacesContext.getCurrentInstance();
        String token = fc.getExternalContext().getRequestParameterMap().get("token");

        try (Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@//host:port/service", "user", "password")) {
            // Vérifier le jeton
            String sql = "SELECT username FROM PASSWORD_RESET_TOKENS WHERE token = ?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, token);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    String username = rs.getString("username");
                    // Exécuter WLST pour changer le mot de passe LDAP
                    resetPassword(username, nouveauMotDePasse);
                    System.out.println("Mot de passe changé !");
                } else {
                    System.out.println("Jeton invalide !");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /*public static boolean resetPassword(String username, String newPassword) {
            try {
                JpsContextFactory jps = JpsContextFactory.getContextFactory();
                JpsContext jpsContext = jps.getContext();
                IdentityStoreService storeService = jpsContext.getServiceInstance(IdentityStoreService.class);
                IdentityStore identityStore = storeService.getIdmStore();
                User user = (User) identityStore.searchUser(username.toLowerCase());
                // 1?? Charger le contexte JPS
                //JpsContext jpsContext = JpsContextFactory.getContextFactory().getContext();

                // 2?? Récupérer l'IdentityStore (LDAP)
                //IdentityStore identityStore = jpsContext.getServiceInstance(IdentityStore.class);

                // 3?? Vérifier si l'utilisateur existe
                //UserProfile user = identityStore.getUserProfile(username);
                if (user == null) {
                    System.out.println("Utilisateur non trouvé : " + username);
                    return false;
                }

                // 4?? Mettre à jour le mot de passe
                identityStore.updateCredential(username, newPassword.toCharArray());
                identityStore.
                System.out.println("Mot de passe mis à jour pour : " + username);
                return true;
            } catch (IdentityStoreException e) {
                e.printStackTrace();
                return false;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }*/

    public static boolean resetPassword(String username, String newPassword) {
        try {
            // 1?? Connexion au serveur MBean
            String host = "localhost"; // Remplace par l'URL WebLogic
            String port = "7101"; // Port WebLogic
            String adminUser = "weblogic"; // Utilisateur WebLogic Admin
            String adminPassword = "weblogic123"; // Mot de passe Admin

            Hashtable<String, String> env = new Hashtable<>();
            env.put(Context.SECURITY_PRINCIPAL, adminUser);
            env.put(Context.SECURITY_CREDENTIALS, adminPassword);
            env.put(JMXConnector.CREDENTIALS, adminUser + ":" + adminPassword);
            env.put(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");

            String jmxURL = "service:jmx:t3://" + host + ":" + port + "/jndi/weblogic.management.mbeanservers.domainruntime";
            JMXServiceURL serviceURL = new JMXServiceURL(jmxURL);
            JMXConnector connector = JMXConnectorFactory.connect(serviceURL, env);
            MBeanServerConnection mbeanServer = connector.getMBeanServerConnection();

            // 2?? Récupérer l'authentificateur WebLogic (DefaultAuthenticator)
            ObjectName authenticatorName = new ObjectName("com.bea:Name=DefaultAuthenticator,Type=weblogic.management.security.authentication.UserPasswordEditorMBean");

            // 3?? Modifier le mot de passe de l'utilisateur LDAP
            mbeanServer.invoke(authenticatorName, "changeUserPassword",
                    new Object[]{username, null, newPassword}, // null = pas besoin de l'ancien mot de passe
                    new String[]{"java.lang.String", "java.lang.String", "java.lang.String"});

            System.out.println("Mot de passe changé avec succès pour : " + username);
            connector.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getNouveauMotDePasse() { return nouveauMotDePasse; }
    public void setNouveauMotDePasse(String nouveauMotDePasse) { this.nouveauMotDePasse = nouveauMotDePasse; }

    public void reinitialiserMotDePasse(ActionEvent actionEvent) {
        // Add event code here...
        reinitialiserMotDePasse();
    }
}

