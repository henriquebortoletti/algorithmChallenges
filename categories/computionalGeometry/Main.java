package computionalGeometry;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

public class Main {
    
	static Scanner s;
    public static void main(String[] args) throws IOException {
        
        s = new Scanner(System.in);
        
        ArrayList<Painel> paineis = new ArrayList<>();
        int numJanelas;
        while((numJanelas=s.nextInt())!=0)
        {
        	paineis.add(lerJanelas(numJanelas));
        }
        
        
    }
    
    public static Painel lerJanelas(int numJanelas)
    {
    	Painel p;
    	int minX=10000,minY=10000,maxX=-100000,maxY=-100000;
    	while(numJanelas-- > 0)
    	{
	    	int x1=s.nextInt(),y1=s.nextInt(),x2=s.nextInt(),y2=s.nextInt();
	    	if(x1<=minX && y1 <= minY)
	    	{
	    		minX = x1;
	    		minY = y1;
	    	}
	    	if(x2>=maxX && y2>=maxY)
	    	{
	    		maxX = x2;
	    		maxY = y2;
	    	}
    	}
    	p = new Painel(Math.abs(maxX-minX),Math.abs(maxY-minY));
    	
    	return p;
    }
    
    
    public static class Painel
    {
    	LinkedList<Linha> linhas;
    	int comprimento,altura;
    	Linha proxCorte = null;
    	
    	Painel(int c,int a)
    	{
    		comprimento=c;
    		altura=a;
    	}
    	
    }
    
    public static class Linha
    {
    	int x,y,comp;
    	boolean vertical;
    	
    	Linha(int a,int b, int c, boolean v)
    	{
    		x=a;
    		y=b;
    		comp=c;
    		vertical=v;
    	}
    	
    }
    
}
