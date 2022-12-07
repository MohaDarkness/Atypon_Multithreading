import java.util.NoSuchElementException;

public class TasksPQ {

    private Task[] array;
    private int n;
    public TasksPQ(){
        n = 0;
        array = new Task[2];
    }

    public void insert(Task item) { // O(log N)
        if(item==null)
            throw new IllegalArgumentException();
        if (n == array.length - 1)
            resize(array.length * 2);
        n++;
        array[n] = item;
        swim(n);
    }

    public Task pop() {
        if(n==0)
            throw new NoSuchElementException();
        Task tmp = array[1];
        exchange(1,n);
        array[n] = null;
        n--;
        sink(1);
        if ((n > 2) && (n == (array.length - 1) / 4))
            resize(array.length / 2);

        return tmp;
    }

    public boolean isEmpty(){
        return n==0;
    }
    private boolean less(int i, int j) {
        if(array[i].getPriority() < array[j].getPriority())
            return true;
        if(array[i].getPriority() > array[j].getPriority())
            return false;
        return array[i].getDurationTime() < array[j].getDurationTime();
    }
    public String printQueue(){
        String st = n + " Tasks Waiting Inside Queue -> [";
        for(int i = 1; i <= n; i++){
            if(i == n)
                st += array[i].getTaskName();
            else
                st += array[i].getTaskName() + ", ";
        }
        st += "]";
        return st;
    }

    private void swim(int k) {
        while (k > 1 && less(k/2, k)) {
            exchange(k, k/2);
            k = k/2;
        }
    }

    private void sink(int k){
        while(2*k <= n){
            int j = 2*k;
            if(2*k+1 <= n && less(2*k,2*k+1))
                j = 2*k+1;
            if(!less(k,j)) break;
            exchange(k,j);
            k = j;
        }
    }

    private void exchange(int i, int j) {
        Task tmp = array[i];
        array[i] = array[j];
        array[j] = tmp;
    }

    private void resize(int capacity){
        Task[] temp = new Task[capacity];
        if (n >= 0) System.arraycopy(array, 1, temp, 1, n);
        array = temp;
    }

}