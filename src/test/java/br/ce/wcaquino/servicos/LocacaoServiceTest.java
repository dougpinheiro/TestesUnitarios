package br.ce.wcaquino.servicos;

import static br.ce.wcaquino.matchers.MatchersProprios.caiNumaSegunda;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Assume;
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
import br.ce.wcaquino.matchers.MatchersProprios;
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
	public void deveAlugarFilme() throws Exception {
		Assume.assumeFalse(DataUtils.verificarDiaSemana(new Date(), Calendar.SATURDAY));
		
		//cen�rio
		Usuario usuario = new Usuario("Douglas");

		Filme filme1 = new Filme("Oz", 2, 5.0);

		List<Filme> filmes = Arrays.asList(filme1);

		//acao
		Locacao locacao = locacaoService.alugarFilme(usuario, filmes);

		//verifica��o
		//errorCollector.checkThat(locacao.getValor(), CoreMatchers.is(CoreMatchers.equalTo(5.0)));
		//errorCollector.checkThat(DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()), CoreMatchers.is(true));
		errorCollector.checkThat(locacao.getDataLocacao(), MatchersProprios.ehHoje());
		//errorCollector.checkThat(DataUtils.isMesmaData(locacao.getDataRetorno(), DataUtils.obterDataComDiferencaDias(1)), CoreMatchers.is(true));			
		errorCollector.checkThat(locacao.getDataRetorno(), MatchersProprios.ehHojeComDiferencaDias(1));			
	}

	@Test(expected=FilmeSemEstoqueException.class)
	public void deveLancarExcecaoQuandoNaoHouverEstoque() throws Exception {
		//cen�rio
		Usuario usuario = new Usuario("Douglas");
		Filme filme1 = new Filme("Oz", 2, 5.0);
		Filme filme2 = new Filme("The Lion King", 0, 7.0);
		Filme filme3 = new Filme("Titanic", 10, 5.0);

		List<Filme> filmes = Arrays.asList(filme1, filme2, filme3);

		//a��o
		locacaoService.alugarFilme(usuario, filmes);
	}

	@Test
	public void naoDeveAlugarFilmeSemUsuario() throws FilmeSemEstoqueException {
		//cenario
		Filme filme1 = new Filme("Oz", 2, 5.0);
		Filme filme2 = new Filme("The Lion King", 3, 7.0);
		Filme filme3 = new Filme("Titanic", 10, 5.0);

		List<Filme> filmes = Arrays.asList(filme1, filme2, filme3);

		//a��o
		try {
			locacaoService.alugarFilme(null, filmes);
			Assert.fail();
		} catch (LocadoraException e) {
			Assert.assertThat(e.getMessage(), CoreMatchers.is("Usu�rio vazio!"));
		} 


	}

	@Test
	public void naoDeveAlugarFilmeSemFilme() throws FilmeSemEstoqueException, LocadoraException {
		//cenario
		Usuario usuario = new Usuario("Douglas");

		expectedException.expect(LocadoraException.class);
		expectedException.expectMessage("Filme vazio!");

		//a��o
		locacaoService.alugarFilme(usuario, null);

	}

	@Test
	public void deveAplicarDescontoDe25PctNoTerceiroFilme() throws LocadoraException, FilmeSemEstoqueException {
		//cenario
		Usuario usuario = new Usuario("douglas.pinheiro");
		
		Filme filme1 = new Filme("Oz2", 2, 5.0);
		Filme filme2 = new Filme("The Lion King1", 3, 5.0);
		Filme filme3 = new Filme("Titanic", 10, 5.0);
		

		List<Filme> filmes = Arrays.asList(filme1, filme2, filme3);

		//a��o
		Locacao locacao = locacaoService.alugarFilme(usuario, filmes);
		
		Assert.assertEquals(Double.valueOf(13.75d), locacao.getValor(), 0.01);
		
	}

	@Test
	public void deveAplicarDescontoDe50PctNoQuartoFilme() throws LocadoraException, FilmeSemEstoqueException {
		//cenario
		Usuario usuario = new Usuario("douglas.pinheiro");
		
		Filme filme1 = new Filme("Oz2", 2, 5.0);
		Filme filme2 = new Filme("The Lion King1", 3, 5.0);
		Filme filme3 = new Filme("Titanic", 10, 5.0);
		Filme filme4 = new Filme("The Lion King", 3, 5.0);
		
		
		List<Filme> filmes = Arrays.asList(filme1, filme2, filme3, filme4);
		
		//a��o
		Locacao locacao = locacaoService.alugarFilme(usuario, filmes);
		
		Assert.assertEquals(Double.valueOf(16.25d), locacao.getValor(), 0.01);
		
	}

	@Test
	public void deveAplicarDescontoDe75PctNoQuintoFilme() throws LocadoraException, FilmeSemEstoqueException {
		//cenario
		Usuario usuario = new Usuario("douglas.pinheiro");
		
		Filme filme1 = new Filme("Oz2", 2, 5.0);
		Filme filme2 = new Filme("The Lion King1", 3, 5.0);
		Filme filme3 = new Filme("Titanic", 10, 5.0);
		Filme filme4 = new Filme("The Lion King", 3, 5.0);
		Filme filme5 = new Filme("Titanic", 10, 5.0);
		
		
		List<Filme> filmes = Arrays.asList(filme1, filme2, filme3, filme4, filme5);
		
		//a��o
		Locacao locacao = locacaoService.alugarFilme(usuario, filmes);
		
		Assert.assertEquals(Double.valueOf(17.50d), locacao.getValor(), 0.01);
		
	}

	@Test
	public void deveAplicarDescontoDe100PctNoSextoFilme() throws LocadoraException, FilmeSemEstoqueException {
		//cenario
		Usuario usuario = new Usuario("douglas.pinheiro");
		
		Filme filme1 = new Filme("Oz2", 2, 5.0);
		Filme filme2 = new Filme("The Lion King1", 3, 5.0);
		Filme filme3 = new Filme("Titanic", 10, 5.0);
		Filme filme4 = new Filme("The Lion King", 3, 5.0);
		Filme filme5 = new Filme("Titanic", 10, 5.0);
		Filme filme6 = new Filme("Oz2", 2, 5.0);
		
		
		List<Filme> filmes = Arrays.asList(filme1, filme2, filme3, filme4, filme5, filme6);
		
		//a��o
		Locacao locacao = locacaoService.alugarFilme(usuario, filmes);
		
		Assert.assertEquals(Double.valueOf(17.50d), locacao.getValor(), 0.01);
		
	}
	
	@Test
	public void naoDeveDevolverFilmeNoDomingo() throws LocadoraException, FilmeSemEstoqueException {
		Assume.assumeTrue(DataUtils.verificarDiaSemana(new Date(), Calendar.SATURDAY));

		//cen�rio
		Usuario usuario = new Usuario("UsuarioTeste");
		Filme filme1 = new Filme("Oz2", 1, 5.0);
		List<Filme> filmes = Arrays.asList(filme1);
		
		//a��o
		Locacao locacao = locacaoService.alugarFilme(usuario, filmes);
		
		//verifica��o
		//Assert.assertThat(locacao.getDataRetorno(), MatchersProprios.caiEm(Calendar.WEDNESDAY));
		Assert.assertThat(locacao.getDataRetorno(), caiNumaSegunda());
		//Assert.assertThat(locacao.getDataRetorno(), new DiaSemanaMatcher(Calendar.MONDAY));
	}

	
	




}
