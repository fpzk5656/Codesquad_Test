package fpzk5656.sokobanGame;

import java.util.Scanner;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class stage3 {

	// 플레이어를 움직이고 맵 상태를 갱신함
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
			// 원래 플레이어가 이동하려는 위치에 장애물이 있을 경우 경고 표시를 출력하려 했으나, 맵출력할 대랑 순서에 맞지 않아서 제외
			// System.out.println(Character.toUpperCase(key) + ": (!경고) 해당 명령을 수행할 수
			// 없습니다!");
		}

		return mapTale;
	}

	// 두 2차원 배열을 비교해서 플레이어 위치가 같은 지 구하는 함수
	public static boolean playerPositionEquals(int[][] tale1, int[][] tale2) {
		boolean theSame = false;
		int[] columnGroup = getColumnGroupOfMap(tale1);
		int row = getRowOfMap(tale1);
		int playerColumn = 0;
		int playerRow = 0;

		// 플레이어 위치 구하기
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < columnGroup[i]; j++) {
				if (tale1[j][i] == 3) {
					playerColumn = j;
					playerRow = i;
				}
			}
		}
		// 플레이어 포지션 비교
		if (tale1[playerColumn][playerRow] == tale2[playerColumn][playerRow]) {
			int s = tale1[playerColumn][playerRow];
			int t = tale2[playerColumn][playerRow];
			int c = s + t;
			theSame = true;
		}
		return theSame;
	}
	
	public static int[][] deepCopy(int[][] mapTale)
	{
		int[][] tempTale = new int[999][999];
		for(int i = 0; i < mapTale.length; i++)
		{
			for(int j = 0; j < mapTale[i].length;j++)
			{
				tempTale[i][j] = mapTale[i][j];
			}
		}
		return tempTale;
	}

	// 방향키 입력받으면 처리하는 함수
	public static void inputKey(Scanner scanner, int[][] mapTale) throws IOException {
		boolean gameOver = false;
		// tempMap에 mapTale을 깊은복사
		int[][] tempMap = new int[999][999];
		tempMap = deepCopy(mapTale);
		
		// 입력이 반복 되도록(q키를 누르기 전까진)
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
					if (playerPositionEquals(mapTale, tempMap)) {
						System.out.println(Character.toUpperCase(key) + ": (!경고) 해당 명령을 수행할 수 없습니다!");
					} else {
						System.out.println(Character.toUpperCase(key) + ": 위로 이동합니다.");
					}
					break;
				case 's':
					mapTale = movePlayer(mapTale, key);
					resultMap(mapTale);
					if (playerPositionEquals(mapTale, tempMap)) {
						System.out.println(Character.toUpperCase(key) + ": (!경고) 해당 명령을 수행할 수 없습니다!");
					} else {
						System.out.println(Character.toUpperCase(key) + ": 아래로 이동합니다.");
					}
					break;
				case 'a':
					mapTale = movePlayer(mapTale, key);
					resultMap(mapTale);
					if (playerPositionEquals(mapTale, tempMap)) {
						System.out.println(Character.toUpperCase(key) + ": (!경고) 해당 명령을 수행할 수 없습니다!");
					} else {
						System.out.println(Character.toUpperCase(key) + ": 왼쪽으로 이동합니다.");
					}
					break;
				case 'd':
					mapTale = movePlayer(mapTale, key);
					resultMap(mapTale);
					if (playerPositionEquals(mapTale, tempMap)) {
						System.out.println(Character.toUpperCase(key) + ": (!경고) 해당 명령을 수행할 수 없습니다!");
					} else {
						System.out.println(Character.toUpperCase(key) + ": 오른쪽으로 이동합니다.");
					}
					break;
				case 'q':
					System.out.println("게임을 종료 합니다.");
					i = input.length();// 탈출
					gameOver = true;
					break;
				case 'r':
					// 스테이지를 리셋해야됨.
					// 리셋 함수 제작
					int stageNum = 1;
					resetGame(1);
					break;
				default:
					resultMap(mapTale);
					System.out.println(Character.toUpperCase(key) + ": (!경고) 해당 명령을 수행할 수 없습니다!");
					System.out.print("\n");
					break;
				}
				tempMap = deepCopy(mapTale);
			}
			if (gameOver) {
				break;
			}
		}
	}
	
	// 스테이지를 리셋하는 함수
	public static void resetGame(int stageNum)throws IOException
	{
		String mapInfo = readFile("C:\\Users\\admin\\Desktop\\CodesquadTest\\map.txt");
		calculate(mapInfo, stageNum);	//calculate(mapInfo, stageNum) 이렇게 만들어야 될 것 같다.
	}

	// 처음에 맵을 설치하는 함수
	public static int[][] initMap(String input) {
		int[][] tale = new int[999][999];
		// ===구분선이 없을 때만 출력하는 것..그래서 안나오던거였네 전체를 input에 넣어놨으니
		if (!input.contains("=")) {
			System.out.println(input);
			if (input.contains("Stage")) {
				System.out.printf("\n");	// Stage가 있을 경우 출력하고 한칸 띄우기

			}
		}
		// #이 있고나서부터 2차원 배열에 내용물 저장하기 시작
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
			if (line == "" && inGame == true) {
				break;
			}
			System.out.println(line);
		}
	}

	public static void calculate(String input, int stageNum) throws IOException {
		// 여기서 "Stage"+ stageNum을 기준으로 구분선까지 잘라내기
		String[] stageMap = input.split("=");
		
		
		//========
		String[] lines = stageMap[stageNum-1].split("\n");
		//String[] lines = input.split("\n");
		
		// 맵 정보를 잘라내서 행과 열의 개수를 파악하는 계산
		int count = 0;
		for (int i = 0; i < lines.length; i++) {
			if (count < lines[i].length()) {
				count = lines[i].length();
			}
		}
		// 맵 타일 제작 및 할당
		int[][] mapTale = new int[lines.length][count];
		mapTale = initMap(stageMap[stageNum-1]);
		//mapTale = initMap(input);

		Scanner scanner = new Scanner(System.in);
		inputKey(scanner, mapTale);
	}

	public static String readFile(String fileName) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(fileName));

		try {
			StringBuilder sb = new StringBuilder();
			String line = reader.readLine();

			while (line != null) {
				sb.append(line);
				sb.append("\n");
				line = reader.readLine();
			}
			return sb.toString();
		} finally {
			reader.close();
		}
	}

	public static void main(String[] args) throws IOException {
		
		String mapInfo = readFile("C:\\Users\\admin\\Desktop\\CodesquadTest\\map.txt");
		calculate(mapInfo, 1);
	}
}
