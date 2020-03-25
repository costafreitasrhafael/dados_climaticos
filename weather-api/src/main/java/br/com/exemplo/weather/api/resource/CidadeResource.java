package br.com.exemplo.weather.api.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.exemplo.weather.api.dto.CidadeDTO;
import br.com.exemplo.weather.api.model.Cidade;
import br.com.exemplo.weather.api.repository.CidadeRepository;

@RestController
@RequestMapping("/cidades")
public class CidadeResource {

	@Autowired
	private CidadeRepository cidadeRepository;

	/**
	 * Listar todas as cidades
	 * @return Lista de cidades
	 */
	@GetMapping
	public List<CidadeDTO> listar() {
		return cidadeRepository.listar();
	}
	
	/**
	 * Buscar cidade pelo nome
	 * @param nome
	 * @return Cidade
	 */
	@GetMapping("/porNome")
	public Cidade findByName(@RequestParam String nome) {
		return cidadeRepository.findByNomeLike(nome);
	}
	
	/**
	 * Buscar cidade que está syncronizando os dados do servidor openweathermap
	 * @return Cidade
	 */
	@GetMapping("/sync")
	public CidadeDTO findBySync() {
		Cidade cidade = cidadeRepository.findByAtivoIsTrue();
		return new CidadeDTO(cidade.getId(), cidade.getNome());
	}

	/**
	 * Método responsavel por alterar a cidade que vai syncronizar os dados climáticos
	 * @param codigo da cidade
	 * @return Cidade
	 */
	@PutMapping("/{codigo}/ativo")
	public ResponseEntity<Cidade> atualizarPropriedadeAtivo(@PathVariable Long codigo) {

		Cidade cidade = null;
		
		try {
			Cidade cidadeAtiva = cidadeRepository.findByAtivoIsTrue();		
			cidade = cidadeRepository.findById(codigo).get();
			
			cidadeAtiva.setAtivo(false);
			cidade.setAtivo(true);

			cidadeRepository.save(cidadeAtiva);
			cidadeRepository.save(cidade);

			return ResponseEntity.status(HttpStatus.OK).body(cidade);

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(cidade);
		}
		
	}

}
