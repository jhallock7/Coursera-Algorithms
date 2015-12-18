


public class KdTree {
	private Node root;
	private int count;
	
	
	private static class Node {
		   private Point2D p;      // the point
		   private RectHV rect;    // the axis-aligned rectangle corresponding to this node
		   private Node lb;        // the left/bottom subtree
		   private Node rt;        // the right/top subtree
		   
		   public Node(Point2D p, double xmin, double ymin, double xmax, double ymax) {
			   this.p = p;
			   this.rect = new RectHV(xmin, ymin, xmax, ymax);
		   }
		}
	
	// is the set empty?
	public boolean isEmpty() {
		if (root == null) { return true; }
		else return false;
	}
	
	// number of points in the set
	public int size() {	return count; }
	
	/************************************************************
	 CONTAINS method and helper methods GET
	 ************************************************************/
	public boolean contains(Point2D p) {
		return get(p) != null;
	    }
	//Z = 1 = vertical; Z = 2 = horizontal
	private Point2D get(Point2D p) {
        return get(root, p, 1);
    }
	
    private Point2D get(Node x, Point2D p, int z) {
        if (x == null) return null;
        
        if (z == 1) {
	       
	        double cmp = (p.x() - x.p.x());
	     
	        if      (cmp < 0) 			return get(x.lb, p, 2);
	        else if (cmp > 0) 			return get(x.rt, p, 2);
	        else if (p.y() != x.p.y()) 	return get(x.rt, p, 2);
	        else              			return x.p;
        }
        
        if (z == 2) {
 	       
	        double cmp = (p.y() - x.p.y());
	     
	        if      (cmp < 0) 			return get(x.lb, p, 1);
	        else if (cmp > 0) 			return get(x.rt, p, 1);
	        else if (p.x() != x.p.x()) 	return get(x.rt, p, 1);
	        else              			return x.p;
        }
        
        else return null;
        
    }
	
	
    
    /***********************************************************************
     *  Add the point to the set (if it is not already in the set)
     ***********************************************************************/
     public void insert(Point2D p) {
    	 RectHV unit = new RectHV(0, 0, 1, 1);
         root = put(root, p, 1, 0, unit);
         count++;
     }
     
     //Z = 1 = vertical; Z = 2 = horizontal
     private Node put(Node x, Point2D p, int z, double v, RectHV rect) {
         
         if (z == 1) {
        	 if (x == null) {
         		if (p.y() < v)	{ return new Node(p, rect.xmin(),			rect.ymin(), 	rect.xmax(), 		v); }
         		else 			{ return new Node(p, rect.xmin(), 			v,				rect.xmax(), 		rect.ymax()); }
         	}
 	        double cmp = (p.x() - x.p.x());
 	        if (cmp < 0) 		x.lb = put(x.lb, p, 2, x.p.x(), x.rect);
 	        else 			 	x.rt = put(x.rt, p, 2,  x.p.x(), x.rect);
 	        return x;
         }
         
         else if (z == 2) { 
        	if (x == null) {
        		if (p.x() < v)	{ return new Node(p, rect.xmin(),	rect.ymin(), 	v, 					rect.ymax()); }
        		else 			{ return new Node(p, v, 			rect.ymin(),	rect.xmax(), 		rect.ymax()); }
        	}
  	        double cmp = (p.y() - x.p.y());
  	        if (cmp < 0) 		x.lb = put(x.lb, p, 1, x.p.y(), x.rect);
  	        else 				x.rt = put(x.rt, p, 1, x.p.y(), x.rect);
  	        return x;
          }
         else return null;
     }
    
    
    
	/************************************************************
	 
	 ************************************************************/
    
     
     // all points that are inside the rectangle
     public Iterable<Point2D> range(RectHV rect){
    	 Stack<Point2D> stack = new Stack<Point2D>();
    	 look(root, stack, rect);
    	 return stack;
    	 
     }
     
    
     private void look(Node node, Stack<Point2D> stack, RectHV rect) {
    	 if (node.lb != null) {
    		 if (node.lb.rect.intersects(rect)) { look(node.lb, stack, rect); }
    	 }
    	 
    	 if (node.rt != null) {
    		 if (node.rt.rect.intersects(rect)) { look(node.rt, stack, rect); }
    	 }
    	 
    	 if ( rect.contains(node.p) )  { stack.push(node.p); }
    	 //return;
    	 
     }
     
     
     

     
     public Point2D nearest(Point2D p) {
    	if (root == null) return null;
    	
    	return near(p, root, root.p, root.p.distanceSquaredTo(p));
     }
     
     
     private Point2D near(Point2D p, Node node, Point2D nearest, double dtSq) {
    	 
    	 if (node.rect.distanceSquaredTo(p) > dtSq) return nearest;
    	 
    	 if (node.p.distanceSquaredTo(p) < dtSq) {
    		 nearest = node.p;
    		 dtSq = node.p.distanceSquaredTo(p);
    	 }
 
    	 double lbDist;
    	 double rtDist;
    	 boolean lbPos = false;
    	 boolean rtPos = false;
    	 boolean lbSide = false;
    	 boolean rtSide = false;
    	 if (node.lb != null) {
    		 lbDist = node.lb.rect.distanceSquaredTo(p);
    		 if (lbDist < dtSq) { lbPos = true; }
    		 if (node.lb.rect.contains(p)) { lbSide = true; }
    	 }
    	 if (node.rt != null) {
    		 rtDist = node.rt.rect.distanceSquaredTo(p);
    		 if (rtDist < dtSq) { rtPos = true; }
    		 if (node.rt.rect.contains(p)) { rtSide = true; }
    	 }
    	 
    	 if (lbPos  &&  rtPos) {
    		 if (lbSide) {
    			 nearest = near(p, node.lb, nearest, dtSq);
    			 nearest = near(p, node.rt, nearest, dtSq);
    		 }
    		 else {
    			 nearest = near(p, node.rt, nearest, dtSq);
    			 nearest = near(p, node.lb, nearest, dtSq);
    		 }
    	 }
    	 if (lbPos  && !rtPos) { near(p, node.lb, nearest, dtSq); }
    	 if (!lbPos &&  rtPos) { near(p, node.rt, nearest, dtSq); }
    	 if (!lbPos && !rtPos) {}
    	 
    	 return nearest;
     }
     
     
     public void draw() {}
     
     
     
     
     
     
     
     
     
     
     
}










