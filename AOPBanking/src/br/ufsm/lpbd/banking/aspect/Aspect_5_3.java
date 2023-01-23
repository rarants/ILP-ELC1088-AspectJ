package br.ufsm.lpbd.banking.aspect;

import br.ufsm.lpbd.banking.core.Account;
import br.ufsm.lpbd.banking.exception.InsufficientBalanceException;

public aspect Aspect_5_3 {
	 pointcut saldoMenor(Account ac, float amount) : execution(* Account.debit(float)) 
	 		&& !target(OverdraftAccount)
			&& args(amount) 
			&& target(ac);
	 
	 void around(Account ac, float amount) throws InsufficientBalanceException : saldoMenor(ac, amount) {
		 if (ac.getBalance() - amount < 100) {
			throw new InsufficientBalanceException(
				"[TRANSAÇÃO RECUSADA] O saldo da conta ficará menor que o limite de segurança"
			);
		 }
	 }
}
