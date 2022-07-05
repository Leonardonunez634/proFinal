/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MusicTown.ver1.Entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import org.hibernate.annotations.GenericGenerator;

import MusicTown.ver1.Enum.Roles;


@Entity
public class Usuario implements Serializable {
     @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private String nombre;
    private String mail;
   
    private Date alta;
    private Date baja;
    
    @Enumerated(EnumType.STRING)
    private Roles rol;

    private String clave;

    public Roles getRol() {
        return rol;
    }

    public void setRol(Roles rol) {
        this.rol = rol;
    }

	
    public String getId() {
        return id;
    }

    
    public void setId(String id) {
        this.id = id;
    }

    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

 
    public String getNombreApellido() {
        return nombre;
    }

   
    public void setNombreApellido(String nombre) {
        this.nombre = nombre;
    }

   
    public String getMail() {
        return mail;
    }

   
    public void setMail(String mail) {
        this.mail = mail;
    }

   
    public String getClave() {
        return clave;
    }

 
    public void setClave(String clave) {
        this.clave = clave;
    }
    
//    public Usuario get() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }


    public Date getAlta() {
        return alta;
    }

  
    public void setAlta(Date alta) {
        this.alta = alta;
    }

    
    public Date getBaja() {
        return baja;
    }

   
    public void setBaja(Date baja) {
        this.baja = baja;
    }
}
