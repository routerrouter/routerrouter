package dev.router.sisggar.data.dto;

import java.util.Date;
import java.util.List;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import dev.router.sisggar.data.model.Localidade;
import lombok.Data;

@Data
@JsonPropertyOrder({ "codigo", "designacao", "capacidade", "enabled","localidades", "createdBy", "createdDate", "lastModifiedBy",
		"lastModifiedDate" })
public class ArmazemDTO extends ResourceSupport {


	private Long codigo;
	private String designacao;
    private List<Localidade> localidades;
	private Integer capacidade;
	private Boolean enabled;
	
	private Date createdDate;
	private Date lastModifiedDate;
	private String createdBy;
	private String lastModifiedBy;
	
	

	public ArmazemDTO() {
	}

	
}