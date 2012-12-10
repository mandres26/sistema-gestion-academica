package ec.edu.sga.controller;

import ec.edu.sga.facade.MenuTipousuarioFacade;
import ec.edu.sga.modelo.usuarios.MenuTipousuario;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author juanmanuelmarchese
 */
@Named
@SessionScoped
public class MenuTipousuarioController {

    @EJB
    private MenuTipousuarioFacade dao;
    private MenuTipousuario selected;

    // ---------------------- Constructor de la Clase ----------------------

    public MenuTipousuarioController() {
    }

    public MenuTipousuario getSelected() {
        if (selected == null) { selected = new MenuTipousuario(); }
        return selected;
    } // Fin public Usuario getSelected

    // ---------------------- Métodos del Managed Bean ----------------------

    public String index() {
        return "/menu_tipousuario/index";
    } // Fin public String index

    public List<MenuTipousuario> listado() {        
        return dao.findAll();
    } // Fin public List<Usuario> listado

    public String create() {
        selected = new MenuTipousuario();
        return "/menu_tipousuario/new";
    } // Fin public String create

    public String agregar() {
        Date d = new Date();
        selected.setCreated(d);
        selected.setUpdated(d);
        dao.create(selected);
        return "/menu_tipousuario/index";
    } // Fin public String agregar

    public String edit(int codigo) {
        selected = dao.find(codigo);
        return "/menu_tipousuario/edit";
    } // Fin public Tipousuario edit

    public String guardar() {
        Date d = new Date();
        selected.setUpdated(d);
        dao.edit(selected);
        return "/menu_tipousuario/index";
    } // Fin public String guardar

    public String eliminar(int codigo) {
        selected = dao.find(codigo);
        dao.remove(selected);
        return "/menu_tipousuario/index";
    } // Fin public String eliminar

}
