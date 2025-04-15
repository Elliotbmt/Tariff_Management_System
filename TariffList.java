import java.util.NoSuchElementException;

//---------------------------------------------------------------------------------------
// Assignment 3
// Question:
// Written by: Nithushan Kanapathippillai (40313552) and Elliot Boismartel (40315629)
//---------------------------------------------------------------------------------------

public class TariffList implements TariffPolicy {

    private static TariffNode head;
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
        TariffNode node = new TariffNode();
        node.t = t;
        node.next = null;

        node.next = head; //moves the head to the next node
        head = node; //assign new node to the head

        size++;
    }

    public void insertAtIndex(int index, Tariff t){
        TariffNode node = new TariffNode();
        node.t = t;
        node.next = null;

        if (index < 0 || index > size) {
            throw new NoSuchElementException("Invalid index");
        }

        if (index == 0) {
            addToStart(t);
        }

        else {
            TariffNode n = head; //always start with this when travelling through nodes
            for (int i = 0; i<index-1; i++) { //index-1 to change the address of the previous node to the one u wanna add
                n = n.next;
            }

            node.next = n.next; //assign the adressof the old node (from the node right before) to the address of the node youre adding
            n.next = node; //assign the new node to the adress of the one at index-1

            size++;
        }
    }

    public void deleteFromIndex(int index){

        if (index < 0 || index > size) {
            throw new NoSuchElementException("Invalid index");
        }
        
        if (index ==0 ) {
            head = head.next;
            size--;
        }

        else {
            TariffNode n = head;
            TariffNode n1 = null; 
            for (int i = 0; i<index-1; i++) { 
                n = n.next;
            } 
            n1 = n.next;
            n.next = n1.next;

            n1 = null; //completely deleted
            size--;
        }
    }


    public void deleteFromStart() {
        
        if (size == 0) {
            return;
        }

        head = head.next;
        size--;

    }


    public void replaceAtIndex(int index, Tariff t) {
        if (index < 0 || index >= size) {
            return;  
        }

        else {
            TariffNode n = head;
        
            for (int i = 0; i<index; i++) { 
                n = n.next;
            } 
            n.setT(t); //not sure if this function works
        }

    }

    //search list for a tarif
    public TariffNode find(String destination, String origin, String category) {
        int iterations = 0;
        TariffNode n = head; 
        
        while(n!= null) {
            iterations++;

            Tariff t = n.getT();
            if (t.getOriginCountry().equalsIgnoreCase(origin) && t.getDestinationCountry().equalsIgnoreCase(destination) && t.getProductCategory().equalsIgnoreCase(category)) {
                System.out.println("\n----------------------------------------------------\nIt took " + iterations + " iterations to find this product:");
                return n; //returns a pointer to that TariffNode
            }

            n = n.next;
        }

        System.out.println("\n--------------------------------------------------\nAfter " + iterations + " iterations this product was not found.");
        return null;

    }

    public boolean contains(String destination, String origin, String category) {
        TariffNode n = head;

        while(n!= null) {
            Tariff t = n.getT();

            if (t.getOriginCountry().equalsIgnoreCase(origin) && t.getDestinationCountry().equalsIgnoreCase(destination) && t.getProductCategory().equalsIgnoreCase(category)) { 
                return true;
            }
            n = n.next;
        }
        return false;
    }

    public boolean equals(TariffList other) {
        //check if empty and check if same size
        if (other == null || this.size != other.size) {
            return false;
        }

        TariffNode n1 = this.head;
        TariffNode n2 = other.head;

        while (n1 != null && n2 != null) {
            if (!n1.getT().equals(n2.getT())) {
                return false;
            }

            n1 = n1.getNext();
            n2 = n2.getNext();
        }

    return (n1 == null && n2 == null);

    }

    public static String printList(TariffList list){
        TariffList.TariffNode current = list.head;
        StringBuilder result = new StringBuilder();

        while (current != null) {
            result.append(current.getT().toString()).append("\n");
            current = current.getNext();
        }

        return result.toString();
    }


    @Override
    public String evaluateTrade(double proposedTariff, double minimumTariff) {
        if (proposedTariff >= minimumTariff) {
            return "Accepted";
        } 

        else if (proposedTariff >= minimumTariff * 0.8) {
            return "Conditionally Accepted";
        } 
        else {
            return "Rejected";
        }
    }
}
