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
    private Date criterio;
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

     public Date getCriterio() {
        return criterio;
    }

    public void setCriterio(Date criterio) {
        this.criterio = criterio;
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

    public void setAnioLectivoId(Long anioLectivoId) {
        this.beginConversation();
        if (anioLectivoId != null && anioLectivoId.longValue() > 0) { //Verifica que el id no sea vacío
            this.current = ejbFacade.find(anioLectivoId);//Busca un año de acuerdo al ID y lo asigna a current
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
        String summary = ResourceBundle.getBundle("/Bundle").getString("mensaje.anio.creado");
        JsfUtil.addInformacionMessage(summary);
        FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
        return "/anioLectivo/List?faces-redirect=true";
    }

    public String update() {
        System.out.println("Ingreso a actualizar: " + current.getFechaInicio());
        current.setUpdated(new Date());
        ejbFacade.edit(current);
        System.out.println("Ya actualicé el año lectivo: " + current.getFechaInicio());
        this.endConversation();
        String summary = ResourceBundle.getBundle("/Bundle").getString("mensaje.anio.actualizado");
        JsfUtil.addInformacionMessage(summary);
        FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
        return "/anioLectivo/List?faces-redirect=true";
    }

    public String delete() {
        System.out.println("Ingreso a eliminar el año lectivo: " + current.getFechaInicio());
        ejbFacade.remove(current);
        System.out.println("ya eliminé el año lectivo");
        this.endConversation();
        String summary = ResourceBundle.getBundle("/Bundle").getString("mensaje.anio.eliminado");
        JsfUtil.addInformacionMessage(summary);
        FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
        return "/anioLectivo/List?faces-redirect=true";
    }

    public String cancelEdit() {
        System.out.println("me acaban de llamar: canceledit()");
        this.endConversation();
        return "/anioLectivo/List?faces-redirect=true";
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

    //Método que encuentra todos los años
    public String findAll() {
        resultlist = ejbFacade.findAll();
        return "anioLectivo/List";
    }

    //Método que encuentra todos los años de acuerdo a un criterio(metodo que si funciona)
//    public String findAllAniosByCriterio() {
//        System.out.println("criterio inicio: " + criterio);
//        if (criterio != null) {
//            resultlist = null;
//            }
//
//        Date prueba = new Date();
//        for (AnioLectivo a : ejbFacade.findAll()) {
//            if (a.getFechaInicio().getYear() == criterio.getYear()) {
//                resultlist.add(a);
//            }
//        }
//
//        return "/anioLectivo/List";
//    }

    // ______________________MÉTODOS PARA DEVOLVER UNA LISTA DE AÑOS LECTIVOS_______________________//
    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    //Método que retorna el año que está actualmente activo
    public SelectItem[] getItemAnioActivate() {
        return JsfUtil.getSelectItem(ejbFacade.findAnioActivate(Boolean.TRUE));
    }
 
}
