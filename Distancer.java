public class Distancer {
	private int[][] matrix;
	private String previous = "";

	public Distancer(int maxlength) {
		matrix = new int[maxlength + 1][maxlength + 1];
		for(int i = 0; i <= maxlength; i++) {
			matrix[i][0] = i;
			matrix[0][i] = i;
		}
	}

	public int distance(String a, String b) {
		int matching = 0;
		while (matching < a.length() && matching < previous.length() && a.charAt(matching) == previous.charAt(matching)) {
			matching++;
		}
		previous = a;
		for(int i = 1; i <= matching; i++) {
			for(int j = matching + 1; j <= b.length(); j++) {
				cell(a, b, i, j);
			}
		}
		for(int i = matching + 1; i <= a.length(); i++) {
			for(int j = 1; j <= b.length(); j++) {
				cell(a, b, i, j);
			}
		}
		return matrix[a.length()][b.length()];
	}

	private void cell(String a, String b, int i, int j) {
		if(a.charAt(i - 1) == b.charAt(j - 1)) {
			matrix[i][j] = matrix[i - 1][j - 1];
		} else {
			matrix[i][j] = 1 + Math.min(
					matrix[i - 1][j - 1],
					Math.min(
						matrix[i - 1][j],
						matrix[i][j - 1]
						)
					);
		}
	}
}
