package com.todo1.app.model.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.CreditCardNumber;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * Entidad de persistencia para los Clientes/Usuarios
 * 
 * @author Gabriel E. 
 *
 */
@Entity
@Table(name = "clientes")
public class Cliente implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 50)
	@NotEmpty
	@Size(min = 4, max = 49)
	private String nombre;

	@Column(length = 50)
	@NotEmpty
	@Size(min = 4, max = 50)
	private String apellido;
	
	@NotEmpty
	@Column(unique = true, length = 74)
	private String username;
	
	@NotEmpty
	@Size(min = 8)
	@Column(length = 60)
	private String password;
	
	@Column(columnDefinition = "boolean default true")
	private Boolean enabled;

	@Transient
	@CreditCardNumber
	private String tarjetaCredito;
	
	private String tarjetaCreditoEncriptada;

	@Pattern(regexp = "[0-9]{2}[/][0-9]{2}")
	private String fechaCaducidad;

	@NotEmpty
	@Size(min = 3)
	private String codigoSeguridadTarjeta;

	@NotEmpty
	@Email
	private String email;

	private String foto;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date creadoEn;

	@OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Factura> facturas;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "usuarios_roles", 
		joinColumns = @JoinColumn(name ="user_id" ), 
		inverseJoinColumns = @JoinColumn(name ="role_id" ),
		uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id","role_id"})})
	private List<Role> roles;

	private static final long serialVersionUID = 1L;
	
	@PrePersist
	public void prePersist() {
		creadoEn = new Date();
		foto = new String();
	}
	
	// extra apara añadir de una en una
	public void addFactura(Factura factura) {
		facturas.add(factura);
	}

	// inicializo con el constructor la lista
	public Cliente() {
		facturas = new ArrayList<Factura>();
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getCreadoEn() {
		return creadoEn;
	}

	public void setCreadoEn(Date creadoEn) {
		this.creadoEn = creadoEn;
	}

	public List<Factura> getFacturas() {
		return facturas;
	}

	public void setFacturas(List<Factura> facturas) {
		this.facturas = facturas;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public String getFechaCaducidad() {
		return fechaCaducidad;
	}

	public void setFechaCaducidad(String fechaCaducidad) {
		this.fechaCaducidad = fechaCaducidad;
	}

	public String getCodigoSeguridadTarjeta() {
		return codigoSeguridadTarjeta;
	}

	public void setCodigoSeguridadTarjeta(String codigoSeguridadTarjeta) {
		this.codigoSeguridadTarjeta = codigoSeguridadTarjeta;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
	public String getTarjetaCredito() {
		return tarjetaCredito;
	}

	public void setTarjetaCredito(String tarjetaCredito) {
		this.tarjetaCredito = tarjetaCredito;
	}

	public String getTarjetaCreditoEncriptada() {
		return tarjetaCreditoEncriptada;
	}

	public void setTarjetaCreditoEncriptada(String tarjetaCreditoEncriptada) {
		this.tarjetaCreditoEncriptada = tarjetaCreditoEncriptada;
	}

	@Override
	public String toString() {
		return nombre + " " + apellido;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

}
