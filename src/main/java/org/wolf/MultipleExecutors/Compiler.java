package org.wolf.MultipleExecutors;

import org.wolf.MultipleExecutors.commands.CommandException;
import org.wolf.MultipleExecutors.commands.Commands;

import java.util.ArrayList;
import java.util.HashMap;

public class Compiler
{
	private static final char SEPARATOR = ';';

	private static final int PARSE = 0;
	private static final int FIND = 1;
	private int state = PARSE;

	private boolean isParse = true;
	private int countCommand = 1;

	private StringBuilder currentWord = new StringBuilder();
	private String algorithmText;
	private String originAlgorithmText;
	private int currentPositionText = 0;
	private Commands current = null;

	private ArrayList<String> allowTitleCommand = new ArrayList<>();
	private ArrayList<Commands> allowCommand = new ArrayList<>();
	private ArrayList<String> controlTitleCommand = new ArrayList<>();
	private ArrayList<Commands> controlCommand = new ArrayList<>();

	/**
	 * @param text
	 */
	public Compiler(String text)
	{
		originAlgorithmText = text;
		algorithmText = text.toLowerCase();

		for (Commands c : Commands.values()) {
			if (c.isControl) {
				controlTitleCommand.add(c.userTitle);
				controlCommand.add(c);
			} else {
				allowTitleCommand.add(c.userTitle);
				allowCommand.add(c);
			}
		}
	}

	/**
	 * return array like this:
	 * [
	 * [command, condition, end index, if control command]
	 * -----
	 * [command, condition, end index]
	 * ]
	 *
	 * @return HashMap
	 * @throws CommandException
	 */
	public HashMap<Integer, String[]> prepare() throws CommandException
	{
		HashMap<Integer, String[]> algorithm = new HashMap<>();

		while (isNext()) {
			getNextCommand();
			algorithm.put(countCommand++, new String[]{this.current.toString()});
		}

		return algorithm;
	}

	/**
	 * Find next word command and switch state
	 */
	private void getNextCommand() throws CommandException
	{
		currentWord.delete(0, currentWord.length());
		while (true) {
			currentWord.append(algorithmText.charAt(currentPositionText));
			if (allowTitleCommand.contains(currentWord.toString())) {
				current = allowCommand.get(allowTitleCommand.indexOf(currentWord.toString()));
				currentPositionText++;
				break;
			} else if (Character.isWhitespace(algorithmText.charAt(currentPositionText))) {
				if (currentWord.length() == 1) {
					currentWord.delete(0, currentWord.length());
				} else {
					throw new CommandException("Не найдена команда: '" + currentWord + "'");
				}
			}
			currentPositionText++;
		}
	}

	private void parseCondition()
	{

	}

	/**
	 * Check if algorithm text is end
	 *
	 * @return boolean
	 */
	private boolean isNext()
	{
		return isParse && algorithmText.length() != currentPositionText;
	}

}
