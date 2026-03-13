package structures.linked.list;

public class NodeLinked<T> implements Node {
    private final T data;
    private NodeLinked<T> next;

    public NodeLinked(T data) {
        this.data = data;
        this.next = new NodeLinked<>(); // ✅ Sentinela NIL, não null;
    }
    public NodeLinked() {
        this.data = null;
        this.next = null;
    }


    public NodeLinked<T> getNext() {
        return next;
    }
    public void setNext(NodeLinked<T> nodeLinked){
        this.next = nodeLinked;
    }

    public T getData() {return data;}

    @Override
    public boolean isNIL(){
        return data == null;
    }


}
