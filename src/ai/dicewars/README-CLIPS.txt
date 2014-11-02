Tested on:
	jdk1.8.0_25 32-bit
	Windows 7

Possible problems:
As I've tested this on windows, I'm not sure if it will work on linux. There may be problems with JNDI library due to using .dll extension. let me know.



INSTRUCTION

JNI libraries provided by CPLIS download page are 32-bit.
The choice is either to:
	- use 32-bit JDK (recommended)
	- recompile the DLL for 64-bit (from C source files attached in folder CLIPSJNI)

A proper path to JNI libraries files must be given on runtime.
To do so:
a) Eclipse
	1. In the Run menu, select Run Configuration.
	2. Go to the (x)=Arguments tab of your sketch.
	3. Add this in the VM arguments field:
		-Djava.library.path="lib"
		(Assuming "Working Directory" is set to default)
b) console
..\ai-dicewars-headnote > java -Djava.library.path=./lib -jar myApplication.jar	



