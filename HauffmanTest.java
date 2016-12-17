/**
 * File Name: HauffmanTest.java 
 * Test Hauffman encode and decode algorithms
 * 
 * @author Jagadeesh Vasudevamurthy
 * @year 2016
 */

public class HauffmanTest{
  private static final IntUtil u = new IntUtil();
  
  public static void test1(String s, boolean show, String dotfilename) {
    Hauffman h = new Hauffman(s, show, dotfilename);
    String d = h.decode();
    String f = h.encode(); 
    
    u.myassert(s.equals(f)) ;
    double sl = s.length() * 7 ;
    double dl = d.length();
    System.out.println("Original string cost = " + sl) ;
    System.out.println("Decoded  string cost = " + dl) ;
    double r = ((dl - sl)/sl) * 100 ;
    System.out.println("% reduction = " + (-r)) ;
    System.out.println();
  }
  
  public static void testbed() {
    boolean show = true;
    //test1("a",show,"C:\\work\\java\\fig\\1.dot");
    test1("a",show,"/Users/trucngo/1.dot");
    //test1("aba",show,"C:\\work\\java\\fig\\2.dot");
    test1("aba",show,"/Users/trucngo/2.dot");
    //test1("aaabbggggghhhhaaaggggaaaaa_+@#",show,"C:\\work\\java\\fig\\3.dot");
    test1("aaabbggggghhhhaaaggggaaaaa_+@#",show,"/Users/trucngo/3.dot");
    //test1("A quick brown fox jumps over the lazy dog",show,"C:\\work\\java\\fig\\4.dot");
    test1("A quick brown fox jumps over the lazy dog",show,"/Users/trucngo/4.dot");
    //test1("Pack my box with five dozen liquor jugs",show,"C:\\work\\java\\fig\\5.dot");
    test1("Pack my box with five dozen liquor jugs",show,"/Users/trucngo/5.dot");
    //test1("Long years ago we made a tryst with destiny, and now the time comes when we shall redeem our pledge, not wholly or in full measure, but very substantially.At the stroke of the midnight hour, when the world sleeps, India will awake to life and freedom. A moment comes, which comes but rarely in history, when we step out from the old to the new, when an age ends, and when the soul of a nation, long suppressed, finds utterance.",show,"C:\\work\\java\\fig\\6.dot");
    test1("Long years ago we made a tryst with destiny, and now the time comes when we shall redeem our pledge, not wholly or in full measure, but very substantially.At the stroke of the midnight hour, when the world sleeps, India will awake to life and freedom. A moment comes, which comes but rarely in history, when we step out from the old to the new, when an age ends, and when the soul of a nation, long suppressed, finds utterance.",show,"/Users/trucngo/6.dot");
    //test1("Baa, baa, black sheep, have you any wool?",show,"C:\\work\\java\\fig\\7.dot");
    test1("Baa, baa, black sheep, have you any wool?",show,"/Users/trucngo/7.dot") ;

    if (show) {
      System.out.println("===============  Done with Test1 ==================") ;
    } 
  }
  
  public static void main(String[] args) {
    System.out.println("HauffmanTest.java");
    testbed() ;
    System.out.println("All Hauffman Test passed. You are great. You should get an award");
  }
  
}