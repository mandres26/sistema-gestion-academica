package ec.edu.sga.controller;

import ec.edu.sga.controller.util.SessionUtil;
import ec.edu.sga.facade.MenuFacade;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.SelectItem;
import ec.edu.sga.modelo.usuarios.Menu;
/**
 *
 * @author juanmanuelmarchese
 */
@ManagedBean
@SessionScoped
public class MenuController {

    @EJB
    private MenuFacade dao;
    private Menu selected;

    // ---------------------- Constructor de la Clase ----------------------

    public MenuController() {
    }

    public Menu getSelected() {
        if (selected == null) { selected = new Menu(); }
        return selected;
    }

    // ---------------------- Métodos del Managed Bean ----------------------

    public String index() {
        return "/menu/index";
    }

    public List<Menu> listado() {
        return dao.findAllOrderMenu();
    }

    public String create() {
        selected = new Menu();
        return "/menu/new";
    } // Fin public String create

    public String agregar() {
        Date d = new Date();
        selected.setCreated(d);
        selected.setUpdated(d);
        dao.create(selected);
        return "/menu/index";
    } // Fin public String agregar

    public String edit(int codigo) {
        selected = dao.find(codigo);
        return "/menu/edit";
    }

    public String guardar() {
        Date d = new Date();
        selected.setUpdated(d);
        dao.edit(selected);
        return "/menu/index";
    } // Fin public String guardar

    public String eliminar(int codigo) {
        selected = dao.find(codigo);
        try { dao.remove(selected); }
        catch (Exception e) { SessionUtil.addErrorMessage("No se puede eliminar, posibles datos asociados"); }
        return "/menu/index";
    } // Fin public String eliminar

    // --------------------- Métodos de Ayuda para acceder al Bean por otras Clases ---------------------

    public SelectItem[] getItemsAvailableSelectOne() {
        return getSelectItems(dao.findAll(), true);
    }

    // Genera una lista con los items seleccionados (uno o muchos según selectOne). Para tablas relacionadas.
    public static SelectItem[] getSelectItems(List<?> entities, boolean selectOne) {

        int size = selectOne ? entities.size() + 1 : entities.size();
        SelectItem[] items = new SelectItem[size];
        int i = 0;
        if (selectOne) {
            items[0] = new SelectItem("", "---");
            i++;
        }
        for (Object x : entities) {
            items[i++] = new SelectItem(x, x.toString());
        }
        return items;

    }

  

}
