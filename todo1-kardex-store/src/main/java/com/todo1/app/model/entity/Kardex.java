package com.todo1.app.model.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Entidad de persistencia para manejo del Kardex
 * 
 * @author Gabriel E. 
 *
 */

@Entity
@Table(name="kardex")
public class Kardex implements Serializable {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// Entrada, Salida, Inv. Inicial, Venta
	@NotEmpty
	private String tipoOperacion; 
	
	@ManyToOne(fetch=FetchType.LAZY) 
	private Producto producto;
	
	@ManyToOne(fetch=FetchType.LAZY) 
	private Factura factura;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaRegistro;
	
	private String observacion;
	
	@NotNull
	private BigDecimal precioDeCosto;
	
	private Integer cantidadMovimiento;
	
	private BigDecimal valorTotalDeCosto;
	
	@NotNull
	private Integer saldoCantidad;
	
	@PrePersist
	public void prePersist() {
		fechaRegistro = new Date();
		valorTotalDeCosto = precioDeCosto.multiply(new BigDecimal(cantidadMovimiento));
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public Factura getFactura() {
		return factura;
	}

	public void setFactura(Factura factura) {
		this.factura = factura;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public String getTipoOperacion() {
		return tipoOperacion;
	}

	public void setTipoOperacion(String tipoOperacion) {
		this.tipoOperacion = tipoOperacion;
	}

	public BigDecimal getPrecioDeCosto() {
		return precioDeCosto;
	}

	public void setPrecioDeCosto(BigDecimal precioDeCosto) {
		this.precioDeCosto = precioDeCosto;
	}

	public Integer getCantidadMovimiento() {
		return cantidadMovimiento;
	}

	public void setCantidadMovimiento(Integer cantidadMovimiento) {
		this.cantidadMovimiento = cantidadMovimiento;
	}

	public BigDecimal getValorTotalDeCosto() {
		return valorTotalDeCosto;
	}

	public void setValorTotalDeCosto(BigDecimal valorTotalDeCosto) {
		this.valorTotalDeCosto = valorTotalDeCosto;
	}

	public Integer getSaldoCantidad() {
		return saldoCantidad;
	}

	public void setSaldoCantidad(Integer saldoCantidad) {
		this.saldoCantidad = saldoCantidad;
	}
	
	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	private static final long serialVersionUID = 5497527819468414737L;

}
