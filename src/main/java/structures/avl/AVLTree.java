package structures.avl;

import structures.bst.*;

public class AVLTree<T extends Comparable<T>> extends BST<T> implements AVL<T> {

    private int calculateBalanceFactor(BSTNode<T> node){

        return height(node.getLeft()) - height(node.getRight());
    }

    private void leftRotation(BSTNode<T> node){

        BSTNode<T> pivot = node.getRight();
        pivot.setParent(node.getParent());

        if(node.getParent() == null){
            this.root = pivot;
        }
        else if(node == node.getParent().getLeft()){
            node.getParent().setLeft(pivot);
        }
        else{
            node.getParent().setRight(pivot);
        }

        node.setRight(pivot.getLeft());

        if(!pivot.getLeft().isEmpty()){
            pivot.getLeft().setParent(node);
        }

        pivot.setLeft(node);
        node.setParent(pivot); // A troca só é feita aqui.
    }

    private void rightRotation(BSTNode<T> node){

        BSTNode<T> pivot = node.getLeft();
        pivot.setParent(node.getParent());

        if(node.getParent() == null){
            this.root = pivot;
        }
        else if(node == node.getParent().getLeft()){
            node.getParent().setLeft(pivot);
        }
        else{
            node.getParent().setRight(pivot);
        }

        node.setLeft(pivot.getRight());

        if(!pivot.getRight().isEmpty()){
            pivot.getRight().setParent(node);
        }

        pivot.setRight(node);
        node.setParent(pivot);
    }

    private void rebalance(BSTNode<T> node){

        int balance = calculateBalanceFactor(node);

        if(balance > 1){
            if(calculateBalanceFactor(node.getLeft()) < 0){
                leftRotation(node.getLeft());
            }
            
            rightRotation(node);
        }
        else if(balance < -1){
            if(calculateBalanceFactor(node.getRight()) > 0){
                rightRotation(node.getRight());
            }

            leftRotation(node);
        }
    }

    @Override
    public void insert(T element){

        super.insert(element);

        BSTNode<T> node = search(element);

        while (node != null) {
            rebalance(node);
            node = node.getParent(); // subir até o topo de pai em pai.
            
        }
    }

    @Override
    public void remove(T element){

        BSTNode<T> node = search(element);

        super.remove(element);

        while(node != null){
            rebalance(node);
            node = node.getParent();
        }
    }




    
}
