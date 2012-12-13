/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.sga.modelo.usuarios;

import ec.edu.sga.modelo.academico.ExpedienteAcademico;
import ec.edu.sga.modelo.academico.RegistroCalificaciones;
import ec.edu.sga.modelo.matriculacion.Matricula;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author edison
 */
@Entity
@TableGenerator(name = "UsuarioGenerador", table = "GeneradorIdentificador", pkColumnName = "nombre",
valueColumnName = "valor", pkColumnValue = "Usuario", initialValue = 1, allocationSize = 1)
@NamedQueries({
    
    @NamedQuery(name = "Usuario.findByLogin", query = "SELECT u FROM Usuario u WHERE u.login = :login"),
    @NamedQuery(name = "Usuario.findLogin", query = "SELECT u FROM Usuario u WHERE u.login = :login AND u.clave = :clave"),
    @NamedQuery(name = "Usuario.buscarPorClave",
    query = "select u from Usuario u where"
    + " lower(u.dni) like lower(concat('%',:clave,'%'))"
    + "or"
    + " lower(u.nombres) like lower(concat('%',:clave,'%')) "
    + "or"
    + " lower(u.apellidos) like lower(concat('%',:clave,'%'))"
    + "order by u.nombres"),
    @NamedQuery(name = "Usuario.buscarPorId",
    query = "select distinct u from Usuario u left join fetch u.ficha where"
    + " u.id=:id")
})
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "UsuarioGenerador")
    private Long id;
    private String login;
    private String clave;
    @Column(name = "CREATED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;
    @Column(name = "UPDATED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updated;
    private boolean estado;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaLogin;
    private String dni;
    private String libretaMilitar;
    private String nombres;
    private String apellidos;
    @Enumerated(EnumType.STRING)
    private TipoSexo sexo;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaNacimiento;
    @OneToOne(mappedBy = "usuario", cascade = {CascadeType.ALL}, orphanRemoval = true, fetch = FetchType.EAGER)
    private Ficha ficha;
    @OneToMany(mappedBy = "usuario")
    private List<Matricula> matriculas;
    @OneToOne(mappedBy = "usuario")
    private RegistroCalificaciones registroCalificaciones;
    @OneToOne(mappedBy = "usuario")
    private ExpedienteAcademico expedienteAcademico;
    @OneToMany(mappedBy = "usuario", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<Rol> roles;
    @ManyToOne
    private Tipousuario tipousuarioId;

    public Usuario() {
        matriculas = new ArrayList<Matricula>();
        roles = new ArrayList<Rol>();
    }

    public void add(Rol rol) {

        if (!roles.contains(rol)) {
            roles.add(rol);
            rol.setUsuario(this);
        }

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public boolean getEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public Date getFechaLogin() {
        return fechaLogin;
    }

    public void setFechaLogin(Date fechaLogin) {
        this.fechaLogin = fechaLogin;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }
    

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getLibretaMilitar() {
        return libretaMilitar;
    }

    public void setLibretaMilitar(String libretaMilitar) {
        this.libretaMilitar = libretaMilitar;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public TipoSexo getSexo() {
        return sexo;
    }

    public void setSexo(TipoSexo sexo) {
        this.sexo = sexo;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Ficha getFicha() {
        return ficha;
    }

    public void setFicha(Ficha ficha) {
        this.ficha = ficha;
    }

    public List<Matricula> getMatriculas() {
        return matriculas;
    }

    public void setMatriculas(List<Matricula> matriculas) {
        this.matriculas = matriculas;
    }

    public RegistroCalificaciones getRegistroCalificaciones() {
        return registroCalificaciones;
    }

    public void setRegistroCalificaciones(RegistroCalificaciones registroCalificaciones) {
        this.registroCalificaciones = registroCalificaciones;
    }

    public ExpedienteAcademico getExpedienteAcademico() {
        return expedienteAcademico;
    }

    public void setExpedienteAcademico(ExpedienteAcademico expedienteAcademico) {
        this.expedienteAcademico = expedienteAcademico;
    }

    public List<Rol> getRoles() {
        return roles;
    }

    public void setRoles(List<Rol> roles) {
        this.roles = roles;
    }

    public Tipousuario getTipousuarioId() {
        return tipousuarioId;
    }

    public void setTipousuarioId(Tipousuario tipousuarioId) {
        this.tipousuarioId = tipousuarioId;
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
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nombres + " " + apellidos;
    }
}
