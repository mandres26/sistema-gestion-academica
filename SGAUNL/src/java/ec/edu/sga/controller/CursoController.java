package ec.edu.sga.controller;

import ec.edu.sga.controller.util.JsfUtil;
import ec.edu.sga.facade.ParaleloFacade;
import ec.edu.sga.modelo.matriculacion.Curso;
import ec.edu.sga.modelo.matriculacion.Paralelo;
import java.io.Serializable;
import java.util.ArrayList;
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

@Named("cursoController")
@ConversationScoped
public class CursoController implements Serializable {

    private Curso current;
    private List<Curso> resultlist;
   private List<Paralelo> listParalelos;
    @EJB
    private ec.edu.sga.facade.CursoFacade ejbFacade;
    @EJB
    private ParaleloFacade ejbFacadeParalelo;
    private Long cursoId;
    @Inject
    Conversation conversation;
    

    //_____________________________________CONSTRUCTOR______________________________//
    public CursoController() {
        resultlist = new ArrayList();
        current=new Curso();
    }

    //____________________________________getters and setters______________________//
    public Curso getCurrent() {
        return current;
    }

    public void setCurrent(Curso current) {
        System.out.println("Ingreso a fijar curso: " + current);
        this.beginConversation();
        this.current = current;
    }

    public List<Curso> getResultlist() {
        return resultlist;
    }

    public void setResultlist(List<Curso> resultlist) {
        this.resultlist = resultlist;
    }

    
    
    
    public Long getCursoId(){
    if(current != null){
        cursoId = current.getId();
        return  cursoId;
    }
    
    return null;
    }
    
    public void setCursoId(Long cursoId){
    
        System.out.println("========> Ingreso a fijar el id de un Curso: " + cursoId);
        this.beginConversation();
        if (cursoId != null && cursoId.longValue() > 0) {
            
            
            
            //Method that return an Course, next of to enter the id 
            //this.current = ejbFacade.find(cursoId);
            //Method that return an Course whith all their paralels 
            this.current = ejbFacade.findCursoByCursoId(cursoId);
            this.cursoId = this.current.getId();
            
            //esto era una prueba       this.getItemsAvailableSelectOne();
            System.out.println("========> INGRESO a Editar un Curso: " + current.getNombreCurso());
        } else {
            System.out.println("========> INGRESO a Crear un Curso: ");
            this.current = new Curso();
        }
        
        
    }

    public List<Paralelo> getListParalelos() {
        return listParalelos;
    }

    public void setListParalelos(List<Paralelo> listParalelos) {
        this.listParalelos = listParalelos;
    }
    
    
    

    //--------------------------------------MÉTODOS--------------------------------//
    
    
    
    public String findAll() { 
        resultlist = ejbFacade.findAll();
        for (Curso object : resultlist) {
            System.out.println("cursos: "+ object);
            
        }
        String summary = "Encontrado Correctamente!";
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null));
        return "curso/List";
    }
    
    public String findAllCursosAndParalelos() { 
        resultlist = ejbFacade.findCursosAndParalelos();
         for (Curso object : resultlist) {
            System.out.println("cursos: "+ object);
            
        }
        String summary = "Encontrado Correctamente!";
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null));
        return "curso/List";
    }
    
    
    
    
    
    public void addParalelos(){
        Paralelo p = new Paralelo();
        current.add(p);
    }
    
    
    
    
   //________________MÉTODOS PARA INICIALIZAR Y FINALIZAR LA CONVERSACIÓN_________//
    
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
    
    
     public String cancelEdit() {
        System.out.println("me acaban de llamar: canceledit()");
        this.endConversation();
        return "/curso/List?faces-redirect=true";
    }
    
    
    //_______________________PERSISTIR OBJETOS________________________________//
    
    
    public String createInstance() {
       
        System.out.println("========> INGRESO a Crear una instancia de curso: " + current.getNombreCurso());
        this.current = new Curso();
        return "/curso/Edit?faces-redirect=true";
        //return "/vehicle/BrandEdit";
    }

    public String persist() {

        System.out.println("========> INGRESO a Grabar nuevo Curso: " + current.getNombreCurso());
        ejbFacade.create(current);
        this.endConversation();

        String summary = ResourceBundle.getBundle("/Bundle").getString("CursoCreated");
        JsfUtil.addSuccessMessage(summary);
       
        FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);

        return "/curso/List?faces-redirect=true";
       

    }

    public String update() {

        System.out.println("========> INGRESO a Actualizar al Curso: " + current.getNombreCurso());
        ejbFacade.edit(current);
        System.out.println("ya modifique");
        this.endConversation();

        String summary = ResourceBundle.getBundle("/Bundle").getString("EstudianteUpdated");
        FacesContext.getCurrentInstance().addMessage("successInfo", new FacesMessage(FacesMessage.SEVERITY_INFO, summary, summary));
        FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);

        return "/curso/List?faces-redirect=true";

    }

    public String delete() {
        System.out.println("========> INGRESO a Eliminar Curso: " + current.getNombreCurso());
        ejbFacade.remove(current);

        //cambia este método por uno implementado con búsqueda por criteria
        this.findAll();

        this.endConversation();

        String summary = "Curso Eliminado Correctamente!";
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null));


        return "/curso/List?faces-redirect=true";

    }
    
    
   // ______________________MÉTODOS PARA DEVOLVER UNA LISTA DE CURSOS_______________________//
    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

//    public SelectItem[] getItemsAvailableSelectOne() {
//       return JsfUtil.getSelectItems(ejbFacade.findParalelosByCursoId2(cursoId), true);
//    }
    
    
    private SelectItem[] itemsParalelos;

    

    public void setItemsParalelos(SelectItem[] itemsParalelos) {
        this.itemsParalelos = itemsParalelos;
    }
    
//    public SelectItem[] getItemsParalelos(){
//        
//        int cont = 0;
//        itemsParalelos = new SelectItem[ejbFacade.findParalelosByCursoId2(cursoId).size()];
//        for (Paralelo paralelo : ejbFacade.findParalelosByCursoId2(cursoId)) {
//            
//            itemsParalelos[cont++] = new SelectItem(paralelo, paralelo.toString()); 
//            System.out.println("prueba items"+ itemsParalelos[cont]);
//        }
//        return itemsParalelos;        
//    }
}
