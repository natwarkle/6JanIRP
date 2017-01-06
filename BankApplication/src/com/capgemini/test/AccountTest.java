package com.capgemini.test;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.capgemini.exceptions.DuplicateAccountNumberException;
import com.capgemini.exceptions.InsufficientBalanceException;
import com.capgemini.exceptions.InsufficientInitialAmountException;
import com.capgemini.exceptions.InvalidAccountNumberException;
import com.capgemini.exceptions.InvalidTransferAmountException;
import com.capgemini.model.Account;
import com.capgemini.repository.AccountRepository;
import com.capgemini.service.AccountService;
import com.capgemini.service.AccountServiceImpl;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
public class AccountTest {

	AccountService accountService;
	
	@Mock
	AccountRepository accountRepository;
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		
		accountService = new AccountServiceImpl(accountRepository);
	}

	/*
	 * create account
	 * 1.when the amount is less than 500 then system should throw exception
	 * 2.when the valid info is passed account should be created successfully
	 */
	
	@Test(expected=com.capgemini.exceptions.InsufficientInitialAmountException.class)
	public void whenTheAmountIsLessThan500SystemShouldThrowException() throws InsufficientInitialAmountException, DuplicateAccountNumberException, InvalidAccountNumberException
	{
		accountService.createAccount(101, 400);
	}
	
	@Test
	public void whenTheValidInfoIsPassedAccountShouldBeCreatedSuccessfully() throws InsufficientInitialAmountException, DuplicateAccountNumberException, InvalidAccountNumberException
	{
		Account account =new Account();
		account.setAccountNumber(101);
		account.setAmount(5000);
		when(accountRepository.save(account)).thenReturn(true);
		assertEquals(account, accountService.createAccount(101, 5000));
	}
	
	
	@Test
	public void findBalanceTestCase() throws InsufficientInitialAmountException, DuplicateAccountNumberException, InvalidAccountNumberException
	{
		Account account =new Account();
		account.setAccountNumber(102);
		account.setAmount(5000);
		when(accountRepository.save(account)).thenReturn(true);
		assertEquals(account, accountService.createAccount(102, 5000));
		assertEquals(5000, accountService.findBalance(102));
	}
	//4/1/2017 all Exception Class Test Cases
	@Test(expected=com.capgemini.exceptions.DuplicateAccountNumberException.class)
	public void duplicateAmountNumberTestCase() throws InsufficientInitialAmountException, DuplicateAccountNumberException, InvalidAccountNumberException
	{
		//accountService.createAccount(101, 400);
		Account account =new Account();
		account.setAccountNumber(103);
		account.setAmount(5000);
		when(accountRepository.save(account)).thenReturn(true);
		assertEquals(account, accountService.createAccount(103, 5000));
		
		Account account1 =new Account();
		account.setAccountNumber(103);
		account.setAmount(5000);
		when(accountRepository.save(account1)).thenReturn(true);
		assertEquals(account1, accountService.createAccount(103, 5000));
	}
	@Test(expected=com.capgemini.exceptions.InvalidAccountNumberException.class)
	public void invalidAccountNumberTestCase() throws InsufficientInitialAmountException, DuplicateAccountNumberException, InvalidAccountNumberException
	{
		accountService.createAccount(-104, 400);
	}
	@Test(expected=com.capgemini.exceptions.InvalidTransferAmountException.class)
	public void invalidTransferAmountTestCase() throws InsufficientInitialAmountException, DuplicateAccountNumberException, InvalidAccountNumberException, InvalidTransferAmountException, InsufficientBalanceException
	{
		Account account =new Account();
		account.setAccountNumber(105);
		account.setAmount(1000);
		when(accountRepository.save(account)).thenReturn(true);
		//assertEquals(account, accountService.createAccount(105,1000));
		
		Account account1 =new Account();
		account.setAccountNumber(106);
		account.setAmount(7000);
		when(accountRepository.save(account1)).thenReturn(true);
		//assertEquals(account1, accountService.createAccount(106,7000));
		/*accountService.createAccount(105, 4000);
		accountService.createAccount(106, 4000);*/
		accountService.fundTransfer(0,105,106);
		
	}
	@Test(expected=com.capgemini.exceptions.InsufficientBalanceException.class)
	public void insufficientBalanceExceptionTestCase() throws InsufficientInitialAmountException, DuplicateAccountNumberException, InsufficientBalanceException, InvalidAccountNumberException
	{
		Account account =new Account();
		account.setAccountNumber(107);
		account.setAmount(1000);
		when(accountRepository.save(account)).thenReturn(true);
		assertEquals(account, accountService.createAccount(107, 1000));
		accountService.withdrawAmount(2000, 107);
	}
	//all Service class Test Methods..
	@Test
	public void depositAmountTestCase() throws InvalidAccountNumberException, InsufficientInitialAmountException, DuplicateAccountNumberException{
		Account account =new Account();
		account.setAccountNumber(108);
		account.setAmount(1000);
		when(accountRepository.save(account)).thenReturn(true);
		assertEquals(account, accountService.createAccount(108, 1000));
		assertEquals(200,accountService.deposit(200, 108));
	}
	@Test
	public void withDrawAmountTestCase() throws InsufficientInitialAmountException, DuplicateAccountNumberException, InsufficientBalanceException, InvalidAccountNumberException{
		Account account =new Account();
		account.setAccountNumber(109);
		account.setAmount(1000);
		when(accountRepository.save(account)).thenReturn(true);
		assertEquals(account, accountService.createAccount(109, 1000));
		assertEquals(200,accountService.withdrawAmount(200, 109));
	}
	@Test
	public void fundTransferTestCase() throws InsufficientInitialAmountException, DuplicateAccountNumberException, InvalidAccountNumberException, InvalidTransferAmountException, InsufficientBalanceException{
		Account account =new Account();
		account.setAccountNumber(123);
		account.setAmount(1000);
		when(accountRepository.save(account)).thenReturn(true);
		when(accountRepository.searchAccount(123)).thenReturn(account);
		//assertEquals(account, accountService.createAccount(123,1000));
		
		
		Account account1 =new Account();
		account.setAccountNumber(124);
		account.setAmount(7000);
		when(accountRepository.save(account1)).thenReturn(true);
	    when(accountRepository.searchAccount(124)).thenReturn(account1);
		System.out.println("inside fd1");System.out.println();System.out.println();
		//assertEquals(account1, accountService.createAccount(124,7000));
		assertEquals(true, accountService.fundTransfer(100,123,124));
		System.out.println("inside fd2");
		
	}
	
	
}
