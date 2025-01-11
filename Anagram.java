class Anagram {
    public static void main(String[] args) {
        String s1="care";
        String s2="race";
        if(s1.length()!=s2.length()){
            char[]c1=s1.toCharArray();
            char[]c2=s2.toCharArray();
            Arrays.sort(c1);
            Arrays.sort(c2);
            return Arrays.equals(c1,c2);
            if(Anagram(s1,s2)){
                System.out.println("Anagram");
            }else{
                System.out.println("Not anagram");
        }
        }
        }
}


