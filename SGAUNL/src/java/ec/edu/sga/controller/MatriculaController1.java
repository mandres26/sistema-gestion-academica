package ec.edu.sga.controller;

import ec.edu.sga.controller.util.JsfUtil;
import ec.edu.sga.facade.AnioLectivoFacade;
import ec.edu.sga.facade.CursoFacade;
import ec.edu.sga.facade.EspecialidadFacade;
import ec.edu.sga.facade.MatriculaFacade;
import ec.edu.sga.facade.NivelFacade;
import ec.edu.sga.facade.ParaleloFacade;
import ec.edu.sga.facade.UsuarioFacade;
import ec.edu.sga.modelo.matriculacion.Curso;
import ec.edu.sga.modelo.matriculacion.Especialidad;
import ec.edu.sga.modelo.matriculacion.Matricula;
import ec.edu.sga.modelo.matriculacion.Nivel;
import ec.edu.sga.modelo.matriculacion.Paralelo;
import ec.edu.sga.modelo.usuarios.Usuario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

@Named("matriculaController1")
@ConversationScoped

public class MatriculaController1 implements Serializable {
    
    private Matricula current;
    private Long matriculaId;
    private Long usuarioId;
    private Long nivelId;
    private Long especialidadId;
    private Long cursoId;
    private List<Matricula> matriculas;
    private Curso curso;
    private Nivel nivel;
    private Paralelo paralelo;
    private Especialidad especialidad;
    @EJB
    private ec.edu.sga.facade.MatriculaFacade ejbFacade;
    @EJB
    private ec.edu.sga.facade.AnioLectivoFacade ejbFacadeAnioLectivo;
    @EJB
    private UsuarioFacade ejbFacadeUsuario;
    @EJB
    private NivelFacade ejbFacadeNivel;
    @EJB
    private EspecialidadFacade ejbFacadeEspecialidad;
    @EJB
    private CursoFacade ejbFacadeCurso;
    @EJB
    private ParaleloFacade ejbFacadeParalelo;
    @Inject
    Conversation conversation;
    @Inject
    EspecialidadController especialidadController;

    //_______________________CONSTRUCTORES______________________________//
    public MatriculaController1() {
        current = new Matricula();
        matriculas = new ArrayList<Matricula>();
        curso = new Curso();
        nivel = new Nivel();
        paralelo = new Paralelo();
        especialidad = new Especialidad();
        
       // especialidad.setNivel(nivel);
       // curso.setNivel(nivel);
//       // curso.setEspecialidad(especialidad);
       // paralelo.setCurso(curso);
        current.setCurso(curso);
//        // curso.setNivel(nivel);

        
        
        System.out.println("Este es el valor de cursoId: " + nivelId);
        System.out.println("Este es el valor de usuarioId: " + usuarioId);
        System.out.println("Este es el valor de usuarioId: " + cursoId);
        
        
    }

    //_____________________SETTERS AND GETTERS_____________________________//
    public Matricula getCurrent() {
        return current;
    }
    
    
    
    
    
    
    public void setCurrent(Matricula current) {
        System.out.println("Ingreso a fijar matricula" + this.current.getCurso());
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
    
    public Long getNivelId() {
        return nivelId;
    }
    
    public void setNivelId(Long nivelId) {
        this.nivelId = nivelId;
    }
    
    public Long getEspecialidadId() {
        return especialidadId;
    }
    
    public void setEspecialidadId(Long especialidadId) {
        this.especialidadId = especialidadId;
    }
    
    public Long getCursoId() {
        return cursoId;
    }
    
    public void setCursoId(Long cursoId) {
        this.cursoId = cursoId;
    }
    
    public Paralelo getParalelo() {
        return paralelo;
    }
    
    public void setParalelo(Paralelo paralelo) {
        this.paralelo = paralelo;
    }
    
    public Especialidad getEspecialidad() {
        return especialidad;
    }
    
    public void setEspecialidad(Especialidad especialidad) {
        this.especialidad = especialidad;
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
    
    
    public void asignarCursosParalelos(){
       
        Nivel n = ejbFacadeNivel.find(nivel.getId());
        
    }
    
    
    
    
    
    public String persist() {
        System.out.println("Ingreso a grabar la matrícula: " + current.getTipoMatricula());
        current.setCurso(curso);
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
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    //Method that return the Anio that are activate == true
    public SelectItem[] getItemAnioActivo() {
        return JsfUtil.getSelectItem(ejbFacadeAnioLectivo.findAnioActivate(Boolean.TRUE));
    }
    
    public SelectItem[] getItemsNiveles() {
        return JsfUtil.getSelectItems(ejbFacadeNivel.findAll(), false);
    }
    
    
    public SelectItem[] getItemsEspecialidadesByNivelId() {
                 return JsfUtil.getSelectItems(ejbFacadeEspecialidad.findEspecialidadesByNivelId(nivel.getId()), false);
    }
    
    public void nivelChangeEvent(ValueChangeEvent e){
        
         try {             
            itemsCursosbyNivelId = JsfUtil.getSelectItems(ejbFacadeCurso.findAllCursosbyNivelId(((Nivel)e.getNewValue()).getId()), false);
        } catch (Exception ex) {
            itemsCursosbyNivelId = new SelectItem[0];
        }
         
        
    }
    
    private SelectItem[] itemsCursosbyNivelId;
    
    public SelectItem[] getItemsCursosbyNivelId() {
         return itemsCursosbyNivelId;
    }
    
    public void setItemsCursosbyNivelId(SelectItem[] its) {
         this.itemsCursosbyNivelId = its;
    }
    
    public SelectItem[] getItemsCursosByEspecialidadId() {
           
        return JsfUtil.getSelectItems(ejbFacadeCurso.findAllCursosbyEspecialidadId(especialidad.getId()), false);
    }
    
    public SelectItem[] getItemsParalelosbyCursoId() {
           
        return JsfUtil.getSelectItems(ejbFacadeParalelo.findAllParalelosByCursoId(curso.getId()), false); //Long.parseLong("4")
    }
    
    
    
    
    
    
    public void prueba() {
        System.out.println("esta es una prueba");
        System.out.println("esta es una prueba");
        System.out.println("esta es una prueba");
        System.out.println("esta es una prueba");
        System.out.println("esta es una prueba");
        System.out.println("esta es una prueba");
        System.out.println("esta es una prueba");
        System.out.println("esta es una prueba");
        System.out.println("Este es el valor de nivel: " + nivel);
        System.out.println("Este es el valor de nivelId: " + nivelId);
        System.out.println("Este es el valor de cursoId: " + cursoId);
        System.out.println("Este es el valor de usuarioId: " + usuarioId);
        System.out.println("Este es el valor de curso: " + curso);
    }
    
   
}
