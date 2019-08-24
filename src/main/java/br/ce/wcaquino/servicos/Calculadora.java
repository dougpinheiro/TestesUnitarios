package br.ce.wcaquino.servicos;

import br.ce.wcaquino.exceptions.DividindoPorZeroException;

public class Calculadora {

	public int soma(int a, int b) {
		return a+b;
	}

	public int subtrai(int a, int b) {
		return a-b;
	}

	public int divide(int a, int b) throws DividindoPorZeroException {
		if(b == 0) {
			throw new DividindoPorZeroException();
		}
		return a/b;
	}

	public int multiplica(int a, int b) {
		return a * b;
	}

}
