JAVA=java $(JAVAFLAGS)
JAVAC=javac $(JAVACFLAGS)
JAVADOC=javadoc $(JAVADOCFLAGS)
OUTPUTFILTER=2>&1 | nkf
FIND=find
MKDIR=mkdir -p 
MAKE=make
CLASSPATH=lib/javacv.jar:lib/commons-codec-1.6.jar:lib/jocl.jar:lib/gluegen-rt.jar:lib/easycl.jar
JAVACFLAGS= -encoding utf8 -d bin -sourcepath src -cp bin:$(CLASSPATH) -Xlint:deprecation
JAVAFLAGS = -cp bin:$(CLASSPATH):resource
JAVADOCFLAGS= -encoding utf8 -charset utf-8 -d javadoc -sourcepath src -J-Dhttp.proxyHost=proxy.csc.titech.ac.jp -J-Dhttp.proxyPort=8080 -link http://java.sun.com/javase/ja/6/docs/ja/api -link easycl -link http://jogamp.org/deployment/jogamp-next/javadoc/jocl/javadoc -classpath $(CLASSPATH)

RM=rm -rf

SERVADDR=localhost
NUMBER=`echo \`whoami\`0 |sed -e "s/[^0-9]//g"`

.PHONY: clean javadoc extar

.SUFFIXES: .class .java

.java.class: $(<:.class=.java)
	$(MKDIR) bin
	$(JAVAC) $< $(OUTPUTFILTER)

bin/%.class: src/%.java
	$(MKDIR) bin
	$(JAVAC) $< $(OUTPUTFILTER)

.class:
	$(JAVA) $(<:bin/%.class=%)

Main0: 
	$(MKDIR) bin
	$(JAVAC) src/pro3/$@.java $(OUTPUTFILTER)
	$(JAVA) pro3.$@

Main1:
	$(MKDIR) bin
	$(JAVAC) src/pro3/$@.java $(OUTPUTFILTER)
	$(JAVA) pro3.$@

Main2:
	$(MKDIR) bin
	$(JAVAC) src/pro3/$@.java $(OUTPUTFILTER)
	$(JAVA) pro3.$@

Main3:
	$(MKDIR) bin
	$(JAVAC) src/pro3/$@.java $(OUTPUTFILTER)
	$(JAVA) pro3.$@

Main4:
	$(MKDIR) bin
	$(JAVAC) src/pro3/$@.java $(OUTPUTFILTER)
	$(JAVA) pro3.$@

Main5:
	$(MKDIR) bin
	$(JAVAC) src/pro3/$@.java $(OUTPUTFILTER)
	$(JAVA) pro3.$@

Main6:
	$(MKDIR) bin
	$(JAVAC) src/pro3/$@.java $(OUTPUTFILTER)
	$(JAVA) pro3.$@

Main7: 
	$(MKDIR) bin
	$(JAVAC) src/pro3/$@.java $(OUTPUTFILTER)
	$(JAVA) pro3.$@

Main8: 
	$(MKDIR) bin
	$(JAVAC) src/pro3/$@.java $(OUTPUTFILTER)
	$(JAVA) pro3.$@

Main9: 
	$(MKDIR) bin
	$(JAVAC) src/pro3/$@.java $(OUTPUTFILTER)
	$(JAVA) pro3.$@

Main10: 
	$(MKDIR) bin
	$(JAVAC) src/pro3/$@.java $(OUTPUTFILTER)
	$(JAVA) pro3.$@

Main11:
	make Main11Server &
	sleep 3
	make Main11Client

Main11Server: 
	$(MKDIR) bin
	$(JAVAC) src/pro3/$@.java $(OUTPUTFILTER)
	$(JAVA) pro3.$@

# at remote host, make Main11Server &
# then make SERVADDR=hostname_or_hostip Main11Client
Main11Client: 
	$(MKDIR) bin
	$(JAVAC) src/pro3/$@.java $(OUTPUTFILTER)
	$(JAVA) pro3.$@ $(SERVADDR)

Main12:
	$(MKDIR) bin
	$(JAVAC) src/pro3/$@.java $(OUTPUTFILTER)
	$(JAVA) pro3.$@

Main13Server: 
	$(MKDIR) bin
	$(JAVAC) src/pro3/$@.java $(OUTPUTFILTER)
	$(JAVA) pro3.$@

# at remote host, make Main13Server &
# make SERVADDR=hostname_or_hostip Main13Client or
# make SERVADDR=hostname_or_hostip NUMBER=your_favorite_integer Main13Client
Main13Client:
	$(MKDIR) bin
	$(JAVAC) src/pro3/$@.java $(OUTPUTFILTER)
	$(JAVA) pro3.$@ $(NUMBER) $(SERVADDR)

# make Main14 or
# make NUMBER=your_favorite_integer Main14
Main14:
	$(MKDIR) bin
	$(JAVAC) src/pro3/$@.java $(OUTPUTFILTER)
	$(JAVA) pro3.$@ $(NUMBER)

Main15:
	$(MKDIR) bin
	$(JAVAC) src/pro3/$@.java $(OUTPUTFILTER)
	$(JAVA) pro3.$@

Main16:
	$(MKDIR) bin
	$(JAVAC) src/pro3/$@.java $(OUTPUTFILTER)
	$(JAVA) pro3.$@

Main17:
	$(MKDIR) bin
	$(JAVAC) src/pro3/$@.java $(OUTPUTFILTER)
	$(JAVA) pro3.$@

Main18:
	$(MKDIR) bin
	$(JAVAC) src/pro3/$@.java $(OUTPUTFILTER)
	$(JAVA) pro3.$@

Main19:
	$(MKDIR) bin
	$(JAVAC) src/pro3/$@.java $(OUTPUTFILTER)
	$(JAVA) pro3.$@

Main20:
	$(MKDIR) bin
	$(JAVAC) src/pro3/$@.java $(OUTPUTFILTER)
	$(JAVA) pro3.$@

Main21:
	$(MKDIR) bin
	$(JAVAC) src/pro3/$@.java $(OUTPUTFILTER)
	$(JAVA) pro3.$@

Main22:
	$(MKDIR) bin
	$(JAVAC) src/pro3/$@.java $(OUTPUTFILTER)
	$(JAVA) pro3.$@

Main23:
	$(MKDIR) bin
	$(JAVAC) src/pro3/$@.java $(OUTPUTFILTER)
	$(JAVA) pro3.$@

Main24:
	$(MKDIR) bin
	$(JAVAC) src/pro3/$@.java $(OUTPUTFILTER)
	$(JAVA) pro3.$@

Main25:
	$(MKDIR) bin
	$(JAVAC) src/pro3/$@.java $(OUTPUTFILTER)
	$(JAVA) pro3.$@

Main26Server:
	$(MKDIR) bin
	$(JAVAC) src/pro3/$@.java $(OUTPUTFILTER)
	$(JAVA) pro3.$@

# at remote host, make Main26Server &
# then make SERVADDR=hostname_or_hostip Main26Client
Main26Client: 
	$(MKDIR) bin
	$(JAVAC) src/pro3/$@.java $(OUTPUTFILTER)
	$(JAVA) pro3.$@ $(SERVADDR)

HelloOpenCL:
	$(MKDIR) bin
	$(JAVAC) src/pro3/$@.java $(OUTPUTFILTER)
	$(JAVA) pro3.$@

Sum3:
	$(MKDIR) bin
	$(JAVAC) src/pro3/$@.java $(OUTPUTFILTER)
	$(JAVA) pro3.$@

Max3:
	$(MKDIR) bin
	$(JAVAC) src/pro3/$@.java $(OUTPUTFILTER)
	$(JAVA) pro3.$@

clean:	
	$(RM) bin javadoc movie.avi moviec.avi tout.txt

javadoc::
	$(FIND) . -name "._*" -exec $(RM) {} \;
	$(MKDIR) javadoc
	cp -R lib/javadoc/easycl javadoc
	$(JAVADOC) pro3 pro3.shape pro3.target pro3.parser pro3.camera pro3.opencl $(OUTPUTFILTER)

# make tar.gz from git archive
extar::
	git diff
#	git commit
	git archive --format=tar --prefix=Zukei/ HEAD |gzip > ../zukei-export.tar.gz
