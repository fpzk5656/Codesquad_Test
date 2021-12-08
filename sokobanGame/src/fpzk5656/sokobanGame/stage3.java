package fpzk5656.sokobanGame;

import java.util.Scanner;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class stage3 {

	// 플레이어를 움직이고 맵 상태를 갱신함
	public static int[][] movePlayer(int[][] mapTale, char key) throws IOException {
		int[] columnGroup = getColumnGroupOfMap(mapTale);
		int row = getRowOfMap(mapTale);
		int playerColumn = 0;
		int playerRow = 0;

		// 플레이어 위치 구하기
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < columnGroup[i]; j++) {
				if (mapTale[j][i] == 3 || mapTale[j][i] == 6) {
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
		
		// 플레이어가 빈공간위에 서있을 경우
		boolean goalIsCover = false;
		if (mapTale[playerColumn][playerRow] == 6)
			goalIsCover = true;

		switch (mapTale[aimColumn][aimRow]) {
		case 1: // 빈 구멍
			// 플레이어가 빈구멍에 도착 하면 빈 구멍은 플레이어가 있는동안 모습을 감춘다.
			mapTale[playerColumn][playerRow] = goalIsCover ? 1 : 7;
			mapTale[aimColumn][aimRow] = 6;
			break;
		case 2: // 작은 공
			// 플레이어가 작은 공 위치에 도착했을 때
			// 작은공이 밀려나는 방향의 위치가 7 이거나 1일 때만 작은공은 그곳으로 이동한다
			// 만약 1이면 작은공이 이동한 곳에 9가 나타난다 9는 '0'모양임
			// 이 때 맵을 검토해서 1이 전부 없다면 다음 스테이지로 넘어간다.
			int moveCol = aimColumn;
			int moveRow = aimRow;
			if (aimColumn - playerColumn > 0)
				moveCol++;
			else if (aimColumn - playerColumn < 0)
				moveCol--;
			else if (aimRow - playerRow > 0)
				moveRow++;
			else if (aimRow - playerRow < 0)
				moveRow--;

			if (mapTale[moveCol][moveRow] == 7) {
				mapTale[moveCol][moveRow] = 2;
				mapTale[playerColumn][playerRow] = goalIsCover ? 1 : 7;
				mapTale[aimColumn][aimRow] = 3;
			} else if (mapTale[moveCol][moveRow] == 1) {
				mapTale[moveCol][moveRow] = 9;
				mapTale[playerColumn][playerRow] = goalIsCover ? 1 : 7;
				mapTale[aimColumn][aimRow] = 3;
				
				//여기가 클리어 포인트
				//stageClear(mapTale);
			}
			break;
		case 7: // 빈 공간
			mapTale[aimColumn][aimRow] = 3;
			mapTale[playerColumn][playerRow] = goalIsCover ? 1 : 7;
			break;
		case 9: // 9를 밀어내면 9가 있던 자리에 1(빈구멍)이 되고, 방향으로는 2가 생긴다.
				// 물론 밀어내려는 방향 쪽이 7이거나 1일 때만 가능하다
			int moveCol2 = aimColumn;
			int moveRow2 = aimRow;
			if (aimColumn - playerColumn > 0)
				moveCol2++;
			else if (aimColumn - playerColumn < 0)
				moveCol2--;
			else if (aimRow - playerRow > 0)
				moveRow2++;
			else if (aimRow - playerRow < 0)
				moveRow2--;

			if (mapTale[moveCol2][moveRow2] == 7) {
				mapTale[moveCol2][moveRow2] = 2;
				mapTale[playerColumn][playerRow] = goalIsCover ? 1 : 7;
				mapTale[aimColumn][aimRow] = 6;
			} else if (mapTale[moveCol2][moveRow2] == 1) {
				mapTale[moveCol2][moveRow2] = 9;
				mapTale[playerColumn][playerRow] = goalIsCover ? 1 : 7;
				mapTale[aimColumn][aimRow] = 6;
			}
			break;

		}

		return mapTale;
	}

	// 스테이지 클리어 함수
	public static void checkStageClear(int[][] mapTale) throws IOException {
		int[] columnGroup = getColumnGroupOfMap(mapTale);
		int row = getRowOfMap(mapTale);
		int ballCount = 0; 

		// 볼 카운트 점검
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < columnGroup[i]; j++) {
				if (mapTale[j][i] == 2) ballCount++;
			}
		}
		
		if(ballCount == 0)
		nextStage();
	}

	// 다음 스테이지 이동 함수
	public static void nextStage() throws IOException {
		System.out.printf("스테이지 %d를 클리어하였습니다.",stageNumber + 1);
		stageNumber++;
		resetGame();
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
				if (tale1[j][i] == 3 || tale1[j][i] == 6) {
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

	// 깊은 복사 함수
	public static int[][] deepCopy(int[][] mapTale) {
		int[][] tempTale = new int[999][999];
		for (int i = 0; i < mapTale.length; i++) {
			for (int j = 0; j < mapTale[i].length; j++) {
				tempTale[i][j] = mapTale[i][j];
			}
		}
		return tempTale;
	}

	// 방향키 입력받으면 처리하는 함수
	public static void inputKey(Scanner scanner, int[][] mapTale) throws IOException {
		// boolean gameOver = false;
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
					//여기다가 두 함수 몰아놓기 쌉가능하고
				switch (key) {
				case 'w':
					mapTale = movePlayer(mapTale, key);
					resultMap(mapTale);
					//stageClear(mapTale);
					if (playerPositionEquals(mapTale, tempMap)) {
						System.out.println(Character.toUpperCase(key) + ": (!경고) 해당 명령을 수행할 수 없습니다!");
					} else {
						System.out.println(Character.toUpperCase(key) + ": 위로 이동합니다.");
					}
					break;
				case 's':
					mapTale = movePlayer(mapTale, key);
					resultMap(mapTale);
					//stageClear(mapTale);
					if (playerPositionEquals(mapTale, tempMap)) {
						System.out.println(Character.toUpperCase(key) + ": (!경고) 해당 명령을 수행할 수 없습니다!");
					} else {
						System.out.println(Character.toUpperCase(key) + ": 아래로 이동합니다.");
					}
					break;
				case 'a':
					mapTale = movePlayer(mapTale, key);
					resultMap(mapTale);
					//stageClear(mapTale);
					if (playerPositionEquals(mapTale, tempMap)) {
						System.out.println(Character.toUpperCase(key) + ": (!경고) 해당 명령을 수행할 수 없습니다!");
					} else {
						System.out.println(Character.toUpperCase(key) + ": 왼쪽으로 이동합니다.");
					}
					break;
				case 'd':
					mapTale = movePlayer(mapTale, key);
					resultMap(mapTale);
					//stageClear(mapTale);
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
					// 스테이지를 리셋하는 함수
					resetGame();
					break;
				default:
					resultMap(mapTale);
					System.out.println(Character.toUpperCase(key) + ": (!경고) 해당 명령을 수행할 수 없습니다!");
					System.out.print("\n");
					break;
				}
				tempMap = deepCopy(mapTale);
			}
			turnNumber++;
			System.out.println("턴수: " + turnNumber);
			checkStageClear(mapTale);
			if (gameOver) {
				break;
			}
		}
	}

	// 스테이지를 리셋하는 함수
	public static void resetGame() throws IOException {
		turnNumber = 0;
		String mapInfo = readFile("C:\\Users\\admin\\Desktop\\CodesquadTest\\map.txt");
		calculate(mapInfo); // calculate(mapInfo, stageNum) 이렇게 만들어야 될 것 같다.
	}

	// 처음에 맵을 설치하는 함수
	public static int[][] initMap(String input) {
		int[][] tale = new int[999][999];
		// ===구분선이 없을 때만 출력하는 것..그래서 안나오던거였네 전체를 input에 넣어놨으니
		if (!input.contains("=")) {
			System.out.println(input);
			if (input.contains("Stage")) {
				System.out.printf("\n"); // Stage가 있을 경우 출력하고 한칸 띄우기

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
	public static void resultMap(int[][] mapTale) throws IOException {
		int[] columnGroup = getColumnGroupOfMap(mapTale);
		int row = getRowOfMap(mapTale);
		int ballCount = 0;
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
					ballCount++;
					break;
				case 3:
					line += 'P'; // 플레이어
					break;
				case 4:
					line += '='; // 구분선
					break;
				case 6:
					line += 'P';
					break;
				case 7:
					line += ' '; // 빈공간
					break;
				case 9:
					line += '0'; // 골인 상태
					break;
				}
			}
			// 저장되지 않은 2차원 배열의 인덱스 내용까지 출력되지 않도록 예외처리하는 것
			if (line == "" && inGame == true) {
				break;
			}
			System.out.println(line);
			// 맵을 검토하고 다음 스테이지로 넘어가는 함수
			//if(ballCount == 0)stageClear();
		}
	}

	public static void calculate(String input) throws IOException {
		// 여기서 "Stage"+ stageNum을 기준으로 구분선까지 잘라내기
		String[] stageMap = input.split("=");

		if (stageNumber >= stageMap.length) {
			gameOver = true;
			System.out.println("전체 게임을 클리어하셨습니다!" + "\n 축하드립니다!");
		} else {
			String[] lines = stageMap[stageNumber].split("\n");
			int count = 0;
			for (int i = 0; i < lines.length; i++) {
				if (count < lines[i].length()) {
					count = lines[i].length();
				}
			}
			// 맵 타일 제작 및 할당
			int[][] mapTale = new int[lines.length][count];
			mapTale = initMap(stageMap[stageNumber]);

			Scanner scanner = new Scanner(System.in);
			inputKey(scanner, mapTale);
		}
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

	static int stageNumber = 0;
	static int turnNumber = 0;
	static boolean gameOver = false;

	public static void main(String[] args) throws IOException {

		String mapInfo = readFile("C:\\Users\\admin\\Desktop\\CodesquadTest\\map.txt");
		calculate(mapInfo);
	}
}
