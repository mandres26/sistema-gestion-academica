/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.sga.controller;

import ec.edu.sga.facade.UsersFacade;
import ec.edu.sga.modelo.usuarios.Users;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
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

    /**
     * Creates a new instance of LoginController
     */
   
    Users users;
     public LoginController() {
         
    }

    public void login(ActionEvent actionEvent) {
        RequestContext context = RequestContext.getCurrentInstance();
        FacesMessage msg = null;
        boolean loggedIn = false;
        users = usersFacade.buscarUser(users);
        System.out.println("Usuario:    "+users.getUsuario());
        if (users != null) {
            System.out.println("Valor del user:   "+users.getUsuario());
            loggedIn = true;
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Welcome", users.getUsuario());
        } else {
            loggedIn = false;
            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Login Error", "Invalid credentials");
        }

        FacesContext.getCurrentInstance().addMessage(null, msg);
        context.addCallbackParam("loggedIn", loggedIn);
    }

    public Users getUsers() {
        if (users == null) {
            users = new Users();
        }
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }
}
