package functions;

public class FunctionNode {
    private FunctionPoint point;
    private FunctionNode next;
    private FunctionNode prev;


    public FunctionNode(FunctionPoint point){
        this.point = point;
    }

    public FunctionNode(){
        this.point = new FunctionPoint();
    }


    public FunctionNode getNext() {
        return next;
    }

    public FunctionNode getPrev() {
        return prev;
    }

    public FunctionPoint getPoint() {
        return point;
    }

    public void setNext(FunctionNode next) {
        this.next = next;
    }

    public void setPoint(FunctionPoint point) {
        this.point = point;
    }

    public void setPrev(FunctionNode prev) {
        this.prev = prev;
    }
}

