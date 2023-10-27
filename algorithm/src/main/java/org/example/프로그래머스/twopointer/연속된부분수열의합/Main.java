package org.example.프로그래머스.twopointer.연속된부분수열의합;


public class Main {

    public int[] solution(int[] sequence, int k) {

        int[] answer = new int[2];
        int currentLength = Integer.MAX_VALUE;
        int left = 0;
        int right, sum = 0;

        for(int i = 0; i < sequence.length; i++){
            right = i;
            sum += sequence[right];

            while(sum > k){
                sum -= sequence[left++];
            }

            if(sum == k){
                if (currentLength > right - left +1) {
                    answer[0] = left;
                    answer[1] = right;
                    currentLength = right - left +1;
                }
            }
        }

        return answer;
    }

    public static void main(String[] args) {
        Main main = new Main();
        main.solution(new int[]{1, 1, 1, 2, 3, 4, 5}, 5);
    }
}
