package 키패드누르기;

import java.util.*;
class Solution {
    HashMap< Integer, String > mapL = new HashMap<>();
    HashMap< Integer, String > mapR = new HashMap<>();
    HashMap< Integer, int[] > mapPos = new HashMap<>();
    String answer = "";
    int[] left, right;
    public String solution(int[] numbers, String hand) {
        init();

        left = new int[]{3,0};
        right = new int[]{ 3,2 };

        for( int next: numbers ){
            // System.out.println( "depth: "+ next + Arrays.toString( left ) + "::" + Arrays.toString( right ) );
            if( mapL.containsKey( next ) ){
                goLeft( next );
            }else if( mapR.containsKey( next ) ){
                goRight( next );
            }else{
                //만약 두 엄지손가락의 거리가 같다면, 오른손잡이는 오른손 엄지손가락, 왼손잡이는 왼손 엄지손가락을 사용합니다.
                int disL = getDist( left, mapPos.get( next ) );
                int disR = getDist( right, mapPos.get( next ) );

                if( disL < disR ){
                    goLeft( next );
                }else if( disL > disR ){
                    goRight( next );
                }else{

                    if( hand.equals( "left" ) ){
                        goLeft( next );
                    }else{
                        goRight( next );
                    }
                }
            }
        }


        return answer;
    }
    public void goLeft( int next ){
        left = mapPos.get( next );
        answer += "L";
    }

    public void goRight( int next ){
        right = mapPos.get( next );
        answer += "R";
    }

    public int getDist( int[] a, int[] b ){
        return Math.abs( a[ 0 ] - b[ 0 ] ) + Math.abs( a[ 1 ] - b[ 1 ] );
    }



    public void init(){
        mapL.put( 1, "L" );
        mapL.put( 4, "L" );
        mapL.put( 7, "L" );

        mapR.put( 3, "R" );
        mapR.put( 6, "R" );
        mapR.put( 9, "R" );

        mapPos.put( 1, new int[]{ 0, 0 });
        mapPos.put( 2, new int[]{ 0, 1 });
        mapPos.put( 3, new int[]{ 0, 2 });
        mapPos.put( 4, new int[]{ 1, 0 });
        mapPos.put( 5, new int[]{ 1, 1 });
        mapPos.put( 6, new int[]{ 1, 2 });
        mapPos.put( 7, new int[]{ 2, 0 });
        mapPos.put( 8, new int[]{ 2, 1 });
        mapPos.put( 9, new int[]{ 2, 2 });
        mapPos.put( 0, new int[]{ 3, 1 });

    }
}





