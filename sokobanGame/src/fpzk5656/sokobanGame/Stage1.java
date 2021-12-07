package fpzk5656.sokobanGame;

import java.util.Scanner;

public class Stage1 {

	public static void print(int[][] tale, int[] horizontalGroup, int horizontal, int vertical) {
		int hole = 0;
		int ball = 0;
		int playerX = 0;
		int playerY = 0;
		int count = 0;
		while (true) {
			if (horizontalGroup[count] == 0)
				break;

			for (int i = 0; i < vertical; i++) {
				for (int j = 0; j < horizontalGroup[count]; j++) {

					switch (tale[j][i]) {
					case 1:
						hole++;
						break;
					case 2:
						ball++;
						break;
					case 3:
						playerX = j + 1;
						playerY = i + 1;
						break;
					}
				}
				count++;
			}
		}
		System.out.println("가로크기: " + horizontal);
		System.out.println("세로크기: " + vertical);
		System.out.println("구멍의 수: " + hole);
		System.out.println("공의 수: " + ball);
		System.out.printf("플레이어 위치 (%d, %d)\n", playerY, playerX);
		System.out.printf("\n");
	}

	public static void calculate(Scanner scanner) {
		int horizontal = 0;
		int vertical = 0;
		int[][] tale = new int[999][999];
		int[] horizontalGroup = new int[999];
		boolean nextStage = false;
		while (true) {
			String input = scanner.nextLine();
			
			if (!input.contains("=")) {
				System.out.println(input);
				if(input.contains("Stage"))
				{
					System.out.printf("\n");
				}
			}
			else
			{
				// 구분선이 있다면 다음 스테이지가 있다는 얘기니까 활성화 됨
				nextStage = true;
			}

			// 요녀석을 boolean값으로 만들어서 함수에 넣는 식으로 하는 거지
			if (!input.contains("#") && !input.contains("Stage")) {
				break;
			}
			if (input.indexOf("#") > -1) {

				if (horizontal < input.length())
					horizontal = input.length();
				horizontalGroup[vertical] = input.length();

				// 문자열의 문자를 하나하 쪼개서 분석하기
				for (int i = 0; i < input.length(); i++) {
					char n = input.charAt(i);
					switch (n) {
					case '#':
						tale[i][vertical] = 0;	// 벽
						break;
					case 'O':
						tale[i][vertical] = 1;	// 구멍
						break;
					case 'o':
						tale[i][vertical] = 2;	// 공
						break;
					case 'P':
						tale[i][vertical] = 3;	// 플레이어
						break;
					case '=':
						tale[i][vertical] = 4;	// 구분선
						break;
					case ' ':
						tale[i][vertical] = 7; // 빈공간
						break;
					}
				}
				vertical++;
			}
		}

		print(tale, horizontalGroup, horizontal, vertical);
		if(nextStage)
		{
			nextStage = false;
			calculate(scanner);
		}
	}

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		calculate(scanner);

	}

}
