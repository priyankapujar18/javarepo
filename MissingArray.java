class MissingArray {
    public static int findMissingNumber(int[]array,int n) {
        int expectedSum=n*(n+1)/2;
        int actualSum=0;
        for(int num:array){
            actualSum+=num;
         }
         return expectedSum-actualSum;
        }
        public static void main(String[] args) {
            int[]array={1,2,3,4,5,6};
            int n=6;
            int missingnumber=findMissingNumber(array,n);
            System.out.println("The missing number is: " );
        }
    
}
