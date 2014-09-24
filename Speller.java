import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class Speller {
	private final int maxrows = 500000;
	private List<Row> rows = new ArrayList<Row>();
	private int maxlength = 0;

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
			maxlength = Math.max(word.length(), maxlength);
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

	public SpellingResult spell(String word) {
		Distancer distancer = new Distancer(maxlength);
		SpellingResult result = new SpellingResult();
		String previous = "";
		for(Row row : rows) {
			int distance = distancer.distance(row.matching, row.word, previous);
			if(distance > result.distance) {
				break;
			}
			if(distance < result.distance) {
				result.distance = distance;
				result.possibleSpellings.clear();
			}
			result.possibleSpellings.add(row.word);
		}
		return result;
	}

	private static class Row {
		int matching;
		String word;
		Row(int matching, String word) {
			this.matching = matching;
			this.word = word;
		}
	}

	public static class SpellingResult {
		public int distance = Integer.MAX_VALUE;
		public List<String> possibleSpellings = new ArrayList<String>();
	}

	public static void main(String[] args) throws IOException {
		BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
		Speller speller = new Speller();
		speller.initialize(stdin);
		for(int i = 0; i < 10; i++) {
			Row row = speller.rows.get(i);
			System.err.println(row.matching + " " + row.word);
		}
		String word;
    	while ((word = stdin.readLine()) != null) {
    		SpellingResult result = speller.spell(word);
    	}
	}

}