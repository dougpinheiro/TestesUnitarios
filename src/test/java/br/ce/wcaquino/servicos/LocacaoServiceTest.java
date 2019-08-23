package br.ce.wcaquino.servicos;

import java.util.Date;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
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

	@Rule
	public ErrorCollector errorCollector = new ErrorCollector();
	
	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	@Test
	public void testeLocacao() throws Exception {
		//cenário
		LocacaoService locacaoService = new LocacaoService();
		Usuario usuario = new Usuario("Douglas");
		Filme filme = new Filme("Oz", 2, 5.0);

		//acao
		Locacao locacao = locacaoService.alugarFilme(usuario, filme);

		//verificação
		errorCollector.checkThat(locacao.getValor(), CoreMatchers.is(CoreMatchers.equalTo(5.0)));
		errorCollector.checkThat(DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()), CoreMatchers.is(true));
		errorCollector.checkThat(DataUtils.isMesmaData(locacao.getDataRetorno(), DataUtils.obterDataComDiferencaDias(1)), CoreMatchers.is(true));

	}
	
	@Test(expected=FilmeSemEstoqueException.class)
	public void testLocacao_filmeSemEstoque() throws Exception {
		//cenário
		LocacaoService locacaoService = new LocacaoService();
		Usuario usuario = new Usuario("Douglas");
		Filme filme = new Filme("Oz", 0, 5.0);
		
		//ação
		locacaoService.alugarFilme(usuario, filme);
	}
	
	@Test
	public void testLocacao_usuarioVazio() throws FilmeSemEstoqueException {
		//cenario
		LocacaoService locacaoService = new LocacaoService();
		Filme filme = new Filme("Oz", 1, 5.0);
		Usuario usuario = new Usuario("Douglas");
		
		//ação
		try {
			locacaoService.alugarFilme(null, filme);
			Assert.fail();
		} catch (LocadoraException e) {
			Assert.assertThat(e.getMessage(), CoreMatchers.is("Usuário vazio!"));
		} 
		
		System.out.println("Forma Robusta");
		
	}

	@Test
	public void testLocacao_filmeVazio() throws FilmeSemEstoqueException, LocadoraException {
		//cenario
		LocacaoService locacaoService = new LocacaoService();
		Usuario usuario = new Usuario("Douglas");
		
		expectedException.expect(LocadoraException.class);
		expectedException.expectMessage("Filme vazio!");
		
		//ação
		locacaoService.alugarFilme(usuario, null);
		System.out.println("Forma Nova"); //Não aparece no log
		
	}

	

}
