package fpzk5656.sokobanGame;

import java.util.Scanner;

public class Stage1 {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		// 문자열 받아온 것을 2차원 배열에 저장하거나 출력한다.
		// 맵의 한정크기를 처음에 입력 받지 않으므로 배열 크기는 임시로 정한다.
		int[][] tale = new int[9999][9999];
		int countY = 0;
		int countX = 0;
		int horizontal = 0;
		int[] horizontalGroup = new int[999];
		int vertical = 0;
		int hole = 0;
		int ball = 0;
		int playerX = 0;
		int playerY = 0;

		while (vertical < 4) {
			String input = scanner.nextLine();
			System.out.println(input);

			if (horizontal < input.length())
				horizontal = input.length();
			horizontalGroup[vertical] = input.length();

			// 문자열의 문자를 하나하 쪼개서 분석하기
			for (int i = 0; i < input.length(); i++) {
				char n = input.charAt(i);
				switch (n) {
				case '#':
					tale[i][vertical] = 0;
					break;
				case 'O':
					tale[i][vertical] = 1;
					break;
				case 'o':
					tale[i][vertical] = 2;
					break;
				case 'P':
					tale[i][vertical] = 3;
					break;
				case '=':
					tale[i][vertical] = 4;
					break;
				case ' ':
					tale[i][vertical] = 7; // 잠시만 7로 해놓는거
					break;
				}
			}

			vertical++;
		}
		int count = 0;
		while (true) {
			if(horizontalGroup[count] == 0)break;
			
			//for (int i = 0; i < horizontal; i++) {
			for(int i = 0; i < vertical; i++) {
				for (int j = 0; j < horizontalGroup[count]; j++) {
					int k = horizontalGroup[count];
					System.out.println(tale[j][i]);
					
					switch(tale[j][i])
					{
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
		System.out.printf("플레이어 위치 (%d, %d)",playerX,playerY);
		
	}
}
