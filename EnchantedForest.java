import java.util.ArrayList;
import java.util.List;

public class EnchantedForest {
	
	private TreeNode root, parentOfCurrent;

	public EnchantedForest() {}
	
	static class TreeNode{
		private int power;
		private String name;
		private boolean isRed;
		private TreeNode left, right, parent;
		
		TreeNode(int power, String name){
			this.power = power;
			this.name= name;
			this.isRed = true;
			left = right = parent = null;
		}		
		
		public int getPower() {
			return power;
		}
		
		public String getName() {
			return name;
		}
		
	}
	public void plantTree(int power, String name){
		TreeNode current = root;
		TreeNode z = new TreeNode(power, name);
		boolean isLeftChild = false;
		
		if(power < 0) {
			return;
		}
		
		if(current == null) {
			root = new TreeNode(power, name);
			root.isRed = false;
			return;
		}
		
		while(current != null) {
			boolean isLess = current.power < power;
			if(current.power == power) {
				break;
			}
			if(isLess && current.right == null) {
				parentOfCurrent = current;
				current.right = new TreeNode(power, name);
				isLeftChild = false;
			}else if(!isLess && current.left == null) {
				parentOfCurrent = current;
				current.left = new TreeNode(power, name);
				isLeftChild = true;
			}
			if(isLess && current.right != null) {
				current = current.right;
			}else if(!isLess && current.left != null) {
				current = current.left;
			}
		}
		z.parent = parentOfCurrent;
		if(isLeftChild) {
			parentOfCurrent.left = z;
			
		}else {
			parentOfCurrent.right = z;
		}
		
		while(z.parent != null && z != root && z.parent.isRed) {
			TreeNode y = uncle(z);
			
			if(y != null && y.isRed ) {
				z.parent.isRed = false;
				y.isRed = false;
				z.parent.parent.isRed = true;
				z = z.parent.parent;
			}else {
				boolean isCase2 = false;
				if(isRightChild(z) && isLeftChild(z.parent)) {
					isCase2 = true;
					z = z.parent;
					leftRotate(z);
				}else if(isLeftChild(z) && isRightChild(z.parent)) {
					isCase2 = true;
					z=z.parent;
					rightRotate(z);
				}
				
				z.parent.isRed = false;
				z.parent.parent.isRed = true;
				if(isLeftChild(z.parent)) {
					if(!isCase2) {
						rightRotate(z.parent.parent);
					}
				}else {
					if(!isCase2) {
						leftRotate(z.parent.parent);
					}
				}	
			}
		}
		root.isRed = false;
			
	}
	
	public boolean witherTree(int power) {
		    TreeNode z = findTree(power);
		    TreeNode node = new TreeNode(0, null);
		    node.isRed = false;
		    if (z == null) {
		        return false; 
		    }

		    TreeNode y = z;
		    TreeNode x = null;
		    boolean yOGColor = y.isRed;

		    if (z.left == null) {
		    	if(z.right!= null) {
		    		 x = z.right;
		    	}else {
		    		x = node;
		    	}
		    	   yOGColor = z.isRed;
		        if (z == root) {
		            root = x;
		        } else {
		            if (isLeftChild(z)) {
		                z.parent.left = x;
		            } else {
		                z.parent.right = x;
		            }
		            if(x != null) {
		            	x.parent = z.parent;
		            }
		        }
		    } else if (z.right == null) {
		        x = z.left;
		        yOGColor = z.isRed;
		        if (z == root) {
		            root = x;
		        } else {
		            if (isLeftChild(z)) {
		                z.parent.left = x;
		            } else {
		                z.parent.right = x;
		            }
		            if(x != null) {
		            	x.parent = z.parent;
		            }
		        }
		    } else {
		        y = z.right;
		        while (y.left != null) {
		            y = y.left;
		        }
		        
		       yOGColor = y.isRed;
		       if(y.right == null) {
		    	   x = node;
		       }else {
		    	   x = y.right;
		       }
		      }

		        if (y.parent != z) {
		            if (y.parent != null) {
		                if (isLeftChild(y)) {
		                    y.parent.left = x;
		                } else if(isRightChild(y)){
		                    y.parent.right = x;
		                }
		            }
		            if(x != null) {
		            	x.parent = y.parent;
		            }
		            y.right = z.right;
		            if (z.right != null) {
		                z.right.parent = y; 
		            }
		        }

		        if (z == root) {
		            root = y;
		        } else if (isLeftChild(z)) {
		        	if(z.parent != null)
		        		z.parent.left = y;
		        } else if (isRightChild(z)){
		        	if(z.parent != null) 
		        		z.parent.right = y;
		        	
		        }

		    y.parent = z.parent;
		    y.left = z.left;
		    if (z.left != null) { 
		         z.left.parent = y; 
		    }
		    y.isRed = z.isRed;		        

		    if (!yOGColor) {
		        fixDelete(x);
		    }
		    return true;
}
	
	public TreeNode findTree(int power) {
		TreeNode current = root;
		
		while(current != null) {
			if(current.power == power) {
				return current;
			}
			
			if(current.power < power) {
				current = current.right;
			}else {
				current = current.left;
			}
		}
		return null;	
	}
	
	
	public List<TreeNode> getTreesInRange(int lowerBound, int upperBound){
		List<TreeNode> list = new ArrayList<>();
		
		inOrderTraversal(root, lowerBound, upperBound, list);
		
		return list;
		
	}
	
	private void inOrderTraversal(TreeNode node, int low, int up, List<TreeNode> ls) {
		if(node == null) {
			return;
		}
		
		inOrderTraversal(node.left, low, up, ls);
		
		if(node.power > low && node.power < up) {
			ls.add(node);
		}
		
		inOrderTraversal(node.right, low, up, ls);
	}
		
	private boolean isLeftChild(TreeNode node) {
		return node.parent != null && node.parent.left == node;
	}
	
	private boolean isRightChild(TreeNode node) {
		return node.parent != null && node.parent.right == node;
	}
	
	private TreeNode uncle(TreeNode node){
		if(node.parent.parent == null) {
			return null;
		}
		if(isLeftChild(node.parent)) {
			return node.parent.parent.right;
		}
		if(isRightChild(node.parent)) {
			return node.parent.parent.left;
		}
		
		return null;
		
	}
	
	private void leftRotate(TreeNode x) {
		TreeNode y = x.right;
		
			x.right = y.left;
		
		
		if(y.left != null) {
			y.left.parent = x;
		}
		
		y.parent = x.parent;
		
		if(x.parent == null) {
			root = y;
		}else if(x == x.parent.left) {
			x.parent.left = y;
		}else {
			x.parent.right = y;
		}
		y.left = x;
		x.parent = y;
	}
	
	private void rightRotate(TreeNode y) {
		TreeNode x = y.left;
		
		y.left = x.right;
		
		if(x.right != null) {
			x.right.parent = y;
		}
		x.parent = y.parent;
		
		if(y.parent == null) {
			root = x;
		}else if(y == y.parent.right) {
			y.parent.right = x;
		}else {
			y.parent.left = x;
		}
		x.right = y;
		y.parent = x;
		
	}
	
	private void fixDelete(TreeNode x) {
		while(x != root && !x.isRed) {
			if(isLeftChild(x)) {
				TreeNode w = x.parent.right;
				if(w != null && w.isRed) {
					w.isRed = false;
					x.parent.isRed = true;
					leftRotate(x.parent);
					w = x.parent.right;	
				}
				
				if(!w.right.isRed && !w.left.isRed ) {
					w.isRed = true;
					x = x.parent;
				}
				else {
					if(!w.right.isRed) {
						if(w.left != null) {
							w.left.isRed = false;
						}
						w.isRed = true;
						rightRotate(w);
						w = x.parent.right;
					}
				w.isRed = x.parent.isRed;
				x.parent.isRed = false;
				if(w.right != null) {
					w.right.isRed = false;
				}
				leftRotate(x.parent);
				x = root;
			}
		}else {
				TreeNode w = x.parent.left;
				if(w != null && w.isRed) {
					w.isRed = false;
					x.parent.isRed = true;
					rightRotate(x.parent);
					w = x.parent.left;
				}
				
				if(!w.right.isRed && !w.left.isRed ) {
					w.isRed = true;
					x = x.parent;
				}else {
					if(!w.left.isRed) {
						if(w.right != null) {
							w.right.isRed = false;
						}
						w.isRed = true;
						leftRotate(w);
						w = x.parent.left;
					}
				
					w.isRed = x.parent.isRed;
					x.parent.isRed = false;
					if(w.left != null) {
						w.left.isRed = false;
					}
					rightRotate(x.parent);
					x = root;	
				}		
			}
		}
		x.isRed = false;
	}
}
