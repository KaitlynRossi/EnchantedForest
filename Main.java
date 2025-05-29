import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Main {

	public static void main(String[] args) {
		EnchantedForest fairyGarden = new EnchantedForest();
		try (Scanner scn = new Scanner(System.in)) {
			int userInput = -1;
			int power = 0;
			
			System.out.println("Welcome to the  Enchanted Forest! Here you can manage the magical trees and "
					+ "discover their powers.\n");
			
			while(userInput != 0) {
				System.out.println("**Menu:**\n");
				System.out.println("1. Plant a Tree");
				System.out.println("2. Wither a Tree");
				System.out.println("3. Find a Tree");
				System.out.println("4. Explore Trees By Power");
				System.out.println("5. Load Trees From File");
				System.out.println("0. Exit\n");
				System.out.println("Please Enter Your Choice:");
				
				userInput = scn.nextInt();
				
				switch(userInput) {
					case 1: 
						System.out.println("Enter a tree power and name:");
						power = scn.nextInt();
						String name = scn.nextLine();
						name = name.trim();
						fairyGarden.plantTree(power, name);
						if(power > 0) {
							System.out.println("Tree " + name + " with power " + power + " planted.\n");
						}else {
							System.out.println("Tree " + name + " with power " + power + " cannot be planted.\nNegative powers are not allowed.");
						}
						break;
						
					case 2: 
						System.out.println("Enter a power to wither:");
						power = scn.nextInt();
						if(fairyGarden.witherTree(power)) {
							System.out.println("Tree with power " + power + " removed." );
						}else {
							System.out.println("Tree with power " + power + " not found");
						}
						break;
						
					case 3: 
						System.out.println("Enter a power to search by:");
						power = scn.nextInt();
						EnchantedForest.TreeNode tree = fairyGarden.findTree(power);
						if(tree == null) {
							System.out.println("No tree with that power exists");
						}else {
							System.out.println(tree.getName() + " " + tree.getPower());
						}
						
						break;
						
					case 4: 
						System.out.println("Enter a range query (lower bound and upper bound):");
						int low = scn.nextInt();
						int high = scn.nextInt();
						List<EnchantedForest.TreeNode> list = new ArrayList<>();
						list = fairyGarden.getTreesInRange(low, high);
						for(EnchantedForest.TreeNode node : list) {
							System.out.println(node.getName() + " " + node.getPower());
						}
						break;
						
					case 5: 
						scn.nextLine();
						System.out.println("Enter file name: ");
						String fileName = scn.nextLine();
						List<String> treeList = readText(fileName);
						for(int i = 0; i < treeList.size(); i++) {
							 String data = treeList.get(i).toString();
							 String[] info = data.split(" ");
							fairyGarden.plantTree(Integer.parseInt(info[0]), info[1]);
						}
						System.out.println("\nLoading trees from " + fileName + "...\n");
						break;
						
				}
				
			}
			System.out.println("Goodbye");
			scn.close();
		}
	}
	
	public static List<String> readText(String fileName) {
		List<String> list = new ArrayList<>();
		File myFile = new File(fileName);
		try {
			Scanner scn = new Scanner(myFile);
			
			while(scn.hasNextLine()) {
				String data = scn.nextLine();
				list.add(data);
			}
			scn.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return list;
	}
}
