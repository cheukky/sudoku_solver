package sudoku;




import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class sudoku {
	
	private static int[][] sudokuMap = new int[9][9];
	private static List<Integer> base = new ArrayList<>(Arrays.asList(1,2,3,4,5,6,7,8,9));
	private static List<List<Integer>> rows = new ArrayList<>(9);
	private static List<List<Integer>> cols = new ArrayList<>(9);
	private static List<List<Integer>> boxes = new ArrayList<>(9);
	private static List<List<Numbers>> sudokuPoss = new ArrayList<>(9);
	private static boolean changed;
	private static List<String> map;
	private static boolean isDone;
	private static int minNotDone;
	
	public static void main(String[] args) {
		System.out.println("Sudoku solver");
		changed = false;
		isDone = false;
		getInput();

		for(int i = 0 ; i<9; i++){
			rows.add(new ArrayList<>(base));
			cols.add(new ArrayList<>(base));
			boxes.add(new ArrayList<>(base));
		}
		checkRowsColsBoxes();

		while(!isDone){
			minNotDone = 10;
			changed = false;
			isDone = true;
			checkPossibilities();
			if(!changed){
				toggle();
			}
		}
		redraw();
		drawSudoku();
		try {
			printtoFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void printtoFile() throws IOException{
		Files.write(Paths.get("solution.txt"), map, StandardCharsets.UTF_8);
	}
	
	public static void toggle(){
		for(int j = 0; j < 9 ; j++){
	    	for(int i = 0; i < 9 ; i++){
	    		if((sudokuMap[j][i] == 0)){	
	    			Numbers l;
	    			int o;
	    			if(minNotDone == (l = sudokuPoss.get(j).get(i)).sizePoss()){
	    				
	    				sudokuMap[j][i]= (o =l.returnFirst());
	    				rows.get(j).remove((Integer)o);
		    			cols.get(i).remove((Integer)o);
		    			boxes.get(checkRegion(j,i)).remove((Integer)o);
		    			return;
	    			}
	    			
	    	 	}
	    	}
		}
	}
	
	public static int checkRegion(int i, int j){
		int region = -1;
		int rowReg = i/3;
		int colReg = j/3;
		if(rowReg == 0){
			if(colReg == 0){
				region = 0;
			}else if(colReg == 1){
				region = 1;
			}else{
				region = 2;
			}
		}else if(rowReg == 1){
			if(colReg == 0){
				region = 3;
			}else if(colReg == 1){
				region = 4;
			}else{
				region = 5;
			}
		}else{
			if(colReg == 0){
				region = 6;
			}else if(colReg == 1){
				region = 7;
			}else{
				region = 8;
			}
		}
		
		return region;
	}
	
	public static void checkRowsColsBoxes(){
		for(int j = 0; j < 9 ; j++){			
	    	for(int i = 0; i < 9 ; i++){
	    		if((sudokuMap[j][i] != 0)){
		    		rows.get(j).remove(Integer.valueOf(sudokuMap[j][i]));
		    		cols.get(i).remove(Integer.valueOf(sudokuMap[j][i]));
		    		boxes.get(checkRegion(j,i)).remove(Integer.valueOf(sudokuMap[j][i]));
		    	}
	    		
	    	}
		}
		
	}
	
	public static void checkPossibilities(){
		sudokuPoss = new ArrayList<>(9);
		for(int j = 0; j < 9 ; j++){
			sudokuPoss.add(new ArrayList<Numbers>());
	    	for(int i = 0; i < 9 ; i++){
	    		List<Integer> poss = new ArrayList <>();
	    		if((sudokuMap[j][i] == 0)){	    			
	    			for (int k = 0; k < rows.get(j).size(); k++) {
	    				int maybe = rows.get(j).get(k);
	    				if(cols.get(i).contains(maybe) && boxes.get(checkRegion(j,i)).contains(maybe)){
	    					poss.add(maybe);
	    				}
	    			}
	    			isDone = false;
	    			if(poss.size()< minNotDone){
	    				minNotDone = poss.size();
	    			}
	    	 	}
	    		
	    		if(poss.size()==1){
	    			sudokuMap[j][i]= poss.get(0);
	    			rows.get(j).remove((Integer)poss.get(0));
	    			cols.get(i).remove((Integer)poss.get(0));
	    			boxes.get(checkRegion(j,i)).remove((Integer)poss.remove(0));
	    			changed = true;
	    		}
	    		sudokuPoss.get(j).add(new Numbers(j,i,poss));
	    	}
		}
	}
	
	public static void getInput(){
        String line = null;
        int row = 0;
        try {

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
            	      new FileInputStream("sudoku.txt"), "UTF-8"));
            bufferedReader.mark(1);
            if (bufferedReader.read() != 0xFEFF)
              bufferedReader.reset();
            while((line = bufferedReader.readLine()) != null && row < 10) {
            	String[] lineSplit = new String[10];
            	lineSplit = line.split(",");
            	for(int i = 0; i < 9 ; i++){
            		sudokuMap[row][i]= Integer.parseInt(lineSplit[i]);
            	}
                row ++;
            }   

            // Always close files.
            bufferedReader.close();         
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                "Unable to open file 'sudoku.txt'");                
        }
        catch(IOException ex) {
            System.out.println(
                "Error reading file 'sudoku.txt'");       
        }
    }
	
	public static void drawSudoku(){
		map = new ArrayList<>();
		String k = "";
		map.add(" -----------------------");
		for(int j = 0; j < 9 ; j++){
			k = "" + "| ";
	    	for(int i = 0; i < 9 ; i++){
	    		k = k + sudokuMap[j][i]+" ";
	    		if((i+1)%3 == 0){
	    			k = k + "| ";
		    	}
	    	}
	    	map.add(k);
	    	if((j+1)%3 == 0){
	    		map.add(" -----------------------");
	    	}
		}
	}
	
	public static void redraw(){
		System.out.print(" -----------------------");
		System.out.println();
		for(int j = 0; j < 9 ; j++){
			System.out.print("| ");
			
	    	for(int i = 0; i < 9 ; i++){
	    		if(sudokuMap[j][i] != 0){
	    			System.out.print(sudokuMap[j][i]+" ");
	    		}else {
	    			
	    			System.out.print(sudokuPoss.get(j).get(i).toString()+" ");
	    		}	    		
	    		if((i+1)%3 == 0){
		    		System.out.print("| ");
		    	}
	    		
	    	}
	    	System.out.println();
	    	if((j+1)%3 == 0){
	    		System.out.print(" -----------------------");
	    		System.out.println();
	    	}
		}		
	}

}
