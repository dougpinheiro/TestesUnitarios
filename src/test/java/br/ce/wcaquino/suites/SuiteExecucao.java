package br.ce.wcaquino.suites;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runners.Suite.SuiteClasses;

import br.ce.wcaquino.servicos.CalculadoraTest;
import br.ce.wcaquino.servicos.CalculoValorLotacaoTest;
import br.ce.wcaquino.servicos.LocacaoServiceTest;

//@RunWith(Suite.class)
@SuiteClasses({
	CalculadoraTest.class,
	CalculoValorLotacaoTest.class,
	LocacaoServiceTest.class
})
public class SuiteExecucao {
	@BeforeClass
	public static void before() {
		System.out.println("Before class...");
	}

	@AfterClass
	public static void after() {
		System.out.println("After class...");
	}
	
}
