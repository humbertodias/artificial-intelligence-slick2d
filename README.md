# Games with Slick2D

Simple games using Slick2d Library


## Prerequires

1. Git 2.6+
2. Maven 3+
3. Java 8+


## How to Play

Clone

```
git clone https://github.com/humbertodias/games-with-slick2d.git
```

Inside the folder

```
cd games-with-slick2d
```

Export

**Windows**

```
set MAVEN_OPTS=-Djava.library.path=target/natives
```

**Linux | Mac**

```
export MAVEN_OPTS=-Djava.library.path=target/natives
```

Run

```
mvn package exec:java -Dexec.mainClass="pong.Main"
```
![Preview](doc/pong.gif)
```
mvn package exec:java -Dexec.mainClass="pacman.Main"
```
![Preview](doc/pac-man.gif)
```
mvn package exec:java -Dexec.mainClass="invaders.Main"
```
![Preview](doc/invaders.gif)

```
mvn package exec:java -Dexec.mainClass="spaceinvaders.Main"
```
![Preview](doc/spaceinvaders.gif)



## Manual steps

### Eclipse

* You must have the Maven **Eclipse** integration plugin installed (m2e)
* Import the Maven project
* Right-click on `Game`, Debug as, Java application
* This will fail with `java.lang.UnsatifsiedLinkError`
* Run `mvn package` once, the native libraries will get copied in `target/natives`
* Edit your debug configuration (menu Run, Debug configurations...), on the "Arguments" tab, "VM Arguments" field, enter `-Djava.library.path=target/natives`
* Click on "Debug" and you're all set !


![Run Configuration](doc/run_config_eclipse.png)

### NetBeans

Click on Project and Right Button > Properties.
Then, Run > VM Options

![Run Configuration](doc/run_config_netbeans.png)



## References

[Slick2D Home](http://slick.ninjacave.com)

[LWJGL with Maven](http://wiki.lwjgl.org/index.php?title=Setting_Up_LWJGL_with_Maven)

[Revista como programar: Pong com Slick2D](http://www.portugal-a-programar.pt/revista-programar/edicoes/download.php?e=25&t=site)
