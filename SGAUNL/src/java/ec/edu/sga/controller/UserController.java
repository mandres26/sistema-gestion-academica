/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.sga.controller;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;

/**
 *
 * @author edison
 */
@Named(value = "userController")
@SessionScoped
public class UserController implements Serializable {

    /**
     * Creates a new instance of UserController
     */
    public UserController() {
    }
}
