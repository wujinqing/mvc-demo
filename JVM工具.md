# JVM工具
> 1.jmap：查看JVM的堆情况

> 2.jcmd：查看JVM的堆情况、启动参数、程堆栈信息

> 3.jstat：JVM统计监测工具

> 4.jstack：查看某个Java进程内的线程堆栈信息

> 5.jps 查看java进程


## 常用场景
> 1.查看新生代、老年代内存：jmap -heap pid

> 2.查看新生代、老年代内存、元空间大小、CompressedClassSpaceSize大小：jcmd pid GC.heap_info 

> 3.查看启动脚本的命令行参数：jcmd pid VM.command_line

> 4.查看新生代、老年代内存、元空间大小、CompressedClassSpaceSize大小及使用情况：jstat -gc 265205

> 5.查看新生代、老年代内存、元空间使用百分比: jstat -gcutil  272383

> 6.导出线程堆栈信息：jstack 596426 > 596426.tdump


## jmap
> jmap option pid

### option

|选项|说明|示例|
|---|---|---|
|-heap|查看JVM堆信息，只能查看新生代、老年代的内存使用情况|jmap -heap 260525|
|-histo|查看JVM中类的实例数和占用大小|jmap -histo:live 260525|


#### 示例1
> jmap -heap 260525

``` 
Attaching to process ID 260525, please wait...
Debugger attached successfully.
Server compiler detected.
JVM version is 25.191-b12

using parallel threads in the new generation.
using thread-local object allocation.
Concurrent Mark-Sweep GC

Heap Configuration:
   MinHeapFreeRatio         = 40 --当堆空间空闲率小于40%将触发扩容
   MaxHeapFreeRatio         = 70 --当堆空间空闲率超过70%将触发缩容
   MaxHeapSize              = 536870912 (512.0MB) --最大堆空间大小
   NewSize                  = 104857600 (100.0MB) --新生代大小
   MaxNewSize               = 314572800 (300.0MB) --最大新生代大小
   OldSize                  = 209715200 (200.0MB) --老年代大小
   NewRatio                 = 2 --配置年轻代与老年代在堆结构的占比, 新生代占1,老年代占2,年轻代占整个堆的1/3
   SurvivorRatio            = 8 --新生代中可以分为伊甸园区（Eden区），From Survivor 区 （S0区）和 To Survivor 区 （S1区）。 占用的空间分别默认为 8：1：1
   MetaspaceSize            = 134217728 (128.0MB) --元空间大小(Class/方法)
   CompressedClassSpaceSize = 260046848 (248.0MB) --
   MaxMetaspaceSize         = 268435456 (256.0MB) --最大元空间大小
   G1HeapRegionSize         = 0 (0.0MB)

Heap Usage:
New Generation (Eden + 1 Survivor Space):
   capacity = 94371840 (90.0MB)
   used     = 52031376 (49.62098693847656MB)
   free     = 42340464 (40.37901306152344MB)
   55.134429931640625% used
Eden Space: --Eden区
   capacity = 83886080 (80.0MB) --容量
   used     = 44035920 (41.99592590332031MB)--已使用
   free     = 39850160 (38.00407409667969MB)--空闲
   52.49490737915039% used--已使用百分比
From Space: --Survivor 1
   capacity = 10485760 (10.0MB)
   used     = 7995456 (7.62506103515625MB)
   free     = 2490304 (2.37493896484375MB)
   76.2506103515625% used
To Space: --Survivor2
   capacity = 10485760 (10.0MB)
   used     = 0 (0.0MB)
   free     = 10485760 (10.0MB)
   0.0% used
concurrent mark-sweep generation: --永久代内存
   capacity = 209715200 (200.0MB)
   used     = 122466376 (116.79303741455078MB)
   free     = 87248824 (83.20696258544922MB)
   58.39651870727539% used

61637 interned Strings occupying 6328984 bytes.
```

#### 实例2
> jmap -histo:live 260525

> jmap -histo:live 260525 > tp.txt

> grep  'com.jin' tp.txt  | head -n 10  
``` 
 num     #instances         #bytes  class name
----------------------------------------------
   1:        363539       51910680  [C
   2:         24383       10797248  [B
   3:        117463       10336744  java.lang.reflect.Method
   4:        333925        8014200  java.lang.String
   5:         20591        6393128  [I
   6:        194369        6219808  java.util.concurrent.ConcurrentHashMap$Node
   7:        109920        5652176  [Ljava.lang.Object;
   8:        103860        4154400  java.util.LinkedHashMap$Entry
   9:           434        4059672  [J
  10:         33959        3699800  java.lang.Class
  11:        140408        2840552  [Ljava.lang.Class;
```


## jcmd

### 常用命令

|选项|说明|示例|
|---|---|---|
|Thread.print|查看线程堆栈信息，和jstack类似|jcmd 257587  Thread.print|
|GC.heap_info|查看堆内存信息，和jmap -heap类似|jcmd 257587  GC.heap_info|
|GC.heap_dump|导出堆栈信息|jcmd 988285 GC.heap_dump  1988285.hprof|
|VM.command_line|查看启动脚本的命令行参数|jcmd 257587   VM.command_line|


``` 
The following commands are available:
JFR.stop
JFR.start
JFR.dump
JFR.check
VM.native_memory
VM.check_commercial_features
VM.unlock_commercial_features
ManagementAgent.stop
ManagementAgent.start_local
ManagementAgent.start
VM.classloader_stats
GC.rotate_log
Thread.print
GC.class_stats
GC.class_histogram
GC.heap_dump
GC.finalizer_info
GC.heap_info
GC.run_finalization
GC.run
VM.uptime
VM.dynlibs
VM.flags
VM.system_properties
VM.command_line
VM.version
help

For more information about a specific command use 'help <command>'.
```

#### 示例1
> jcmd 257587  Thread.print

> jcmd 257587  Thread.print > tp2.txt

> jcmd 257587  Thread.print | grep -A 5 -B 1 'java.lang.Thread.State: RUNNABLE'

``` 
257587:
2021-10-15 15:58:58
Full thread dump Java HotSpot(TM) 64-Bit Server VM (25.191-b12 mixed mode):

"Attach Listener" #534 daemon prio=9 os_prio=0 tid=0x00007fb468014000 nid=0x40e01 waiting on condition [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE

"Okio Watchdog" #525 daemon prio=5 os_prio=0 tid=0x00007fb424081000 nid=0x40a31 in Object.wait() [0x00007fb3fa5ce000]
   java.lang.Thread.State: TIMED_WAITING (on object monitor)
	at java.lang.Object.wait(Native Method)
	at okio.AsyncTimeout.awaitTimeout(AsyncTimeout.java:347)
	at okio.AsyncTimeout$Watchdog.run(AsyncTimeout.java:312)
	- locked <0x00000000fb038278> (a java.lang.Class for okio.AsyncTimeout)

"VM Thread" os_prio=0 tid=0x00007fb4a8176800 nid=0x3ee3b runnable 

"Gang worker#0 (Parallel GC Threads)" os_prio=0 tid=0x00007fb4a801b800 nid=0x3ee36 runnable 

"Gang worker#1 (Parallel GC Threads)" os_prio=0 tid=0x00007fb4a801d000 nid=0x3ee37 runnable 

"Gang worker#2 (Parallel GC Threads)" os_prio=0 tid=0x00007fb4a801f000 nid=0x3ee38 runnable 

"Gang worker#3 (Parallel GC Threads)" os_prio=0 tid=0x00007fb4a8021000 nid=0x3ee39 runnable 

"Concurrent Mark-Sweep GC Thread" os_prio=0 tid=0x00007fb4a8063800 nid=0x3ee3a runnable 

"VM Periodic Task Thread" os_prio=0 tid=0x00007fb4a81ce800 nid=0x3ee44 waiting on condition 

JNI global references: 2553	
```


#### 示例2
> jcmd 257587  GC.heap_info

``` 
 par new generation   total 92160K, used 46913K [0x00000000e0000000, 0x00000000e6400000, 0x00000000f2c00000)
  eden space 81920K,  49% used [0x00000000e0000000, 0x00000000e2735418, 0x00000000e5000000)
  from space 10240K,  66% used [0x00000000e5a00000, 0x00000000e609b018, 0x00000000e6400000)
  to   space 10240K,   0% used [0x00000000e5000000, 0x00000000e5000000, 0x00000000e5a00000)
 concurrent mark-sweep generation total 204800K, used 109421K [0x00000000f2c00000, 0x00000000ff400000, 0x0000000100000000)
 Metaspace --元空间大小       used 132015K, capacity 141885K, committed 142208K, reserved 1173504K
  class space --CompressedClassSpaceSize大小    used 16713K, capacity 18217K, committed 18304K, reserved 1048576K


Metaspace=Metaspace area + class space

```

#### 示例3
> jcmd 257587 VM.command_line

``` 
VM Arguments:
jvm_args: -Xms300M -Xmx512M -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=256m -XX:NewSize=100m -XX:MaxNewSize=300m -XX:SurvivorRatio=8 -XX:-ReduceInitialCardMarks -XX:ParallelGCThreads=4 -XX:MaxTenuringThreshold=9 -XX:+DisableExplicitGC -XX:+ScavengeBeforeFullGC -XX:SoftRefLRUPolicyMSPerMB=0 -XX:+ExplicitGCInvokesConcurrent -XX:+HeapDumpOnOutOfMemoryError -XX:-OmitStackTraceInFastThrow -Duser.timezone=Asia/Shanghai -Dclient.encoding.override=UTF-8 -Dfile.encoding=UTF-8 -Djava.security.egd=file:/dev/./urandom -XX:+UseParNewGC -Xloggc:/app/msa/logs/mutms-customize/gc.log -XX:+PrintGCDetails -XX:+UseConcMarkSweepGC -XX:+UseCMSCompactAtFullCollection -XX:+UseCMSInitiatingOccupancyOnly -XX:CMSInitiatingOccupancyFraction=60 -XX:+CMSClassUnloadingEnabled -XX:+CMSParallelRemarkEnabled -XX:CMSFullGCsBeforeCompaction=9 -XX:+CMSClassUnloadingEnabled -XX:+PrintGCDateStamps -XX:+PrintGCApplicationConcurrentTime -XX:+PrintHeapAtGC -XX:+UseGCLogFileRotation -XX:NumberOfGCLogFiles=5 -XX:GCLogFileSize=5M 
java_command: mutms-customize.jar
java_class_path (initial): mutms-customize.jar
Launcher Type: SUN_STANDARD

```

## jstat

### 常用命令

|选项|说明|示例|
|---|---|---|
|-gc|垃圾回收统计|jstat -gc 265205|
||||
||||
||||

### 代码说明
|代码|说明|
|---|---|
|S0C|年轻代中第一个survivor（幸存区）的容量 （字节）|
|S1C|年轻代中第二个survivor（幸存区）的容量 (字节)|
|S0U|年轻代中第一个survivor（幸存区）目前已使用空间 (字节)|
|S1U|年轻代中第二个survivor（幸存区）目前已使用空间 (字节)|
|EC|年轻代中Eden（伊甸园）的容量 (字节)|
|EU|伊甸园区的使用大小|
|OC|老年代大小|
|OU|老年代使用大小|
|MC|方法区大小|
|MU|方法区使用大小|
|CCSC|压缩类空间大小|
|CCSU|压缩类空间使用大小|
|YGC|年轻代垃圾回收次数|
|YGCT|年轻代垃圾回收消耗时间|
|FGC|老年代垃圾回收次数|
|FGCT|老年代垃圾回收消耗时间|
|GCT|垃圾回收消耗总时间|
|NGCMN|年轻代(young)中初始化(最小)的大小(字节)|
|NGCMX|年轻代(young)的最大容量 (字节)|
|NGC|年轻代(young)中当前的容量 (字节)|
|S0C|年轻代中第一个survivor（幸存区）的容量 (字节)|
|S1C|年轻代中第二个survivor（幸存区）的容量 (字节)|
|EC|年轻代中Eden（伊甸园）的容量 (字节)|
|OGCMN|old代中初始化(最小)的大小 (字节)|
|OGCMX|old代的最大容量(字节)|
|OGC|old代当前新生成的容量 (字节)|
|OC|Old代的容量 (字节)|
|MCMN|metaspace(元空间)中初始化(最小)的大小 (字节)|
|MCMX|metaspace(元空间)的最大容量 (字节)|
|MC|metaspace(元空间)当前新生成的容量 (字节)|
|CCSMN|最小压缩类空间大小|
|CCSMX|最大压缩类空间大小|
|CCSC|当前压缩类空间大小|
|YGC|从应用程序启动到采样时年轻代中gc次数|
|FGC|从应用程序启动到采样时old代(全gc)gc次数|
|FGCT|从应用程序启动到采样时old代(全gc)gc所用时间(s)|
|GCT|从应用程序启动到采样时gc用的总时间(s)|
|||
|||




``` 
[root@localhost tmp]# jstat -options
-class
-compiler
-gc
-gccapacity
-gccause
-gcmetacapacity
-gcnew
-gcnewcapacity
-gcold
-gcoldcapacity
-gcutil
-printcompilation

-class 显示ClassLoad的相关信息；
-compiler 显示JIT编译的相关信息；
-gc 显示和gc相关的堆信息；
-gccapacity 　　 显示各个代的容量以及使用情况；
-gcmetacapacity 显示metaspace的大小
-gcnew 显示新生代信息；
-gcnewcapacity 显示新生代大小和使用情况；
-gcold 显示老年代和永久代的信息；
-gcoldcapacity 显示老年代的大小；
-gcutil　　 显示垃圾收集信息；
-gccause 显示垃圾回收的相关信息（通-gcutil）,同时显示最后一次或当前正在发生的垃圾回收的诱因；
-printcompilation 输出JIT编译的方法信息；

https://www.jianshu.com/p/213710fb9e40

```


#### 示例1 
> jstat -gc 265205

``` 
S0C    S1C    S0U    S1U      EC       EU        OC         OU       MC     MU    CCSC   CCSU   YGC     YGCT    FGC    FGCT     GCT   
10240.0 10240.0 6661.9  0.0   81920.0  52018.4   204800.0   122339.9  166960.0 150026.8 21296.0 18683.0    216    2.311   2      0.083    2.393
```

#### 示例2
> jstat -gccapacity 265205

``` 
 NGCMN    NGCMX     NGC     S0C   S1C       EC      OGCMN      OGCMX       OGC         OC       MCMN     MCMX      MC     CCSMN    CCSMX     CCSC    YGC    FGC 
102400.0 307200.0 102400.0 10240.0 10240.0  81920.0   204800.0   217088.0   204800.0   204800.0      0.0 1196032.0 166960.0      0.0 1048576.0  21296.0    217     2

NGCMN ：年轻代(young)中初始化(最小)的大小(字节)
NGCMX ：年轻代(young)的最大容量 (字节)
NGC ：年轻代(young)中当前的容量 (字节)
S0C ：年轻代中第一个survivor（幸存区）的容量 (字节)
S1C ： 年轻代中第二个survivor（幸存区）的容量 (字节)
EC ：年轻代中Eden（伊甸园）的容量 (字节)
OGCMN ：old代中初始化(最小)的大小 (字节)
OGCMX ：old代的最大容量(字节)
OGC：old代当前新生成的容量 (字节)
OC ：Old代的容量 (字节)
MCMN：metaspace(元空间)中初始化(最小)的大小 (字节)
MCMX ：metaspace(元空间)的最大容量 (字节)
MC ：metaspace(元空间)当前新生成的容量 (字节)
CCSMN：最小压缩类空间大小
CCSMX：最大压缩类空间大小
CCSC：当前压缩类空间大小
YGC ：从应用程序启动到采样时年轻代中gc次数
FGC：从应用程序启动到采样时old代(全gc)gc次数

```

#### 示例3 meta容量信息
> jstat -gcmetacapacity 265205

``` 
MCMN       MCMX        MC       CCSMN      CCSMX       CCSC     YGC   FGC    FGCT     GCT   
0.0  1196032.0   167216.0        0.0  1048576.0    21296.0   219     4    0.178    2.506

MCMN:最小元数据容量
MCMX：最大元数据容量
MC：当前元数据空间大小
CCSMN：最小压缩类空间大小
CCSMX：最大压缩类空间大小
CCSC：当前压缩类空间大小
YGC ：从应用程序启动到采样时年轻代中gc次数
FGC ：从应用程序启动到采样时old代(全gc)gc次数
FGCT ：从应用程序启动到采样时old代(全gc)gc所用时间(s)
GCT：从应用程序启动到采样时gc用的总时间(s)

```

#### 示例4 新生代
> jstat -gcnew 272383

``` 
S0C    S1C    S0U    S1U   TT MTT  DSS      EC       EU     YGC     YGCT  
10240.0 10240.0    0.0 9187.4  2   9 5120.0  81920.0  48155.6    395    3.220

S0C ：年轻代中第一个survivor（幸存区）的容量 (字节)
S1C ：年轻代中第二个survivor（幸存区）的容量 (字节)
S0U ：年轻代中第一个survivor（幸存区）目前已使用空间 (字节)
S1U ：年轻代中第二个survivor（幸存区）目前已使用空间 (字节)
TT：持有次数限制
MTT：最大持有次数限制
DSS：期望的幸存区大小
EC：年轻代中Eden（伊甸园）的容量 (字节)
EU ：年轻代中Eden（伊甸园）目前已使用空间 (字节)
YGC ：从应用程序启动到采样时年轻代中gc次数
YGCT：从应用程序启动到采样时年轻代中gc所用时间(s)

```

#### 示例5 新生代容量
> jstat -gcnewcapacity 272383

``` 
NGCMN      NGCMX       NGC      S0CMX     S0C     S1CMX     S1C       ECMX        EC      YGC   FGC 
102400.0   307200.0   102400.0  30720.0  10240.0  30720.0  10240.0   245760.0    81920.0   395     2

NGCMN ：年轻代(young)中初始化(最小)的大小(字节)
NGCMX ：年轻代(young)的最大容量 (字节)
NGC ：年轻代(young)中当前的容量 (字节)
S0CMX ：年轻代中第一个survivor（幸存区）的最大容量 (字节)
S0C ：年轻代中第一个survivor（幸存区）的容量 (字节)
S1CMX ：年轻代中第二个survivor（幸存区）的最大容量 (字节)
S1C：年轻代中第二个survivor（幸存区）的容量 (字节)
ECMX：年轻代中Eden（伊甸园）的最大容量 (字节)
EC：年轻代中Eden（伊甸园）的容量 (字节)
YGC：从应用程序启动到采样时年轻代中gc次数
FGC：从应用程序启动到采样时old代(全gc)gc次数


```

#### 示例6 老年代

> jstat -gcold 272383

``` 
MC       MU      CCSC     CCSU       OC          OU       YGC    FGC    FGCT     GCT   
144384.0 135899.9  18176.0  16773.0    204800.0     93247.0    396     2    0.076    3.304


MC ：metaspace(元空间)的容量 (字节)
MU：metaspace(元空间)目前已使用空间 (字节)
CCSC:压缩类空间大小
CCSU:压缩类空间使用大小
OC：Old代的容量 (字节)
OU：Old代目前已使用空间 (字节)
YGC：从应用程序启动到采样时年轻代中gc次数
FGC：从应用程序启动到采样时old代(全gc)gc次数
FGCT：从应用程序启动到采样时old代(全gc)gc所用时间(s)
GCT：从应用程序启动到采样时gc用的总时间(s)

```

#### 示例7 老年代容量
> jstat -gcoldcapacity  272383

``` 
   OGCMN       OGCMX        OGC         OC       YGC   FGC    FGCT     GCT   
   204800.0    217088.0    204800.0    204800.0   396     2    0.076    3.304
   
OGCMN ：old代中初始化(最小)的大小 (字节)
OGCMX ：old代的最大容量(字节)
OGC ：old代当前新生成的容量 (字节)
OC ：Old代的容量 (字节)
YGC ：从应用程序启动到采样时年轻代中gc次数
FGC ：从应用程序启动到采样时old代(全gc)gc次数
FGCT ：从应用程序启动到采样时old代(全gc)gc所用时间(s)
GCT：从应用程序启动到采样时gc用的总时间(s)
 
```
#### 示例8
> jstat -gcutil  272383

``` 
S0     S1     E      O      M     CCS    YGC     YGCT    FGC    FGCT     GCT   
67.58   0.00  84.12  45.53  94.12  92.28    396    3.228     2    0.076    3.304

S0 ：年轻代中第一个survivor（幸存区）已使用的占当前容量百分比
S1 ：年轻代中第二个survivor（幸存区）已使用的占当前容量百分比
E ：年轻代中Eden（伊甸园）已使用的占当前容量百分比
O ：old代已使用的占当前容量百分比
P ：perm代已使用的占当前容量百分比
YGC ：从应用程序启动到采样时年轻代中gc次数
YGCT ：从应用程序启动到采样时年轻代中gc所用时间(s)
FGC ：从应用程序启动到采样时old代(全gc)gc次数
FGCT ：从应用程序启动到采样时old代(全gc)gc所用时间(s)
GCT：从应用程序启动到采样时gc用的总时间(s)

```

#### 示例9
> jstat -gccause  272383


``` 
  S0     S1     E      O      M     CCS    YGC     YGCT    FGC    FGCT     GCT    LGCC                 GCC                 
  0.00  56.98  15.31  46.62  93.55  91.41    397    3.237     2    0.076    3.314 Allocation Failure   No GC      
  
  LGCC：最后一次GC原因
GCC：当前GC原因（No GC 为当前没有执行GC）
 
```


## jstack

### 常用命令
> jstack pid

#### 示例1
> jstack 596426

> jstack 596426 > tp.txt
``` 
257587:
2021-10-15 15:58:58
Full thread dump Java HotSpot(TM) 64-Bit Server VM (25.191-b12 mixed mode):

"Attach Listener" #534 daemon prio=9 os_prio=0 tid=0x00007fb468014000 nid=0x40e01 waiting on condition [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE

"Okio Watchdog" #525 daemon prio=5 os_prio=0 tid=0x00007fb424081000 nid=0x40a31 in Object.wait() [0x00007fb3fa5ce000]
   java.lang.Thread.State: TIMED_WAITING (on object monitor)
	at java.lang.Object.wait(Native Method)
	at okio.AsyncTimeout.awaitTimeout(AsyncTimeout.java:347)
	at okio.AsyncTimeout$Watchdog.run(AsyncTimeout.java:312)
	- locked <0x00000000fb038278> (a java.lang.Class for okio.AsyncTimeout)

"VM Thread" os_prio=0 tid=0x00007fb4a8176800 nid=0x3ee3b runnable 

"Gang worker#0 (Parallel GC Threads)" os_prio=0 tid=0x00007fb4a801b800 nid=0x3ee36 runnable 

"Gang worker#1 (Parallel GC Threads)" os_prio=0 tid=0x00007fb4a801d000 nid=0x3ee37 runnable 

"Gang worker#2 (Parallel GC Threads)" os_prio=0 tid=0x00007fb4a801f000 nid=0x3ee38 runnable 

"Gang worker#3 (Parallel GC Threads)" os_prio=0 tid=0x00007fb4a8021000 nid=0x3ee39 runnable 

"Concurrent Mark-Sweep GC Thread" os_prio=0 tid=0x00007fb4a8063800 nid=0x3ee3a runnable 

"VM Periodic Task Thread" os_prio=0 tid=0x00007fb4a81ce800 nid=0x3ee44 waiting on condition 

JNI global references: 2553	
```









