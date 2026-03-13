package BST;

import java.util.ArrayList;
import java.util.List;

public class BSTImpl<T extends Comparable <T>> implements BST<T>{

    protected BSTNode<T> root;

    public BSTImpl(){
        root = new BSTNode<T>();
    }

    @Override
    public boolean isEmpty(){
        return root.isEmpty();
    }

    @Override
    public int height(){
        return height(root);
    }

    private int height(BSTNode<T> node){
        if(node == null || node.isEmpty()) return -1;

        return 1 + Math.max(height(node.getLeft()), height(node.getRight()));
    }

    @Override
    public BSTNode<T> search(T element){
        return search(root, element);
    }

    private BSTNode<T> search(BSTNode<T> node, T element){

        if(node.isEmpty()) return node;

        int compare = element.compareTo(node.getData());

        if(compare == 0) return node;

        if(compare < 0) return search(node.getLeft(), element);

        return search(node.getRight(), element);
    }

    @Override
    public void insert(T element){
        if(element == null) return;

        insert(root, element);
    }

    private void insert(BSTNode<T> node, T element){
        if(node.isEmpty()){
            node.setData(element);
            node.setLeft(new BSTNode<T>());
            node.setRight(new BSTNode<T>());
            node.getLeft().setParent(node);
            node.getRight().setParent(node);
        } 
        
        else {
            int compare = element.compareTo(node.getData());

            if(compare < 0) insert(node.getLeft(), element);
            else if(compare > 0) insert(node.getRight(), element);
        }
    }

    @Override
    public void remove(T element){
        BSTNode<T> node = search(element);
        
        if(!node.isEmpty()){
            remove(node);
        }
    }

    private void remove(BSTNode<T> node){
        if(node.getLeft().isEmpty() && node.getRight().isEmpty()){
            node.setData(null);
        }

        else if(node.getLeft().isEmpty() || node.getRight().isEmpty()){

            BSTNode<T> child;

            if(!node.getLeft().isEmpty()){
                child = node.getLeft();
            }
            else{
                child = node.getRight();
            }

            node.setData(child.getData());
            node.setLeft(child.getLeft());
            node.setRight(child.getRight());
         }

         else{

            BSTNode<T> predecessor = maximum(node.getLeft());
            node.setData(predecessor.getData());
            remove(predecessor);

         }
    }

    @Override
    public BSTNode<T> maximum(){
        return maximum(root);
    }

    private BSTNode<T> maximum(BSTNode<T> node){
        if(node.isEmpty()) return node;

        while(!node.getRight().isEmpty()){
            node = node.getRight();
        }

        return node;
    }

    @Override
    public BSTNode<T> minimum(){
        return minimum(root);
    }

    private BSTNode<T> minimum(BSTNode<T> node){
        if(node.isEmpty()) return node;

        while(!node.getLeft().isEmpty()){
            node = node.getLeft();
        }

        return node;
    }

    @Override
    public T[] preOrder(){
        List<T> list = new ArrayList<>();
        preOrder(root, list);
        return (T[]) list.toArray(new Comparable[list.size()]);
    }

    private void preOrder(BSTNode<T> node, List<T> list) {

        if (!node.isEmpty()) {

            list.add(node.getData());
            preOrder(node.getLeft(), list);
            preOrder(node.getRight(), list);

        }
    }

    @Override
    public T[] Order(){
        List<T> list = new ArrayList<>();
        Order(root, list);
        return (T[]) list.toArray(new Comparable[list.size()]);
    }

    private void Order(BSTNode<T> node, List<T> list) {

        if (!node.isEmpty()) {

            Order(node.getLeft(), list);
            list.add(node.getData());
            Order(node.getRight(), list);

        }
    }

    @Override
    public T[] postOrder(){
        List<T> list = new ArrayList<>();
        postOrder(root, list);
        return (T[]) list.toArray(new Comparable[list.size()]);
    }

    private void postOrder(BSTNode<T> node, List<T> list) {

        if (!node.isEmpty()) {

            postOrder(node.getLeft(), list);
            postOrder(node.getRight(), list);
            list.add(node.getData());

        }
    }

    @Override
    public int size(){
        return size(root);
    }

    private int size(BSTNode<T> node){
        if(node.isEmpty()) return 0;

        return 1 + size(node.getLeft()) + size(node.getRight());
    }

}
