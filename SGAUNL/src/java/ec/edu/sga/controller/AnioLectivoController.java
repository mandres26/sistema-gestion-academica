package ec.edu.sga.controller;

import ec.edu.sga.controller.util.JsfUtil;
import ec.edu.sga.facade.AnioLectivoFacade;
import ec.edu.sga.modelo.matriculacion.AnioLectivo;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

@Named("anioLectivoController")
@ConversationScoped
public class AnioLectivoController implements Serializable {

    private List<AnioLectivo> resultlist;
    @EJB
    private AnioLectivoFacade ejbFacade;
    private AnioLectivo current;
    private Long anioLectivoId;
    @Inject
    Conversation conversation;

    //_________________________CONSTRUCTORES______________________________//
    public AnioLectivoController() {
        System.out.println("Llamando al constructor de Año Lectivo");
        resultlist = new ArrayList<AnioLectivo>();
        current = new AnioLectivo();
    }

    //______________________________get and set________________________________
    public List<AnioLectivo> getResultlist() {
        return resultlist;
    }

    public void setResultlist(List<AnioLectivo> resultlist) {
        this.resultlist = resultlist;
    }

    public AnioLectivoFacade getEjbFacade() {
        return ejbFacade;
    }

    public void setEjbFacade(AnioLectivoFacade ejbFacade) {
        this.ejbFacade = ejbFacade;
    }

    public AnioLectivo getCurrent() {
        return current;
    }

    public void setCurrent(AnioLectivo current) {
        System.out.println("Ingreso a fijar Año Lectivo" + this.current);
        this.beginConversation();
        this.current = current;
    }

    public Long getAnioLectivoId() {
        if (current != null) {
            this.anioLectivoId = current.getId();
            return this.anioLectivoId;
        }
        return null;
    }

    public void setAnioLectivo(Long anioLectivoId) {
        conversation.begin();
        if (anioLectivoId != null && anioLectivoId.longValue() > 0) { //Verifica que el id no sea vacío
            this.current = ejbFacade.find(anioLectivoId);//BUsca un paralelo de acuerdo al ID y lo asigna a current
            this.anioLectivoId = current.getId();
            System.out.println("Ingreso a editar año lectivo: " + current.getFechaInicio());

        } else {
            System.out.println("Ingreso a crear un nuevo año lectivo");
            this.current = new AnioLectivo();
        }


    }

    //____________________________MÉTODOS_______________________________
    public String persist() {
        System.out.println("Ingreso a grabar el Año Lectivo: " + current.getFechaInicio());
        current.setCreated(new Date());
        current.setUpdated(new Date());
        ejbFacade.create(current);
        this.endConversation();
        String summary = ResourceBundle.getBundle("/Bundle").getString("AnioLectivoCreated");
        JsfUtil.addSuccessMessage(summary);

        FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);

        return "/index";
    }

    public String update() {
        System.out.println("Ingreso a actualizar: " + current.getFechaInicio());
        current.setUpdated(new Date());
        ejbFacade.edit(current);
        System.out.println("Ya actualicé el año lectivo: " + current.getFechaInicio());
        this.endConversation();
        return "/anioLectivo/List";
    }

    public String delete() {
        System.out.println("Ingreso a eliminar el año lectivo: " + current.getFechaInicio());
        ejbFacade.remove(current);
        System.out.println("ya eliminé el año lectivo");
        this.endConversation();
        return "/anioLectivo/List";
    }

    public void cancelEdit() {
        System.out.println("Terminando la conversación, cancelando el evento");
        this.endConversation();

    }

    public void beginConversation() {
        if (conversation.isTransient()) {
            conversation.begin();
            System.out.println("Iniciando la conversación en anioLectivo");
        }

    }

    public void endConversation() {
        if (!conversation.isTransient()) {
            conversation.end();
            System.out.println("Finalizando la conversación en AnioLectivo");
        }

    }

    public String findAllAnios() {
        resultlist = ejbFacade.findAll();
        return "anioLectivo/List";
    }

    // ______________________MÉTODOS PARA DEVOLVER UNA LISTA DE AÑOS LECTIVOS_______________________//
    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    public SelectItem[] getItemAnioActivate() {
        return JsfUtil.getSelectItem(ejbFacade.findAnioActivate(Boolean.TRUE));
    }
    //Method that return the Anio that are activate == true
//    public AnioLectivo anioActivo(){
//        beginConversation();
//        AnioLectivo a = ejbFacade.findAnioActivate(Boolean.TRUE);
//        System.out.println("el año activo es: " +a);
//        anioLectivoId=a.getId();
//        return ejbFacade.findAnioActivate(Boolean.TRUE);
//        
//       
//    }
}
