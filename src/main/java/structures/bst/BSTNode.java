package structures.bst;

public class BSTNode<T> {

    private T data;
    private BSTNode<T> left;
    private BSTNode<T> right;
    private BSTNode<T> parent;

    public BSTNode(){};

    public BSTNode(T data) {
        this.data = data;
    }

    public T getData(){
        return data;
    }

    public void setData(T data){
        this.data = data;
    }

    public BSTNode<T> getLeft(){
        return left;
    }

    public void setLeft(BSTNode<T> left){
        this.left = left;
    }

    public BSTNode<T> getRight(){
        return right;
    }

    public void setRight(BSTNode<T> right){
        this.right = right;
    }

    public BSTNode<T> getParent(){
        return parent;
    }

    public void setParent(BSTNode<T> parent){
        this.parent = parent;
    }

    public boolean isEmpty(){
        return data == null;
    }

    @Override
    public String toString(){
        return data == null ? "NILL": data.toString();
    }
}
