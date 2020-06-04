package lab8;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lab7.lab7.Point;

public class lab8 {
	
	private static long beforeTransfers;
	private static long afterTransfers;

	public static void main(String[] args) {
		Bank bank=new Bank();
		beforeTransfers=bank.getAllMoneys();
		System.out.print("Moneys before random transfers: " + beforeTransfers);
		bank.startRandomTransfers();
		afterTransfers=bank.getAllMoneys();
		System.out.print("\nMoneys after random transfers:" +afterTransfers);
	}
	
	
	private static class Account{
		private long moneys;
		
		Account(long moneys){
			this.moneys=moneys;
		}
		
		long getMoneys() {
			return moneys;
		}
		
		void setMoneys(long moneys) {
			this.moneys=moneys;
		}
	}
	
	private static class Bank{
		private List<Account> accounts;
		
		Bank(){
			initAccounts();
		}
		
		void initAccounts(){
	    	Random r =new Random();
	    	Supplier<Account> supplier = () -> {
	    		int moneys=r.nextInt();
	    		return new Account(moneys<0?0:moneys);
	    	};
	    	accounts=Stream.generate(supplier).limit(200).collect(Collectors.toList());
		}
		
		private void startRandomTransfers() {
			ExecutorService service = Executors.newFixedThreadPool(2000);
			for(int i=0;i<10000;i++) {
				service.submit(()->{
					Random random =new Random();
					Account from=accounts.get(random.nextInt(accounts.size()));
					Account to=accounts.get(random.nextInt(accounts.size()));
					int amount=random.nextInt();
					transfer(from,to,amount );
				});
			}
			service.shutdown();
		}
		
		private synchronized void transfer(Account from, Account to, int amount) {
			synchronized(from) {
				synchronized(to) {
					long tempAmount=amount;
					if(amount>from.getMoneys()) {
						tempAmount=from.getMoneys();
					}
					from.setMoneys(from.getMoneys()-tempAmount);
					to.setMoneys(to.getMoneys()+tempAmount);
				}
			}
		}
		
		private long getAllMoneys() {
			long allMoneys=0;
			for(int i=0;i<accounts.size();i++){
				allMoneys+=accounts.get(i).getMoneys();
			}
			return allMoneys;
		}
	}
}
