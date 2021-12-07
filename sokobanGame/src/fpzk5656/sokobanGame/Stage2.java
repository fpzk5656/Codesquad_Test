package fpzk5656.sokobanGame;

import java.util.Scanner;

public class Stage2 {

	// 처음 맵 정보 입력하고,
	// 맵 상태 저장할 변수, 맵 출력 함수,
	// 플레이어 움직이는 함수로 맵 상태 갱신 (예외처리 할 것들도 필요)
	public static int[][] movePlayer(int[][] mapTale, char key) {
		int[] columnGroup = getColumnGroupOfMap(mapTale);
		int row = getRowOfMap(mapTale);
		int playerColumn = 0;
		int playerRow = 0;

		// 플레이어 위치 구하기
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < columnGroup[i]; j++) {
				if (mapTale[j][i] == 3) {
					playerColumn = j;
					playerRow = i;
				}
			}
		}
		// 들어온 키워드에 따라 플레이어 스왑해주기
		// 먼저 스왑할 위치 정하기
		int aimColumn = playerColumn;
		int aimRow = playerRow;
		switch (key) {
		case 'w':
			aimRow--;
			break;
		case 's':
			aimRow++;
			break;
		case 'a':
			aimColumn--;
			break;
		case 'd':
			aimColumn++;
			break;
		}
		// 스왑
		if (mapTale[aimColumn][aimRow] == 7) {
			int[][] tmp = new int[1][1];
			tmp[0][0] = mapTale[aimColumn][aimRow];
			mapTale[aimColumn][aimRow] = mapTale[playerColumn][playerRow];
			mapTale[playerColumn][playerRow] = tmp[0][0];
		} else {
			System.out.println(Character.toUpperCase(key) + ": (!경고) 해당 명령을 수행할 수 없습니다!");
		}

		return mapTale;
	}

	public static void inputKey(Scanner scanner, int[][] mapTale) {
		boolean gameOver = false;
		while (true) {
			System.out.print("SOKOBAN> ");
			String input = scanner.nextLine();
			System.out.println(input);
			for (int i = 0; i < input.length(); i++) {
				char key = input.charAt(i);

				switch (key) {
				case 'w':
					mapTale = movePlayer(mapTale, key);
					resultMap(mapTale);
					System.out.println(Character.toUpperCase(key) + ": 위로 이동합니다.");
					break;
				case 's':
					mapTale = movePlayer(mapTale, key);
					resultMap(mapTale);
					System.out.println(Character.toUpperCase(key) + ": 아래로 이동합니다.");
					break;
				case 'a':
					mapTale = movePlayer(mapTale, key);
					resultMap(mapTale);
					System.out.println(Character.toUpperCase(key) + ": 왼쪽으로 이동합니다.");
					break;
				case 'd':
					mapTale = movePlayer(mapTale, key);
					resultMap(mapTale);
					System.out.println(Character.toUpperCase(key) + ": 오른쪽으로 이동합니다.");
					break;
				case 'q':
					System.out.println("게임을 종료 합니다.");
					i = input.length();// 탈출
					gameOver = true;
					break;
				default:
					System.out.println(Character.toUpperCase(key) + " (!경고) 해당 명령을 수행할 수 없습니다!");
					System.out.print("\n");
					break;
				}
				//resultMap(mapTale);
			}
			if (gameOver) {
				break;
			}
		}
	}

	public static int[][] initMap(String input, int row, int column) {
		int[][] tale = new int[999][999];
		if (!input.contains("=")) {
			System.out.println(input);
			if (input.contains("Stage")) {
				System.out.printf("\n");
			}
		}
		if (input.indexOf("#") > -1) {
			String[] lines = input.split("\n");

			// 플레이어 위치 구하기
			for (int i = 0; i < lines.length; i++) {
				for (int j = 0; j < lines[i].length(); j++) {
					char n = lines[i].charAt(j);
					switch (n) {
					case '#':
						tale[j][i] = 8;
						break;
					case 'O':
						tale[j][i] = 1;
						break;
					case 'o':
						tale[j][i] = 2;
						break;
					case 'P':
						tale[j][i] = 3;
						break;
					case '=':
						tale[j][i] = 4;
						break;
					case ' ':
						tale[j][i] = 7;
						break;
					}
				}
			}
		}
		return tale;
	}

	public static int getRowOfMap(int[][] tale) {
		int result = tale.length;
		return result;
	}

	public static int[] getColumnGroupOfMap(int[][] tale) {
		int[] result = new int[tale.length];
		for (int i = 0; i < tale.length; i++) {
			result[i] = tale[i].length;// i 번째 행의 원소 개수
		}

		return result;
	}

	// 맵 결과 표시
	public static void resultMap(int[][] mapTale) {
		int[] columnGroup = getColumnGroupOfMap(mapTale);
		int row = getRowOfMap(mapTale);
		boolean inGame = false;

		String line = "";
		for (int i = 0; i < row; i++) {
			line = "";
			for (int j = 0; j < columnGroup[i]; j++) {
				switch (mapTale[j][i]) {
				case 8:
					line += '#'; // 벽
					inGame = true;
					break;
				case 1:
					line += 'O'; // 구멍
					break;
				case 2:
					line += 'o'; // 공
					break;
				case 3:
					line += 'P'; // 플레이어
					break;
				case 4:
					line += '='; // 구분선
					break;
				case 7:
					line += ' '; // 빈공간
					break;
				}
			}
			if (line == "" && inGame == true)
			{
				break;
			}
			System.out.println(line);
		}
	}
	
	public static void calculate(String input)
	{
		String[] lines = input.split("\n");

		int count = 0;
		for (int i = 0; i < lines.length; i++) {
			if (count < lines[i].length()) {
				count = lines[i].length();
			}
		}
		int[][] mapTale = new int[lines.length][count];

		mapTale = initMap(input, lines.length, count);

		Scanner scanner = new Scanner(System.in);
		inputKey(scanner, mapTale);
	}

	public static void main(String[] args) {

		String initStage2 = "Stage 2\n" + "\n" + "  #######\n" + "###  O  ###\n" + "#    o    #\n" + "# Oo P oO #\n"
				+ "###  o  ###\n" + " #   O  # \n" + " ########";
		String[] lines = initStage2.split("\n");

		calculate(initStage2);
	}
}
