(ns sivs.conk
  (:require  [me.raynes.conch :as conch]
             [me.raynes.fs :as fs]
             [me.raynes.conch.low-level :as sh]
             [clojure.java.io :as io]
             [clojure.tools.cli :refer [cli parse-opts]]
             [clojure.string :as s])
  (:gen-class))

(def cli-options
  [["-i" "--image IMAGE" "The png image file"
    :default nil]
   ["-a" "--audio AUDIO" "The ogg-vorbis audio file"
    :default nil]
   ["-t" "--target TARGET" "The output file"
    :default "/tmp/out.webm"
    :parse-fn #(str %)]
   ])

(defn prep-ffmpeg [ffmpeg]
  (with-open [in (io/input-stream (io/resource "ws/schild/jave/nativebin/ffmpeg-amd64"))]
    (io/copy in (io/file ffmpeg))
    (fs/chmod "+x" ffmpeg)
    true))

(defn build-args [sound image target]
  (list "-y" "-loglevel" "info" "-loop" "1" "-framerate" "1" "-i" image
        "-i" sound
        "-c:v" "libvpx-vp9"
        "-r" "10"
        "-c:a" "copy"
        "-f" "webm"
        "-shortest"
        target)
  )

(defn process-video [path args]
  (println "begin processing attempt")
  (conch/let-programs [ffmpeg path]
                      (cond
                        (nil? args) (do
                                        (println "invalid config for video encoder. printing version..." )
                                        (print (ffmpeg "-version")))
                        :else (print (apply ffmpeg args))))
  (println "end processing attempt")
  (System/exit 0)
  )

(defn exec [& {:keys [image audio target]}]
  (let [ffmpeg "/tmp/ffmpeg64"]
    (println (str "input-img=" image ",input-snd=" audio ",target=" target))
    (cond
      (prep-ffmpeg ffmpeg) (do
                             (println "ffmpeg-amd64 unpacked and set executable!")
                             (let [args (build-args audio image target)]
                               (process-video ffmpeg args)))
      :else (println "failed to initialize ffmpeg. sorry"))))

(defn -main [& args]
  (let [{:keys [options arguments errors summary]} (parse-opts args cli-options)
        image (:image options)
        audio (:audio options)
        target (:target options)]
    (println summary)
    (println options)
    (cond
      (and (some? image)(some? audio)) (exec :image image :audio audio :target target)
      :else (println "no args given!"))))
