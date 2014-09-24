import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class Speller {
	private final int maxrows = 500000;
	private List<Row> rows;

	public void initialize(BufferedReader input) throws IOException {
		readWords(input);
		countPrefix();
	}

	public void readWords(BufferedReader input) throws IOException {
		Row[] rows = new Row[maxrows];
		String word = input.readLine();
		int i = maxrows - 1;
		do {
			rows[i] = new Row(0, word);
			word = input.readLine();
			i--;
		} while(!word.equals("#"));
		this.rows = Arrays.asList(rows).subList(i + 1, maxrows);
	}

	public void countPrefix() {
		String previous = "";
		for(Row row : rows) {
			int j = 0;
			while(previous.length() > j && row.word.length() > j && previous.charAt(j) == row.word.charAt(j)) {
				j++;
			}
			row.matching = j;
			previous = row.word;
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
		Speller speller = new Speller();
		speller.initialize(stdin);
		for(int i = 0; i < 10; i++) {
			Row row = speller.rows.get(i);
			System.out.println(row.matching + " " + row.word);
		}
	}

	private static class Row {
		public int matching;
		public String word;
		public Row(int matching, String word) {
			this.matching = matching;
			this.word = word;
		}
	}
}