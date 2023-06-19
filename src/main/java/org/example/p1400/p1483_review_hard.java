package org.example.p1400;

import java.util.Arrays;

public class p1483_review_hard {
    class TreeAncestor {
        static final int LOG = 16;
        int[][] ancestors;

        public TreeAncestor(int n, int[] parent) {
            ancestors = new int[n][LOG];
            for (int i = 0; i < n; i++) {
                Arrays.fill(ancestors[i], -1);
                //填充
            }
            for (int i = 0; i < n; i++) {
                ancestors[i][0] = parent[i];
            }
            for (int j = 1; j < LOG; j++) {
                for (int i = 0; i < n; i++) {
                    if (ancestors[i][j - 1] != -1) {
                        ancestors[i][j] = ancestors[ancestors[i][j - 1]][j - 1];
                    }
                }
            }
        }

        public int getKthAncestor(int node, int k) {
            for (int j = 0; j < LOG; j++) {
                // 检查二进制数 k 的第 j 位是否为1
                if (((k >> j) & 1) != 0) {
                    node = ancestors[node][j];
                    if (node == -1) {
                        return -1;
                    }
                }
            }
            return node;
        }
    }

}
