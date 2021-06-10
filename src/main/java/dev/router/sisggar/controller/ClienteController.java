package dev.router.sisggar.controller;


import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
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

import dev.router.sisggar.data.model.Cliente;
import dev.router.sisggar.services.ClienteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "ClienteEndpoint")
@RestController
@RequestMapping("/api/cliente/v1")
public class ClienteController {
	
	@Autowired
	private ClienteService service;
	
	@ApiOperation(value = "Find all cliente" ) 
	@GetMapping(produces = { "application/json", "application/xml", "application/x-yaml" })

	public Page<Cliente> findAll(
			@RequestParam(value="page", defaultValue = "0") int page,
			@RequestParam(value="limit", defaultValue = "12") int limit,
			@RequestParam(value="direction", defaultValue = "asc") String direction) {
		
		var sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;
		
		Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection, "nome"));
		
		Page<Cliente> cliente =  service.findAll(pageable);
		
		
		 /** Adicionar os links:
		 * 					1 - Extrato do cliente
		 * 					2 - Nota Entrega ...
		 * */
		/*cliente
			.stream()
			.forEach(p -> p.add(
					linkTo(methodOn(LocalidadeController.class).findById(p.getKey())).withSelfRel()
				)
			);*/
		
		return cliente;
	}
	
	
	@ApiOperation(value = "Find a specific cliente by nome" ) 
	@GetMapping(value = "/findClienteLikeNome/{nome}", produces = { "application/json", "application/xml", "application/x-yaml" })
	public Page<Cliente> findClienteLikeNome(
			@PathVariable("nome") String nome,
			@RequestParam(value="page", defaultValue = "0") int page,
			@RequestParam(value="limit", defaultValue = "12") int limit,
			@RequestParam(value="direction", defaultValue = "asc") String direction) {
		
		var sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;
		
		Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection, "nome"));
		
		Page<Cliente> cliente =  service.findClienteLikeNome(nome, pageable);
		/*
		 * Adicionar os links:
		 * 					1 - Extrato do cliente
		 * 					2 - Nota Entrega ...
		 * */
		/*cliente
			.stream()
			.forEach(p -> p.add(
					linkTo(methodOn(ClienteController.class).findById(p.getId())).withSelfRel()
				)
			);*/
		
		return cliente;
	}	
	
	
	@ApiOperation(value = "Find a specific cliente by telefone" ) 
	@GetMapping(value = "/findClienteLikeTelefone/{telefone}", produces = { "application/json", "application/xml", "application/x-yaml" })
	public Page<Cliente> findClienteByTelefone(
			@PathVariable("telefone") String telefone,
			@RequestParam(value="page", defaultValue = "0") int page,
			@RequestParam(value="limit", defaultValue = "12") int limit,
			@RequestParam(value="direction", defaultValue = "asc") String direction) {
		
		var sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;
		
		Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection, "nome"));
		
		Page<Cliente> cliente =  service.findClienteLikeTelefone(telefone, pageable);
		/*
		 * Adicionar os links:
		 * 					1 - Extrato do cliente
		 * 					2 - Nota Entrega ...
		 * */
		/*cliente
			.stream()
			.forEach(p -> p.add(
					linkTo(methodOn(LocalidadeController.class).findById(p.getKey())).withSelfRel()
				)
			);
		 */
		return cliente;
	}	
	
	
	@ApiOperation(value = "Find a specific cliente by nif" ) 
	@GetMapping(value = "/findClienteLikeNif/{nif}", produces = { "application/json", "application/xml", "application/x-yaml" })
	public Page<Cliente> findClienteLikeNif(
			@PathVariable("nif") String nif,
			@RequestParam(value="page", defaultValue = "0") int page,
			@RequestParam(value="limit", defaultValue = "12") int limit,
			@RequestParam(value="direction", defaultValue = "asc") String direction) {
		
		var sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;
		
		Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection, "nome"));
		
		Page<Cliente> cliente =  service.findClienteLikeNif(nif, pageable);
		/*
		 * Adicionar os links:
		 * 					1 - Extrato do cliente
		 * 					2 - Nota Entrega ...
		 * */
		/*cliente
			.stream()
			.forEach(p -> p.add(
					linkTo(methodOn(LocalidadeController.class).findById(p.getKey())).withSelfRel()
				)
			);
		 */
		return cliente;
	}
	
	
	@ApiOperation(value = "Find all clientes by status" ) 
	@GetMapping(value = "/findClienteByStatus/{status}", produces = { "application/json", "application/xml", "application/x-yaml" })
	public Page<?> findClienteByStatus(
			@PathVariable("status") boolean status,
			@RequestParam(value="page", defaultValue = "0") int page,
			@RequestParam(value="limit", defaultValue = "12") int limit,
			@RequestParam(value="direction", defaultValue = "asc") String direction) {
		
		var sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;
		
		Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection, "nome"));
		
		Page<Cliente> cliente =  service.findClienteByStatus(status, pageable);
		/*
		 * Adicionar os links:
		 * 					1 - Extrato do cliente
		 * 					2 - Nota Entrega ...
		 * */
		/*cliente
			.stream()
			.forEach(p -> p.add(
					linkTo(methodOn(LocalidadeController.class).findById(p.getKey())).withSelfRel()
				)
			);
		 */
		return cliente;
	}
	
	
	@ApiOperation(value = "Find all clientes sem posse" ) 
	@GetMapping(value = "/findClienteSemPosse", produces = { "application/json", "application/xml", "application/x-yaml" })
	public Page<Cliente> findClienteSemPossse(
			@RequestParam(value="page", defaultValue = "0") int page,
			@RequestParam(value="limit", defaultValue = "12") int limit,
			@RequestParam(value="direction", defaultValue = "asc") String direction) {
		
		var sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;
		
		Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection, "nome"));
		
		Page<Cliente> cliente =  service.findAllSemPosse(pageable);
		/*
		 * Adicionar os links:
		 * 					1 - Extrato do cliente
		 * 					2 - Nota Entrega ...
		 * */
		/*cliente
			.stream()
			.forEach(p -> p.add(
					linkTo(methodOn(LocalidadeController.class).findById(p.getKey())).withSelfRel()
				)
			);
		 */
		return cliente;
	}	

	
	
	
	@ApiOperation(value = "Busca um cliente especifico pelo seu ID" )
	@GetMapping(value = "/{id}", produces = { "application/json", "application/xml", "application/x-yaml" })
	public Cliente findById(@PathVariable("id") Long id) {
		Cliente cliente = service.findById(id);
		//clienteVO.add(linkTo(methodOn(LocalidadeController.class).findById(id)).withSelfRel());
		return cliente;
	}		
	
	@ApiOperation(value = "Create a new cliente") 
	@PostMapping(produces = { "application/json", "application/xml", "application/x-yaml" }, 
			consumes = { "application/json", "application/xml", "application/x-yaml" })
	public Cliente create(@RequestBody Cliente cliente) {
		Cliente cliente_salvo = service.create(cliente);
		//clienteVO.add(linkTo(methodOn(LocalidadeController.class).findById(clienteVO.getKey())).withSelfRel());
		return cliente_salvo;
	}
	
	@ApiOperation(value = "Update a specific cliente")
	@PutMapping(produces = { "application/json", "application/xml", "application/x-yaml" }, 
			consumes = { "application/json", "application/xml", "application/x-yaml" })
	public Cliente update(@RequestBody Cliente cliente) {
		Cliente cliente_save = service.update(cliente);
		//clienteVO.add(linkTo(methodOn(LocalidadeController.class).findById(clienteVO.getKey())).withSelfRel());
		return cliente_save;
	}	
	
	
	@ApiOperation(value = "Desactiva um cliente pelo deu ID" )
	@PatchMapping(value = "/{id}", produces = { "application/json", "application/xml", "application/x-yaml" })
	public Cliente disableCliente(
			@PathVariable("id") Long id, 
			@RequestParam("status") Boolean status) {
		Cliente cliente = service.isActiveOrNotCliente(id,status);
		//clienteVO.add(linkTo(methodOn(LocalidadeController.class).findById(id)).withSelfRel());
		return cliente;
	}
	
	
	@ApiOperation(value = "Elimina um cliente pelo seu ID")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {
		service.delete(id);
		return ResponseEntity.ok().build();
	}	
	
}