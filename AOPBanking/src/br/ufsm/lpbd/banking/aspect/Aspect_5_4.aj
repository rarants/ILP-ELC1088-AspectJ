package br.ufsm.lpbd.banking.aspect;

import br.ufsm.lpbd.banking.core.Account;
import br.ufsm.lpbd.banking.core.OverdraftAccount;
import br.ufsm.lpbd.banking.exception.InsufficientBalanceException;

public aspect Aspect_5_4 {
	 pointcut compensasao(Account ac, float amount) : execution(* Account.debit(float)) 
		&& !target(OverdraftAccount) 
		&& args(amount) 
		&& target(ac);
	 
	 void around(Account ac, float amount) throws InsufficientBalanceException : compensasao(ac, amount) {
		boolean hasAmount = false;
		if (ac.getBalance() - amount < 100) {
			Customer costumer = ac.getCustomer();
			List<Account> overdraftAccounts = costumer.getOverdraftAccounts();
			for(Account overdraftAccount : overdraftAccounts) {
				float required = 100 - (ac.getBalance() - amount);
				if (overdraftAccount.getBalance() >= required + 100) {
					hasAmount = true;
					overdraftAccount.debit(required);
					ac.credit(required);
					System.out.println("[CRÉDITO] Transação realizada. 
						\nCrédito realizado via empréstimo de " 
						+ required
						+ " para a conta "
						+ ac.getAccountNumber().);
				}
			}
			if (hasAmount == false) {
				throw new InsufficientBalanceException(
					"[TRANSAÇÃO RECUSADA] O saldo da conta ficará menor que o limite de segurança"
				);
			}
	 	} else {
			ac.credit(amount);
			System.out.println("[CRÉDITO] Transação realizada. 
				\nCrédito de "
				+ amount
				+ "realizado para a conta "
				+ ac.getAccountNumber().);
		}
	 }
}