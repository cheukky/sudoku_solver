package sudoku;

import java.util.*;

public class Numbers {
	private int x;
	private int y;
	private List<Integer> poss = new ArrayList<>();
	
	public Numbers(int xpos, int ypos, List<Integer> possi){
		x = xpos;
		y = ypos;
		poss = possi;
	}
	
	public boolean positionReturn (int xpos, int ypos){
		
		return (x==xpos && y == ypos);
	}
	
	public int sizePoss (){
			
		return poss.size();
	}	

	public int returnFirst(){
		return poss.remove(0);
	}
	
	public String toString(){
		String str = "";
		str = "("+ x+ "," + y +")" + poss;
		return str;
	}
}
