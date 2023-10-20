d:
cd D:\git\blg\com.tugalsan.gvm.fix.input
cmd /c mvnd clean install -DskipTests -q versions:display-dependency-updates 
copy D:\git\gvm\com.tugalsan.gvm.fix.input\target\com.tugalsan.gvm.fix.input-1.0-SNAPSHOT-jar-with-dependencies.jar C:\bin\com.tugalsan\com.tugalsan.gvm.fix.input\com.tugalsan.gvm.fix.input.jar /y