import java.util.*;
import java.io.*;

//행맨 게임 내 단어와 관련된 클래스
class Alphabet{
	StringBuffer tmp;
	int a[];
	HashMap<Character,Vector<Integer>> hm = new HashMap<Character,Vector<Integer>>();
	//난수에 의해 지정된 빈곳에 알파벳 대신 -를 집어넣고 알파벳과 알파벳에 해당하는 위치를 HashMap에 저장함.
	public Alphabet(StringBuffer sb, int a[]) {
		this.a = a;
		this.tmp = sb;
		for(int i=0;i<a.length;i++) {
			if(hm.containsKey(tmp.charAt(a[i]))) {
				Vector<Integer> v = hm.get(sb.charAt(a[i]));
				v.add(a[i]);
				hm.put(tmp.charAt(a[i]), v);
			}
			else {
				Vector<Integer> v = new Vector<Integer>();
				v.add(a[i]);
				hm.put(tmp.charAt(a[i]),v);
			}
			tmp.delete(a[i], a[i]+1);
			tmp.insert(a[i], "-");
		}
	}
	//행맨 게임 현 상태의 문자열을 보여줌.
	public void show() {
		for(int i=0;i<tmp.length();i++) {
			System.out.print(tmp.charAt(i));
		}
	}
	//입력받은 문자로 행맨의 빈곳을 대체함.
	public boolean sub(String in) {
		if(hm.containsKey(new Character(in.charAt(0)))){
			Vector<Integer> v = hm.get(in.charAt(0));
			for(int i=0;i<v.size();i++) {
				tmp.delete(v.get(i), v.get(i)+1);
				tmp.insert(v.get(i), in);
			}
			hm.remove(in.charAt(0));
			return true;
		}
		else {
			return false;
		}
	}
	//처음 입력받았던 문자열과 현재의 문자열이 같은지 비교함.
	public boolean same(){
		for(int i=0;i<tmp.length();i++) {
			if((tmp.charAt(i))=='-') {
				return false;
			}
		}
		return true;
	}
}

public class problem9{
	@SuppressWarnings("resource")
	//메인 메소드 포함
	public static void main(String[] args) throws IOException {
		//행맨 게임을 위한 단어가 들어있는 텍스트 파일 불러오기
		File f = new File("words.txt");
		while(true) {
			//파일에 있는 단어의 개수 파악
			int filelength = 0;
			FileReader fr = new FileReader(f);
			int c;
			while((c = fr.read()) != -1) {
				if((char)c=='\n')
					filelength++;
			}
			fr.close();
			System.out.println("지금부터 행맨 게임을 시작합니다.");
			Scanner sc = new Scanner(new FileReader(f));
			//i에 0부터 파일에 존재하는 단어수 사이의 무작위 숫자를 생성한 뒤 그 숫자에 해당하는 단어를 hangman에 저장함. 
			int i = (int) Math.round(Math.random()*filelength);
			int count = 0;
			String hangman = null;
			while(count<=i) {
				hangman = sc.nextLine();
				count++;
			}
			//단어 hangman을 StringBuffer로 바꿔서 저장
			StringBuffer sb = new StringBuffer(hangman);
			sc = new Scanner(System.in);
			//입력받은 게임 난이도에 해당하는 숫자만큼 hangman 단어에 생성할 빈 공간 위치를 결정하는 숫자를 생성함.(단, 위치가 중복되지 않게 생성)
			int tmp;
			while(true) {
				System.out.print("게임 난이도를 입력해주세요.(1,2,3,4)>>");
				tmp = sc.nextInt();
				if(tmp+1<=sb.length()) {
					break;
				}
			}
			int a[] = new int[tmp+1];
			a[0] = (int) Math.round(Math.random()*(sb.length()-1));
			for(int i1=1;i1<tmp+1;i1++) {
				a[i1] = (int) Math.round(Math.random()*(sb.length()-1));
				for(int j = 0;j<i1;j++) {
					if(a[i1]==a[j]) {
						i1--;
					}
				}
			}
			//본격적인 행맨 게임 시작, hangman단어를 Alphabet class를 이용해 변형
			int death = 0;
			Alphabet al = new Alphabet(sb,a);
			while(death<5) {
				al.show();
				System.out.print(">>");
				if(!al.sub(sc.next())) {
					death++;
				}
				else {
					if(al.same()) {
						break;
					}
				}
			}
			//단어 맞추기 5번 틀리면 출력
			if(death==5) {
				System.out.println("5번 실패 하였습니다.");
				System.out.print(hangman);}
			else {
				System.out.print(hangman);
				System.out.println("\n맞췄습니다.");
			}
			//게임을 더 이어나갈 것인지 결정
			System.out.print("\nNext(y/n)?");
			if(sc.next().equals("n")) {
				sc.close();
				System.exit(0);
			}
			else {}
		}
	}
}
