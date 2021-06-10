package dev.router.sisggar.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.router.sisggar.data.model.Cliente;
import dev.router.sisggar.exception.NegocioException;
import dev.router.sisggar.repository.ClienteRepository;

@Service
public class ClienteService {

	@Autowired
	ClienteRepository repository;

	public Cliente create(Cliente request) {
		boolean empresaExiste = repository.findByNome(request.getNome()).stream()
				.anyMatch(empresa_existente -> !empresa_existente.equals(request));

		if (empresaExiste) {
			throw new NegocioException("Já existe um cliente com este nome ou designação");
		}
		return repository.save(request);
	}

	public Page<Cliente> findAll(Pageable pageable) {
		return repository.findAll(pageable);
	}


	public Cliente findById(Long id) {
		var entity = repository.findById(id).orElseThrow(() -> new NegocioException("Não existe cliente com este ID"));
		return entity;
	}

	public Page<Cliente> findClienteLikeNome(String nome, Pageable pageable) {
		var page = repository.findClienteLikeNome(nome, pageable);
		return page;
	}

	public Page<Cliente> findClienteLikeNif(String nif, Pageable pageable) {
		var page = repository.findClienteLikeNif(nif, pageable);
		return page;
	}
	
	public Page<Cliente> findClienteByStatus(boolean status, Pageable pageable) {
		var page = repository.findClienteByStatus(status, pageable);
		return page;
	}
	
	public Page<Cliente> findAllSemPosse(Pageable pageable) {
		var page = repository.findClienteSemPosse(pageable);
		return page;
	}

	public Page<Cliente> findClienteLikeTelefone(String telefone, Pageable pageable) {
		var page = repository.findClienteLikeTelefone(telefone, pageable);
		return page;
	}

	public Cliente update(Cliente cliente) {
		boolean clienteExiste = repository.existsById(cliente.getCodigo());

		if (!clienteExiste) {
			throw new NegocioException("Não existe um cliente com este ID");
		}
		cliente.setCodigo(cliente.getCodigo());
		return repository.save(cliente);
	}

	public void delete(Long id) {
		Cliente entity = repository.findById(id)
				.orElseThrow(() -> new NegocioException("Não existe armazem com este ID"));
		repository.delete(entity);
	}

	@Transactional
	public Cliente isActiveOrNotCliente(Long id, Boolean status) {
		repository.isActiveOrNotCliente(id, status);
		var entity = repository.findById(id).orElseThrow(() -> new NegocioException("Não existe armazem com este ID"));
		return entity;
	}

}
