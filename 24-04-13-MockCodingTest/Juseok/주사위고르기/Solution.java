package 주사위고르기;

import java.util.*;
class Solution {
    int n;
    int Mx = 0;
    boolean[] visited;
    int[][] dice;
    int[] answer;
    public int[] solution(int[][] dice) {
        this.dice = dice;
        n = dice.length;
        visited = new boolean[ n ];
        answer= new int[ n / 2 ];
        // Arrays.fill( answer, -1 );

        getCombinations( n, n/2, 0, 0 );

        return answer;
    }

    public void getCombinations( int n, int depthMx, int depth, int start ){
        if( depth == depthMx ){
            int[] A = new int[ n / 2 ];
            int[] B = new int[ n / 2 ];
            int Aidx = 0;
            int Bidx = 0;
            for( int i = 0; i < n; i ++ ){
                if( visited[ i ] ){
                    A[Aidx++] = i;
                }else{
                    B[Bidx++] = i;
                }
            }
            getDifferent( A, B );
            // System.out.println( Arrays.toString( A ) ); //배열 생성 체크

            return;
        }


        for( int i = start; i < n; i ++ ){
            if( !visited[ i ] ){
                // System.out.println( "start:" + start +"__"+ depth + "_" + i );
                visited[ i ] = true;
                getCombinations( n, depthMx, depth + 1, i + 1 );
                visited[ i ] = false;
            }
        }
    }

    public void getDifferent( int[] A, int[] B ){
        // System.out.println( Arrays.toString( A ) + " :: " + Arrays.toString( B ) ); 체크
        // dice 원소가 100 이하이면 100이라 가정했을 때 500이 Mx이므로 Integer
        // 합의 길이는 최대 6의 5승
        ArrayList< Integer > aLst = new ArrayList<>();
        ArrayList< Integer > bLst = new ArrayList<>();
        // inSort test
//         int[] testArr = new int[]{ 6,2,4,1 };

//         for( int test: testArr ){
//             InSort( aLst, test );
//             System.out.println( aLst.toString() );
//         }
        getPermutations( A, aLst, 0, 0 );
        getPermutations( B, bLst, 0, 0 );
        // System.out.println( aLst.toString() + " :: " + bLst.toString() );

        // Collections.sort( aLst );
        // Collections.sort( bLst );

        int win = getMx( aLst, bLst );
        if( Mx < win ){
            Mx = win;
            for( int i = 0; i < n/2; i ++){
                answer[ i ] = A[i] + 1;
            }
            // answer = A;
        }
        // System.out.println( Arrays.toString( A ) + "::" + win );
    }

    public int getMx( ArrayList<Integer> aLst, ArrayList<Integer> bLst ){
        int win = 0;
        for( int i = 0; i < aLst.size(); i ++ ){
            int target = aLst.get( i );
            if( bLst.get( 0 ) >= target ){
                continue;
            }
            int answer = 0;
            int lt = 0; int rt = aLst.size() - 1;
            while( lt <= rt ){
                int mid = ( lt + rt ) / 2;

                if( bLst.get( mid ) < target ){
                    answer = mid;
                    lt = mid + 1;
                }else{

                    rt = mid - 1;
                }
            }
            // System.out.println( bLst.toString() + "::" + answer + ":target = " + target );
            win += answer == 0 ? 0 : answer + 1;

        }

        return win;


    }


    public void InSort( ArrayList<Integer> lst, int target ){
        if( lst.size()==0){
            lst.add( target );
            return;
        }else if( lst.get( 0 ) > target ){
            lst.add( 0, target );
            return;
        } else if( lst.get( lst.size() - 1 ) < target ){
            lst.add( target );
            return;
        }

        int lt = 0; int rt = lst.size() - 1;
        int answer = -1;
        while( lt <= rt ){
            int mid = ( lt + rt ) / 2;

            if( lst.get( mid ) < target ){

                lt = mid + 1;
            }else{
                answer = mid;
                rt = mid - 1;
            }
        }

        lst.add( answer, target );


    }

    public void getPermutations( int[] combs, ArrayList< Integer > result, int depth, int sum ){

        if( depth == n/2 ){
            // result.add( sum );
            InSort( result, sum );
            return;
        }

        for( int idx: dice[ combs[ depth ] ] ){
            getPermutations( combs, result, depth + 1, sum + idx );
        }
    }




}