package dev.router.sisggar.data.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import dev.router.sisggar.auditor.Auditable;
import lombok.Data;


@Data
@Entity
@SequenceGenerator(name = "localidade_seq", sequenceName = "localidade_codigo_seq", allocationSize = 1)
@Table(name = "localidade")
public class Localidade extends Auditable<String> implements  Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(generator = "localidade_seq", strategy = GenerationType.AUTO)
	private Long codigo;
	
	@NotBlank
	@Column(name = "descricao", nullable = false)
	private String descricao;
	
	@Valid
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "armazem_id", nullable = false)
	@LazyCollection(LazyCollectionOption.FALSE)
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private Armazem armazem;
	
	@Valid
	@OneToMany(mappedBy = "localidade", fetch = FetchType.LAZY)
	@LazyCollection(LazyCollectionOption.FALSE)
	@JsonIgnoreProperties({"localidade","hibernateLazyInitializer", "handler"})
	private List<Cliente> clientes;
	
	@Column(nullable = false)
	private Boolean enabled;



}
