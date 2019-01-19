# ProgProjects

MPI program je v razredu Program2.

## Installing MPJ and Eclipse configuration
Download MPJ Express Software: http://mpj-express.org/download.php
Extract archive to (example): *C:/MPJ*

My Computer > Properties > Advanced System Settings > Environment Variables...

At **System Variables**, select *Path* > Edit > New, add path to MPJ directory (example) *C:/MPJ/bin*.

At **System Variables**, add new variable. Set Variable Name as *MPJ_HOME* and path to (root folder, example) *C:/MPJ*.

In Eclipse, create new Java Project. Right click on project > Build Path > Configure Build Path > select Libraries tab > Add External JARs... and locate mpj.jar file.

Right click on project again > Run as... > Run configurations > select Arguments tab > add *-jar ${MPJ_HOME}/lib/starter.jar -np 4* to **VM Arguments**.

Select Environment tab > New... > set name as MPJ_HOME, click Variables... > Edit Variables > New... > set name as MPJ_HOME and Value to (root directory, example) *C:/MPJ* > press OK > Apply and Close > select the newly created MPJ_HOME variable > OK > OK.

A new variable MPJ_HOME with Value ${MPJ_HOME} should be created.
Press Apply then Run.

Add *import mpi.\*;* to Java class.
