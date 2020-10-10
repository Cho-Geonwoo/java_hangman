import java.util.*;
import java.io.*;

//��� ���� �� �ܾ�� ���õ� Ŭ����
class Alphabet{
	StringBuffer tmp;
	int a[];
	HashMap<Character,Vector<Integer>> hm = new HashMap<Character,Vector<Integer>>();
	//������ ���� ������ ����� ���ĺ� ��� -�� ����ְ� ���ĺ��� ���ĺ��� �ش��ϴ� ��ġ�� HashMap�� ������.
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
	//��� ���� �� ������ ���ڿ��� ������.
	public void show() {
		for(int i=0;i<tmp.length();i++) {
			System.out.print(tmp.charAt(i));
		}
	}
	//�Է¹��� ���ڷ� ����� ����� ��ü��.
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
	//ó�� �Է¹޾Ҵ� ���ڿ��� ������ ���ڿ��� ������ ����.
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
	//���� �޼ҵ� ����
	public static void main(String[] args) throws IOException {
		//��� ������ ���� �ܾ ����ִ� �ؽ�Ʈ ���� �ҷ�����
		File f = new File("words.txt");
		while(true) {
			//���Ͽ� �ִ� �ܾ��� ���� �ľ�
			int filelength = 0;
			FileReader fr = new FileReader(f);
			int c;
			while((c = fr.read()) != -1) {
				if((char)c=='\n')
					filelength++;
			}
			fr.close();
			System.out.println("���ݺ��� ��� ������ �����մϴ�.");
			Scanner sc = new Scanner(new FileReader(f));
			//i�� 0���� ���Ͽ� �����ϴ� �ܾ�� ������ ������ ���ڸ� ������ �� �� ���ڿ� �ش��ϴ� �ܾ hangman�� ������. 
			int i = (int) Math.round(Math.random()*filelength);
			int count = 0;
			String hangman = null;
			while(count<=i) {
				hangman = sc.nextLine();
				count++;
			}
			//�ܾ� hangman�� StringBuffer�� �ٲ㼭 ����
			StringBuffer sb = new StringBuffer(hangman);
			sc = new Scanner(System.in);
			//�Է¹��� ���� ���̵��� �ش��ϴ� ���ڸ�ŭ hangman �ܾ ������ �� ���� ��ġ�� �����ϴ� ���ڸ� ������.(��, ��ġ�� �ߺ����� �ʰ� ����)
			int tmp;
			while(true) {
				System.out.print("���� ���̵��� �Է����ּ���.(1,2,3,4)>>");
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
			//�������� ��� ���� ����, hangman�ܾ Alphabet class�� �̿��� ����
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
			//�ܾ� ���߱� 5�� Ʋ���� ���
			if(death==5) {
				System.out.println("5�� ���� �Ͽ����ϴ�.");
				System.out.print(hangman);}
			else {
				System.out.print(hangman);
				System.out.println("\n������ϴ�.");
			}
			//������ �� �̾�� ������ ����
			System.out.print("\nNext(y/n)?");
			if(sc.next().equals("n")) {
				sc.close();
				System.exit(0);
			}
			else {}
		}
	}
}
