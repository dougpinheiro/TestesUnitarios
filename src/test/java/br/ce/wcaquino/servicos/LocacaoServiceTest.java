package br.ce.wcaquino.servicos;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.exceptions.FilmeSemEstoqueException;
import br.ce.wcaquino.exceptions.LocadoraException;
import br.ce.wcaquino.utils.DataUtils;

public class LocacaoServiceTest {

	private LocacaoService locacaoService;
	
	private static int count = 0;
	
	@Rule
	public ErrorCollector errorCollector = new ErrorCollector();
	
	@Rule
	public ExpectedException expectedException = ExpectedException.none();
	
	@BeforeClass
	public static void setupBeforeClass() {
	}
	
	@AfterClass
	public static void tearDownAfterClass() {
		
	}
	
	@Before
	public void setup() {
		locacaoService = new LocacaoService();
		count++;
	}
	
	@After
	public void tearDown() {
	}

	@Test
	public void testeLocacao() throws Exception {
		//cenário
		Usuario usuario = new Usuario("Douglas");
		
		Filme filme1 = new Filme("Oz", 2, 5.0);
		Filme filme2 = new Filme("The Lion King", 3, 7.0);
		Filme filme3 = new Filme("Titanic", 10, 5.0);
		
		List<Filme> filmes = Arrays.asList(filme1, filme2, filme3);

		//acao
		Locacao locacao = locacaoService.alugarFilme(usuario, filmes);

		//verificação
		errorCollector.checkThat(locacao.getValor(), CoreMatchers.is(CoreMatchers.equalTo(17.0)));
		errorCollector.checkThat(DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()), CoreMatchers.is(true));
		errorCollector.checkThat(DataUtils.isMesmaData(locacao.getDataRetorno(), DataUtils.obterDataComDiferencaDias(1)), CoreMatchers.is(true));			
	}
	
	@Test(expected=FilmeSemEstoqueException.class)
	public void testLocacao_filmeSemEstoque() throws Exception {
		//cenário
		Usuario usuario = new Usuario("Douglas");
		Filme filme1 = new Filme("Oz", 2, 5.0);
		Filme filme2 = new Filme("The Lion King", 0, 7.0);
		Filme filme3 = new Filme("Titanic", 10, 5.0);
		
		List<Filme> filmes = Arrays.asList(filme1, filme2, filme3);
		
		//ação
		locacaoService.alugarFilme(usuario, filmes);
	}
	
	@Test
	public void testLocacao_usuarioVazio() throws FilmeSemEstoqueException {
		//cenario
		Filme filme1 = new Filme("Oz", 2, 5.0);
		Filme filme2 = new Filme("The Lion King", 3, 7.0);
		Filme filme3 = new Filme("Titanic", 10, 5.0);
		
		List<Filme> filmes = Arrays.asList(filme1, filme2, filme3);
		
		//ação
		try {
			locacaoService.alugarFilme(null, filmes);
			Assert.fail();
		} catch (LocadoraException e) {
			Assert.assertThat(e.getMessage(), CoreMatchers.is("Usuário vazio!"));
		} 
		
		
	}

	@Test
	public void testLocacao_filmeVazio() throws FilmeSemEstoqueException, LocadoraException {
		//cenario
		Usuario usuario = new Usuario("Douglas");
		
		expectedException.expect(LocadoraException.class);
		expectedException.expectMessage("Filme vazio!");
		
		//ação
		locacaoService.alugarFilme(usuario, null);
		
	}

	

}
