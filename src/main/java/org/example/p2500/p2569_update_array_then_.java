package org.example.p2500;

import java.util.ArrayList;
import java.util.List;

public class p2569_update_array_then_ {

//    给你两个下标从 0 开始的数组 nums1 和 nums2 ，和一个二维数组 queries 表示一些操作。总共有 3 种类型的操作：
//
//    操作类型 1 为 queries[i] = [1, l, r] 。你需要将 nums1 从下标 l 到下标 r 的所有 0 反转成 1 或将 1 反转成 0 。l 和 r 下标都从 0 开始。
//    操作类型 2 为 queries[i] = [2, p, 0] 。对于 0 <= i < n 中的所有下标，令 nums2[i] = nums2[i] + nums1[i] * p 。
//    操作类型 3 为 queries[i] = [3, 0, 0] 。求 nums2 中所有元素的和。
//    请你返回一个数组，包含所有第三种操作类型的答案。
//
//    示例 1：
//
//    输入：nums1 = [1,0,1], nums2 = [0,0,0], queries = [[1,1,1],[2,1,0],[3,0,0]]
//    输出：[3]
//    解释：第一个操作后 nums1 变为 [1,1,1] 。第二个操作后，nums2 变成 [1,1,1] ，所以第三个操作的答案为 3 。所以返回 [3] 。
//    示例 2：
//
//    输入：nums1 = [1], nums2 = [5], queries = [[2,0,0],[3,0,0]]
//    输出：[5]
//    解释：第一个操作后，nums2 保持不变为 [5] ，所以第二个操作的答案是 5 。所以返回 [5] 。
//    提示：
//
//            1 <= nums1.length,nums2.length <= 105
//    nums1.length = nums2.length
//1 <= queries.length <= 105
//    queries[i].length = 3
//            0 <= l <= r <= nums1.length - 1
//            0 <= p <= 106
//            0 <= nums1[i] <= 1
//            0 <= nums2[i] <= 109
class Solution {
    public long[] handleQuery(int[] nums1, int[] nums2, int[][] queries) {
        int n = nums1.length;//数组1的长度
        int m = queries.length;//多少次操作
        SegTree tree = new SegTree(nums1);//new 一个线段树

        long sum = 0;
        for (int num : nums2) {
            sum += num; //保持一个累积和
        }
        List<Long> list = new ArrayList<Long>();//列表中将包含所有查询时刻的累积和值。
        for (int i = 0; i < m; i++) {
            if (queries[i][0] == 1) {
                int l = queries[i][1];
                int r = queries[i][2];
                tree.reverseRange(l, r);
            } else if (queries[i][0] == 2) {
                sum += (long) tree.sumRange(0, n - 1) * queries[i][1];//将当前和添加到SegTree上指定范围查询的结果中（tree.sumRange(0, n - 1) * queries[i][1]），并将结果存储在sum变量中。
            } else if (queries[i][0] == 3) {
                list.add(sum);
            }
        }
        long[] ans = new long[list.size()];
        for (int i = 0; i < list.size(); i++) {//类型转换 将列表转成long数组
            ans[i] = list.get(i);
        }
        return ans;
    }
}

    class SegTree {
        private SegNode[] arr;

        public SegTree(int[] nums) {
            int n = nums.length;
                arr = new SegNode[n * 4 + 1];//n * 4用于确保有足够的空间来存储线段树的中间结点和叶结点（中间结点数一般不超过叶结点数的4倍）。+1是为了确保数组的下标从1开始，以便方便进行下标计算。在这个实现中，线段树的根结点存储在arr[1]的位置。
                build(1, 0, n - 1, nums);//0 和 n - 1: 这是线段树表示的原始数组 nums1 的范围。在这个实现中，nums1 表示原始的输入数据，该数据构成了线段树的叶节点。0 是 nums1 数组的左边界，表示第一个元素；n - 1 是 nums1 数组的右边界，表示最后一个元素。
        }

        public int sumRange(int left, int right) {
            return query(1, left, right);
        }

        public void reverseRange(int left, int right) {
            modify(1, left, right);
        }

        /***
         * build函数通过递归地构建线段树，从根结点开始，依次填充线段树的各个结点。在递归的过程中，将线段树结点的区间范围和累加和计算出来，最终构建出完整的线段树。
         * @param id id: 表示当前构建的线段树结点的索引，它对应于arr数组中的位置。
         * @param l  l 和 r: 表示当前构建结点的区间范围，其中 l 是区间左边界，r 是区间右边界。
         * @param r  l 和 r: 表示当前构建结点的区间范围，其中 l 是区间左边界，r 是区间右边界。
         * @param nums nums: 是原始数组，其中存储了线段树所代表的数据。
         *
         */
        public void build(int id, int l, int r, int[] nums) {//是用来构建线段树的递归函数。它的目的是根据给定的区间范围 [l, r] 和原始数组 nums，
            arr[id] = new SegNode();
            arr[id].l = l;              // 将当前结点的区间范围 [l, r] 赋值给线段树结点的成员变量 l 和 r。
            arr[id].r = r;
            arr[id].lazytag = false;    // 将当前结点的懒标记设置为false，表示当前结点的子结点还未被更新。
            if(l == r) {
                arr[id].sum = nums[l];
                return;
            }
            int mid = (l + r) >> 1;      // 计算当前区间范围 [l, r] 的中间位置，用于将区间划分成两部分，分别对应左子树和右子树。

            // 2 * id：这个计算得到当前节点的左子节点的索引。在线段树的二叉树表示中，每个节点都是 arr 数组的一个元素，当前节点的左子节点索引为 2 * id。
            // 递归构建当前结点的左子树，传入的区间范围为 [l, mid]。
            build(2 * id, l, mid, nums);

            //当前节点的右子节点索引为 2 * id + 1
            //递归构建当前结点的右子树，传入的区间范围为 [mid + 1, r]。
            build(2 * id + 1, mid + 1, r, nums);

            //当左右子树都构建完毕后，计算当前结点的 sum 值，该值等于左子树的 sum 加上右子树的 sum，表示当前结点所代表的区间的值为其左右子树的值之和。
            arr[id].sum = arr[2 * id].sum + arr[2 * id + 1].sum;

        }

        /* pushdown函数：下传懒标记，即将当前区间的修改情况下传到其左右孩子结点 */
        public void pushdown(int x) {
            // 检查当前节点arr[x]是否有懒标记。如果有懒标记，说明当前区间内有尚未下传的修改操作。
            if(arr[x].lazytag) {
                //将当前节点的左孩子的懒标记取反，表示将当前节点的修改操作下传给左孩子节点。
                arr[2 * x].lazytag = !arr[2 * x].lazytag;

                // 更新左孩子节点的sum值。这里计算了原来左孩子节点区间长度减去原来的sum值，相当于将0变为1，1变为0。 为啥呢
                arr[2 * x].sum = arr[2 * x].r - arr[2 * x].l + 1 - arr[2 * x].sum;

                //将当前节点的右孩子的懒标记取反，表示将当前节点的修改操作下传给右孩子节点。
                arr[2 * x + 1].lazytag = !arr[2 * x + 1].lazytag;

                //更新右孩子节点的sum值。同样，这里计算了原来右孩子节点区间长度减去原来的sum值，相当于将0变为1，1变为0。
                arr[2 * x + 1].sum = arr[2 * x + 1].r - arr[2 * x + 1].l + 1 - arr[2 * x + 1].sum;

                //更新右孩子节点的sum值。同样，这里计算了原来右孩子节点区间长度减去原来的sum值，相当于将0变为1，1变为0。
                arr[x].lazytag = false;
            }
        }

//        下传懒标记是线段树中的一种优化技术，用来减少不必要的更新操作，从而提高线段树的性能。线段树在进行区间修改操作时，可能会导致某个结点的区间被重复修改多次，这样会造成额外的计算和更新开销。
//        具体来说，在线段树的区间修改过程中，当对某个结点进行修改时，我们可以先将修改操作记录在结点的懒标记中，而不是立即更新该结点。这样可以避免多余的更新操作，只有在需要查询或进一步修改子结点时，才会将懒标记下传，并更新相应的结点。
//        在这个具体的实现中，SegNode 类中的 lazytag 变量就是用来存储懒标记的。当需要对某个结点的区间进行修改时，我们将修改操作记录在该结点的 lazytag 中，并不立即更新该结点的 sum 值。
//        在后续的查询或修改操作中，当需要用到该结点的值时，会先检查 lazytag 是否为 true，如果是，则将修改操作下传给子结点，并更新相应的 sum 值。这样可以确保线段树中的结点始终处于正确的状态，避免了重复的修改操作，提高了性能。
//        在 pushdown 方法中，当需要对某个结点进行查询或修改操作时，会先检查该结点的 lazytag，如果为 true，则将修改操作下传给子结点，并更新子结点的 sum 值。
//        然后将当前结点的 lazytag 置为 false，表示当前结点的子结点已经更新，下次查询或修改子结点时不需要再传递修改操作。
//        总的来说，下传懒标记的作用是将当前结点的修改操作下传到其子结点，避免重复的修改，提高线段树的查询和修改性能。

        /* 区间修改 */
        public void modify(int id, int l, int r) {
            //首先，方法会检查当前结点的区间是否完全包含在要修改的区间 [l, r] 内。如果是，则直接对当前结点进行修改，而不需要对其子结点进行更新。
            if (arr[id].l >= l && arr[id].r <= r) {
                //因为线段树的构建过程中计算了每个结点的 sum 值，所以我们可以利用这个信息来方便进行区间修改和查询操作。
                arr[id].sum = (arr[id].r - arr[id].l + 1) - arr[id].sum;

                //是将当前结点的懒标记取反，表示当前结点区间内的修改操作已经下传到其子结点，下次查询或继续操作子结点时需要更新。
                arr[id].lazytag = !arr[id].lazytag;
                return;
            }

            //下传懒标记，即将当前区间的修改情况下传到其左右孩子结点
            pushdown(id);

            int mid = (arr[id].l + arr[id].r) >> 1;
            //如果当前结点的左子结点的右边界大于等于要修改的左边界 l，说明左子结点可能与要修改的区间有交集，递归调用 modify 方法对左子结点进行修改。
            if (arr[2 * id].r >= l) {
                modify(2 * id, l, r);
            }
            //如果当前结点的右子结点的左边界小于等于要修改的右边界 r，说明右子结点可能与要修改的区间有交集，递归调用 modify 方法对右子结点进行修改。
            if(arr[2 * id + 1].l <= r) {
                modify(2 * id + 1, l, r);
            }
            // 更新当前结点的 sum 值，即将左子结点和右子结点的 sum 值相加，得到当前结点的 sum 值。
            arr[id].sum = arr[2 * id].sum + arr[2 * id + 1].sum;
        }

        /* 区间查询 */
        public int query(int id, int l, int r) {
            if (arr[id].l >= l && arr[id].r <= r) {
                // 如果当前结点的区间完全包含在要查询的区间 [l, r] 内，直接返回该结点的 sum 值，表示该区间内的数值之和。
                return arr[id].sum;
            }

            // 如果当前结点的区间与要查询的区间没有交集（即当前结点的右边界小于要查询的左边界 l 或当前结点的左边界大于要查询的右边界 r），则表示当前结点和要查询的区间没有重叠部分，返回0。
            if (arr[id].r < l || arr[id].l > r) {
                return 0;
            }

            // 如果当前结点的区间与要查询的区间没有交集（即当前结点的右边界小于要查询的左边界 l 或当前结点的左边界大于要查询的右边界 r），则表示当前结点和要查询的区间没有重叠部分，返回0。
            pushdown(id);

            //创建一个变量 res 用于存储最终查询结果，初始化为0。
            int res = 0;

            //:如果当前结点的右子结点的左边界小于等于要查询的右边界 r，说明右子结点与要查询的区间有交集，递归调用 query 方法对右子结点进行查询，并将查询结果加到 res 中。
            if (arr[2 * id].r >= l) {
                res += query(2 * id, l, r);
            }

            //: 如果当前结点的右子结点的左边界小于等于要查询的右边界 r，说明右子结点与要查询的区间有交集，递归调用 query 方法对右子结点进行查询，并将查询结果加到 res 中。
            if (arr[2 * id + 1].l <= r) {
                //表示线段树中位于区间 [l, r] 内的结点所代表的数值之和。
                res += query(2 * id + 1, l, r);
            }
            return res;
        }
    }

    class SegNode {
        public int l, r, sum;//用来表示区间内1的数量，方便进行区间修改和查询操作。
        public boolean lazytag;

        public SegNode() {
            this.l = 0;
            this.r = 0;
            this.sum = 0;
            this.lazytag = false;
        }
    }

}
