package distribuidora.scrapping.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
public class DatosDistribuidora {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private boolean active;
	@ManyToOne
	@JoinColumn(name = "lv_distribuidora_id")
	private LookupValor distribuidora;
	private Date fechaActualizacion;
	@Column(name = "size")
	private Integer cantidadDeProductosAlmacenados;
	private boolean web;
	private boolean excel;
	private String webUrl;
	@Column(name = "has_paginator")
	private boolean paginator;
}
