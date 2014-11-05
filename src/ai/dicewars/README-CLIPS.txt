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

RULES

Provide rules in rules-agent1.clp for your agent and in rules-agent2.clp for the enemy agent.
The fact defining your player number is given as instance of player deftemplate:
	(player (playerNumber 1) (who me)) 
	where 1 stand for your player number.
	
As the result of reasoning made by your rules finally there should be a fact of deftemplate next-move present.
This fact is then retrieved from clips in java as the next move of the actor.



