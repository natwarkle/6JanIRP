package com.capgemini.service;

import com.capgemini.exceptions.DuplicateAccountNumberException;
import com.capgemini.exceptions.InsufficientBalanceException;
import com.capgemini.exceptions.InsufficientInitialAmountException;
import com.capgemini.exceptions.InvalidAccountNumberException;
import com.capgemini.exceptions.InvalidTransferAmountException;
import com.capgemini.model.Account;

public interface AccountService {

		Account createAccount(int accountNumber, int amount) throws InsufficientInitialAmountException, DuplicateAccountNumberException, InvalidAccountNumberException;
    	//added by natwar kumar
		public int findBalance(int accountNumber) throws InvalidAccountNumberException;
    	public int deposit(int amount,int accountNumber) throws InvalidAccountNumberException;
    	public int withdrawAmount(int amount,int accountNumber) throws InsufficientBalanceException, InvalidAccountNumberException;
    	public boolean fundTransfer(int amount,int fromAccountNumber,int toAccountNumber)throws InvalidAccountNumberException, InvalidTransferAmountException, InsufficientBalanceException;
}