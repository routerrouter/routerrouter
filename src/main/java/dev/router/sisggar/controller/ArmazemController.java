package dev.router.sisggar.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import dev.router.sisggar.data.dto.ArmazemDTO;
import dev.router.sisggar.data.model.Armazem;
import dev.router.sisggar.exception.NegocioException;
import dev.router.sisggar.services.ArmazemServices;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "ArmazemEndpoint")
@RestController
@RequestMapping("/api/armazem/v1")
public class ArmazemController {

	@Autowired
	private ArmazemServices service;

	@Autowired
	private PagedResourcesAssembler<ArmazemDTO> assembler;

	@Autowired
	private ModelMapper modelMapper;

	private ArmazemDTO convertModel(Armazem armazem) {
		return modelMapper.map(armazem, ArmazemDTO.class);
	}

	@ApiOperation(value = "Buscar todos os Armazens")
	@GetMapping(produces = { "application/json", "application/xml", "application/x-yaml" })
	public ResponseEntity<List<ArmazemDTO>> findAll(@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "limit", defaultValue = "12") int limit,
			@RequestParam(value = "direction", defaultValue = "asc") String direction) {

		List<ArmazemDTO> list = service.findAll();
		if (list.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			list.stream().forEach(p -> p
					.add(linkTo(methodOn(ArmazemController.class).buscarPeloCodigo(p.getCodigo())).withSelfRel()));
		}

		return new ResponseEntity<List<ArmazemDTO>>(list, HttpStatus.OK);
	}

	@GetMapping(value = "/getList")
	@ResponseBody
	public ResponseEntity<List<Armazem>> getPosts(
			@RequestParam(defaultValue = "0") Integer pageNo,
			@RequestParam(defaultValue = "2") Integer pageSize, 
			@RequestParam(defaultValue = "designacao") String sortBy) {

		List<Armazem> list = service.getArmazemList(pageNo, pageSize, sortBy);

		return new ResponseEntity<List<Armazem>>(list, new HttpHeaders(), HttpStatus.OK);
		/*
		 * return posts.stream() .map(this::convertModel) .collect(Collectors.toList());
		 */
	}

	@ApiOperation(value = "Buscar um armazem especifico pelo seu ID")
	@GetMapping(value = "/{id}", produces = { "application/json", "application/xml", "application/x-yaml" })
	public ResponseEntity<ArmazemDTO> buscarPeloCodigo(@PathVariable("id") Long id) {
		ArmazemDTO armazemDTO = service.findById(id);

		armazemDTO.add(linkTo(methodOn(ArmazemController.class).findAll(0, 12, "ASC")).withSelfRel());
		return new ResponseEntity<ArmazemDTO>(armazemDTO, HttpStatus.OK);
	}

	@ApiOperation(value = "Buscar um armazem especifico pelo Nome")
	@GetMapping(value = "/findArmazemByDesignacao/{designacao}", produces = { "application/json", "application/xml",
			"application/x-yaml" })
	public ResponseEntity<ArmazemDTO> findArmazemByDesignacao(@PathVariable("designacao") String designacao) {
		ArmazemDTO armazemDTO = service.findArmazemByDesignacao(designacao);

		armazemDTO.add(linkTo(methodOn(ArmazemController.class).findAll(0, 12, "ASC")).withSelfRel());
		return new ResponseEntity<ArmazemDTO>(armazemDTO, HttpStatus.OK);
	}

	@ApiOperation(value = "Buscar um armazem especifico pelo Nome")
	@GetMapping(value = "/findArmazemLikeDesignacao/{designacao}", produces = { "application/json", "application/xml",
			"application/x-yaml" })
	public ResponseEntity<List<ArmazemDTO>> findArmazemLikeDesignacao(@PathVariable("designacao") String designacao) {

		List<ArmazemDTO> list = service.findArmazemLikeDesignacao(designacao);

		if (list.isEmpty()) {
			throw new NegocioException("Não existem armazens com esta designação");
		} else {
			list.stream().forEach(p -> p
					.add(linkTo(methodOn(ArmazemController.class).buscarPeloCodigo(p.getCodigo())).withSelfRel()));
		}

		return new ResponseEntity<List<ArmazemDTO>>(list, HttpStatus.OK);

	}

	@ApiOperation(value = "Criar um novo Armazem")
	@PostMapping(produces = { "application/json", "application/xml", "application/x-yaml" }, consumes = {
			"application/json", "application/xml", "application/x-yaml" })
	public ResponseEntity<ArmazemDTO> create(@Valid @RequestBody ArmazemDTO armazemDto) {
		ArmazemDTO armazem_save = service.create(armazemDto);
		armazem_save
				.add(linkTo(methodOn(ArmazemController.class).buscarPeloCodigo(armazemDto.getCodigo())).withSelfRel());
		return ResponseEntity.status(HttpStatus.CREATED).body(armazem_save);
	}

	@ApiOperation(value = "Actualiza um armazem especifico")
	@PutMapping(produces = { "application/json", "application/xml", "application/x-yaml" }, consumes = {
			"application/json", "application/xml", "application/x-yaml" })
	public ResponseEntity<ArmazemDTO> update(@Valid @RequestBody ArmazemDTO armazemDto) {
		ArmazemDTO armazemUpdate = service.update(armazemDto);
		armazemUpdate.add(
				linkTo(methodOn(ArmazemController.class).buscarPeloCodigo(armazemUpdate.getCodigo())).withSelfRel());
		return ResponseEntity.status(HttpStatus.OK).body(armazemUpdate);
	}

	@ApiOperation(value = "Desactivar um armazem especifico")
	@PatchMapping(value = "/{id}", produces = { "application/json", "application/xml", "application/x-yaml" })
	public ArmazemDTO disableArmazem(@PathVariable("id") Long id, @RequestParam("status") Boolean status) {
		ArmazemDTO armazemDTO = service.isActiveOrNotArmazem(id, status);
		// armazemVO.add(linkTo(methodOn(ArmazemController.class).findById(id)).withSelfRel());
		return armazemDTO;
	}

	@ApiOperation(value = "Eliminar um armazem especifico pelo seu ID")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {
		service.delete(id);
		return ResponseEntity.ok().build();
	}

}