/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.sga.controller;

import ec.edu.sga.facade.UsersFacade;
import ec.edu.sga.modelo.usuarios.Users;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import org.primefaces.context.RequestContext;

/**
 *
 * @author edison
 */
@Named(value = "loginController")
@SessionScoped
public class LoginController implements Serializable {

    @EJB
    private UsersFacade usersFacade;
    private Users users;

    /**
     * Creates a new instance of LoginController
     */
    public LoginController() {
        users = new Users();

    }

    public String login() {
        RequestContext context = RequestContext.getCurrentInstance();
        FacesMessage msg = null;
        boolean loggedIn = false;

        try {
            System.out.println("antes del primer if - el usuario es : " + users.getUsuario());
            if (usersFacade.buscarUser(users.getUsuario(), users.getPassword()) != null) {
                System.out.println("dentro del primer if");
                users = usersFacade.buscarUser(users.getUsuario(), users.getPassword());
            }
        } catch (Exception e) {
            return "/login/Login.xhtml";
        }


        System.out.println("fuera del primer if");

        System.out.println("Usuario:    " + users);
        if (users != null) {
            System.out.println("Valor del user:   " + users);
            loggedIn = true;
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Welcome", users.getUsuario());
            return "/index.xhtml";
        } else {
            loggedIn = false;
            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Login Error", "Invalid credentials");

        }

        FacesContext.getCurrentInstance().addMessage(null, msg);
        context.addCallbackParam("loggedIn", loggedIn);
        return "/login/Login.xhtml";
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }
}
