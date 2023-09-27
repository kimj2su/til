package org.example.백준.우선순위큐.최대힙11279;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

class MaxPriorityQueue {
    int[] heap;
    int size;

    public MaxPriorityQueue() {
        this.heap = new int[100001];
        this.size = 0;
    }

    void swap(int a, int b) {
        int temp = heap[a];
        heap[a] = heap[b];
        heap[b] = temp;
    }

    public void push(int x) {
        heap[++size] = x;
        int current = size;
        while (current > 1) {
            int parent = current / 2;
            if (heap[parent] >= heap[current]) break;
            swap(parent, current);
            current = parent;
        }
    }

    public int pop() {
        if (size == 0) return 0;
        int ret = heap[1];

        heap[1] = heap[size--];
        int current = 1;
        while (current * 2 <= size) {
            int left = current * 2;
            int right = left + 1;
            int child = left;
            if (right <= size && heap[left] < heap[right]) {
                child = right;
            }
            if (heap[current] >= heap[child]) break;
            swap(current, child);
            current = child;
        }
        return ret;
    }
}

public class Main {

    static int N;

    public static void main(String[] args) {
        MaxPriorityQueue maxPriorityQueue = new MaxPriorityQueue();
        Reader reader = new Reader();
        N = reader.nextInt();

        StringBuilder sb = new StringBuilder();
        while (N-- > 0) {
            int x = reader.nextInt();
            if (x == 0) {
                sb.append(maxPriorityQueue.pop()).append("\n");
            } else {
                maxPriorityQueue.push(x);
            }
        }
        System.out.println(sb.toString());
    }


    static class Reader {
        BufferedReader br;
        StringTokenizer st;

        public Reader() {
            br = new BufferedReader(new InputStreamReader(System.in));
        }

        String next() {
            while (st == null || !st.hasMoreElements()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }

        int nextInt() {
            return Integer.parseInt(next());
        }

        long nextLong() {
            return Long.parseLong(next());
        }

        double nextDouble() {
            return Double.parseDouble(next());
        }

        String nextLine() {
            String str = "";
            try {
                str = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return str;
        }
    }
}
