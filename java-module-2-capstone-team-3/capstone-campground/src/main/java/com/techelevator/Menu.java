package com.techelevator;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.Scanner;

public class Menu {

	private PrintWriter out;
	private Scanner in;

	public Menu(InputStream input, OutputStream output) {
		this.out = new PrintWriter(output);
		this.in = new Scanner(input);
	}

	public Object getChoiceFromOptions(Object[] options) {
		Object choice = null;
		while (choice == null) {
			displayMenuOptions(options);
			choice = getChoiceFromUserInput(options);
		}
		return choice;
	}
	
	public String getUserStringInput(String prompt) {
		String response = "";
		while (response.isEmpty()) {
			out.print(prompt + "  ");
			out.flush();
			response = in.nextLine();
		}
		return response;
	}
	

	public int getIntChoiceFromOptions(Object[] options) {
		int choice = -1;
		while (choice == -1) {
			displayMenuOptions(options);
			choice = getIntChoiceFromUserInput(options.length);
		}
		return choice;
	}
	
	//Get an integer choice between minChoice and maxChoice inclusive
	public int getIntChoiceFromPrompt(String prompt, int minChoice, int maxChoice) {
		int choice = maxChoice + 1;
		while (choice < minChoice || choice > maxChoice){
			out.print(prompt + "  ");
			out.flush();
			choice = getIntChoiceFromUserInputWithBounds(minChoice, maxChoice);
		}
		return choice;
	}
	
	public LocalDate getFutureDateFromPrompt(String prompt) {
		LocalDate choice = null;
		while(choice == null) {
			out.print(prompt + "  ");
			out.flush();
			String userInput = in.nextLine();
			String[] dateArray = userInput.split("/");
			try {
				int month = Integer.parseInt(dateArray[0]);
				int day = Integer.parseInt(dateArray[1]);
				int year = Integer.parseInt(dateArray[2]);
				choice = LocalDate.of(year, month, day);
			} catch (NumberFormatException e) {
			} catch (DateTimeException e) {
			} catch (ArrayIndexOutOfBoundsException e) {
			}
			if (choice == null) {
				out.println("\n*** " + userInput + " is not a valid date ***");
				out.println("Use format mm/dd/yyyy.\n");
				out.flush();
			}
			else if (choice.compareTo(LocalDate.now()) < 0) {
				out.println("\n*** Booking date has already passed ***\n");
				out.flush();
			}
		}
		return choice;
	}
	

	private Object getChoiceFromUserInput(Object[] options) {
		Object choice = null;
		String userInput = in.nextLine();
		try {
			int selectedOption = Integer.valueOf(userInput);
			if (selectedOption > 0 && selectedOption <= options.length) {
				choice = options[selectedOption - 1];
			}
		} catch (NumberFormatException e) {
			// eat the exception, an error message will be displayed below since choice will
			// be null
		}
		if (choice == null) {
			out.println("\n*** " + userInput + " is not a valid option ***\n");
			out.flush();
		}
		return choice;
	}

	private void displayMenuOptions(Object[] options) {
		out.println();
		for (int i = 0; i < options.length; i++) {
			int optionNum = i + 1;
			out.println(optionNum + ") " + options[i]);
			out.flush();
		}
		out.print("\nPlease choose an option >>> ");
		out.flush();
	}

	private int getIntChoiceFromUserInput(int max) {
		int choice = -1;
		String userInput = in.nextLine();
		try {
			int selectedOption = Integer.valueOf(userInput);
			if (selectedOption > 0 && selectedOption <= max) {
				choice = selectedOption -1;
			}
		} catch (NumberFormatException e) {
			// eat the exception, an error message will be displayed below since choice will
			// be -1
		}
		if (choice == -1) {
			out.println("\n*** " + userInput + " is not a valid option ***\n");
			out.flush();
		}
		return choice;
	}
	
	private int getIntChoiceFromUserInputWithBounds(int min, int max) {
		int choice = min - 1;
		boolean badChoice = true;
		String userInput = in.nextLine();
		try {
			int selectedOption = Integer.valueOf(userInput);
			if (selectedOption >= min && selectedOption <= max) {
				choice = selectedOption;
				badChoice = false;
			}
		} catch (NumberFormatException e) {
		}
		if (badChoice ) {
			out.println("\n*** " + userInput + " is not a valid option ***\n");
			out.flush();
		}
		return choice;
	}

}