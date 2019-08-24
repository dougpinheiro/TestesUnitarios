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
		//cen�rio
		int a = 4;
		int b = 5;
		
		//a��o
		int resultado = calc.soma(a,b);
		
		//verifica��o
		Assert.assertEquals(9, resultado);
		
	}

	@Test
	public void calculadora_deveSubtrairDoisNumeros() {
		//cen�rio
		int a = 5;
		int b = 5;
		
		//a��o
		int resultado = calc.subtrai(a,b);
		
		//verifica��o
		Assert.assertEquals(0, resultado);
		
	}

	@Test
	public void calculadora_deveDividirDoisNumeros() throws DividindoPorZeroException {
		//cen�rio
		int a = 5;
		int b = 5;
		
		//a��o
		int resultado = calc.divide(a,b);
		
		//verifica��o
		Assert.assertEquals(1, resultado);
		
	}

	@Test(expected=DividindoPorZeroException.class)
	public void calculadora_deveLancarExcecaoAoDividirPorZero() throws DividindoPorZeroException {
		//cen�rio
		int a = 5;
		int b = 0;
		
		//a��o
		calc.divide(a,b);
		
		
	}

	@Test
	public void calculadora_deveMultiplicarDoisNumeros() {
		//cen�rio
		int a = 5;
		int b = 5;
		
		//a��o
		int resultado = calc.multiplica(a,b);
		
		//verifica��o
		Assert.assertEquals(25, resultado);
		
	}

}
