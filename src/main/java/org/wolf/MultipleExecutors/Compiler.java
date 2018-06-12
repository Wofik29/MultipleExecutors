package org.wolf.MultipleExecutors;

import org.wolf.MultipleExecutors.commands.CommandException;
import org.wolf.MultipleExecutors.commands.Commands;

import java.util.*;

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
	private ArrayList<String> controlTitleCommand = new ArrayList<>();
	private ArrayList<String> directionTitleCommand = new ArrayList<>();
	private ArrayList<String> allowTitleCell = new ArrayList<>();

	private ArrayList<Commands> allowCommand = new ArrayList<>();
	private ArrayList<Commands> controlCommand = new ArrayList<>();
	private ArrayList<Commands> directionCommand = new ArrayList<>();
	private ArrayList<Cell> allowCell = new ArrayList<>();

	private Stack<String[]> controlStack = new Stack<>();

	/**
	 * @param text
	 */
	public Compiler(String text)
	{
		originAlgorithmText = text;
		algorithmText = text.toLowerCase().replace('\n', ' ');

		for (Commands c : Commands.values()) {
			if (c.isDirection) {
				directionTitleCommand.add(c.userTitle);
				directionCommand.add(c);
			} else {
				if (c.isControl) {
					controlTitleCommand.add(c.userTitle);
					controlCommand.add(c);
				} else {
					allowTitleCommand.add(c.userTitle);
					allowCommand.add(c);
				}
			}
		}

		for (Cell c: Cell.values()) {
			if (!allowTitleCell.contains(c.title)) {
				allowTitleCell.add(c.title);
				allowCell.add(c);
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
			if (current.isControl) {
				if (current == Commands.End) {
					if (controlStack.isEmpty()) {
						throw new CommandException("Не найдено начало End");
					}
					String[] control = controlStack.pop();
					algorithm.put(countCommand++, new String[]{current.toString(), control[3]});
				} else if (current == Commands.Else) {
					if (controlStack.isEmpty()) {
						throw new CommandException("Найдено вторая ветка ветвления("+Commands.Else.userTitle+"), но не найдено начало ветвления("+Commands.If.userTitle+")");
					}
					String[] control = controlStack.pop();
					algorithm.put(countCommand++, new String[]{current.toString(), control[3]});
					controlStack.push(control);
				} else {
					String[] condition = parseCondition();
					String conditionStr = condition[0] + ',' + condition[1] + ',' + condition[2];
					controlStack.push(new String[]{condition[0], condition[1], condition[2], Integer.toString(countCommand)});
					algorithm.put(countCommand++, new String[]{current.toString(), conditionStr, Integer.toString(countCommand)});
				}
			} else {
				algorithm.put(countCommand++, new String[]{this.current.toString()});
			}
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
			} else if (controlTitleCommand.contains(currentWord.toString())) {
				current = controlCommand.get(controlTitleCommand.indexOf(currentWord.toString()));
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

	/**
	 *
	 * @return array like this [leftOperat, rightOperat, symbolBetween]
	 * @throws CommandException
	 */
	private String[] parseCondition() throws CommandException
	{
		boolean isExistLeftBracket = false;
		boolean isExistRightBracket = false;
		boolean isWhile = true;

		String[] condition = new String[3];
		int index = 0;
		currentWord.delete(0, currentWord.length());
		while (isWhile) {
			char nextChar = algorithmText.charAt(currentPositionText);
			currentWord.append(nextChar);
			if (!isExistLeftBracket) {
				if (nextChar == '(') {
					isExistLeftBracket = true;
					currentWord.delete(0, currentWord.length());
					currentPositionText++;
					continue;
				}
			} else if (!isExistRightBracket) {
				if (allowTitleCell.contains(currentWord.toString())) {
					condition[index] = allowCell.get(allowTitleCell.indexOf(currentWord.toString())).toString();
					index++;
					currentWord.delete(0, currentWord.length());
				} else if (directionTitleCommand.contains(currentWord.toString())) {
					condition[index] = directionCommand.get(directionTitleCommand.indexOf(currentWord.toString())).toString();
					index++;
					currentWord.delete(0, currentWord.length());
				}

				if (nextChar == '=') {
					condition[index++] = "=";
					currentWord.delete(0, currentWord.length());
				}

				if  (currentWord.indexOf("!=") > -1) {
					condition[index++] = "!=";
					currentWord.delete(0, currentWord.length());
				}

				if (nextChar == ')') {
					isExistRightBracket = true;
					currentWord.delete(0, currentWord.length());
					if (index != 3) {
						throw new CommandException("Описано не все условие");
					}
				}
			}
			if (Character.isWhitespace(nextChar)) {
				if (currentWord.length() == 1) {
					currentWord.delete(0, currentWord.length());
				} else {
					throw new CommandException("Не найдена команда: '" + currentWord + "'");
				}
			}
			currentPositionText++;

			isWhile = (algorithmText.length() > currentPositionText);
			if (isExistLeftBracket && isExistRightBracket && index == 3) {
				isWhile = false;
			}
		}
		return condition;
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
