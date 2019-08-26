package br.ce.wcaquino.matchers;

import java.util.Calendar;
import java.util.Date;

import org.hamcrest.Matcher;

public class MatchersProprios {

	public static DiaSemanaMatcher caiEm(Integer diaSemana) {
		return new DiaSemanaMatcher(diaSemana);
	}
	
	public static DiaSemanaMatcher caiNumaSegunda() {
		return new DiaSemanaMatcher(Calendar.MONDAY);
	}

	public static Matcher<Date> ehHoje() {
		return ehHojeComDiferencaDias(0);
	}

	public static Matcher<Date> ehHojeComDiferencaDias(int i) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, i);
		return new DiaSemanaMatcher(calendar.get(Calendar.DAY_OF_WEEK));
	}
}
