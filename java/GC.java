import org.junit.Test;

/**
 * Created by mengyue on 2017/2/6.
 */
public class GC {

	@Test
	public void test1(){


		{
			byte [] b = new byte[30*1024*1024];

			//b=null;也可以这样来让gc取回收
		}

		System.gc();

		System.out.println("first explict gc over");



/*			结果如下 ： gc 并没有去回收b这个东西 但如果在 {} 外 在声明一个变量的话 就回收了

			[GC (System.gc())  38594K->32184K(251392K), 0.0159735 secs]
			[Full GC (System.gc())  32184K->32029K(251392K), 0.0118379 secs]
			first explict gc over*/
	}

	@Test
	public void test2() {


		{
			byte[] b = new byte[30 * 1024 * 1024];

		}

		int a = 0;
		System.gc();

		System.out.println("first explict gc over");
	}

	/*

		可以看到在声明了 int a = 0 以后 gc明显的下降了 也就是回收了

	[GC (System.gc())  38584K->1440K(251392K), 0.0044433 secs]
	[Full GC (System.gc())  1440K->1310K(251392K), 0.0109610 secs]
	first explict gc over*/


	@Test
	public void test3() {


		{
			int c = 0 ;
			byte[] b = new byte[30 * 1024 * 1024];

		}

		int a = 1;

		System.gc();

		System.out.println("first explict gc over");
	}


	/*
	因为a 复用了 c 的字 所以 b的空间并没有被gc所回收（这里的字 暂时理解为空间吧）

					[GC (System.gc())  38591K->32136K(251392K), 0.0245782 secs]
					[Full GC (System.gc())  32136K->32013K(251392K), 0.0103142 secs]
	first explict gc over
*/


	@Test
	public void test4() {


		{
			int c = 0 ;
			byte[] b = new byte[30 * 1024 * 1024];

		}

		int a = 1;
		int d = 2;
		System.gc();

		System.out.println("first explict gc over");
	}

	/*

	a 和 d都复用了c和b 所以这个量会下来

	[GC (System.gc())  38591K->1376K(251392K), 0.0037891 secs]
					[Full GC (System.gc())  1376K->1294K(251392K), 0.0141798 secs]
	first explict gc over*/


	@Test
	public void test5(){

		byte [] b1 = new byte[1024*1024/2];
		byte [] b2 = new byte[1024*1024*8];
		b2=null;
		b2=new byte[1024*1024*8];
		System.gc();

		/*
		不加System.gc()的情况下

		Heap
		PSYoungGen      total 18432K, used 13971K [0x00000000fec00000, 0x0000000100000000, 0x0000000100000000)
		eden space 16384K, 85% used [0x00000000fec00000,0x00000000ff9a4fb8,0x00000000ffc00000)
		from space 2048K, 0% used [0x00000000ffe00000,0x00000000ffe00000,0x0000000100000000)
		to   space 2048K, 0% used [0x00000000ffc00000,0x00000000ffc00000,0x00000000ffe00000)
		ParOldGen       total 20480K, used 8192K [0x00000000fd800000, 0x00000000fec00000, 0x00000000fec00000)
		object space 20480K, 40% used [0x00000000fd800000,0x00000000fe000010,0x00000000fec00000)
		Metaspace       used 5035K, capacity 5264K, committed 5504K, reserved 1056768K
		class space    used 593K, capacity 627K, committed 640K, reserved 1048576K*/



		/*

		加了System.gc()的情况下
		[GC (System.gc()) [PSYoungGen: 13947K->1912K(18432K)] 22139K->10112K(38912K), 0.0057735 secs] [Times: user=0.00 sys=0.00, real=0.01 secs]
[Full GC (System.gc()) [PSYoungGen: 1912K->0K(18432K)] [ParOldGen: 8200K->10018K(20480K)] 10112K->10018K(38912K), [Metaspace: 5026K->5026K(1056768K)], 0.0354225 secs] [Times: user=0.08 sys=0.00, real=0.04 secs]
		Heap
		PSYoungGen      total 18432K, used 327K [0x00000000fec00000, 0x0000000100000000, 0x0000000100000000)
		eden space 16384K, 2% used [0x00000000fec00000,0x00000000fec51ee0,0x00000000ffc00000)
		from space 2048K, 0% used [0x00000000ffc00000,0x00000000ffc00000,0x00000000ffe00000)
		to   space 2048K, 0% used [0x00000000ffe00000,0x00000000ffe00000,0x0000000100000000)
		ParOldGen       total 20480K, used 10018K [0x00000000fd800000, 0x00000000fec00000, 0x00000000fec00000)
		object space 20480K, 48% used [0x00000000fd800000,0x00000000fe1c8800,0x00000000fec00000)
		Metaspace       used 5040K, capacity 5264K, committed 5504K, reserved 1056768K
		class space    used 593K, capacity 627K, committed 640K, reserved 1048576K*/
	}


}
