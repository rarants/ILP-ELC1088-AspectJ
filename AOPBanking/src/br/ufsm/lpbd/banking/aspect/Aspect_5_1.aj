package br.ufsm.lpbd.banking.aspect;

import br.ufsm.lpbd.banking.core.Account;

public aspect Aspect_5_1 {
	pointcut saqueMaior(Account ac, float amount) : call(* Account.debit(float)) 
		&& args(quant) && target(ac);
	
	before(Account ac, float amount): saqueMaior(ac, amount) {
		if (amount > 10000) 
			System.out.println("[SAQUE] Saque de " 
				+ amount 
				+ " foi realizado pela conta "
				+ ac.getAccountNumber());
	}
}