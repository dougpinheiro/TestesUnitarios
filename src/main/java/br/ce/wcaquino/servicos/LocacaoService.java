package br.ce.wcaquino.servicos;

import static br.ce.wcaquino.utils.DataUtils.adicionarDias;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.exceptions.FilmeSemEstoqueException;
import br.ce.wcaquino.exceptions.LocadoraException;
import br.ce.wcaquino.utils.DataUtils;

public class LocacaoService {
	
	public Locacao alugarFilme(Usuario usuario, List<Filme> filmes) throws LocadoraException, FilmeSemEstoqueException {
		
		if(usuario == null) {
			throw new LocadoraException("Usu�rio vazio!");
		}

		if(filmes == null || filmes.isEmpty()) {
			throw new LocadoraException("Filme vazio!");
		}
		
		for(Filme f : filmes) {
			if(f.getEstoque() == 0) {
				throw new FilmeSemEstoqueException();
			}
		}
				
		Locacao locacao = new Locacao();
		locacao.setFilmes(filmes);
		locacao.setUsuario(usuario);
		locacao.setDataLocacao(new Date());
		
		double total = 0.0d;
		
		int count = 0;
		
		for(Filme f : filmes) {
			count++;
			switch (count) {
			case 3:
				total+=(f.getPrecoLocacao() * 0.75);								
				break;
			case 4:
				total+=(f.getPrecoLocacao() * 0.50);								
				break;
			case 5:
				total+=(f.getPrecoLocacao() * 0.25);												
				break;
			case 6:
				total+=(f.getPrecoLocacao() * 0);																
				break;
			default:
				total+=f.getPrecoLocacao();				
				break;
			}
		}
		
		locacao.setValor(total);

		//Entrega no dia seguinte
		Date dataEntrega = new Date();
		dataEntrega = adicionarDias(dataEntrega, 1);
		
		if(DataUtils.verificarDiaSemana(dataEntrega, Calendar.SUNDAY)) {
			dataEntrega = adicionarDias(dataEntrega, 1);			
		}
		
		locacao.setDataRetorno(dataEntrega);
		
		//Salvando a locacao...	
		//TODO adicionar método para salvar
		
		return locacao;
	}

}