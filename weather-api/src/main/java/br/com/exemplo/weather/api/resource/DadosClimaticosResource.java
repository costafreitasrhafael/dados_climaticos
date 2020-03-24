package br.com.exemplo.weather.api.resource;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.exemplo.weather.api.dto.DadosGraficoDTO;
import br.com.exemplo.weather.api.model.DadosClimaticos;
import br.com.exemplo.weather.api.repository.DadosClimaticosRepository;

@RestController
@RequestMapping("/dados/climaticos")
public class DadosClimaticosResource {

	@Autowired
	private DadosClimaticosRepository dadosClimaticosRepository;

	@GetMapping
	public List<DadosClimaticos> listar() {
		return dadosClimaticosRepository.findAll();
	}
	
	@GetMapping("/graficos/{cidadeId}")
	public List<DadosGraficoDTO> buscarDados(@PathVariable Long cidadeId, 
											 @RequestParam(name = "dataInicial", required= false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate inicio,
											 @RequestParam(name = "dataFinal", required= false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fim) {

		return dadosClimaticosRepository.buscarDadosAgrupadosPorMesAno(cidadeId, inicio, fim);

	}

}