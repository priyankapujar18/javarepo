class Array{
    public static void main(String[] args) {
        int[] numbers={40,25,7,65,88};
        int largest=numbers[0];
        for(int i=0;i<numbers.length;i++){
            if(numbers[i]>largest){
                largest=numbers[i];
            }
        }
        System.out.println("The largest number is:"+largest);
    }
}