package com.capgemini.service;

import java.util.ArrayList;
import java.util.List;

import com.capgemini.exceptions.DuplicateAccountNumberException;
import com.capgemini.exceptions.InsufficientBalanceException;
import com.capgemini.exceptions.InsufficientInitialAmountException;
import com.capgemini.exceptions.InvalidAccountNumberException;
import com.capgemini.exceptions.InvalidTransferAmountException;
import com.capgemini.model.Account;
import com.capgemini.repository.AccountRepository;

public class AccountServiceImpl implements AccountService {
	
	/* (non-Javadoc)
	 * @see com.capgemini.service.AccountService#createAccount(int, int)
	 */
	
	AccountRepository accountRepository;
	List<Account> accountList;
	
	public AccountServiceImpl(AccountRepository accountRepository) {
		super();
		this.accountRepository = accountRepository;
		this.accountList=new ArrayList<Account>();
	}


	@Override
	public Account createAccount(int accountNumber,int amount) throws InsufficientInitialAmountException,DuplicateAccountNumberException, InvalidAccountNumberException
	{
		Account account1=null;
		if(accountNumber<0||accountNumber==0) {
			throw new InvalidAccountNumberException();
		}
		if(amount<500)
		{
			throw new InsufficientInitialAmountException();
		}
		for(int i=0;i<this.accountList.size();i++) {
			   Account account= this.accountList.get(i);   
			   if(accountNumber==account.getAccountNumber()) {
				   //System.out.println(account.getAmount());
				   throw new DuplicateAccountNumberException();
			   }
		   }
		 account1 = new Account();
		 account1.setAccountNumber(accountNumber);
		 account1.setAmount(amount);
		 
		if(accountRepository.save(account1)) {
			this.accountList.add(account1);
		 }
		System.out.println(" Size  create Account: "+this.accountList.size());
		for(int i=0;i<this.accountList.size();i++) {
			   Account account= this.accountList.get(i);   
			   System.out.println(" acc: "+account.getAccountNumber()+" amt : "+account.getAmount());
		}
		
		return account1;
		
	}


	@Override
	public int findBalance(int accountNumber)throws InvalidAccountNumberException {
		// TODO Auto-generated method stub
		int bal=0;
		boolean flag=false;
		for(int i=0;i<this.accountList.size();i++) {
			   Account account= this.accountList.get(i);   
			   if(accountNumber==account.getAccountNumber()) {
				   System.out.println(account.getAmount());
			       flag=true;
			       bal=account.getAmount();
			       break;
			   }
		}
		if(!flag) {
		   	
			throw new InvalidAccountNumberException();
		}
		return bal;
    }


	@Override
	public int deposit(int amount, int accountNumber)throws InvalidAccountNumberException {
		// TODO Auto-generated method stub
		int depAmt=0;
		boolean flag=false;
		for(int i=0;i<this.accountList.size();i++) {
			   Account account= this.accountList.get(i);   
			   if(accountNumber==account.getAccountNumber()) {
				   //System.out.println(account.getAmount());
			       int amt=account.getAmount();
			       amt+=amount;
			       account.setAmount(amt);
			       this.accountList.add(account);
			       flag=true;
			       break;
			   }
		   }
		if(!flag){
			throw new InvalidAccountNumberException();
		}
		depAmt=amount;
		return depAmt;
	}


	@Override
	public int withdrawAmount(int amount, int accountNumber)throws InsufficientBalanceException, InvalidAccountNumberException {
		// TODO Auto-generated method stub
		int returnAmt=0;
		boolean flag=false;
		for(int i=0;i<this.accountList.size();i++) {
			   Account account= this.accountList.get(i);   
			   if(accountNumber==account.getAccountNumber()) {
				   //System.out.println(account.getAmount());
			       int amt=account.getAmount();
			       amt-=amount;
			       if(amt<500) {
			    	   System.out.println("Enterd Amount is More Please Give Less Amount :wA ");
			    	   throw new InsufficientBalanceException();
			       }
			       account.setAmount(amt);
			       this.accountList.add(account);
			       flag=true;
			       break;
			   }
		   }
		if(!flag){
			throw new InvalidAccountNumberException();
		}
		else {
			returnAmt=amount;
		}
		return returnAmt;
	}
	
	
    public Account searchAccount(int accNum){
    	Account acc=null;
    	for(int i=0;i<this.accountList.size();i++) {
			   Account account= this.accountList.get(i);   
			   if(accNum==(account.getAccountNumber())) {
				   acc=account;
			  }
		   }
     return acc;
    }
    
    public int searchAccountandReturnAmount(int accNum){
    	int amt=0;
    	for(int i=0;i<this.accountList.size();i++) {
			   Account account= this.accountList.get(i);   
			   if(accNum==(account.getAccountNumber())) {
				   amt=account.getAmount();
			  }
		   }
     return amt;
    }
    
    
   
	@Override
	public boolean fundTransfer(int amount, int fromAccountNumber, int toAccountNumber)
			throws InvalidAccountNumberException,InvalidTransferAmountException, InsufficientBalanceException {
		// TODO Auto-generated method stub
		boolean fg=false;
		System.out.println(" inside fd as");
		if(amount<0||amount==0){
			throw new InvalidTransferAmountException();
		}
		if(fromAccountNumber==0||toAccountNumber==0){
			throw new InvalidAccountNumberException();
		}
		
		else if(fromAccountNumber==toAccountNumber) {
			this.deposit(amount, toAccountNumber);
		}
		System.out.println(" Size:fd  "+this.accountList.size());
		System.out.println( amount+" "+fromAccountNumber+" "+toAccountNumber);
		
		Account fromAccount=accountRepository.searchAccount(fromAccountNumber);
		Account toAccount=accountRepository.searchAccount(fromAccountNumber);
		if((fromAccount.getAmount()-amount)>=500)
		{
			fromAccount.setAmount(fromAccount.getAmount()-amount);
			toAccount.setAmount(toAccount.getAmount()+amount);
			
			return true;
			
		}
		return false;
		/*boolean acountFlag1 = false,accountFlag2=false;
		for(int i=0;i<this.accountList.size();i++) {
			   Account account= this.accountList.get(i);   
			   if(fromAccountNumber==account.getAccountNumber()) {
				   
				   acountFlag1=true;
				   break;
			   }
		   }
		
		for(int i=0;i<this.accountList.size();i++) {
			   Account account= this.accountList.get(i);   
			   if(toAccountNumber==account.getAccountNumber()) {
				   
				   accountFlag2=true;
				   break;
			   }
		   }
		
		if(!acountFlag1 || !accountFlag2) {
			throw new InvalidAccountNumberException();
		}
		System.out.println(" Size: "+this.accountList.size());
		for(int i=0;i< this.accountList.size();i++) {
			System.out.println(" fd "+ i+" "+ this.accountList.get(i).getAccountNumber());
		}
		
		Account a1=searchAccount(fromAccountNumber);
		Account a2=searchAccount(toAccountNumber);
		int amtFrom=0,amtDep=0;
		amtFrom=searchAccountandReturnAmount(fromAccountNumber);
		amtDep=searchAccountandReturnAmount(toAccountNumber);
			System.out.println(amtFrom+" "+amtDep);		    		  
					    		   amtFrom=amtFrom-amount;
					    		 
					    		   if(amtFrom<500) {
					    			   System.out.println("Enterd Amount is More Please Give Less Amount fd");
							    	   throw new InsufficientBalanceException();
					    		   }
					    		   else { 
					    		  amtDep+=amount;
					    		  a1.setAmount(amtFrom);
					    		  a2.setAmount(amtDep);
					    		  this.accountList.add(a1);
					    		  this.accountList.add(a2);
					    		System.out.println("Enterd Amount is More Please Give Less Amount fd"+ fg);			    		  
					    		     fg=true;
					    		  }
					    	
		return fg;*/
	}

}
