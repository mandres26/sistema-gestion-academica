package ec.edu.sga.controller;

import ec.edu.sga.modelo.matriculacion.Matricula;
import ec.edu.sga.controller.util.JsfUtil;
import ec.edu.sga.facade.MatriculaFacade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Inject;

@Named("matriculaController")
@SessionScoped
public class MatriculaController implements Serializable {

    private Matricula current;
    private Long matriculaId;
    private List<Matricula> matriculas;
   
    @EJB
    private ec.edu.sga.facade.MatriculaFacade ejbFacade;
     @Inject
    Conversation conversation;
   

    //_______________________CONSTRUCTORES______________________________//
    public MatriculaController() {
        current = new Matricula();
        matriculas = new ArrayList<Matricula>();
    }

    
    //_____________________SETTERS AND GETTERS_____________________________//
    
    public Matricula getCurrent() {
        return current;
    }

    public void setCurrent(Matricula current) {
         System.out.println("Ingreso a fijar matricula" + this.current);
        this.beginConversation();
        this.current = current;
    }

    public Long getMatriculaId() {
        if (current != null) {
            this.matriculaId = current.getId();
            return this.matriculaId;
        }
        return null;
    }

    public void setMatriculaId(Long matriculaId) {
        conversation.begin();
        if (matriculaId != null && matriculaId.longValue() > 0) { 
            this.current = ejbFacade.find(matriculaId);
            this.matriculaId = current.getId();
             System.out.println("Ingreso a editar matriculas: " +current.getTipoMatricula());

        } else {
            System.out.println("Ingreso a crear una nueva matrícula");
            this.current = new Matricula();
        }
    }

    public List<Matricula> getMatriculas() {
        return matriculas;
    }

    public void setMatriculas(List<Matricula> matriculas) {
        this.matriculas = matriculas;
    }

    public MatriculaFacade getEjbFacade() {
        return ejbFacade;
    }

    public void setEjbFacade(MatriculaFacade ejbFacade) {
        this.ejbFacade = ejbFacade;
    }

    public Conversation getConversation() {
        return conversation;
    }

    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }

   
    
    //_____________________________MÉTODOS_____________________________//


    public String persist() {
        System.out.println("Ingreso a grabar la matrícula: " + current.getTipoMatricula());
        ejbFacade.create(current);
        this.endConversation();
         String summary = ResourceBundle.getBundle("/Bundle").getString("MatriculaCreated");
        JsfUtil.addSuccessMessage(summary);
       
        FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);

        return "/index";
    }

    public String update() {
        System.out.println("Ingreso a actualizar: " + current.getTipoMatricula());
        ejbFacade.edit(current);
        System.out.println("Ya actualicé la matrícula: " + current.getTipoMatricula());
        this.endConversation();
        return "/matricula/List";
    }

    public String delete() {
        System.out.println("Ingreso a eliminar la matrícula: " + current.getTipoMatricula());
        ejbFacade.remove(current);
        System.out.println("ya eliminé la matrícula");
        this.endConversation();
        return "/matricula/List";
    }

    public void cancelEdit() {
        System.out.println("Terminando la conversación, cancelando el evento");
        this.endConversation();

    }

    public void beginConversation() {
        if (conversation.isTransient()) {
            conversation.begin();
            System.out.println("Iniciando la conversación en matrícula");
        }

    }

    public void endConversation() {
        if (!conversation.isTransient()) {
            conversation.end();
            System.out.println("Finalizando la conversación en matrícula");
        }

    }
    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

        
}
