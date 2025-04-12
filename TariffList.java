//---------------------------------------------------------------------------------------
// Assignment 3
// Question:
// Written by: Nithushan Kanapathippillai (40313552) and Elliot Boismartel (40315629)
//---------------------------------------------------------------------------------------

public class TariffList implements TariffPolicy {

    private TariffNode head;
    private int size;

    public class TariffNode {
        private Tariff t;
        private TariffNode next;

        //default
        public TariffNode() {
            this.t = null;
            this.next = null;
        }

        public TariffNode(Tariff t, TariffNode tn){
            this.t=new Tariff(t);
            this.next=tn;
        }

        //copy constructor
        public TariffNode(TariffNode other){
            this.t=new Tariff(other.t);
            this.next=null;
        }

        //clone method
        public TariffNode clone(){
            return new TariffNode(this);
        }

        public boolean equals(TariffNode tn){
            return this.t==tn.t;
        }

        //getters and setters
        public Tariff getT(){return t;}
        public void setT(Tariff t){this.t=t;};
        public TariffNode getNext(){return next;};
        public void setNext(TariffNode tn){this.next=tn;};
    }

    public TariffList(){
        head=null;
        size=0;
    }

    //copy constructor
    public TariffList(TariffList other){
        //if the list is empty
        if (other.head==null){
            head=null;
            size=0;
            return;
        }
        head=other.head.clone();

        //new list
        TariffNode current=head;

        //old list "other"
        TariffNode current2=other.head.next;

        //clone "other" node to new list
        while (current2!=null){
            current.next=current2.clone();
            current=current.next;
            current2=current2.next;
        }
        //They are the same size
        size=other.size;
    }

    public void addToStart(Tariff t){
        head=new TariffNode(t, head);
        size++;
    }

    public void insertAtIndex(Tariff t, int i){

    }

    public void deleteFromIndex(int i){

    }

}
