package br.ce.wcaquino.servicos;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.ce.wcaquino.builders.UsuarioBuilder;
import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.exceptions.FilmeSemEstoqueException;
import br.ce.wcaquino.exceptions.LocadoraException;

//@RunWith(Parameterized.class)
public class CalculoValorLotacaoTest {

	private LocacaoService locacaoService;
	
	private Double valorLotacao;
	
	@Before
	public void setup() {
		locacaoService = new LocacaoService();
	}
	
	@Test
	public void deveCalcularValorLotacaoConsiderandoDescontos() throws LocadoraException, FilmeSemEstoqueException {
		//cenario
		Usuario usuario = UsuarioBuilder.umUsuario().agora();
		
		Filme filme1 = new Filme("Oz2", 2, 5.0);
		Filme filme2 = new Filme("The Lion King1", 3, 5.0);
		Filme filme3 = new Filme("Titanic", 10, 5.0);
		

		List<Filme> filmes = Arrays.asList(filme1, filme2, filme3);

		//ação
		Locacao locacao = locacaoService.alugarFilme(usuario, filmes);
		
		valorLotacao = 13.75d;
		
		Assert.assertEquals(Double.valueOf(valorLotacao), locacao.getValor(), 0.01);
		
	}
}
