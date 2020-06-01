package lab7;

import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ForkJoinPool;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class lab7 {
	public static void main(String[] args) {
    	Scanner sc=new Scanner(System.in);
    	int threadsCount =sc.nextInt();
    	float startTime=System.currentTimeMillis();
    	Point[] points=initPoint();
    	Point[] pointInCircle=filterPoint(points,threadsCount);
    	float endTime=System.currentTimeMillis();
    	printPi(points,pointInCircle);
    	printIterations(points);
    	pritbtThreadsCount(threadsCount);
    	printTimeResult(startTime, endTime);
	}
	
	public static void printPi(Point[] points,Point[] pointInCircle) {
		System.out.print("Pi is "+4.0f*((float)pointInCircle.length/(float)points.length));
	}

	public static void pritbtThreadsCount(int threadsCount) {
		System.out.print("\n");
		System.out.print("Threads "+threadsCount);
	}

	public static void printIterations(Point[] points) {
		System.out.print("\n");
		System.out.print("Iterations "+points.length);
	}
	
	public static void printTimeResult(float startTime, float endTime) {
		System.out.print("Time: "+(endTime-startTime)+"ms");
	}
	
	public static Point[] initPoint() {
    	Random r =new Random();
    	Supplier<Point> supplier = () -> new Point(r.nextFloat(), r.nextFloat());
    	return Stream.generate(supplier).limit(10000000).toArray(Point[]::new);
	}
	
	public static Point[] filterPoint(Point[] points, int threadsCount) {   
		Point[] filteredPoints=null;
		ForkJoinPool forkJoinPool = null;


		try {
		    forkJoinPool = new ForkJoinPool(threadsCount);
		    filteredPoints = forkJoinPool.submit(() ->
		    	Arrays.stream(points).parallel()
		    			.filter(point->Math.sqrt(point.x*point.x+point.y*point.y)<=1)
		    			.toArray(Point[]::new)
		    ).get();
		} catch (Exception e) {
		    throw new RuntimeException(e);
		} finally {
		    if (forkJoinPool != null) {
		        forkJoinPool.shutdown();
		    }
		}
		return filteredPoints;
	}
	
	public static class Point{
		private float x;
		private float y;
		
		public Point(float x,float y) {
			this.x=x;
			this.y=y;
		}

		public float getX() {
			return x;
		}

		public float getY() {
			return y;
		}
	}

		

}
