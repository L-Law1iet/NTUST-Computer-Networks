JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) -encoding utf-8 $*.java

CLASSES = \
	Iperfer.java \
	Iperfer_Server.java \
	Iperfer_Client.java
	

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class
