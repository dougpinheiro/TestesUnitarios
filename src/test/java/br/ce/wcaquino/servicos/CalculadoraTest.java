package br.ce.wcaquino.servicos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.ce.wcaquino.exceptions.DividindoPorZeroException;


public class CalculadoraTest {
	
	private Calculadora calc;

	@Before
	public void setup() {
		calc = new Calculadora();
	}
	
	@Test
	public void calculadora_deveSomarDoisNumeros() {
		//cenário
		int a = 4;
		int b = 5;
		
		//ação
		int resultado = calc.soma(a,b);
		
		//verificação
		Assert.assertEquals(9, resultado);
		
	}

	@Test
	public void calculadora_deveSubtrairDoisNumeros() {
		//cenário
		int a = 5;
		int b = 5;
		
		//ação
		int resultado = calc.subtrai(a,b);
		
		//verificação
		Assert.assertEquals(0, resultado);
		
	}

	@Test
	public void calculadora_deveDividirDoisNumeros() throws DividindoPorZeroException {
		//cenário
		int a = 5;
		int b = 5;
		
		//ação
		int resultado = calc.divide(a,b);
		
		//verificação
		Assert.assertEquals(1, resultado);
		
	}

	@Test(expected=DividindoPorZeroException.class)
	public void calculadora_deveLancarExcecaoAoDividirPorZero() throws DividindoPorZeroException {
		//cenário
		int a = 5;
		int b = 0;
		
		//ação
		calc.divide(a,b);
		
		
	}

	@Test
	public void calculadora_deveMultiplicarDoisNumeros() {
		//cenário
		int a = 5;
		int b = 5;
		
		//ação
		int resultado = calc.multiplica(a,b);
		
		//verificação
		Assert.assertEquals(25, resultado);
		
	}

}
