package dev.router.sisggar.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
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
import org.springframework.web.bind.annotation.RestController;

import dev.router.sisggar.data.dto.LocalidadeVO;
import dev.router.sisggar.data.model.Localidade;
import dev.router.sisggar.data.vo.v1.response.ResponseReturn;
import dev.router.sisggar.services.ConverterService;
import dev.router.sisggar.services.LocalidadeServices;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "LocalidadeEndpoint")
@RestController
@RequestMapping("/api/localidade/v1")
public class LocalidadeController {

	@Autowired
	private LocalidadeServices service;

	@Autowired
	private PagedResourcesAssembler<LocalidadeVO> assembler;
	
	private final ConverterService<Localidade, LocalidadeVO> convert;

	public LocalidadeController() {
	    super();
	    this.convert = new ConverterService<>(Localidade.class, LocalidadeVO.class);
	}

	@ApiOperation(value = "Buscar todas as localidades")
	@GetMapping(produces = { "application/json", "application/xml", "application/x-yaml" })
	public ResponseReturn listarTodos(@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "limit", defaultValue = "12") int limit,
			@RequestParam(value = "direction", defaultValue = "asc") String direction) {

		var sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;

		Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection, "descricao"));
		Page<Localidade> localidade = service.findAll(pageable);
		
		List<LocalidadeVO> localidadeVO = convert.toDto(localidade.getContent());

		return new ResponseReturn(localidadeVO, localidade.getSize(), localidade.getTotalElements(), 
				localidade.getTotalPages(), localidade.getNumber());
	}

	@ApiOperation(value = "Buscar uma localidade especifica pela descrição")
	@GetMapping(value = "/findLocalidadeByDescricao/{descricao}", produces = { "application/json", "application/xml",
			"application/x-yaml" })
	public ResponseEntity<?> findLocalizacaoByDescricao(@PathVariable("descricao") String descricao,
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "limit", defaultValue = "12") int limit,
			@RequestParam(value = "direction", defaultValue = "asc") String direction) {

		var sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;

		Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection, "descricao"));

		Page<LocalidadeVO> localidade = service.findLocalidadeByDescricao(descricao, pageable);

		/*localidade.stream()
				.forEach(p -> p.add(linkTo(methodOn(ArmazemController.class).findAll(0, 12, direction)).withSelfRel()));*/

		PagedResources<?> resources = assembler.toResource(localidade);

		return new ResponseEntity<>(resources, HttpStatus.OK);
	}

	@ApiOperation(value = "Buscar uma localidade especifica pelo armazem")
	@GetMapping(value = "/findLocalizacaoByArmazem/{armazem}", produces = { "application/json", "application/xml",
			"application/x-yaml" })
	public Page<Localidade> findLocalizacaoByArmazem(@PathVariable("armazem") Integer armazem,
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "limit", defaultValue = "12") int limit,
			@RequestParam(value = "direction", defaultValue = "asc") String direction) {

		var sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;

		Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection, "descricao"));

		Page<Localidade> localidade = service.findLocalidadesByArmazem(armazem, pageable);
		/*
		 * localidade .stream() .forEach(p -> p.add(
		 * linkTo(methodOn(LocalidadeController.class).findById(p.getKey())).withSelfRel
		 * () ) );
		 */
		return localidade;
	}

	@ApiOperation(value = "Buscar uma localidade especifica pela descrição e armazem")
	@GetMapping(value = "/findLocalizacaoByDescricaoAndArmazem/{descricao}", produces = { "application/json",
			"application/xml", "application/x-yaml" })
	public Page<Localidade> findLocalizacaoByDescricaoAndArmazem(@PathVariable("descricao") String descricao,
			@PathVariable("armazem") Integer armazem, @RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "limit", defaultValue = "12") int limit,
			@RequestParam(value = "direction", defaultValue = "asc") String direction) {

		var sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;

		Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection, "descricao"));

		Page<Localidade> localidade = service.findLocalidadesByDescricaoAndArmazem(descricao, armazem, pageable);
		/*
		 * localidade .stream() .forEach(p -> p.add(
		 * linkTo(methodOn(LocalidadeController.class).findById(p.getKey())).withSelfRel
		 * () ) );
		 */
		return localidade;
	}

	/*@ApiOperation(value = "Buscar uma localidade especifica pelo ID do armazem")
	@GetMapping(value = "/findByArmazemId/{armazem_id}", produces = { "application/json", "application/xml",
			"application/x-yaml" })
	public ResponseEntity<?> findByArmazemId(@PathVariable("armazem_id") Integer armazem_id,
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "limit", defaultValue = "12") int limit,
			@RequestParam(value = "direction", defaultValue = "asc") String direction) {

		var sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;

		Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection, "descricao"));

		Page<LocalidadeVO> localidade = service.findByArmazemCodigo(armazem_id, pageable);

		/*localidade.stream()
				.forEach(p -> p.add(linkTo(methodOn(ArmazemController.class).findAll(0, 12, direction)).withSelfRel()));
		
		PagedResources<?> resources = assembler.toResource(localidade);

		return new ResponseEntity<>(resources, HttpStatus.OK);
	}*/

	@ApiOperation(value = "Buscar uma localidade especifica pelo ID")
	@GetMapping(value = "/{id}", produces = { "application/json", "application/xml", "application/x-yaml" })
	public LocalidadeVO findById(@PathVariable("id") Long id) {
		LocalidadeVO localidade_existente = service.findById(id);
		//localidade_existente.add(linkTo(methodOn(LocalidadeController.class).listarTodos(0, 12, "ASC")).withSelfRel());
		return localidade_existente;
	}

	@ApiOperation(value = "Criar uma nova localidade")
	@PostMapping(produces = { "application/json", "application/xml", "application/xml", "application/x-yaml" })
	public ResponseEntity<LocalidadeVO> criar(@Valid @RequestBody LocalidadeVO request, HttpServletResponse response) {
		LocalidadeVO localidadeVO = service.create(request);
		//localidadeVO.add(linkTo(methodOn(LocalidadeController.class).findById(request.getKey())).withSelfRel());
		return ResponseEntity.status(HttpStatus.CREATED).body(localidadeVO);
	}

	@ApiOperation(value = "Actualizar uma especifica localidade")
	@PutMapping(produces = { "application/json", "application/xml", "application/x-yaml" }, consumes = {
			"application/json", "application/xml", "application/x-yaml" })
	public Localidade update(@RequestBody Localidade request) {
		Localidade localidade = service.update(request);
		// localidadeVO.add(linkTo(methodOn(LocalidadeController.class).findById(localidadeVO.getKey())).withSelfRel());
		return localidade;
	}

	@ApiOperation(value = "Disativar uma localidade pelo seu ID")
	@PatchMapping(value = "/{id}/{status}", produces = { "application/json", "application/xml", "application/x-yaml" })
	public LocalidadeVO disableArmazem(@PathVariable("id") Long id, @PathVariable("status") Boolean status) {
		LocalidadeVO localidadeVO = service.isActiveOrNotLocalidade(id, status);
		// localidadeVO.add(linkTo(methodOn(LocalidadeController.class).findById(id)).withSelfRel());
		return localidadeVO;
	}

	@ApiOperation(value = "Eliminar uma localidade pelo seu ID")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {
		service.delete(id);
		return ResponseEntity.ok().build();
	}

}