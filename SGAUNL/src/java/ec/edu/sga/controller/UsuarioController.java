/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.sga.controller;

import ec.edu.sga.controller.util.JsfUtil;
import ec.edu.sga.controller.util.PaginationHelper;
import ec.edu.sga.facade.UsuarioFacade;
import ec.edu.sga.modelo.usuarios.Ficha;
import ec.edu.sga.modelo.usuarios.FichaMedica;
import ec.edu.sga.modelo.usuarios.FichaPersonal;
import ec.edu.sga.modelo.usuarios.FichaSocioeconomica;
import ec.edu.sga.modelo.usuarios.Usuario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author edison
 */
@Named(value = "usuarioController")
@ConversationScoped
public class UsuarioController implements Serializable{

    private Usuario current;
//    private Ficha ficha;
//    private FichaPersonal fichaP;
//    private FichaMedica fichaM;
//    private FichaSocioeconomica fichaS;
    private DataModel items = null;
    @EJB
    private ec.edu.sga.facade.UsuarioFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private List<Usuario> resultlist;
    @Inject
    Conversation conversation;
    
    public UsuarioController() {
        current= new Usuario();
//        ficha= new Ficha();
//        fichaP= new FichaPersonal();
//        fichaM= new FichaMedica();
//        fichaS= new FichaSocioeconomica();
        
        resultlist= new ArrayList<Usuario>();
                
        
    }

//    public String find() {
//        System.out.println("Ingreso a buscar con criterio: " + criterio);
//        resultList = ejbFacade.buscarPorClave(criterio);
//
//        for (Estudiante estudiante : resultList) {
//            System.out.println(estudiante);
//
//        }
//        String summary = "Encontrado Correctamente!";
//        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null));
//        //puedo hacer retornar a la pagina q se quiera
//        return "/estudiante/List";
//
//    }
//    public List<Estudiante> findCompleteBrand(String query) {
//        System.out.println("====INGRESO A BUSCAR AUTOCOMPLETE CON::" + query);
//        return ejbFacade.buscarPorClave(query);
//    }
//    public void agregarContacto() {
//        Contacto c = new Contacto();
//        current.agregar(c);
//    }
    public String createInstance() {
        //return "/vehicle/Edit?faces-redirect=true";
        System.out.println("========> INGRESO a Crear Instance estudiante: " + current.getNombres());
        this.current = new Usuario();
        return "/estudiante/Edit?faces-redirect=true";
        //return "/vehicle/BrandEdit";
    }

    public String persist() {

        System.out.println("========> INGRESO a Grabar nuevo Usuario: " + current.getNombres());
        ejbFacade.create(current);
        this.endConversation();

        String summary = ResourceBundle.getBundle("/Bundle").getString("EstudianteCreated");
        JsfUtil.addSuccessMessage(summary);
        //FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null));
        FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);

        return "/estudiante/Created?faces-redirect=true";
        //return "/vehicle/BrandList";

    }

    public String update() {

        System.out.println("========> INGRESO a Actualizar al Estudiante: " + current.getNombres());
        ejbFacade.edit(current);
        System.out.println("ya modifique");
        this.endConversation();

        String summary = ResourceBundle.getBundle("/Bundle").getString("EstudianteUpdated");
        FacesContext.getCurrentInstance().addMessage("successInfo", new FacesMessage(FacesMessage.SEVERITY_INFO, summary, summary));
        FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);

        return "/estudiante/List?faces-redirect=true";

    }

    public String delete() {
        System.out.println("========> INGRESO a Eliminar Estudiante: " + current.getNombres());
        ejbFacade.remove(current);

        // this.find();

        this.endConversation();

        String summary = "Estudiante Eliminado Correctamente!";
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null));


        return "/estudiante/List?faces-redirect=true";

    }

    public String cancelEdit() {
        System.out.println("me acaban de llamar: canceledit()");
        this.endConversation();
        return "/estudiante/List?faces-redirect=true";
    }

    public void beginConversation() {
        if (conversation.isTransient()) {
            conversation.begin();
            System.out.println("========> INICIANDO CONVERSACION: ");
        }
    }

    public void endConversation() {
        if (!conversation.isTransient()) {
            conversation.end();
            System.out.println("========> FINALIZANDO CONVERSACION: ");
        }
    }

    public Usuario getSelected() {
        if (current == null) {
            current = new Usuario();
            selectedItemIndex = -1;
        }
        return current;
    }

    public Usuario getCurrent() {
        return current;
    }

    public void setCurrent(Usuario current) {
        System.out.println("========> INGRESO a fijar Estudiante: " + current);
        this.beginConversation();
        this.current = current;
    }

    public UsuarioFacade getEjbFacade() {
        return ejbFacade;
    }

    public void setEjbFacade(UsuarioFacade ejbFacade) {
        this.ejbFacade = ejbFacade;
    }

    public int getSelectedItemIndex() {
        return selectedItemIndex;
    }

    public void setSelectedItemIndex(int selectedItemIndex) {
        this.selectedItemIndex = selectedItemIndex;
    }

    public List<Usuario> getResultlist() {
        return resultlist;
    }

    public void setResultlist(List<Usuario> resultlist) {
        this.resultlist = resultlist;
    }

    private UsuarioFacade getFacade() {
        return ejbFacade;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(10) {
                @Override
                public int getItemsCount() {
                    return getFacade().count();
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}));
                }
            };
        }
        return pagination;
    }

    public String prepareList() {
        recreateModel();
        return "List";
    }

    public String prepareView() {
        current = (Usuario) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new Usuario();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("EstudianteCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (Usuario) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String destroy() {
        current = (Usuario) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
        return "List";
    }

    public String destroyAndView() {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "View";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "List";
        }
    }

    private void performDestroy() {
        try {
            getFacade().remove(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("EstudianteDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    private void updateCurrentItem() {
        int count = getFacade().count();
        if (selectedItemIndex >= count) {
            // selected index cannot be bigger than number of items:
            selectedItemIndex = count - 1;
            // go to previous page if last page disappeared:
            if (pagination.getPageFirstItem() >= count) {
                pagination.previousPage();
            }
        }
        if (selectedItemIndex >= 0) {
            current = getFacade().findRange(new int[]{selectedItemIndex, selectedItemIndex + 1}).get(0);
        }
    }

    public DataModel getItems() {
        if (items == null) {
            items = getPagination().createPageDataModel();
        }
        return items;
    }

    private void recreateModel() {
        items = null;
    }

    private void recreatePagination() {
        pagination = null;
    }

    public String next() {
        getPagination().nextPage();
        recreateModel();
        return "List";
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "List";
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }
}
