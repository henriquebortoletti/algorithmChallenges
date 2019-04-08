import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

public class Main {
    
	public static final boolean VERTICAL=true,HORIZONTAL=false;
	static Scanner s;
	static HashMap<Integer,ArrayList<Linha>> linhasVerticais = new HashMap<>();
	static HashMap<Integer,ArrayList<Linha>> linhasHorizontais = new HashMap<>();
    public static void main(String[] args) throws IOException {
        
        s = new Scanner(System.in);
        
        ArrayList<Painel> paineis = new ArrayList<>();
        int numJanelas;
        StringBuilder st = new StringBuilder();
        while((numJanelas=s.nextInt())!=0)
        {
        	paineis.add(lerJanelas(numJanelas));
        	while(!paineis.isEmpty())
        	{
        		Painel escolhido= null;
        		for(Painel p : paineis)if(escolhido==null || escolhido.proxCorte.compareTo(p.proxCorte)>0)escolhido = p;
        		escolhido.realizarCorte(paineis);
        		st.append(escolhido.proxCorte+"\n");
        	}
        	linhasVerticais.clear();
        	linhasHorizontais.clear();
        	st.append("\n");
        }
        System.out.print(st.toString());
        
        
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
	    	
	    	Linha[] lados = {new Linha(x1,y1,y2-y1,VERTICAL),
	    				   new Linha(x1,y1,x2-x1,HORIZONTAL),
	    				   new Linha(x1,y2,x2-x1,HORIZONTAL),
	    				   new Linha(x2,y1,y2-y1,VERTICAL)};
	    	for(Linha l: lados)adicionarLinha(l);
	    	
    	}
    	p = new Painel(minX,minY,maxX,maxY);
    	p.encontrarProxCorte();
    	return p;
    }
    
    public static void adicionarLinha(Linha lin)
    {
    	ArrayList<Linha> linhas;
    	if(lin.vertical)
    	{
    		if(!linhasVerticais.containsKey(lin.x))linhasVerticais.put(lin.x,new ArrayList<Linha>());
    		linhas = linhasVerticais.get(lin.x);
    		for(int i=0;i<linhas.size();i++)
    		{
    			Linha a = linhas.get(i);
    			if((lin.y <= a.y && lin.y+lin.comp >= a.y) || ( lin.y>=a.y && a.y+a.comp >=lin.y))
    			{
    				int aux = Math.min(lin.y, a.y);
    				lin.comp = Math.max(lin.y+lin.comp, a.y+a.comp)-aux;
    				lin.y = aux;
    				linhas.remove(i--);
    			}
    		}
    		linhas.add(lin);
    	}
    	else
    	{
    		if(!linhasHorizontais.containsKey(lin.y))linhasHorizontais.put(lin.y,new ArrayList<Linha>());
    		linhas = linhasHorizontais.get(lin.y);
    		for(int i=0;i<linhas.size();i++)
    		{
    			Linha a = linhas.get(i);
    			if((lin.x <= a.x && lin.x+lin.comp >= a.x) || ( lin.x>=a.x && a.x+a.comp >=lin.x))
    			{
    				int aux = Math.min(lin.x, a.x);
    				lin.comp = Math.max(lin.x+lin.comp, a.x+a.comp)-aux;
    				lin.x = aux;
    				linhas.remove(i--);
    			}
    		}
    		linhas.add(lin);
    	}
    }
    
    
    public static class Painel
    {
    	int x1,y1,x2,y2;
    	Linha proxCorte = null;
    	
    	Painel(int xx1,int yy1,int xx2,int yy2)
    	{
    		x1=xx1;
    		x2=xx2;
    		y1=yy1;
    		y2=yy2;
    	}
    	
    	public void encontrarProxCorte()
    	{
    		for(int x = x1+1;x<x2;x++)
    		{
    			if(linhasVerticais.containsKey(x))for(Linha candidata: linhasVerticais.get(x))
    			{
	    			if(candidata.estaNoPainel(this) && candidata.comp==altura() && (proxCorte==null || candidata.compareTo(proxCorte)<0))
	    				proxCorte = candidata;
    			}
    		}
    		for(int y = y1+1;y<y2;y++)
    		{
    			if(linhasHorizontais.containsKey(y))for(Linha candidata: linhasHorizontais.get(y))
    			{
	    			if(candidata.estaNoPainel(this) && candidata.comp==comp() && (proxCorte==null || candidata.compareTo(proxCorte)<0))
	    				proxCorte = candidata;
    			}
    		}
    	}
    	
    	public void realizarCorte(ArrayList<Painel> paineis)
    	{
    		Painel prox1,prox2;
    		if(proxCorte.vertical)
    		{
    			for(int y = y1+1;y<y2;y++)
        		{
        			if(linhasHorizontais.containsKey(y))
        			{
        				ArrayList<Linha> lins = linhasHorizontais.get(y);
        				for(int i=0;i<lins.size();i++)
        				{
        					Linha l = lins.get(i);
        					if(l.estaNoPainel(this) && l.x < proxCorte.x && l.x+l.comp > proxCorte.x)
        					{
        						lins.add(new Linha(l.x,l.y,proxCorte.x-l.x,HORIZONTAL));
        						lins.add(new Linha(proxCorte.x,l.y,l.comp-(proxCorte.x-l.x),HORIZONTAL));
        						lins.remove(l);
        						break;
        					}
        				}
        			}
        		}
    			prox1 = new Painel(x1,y1,proxCorte.x,y2);
    			prox2 = new Painel(proxCorte.x,y1,x2,y2);
    		}
    		else
    		{
    			for(int x = x1+1;x<x2;x++)
        		{
        			if(linhasVerticais.containsKey(x))
        			{
        				ArrayList<Linha> lins = linhasVerticais.get(x);
        				for(int i=0;i<lins.size();i++)
        				{
        					Linha l = lins.get(i);
        					if(l.estaNoPainel(this) && l.y < proxCorte.y && l.y+l.comp > proxCorte.y)
        					{
        						lins.add(new Linha(x,l.y,proxCorte.y-l.y,VERTICAL));
        						lins.add(new Linha(x,proxCorte.y,l.comp-(proxCorte.y-l.y),VERTICAL));
        						lins.remove(l);
        						break;
        					}
        				}
        			}
        		}
    			prox1 = new Painel(x1,y1,x2,proxCorte.y);
    			prox2 = new Painel(x1,proxCorte.y,x2,y2);
    		}
    		paineis.remove(this);
    		prox1.encontrarProxCorte();
    		prox2.encontrarProxCorte();
    		if(prox1.proxCorte!=null)paineis.add(prox1);
    		if(prox2.proxCorte!=null)paineis.add(prox2);
    	}
    	
    	
    	public int comp()
    	{
    		return x2-x1;
    	}
    	public int altura()
    	{
    		return y2-y1;
    	}
    	
    	@Override
    	public String toString()
    	{
    		return x1+ " "+y1+ " "+x2+ " "+y2;
    	}
    	
    }
    
    public static class Linha implements Comparable<Linha>
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
    	
    	public boolean estaNoPainel(Painel p)
    	{
    		return (x>=p.x1 && x<p.x2 && y>=p.y1 && y<p.y2);
    	}

		@Override
		public int compareTo(Linha arg0) {
			if(x!=arg0.x)return Integer.compare(x, arg0.x);
			return Integer.compare(y, arg0.y);
		}
    	
		@Override
		public String toString()
		{
			if(vertical)return ""+x+" "+y+ " "+x+" "+(y+comp);
			else return ""+x+" "+y+ " "+(x+comp)+" "+y;
		}
    	
    	
    	
    }
    
}
