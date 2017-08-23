package generic_binary_search_tree;

import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Stream;

public class Main {

	public static void main(String[] args) {
		BinarySearchTree<Integer> bst = new BinarySearchTree<>();
		Random r = new Random();

		System.out.println("Kolejnosc dodawania");
		while (bst.size() < 10) {
			int number = r.nextInt(100);
			bst.add(number);
			System.out.print(number + " ");
		}

		System.out.println("\nReverseInOrder");
		Iterator<Integer> it = bst.reverseIterator();
		while (it.hasNext())
			System.out.print(it.next() + " ");

		System.out.println("\nInOrder");
		for (Integer i : bst)
			System.out.print(i + " ");

		Scanner sc = new Scanner(System.in);

		System.out.println();
		Stream.generate(() -> "-").limit(30).forEach(System.out::print);
		System.out.println();

		System.out.println("Podaj liczbe do usuniecia");
		int value = sc.nextInt();
		System.out.println(bst.remove(value) ? "Usunieto " + value : "Nie znaleziono " + value);
		System.out.println("Drzewo:" + bst);
		
		sc.close();
	}
}
