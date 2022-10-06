package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.stream.Collectors;

import model.entities.Sale;

public class Program {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		Locale.setDefault(Locale.US);
		
		List<Sale> list = new ArrayList<>();
		
		System.out.print("Entre o caminho do arquivo: ");
		String path = "c:\\temp\\in.csv";
		
		try (BufferedReader br = new BufferedReader(new FileReader(path))){
			
			String line = br.readLine();
			while (line != null) {
				
				String[] fields = line.split(",");
				int month = Integer.parseInt(fields[0]);
				int year = Integer.parseInt(fields[1]);
				String seller = fields[2];
				int items = Integer.parseInt(fields[3]);
				Double total = Double.parseDouble(fields[4]);
				
				list.add(new Sale(month, year, seller, items, total));
				
				line = br.readLine();
			}
			
			System.out.println("Cinco primeiras vendas de 2016 de maior preço médio");
			System.out.println();
			
			Comparator<Sale> comp = (s1, s2) -> s1.avaregePrice().compareTo(s2.avaregePrice());
			int inYear = 2016;
			
			List<Sale> sales = list.stream()
					.filter(x -> x.getYear() == inYear)
					.sorted(comp.reversed())
					.limit(5)
					.collect(Collectors.toList());

			sales.forEach(System.out::println);
			System.out.println();
					
			String sellerIn = "Logan";
			int firstMonth = 1;
			int secondMonth = 7;
			
			List<Sale> solds = list.stream()
					.filter(x -> x.getSeller().equals(sellerIn))
					.collect(Collectors.toList());
			
			double firstSold = solds.stream()
					.filter(x -> x.getMonth() == firstMonth)
					.map(x -> x.getTotal())
					.reduce(0.0, (x, y) -> x + y);
			
			double secondSold = solds.stream()
					.filter(x -> x.getMonth() == secondMonth)
					.map(x -> x.getTotal())
					.reduce(0.0, (x, y) -> x + y);
			
			double totalSold = firstSold + secondSold;
			
			System.out.println("Valor total vendido pelo vendedor Logan nos meses 1 e 7 = " + String.format("%.2f", totalSold));
					
		}catch(IOException e) {
			System.out.println("Error: " + e.getMessage());
			
		}
	}

}
