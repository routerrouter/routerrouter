package dev.router.sisggar.data.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.github.dozermapper.core.Mapping;

import dev.router.sisggar.data.model.Armazem;
import dev.router.sisggar.data.model.Cliente;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonPropertyOrder({ "id", "descricao", "enabled","armazem","clientes","createdBy", "createdDate", "lastModifiedBy",
	"lastModifiedDate" })
public class LocalidadeVO implements Serializable{
 
	private static final long serialVersionUID = 1L;

//	@Mapping("id")
	//@JsonProperty("id")
	private Long id;
	private String descricao;
	private Date createdDate;
	private Date lastModifiedDate;
	private String createdBy;
	private String lastModifiedBy;
	private List<Cliente> clientes;
	private ArmazemDTO armazem;
	private Boolean enabled;
	
	public LocalidadeVO() {
	}

	
}