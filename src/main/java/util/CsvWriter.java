package util;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CsvWriter {

	public static void write(List<String[]> data, String fileName) throws IOException {

		FileWriter writer = new FileWriter(fileName);

		for (String[] row : data) {

			String newRow = "";
			for (String col : row) {
				if (!newRow.isEmpty()) {
					newRow = newRow + ",";
				}
				newRow = newRow + col;
			}
			writer.write(newRow + "\n");			
		}
		writer.close();
	}
}
