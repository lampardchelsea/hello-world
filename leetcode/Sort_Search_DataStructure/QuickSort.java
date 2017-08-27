/**
 * Refer to
 * http://flyingcat2013.blog.51cto.com/7061638/1281614
 * http://blog.csdn.net/morewindows/article/details/6684558
*/
public class QuickSort {
	public void quickSort(int[] arr) {
        qsort(arr, 0, arr.length - 1);
    }
  
    private void qsort(int[] arr, int low, int high) {
        if (low < high){
            int pivot = partition(arr, low, high);        //将数组分为两部分
            qsort(arr, low, pivot - 1);                   //递归排序左子数组
            qsort(arr, pivot + 1, high);                  //递归排序右子数组
        }
    }
  
    private int partition(int[] arr, int low, int high) {
        int pivot = arr[low];     //枢轴记录
        while(low < high) {
            while(low < high && arr[high] >= pivot) {
                --high;            
            }
            arr[low] = arr[high];            //交换比枢轴小的记录到左端
            while(low < high && arr[low] <= pivot) {
                ++low;           
            }
            arr[high] = arr[low];           //交换比枢轴小的记录到右端
        }
        //扫描完成，枢轴到位
        arr[low] = pivot;
        //返回的是枢轴的位置
        return low;
    }
    
    public static void main(String[] args) {
    	QuickSort q = new QuickSort();
    	int[] arr = {2,3,1};
    	q.quickSort(arr);
    	StringBuilder sb = new StringBuilder();
    	for(int i = 0; i < arr.length; i++) {
    		if(i == arr.length - 1) {
        	    sb.append(arr[i]);  			
    		} else {
    		    sb.append(arr[i]).append("->");
    		}
    	}
    	System.out.println(sb.toString());
    }
}
