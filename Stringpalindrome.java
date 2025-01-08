class Stringpalindrome {
    public static void main(String[] args) {
        String original="MOM";
        String rev="";
        for(int i=original.length()-1;i>=0;i--){
            rev+=original.substring(i,i+1);
        }
        if(original.equals(rev)){
            System.out.println(original + "String is Palindrome");
        }else{
            System.out.println(original + "String is not Palindrome");
        }
    }
    
}
