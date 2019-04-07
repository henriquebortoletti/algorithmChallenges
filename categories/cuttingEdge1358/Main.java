package cuttingEdge1358;

import java.io.IOException;
import java.util.*;

public class Main {

	static Scanner s;

	public static void main(String[] args) throws IOException {

		s = new Scanner(System.in);

		int numJanelas;
		while ((numJanelas = s.nextInt()) != 0) {
			List<Painel> paineis = new ArrayList<>();
			while (numJanelas-- > 0) {
				paineis.add(lerJanelas(numJanelas));
			}
			paineis.sort((p1,p2) ->		
					 p1.maxDistance - p2.maxDistance);
			cutting(paineis);
		}

	}

	public static Painel lerJanelas(int numJanelas) {
		int x1 = s.nextInt(), y1 = s.nextInt(), x2 = s.nextInt(), y2 = s.nextInt();
		int verticalDistance = Math.abs(Math.abs(x1) - Math.abs(x2));
		int horizontalDistance = Math.abs(Math.abs(y1) - Math.abs(y2));
		if (verticalDistance > horizontalDistance) {
			return new Painel(x1, y1, x2, y2, verticalDistance);
		}
		return new Painel(x1, y1, x2, y2, horizontalDistance);
	}

	public static void cutting(List<Painel> paineis) {
		List<String> resp = new ArrayList<>();
		while (paineis.size() > 1) {
			String aux = cut(paineis.get(0), paineis);
			if(aux != null) {
				resp.add(aux);	
			}
		}
		for(String aux: resp) {
			System.out.println(aux);
		}
	}

	public static String cut(Painel p, List<Painel> paineis) {
		Painel horizontal = horizontalCut(p, paineis);
		Painel vertical = verticalCut(p, paineis);
		Painel aux;
		if (horizontal.maxDistance > vertical.maxDistance) {
			aux = horizontal;
		} else {
			aux = vertical;
		}

		if (aux.doubleCut == false)
			paineis.remove(0);
		return aux.toString();
	}

	private static Painel horizontalCut(Painel p, List<Painel> paineis) {
		Painel aux = new Painel();
		aux.maxDistance = -1;
		for(int i =1;i<paineis.size();i++) {
			Painel iterator = paineis.get(i);
			if(iterator.lowerLeftY == p.upperRightY ) {
				int distance = Math.abs(Math.abs(iterator.lowerLeftX)-Math.abs(p.upperRightX));
				if(aux.maxDistance < distance) {
					aux.maxDistance = distance;
					aux.lowerLeftY = iterator.lowerLeftY;
					aux.upperRightY = aux.lowerLeftY;
					aux.lowerLeftX = iterator.lowerLeftX;
					aux.upperRightX = iterator.upperRightX;
				}else if(aux.maxDistance == distance && aux.lowerLeftX > iterator.lowerLeftX) {
					aux.lowerLeftY = iterator.lowerLeftY;
					aux.upperRightY = aux.lowerLeftY;
					aux.lowerLeftX = iterator.lowerLeftX;
					aux.upperRightX = iterator.upperRightX;
				}
			}
		}
		return aux.maxDistance == -1 ? null:aux;
	}

	private static Painel verticalCut(Painel p, List<Painel> paineis) {
		// TODO Auto-generated method stub
		return null;
	}

	public static class Painel {
		int lowerLeftX;
		int lowerLeftY;
		int upperRightX;
		int upperRightY;
	    int maxDistance;
		boolean doubleCut = false;

		Painel(int LFX, int LFY, int URX, int URY, int MD) {
			this.lowerLeftX = LFX;
			this.lowerLeftY = LFY;
			this.upperRightX = URX;
			this.upperRightY = URY;
			this.maxDistance = MD;
		}
		
		Painel(){
			
		}

		@Override
		public String toString() {
			return lowerLeftX + " " + lowerLeftY + " " + upperRightX + " " + upperRightY;
		}

	}

}
