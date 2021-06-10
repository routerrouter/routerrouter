package dev.router.sisggar.controller;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import dev.router.sisggar.data.model.Empresa;
import dev.router.sisggar.event.RecursoCriadoEvent;
import dev.router.sisggar.services.EmpresaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "EmpresaEndpoint")
@RestController
@RequestMapping("/api/empresa/v1")
public class EmpresaController {

	@Autowired
	private EmpresaService service;
	
	@Autowired
	private ApplicationEventPublisher publisher;

	@ApiOperation(value = "Create a new armazem")
	@PostMapping(produces = { "application/json", "application/xml", "application/x-yaml" }, consumes = {
			"application/json", "application/xml", "application/x-yaml" })
	//@PreAuthorize("hasAuthority('ROLE_CADASTRAR_PESSOA') and #oauth2.hasScope('write')")
	public ResponseEntity<Empresa> criar(@Valid @RequestBody Empresa pessoa, HttpServletResponse response) {
		Empresa empresaSalva = service.save(pessoa);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, empresaSalva.getCodigo()));
		return ResponseEntity.status(HttpStatus.CREATED).body(empresaSalva);
	}

	@ApiOperation(value = "buscar empresa")
	@GetMapping(value = "/{codigo}",produces = { "application/json", "application/xml", "application/x-yaml" })
	//@PreAuthorize("hasAuthority('ROLE_PESQUISAR_PESSOA') and #oauth2.hasScope('read')")
	public ResponseEntity<Empresa> buscarPeloCodigo(@PathVariable Long codigo) {
		Empresa empresa = service.buscarEmpresaPeloCodigo(codigo);
		return ResponseEntity.ok(empresa);
	}
	
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	//@PreAuthorize("hasAuthority('ROLE_REMOVER_PESSOA') and #oauth2.hasScope('write')")
	public void remover(@PathVariable Long codigo) {
		service.delete(codigo);
	}
	
	@PutMapping("/{codigo}")
	//@PreAuthorize("hasAuthority('ROLE_CADASTRAR_PESSOA') and #oauth2.hasScope('write')")
	public ResponseEntity<Empresa> atualizar(@PathVariable Long codigo, @Valid @RequestBody Empresa empresa) {
		Empresa empresaSalva = service.update(empresa);
		return ResponseEntity.ok(empresaSalva);
	}
	
	@ApiOperation(value = "Desactivar Empresa")
	@PatchMapping(value = "/{codigo}", produces = { "application/json", "application/xml", "application/x-yaml" })
	//@PreAuthorize("hasAuthority('ROLE_CADASTRAR_PESSOA') and #oauth2.hasScope('write')")
	public Empresa desactivar(@PathVariable("codigo") Long codigo, @RequestParam("status") Boolean status) {
		Empresa empresa = service.isActiveOrNotEmpresa(codigo, status);
		// EmpresaVO.add(linkTo(methodOn(ArmazemController.class).findById(codigo)).withSelfRel());
		return empresa;
	}
	
	@GetMapping(value = "/pesquisar")
	//@PreAuthorize("hasAuthority('ROLE_PESQUISAR_PESSOA')")
	public Page<Empresa> pesquisar(@RequestParam(required = false, defaultValue = "%") String designacao, Pageable pageable) {
		return service.pesquisar(designacao, pageable);
	}
	
	@GetMapping
	//@PreAuthorize("hasAuthority('ROLE_PESQUISAR_PESSOA')")
	public Page<Empresa> listar(Pageable pageable) {
		return service.listar(pageable);
	}
	

}