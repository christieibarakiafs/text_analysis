package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CsvReader {

	public static String getDesktopPath() {

		return System.getProperty("user.home") + "\\Desktop\\";

	}

	private static String removeQuotes(String input) {

		try {
			input = input.trim();
			String firstChar = Character.toString(input.charAt(0));
			String secondChar = Character.toString(input.charAt(input.length() - 1));

			if (firstChar.equals("\"") && secondChar.equals("\"")) {
				return input.substring(1, input.length() - 1);
			}

		} catch (Exception e) {

		}

		return input;

	}

	private static String[] cleanRow(String[] row) {

		String[] result = new String[row.length];

		for (int i = 0; i < row.length; i++) {
			result[i] = removeQuotes(row[i]);
		}

		return result;

	}

	public static List<String[]> read(File file) {

		List<String[]> result = new ArrayList<String[]>();
		BufferedReader br = null;
		String line = "";

		try {

			br = new BufferedReader(new FileReader(file.getAbsolutePath()));

			while ((line = br.readLine()) != null) {

				String[] row = cleanRow(line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)"));
				result.add(row);

			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {

			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}

		return result;

	}

}
