package ec.edu.sga.controller;

import ec.edu.sga.modelo.matriculacion.Matricula;
import ec.edu.sga.controller.util.JsfUtil;
import ec.edu.sga.facade.AnioLectivoFacade;
import ec.edu.sga.facade.MatriculaFacade;
import ec.edu.sga.facade.UsuarioFacade;
import ec.edu.sga.modelo.matriculacion.AnioLectivo;
import ec.edu.sga.modelo.matriculacion.Curso;
import ec.edu.sga.modelo.matriculacion.Nivel;
import ec.edu.sga.modelo.usuarios.Usuario;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.inject.Inject;

@Named("matriculaController")
@SessionScoped
public class MatriculaController implements Serializable {

    private Matricula current;
    private Long matriculaId;
    private Long usuarioId;
    private List<Matricula> matriculas;
    private Curso curso;
    private Nivel nivel;
    @EJB
    private ec.edu.sga.facade.MatriculaFacade ejbFacade;
    @EJB
    private ec.edu.sga.facade.AnioLectivoFacade ejbFacadeAnioLectivo;
    @EJB
    private UsuarioFacade ejbFacadeUsuario;
    @Inject
    Conversation conversation;
    @Inject
    EspecialidadController especialidadController;

    //_______________________CONSTRUCTORES______________________________//
    public MatriculaController() {
        current = new Matricula();
        matriculas = new ArrayList<Matricula>();
        curso = new Curso();
        nivel = new Nivel();

        curso.setNivel(nivel);
        current.setCurso(curso);

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
            System.out.println("Ingreso a editar matriculas: " + current.getTipoMatricula());

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

    public AnioLectivoFacade getEjbFacadeAnioLectivo() {
        return ejbFacadeAnioLectivo;
    }

   
    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public Nivel getNivel() {
        return nivel;
    }

    public void setNivel(Nivel nivel) {
        this.nivel = nivel;
    }
    
    

    //_____________________________MÉTODOS_____________________________//
   
    
    // Metodo que permite buscar un usuario para poder matricular
    public void findUserEnrollment() {
        beginConversation();
        if (usuarioId != null && usuarioId.longValue() > 0) {
            Usuario u = ejbFacadeUsuario.find(usuarioId);
            current.setUsuario(u);
        }
    }
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

    //___________________________MÉTODOS DE BÚSQUEDA___________________________//
    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    //Method that return the Anio that are activate == true
    public SelectItem[] getItemAnioActivo() {
        // beginConversation();
        return JsfUtil.getSelectItem(ejbFacadeAnioLectivo.findAnioActivate(Boolean.TRUE));
    }
    
    private List<SelectItem> idEspecialidad = new ArrayList<SelectItem>();
    
     public void valueChanged(ValueChangeEvent event) {
        idEspecialidad.clear();
        if (null != event.getNewValue()) {
            SelectItem[] currentItems;
 
            if (((String) event.getNewValue()).equals("Bachillerato")) {
                currentItems = especialidadController.getItemsAvailableSelectOne();
            }
            else {
                currentItems = especialidadController.getItemsAvailableSelectOne();
            }
 
            for (int i = 0; i < currentItems.length; i++) {
                SelectItem item = new SelectItem(currentItems[i]);
 
                idEspecialidad.add(item);
            }
        }
    }
    
    
}
