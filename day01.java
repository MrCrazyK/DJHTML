public class day01{
    public static void main(String[] args){
        int a = 3;
        String b = "4";
        System.out.println("Hello,world!");
        System.out.println(func(a,b));
    }

    public static int func(int c, String d){
        for (int i = 1; i < 10; i++) {
            for (int j = 1; j <= i; j++) {
                System.out.print(j + "X" + i + "=" + i*j + "\t");
            }
            System.out.println();
        }
        return 0;
    }
}