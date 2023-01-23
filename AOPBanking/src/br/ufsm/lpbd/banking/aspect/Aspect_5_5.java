package br.ufsm.lpbd.banking.aspect;

import br.ufsm.lpbd.banking.core.OverdraftAccount;
import br.ufsm.lpbd.banking.core.Account;


public aspect Aspect_5_5 {
	pointcut taxacao(OverdraftAccount ac, float amount) : execution(* Account.debit(float)) 
		&& args(amount) && target(ac);
	
	after(OverdraftAccount ac, float amount) : taxacao(ac, amount) {
		ac.registerTax(amount*0.01);
		System.out.println(
			"[TAXAÇÃO] Taxa de 1% aplicada pela realização de empréstimo para a conta"
			+ ac.getAccountNumber());
	}
}