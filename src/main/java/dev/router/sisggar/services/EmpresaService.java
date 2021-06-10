package dev.router.sisggar.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.router.sisggar.data.model.Empresa;
import dev.router.sisggar.exception.NegocioException;
import dev.router.sisggar.repository.EmpresaRepository;

@Service
public class EmpresaService {
	
	@Autowired
	EmpresaRepository repository;
		
	@Transactional
	public Empresa save(Empresa request) {
		boolean empresaExiste = repository.findByDesignacao(request.getDesignacao())
				.stream()
				.anyMatch(empresa_existente -> !empresa_existente.equals(request));
		
		if(empresaExiste) {
			throw new NegocioException("Já existe uma empresa com esta designação");
		}
		return repository.save(request);
	}
	
	public Page<Empresa> pesquisar(String designacao,Pageable pageable) {
		return repository.findByDesignacaoContaining(designacao,pageable);
	}
	
	public Page<Empresa> listar(Pageable pageable) {
		return repository.findAll(pageable);
	}
	
	
	public Empresa buscarEmpresaPeloCodigo(Long codigo) {
		Optional<Empresa> empresaSalva = repository.findById(codigo);
		if (!empresaSalva.isPresent()) {
			throw new NegocioException("Não existe uma empresa com este código");
		}
		return empresaSalva.get();
	}
	
	public Optional<Empresa> findByDesignacao(String designacao) {
		return repository.findByDesignacao(designacao);
	}
		
	public Empresa update(Empresa empresa) {
		boolean empresaExiste = repository.existsById(empresa.getCodigo());
		
		if(!empresaExiste) {
			throw new NegocioException("Não existe uma empresa com este ID");
		}
		empresa.setCodigo(empresa.getCodigo());
		return repository.save(empresa);
	}	
	
	public void delete(Long id) {
		Empresa entity = repository.findById(id)
				.orElseThrow(() -> new NegocioException("Não existe uma empresa com este ID"));
		repository.delete(entity);
	}
	
	@Transactional
	public Empresa isActiveOrNotEmpresa(Long id,Boolean status) {
		repository.isActiveOrNotEmpresa(id,status);			
		return repository.findById(id)
				.orElseThrow(() -> new NegocioException("Não existe Empresa com este ID"));
	}
	
	


}
