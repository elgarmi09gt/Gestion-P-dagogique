package view.beans.login;

import java.util.Arrays;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import javax.security.auth.Subject;
import javax.security.auth.login.FailedLoginException;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import oracle.adf.share.ADFContext;
import oracle.adf.share.security.SecurityContext;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;
import oracle.adf.view.rich.context.AdfFacesContext;

import view.beans.admin.AdministrationBean;

import weblogic.security.URLCallbackHandler;
import weblogic.security.services.Authentication;

import weblogic.servlet.security.ServletAuthentication;

public class LoginBean {
    Map sessionScope = ADFContext.getCurrent().getSessionScope();
    private String _username, _password;
    private RichInputText anc_pass;
    private RichInputText new_pass;
    private RichInputText confirm_pass;
    private String ancpw, newpw, confirmpw;
    private static final String PASSWORD_PATTERN =
        "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";

    private static final Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
    private RichPanelGroupLayout panRoot;
    private RichPanelGroupLayout panMessage;

    public LoginBean() {
    }

    public void setUsername(String _username) {
        this._username = _username;
    }

    public String getUsername() {
        return _username;
    }

    public void setPassword(String _password) {
        this._password = _password;
    }

    public String getPassword() {
        return _password;
    }


    @SuppressWarnings("unchecked")
    public String Login() {
        FacesContext fctx = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) fctx.getExternalContext().getRequest();
        try {
            byte[] pw = _password.getBytes();
            Subject subject = Authentication.login(new URLCallbackHandler(_username, pw));
            ServletAuthentication.runAs(subject, request);
            ServletAuthentication.generateNewSessionID(request);

            ADFContext adfCtx = ADFContext.getCurrent();
            adfCtx.getSessionScope().put("userName", _username);
            adfCtx.getSessionScope().put("id_cal", 0);
            adfCtx.getSessionScope().put("is_parm_enter", false);
            adfCtx.getSessionScope().put("is_saisie_refrechable", 1);

            ExternalContext ectx = fctx.getExternalContext();
            ectx.redirect(ectx.getRequestContextPath() + "/adfAuthentication?success_url=/faces/menu.jsf");
            fctx.responseComplete();
        } catch (FailedLoginException e) {
            FacesMessage msg =
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login ou Mot de passe incorrect",
                                 "Login ou Mot de passe incorrect");
            fctx.addMessage(null, msg);
        } catch (Exception e) {
            e.printStackTrace(); // à remplacer par une vraie gestion d'erreur/log
        }

        return null;
    }

    public String Logout() {
        FacesContext fctx = FacesContext.getCurrentInstance();
        ExternalContext ectx = fctx.getExternalContext();
        String url = ectx.getRequestContextPath() + "/adfAuthentication?logout=true&end_url=/faces/login.jsf";

        HttpSession session = (HttpSession) ectx.getSession(false);
        session.invalidate();

        HttpServletRequest request = (HttpServletRequest) ectx.getRequest();
        ServletAuthentication.logout(request);
        ServletAuthentication.invalidateAll(request);
        ServletAuthentication.killCookie(request);
        this.setUsername("");
        this.setPassword("");
        try {
            ectx.redirect(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        fctx.responseComplete();

        return null;
    }

    @SuppressWarnings("unchecked")
    public void OnChangePassClicked(ActionEvent actionEvent) {
        Boolean matchAnc, matchNew, matchConfirm = false;
        String username = null;
        ADFContext adfCtx = ADFContext.getCurrent();
        SecurityContext secCntx = adfCtx.getSecurityContext();
        //System.out.println(secCntx.getUserPrincipal().getName().toString());
        username = secCntx.getUserPrincipal()
                          .getName()
                          .toString();
        Matcher matcher = pattern.matcher(getAncpw());
        //System.out.println("Ancien pass match : "+matcher.matches());
        matchAnc = matcher.matches();
        if (matchAnc) {
            matcher = pattern.matcher(getNewpw());
            //System.out.println("Nouveau pass match : " + matcher.matches());
            matchNew = matcher.matches();
            matcher = pattern.matcher(getConfirmpw());
            //System.out.println("Confirm pass match : " + matcher.matches());
            matchConfirm = matcher.matches();
            if (matchNew && matchConfirm) {
                if (this.getNewpw().equals(this.getConfirmpw())) {
                    //System.out.println("matchNew && matchConfirm");
                    Integer result = 0;
                    try {
                        result =
                            AdministrationBean.updatePassWord(username, this.getAncpw().toCharArray(),
                                                              this.getNewpw().toCharArray());
                        if (1 == result) {
                        } else if (2 == result) {
                        } else if (3 == result) {
                            //System.out.println("Ancien pas incorect");
                            sessionScope.put("message_", "Ancien mot de passe incorrecte !");
                            /*RichPopup popup = this.getPopPassIncorrect();
                            RichPopup.PopupHints hints = new RichPopup.PopupHints();
                            popup.show(hints);*/
                            this.setAncpw(null);
                        } else if (4 == result) {
                        }
                        Logout();
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                } else {
                    //System.out.println("matchNew && matchConfirm but not = ");
                    sessionScope.put("message_", "La confirmation de mot de passe est incorrecte !");
                    this.setNewpw(null);
                    this.setConfirmpw(null);
                }
            } else {
                //System.out.println("Saisir un mot de pass contenant... ");
                sessionScope.put("message_",
                                 "Saisir un mot de passe d'au moins 8 caractére composé de lettres, chiffres et caractére alpha numérique(!@#&?/*~$) !");
                this.setNewpw(null);
                this.setConfirmpw(null);
            }
        } else {
            //System.out.println("Revoir votre ancien pass");
            sessionScope.put("message_", "Ancien mot de passe incorrecte !");
            this.setAncpw(null);
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(getPanRoot());
        AdfFacesContext.getCurrentInstance().addPartialTarget(getPanMessage());
    }

    public void setAnc_pass(RichInputText anc_pass) {
        this.anc_pass = anc_pass;
    }

    public RichInputText getAnc_pass() {
        return anc_pass;
    }

    public void setNew_pass(RichInputText new_pass) {
        this.new_pass = new_pass;
    }

    public RichInputText getNew_pass() {
        return new_pass;
    }

    public void setConfirm_pass(RichInputText confirm_pass) {
        this.confirm_pass = confirm_pass;
    }

    public RichInputText getConfirm_pass() {
        return confirm_pass;
    }

    public void setAncpw(String ancpw) {
        this.ancpw = ancpw;
    }

    public String getAncpw() {
        return ancpw;
    }

    public void setNewpw(String newpw) {
        this.newpw = newpw;
    }

    public String getNewpw() {
        return newpw;
    }

    public void setConfirmpw(String confirmpw) {
        this.confirmpw = confirmpw;
    }

    public String getConfirmpw() {
        return confirmpw;
    }

    public void setPanRoot(RichPanelGroupLayout panRoot) {
        this.panRoot = panRoot;
    }

    public RichPanelGroupLayout getPanRoot() {
        return panRoot;
    }

    public void setPanMessage(RichPanelGroupLayout panMessage) {
        this.panMessage = panMessage;
    }

    public RichPanelGroupLayout getPanMessage() {
        return panMessage;
    }
}
