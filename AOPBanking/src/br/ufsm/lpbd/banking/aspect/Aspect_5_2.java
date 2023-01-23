package br.ufsm.lpbd.banking.aspect;

import br.ufsm.lpbd.banking.core.Account;

public aspect Aspect_5_2 {
	pointcut logging(Account ac, float amount) : (execution(* Account.credit(float))
		|| execution(void Account.debit(float))) 
		&& args(amount) && target(ac);
	
	before (Account ac, float amount) : logging(ac, amount) {
		System.out.println("_______________________________________\n");
		System.out.println("[TRANSAÇÃO INICIADA] " 
			+ thisJoinPointStaticPart.getSignature().getName() 
			+ ". \nTransação realizada pela conta "
			+ ac.getAccountNumber()
			+ " no valor de "
			+ amount);
	}
	
	after (Account ac, float amount) : logging(ac, amount) {
		System.out.println("[TRANSAÇÃO FINALIZADA] Saldo restante na conta: " 
			+ ac.getBalance());
		System.out.println("\n_______________________________________");
	}
}