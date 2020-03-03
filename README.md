# sivs.conk
A very hacky minimal tool for letting ffmpeg turn one of your sound recordings and a still image
 into the sweetest, web-friendly webM format you could imagine.

Requires your recording to be in Ogg Vorbis format and your input still image a PNG.
You may need to have the PNG be even in height and width although who knows /shrug.

## how-to run
  * have java installed and running (this differs depending on the linux)
  * download the latest `sivs.jar` file from [releases](https://github.com/chrysophylax/sivs-conk/releases "sivs-conk releases on GitHub").
  * run the following command
   `java -jar sivs.jar`
  * read instructions :-)

## features
  * will absolutely overwrite any pre-rendered file unless you change the target
  * assumes you are on a Linux machine, have Java and have an x86_64/amd64 CPU
  * is not afraid of the terror-badger

## thanks to
  * everyone who makes clojure so useful
  * ffmpeg people
  * @a.schild and his helpful jave2 library with a static ffmpeg
  * the hero who compiles ffmpeg statically
