/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.sga.modelo.matriculacion;

import ec.edu.sga.modelo.academico.MallaCurricular;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.TableGenerator;

/**
 *
 * @author lucho
 */
@Entity
@TableGenerator(name = "CursoGenerador", table = "GeneradorIdentificador", pkColumnName = "nombre",
valueColumnName = "valor", pkColumnValue = "Curso", initialValue = 1, allocationSize = 1)
@NamedQueries(value={@NamedQuery(name="Curso.findAll", query="select c from Curso c"),
@NamedQuery(name="findParalelosByEstudentId",
        query="select distinct e from Curso e left join fetch"
        + " e.paralelos where e.id =:id")})
public class Curso implements Serializable {

    //-----------------------ATRIBUTOS----------------------------------//
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator="CursoGenerador")
    private Long id;
    private String nombreCurso;
    @ManyToOne
    private Especialidad especialidad;
    @ManyToOne
    private Nivel nivel;
    @OneToOne
    private Matricula matricula;
    @OneToOne(mappedBy = "curso")
    private MallaCurricular mallaCurricular;
    @OneToMany(mappedBy = "curso", cascade={CascadeType.ALL}, orphanRemoval=true)
    private List<Paralelo> paralelos;

    //----------------------constructores--------------------------------------//
    public Curso() {
        paralelos = new ArrayList<Paralelo>();
    }

    //----------------------MÉTODOS SET AND GET---------------------------------//
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreCurso() {
        return nombreCurso;
    }

    public void setNombreCurso(String nombreCurso) {
        this.nombreCurso = nombreCurso;
    }

    public Especialidad getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(Especialidad especialidad) {
        this.especialidad = especialidad;
    }

    public Nivel getNivel() {
        return nivel;
    }

    public void setNivel(Nivel nivel) {
        this.nivel = nivel;
    }

    public Matricula getMatricula() {
        return matricula;
    }

    public void setMatricula(Matricula matricula) {
        this.matricula = matricula;
    }

    public MallaCurricular getMallaCurricular() {
        return mallaCurricular;
    }

    public void setMallaCurricular(MallaCurricular mallaCurricular) {
        this.mallaCurricular = mallaCurricular;
    }

    public List<Paralelo> getParalelos() {
        return paralelos;
    }

    public void setParalelos(List<Paralelo> paralelos) {
        this.paralelos = paralelos;
    }

    //----------------------MÉTODOS---------------------------------//
    
    public void add(Paralelo paralelo){
        if(!paralelos.contains(paralelo)){
            paralelos.add(paralelo);
            paralelo.setCurso(this);
        }


    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Curso)) {
            return false;
        }
        Curso other = (Curso) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nombreCurso;
    }
}
